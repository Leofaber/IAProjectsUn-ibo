package it.unibo.ai.didattica.mulino.iothincook;

import java.util.HashMap;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;
import it.unibo.ai.didattica.mulino.iothincook.MulinoState;
 

public class HeuristicEvaluator {
	private MulinoState state;
	private Checker player;
	private Phase phase;
	

	
	
	public HeuristicEvaluator(MulinoState state, Checker player){
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
//			result=18*closedMorris()+26*numberOfMorris()+numberOfBlockedOpponentPieces()+9*numberOfPieces()+10*numberOf2PieceConfigurations()+7*numberOf3PieceConfigurations();
			result=26*numberOfMorris()+numberOfBlockedOpponentPieces()+9*numberOfPieces()+20*numberOf2PieceConfigurations()+7*numberOf3PieceConfigurations()+20*blockMill();
			break;
		//14 * (1) + 43 * (2) + 10 * (3) + 11 * (4) + 8 * (7) + 1086 * (8)
		case SECOND:
//			result=14*closedMorris()+43*numberOfMorris()+10*numberOfBlockedOpponentPieces()+11*numberOfPieces()+8*doubleMorris()+1086*winningConfiguration();
			result=60*numberOfMorris()+10*numberOfBlockedOpponentPieces()+11*numberOfPieces()+8*doubleMorris()+20*blockMill()+1086*winningConfiguration();
			break;
		//16 * (1) + 10 * (5) + 1 * (6) + 1190 * (8)
		case FINAL:
//			result=16*closedMorris()+10*numberOf2PieceConfigurations()+numberOf3PieceConfigurations()+1190*winningConfiguration();
			result=10*numberOf2PieceConfigurations()+numberOf3PieceConfigurations()+1190*winningConfiguration();
			break;
		}
		return result;
	}
	
	/* 1 if a morris was closed in the last move by the player 
	 * (and an opponent’s piece should be grabbed in this move), 
	 * -1 if a morris was closed by the opponent in the last move,
	 * 0 otherwise
	 */
	/* private double closedMorris(){
		 //TODO
		return 1;
	}*/
	
	/* 
	 * Difference between the number of yours and yours opponent’s morrises
	 */
	public double numberOfMorris(){
		
			double whiteMorris = state.getNumberOfMorris(Checker.WHITE);
			double blackMorris = state.getNumberOfMorris(Checker.BLACK);
			
	//		System.out.println("[HeuristicEvaluator] White Morris Number = "+whiteMorris+" || Black Morris Number = "+blackMorris);
			
		switch (player){
			case WHITE:
	 				return whiteMorris - blackMorris;
			case BLACK:
	 				return  blackMorris - whiteMorris;
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
		//System.out.println("[HeuristicEvaluator] Blocked Black Pieces Number = "+blackBlocked+" || Blocked White Pieces Number = "+whiteBlocked);

		
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
		
		int numWhite = state.getWhiteCheckersOnBoard();
		int numBlack = state.getBlackCheckersOnBoard();
		
	//	System.out.println("[HeuristicEvaluator] Black Number = "+numBlack+" || White Number = "+numWhite);
		
		switch(player){
			case WHITE:
				return numWhite - numBlack;
			case BLACK:
				return numBlack - numWhite;
			default:
				return 0;
		}
		
	}
	
	/* Difference between the number of yours and yours opponent’s 
	 * 2 piece configurations (A 2-piece configuration is one to which 
	 * adding one more piece would close a morris)
	 */
	public double numberOf2PieceConfigurations(){
	
		int[] morrisArrayBlackOrizz = state.getMorrisArray("BLACK","ROW");
		int[] morrisArrayBlackVert = state.getMorrisArray("BLACK","COLUMN");
		int[] morrisArrayEmptyOrizz = state.getMorrisArray("EMPTY", "ROW");
		int[] morrisArrayEmptyVert = state.getMorrisArray("EMPTY", "COLUMN");
		int[] morrisArrayWhiteOrizz = state.getMorrisArray("WHITE","ROW");
		int[] morrisArrayWhiteVert = state.getMorrisArray("WHITE","COLUMN");
		
        int twoPieceMorrisBlack = 0;
        int twoPieceMorrisWhite = 0;
        
		// si guarda tempOrizz e tempVert se hanno indici per i quali il valore è 2.
        for (int i=0;i<morrisArrayBlackOrizz.length;i++){
            if (morrisArrayBlackOrizz[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyOrizz[i] == 1)
            		twoPieceMorrisBlack++;
            }
            if (morrisArrayBlackVert[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyVert[i] == 1){
            		twoPieceMorrisBlack++;
            		
            	}
            }
        }
        	
        
		
        for (int i=0;i<morrisArrayWhiteOrizz.length;i++){
            if (morrisArrayWhiteOrizz[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyOrizz[i] == 1)
            		twoPieceMorrisWhite++;
            }
            if (morrisArrayWhiteVert[i] == 2){
        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
            	if (morrisArrayEmptyVert[i] == 1)
            		twoPieceMorrisWhite++;
            }
        }

        //a questo punto a secondo che io sia il bianco o il nero
        //faccio la differenza tra i miei tris e i suoi
    //    System.out.println("[HeuristicEvaluator] 2 Pieces Conf Black Number = "+twoPieceMorrisBlack+" || 2 Pieces Conf White Number = "+twoPieceMorrisWhite);
 
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
	public double numberOf3PieceConfigurations(){
		
		int[] morrisArrayBlackOrizz = state.getMorrisArray("BLACK","ROW");
		int[] morrisArrayBlackVert = state.getMorrisArray("BLACK","COLUMN");
		int[] morrisArrayEmptyOrizz = state.getMorrisArray("EMPTY", "ROW");
		int[] morrisArrayEmptyVert = state.getMorrisArray("EMPTY", "COLUMN");
		int[] morrisArrayWhiteOrizz = state.getMorrisArray("WHITE","ROW");
		int[] morrisArrayWhiteVert = state.getMorrisArray("WHITE","COLUMN");
		
        int threePieceMorrisBlack = 0;
        int threePieceMorrisWhite = 0;
        
		// si guarda tempOrizz e tempVert se hanno indici per i quali il valore è 2.
        for (int i=0;i<morrisArrayBlackOrizz.length;i++){
            if (morrisArrayBlackOrizz[i] == 2)
            	if (morrisArrayEmptyOrizz[i] == 1) {
        		// se si, si va a controllare gli indici delle colonne per vedere se in una di quelle 
            	// colonne si ha una two piece configuration
            		int [] linkedColumnsBlack = state.getIndexOfLinkedColumnsToRow(i+1);
				
				// si cercano twoPieceConfiguration in quelle colonne
            		for(int c=0;c<linkedColumnsBlack.length;c++){
					// si sottrae uno, perchè le colonne vanno da 0 a 7.
            			if (morrisArrayBlackVert[linkedColumnsBlack[c]-1] == 2)
            				if (morrisArrayEmptyVert[linkedColumnsBlack[c]-1] == 1){
            					threePieceMorrisBlack++;
            				}
            		}
            	}
     /*
			if (morrisArrayBlackVert[i] == 2)
	           	if (morrisArrayEmptyVert[i] == 1) {
	        	// se si, si va a controllare gli indici delle colonne per vedere se in una di quelle 
	           	// colonne si ha una two piece configuration
	           		int [] linkedRowBlack = state.getIndexOfLinkedRowToColumn(i+1);
				
				// si cercano twoPieceConfiguration in quelle colonne
	           		for(int c=0;c<linkedRowBlack.length;c++){
					// si sottrae uno, perchè le colonne vanno da 0 a 7.
	           			if (morrisArrayBlackOrizz[linkedRowBlack[c]-1] == 2)
	           				if (morrisArrayEmptyOrizz[linkedRowBlack[c]-1] == 1){
	           					threePieceMorrisBlack++;
	           				}
	           		}
	           	}
	*/		
        }
        
        
		// si guarda tempOrizz e tempVert se hanno indici per i quali il valore è 2.
        for (int i=0;i<morrisArrayWhiteOrizz.length;i++){
		    if (morrisArrayWhiteOrizz[i] == 2)
		    	if (morrisArrayEmptyOrizz[i] == 1) {
		    		// se si, si va a controllare gli indici delle colonne per vedere se in una di quelle 
		            // colonne si ha una two piece configuration
		            int [] linkedColumnsWhite = state.getIndexOfLinkedColumnsToRow(i+1);
						
					// si cercano twoPieceConfiguration in quelle colonne
					for(int c=0;c<linkedColumnsWhite.length;c++){
						// si sottrae uno, perchè le colonne vanno da 0 a 7.
						if (morrisArrayWhiteVert[linkedColumnsWhite[c]-1] == 2)
				           	if (morrisArrayEmptyVert[linkedColumnsWhite[c]-1] == 1){
				           		threePieceMorrisWhite++;
				           	}
						}
				}
		    
	/*	    
			if (morrisArrayWhiteVert[i] == 2)
				if (morrisArrayEmptyVert[i] == 1) {
			       	// se si, si va a controllare gli indici delle colonne per vedere se in una di quelle 
					// colonne si ha una two piece configuration
			        int [] linkedRowWhite = state.getIndexOfLinkedRowToColumn(i+1);
						
					// si cercano twoPieceConfiguration in quelle colonne
					for(int c=0;c<linkedRowWhite.length;c++){
						// si sottrae uno, perchè le colonne vanno da 0 a 7.
						if (morrisArrayWhiteOrizz[linkedRowWhite[c]-1] == 2)
				           	if (morrisArrayEmptyOrizz[linkedRowWhite[c]-1] == 1){
				           		threePieceMorrisWhite++;
				           	}
					}
				}
	*/		
        }

        //a questo punto a secondo che io sia il bianco o il nero
        //faccio la differenza tra i miei tris e i suoi
     //   System.out.println("[HeuristicEvaluator] 3 Pieces Conf Black Number = "+threePieceMorrisBlack+" || 3 Pieces Conf White Number = "+threePieceMorrisWhite);
 
        switch (player){
        case WHITE:
                return threePieceMorrisWhite - threePieceMorrisBlack;
        case BLACK:
                return  threePieceMorrisBlack - threePieceMorrisWhite;
        default:
                return 0;
        }
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
	//	System.out.println("[HeuristicEvaluator] Double White Morris Number = "+doubleMorrisWhite+" || Double Black Morris Number = "+doubleMorrisBlack);
				
 	
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
	/* 
	 * fase 2: il primo che arriva a 3 pedine perde.
	 *  fase 3: il primo che arriva a 2 pedine perde.
	 */ 
	private double winningConfiguration(){ 
		int whiteCheckersOnBoard = state.getWhiteCheckersOnBoard(); 
		int blackCheckersOnBoard = state.getBlackCheckersOnBoard(); 
		switch(getPhase()){ 
			case FIRST: return 0; 
			case SECOND: 
				switch (player){ 
					case WHITE: if( whiteCheckersOnBoard>3 && blackCheckersOnBoard<=3 ) 
									return 1; 
								else if (whiteCheckersOnBoard<=3 && blackCheckersOnBoard>3) 
									return -1; 
								else 
									return 0; 
					case BLACK: if( whiteCheckersOnBoard>3 && blackCheckersOnBoard<=3 ) 
									return -1; 
								else if (whiteCheckersOnBoard<=3 && blackCheckersOnBoard>3) 
									return 1; 
								else 
									return 0; 
					default: break; 
				} 
			case FINAL: 
				switch (player){ 
					case WHITE: if( whiteCheckersOnBoard>2 && blackCheckersOnBoard<=2 ) 
									return 1; 
								else if (whiteCheckersOnBoard<=2 && blackCheckersOnBoard>2) 
									return -1; 
								else 
									return 0; 
					case BLACK: if( whiteCheckersOnBoard>2 && blackCheckersOnBoard<=2 ) 
									return -1; 
								else if (whiteCheckersOnBoard<=2 && blackCheckersOnBoard>2) 
									return 1; 
								else 
									return 0; 
					default: break; 
				} 
			default: return 0; 
		} 
	}

	/*
	 * Ritorna 1 se c'è almeno 1 morris avversario
	 * 
	 */
	private double blockMill(){
		int[] morrisArrayBlackOrizz = state.getMorrisArray("BLACK","ROW");
		int[] morrisArrayBlackVert = state.getMorrisArray("BLACK","COLUMN");
		int[] morrisArrayEmptyOrizz = state.getMorrisArray("EMPTY", "ROW");
		int[] morrisArrayEmptyVert = state.getMorrisArray("EMPTY", "COLUMN");
		int[] morrisArrayWhiteOrizz = state.getMorrisArray("WHITE","ROW");
		int[] morrisArrayWhiteVert = state.getMorrisArray("WHITE","COLUMN");
		
		 
	        
	     switch (player){ 
	     
		// se sono il bianco si guardano i neri	
	     case WHITE:
				// si guarda tempOrizz e tempVert se hanno indici per i quali il valore è 2.
		        for (int i=0;i<morrisArrayBlackOrizz.length;i++){
		            if (morrisArrayBlackOrizz[i] == 2){
		        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
		            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
		            	if (morrisArrayEmptyOrizz[i] == 1)
		            		return new Double(1);
		            }
		            if (morrisArrayBlackVert[i] == 2){
		        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
		            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
		            	if (morrisArrayEmptyVert[i] == 1){
		            		return new Double(1);
		            		
		            	}
		            }
		        }
		        
		       
		    //Se sono il nero si guardano i bianchi
			case BLACK:
				for (int i=0;i<morrisArrayWhiteOrizz.length;i++){
		            if (morrisArrayWhiteOrizz[i] == 2){
		        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
		            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
		            	if (morrisArrayEmptyOrizz[i] == 1)
		            		return new Double(1);
		            }
		            if (morrisArrayWhiteVert[i] == 2){
		        		// se si, si va a controllare che il terzo posto di quella riga o di quella colonna sia libero.
		            	// utilizzando l'array empty. se l'array empty in quella posizione è == 1 allora c'è un posto libero.
		            	if (morrisArrayEmptyVert[i] == 1)
		            		return new Double(1);
		            }
		        }
		default:
			break;
				 
				 
					
					
	     
	     }
			
	        
			
	     return 0;
		
	}
	
	public MulinoState getState() {
		return state;
	}

	public void setState(MulinoState state) {
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
