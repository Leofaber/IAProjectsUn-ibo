package it.unibo.ai.didattica.mulino.client;

import java.util.Comparator;
import java.util.List;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.test.ClosedMillTester;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;

public class MulinoIterativeDeepeningAlphaBetaSearch extends
		IterativeDeepeningAlphaBetaSearch<State,String,Checker> implements AdversarialSearch<State, String>{
	
	List<String> orderedActions;
	int lastOrdering;

	public MulinoIterativeDeepeningAlphaBetaSearch(
			Game<State, String, Checker> game, double utilMin, double utilMax,
			int time) {
		super(game, utilMin, utilMax, time);
		lastOrdering=4;
		super.setLogEnabled(true);
	}
	 
	@Override
	public String makeDecision(State state) {
		return super.makeDecision(game.getInitialState());
	}
	
	@Override
	public List<String> orderActions(State state, List<String> actions,
			Checker player, int depth) {
		//System.out.println("Numero di azioni:"+actions.size());
		//actions.sort(new ActionComparator(state,player));
//		if(depth>lastOrdering){
//			System.out.println("START SORTING - PLAYER "+player.toString());
//			actions.sort(new ActionComparator(state, player));
//			lastOrdering=depth;
//			System.out.println("ARRAY ORDINATO - DEPTH "+lastOrdering+": "+actions.toString());
//		}
		return actions;
	}
	
	
}


