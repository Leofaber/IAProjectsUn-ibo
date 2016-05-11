	package it.unibo.ai.didattica.mulino.test;

import it.unibo.ai.didattica.mulino.actions.Phase1;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.client.MulinoGame;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
	
	public class ClosedMillTester {
	
		public static void main(String[] args) {
			State state=new State();
			Phase1Action action;
			try{						
				action = new Phase1Action();
				action.setPutPosition("a1");
				state=Phase1.applyMove(state, action, Checker.BLACK);
				action = new Phase1Action();
				action.setPutPosition("c4");
				state=Phase1.applyMove(state, action, Checker.WHITE);
				action = new Phase1Action();
				action.setPutPosition("a7");
				state=Phase1.applyMove(state, action, Checker.BLACK);
				action = new Phase1Action();
				action.setPutPosition("a4");
				action.setRemoveOpponentChecker("c4");
				state=Phase1.applyMove(state, action, Checker.BLACK);
				action = new Phase1Action();
				action.setPutPosition("g1");
				state=Phase1.applyMove(state, action, Checker.BLACK);
				action = new Phase1Action();
				action.setPutPosition("g7");
				state=Phase1.applyMove(state, action, Checker.BLACK);
				action = new Phase1Action();
//				System.out.println(state.toString());
				
				String actionString="d7";
				System.out.println("BLACK TRIS CON "+actionString+"? "+ (MulinoGame.closedMill(state, actionString, Checker.BLACK) ? "si" : "no"));	

			}catch(Exception e){
				System.out.println("Exception: "+e);
			}
	}
		
}
