package distNewCarc.partition.starbased;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import logicLanguage.IndepClause;
import agLib.agentCommunicationSystem.CanalComm;
import agLib.agentCommunicationSystem.CommunicationModule;
import agLib.agentCommunicationSystem.Message;
import agLib.agentCommunicationSystem.SystemMessage;
import agLib.agentCommunicationSystem.SystemMessageTypes;
import agLib.agentCommunicationSystem.protocols.BaseMainProtocol;
import base.CFMessage;
import base.CFMessageTypes;
import distNewCarc.partition.PBICFAgent;

public class StarBasedProtocol extends BaseMainProtocol implements CFMessageTypes{

		
	public StarBasedProtocol(CommunicationModule cAg, PBICFAgent ag, CanalComm output, CanalComm root) {
			super(cAg);
			this.ag=ag;
			this.root=root;
			this.output=output;
			isRoot=root==cAg.getComm();		
		}

		@Override
		public boolean gereParProtocol(Message<?> m) {
			if (m instanceof CFMessage){
				switch(m.getCode()){
				case PBM_SEND_CLAUSES:
				case PBM_SEND_CONSEQS:
					return true;
				}
			}
			if (m instanceof SystemMessage){
				switch(m.getCode()){
				case SystemMessageTypes.SYS_LAUNCH:
					return true;
				}
			}		
			return false;
		}

		@Override
		public void receiveMessage(Message<?> m) {
			if (m instanceof CFMessage){
				switch(m.getCode()){
				case PBM_SEND_CLAUSES:
					if (isRoot)
						receiveClAsRoot(((CFMessage)m).getArgument(),m.getSender());
					else
						receiveClAsLeaf(((CFMessage)m).getArgument());
					break;
				case PBM_SEND_CONSEQS:
					break;
				}
			}
			if (m instanceof SystemMessage){
				switch(m.getCode()){
				case SystemMessageTypes.SYS_LAUNCH:
					if (isRoot) {
						Collection<IndepClause> emptySet=new ArrayList<IndepClause>();
						receiveClAsRoot(emptySet,null);
					}
					
				}
			}
		}

		@Override
		public void initProtocol() {
			firstRun=true;
		}

		
		
		
		
		
		protected void receiveClAsRoot(Collection<IndepClause> sentCl, CanalComm sender){
			//Collection<IndepClause> newCl=new ArrayList<IndepClause>();
			Collection<IndepClause> newCsq=new ArrayList<IndepClause>();
			boolean sendAll=firstRun;
			
			//Initialisation
			if (firstRun) {
				firstRun=false;
				receivedCl.addAll(ag.getAllTopClauses());
			}
			receivedCl.addAll(sentCl);
			waitingFor.remove(sender);
			
			if (waitingFor.isEmpty()){
				//Computing new consequence
				newCsq=ag.computeNewCons(receivedCl); // TODO: check that correct pfield is used
				ag.updateListNewCons(newCsq);
				//Send relevant new consequences to other agents
				for (CanalComm target:cAg.getNeighbours()){
					Collection<IndepClause> clBatch=new ArrayList<IndepClause>();
					for (IndepClause cl:newCsq){
						if (ag.canResolve(target,cl))
							clBatch.add(cl);
					}
					if (!clBatch.isEmpty() || sendAll){
						cAg.send(new CFMessage(CFMessageTypes.PBM_SEND_CLAUSES,clBatch), target);
						waitingFor.add(target);
					}
				}
				//Ending condition
				if (waitingFor.isEmpty()){
					ArrayList<IndepClause> csqBatch=new ArrayList<IndepClause>();
					for (IndepClause cl:ag.getListConseq()){			
						if (ag.isPossibleOutput(cl))
							csqBatch.add(cl);
					}
					if (!csqBatch.isEmpty())
						cAg.send(new CFMessage(CFMessageTypes.PBM_SEND_CONSEQS,csqBatch), output);
					cAg.send(new SystemMessage(SystemMessageTypes.SYS_FINISH,cAg.getComm()),cAg.commSystem);
				}															
			}
		}

		protected void receiveClAsLeaf(Collection<IndepClause> sentCl){
			Collection<IndepClause> newCl=new ArrayList<IndepClause>();
			Collection<IndepClause> newCsq=new ArrayList<IndepClause>();
			//Initialisation
			if (firstRun) {
				firstRun=false;
				newCl.addAll(ag.getAllTopClauses());
			}
			newCl.addAll(sentCl);
			//Computing new consequence
			newCsq=ag.computeNewCons(newCl);
			ag.updateListNewCons(newCsq);
			//Send all new consequences to root
			cAg.send(new CFMessage(CFMessageTypes.PBM_SEND_CLAUSES,newCsq), root);
		}

		
		protected boolean firstRun=true;
		protected PBICFAgent ag;
		protected CanalComm root,output;
		protected boolean isRoot;
		
		protected HashSet<CanalComm> waitingFor=new HashSet<CanalComm>();
		protected Collection<IndepClause> receivedCl=new ArrayList<IndepClause>();
		
		public static boolean verbose = true;

		
}

