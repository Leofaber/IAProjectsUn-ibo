package it.unibo.ai.didattica.mulino.test;

import it.unibo.ai.didattica.mulino.actions.Phase1;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.client.HeuristicEvaluator;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class EvaluatorTesterIlRitorno {

	public EvaluatorTesterIlRitorno() {
		
	}

	public static void main(String[] args) {
		State state=new State();
		Phase1Action action;
		try{						
			action = new Phase1Action();
			action.setPutPosition("d1");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			
			action = new Phase1Action();
			action.setPutPosition("d2");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			action = new Phase1Action();
			action.setPutPosition("a4");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			
			action = new Phase1Action();
			action.setPutPosition("b4");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			action = new Phase1Action();
			action.setPutPosition("f4");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			
			
			action = new Phase1Action();
			action.setPutPosition("g4");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			
			action = new Phase1Action();
			action.setPutPosition("a7");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			
			action = new Phase1Action();
			action.setPutPosition("d7");
			state=Phase1.applyMove(state, action, Checker.BLACK);

			action = new Phase1Action();
			action.setPutPosition("g1");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			action = new Phase1Action();
			action.setPutPosition("f2");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			
			action = new Phase1Action();
			action.setPutPosition("b6");
			state=Phase1.applyMove(state, action, Checker.WHITE);

			System.out.println(state.toString());
			HeuristicEvaluator eval=new HeuristicEvaluator(state, Checker.BLACK);
			
			/* * * * * * * * * * * * * * * * *
			 * 	 CALA'S POWER FUNCTION CALL  * 
			 * * * * * * * * * * * * * * * * */
			state.setArrayOfMorris();
			/* * * * * * * * * * * * * * * * * *
			 *  END CALA'S POWER FUNCTION CALL *
			 * * * * * * * * * * * * * * * * * */
			
			System.out.println("TRIS BLACK-TRIS WHITE: "+ eval.numberOfMorris());
			System.out.println("DOUBLE BLACK-DOUBLE WHITE: "+ eval.doubleMorris());
			System.out.println("PEZZI BLACK-PEZZI WHITE: "+eval.numberOfPieces());
			System.out.println("PEZZI WHITE BLOCCATI - BLACK BLOCCATI: "+eval.numberOfBlockedOpponentPieces());
			System.out.println("DOUBLE MORRIS BLACK - DOUBLE MORRIS WHITE: "+eval.doubleMorris());
			System.out.println("2PIECEBLACK - 2PIECEWHITE "+eval.numberOf2PieceConfigurations());
			System.out.println("3PIECEBLACK - 3PIECEWHITE "+eval.numberOf3PieceConfigurations());
			
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
	}

}
