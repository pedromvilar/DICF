/**

 * 

 */

package base;



import java.util.List;

import logicLanguage.CNF;
import logicLanguage.IndepClause;
import solarInterface.CFSolver;
import solarInterface.IndepPField;
import solarInterface.SolProblem;
import agLib.masStats.StatCounter;


/**
 * @author Viel Charlotte and Gauvain Bourgne
 *
 */

public class ConsFindingLocalTheory extends SolProblem {

	
	public ConsFindingLocalTheory(SolProblem theory, int id) {
		super(theory,true);
		refNumber=id;
	}


	public ConsFindingLocalTheory(int id) {
		refNumber=id;
	}


	public SolProblem getTheory(boolean ref){
		if (ref)
			return this;
		else
			return new SolProblem(this,true);
	}

	/**
	 * @param rules the rules to add 
	 */
	public boolean addToTheory(CNF clauses)  {
		if (clauses.isEmpty()) return false;
		boolean added = false;

		for (IndepClause cl:clauses)
			added=added||!getAllClauses().contains(cl);

		addAxioms(clauses);

		if (!added)
			System.out.println(" already in theory of ag" + this.refNumber);
		return added;
	}



	////	TOOLS FOR COMPUTATIONS
	
	public CNF consequenceFinding(IndepPField pField, List<IndepClause> topClauses,
					List<StatCounter<Integer>> ctr, boolean usePrevTopClauses, long deadline) {
		
		SolProblem pb;
		if (!usePrevTopClauses)
			pb=new SolProblem(getAllClauses(),topClauses,pField);
		else{
			CNF tc= new CNF();
			tc.addAll(getTopClauses(true));
			tc.addAll(topClauses);
			pb=new SolProblem(getAxioms(false),tc,pField);
		}
		pb.setDepthLimit(getDepthLimit());	
		CNF result = new CNF();
		// TODO !!! Attention : trueNewCarc was at false for PB base approaches... Not sure if better. TO TEST.
		boolean trueNewCarc=!(topClauses==null || topClauses.isEmpty());
		CFSolver.solveToIndepClause(pb, deadline, ctr, result, incremental, trueNewCarc);
		return result;
	}

	

	public String toString() {
		return "Theory "+refNumber+": "+super.toString();		
	}
	
//	protected SolProblem theory=new SolProblem();
	public int refNumber=0;
	public boolean incremental=false;
	public static final int CARC = 0;
	public static final int NEW_CARC = 1;	
//	private boolean verbose;

}

