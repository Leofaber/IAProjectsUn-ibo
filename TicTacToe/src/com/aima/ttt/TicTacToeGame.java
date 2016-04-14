package com.aima.ttt;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.Game;

public class TicTacToeGame implements Game<TTTState, TTTAction, TTTPlayer>{

	
	@Override
	public TTTPlayer getPlayer(TTTState state) {
		int c = state.getNumberOfSign();
		if( c % 2 == 0){
			return TTTPlayer.getFirstPlayer();	
		}else{
			return TTTPlayer.getSecondPlayer();
		}
		
	}


	@Override
	public List<TTTAction> getActions(TTTState state) {
		List<TTTAction> actionsList = new ArrayList<TTTAction>();
		int i,j = 0;
		for(i=0;i<TTTState.gridRows;i++){
			for(j=0;j<TTTState.gridCols;j++){
				if(state.grid[i][j] == TTTState.EMPTY){
					actionsList.add(new TTTAction(i, j));
					
				}
			}
		}	
		System.out.println("State: "+state);
		System.out.println("actions:"+actionsList);
		return actionsList;
	}

	// SI DEVE CREARE UN NUOVO STATO O SI PUO RESTITUIRE IL VECCHIO MODIFICATO?
	@Override
	public TTTState getResult(TTTState state, TTTAction action) {
		int c = state.getNumberOfSign();
		if( c % 2 == 0){
			state.addSign("X", action.row, action.col);	
		}else{
			state.addSign("O", action.row, action.col);
		}
		
		return state;
	}


	@Override
	public boolean isTerminal(TTTState state) {
		if(state instanceof TTTState){
			TTTState s = (TTTState) state;
			
			if(s.checkForTris("X")){
				System.out.println("Trovato tris di X!!");
				return true;
			}
			if(s.checkForTris("O")){
				System.out.println("Trovato tris di O!!");
				return true;
			}
			
			if(s.checkAllFilled()){
				return true;
			}
			
			return false;
		}
		return false;
	}


	@Override
	public double getUtility(TTTState state, TTTPlayer player) {
		
		if(isTerminal(state)){
			if(state.checkForTris(player.sign)){
				return new Double(1);
			}
			
			if(! state.checkForTris(player.sign) && state.checkAllFilled()){
				return new Double(0);
			}
			
		}
		return 0;
	}


	@Override
	public TTTState getInitialState() {
		return new TTTState();
	}


	@Override
	public TTTPlayer[] getPlayers() {
		return (TTTPlayer[]) TTTPlayer.getPlayers();

	}

}
