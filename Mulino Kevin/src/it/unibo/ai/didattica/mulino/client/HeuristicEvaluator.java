package it.unibo.ai.didattica.mulino.client;

import java.util.HashMap;

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
		
		
		
		/* * * * * * * * * * * * * * * * *
		 * 	 CALA'S POWER FUNCTION CALL  * 
		 * * * * * * * * * * * * * * * * */
		state.setArrayOfMorris();
		/* * * * * * * * * * * * * * * * * *
		 *  END CALA'S POWER FUNCTION CALL *
		 * * * * * * * * * * * * * * * * * */
		
		
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
		
		//state.setArrayOfMorrisOrizzontali(Checker.BLACK);
		//state.setArrayOfMorrisVerticali(Checker.BLACK);
		int[] morrisArrayBlackOrizz = state.getMorrisArray("BLACK","ROW");
		int[] morrisArrayBlackVert = state.getMorrisArray("BLACK","COLUMN");
		
	
		int morrisBlack = 0;
		for (int i:morrisArrayBlackOrizz)
			if (i == 3)
				morrisBlack++;
		for (int i:morrisArrayBlackVert)
			if (i == 3)
				morrisBlack++;
		
		//state.setArrayOfMorrisOrizzontali(Checker.WHITE);
	//	state.setArrayOfMorrisVerticali(Checker.WHITE);
		System.out.println("Numero di Morris Black: "+morrisBlack);
		
		
		 int[] morrisArrayWhiteOrizz = state.getMorrisArray("WHITE","ROW");
		 int[] morrisArrayWhiteVert = state.getMorrisArray("WHITE","COLUMN");
			
			
			
		int morrisWhite = 0;
		for (int i:morrisArrayWhiteOrizz)
			if (i == 3)
				morrisWhite++;
		for (int i:morrisArrayWhiteVert)
			if (i == 3)
				morrisWhite++;
		
		
		System.out.println("Numero di Morris White: "+morrisWhite);

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
 
	}
	
	/* Difference between the number of yours opponent’s and yours blocked pieces
	 * (pieces which don’t have an empty adjacent point)
	 */
	public double numberOfBlockedOpponentPieces(){
		int whiteBlocked = 0;
		int blackBlocked = 0;
		String [] adjacentPositions;
		HashMap<String, Checker> board = state.getBoard(); // cosi si chiama una volta sola.
		
		// si cicla sulla board
		for (String position : state.getPositions()) {
			
			// si prende il Checker di ogni posizione così ciclata
			Checker checker = board.get(position);
			
			// appena si trova sulla board un Checker WHITE or BLACK (quindi NON EMPTY)
			if(checker != Checker.EMPTY){
			
				
				try {
					
					// si prendono le posizione adiacenti
					adjacentPositions = state.getAdjacentPositions(position);
					
					// si cerca se tra queste c'è un Checer EMPTY
					boolean existEmpty = false;						
					for(String adjPos : adjacentPositions){
						if(board.get(adjPos) == Checker.EMPTY){
							existEmpty = true;
						}
					}
					
					
					// se non ci sono Checker EMPTY tra le posizioni adiacenti, il Checker è bloccato.
					if(existEmpty==false && checker == Checker.BLACK){
						blackBlocked++;
					}
						
					if(existEmpty==false && checker == Checker.WHITE){
						whiteBlocked++;
					}

						
				} catch (InvalidPositionException e) {
					e.printStackTrace();
				}
				
			
			}
			 
		}
		System.out.println("Black bloccati: "+blackBlocked+" White bloccati: "+whiteBlocked);

		
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
	public double numberOf2PieceConfigurations(){
		
		
	//	state.setArrayOfMorrisOrizzontali(Checker.BLACK);
    //    state.setArrayOfMorrisVerticali(Checker.BLACK);
		
		int[] morrisArrayBlackOrizz = state.getMorrisArray("BLACK","ROW");
		int[] morrisArrayBlackVert = state.getMorrisArray("BLACK","COLUMN");
		int[] morrisArrayEmptyOrizz = state.getMorrisArray("EMPTY", "ROW");
		int[] morrisArrayEmptyVert = state.getMorrisArray("EMPTY", "COLUMN");
		
        int twoPieceMorrisBlack = 0;
        
		// si guarda tempOrizz e tempVert se hanno indici per i quali il valore è 2.
        for (int i=0;i<morrisArrayBlackOrizz.length;i++){
            if (morrisArrayBlackOrizz[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyOrizz[i] == 1)
            		twoPieceMorrisBlack++;
            }
        }
        	
        	
        for (int i=0;i<morrisArrayBlackVert.length;i++){
//        	System.out.print("checkerVert: "+morrisArrayBlackVert[i]+ " all'indice: "+i);
//    		System.out.println(" checkerVertEmpty: "+morrisArrayEmptyVert[i]+ " all'indice: "+i);
            if (morrisArrayBlackVert[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyVert[i] == 1){
            		twoPieceMorrisBlack++;
            		
            	}
            }
        }
                
   //     state.setArrayOfMorrisOrizzontali(Checker.WHITE);
   //     state.setArrayOfMorrisVerticali(Checker.WHITE);
        
        int[] morrisArrayWhiteOrizz = state.getMorrisArray("WHITE","ROW");
		int[] morrisArrayWhiteVert = state.getMorrisArray("WHITE","COLUMN");
		
		
        int twoPieceMorrisWhite = 0;
        for (int i=0;i<morrisArrayWhiteOrizz.length;i++){
            if (morrisArrayWhiteOrizz[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyOrizz[i] == 1)
            		twoPieceMorrisWhite++;
            }
        }
        for (int i=0;i<morrisArrayWhiteVert.length;i++){
        	if (morrisArrayWhiteVert[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyVert[i] == 1)
            		twoPieceMorrisWhite++;
            }
        }
       
        //importante! In questo momento i due array tempTrisOrizz e Vert
        //      sono stati riempiti con i valori relativi ai bianchi
       
        //a questo punto a secondo che io sia il bianco o il nero
        //faccio la differenza tra i miei tris e i suoi
        System.out.println("2PieceBlack: "+twoPieceMorrisBlack);
        System.out.println("2PieceWhite: "+twoPieceMorrisWhite);

        switch (player){
        case WHITE:
                return twoPieceMorrisWhite - twoPieceMorrisBlack;
        case BLACK:
                return  twoPieceMorrisBlack - twoPieceMorrisWhite;
        default:
                return 0;
        }
		
	}
	
	/* Difference between the number of yours and yours opponent’s 
	 * 3 piece configurations (A 3-piece configuration is one to which 
	 * a piece can be added in which one of two ways to close a morris)
	 */
	/*
	 * Per trovare un 3piece configuration bisogna cercare una riga che si interseca con una colonna (condividendo un checker) e 
	 * sia nella riga e nella colonna ci dovranno essere 2 checker e i restanti due posti dovranno essere vuoti.
	 * 
	 * Nota: se la riga e la colonna condividono un checker EMPTY allora la possibilità non è la 3 piece configuration ma il 
	 * doppio morris. (non contemplato)
	 */
	private double numberOf3PieceConfigurations(){
		//TODO
		return 1;
	}
	
	
	
	
	
	
	
	/* Difference between number of yours and yours opponent’s double morrises 
	 * (A double morris is one in which two morrises share a common piece) 
	 */
	
	/*
	 * Ogni double morris è condiviso tra una posizione d'incrontro tra una riga e una colonna.
	 * Ogni double morris è condiviso per una sola posizione.
	 * Usa il metodo getIndexOfLinkedColumnsToRow(int row).
	 */
	public double doubleMorris(){
		int doubleMorrisBlack = 0;
		int doubleMorrisWhite = 0;
		
		// si costruiscono gli arrayOfMorris. 
	//	state.setArrayOfMorrisOrizzontali(Checker.BLACK);
//		state.setArrayOfMorrisVerticali(Checker.BLACK);
		
		int[] morrisArrayBlackOrizz = state.getMorrisArray("BLACK","ROW");
		int[] morrisArrayBlackVert = state.getMorrisArray("BLACK","COLUMN");
		
		// si ciclano
		for(int i=0; i<morrisArrayBlackOrizz.length;i++){
			int numOfCheckerInRow = morrisArrayBlackOrizz[i];

			// se una riga contiene un morris
			if(numOfCheckerInRow == 3){
 				//si prendono le colonne connesse a quella riga (i parte da 0, le righe partono da 1)
				int [] linkedColumns = state.getIndexOfLinkedColumnsToRow(i+1);
				
				// si cercano uno o più morris in quelle colonne
				for(int c=0;c<linkedColumns.length;c++){
										// si sottrae uno, perchè le colonne vanno da 0 a 7.
					if(morrisArrayBlackVert[linkedColumns[c]-1]==3){
						doubleMorrisBlack++;
					}
				}
				
				
				
			}
		}
		
		// si costruiscono gli arrayOfMorris. 
	//			state.setArrayOfMorrisOrizzontali(Checker.WHITE);
	//			state.setArrayOfMorrisVerticali(Checker.WHITE);
		int[] morrisArrayWhiteOrizz = state.getMorrisArray("WHITE","ROW");
		int[] morrisArrayWhiteVert = state.getMorrisArray("WHITE","COLUMN");
		
		// si ciclano
				for(int i=0; i<morrisArrayWhiteOrizz.length;i++){
					int numOfCheckerInRow = morrisArrayWhiteOrizz[i];
 
					// se una riga contiene un morris
					if(numOfCheckerInRow == 3){
		 				//si prendono le colonne connesse a quella riga (i parte da 0, le righe partono da 1)
						int [] linkedColumns = state.getIndexOfLinkedColumnsToRow(i+1);
						
						// si cercano uno o più morris in quelle colonne
						for(int c=0;c<linkedColumns.length;c++){
							if(morrisArrayWhiteVert[linkedColumns[c]-1]==3){
								doubleMorrisWhite++;
							}
						}
						
						
						
					}
				}
		System.out.println("DubleMorrisWHite: "+doubleMorrisWhite+" DoubleMorrusBalck: "+doubleMorrisBlack);
				
 	
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
