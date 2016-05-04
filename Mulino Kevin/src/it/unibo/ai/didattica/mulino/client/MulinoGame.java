package it.unibo.ai.didattica.mulino.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import it.unibo.ai.didattica.mulino.actions.Action;
import it.unibo.ai.didattica.mulino.actions.Phase1;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import aima.core.search.adversarial.Game;
/*
 * S0: The initial state, which specifies how the game is set up at the
 * start.
 * PLAYER(s): Defines which player has the move in a state.
 * ACTIONS(s): Returns the set of legal moves in a state.
 * RESULT(s, a): The transition model, which defines the result of a move.
 * TERMINAL-TEST(s): A terminal test, which is true when the game is over
 * and false TERMINAL STATES otherwise. States where the game has ended are
 * called terminal states.</li>
 * UTILITY(s, p): A utility function, defines the final numeric value for a game 
 * that ends in terminal state s for a player p.
 */
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
	public double getUtility(State state, Checker player) {
		HeuristicEvaluator evaluator=new HeuristicEvaluator(state, player);
		return evaluator.evaluate();
	}

	@Override
	public State getResult(State state, String action) {
		State newState = state.clone();
		String putAction=action.substring(0,2);
		String removeAction;
		switch (getPlayer(state)){
		case BLACK:
			newState.getBoard().replace(putAction, Checker.BLACK);
			newState.setBlackCheckersOnBoard(newState.getBlackCheckersOnBoard()+1);
			newState.setBlackCheckers(newState.getBlackCheckers()-1);
			if(action.length()==4){
				removeAction=action.substring(2,4);
				newState.getBoard().replace(removeAction, Checker.EMPTY);
				newState.setWhiteCheckersOnBoard(newState.getWhiteCheckersOnBoard()-1);
			}
			break;
		case WHITE:
			newState.getBoard().replace(putAction, Checker.WHITE);
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
					if(closedMill(state, s, Checker.BLACK))
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
					if(closedMill(state, s, Checker.WHITE))
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
	public static boolean closedMill(State state, String action,Checker player){
		Double oldNumMorris, newNumMorris=0.0;
		state.setArrayOfMorris();
		State newState=state.clone();
		System.out.println(newState.toString());
		//HashMap<String, Checker> newBoard=newState.getBoard();
		
		oldNumMorris=state.getNumberOfMorris(player);
		System.out.println("OLD NUM MORRIS: "+ oldNumMorris);
		
		newState.getBoard().put(action, player);		
		switch(player){
		case WHITE:
			newState.setWhiteCheckers(newState.getWhiteCheckers()-1);
			newState.setWhiteCheckersOnBoard(newState.getWhiteCheckersOnBoard()+1);
			break;
		case BLACK:
			newState.setBlackCheckers(newState.getBlackCheckers()-1);
			newState.setBlackCheckersOnBoard(newState.getBlackCheckersOnBoard()+1);
			break;
		}
		System.out.println(newState.toString());
		newState.setArrayOfMorris();
			
		newNumMorris=newState.getNumberOfMorris(player);

		System.out.println("NEW NUM MORRIS: "+ newNumMorris);
		
		
		return newNumMorris-oldNumMorris>0;
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
