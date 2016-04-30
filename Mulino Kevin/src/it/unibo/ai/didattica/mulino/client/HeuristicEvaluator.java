package it.unibo.ai.didattica.mulino.client;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

public class HeuristicEvaluator {
	private State state;
	private Checker player;
	private Phase phase;
	

	
	
	public HeuristicEvaluator(State state, Checker player){
		setState(state); setPlayer(player); setPhase(state.getCurrentPhase());
		
		 
	}

	public double evaluate(){
		double result=0;
		switch(getPhase()){
		// 18 * (1) + 26 * (2) + 1 * (3) + 9 * (4) + 10 * (5) + 7 * (6)
		case FIRST:
			result=18*closedMorris()+26*numberOfMorris()+numberOfBlockedOpponentPieces()+9*numberOfPieces()+10*numberOf2PieceConfigurations()+7*numberOf3PieceConfigurations();
			break;
		//14 * (1) + 43 * (2) + 10 * (3) + 11 * (4) + 8 * (7) + 1086 * (8)
		case SECOND:
			result=14*closedMorris()+43*numberOfMorris()+10*numberOfBlockedOpponentPieces()+11*numberOfPieces()+8*doubleMorris()+1086*winningConfiguration();
			break;
		//16 * (1) + 10 * (5) + 1 * (6) + 1190 * (8)
		case FINAL:
			result=16*closedMorris()+10*numberOf2PieceConfigurations()+numberOf3PieceConfigurations()+1190*winningConfiguration();
			break;
		}
		return result;
	}
	
	/* 1 if a morris was closed in the last move by the player 
	 * (and an opponent’s piece should be grabbed in this move), 
	 * -1 if a morris was closed by the opponent in the last move,
	 * 0 otherwise
	 */
	 private double closedMorris(){
		 //TODO
		return 1;
	}
	
	/* 
	 * Difference between the number of yours and yours opponent’s morrises
	 */
	public double numberOfMorris(){
		
		state.setArrayOfMorrisOrizzontali(Checker.BLACK);
		state.setArrayOfMorrisVerticali(Checker.BLACK);
		int morrisBlack = 0;
		for (int i:state.tempTrisOrizz)
			if (i == 3)
				morrisBlack++;
		for (int i:state.tempTrisVert)
			if (i == 3)
				morrisBlack++;
		
		state.setArrayOfMorrisOrizzontali(Checker.WHITE);
		state.setArrayOfMorrisVerticali(Checker.WHITE);
		int morrisWhite = 0;
		for (int i:state.tempTrisOrizz)
			if (i == 3)
				morrisWhite++;
		for (int i:state.tempTrisVert)
			if (i == 3)
				morrisWhite++;
		
		//importante! In questo momento i due array tempTrisOrizz e Vert
		// 		sono stati riempiti con i valori relativi ai bianchi
		
		//a questo punto a secondo che io sia il bianco o il nero
		//faccio la differenza tra i miei tris e i suoi
		switch (player){
		case WHITE:
 				return morrisWhite - morrisBlack;
		case BLACK:
 				return  morrisBlack - morrisWhite;
		default:
				return 0;
		}
//		double differenza = morrisWhite - morrisBlack;	//ad esempio
//		return differenza;
	}
	
	/* Difference between the number of yours opponent’s and yours blocked pieces
	 * (pieces which don’t have an empty adjacent point)
	 */
	private double numberOfBlockedOpponentPieces(){
		int whiteBlocked = 0;
		int blackBlocked = 0;
			// se tutte sono occupate, il Checker è bloccato.
			// altrimenti il Checker è libero.
		
		// si cicla sulla board
		for (String position : state.getPositions()) {
			
			
			// appena si trova sulla board un Checker WHITE or BLACK (quindi NON EMPTY)
			switch(state.getBoard().get(position)){
				case EMPTY:
					break;	
			
				// si chiama la funzione getAdjacentPositions(String pos) per ottenere le posizioni adiacenti (connesse a quest'ultimo)
				case BLACK:
					try {
						String [] adjacentPositions = state.getAdjacentPositions(position);
						// TODO
						
					} catch (InvalidPositionException e) {
						e.printStackTrace();
					}
				break;
				
				case WHITE:
					
					break;
				default:
					break;
		}
			 
		}
		
		
		//faccio la differenza tra i SUOI bloccati e i MIEI bloccati
		switch (player){
			case WHITE:
	 				return blackBlocked - whiteBlocked;
			case BLACK:
	 				return  whiteBlocked - blackBlocked;
			default:
					return 0;
		}
	}
	
	/*
	 *  Difference between the number of yours and yours opponent’s pieces
	 */
	public double numberOfPieces(){
		switch(player){
		case WHITE:
			return state.getWhiteCheckersOnBoard()-state.getBlackCheckersOnBoard();
		case BLACK:
			return state.getBlackCheckersOnBoard()-state.getWhiteCheckersOnBoard();
		default:
			return 0;
		}
		
	}
	
	/* Difference between the number of yours and yours opponent’s 
	 * 2 piece configurations (A 2-piece configuration is one to which 
	 * adding one more piece would close a morris)
	 */
	private double numberOf2PieceConfigurations(){
		//TODO
		return 1;
	}
	
	/* Difference between the number of yours and yours opponent’s 
	 * 3 piece configurations (A 3-piece configuration is one to which 
	 * a piece can be added in which one of two ways to close a morris)
	 */
	private double numberOf3PieceConfigurations(){
		//TODO
		return 1;
	}
	
	/* Difference between number of yours and yours opponent’s double morrises 
	 * (A double morris is one in which two morrises share a common piece) 
	 */
	public double doubleMorris(){
		state.setArrayOfMorrisOrizzontali(Checker.BLACK);
		state.setArrayOfMorrisVerticali(Checker.BLACK);
		int doubleMorrisBlack = 0;
		for (int i:state.tempTrisOrizz)
			if (i == 2)
				doubleMorrisBlack++;
		for (int i:state.tempTrisVert)
			if (i == 2)
				doubleMorrisBlack++;
		
		state.setArrayOfMorrisOrizzontali(Checker.WHITE);
		state.setArrayOfMorrisVerticali(Checker.WHITE);
		int doubleMorrisWhite = 0;
		for (int i:state.tempTrisOrizz)
			if (i == 2)
				doubleMorrisWhite++;
		for (int i:state.tempTrisVert)
			if (i == 2)
				doubleMorrisWhite++;
		
		//importante! In questo momento i due array tempTrisOrizz e Vert
		// 		sono stati riempiti con i valori relativi ai bianchi
		
		//a questo punto a secondo che io sia il bianco o il nero
		//faccio la differenza tra i miei tris e i suoi
		switch (player){
		case WHITE:
				return doubleMorrisWhite - doubleMorrisBlack;
		case BLACK:
				return  doubleMorrisBlack - doubleMorrisWhite;
		default:
				return 0;
		}
	}
	
	/*
	 * 1 if the state is winning for the player, -1 if losing, 0 otherwise
	 */
	private double winningConfiguration(){
		//TODO
		return 1;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Checker getPlayer() {
		return player;
	}

	public void setPlayer(Checker player) {
		this.player = player;
	}

	public void setPhase(Phase phase) {
		this.phase=phase;	
	}
	
	public Phase getPhase(){
		return phase;
	}

	
}
