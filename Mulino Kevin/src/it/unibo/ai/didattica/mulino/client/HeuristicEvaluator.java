package it.unibo.ai.didattica.mulino.client;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

public class HeuristicEvaluator {
	private State state;
	private Checker player;
	private Phase phase;
	
	public int[] tempTrisOrizz = new int[8];
	public int[] tempTrisVert = new int[8];
	
	
	public HeuristicEvaluator(State state, Checker player, Phase phase){
		setState(state); setPlayer(player); setPhase(phase);
		
		//azzero tutta l'array
		for (int t: tempTrisOrizz)
			t = 0;
		for (int t: tempTrisVert)
			t = 0;
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
	private double numberOfMorris(){
		morrisOrizzontali(Checker.BLACK);
		int morrisBlack = 0;
		for (int i:tempTrisOrizz)
			if (i == 3)
				morrisBlack++;
		for (int i:tempTrisVert)
			if (i == 3)
				morrisBlack++;
		
		morrisOrizzontali(Checker.WHITE);
		int morrisWhite = 0;
		for (int i:tempTrisOrizz)
			if (i == 3)
				morrisWhite++;
		for (int i:tempTrisVert)
			if (i == 3)
				morrisWhite++;
		
		//importante! In questo momento i due array tempTrisOrizz e Vert
		// 		sono stati riempiti con i valori relativi ai bianchi
		
		//a questo punto a secondo che io sia il bianco o il nero
		//faccio la differenza tra i miei tris e i suoi
		
		double differenza = morrisWhite - morrisBlack;	//ad esempio
		return differenza;
	}
	
	/* Difference between the number of yours opponent’s and yours blocked pieces
	 * (pieces which don’t have an empty adjacent point)
	 */
	private double numberOfBlockedOpponentPieces(){
		//TODO
		return 1;
	}
	
	/*
	 *  Difference between the number of yours and yours opponent’s pieces
	 */
	private double numberOfPieces(){
		//TODO
		return 1;
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
	private double doubleMorris(){
		//TODO
		return 1;
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

	//le due funzioni sono senza dubbio migliorabili, ma almeno ne abbiamo un abbozzo
	
	public void morrisOrizzontali(Checker c) {
		
		for (String s : state.positions) {
			char first = s.charAt(0);
			char second = s.charAt(1);
			//cerco tris in orizzontale
			switch (first) {
			// se mi trovo nella riga 'a', vado a controllare se nella mia casella
			// della board il valore corrisponde a c. se è così allora incremento
			// il mio array.
				case 'a' : 	if (state.getBoard().get(s) == c)
								tempTrisOrizz[0]++;
							break;
				case 'b':	if (state.getBoard().get(s) == c)
								tempTrisOrizz[1]++;
							break;
				case 'c':	if (state.getBoard().get(s) == c)
								tempTrisOrizz[2]++;
							break;
				case 'd':	if (second <4) {
								if (state.getBoard().get(s) == c)
									tempTrisOrizz[3]++;
							} else {
								if (state.getBoard().get(s) == c)
									tempTrisOrizz[4]++;
							}
							break;
				case 'e':	if (state.getBoard().get(s) == c)
								tempTrisOrizz[5]++;
							break;
				case 'f':	if (state.getBoard().get(s) == c)
								tempTrisOrizz[6]++;
							break;
				case 'g':	if (state.getBoard().get(s) == c)
								tempTrisOrizz[7]++;
							break;
			}
		}
	}
	
	public void morrisVerticali(Checker c) {
		
		for (String s : state.positions) {
			char first = s.charAt(0);	//carattere 0 di s (ad esempio 'a')
			char second = s.charAt(1);	//carattere 1 di s (ad esempio 1)
			//cerco tris in verticale
			switch (second) {
				// se mi trovo nella colonna 1, vado a controllare se nella mia casella
				// della board il valore corrisponde a c. se è così allora incremento
				// il mio array.
				case 1 : 	if (state.getBoard().get(s) == c)
								tempTrisOrizz[0]++;
							break;
				case 2 :	if (state.getBoard().get(s) == c)
								tempTrisOrizz[1]++;
							break;
				case 3: 	if (state.getBoard().get(s) == c)
								tempTrisOrizz[2]++;
							break;
				case 4: 	if (second < 'd') {
								if (state.getBoard().get(s) == c)
									tempTrisOrizz[3]++;
							} else {
								if (state.getBoard().get(s) == c)
									tempTrisOrizz[4]++;
							}
							break;
				case 5: 	if (state.getBoard().get(s) == c)
								tempTrisOrizz[5]++;
							break;
				case 6: 	if (state.getBoard().get(s) == c)
								tempTrisOrizz[6]++;
							break;
				case 7: 	if (state.getBoard().get(s) == c)
								tempTrisOrizz[7]++;
							break;
			}
		}
	}

}