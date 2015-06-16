package base;

import java.util.Collection;

import agLib.agentCommunicationSystem.Agent;

import logicLanguage.IndepLiteral;

public interface TheoryAgent extends Agent{
	public Collection<IndepLiteral> getVocabulary();
	public Collection<IndepLiteral> getNegatedVocabulary();
	public Collection<IndepLiteral> getFullVocabulary();
	
}
