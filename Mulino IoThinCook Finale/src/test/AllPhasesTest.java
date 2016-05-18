package test;

import org.junit.Before;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;
import it.unibo.ai.didattica.mulino.iothincook.MulinoGameAllPhases;
import it.unibo.ai.didattica.mulino.iothincook.MulinoState;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;


/*
 *  		 "W"--------------------"W"------------------"W" 
		      |                      |                    |
		      |    					 |					  |
		 	  |		"b6"------------"d6"------------f6"   |
			  |      |               |              !     |
 			  |		 |		         |              |     |  
			  |		 |	  "c5"------"d5"-----"e5"   |     |
			  |		 |		|                  |    |     |
		      | 	 |		|                  |    |     |
			 "a4"---"b4"--"c4"               "e4"--"f4"--"g4"
			  |      |		|                  |    |     |
			  |		 |		|				   |    |     |
			  |		 |	  "c3"----- "d3"-----"e3"   |     |
			  |		 |				 |				|     |
			  |		 |	 			 |	 			|     |
			  |		"b2"------------"d2"-----------"f2"   |
			  |                      |                    |
			  |						 |					  |
			"a1"--------------------"d1"-----------------"g1"
 * 
 */

public class AllPhasesTest {

	public MulinoState normalState = new MulinoState();
	public MulinoGameAllPhases AL;
	
	
	@Before
	public void setUp(){

		
		normalState.getBoard().put("b2", Checker.WHITE);
		normalState.getBoard().put("b6", Checker.WHITE);
		normalState.getBoard().put("e4", Checker.WHITE);
		normalState.getBoard().put("e5", Checker.WHITE);
		normalState.getBoard().put("f2", Checker.WHITE);
		normalState.getBoard().put("f4", Checker.WHITE);
		normalState.getBoard().put("f6", Checker.WHITE);
		
		normalState.getBoard().put("b4", Checker.BLACK);
		normalState.getBoard().put("d1", Checker.BLACK);
		normalState.getBoard().put("d2", Checker.BLACK);
		normalState.getBoard().put("d3", Checker.BLACK);
		normalState.getBoard().put("d5", Checker.BLACK);
		normalState.getBoard().put("d6", Checker.BLACK);
		normalState.getBoard().put("e3", Checker.BLACK);
		normalState.setCurrentPhase(Phase.SECOND);
				
		normalState.setArrayOfMorris();
		System.out.println(normalState.toString());
		
		AL = new MulinoGameAllPhases(normalState);
	}
	
	@Test
	public void testClosedMill() {
		System.out.println(AL.getActions(normalState).toString());
	 	assertEquals("Tris orizzontale di d5-d6-d7", true, AL.getActions(normalState).size() == 0);
    }
}