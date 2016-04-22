package it.unibo.ai.didattica.mulino.client;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

public class HeuristicEvaluator {
	private State state;
	private Checker player;
	private Phase phase;
	
	public HeuristicEvaluator(State state, Checker player, Phase phase){
		setState(state); setPlayer(player); setPhase(phase);
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
		return 0;
	}
	
	/* 
	 * Difference between the number of yours and yours opponent’s morrises
	 */
	private double numberOfMorris(){
		//TODO
		return 0;
	}
	
	/* Difference between the number of yours opponent’s and yours blocked pieces
	 * (pieces which don’t have an empty adjacent point)
	 */
	private double numberOfBlockedOpponentPieces(){
		//TODO
		return 0;
	}
	
	/*
	 *  Difference between the number of yours and yours opponent’s pieces
	 */
	private double numberOfPieces(){
		//TODO
		return 0;
	}
	
	/* Difference between the number of yours and yours opponent’s 
	 * 2 piece configurations (A 2-piece configuration is one to which 
	 * adding one more piece would close a morris)
	 */
	private double numberOf2PieceConfigurations(){
		//TODO
		return 0;
	}
	
	/* Difference between the number of yours and yours opponent’s 
	 * 3 piece configurations (A 3-piece configuration is one to which 
	 * a piece can be added in which one of two ways to close a morris)
	 */
	private double numberOf3PieceConfigurations(){
		//TODO
		return 0;
	}
	
	/* Difference between number of yours and yours opponent’s double morrises 
	 * (A double morris is one in which two morrises share a common piece) 
	 */
	private double doubleMorris(){
		//TODO
		return 0;
	}
	
	/*
	 * 1 if the state is winning for the player, -1 if losing, 0 otherwise
	 */
	private double winningConfiguration(){
		//TODO
		return 0;
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
