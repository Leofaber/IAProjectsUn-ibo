package com.aima.ftt;

import com.aima.ftt.components.Coords;

import aima.search.framework.HeuristicFunction;

public class WorldStateHeuristicFunction implements HeuristicFunction {

	@Override
	public double getHeuristicValue(Object state) {
		if (state instanceof WorldState) {
			WorldState worldState = (WorldState) state;
			
			WorldState wsTemp = worldState;
			int stepCostCounter = 0;
			while(wsTemp.getFather() != null){
				wsTemp = (WorldState)wsTemp.getFather();
				stepCostCounter++;
 			} 
			
			
			
			Coords treasoureCoords = worldState.map.getTresoureCoords();
			int tx = treasoureCoords.x;
			int ty = treasoureCoords.y;
 			int hVal = (tx - worldState.heroPositionRow) + ( ty - worldState.heroPositionColumn   );
 			
 			int tot = stepCostCounter+hVal;
 			
 			System.out.println("Costo di ( "+worldState.heroPositionRow+" , "+worldState.heroPositionColumn+" ) from StepCost: "+stepCostCounter+" + Heuristic: "+hVal+". TOT = "+tot+". Treasure in ("+tx+","+ty+")");
 			return hVal;
 		}
 		else return Integer.MAX_VALUE;
		 
	}

}
