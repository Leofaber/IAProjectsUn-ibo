package Quadrato;

import aima.search.framework.HeuristicFunction;

public class QHeuristicFunction implements HeuristicFunction{

	@Override
	public double getHeuristicValue(Object state) {
/*
		if (state instanceof QState) {
 			QState qState = (QState) state;
 			int hVal = 100 - qState.getLastValue();
 			return hVal;
 		}
 		else return Integer.MAX_VALUE;
 	}
*/
		
		if (state instanceof QState) {
			QState s = (QState) state;
			int row = s.rigaLastValue;
			int col = s.colonnaLastValue;
			
			String pathChar = s.pathGrid.percorsiGriglia[row][col];
 		//	System.out.println("CHAR PATH PADRE. "+ s.parentPathChar+" NUOVO STATO. row:"+row+" col: "+col+" path char: "+pathChar);
			
			if(pathChar == s.parentPathChar){
				return new Double(1);
			}
			else return new Double(100);
			
 		}

		return new Double(1);
	}
}
