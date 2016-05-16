package it.unibo.ai.didattica.mulino.iothincook;

import java.util.List;

import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.iothincook.MulinoState;
 
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;

public class MulinoIterativeDeepeningAlphaBetaSearch extends
		IterativeDeepeningAlphaBetaSearch<MulinoState,String,Checker> implements AdversarialSearch<MulinoState, String>{
	 

	public MulinoIterativeDeepeningAlphaBetaSearch(
			Game<MulinoState, String, Checker> game, double utilMin, double utilMax,
			int time) {
		super(game, utilMin, utilMax, time);
		if(game instanceof MulinoGame){
			super.setMaxDepth(7);
			super.setInitialDepth(5);
		}else if(game instanceof MulinoGamePhase2){
			super.setMaxDepth(9);
			super.setInitialDepth(7);
		}else if(game instanceof MulinoGamePhase3){
			super.setMaxDepth(8);
			super.setInitialDepth(6);
		}
	}
	 
	@Override
	public String makeDecision(MulinoState state) {
		return super.makeDecision(game.getInitialState());
	}
	
	@Override
	public List<String> orderActions(MulinoState state, List<String> actions,
			Checker player, int depth) {
		return actions;
	}
	
	
}


