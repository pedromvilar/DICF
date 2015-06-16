package base;

import java.util.Collection;

import solarInterface.SolProblem;
import stats.ConsFindingAgentStats;

import logicLanguage.IndepLiteral;
import agLib.agentCommunicationSystem.BasicAgent;
import agLib.agentCommunicationSystem.CanalComm;
import agLib.agentCommunicationSystem.CommunicationModule;
import agLib.agentCommunicationSystem.Network;

public class BasicTheoryAgent extends BasicAgent implements TheoryAgent {

	public BasicTheoryAgent(int id, SolProblem pb,
			CanalComm systComm, Network net, ConsFindingAgentStats das){
		name="Ag"+id;
		CanalComm comm=new CanalComm(this);
		theory=new ConsFindingLocalTheory(pb,id);
		stats=das;
		cAg = new CommunicationModule(comm,systComm, net, das);
	}
	
	public Collection<IndepLiteral> getVocabulary() {		
		return theory.getAllClauses().getVocabulary();
	}

	public Collection<IndepLiteral> getNegatedVocabulary() {
		return theory.getAllClauses().getNegatedVocabulary();
	}

	public Collection<IndepLiteral> getFullVocabulary() {
		return theory.getAllClauses().getFullVocabulary();
	}

	
	
	protected ConsFindingLocalTheory theory;
	public ConsFindingAgentStats stats;
	
}
