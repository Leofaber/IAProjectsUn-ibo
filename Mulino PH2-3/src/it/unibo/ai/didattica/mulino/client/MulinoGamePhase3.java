package it.unibo.ai.didattica.mulino.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import aima.core.search.adversarial.Game;

public class MulinoGamePhase3 implements Game<State, String, State.Checker>{
	private State currentState; 
	
	public MulinoGamePhase3(State state) {
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
		return state.getCurrentPlayer();
	}

	@Override
	public List<String> getActions(State state) {
		List<String> result= new ArrayList<String>();
		state.setArrayOfMorris(); 
		switch(getPlayer(state)){
		case WHITE:
			try{
				if(state.getWhiteCheckersOnBoard()>3){
					for (String myChecker : playerCheckers(state, Checker.WHITE))
						for(String adjacentPosition : state.getAdjacentPositions(myChecker))
							if(state.getBoard().get(adjacentPosition)==Checker.EMPTY)
								if(closedMill(state,myChecker+adjacentPosition,Checker.WHITE)){
									for(String oppCheck: playerCheckers(state,Checker.BLACK))
										if(state.isDeletableChecker(Checker.BLACK,oppCheck))
											result.add(myChecker+adjacentPosition+oppCheck);								
								}else
									result.add(myChecker+adjacentPosition);	
					
				}else if(state.getWhiteCheckersOnBoard()==3){
					for (String myChecker : playerCheckers(state, Checker.WHITE))
						for(String position : state.getPositions())
							if(state.getBoard().get(position)==Checker.EMPTY)
								if(closedMill(state,myChecker+position,Checker.WHITE)){
									for(String oppCheck: playerCheckers(state,Checker.BLACK))
										if(state.isDeletableChecker(Checker.BLACK,oppCheck))
											result.add(myChecker+position+oppCheck);								
								}else
									result.add(myChecker+position);
				}else
					System.out.println("Something wrong happened :-( ");
				
				
			}catch(InvalidPositionException ex){
				System.out.println("Eccezione di Leo: "+ex);
			}
			
			
			break;
		case BLACK:
			try{
				if(state.getBlackCheckersOnBoard()>3){
					for (String myChecker : playerCheckers(state, Checker.BLACK))
						for(String adjacentPosition : state.getAdjacentPositions(myChecker))
							if(state.getBoard().get(adjacentPosition)==Checker.EMPTY)
								if(closedMill(state,myChecker+adjacentPosition,Checker.BLACK)){
									for(String oppCheck: playerCheckers(state,Checker.WHITE))
										if(state.isDeletableChecker(Checker.WHITE,oppCheck))
											result.add(myChecker+adjacentPosition+oppCheck);
								}else
									result.add(myChecker+adjacentPosition);
					
				}else if(state.getBlackCheckersOnBoard()==3){
					for (String myChecker : playerCheckers(state, Checker.BLACK))
						for(String position : state.getPositions())
							if(state.getBoard().get(position)==Checker.EMPTY)
								if(closedMill(state,myChecker+position,Checker.BLACK)){
									for(String oppCheck: playerCheckers(state,Checker.WHITE))
										if(state.isDeletableChecker(Checker.WHITE,oppCheck))
											result.add(myChecker+position+oppCheck);								
								}else
									result.add(myChecker+position);
				}else
					System.out.println("Something wrong happened :-( ");
			}catch(InvalidPositionException ex){
				System.out.println("Eccezione di Leo: "+ex);
			}
			
			break;
		case EMPTY:
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public State getResult(State state, String action) {
		String fromAction=action.substring(0,2);
		String toAction=action.substring(2,4);
		String removeAction;
		State newState = state.clone();
		switch(getPlayer(newState)){
		case WHITE:
			newState.getBoard().put(fromAction,Checker.EMPTY);
			newState.getBoard().put(toAction, Checker.WHITE);
			newState.setCurrentPlayer(Checker.BLACK);
			if(action.length()==6){
				removeAction=action.substring(4, 6);
				newState.getBoard().replace(removeAction, Checker.EMPTY);
				newState.setBlackCheckersOnBoard(newState.getBlackCheckersOnBoard()-1);
			}
			break;
		case BLACK:
			newState.getBoard().put(fromAction,Checker.EMPTY);
			newState.getBoard().put(toAction, Checker.BLACK);
			newState.setCurrentPlayer(Checker.WHITE);
			if(action.length()==6){
				removeAction=action.substring(4, 6);
				newState.getBoard().replace(removeAction, Checker.EMPTY);
				newState.setWhiteCheckersOnBoard(newState.getWhiteCheckersOnBoard()-1);
			}			
			break;
		case EMPTY:
			break;
		default:
			break;	
		}
		
		return newState;
	}

	@Override
	public boolean isTerminal(State state) {
		return state.getBlackCheckersOnBoard()<3 || state.getWhiteCheckersOnBoard()<3;
	}

	@Override
	public double getUtility(State state, Checker player) {
		HeuristicEvaluator evaluator=new HeuristicEvaluator(state, player);
		return evaluator.evaluate();
	}
	
	private List<String> playerCheckers(State state, Checker player){
		List<String> result = new ArrayList<String>();
		for(Entry<String,Checker> entry: state.getBoard().entrySet())
			if(entry.getValue()==player)
				result.add(entry.getKey());
		return result;
	}
	public static boolean closedMill(State state, String action,Checker player){
		Double oldNumMorris, newNumMorris=0.0;
		State newState=state.clone();
		String fromAction=action.substring(0,2);
		String toAction=action.substring(2,4);
		newState.setArrayOfMorris();
		oldNumMorris=newState.getNumberOfMorris(player);
		
		if(newState.getBoard().get(fromAction).equals(player)){
			newState.getBoard().put(fromAction,Checker.EMPTY);
			newState.getBoard().put(toAction, player);
		}else
			System.out.println("C'è qualche problema -.-");
		
		
		newState.setArrayOfMorris();			
		newNumMorris=newState.getNumberOfMorris(player);

		return newNumMorris-oldNumMorris>0;
	}
}