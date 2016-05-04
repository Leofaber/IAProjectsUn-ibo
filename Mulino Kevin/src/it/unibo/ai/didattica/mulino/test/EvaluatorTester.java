package it.unibo.ai.didattica.mulino.test;

import java.util.HashMap;

import it.unibo.ai.didattica.mulino.actions.Phase1;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.client.HeuristicEvaluator;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class EvaluatorTester {

	public EvaluatorTester() {
		
	}

	
	public static void printEvaluations(HeuristicEvaluator eval){
		
		
		
		// 18 * (1) + 26 * (2) + 1 * (3) + 9 * (4) + 10 * (5) + 7 * (6)
		HashMap<String,Integer> vals1 = new HashMap<String,Integer>();
		vals1.put("numberOfPieces", 9);
		vals1.put("numberOfMorris", 26);
		vals1.put("doubleMorris", 9);
		vals1.put("blockedOpponents", 1);
		vals1.put("twoPieces", 10);
		vals1.put("threePieces", 7);
		
		if(eval.getPlayer() == Checker.BLACK){
			System.out.println("[EvaluatorTester]    Black Pieces - White Pieces = "+eval.numberOfPieces()+"\n");
			System.out.println("[EvaluatorTester]    Black Morris - White Morris = "+ eval.numberOfMorris()+"\n");
			System.out.println("[EvaluatorTester]    Black Double Morris - White Double Morris = "+ eval.doubleMorris()+"\n");
			System.out.println("[EvaluatorTester]    White Blocked Pieces - Black Blocked Pieces = "+eval.numberOfBlockedOpponentPieces()+"\n");
			System.out.println("[EvaluatorTester]    Black2PiecesConf - White2PiecesConf = "+eval.numberOf2PieceConfigurations()+"\n");
			System.out.println("[EvaluatorTester]    Black3PiecesConf - White3PiecesConf = "+eval.numberOf3PieceConfigurations()+"\n");
		
		}else{
			System.out.println("[EvaluatorTester]    White Pieces - Black Pieces = "+eval.numberOfPieces());
			System.out.println("[EvaluatorTester]    White Morris - Black Morris = "+ eval.numberOfMorris());
			System.out.println("[EvaluatorTester]    White Double Morris - Black Double Morris = "+ eval.doubleMorris());
			System.out.println("[EvaluatorTester]    Black Blocked Pieces - White Blocked Pieces = "+eval.numberOfBlockedOpponentPieces());
			System.out.println("[EvaluatorTester]    White2PiecesConf - Black2PiecesConf = "+eval.numberOf2PieceConfigurations());
			System.out.println("[EvaluatorTester]    White3PiecesConf - Black3PiecesConf = "+eval.numberOf3PieceConfigurations());
		
		}
	}
	
	public static void main(String[] args) {
		State state=new State();
		Phase1Action action;
		try{						
			action = new Phase1Action();
			action.setPutPosition("d1");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			System.out.println(state.toString());
			HeuristicEvaluator eval=new HeuristicEvaluator(state, Checker.BLACK);
			
			
			/* * * * * * * * * * * * * * * * *
			 * 	 CALA'S POWER FUNCTION CALL  * 
			 * * * * * * * * * * * * * * * * */
			state.setArrayOfMorris();
			/* * * * * * * * * * * * * * * * * *
			 *  END CALA'S POWER FUNCTION CALL *
			 * * * * * * * * * * * * * * * * * */
			
			EvaluatorTester.printEvaluations(eval);

		
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
		try{						
			action = new Phase1Action();
			action.setPutPosition("d2");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			System.out.println(state.toString());
			HeuristicEvaluator eval=new HeuristicEvaluator(state, Checker.BLACK);
			
			/* * * * * * * * * * * * * * * * *
			 * 	 CALA'S POWER FUNCTION CALL  * 
			 * * * * * * * * * * * * * * * * */
			state.setArrayOfMorris();
			/* * * * * * * * * * * * * * * * * *
			 *  END CALA'S POWER FUNCTION CALL *
			 * * * * * * * * * * * * * * * * * */
			
			EvaluatorTester.printEvaluations(eval);

		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		try{						
			action = new Phase1Action();
			action.setPutPosition("a7");
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
			
			EvaluatorTester.printEvaluations(eval);

		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		try{						
			action = new Phase1Action();
			action.setPutPosition("d3");
			action.setRemoveOpponentChecker("a7");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			System.out.println(state.toString());
			HeuristicEvaluator eval=new HeuristicEvaluator(state, Checker.BLACK);
			
			/* * * * * * * * * * * * * * * * *
			 * 	 CALA'S POWER FUNCTION CALL  * 
			 * * * * * * * * * * * * * * * * */
			state.setArrayOfMorris();
			/* * * * * * * * * * * * * * * * * *
			 *  END CALA'S POWER FUNCTION CALL *
			 * * * * * * * * * * * * * * * * * */
	
			EvaluatorTester.printEvaluations(eval);

		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
		try{						
			action = new Phase1Action();
			action.setPutPosition("g1");
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
						
			EvaluatorTester.printEvaluations(eval);

		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
		try{						
			action = new Phase1Action();
			action.setPutPosition("a1");
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
			
			EvaluatorTester.printEvaluations(eval);


		}catch(Exception e){
			System.out.println("Exception: "+e);
		}

		try{						
			action = new Phase1Action();
			action.setPutPosition("c3");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			action = new Phase1Action();
			action.setPutPosition("e3");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			
			System.out.println(state.toString());
			HeuristicEvaluator eval=new HeuristicEvaluator(state, Checker.BLACK);
			
			/* * * * * * * * * * * * * * * * *
			 * 	 CALA'S POWER FUNCTION CALL  * 
			 * * * * * * * * * * * * * * * * */
			state.setArrayOfMorris();
			/* * * * * * * * * * * * * * * * * *
			 *  END CALA'S POWER FUNCTION CALL *
			 * * * * * * * * * * * * * * * * * */
			
			EvaluatorTester.printEvaluations(eval);


		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
		try{						
			action = new Phase1Action();
			action.setPutPosition("e4");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			action = new Phase1Action();
			action.setPutPosition("c4");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			action = new Phase1Action();
			action.setPutPosition("g4");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			action = new Phase1Action();
			
			
			action = new Phase1Action();
			action.setPutPosition("a7");
			state=Phase1.applyMove(state, action, Checker.WHITE);
	
 			action = new Phase1Action();
			action.setPutPosition("f4");
			action.setRemoveOpponentChecker("g1");
			state=Phase1.applyMove(state, action, Checker.BLACK);
			
			action = new Phase1Action();
			action.setPutPosition("a4");
			action.setRemoveOpponentChecker("e3");
			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			action = new Phase1Action();
			action.setPutPosition("e5");
 			state=Phase1.applyMove(state, action, Checker.BLACK);

 			action = new Phase1Action();
			action.setPutPosition("d7");
 			state=Phase1.applyMove(state, action, Checker.WHITE);
 			
 			action = new Phase1Action();
			action.setPutPosition("c5");
			state=Phase1.applyMove(state, action, Checker.BLACK);
 			
 			
			
//			action = new Phase1Action();
//			action.setPutPosition("g7");
//			action.setRemoveOpponentChecker("d3");
//			state=Phase1.applyMove(state, action, Checker.WHITE);
			
//			action = new Phase1Action();
//			action.setPutPosition("b2");
//			state=Phase1.applyMove(state, action, Checker.BLACK);
//			
//			action = new Phase1Action();
//			action.setPutPosition("c3");
//			state=Phase1.applyMove(state, action, Checker.WHITE);
			
			
 			
			System.out.println(state.toString());
			HeuristicEvaluator eval=new HeuristicEvaluator(state, Checker.BLACK);
			
			/* * * * * * * * * * * * * * * * *
			 * 	 CALA'S POWER FUNCTION CALL  * 
			 * * * * * * * * * * * * * * * * */
			state.setArrayOfMorris();
			/* * * * * * * * * * * * * * * * * *
			 *  END CALA'S POWER FUNCTION CALL *
			 * * * * * * * * * * * * * * * * * */
		
			EvaluatorTester.printEvaluations(eval);

		}catch(Exception e){
			System.out.println("Exception: "+e);
		}

		
		
	}
	
	
}
