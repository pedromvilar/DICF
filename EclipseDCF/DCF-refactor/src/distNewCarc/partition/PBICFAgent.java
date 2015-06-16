package distNewCarc.partition;

import java.util.Collection;

import agLib.agentCommunicationSystem.Agent;
import agLib.agentCommunicationSystem.CanalComm;

import logicLanguage.IndepClause;

public interface PBICFAgent extends Agent{
	public Collection<IndepClause> getAllTopClauses();
	public Collection<IndepClause> computeNewCons(Collection<IndepClause> newCl);
	public boolean canResolve(CanalComm target, IndepClause cl);
	public boolean isPossibleOutput(IndepClause cl);
	public void updateListNewCons(Collection<IndepClause> newCl);
	public Collection<IndepClause> getListConseq();
}
