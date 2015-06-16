package distNewCarc.partition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import base.BasicTheoryAgent;
import base.TheoryAgent;


import solarInterface.CFSolver;
import solarInterface.IndepPField;
import solarInterface.SolProblem;
import stats.ConsFindingAgentStats;

import logicLanguage.CNF;
import logicLanguage.IndepClause;
import logicLanguage.IndepLiteral;
import agLib.agentCommunicationSystem.BasicAgent;
import agLib.agentCommunicationSystem.CanalComm;
import agLib.agentCommunicationSystem.CommunicationModule;
import agLib.agentCommunicationSystem.Network;
import agLib.agentCommunicationSystem.protocols.MainProtocol;
import agLib.masStats.StatCounter;

public class IncConsFindingAgent extends BasicTheoryAgent implements TheoryAgent,
		PBICFAgent {

	
	public IncConsFindingAgent(int id, SolProblem pb,
			CanalComm systComm, Network net, ConsFindingAgentStats das, boolean useNewConsAsAxiom, boolean inDepthPruning){
		super(id,pb,systComm,net,das);
		newConsAsAxiom=useNewConsAsAxiom;
		pruneInDepth=inDepthPruning;
	}
	
	public Collection<IndepClause> getAllTopClauses() {
		
		return theory.getTopClauses(true);
	}

	// remove from clauses the clauses that are subsumed by clauses in pruningSet
	public CNF pruneWith(Collection<IndepClause> clauses, Collection<IndepClause> pruningSet){
		CNF result=new CNF();
		if (!pruneInDepth)
			for (IndepClause cl:clauses){
				boolean toKeep=true;
				for (IndepClause cl2:pruningSet){
					if (cl2.subsumes(cl)){
						toKeep=false;
						break;
					}
				}
				if (toKeep)
					result.add(cl);
			}
		else {
			result.addAll(CFSolver.pruneClauseSetFromCons(clauses, pruningSet, true, theory.getDepthLimit(), -1, stats.getSolarCtrList()));
		}
		return result;		
	}


	
	public Collection<IndepClause> computeNewCons(Collection<IndepClause> newCl) {
		CNF result = new CNF();
		//prune(newCl);
		CNF axioms=theory.getAxioms(false);
		if (newConsAsAxiom) 
			axioms.addAll(listCsq);
		else
			axioms.addAll(receivedCl);
		CNF prunedTC=pruneWith(newCl,axioms);
		if (newConsAsAxiom)
			prunedTC=pruneWith(prunedTC,receivedCl);
		else
			prunedTC=pruneWith(prunedTC,listCsq);
		if (verbose){ 
			System.out.println(this+" receives "+newCl);
			System.out.println("After pruning "+prunedTC);
			System.out.println("Input Languages : "+inputLanguages);
		}
		if (prunedTC.isEmpty())
			return result;
		//determine pField as LP, Output Language and reduc(newCl,OutputL)
		IndepPField originalPField=theory.getPField();
		IndepPField computationPField=addReduc(outputLanguage,newCl);
		computationPField=computationPField.mergeWith(originalPField, IndepPField.MRG_UNION_INITFIT);
		
		//compute newcarc
		SolProblem pb=new SolProblem(axioms,prunedTC,computationPField);
		pb.setDepthLimit(theory.getDepthLimit());	
		boolean incremental = false; boolean trueNC = false;
		CFSolver.solveToIndepClause(pb, -1, stats.getSolarCtrList(), result, incremental, trueNC);
		//update receivedCl (after computation in case so that original state is used during it)
		receivedCl=pruneWith(receivedCl,prunedTC);
		receivedCl.addAll(prunedTC);
		return result;
	}

	public IndepPField addReduc(IndepPField base, Collection<IndepClause> clauses){
		List<IndepLiteral> knownLits= base.getLiterals();
		List<IndepLiteral> unknownLits= new ArrayList<IndepLiteral>();
		for (IndepClause cl:clauses){
			for (IndepLiteral lit:cl.getVocabulary()){
				//TODO : use contains or subsumption ? check on free literals ?
				if (knownLits.contains(lit) || knownLits.contains(lit.negate(false)))
					continue;
				else
					if (!unknownLits.contains(lit))
						unknownLits.add(lit);
			}
		}
		return base.addToLiterals(unknownLits);
	}
	
	public boolean canResolve(CanalComm target, IndepClause cl) {
		IndepPField pf=inputLanguages.get(target);
		return pf.partlyBelongsTo(cl);
	}

	public boolean isPossibleOutput(IndepClause cl) {
		return theory.getPField().belongsTo(cl);
	}
	
	public void updateListNewCons(Collection<IndepClause> newCsq) {
		if (newConsAsAxiom) {
			//remove old csq submused by new ones
			CNF updatedList=pruneWith(listCsq,newCsq);
			// prune receivedCl
			receivedCl=pruneWith(receivedCl,newCsq);
			//add new cons
			updatedList.addAll(newCsq);
			//store update
			listCsq=updatedList;
		}
		else {
			//remove old csq submused by new ones
			CNF updatedList=pruneWith(listCsq,newCsq);
			//add new cons
			updatedList.addAll(newCsq);
			//store update while pruning with receivedCl
			listCsq=updatedList;
				//pruneWith(updatedList,receivedCl);			
		}
		if (verbose){ 
			System.out.println(this+": updated receivedCl "+receivedCl);
			System.out.println("updated lstCsq "+listCsq);
		}

	}

	public Collection<IndepClause> getListConseq() {
		return listCsq;
	}

	
	public void setOutputLanguage(IndepPField language){
		outputLanguage=language;
	}
	
	public void setInputLanguage(IndepPField language, CanalComm ag){
		inputLanguages.put(ag, language);
	}
	
	public void setProtocol(MainProtocol prot){
		cAg.setProtocol(prot);
	}
	
	public CommunicationModule getCommModule(){
		return cAg;
	}
	
	protected boolean newConsAsAxiom=true; // else use receivedCl
	protected boolean pruneInDepth=false; //prune by searching consequences and not jsut subsumption
	
	public static boolean verbose=false;
	
	protected Collection<IndepClause> listCsq=new CNF();
	protected Collection<IndepClause> receivedCl=new ArrayList<IndepClause>();
	protected IndepPField outputLanguage;
	protected HashMap<CanalComm,IndepPField> inputLanguages=new HashMap<CanalComm,IndepPField>();

}
