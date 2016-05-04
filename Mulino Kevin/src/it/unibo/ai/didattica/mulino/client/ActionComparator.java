package it.unibo.ai.didattica.mulino.client;

import java.util.Comparator;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class ActionComparator implements Comparator<String>{
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