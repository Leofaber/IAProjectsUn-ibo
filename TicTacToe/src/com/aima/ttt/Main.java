package com.aima.ttt;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.MinimaxSearch;

public class Main {

	public static void main(String[] args) {
	
		/*
		TTTState t = new TTTState();
		t.printState();
		t.addSign("X", 0, 2);
		t.addSign("X", 1, 1);
		t.addSign("X", 2, 0);
		t.printState();
		System.out.println("Terminal Node? " +t.isTerminal(t));
		System.out.println(t.getPlayers());
		*/
		
		TicTacToeGame game = new TicTacToeGame();
		
		TTTState currState = game.getInitialState();
		
		AdversarialSearch<TTTState, TTTAction> search = MinimaxSearch.createFor(game);
		
		while (!(game.isTerminal(currState))) {
			
			System.out.println(game.getPlayer(currState) + "  playing ... ");
			
			
			TTTAction action = search.makeDecision(currState);
			
			
			
			currState = game.getResult(currState, action);
			//System.out.println(currState);
		}
		
		System.out.println("MINI MAX DEMO done");
 	}

}
