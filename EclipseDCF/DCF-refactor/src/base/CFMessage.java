package base;

import java.util.Collection;

import logicLanguage.IndepClause;
import agLib.agentCommunicationSystem.MessageBase;

public class CFMessage extends MessageBase<Collection<IndepClause>> implements CFMessageTypes{

	public CFMessage(int code, Collection<IndepClause> argument){
		super(code, argument);
	}

	@Override
	public int getSize() {		
		return argument.size();
	}

	@Override
	public String toString() {
		switch(code){
		case PBM_SEND_CLAUSES:
			return "Send Clauses : "+argument.toString();
		case PBM_SEND_CONSEQS:
			return "Send Consequences : "+argument.toString();
		}
		return "Unknown message";
	}

}
