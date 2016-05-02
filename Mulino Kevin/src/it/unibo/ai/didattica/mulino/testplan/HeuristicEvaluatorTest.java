package it.unibo.ai.didattica.mulino.testplan;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.unibo.ai.didattica.mulino.actions.NoMoreCheckersAvailableException;
import it.unibo.ai.didattica.mulino.actions.NullCheckerException;
import it.unibo.ai.didattica.mulino.actions.NullStateException;
import it.unibo.ai.didattica.mulino.actions.Phase1;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.actions.PositionNotEmptyException;
import it.unibo.ai.didattica.mulino.actions.TryingToRemoveCheckerInTripleException;
import it.unibo.ai.didattica.mulino.actions.TryingToRemoveEmptyCheckerException;
import it.unibo.ai.didattica.mulino.actions.TryingToRemoveOwnCheckerException;
import it.unibo.ai.didattica.mulino.actions.WrongPhaseException;
import it.unibo.ai.didattica.mulino.actions.WrongPositionException;
import it.unibo.ai.didattica.mulino.client.HeuristicEvaluator;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class HeuristicEvaluatorTest {

	/*
	 *  DA SISTEMARE E' MEGLIO L'EVALUATOR TESTER DI KEVIN
	 * 
	 */
	Phase1Action action;
 	
	HeuristicEvaluator hE;	
	State state;
	
	/*HeuristicEvaluator hE1;	
	State state1;
	
	HeuristicEvaluator hE2;	
	State state2;
	
	HeuristicEvaluator hE3;	
	State state3;
	
	HeuristicEvaluator hE4;	
	State state4;
	
	HeuristicEvaluator hE5;	
	State state5;
	 */
	@Before
	public void setUpState() throws WrongPhaseException, PositionNotEmptyException, NullCheckerException, NoMoreCheckersAvailableException, WrongPositionException, TryingToRemoveOwnCheckerException, TryingToRemoveEmptyCheckerException, NullStateException, TryingToRemoveCheckerInTripleException{
		state = new State();
		action = new Phase1Action();
		action.setPutPosition("d1");
		state=Phase1.applyMove(state, action, Checker.BLACK);
		System.out.println("STATE 0");
		System.out.println(state.toString());
		hE = new HeuristicEvaluator(state, Checker.BLACK);
	}
	
/*
	@Before 
	public void setUpState1() throws WrongPhaseException, PositionNotEmptyException, NullCheckerException, NoMoreCheckersAvailableException, WrongPositionException, TryingToRemoveOwnCheckerException, TryingToRemoveEmptyCheckerException, NullStateException, TryingToRemoveCheckerInTripleException{
		state1 = new State();
		action = new Phase1Action();
		action.setPutPosition("d1");
		state1=Phase1.applyMove(state1, action, Checker.BLACK);
		action = new Phase1Action();
		action.setPutPosition("d2");
		state=Phase1.applyMove(state, action, Checker.BLACK);
		System.out.println("STATE 1");
		System.out.println(state1.toString());
		hE1 = new HeuristicEvaluator(state1, Checker.BLACK);
	}
	

	@Before 
	public void setUpState2() throws WrongPhaseException, PositionNotEmptyException, NullCheckerException, NoMoreCheckersAvailableException, WrongPositionException, TryingToRemoveOwnCheckerException, TryingToRemoveEmptyCheckerException, NullStateException, TryingToRemoveCheckerInTripleException{
		state2 = new State();
		action = new Phase1Action();
		action.setPutPosition("d1");
		state2=Phase1.applyMove(state2, action, Checker.BLACK);
		action = new Phase1Action();
		action.setPutPosition("d2");
		state2=Phase1.applyMove(state2, action, Checker.BLACK);
		action = new Phase1Action();
		action.setPutPosition("a7");
		state2=Phase1.applyMove(state2, action, Checker.WHITE);
		System.out.println("STATE 2");
		System.out.println(state2.toString());
		hE2 = new HeuristicEvaluator(state2, Checker.BLACK);
	}
	
	@Before 
	public void setUpState3() throws WrongPhaseException, PositionNotEmptyException, NullCheckerException, NoMoreCheckersAvailableException, WrongPositionException, TryingToRemoveOwnCheckerException, TryingToRemoveEmptyCheckerException, NullStateException, TryingToRemoveCheckerInTripleException{
		state3 = new State();
		action.setPutPosition("d1");
		state3=Phase1.applyMove(state3, action, Checker.BLACK);
		action.setPutPosition("d2");
		state3=Phase1.applyMove(state3, action, Checker.BLACK);
		action.setPutPosition("a7");
		state3=Phase1.applyMove(state3, action, Checker.WHITE);
		action.setPutPosition("d3");
		action.setRemoveOpponentChecker("a7");
		state=Phase1.applyMove(state, action, Checker.BLACK);
		System.out.println("STATE 3");
		System.out.println(state3.toString());
		hE3 = new HeuristicEvaluator(state3, Checker.BLACK);
	}
	
	@Before 
	public void setUpState4() throws WrongPhaseException, PositionNotEmptyException, NullCheckerException, NoMoreCheckersAvailableException, WrongPositionException, TryingToRemoveOwnCheckerException, TryingToRemoveEmptyCheckerException, NullStateException, TryingToRemoveCheckerInTripleException{
		state4 = new State();
		action.setPutPosition("d1");
		state4=Phase1.applyMove(state4, action, Checker.BLACK);
		action.setPutPosition("d2");
		state4=Phase1.applyMove(state4, action, Checker.BLACK);
		action.setPutPosition("a7");
		state4=Phase1.applyMove(state4, action, Checker.WHITE);
		action.setPutPosition("d3");
		action.setRemoveOpponentChecker("a7");
		state=Phase1.applyMove(state, action, Checker.BLACK);
		action.setPutPosition("g1");
		state=Phase1.applyMove(state, action, Checker.WHITE);
		System.out.println("STATE 4");
		System.out.println(state4.toString());
		hE4 = new HeuristicEvaluator(state4, Checker.BLACK);
	}
	
	@Before 
	public void setUpState5() throws WrongPhaseException, PositionNotEmptyException, NullCheckerException, NoMoreCheckersAvailableException, WrongPositionException, TryingToRemoveOwnCheckerException, TryingToRemoveEmptyCheckerException, NullStateException, TryingToRemoveCheckerInTripleException{
		state5 = new State();
		action.setPutPosition("d1");
		state5=Phase1.applyMove(state5, action, Checker.BLACK);
		action.setPutPosition("d2");
		state5=Phase1.applyMove(state5, action, Checker.BLACK);
		action.setPutPosition("a7");
		state5=Phase1.applyMove(state5, action, Checker.WHITE);
		action.setPutPosition("d3");
		action.setRemoveOpponentChecker("a7");
		state=Phase1.applyMove(state, action, Checker.BLACK);
		action.setPutPosition("g1");
		state=Phase1.applyMove(state, action, Checker.WHITE);
		action.setPutPosition("a1");
		state5=Phase1.applyMove(state5, action, Checker.WHITE);
		System.out.println("STATE 5");
		System.out.println(state5.toString());
		hE5 = new HeuristicEvaluator(state5, Checker.BLACK);
	}
	
	
	*/

	
	
	@Test
	public void numberOfMorrisTest(){
		System.out.println(state.toString());
 		assertEquals("Numero di Morris = 0", true, hE.numberOfMorris() == 0);
 		System.out.println("TRIS NERI-TRIS BIANCHI: "+ hE.numberOfMorris());
 		
 	/*	System.out.println(state1.toString());
 		assertEquals("Numero di Morris = 0", true, hE1.numberOfMorris() == 0);
 		System.out.println("TRIS NERI-TRIS BIANCHI: "+ hE1.numberOfMorris());
 		
 		
 		assertEquals("Numero di Morris = 0", true, hE2.numberOfMorris() == 0);
 		assertEquals("Numero di Morris = 1", true, hE3.numberOfMorris() == 1);
 		assertEquals("Numero di Morris = 0", true, hE4.numberOfMorris() == 0);
 		
*/
	}
	
	 
	
	@Test
	public void numberOfPieces() {
		System.out.println(state.toString());
 		assertEquals("Numero di Pezzi neri meno pezzi bianchi = 0", true, hE.numberOfPieces() == 0);
		System.out.println("PEZZI NERI meno PEZZI BIANCHI: "+hE.numberOfPieces());
	}
	
	@Test
	public void numberOfBlockedOpponentPiecesTest(){
		System.out.println(state.toString());
 		assertEquals("Io sono il giocatore NERO. Numero di Pezzi bloccati BIANCHI - pezzi bloccati NERI = 1", true, hE.numberOfBlockedOpponentPieces() == 0);
		System.out.println("PEZZI NERI meno PEZZI BIANCHI: "+hE.numberOfBlockedOpponentPieces());
	}

}
