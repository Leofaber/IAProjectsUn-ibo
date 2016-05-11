/*package it.unibo.ai.didattica.mulino.domain;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

import java.util.Comparator;
import java.util.HashMap;

public class ActionComparatorClosestChecker implements Comparator<String>{

	private State currentState;

	public ActionComparatorClosestChecker(State state) {
		setCurrentState(state);
	}
	
	@Override
	public int compare(String a1, String a2) {
		boolean val1=false;
		boolean val2=false;
		HashMap<String, Checker> board= currentState.getBoard();
		
		
		if(a1.length() == 4 && a2.length() == 2){
			return -1;
		}
		else if(a2.length() == 4 && a1.length() == 2){
			return 1;
		}else if(a1.length() == 4 && a2.length() == 4){
			return 0;
		}
		
		else{
			
			 
		
				try {
					String[] adjacentPositionsA1 = State.getAdjacentPositions(a1);
					int i=0;
					while( i<adjacentPositionsA1.length && val1 == false){
						if(board.get(adjacentPositionsA1[i])!=Checker.EMPTY){
							val1 = true;
						}
						i++;
					}
				} catch (InvalidPositionException e) {
		 			e.printStackTrace();
				}
				try {
					String[] adjacentPositionsA2 = State.getAdjacentPositions(a2);
					int i=0;
					while( i<adjacentPositionsA2.length && val2 == false){
						if(board.get(adjacentPositionsA2[i])!=Checker.EMPTY){
							val2 = true;
						}
						i++;
					}
				} catch (InvalidPositionException e) {
		 			e.printStackTrace();
				}
				
				
				if(val1 == val2){
					return 0;
				}
				else if(val1 && val2 == false){
					return 1;
				}
				else if(val1 == false && val2){
					return -1;
				}
				return 0;
				
		}
		 
	 
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}
*/