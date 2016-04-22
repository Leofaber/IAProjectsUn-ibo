package it.unibo.ai.didattica.mulino.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import aima.core.search.adversarial.Game;

public class MulinoGame implements Game<State, String, State.Checker> {
	private State currentState;
	
	public MulinoGame(State state) {
		currentState=state;
	}
	
	@Override
	public State getInitialState() {
		return currentState.clone();
	}

	@Override
	public Checker[] getPlayers() {
		Checker c[] ={Checker.WHITE, Checker.BLACK};
		return c;
	}

	@Override
	public Checker getPlayer(State state) {		
		return state.getBlackCheckers()>state.getWhiteCheckers() ? Checker.BLACK : Checker.WHITE;
	}

	@Override
	public boolean isTerminal(State state) {
		switch (state.getCurrentPhase()){
		case FIRST: 
			return state.getBlackCheckers()==0 && state.getWhiteCheckers()==0;
		case SECOND:
			break;
		case FINAL:
			break;
		}
		return false;
		
	}

	@Override
	//USE HEURISTIC EVALUATOR HERE
	public double getUtility(State state, Checker player) {
		HeuristicEvaluator evaluator=new HeuristicEvaluator(state, player, state.getCurrentPhase());
		return evaluator.evaluate();
	}

	@Override
	public State getResult(State state, String action) {
		State newState = state.clone();
		String replaceAction=action.substring(0,2);
		String removeAction;
		switch (getPlayer(state)){
		case BLACK:
			newState.getBoard().replace(replaceAction, Checker.BLACK);
			newState.setBlackCheckersOnBoard(newState.getBlackCheckersOnBoard()+1);
			newState.setBlackCheckers(newState.getBlackCheckers()-1);
			if(action.length()==4){
				removeAction=action.substring(2,4);
				newState.getBoard().replace(removeAction, Checker.EMPTY);
				newState.setWhiteCheckersOnBoard(newState.getWhiteCheckersOnBoard()-1);
			}
			break;
		case WHITE:
			newState.getBoard().replace(replaceAction, Checker.WHITE);
			newState.setWhiteCheckersOnBoard(newState.getWhiteCheckersOnBoard()+1);
			newState.setWhiteCheckers(newState.getWhiteCheckers()-1);
			if(action.length()==4){
				removeAction=action.substring(2,4);
				newState.getBoard().replace(removeAction, Checker.EMPTY);
				newState.setBlackCheckersOnBoard(newState.getBlackCheckersOnBoard()-1);
			}
			break;
		}		
		return newState;
	}

	@Override
	public List<String> getActions(State state) {
		List<String> result= new ArrayList<String>();
		
		switch (getPlayer(state)){
		case BLACK:
			for (String s : state.getPositions())
				if(state.getBoard().get(s)==Checker.EMPTY){
					/* Se non faccio tris, aggiungo alle possibili azioni, tutte le posizioni libere. 
					 * Se invece con questa mossa faccio tris, aggiungo s + la posizione
					 * di una pedina avversaria da eliminare[?]. 
					 */
					if(madeNewMill(state, s))
						for (String oppCheck : opponentCheckers(state, Checker.WHITE))
							result.add(s+oppCheck);
					else
						result.add(s);
				}
		case WHITE:
			for (String s : state.getPositions())
				if(state.getBoard().get(s)==Checker.EMPTY){
					/* Se non faccio tris, aggiungo alle possibili azioni, tutte le posizioni libere. 
					 * Se invece con questa mossa faccio tris, aggiungo s + la posizione
					 * di una pedina avversaria da eliminare[?].
					 * LO METTO QUI O NEI GETRESULT?
					 */
					if(madeNewMill(state, s))
						for (String oppCheck : opponentCheckers(state, Checker.BLACK))
							result.add(s+oppCheck);
					else
						result.add(s);
				}
		default:
			break;
		}
		return result;
	}	
	
	//Verifica se l'azione action genera un nuovo tris
	private boolean madeNewMill(State state, String action){
		
		return true;
	}
	
	//genera la lista dei checker del giocatore player
	private List<String> opponentCheckers(State state, Checker player){
		List<String> result = new ArrayList<String>();
		for(Entry<String,Checker> entry: state.getBoard().entrySet())
			if(entry.getValue()==player)
				result.add(entry.getKey());
		return result;
	}
}
