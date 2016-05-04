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

	public MulinoIterativeDeepeningAlphaBetaSearch(
			Game<State, String, Checker> game, double utilMin, double utilMax,
			int time) {
		super(game, utilMin, utilMax, time);
		super.setLogEnabled(true);
	}
	
	@Override
	public String makeDecision(State state) {
		return super.makeDecision(game.getInitialState());
	}
	
	@Override
	public List<String> orderActions(State state, List<String> actions,
			Checker player, int depth) {
		
		actions.sort(new ActionComparator(state,player));
		return actions;
	}
	
	private class ActionComparator implements Comparator<String>{
		private State currentState;
		private Checker player;
		
		public ActionComparator(State state, Checker player) {
			setCurrentState(state); setPlayer(player);
		}

		@Override
		public int compare(String action1, String action2) {			
			State s1=getCurrentState().clone();
			State s2=getCurrentState().clone();
			HeuristicEvaluator eval1;
			HeuristicEvaluator eval2;
			double val1=0.0 ,val2=0.0;
			
			val1 += MulinoGame.closedMill(s1,action1,getPlayer()) ? 18 : 0;
			val2 += MulinoGame.closedMill(s2,action2,getPlayer()) ? 18 : 0;
			
			s1.getBoard().put(action1, getPlayer());
			s2.getBoard().put(action2, getPlayer());
			eval1=new HeuristicEvaluator(s1, getPlayer());
			eval2=new HeuristicEvaluator(s2, getPlayer());
			val1+=eval1.evaluate();
			val2+=eval2.evaluate();
			
			if (val1>val2)
				return 1;
			else if(val1==val2)
				return 0;
			else
				return -1;	
		}

		public Checker getPlayer() {
			return player;
		}

		public void setPlayer(Checker player) {
			this.player = player;
		}

		public State getCurrentState() {
			return currentState;
		}

		public void setCurrentState(State currentState) {
			this.currentState = currentState;
		}
		
	}
}


