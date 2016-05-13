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
		}else if(game instanceof MulinoGamePhase2){
			super.setMaxDepth(10);
		}else if(game instanceof MulinoGamePhase3){
			super.setMaxDepth(7);
		}
		super.setLogEnabled(true);
	}
	 
	@Override
	public String makeDecision(MulinoState state) {
		return super.makeDecision(game.getInitialState());
	}
	
	@Override
	public List<String> orderActions(MulinoState state, List<String> actions,
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


