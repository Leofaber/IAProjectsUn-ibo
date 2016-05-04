package it.unibo.ai.didattica.mulino.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.unibo.ai.didattica.mulino.actions.Phase1;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.client.ActionComparator;
import it.unibo.ai.didattica.mulino.client.MulinoGame;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class ActionComparatorTest {

	ActionComparator aC;
	State state;
	Phase1Action action;
	@Before
	public void setUp(){
		state = new State();
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
			System.out.println("ACTION COMPARATOR\n"+ state.toString());
			
			
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		aC = new ActionComparator(state, Checker.BLACK);
	}


	
	@Test
	public void compareTest() {
		assertEquals("LOL",true,aC.compare("d7","") == 1);
	}
}
