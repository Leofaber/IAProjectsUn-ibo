package com.aima.ftt;

 
 import java.util.List;

import com.aima.ftt.components.Coords;
import com.aima.ftt.components.Map;

 import aima.search.framework.GoalTest;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.StepCostFunction;

/*
		 
		 * 	*  *  *  	   						
		 *  *  *  *   
		 *  *  *  *  				
		 *  *  *  * 
 */



public class WorldState implements GoalTest, StepCostFunction{

	
	public int heroPositionRow;
	public int heroPositionColumn;
	public Map map;
	
	private WorldState father;
	
	

	public WorldState(int heroRow, int heroCol, boolean random){
		this.heroPositionRow = heroRow;
		this.heroPositionColumn = heroCol;
		if(random){
			this.map = Map.getRandomedInstance();
		}else{
			this.map = Map.getInstance();
		}
		
		
		
		// Remember that everything in the matrix is initialized to null so
		// you must initialize everything
		
		/* 
		 *      *  *  *  *
		 *      *  X  *  *
		 *      *  *  *  X 
				*  X  *  T
		 */     
		
		
		
	}
	
	
//	public String toString(){
//		
//		int x,y=0;
//		String s="\n**************************";
//		for(x=0;x<Map.maxHeight;x++){
//			s = s + "\n\n";
//			for(y=0;y<Map.maxWidth;y++){
//				if(heroPositionRow == x && heroPositionColumn == y){
//					s = s +"@     ";
//				}else{
//					s = s + map.map[x][y].getSymbol()+"     "; 
//				}
//				
//			}
//		}
// 
//		return s;
//	}
	public String toString(){
			
			int x,y=0;
			String s="\n**************************";
			for(x=0;x<Map.maxHeight;x++){
				s = s + "\n\n";
				for(y=0;y<Map.maxWidth;y++){
					if(heroPositionRow == x && heroPositionColumn == y){
						s = s +"@     ";
					}else{
						s = s + map.map[x][y].getSymbol()+"     "; 
					}
					
				}
			}
	 
			return s;
		}
	
	
	@Override
	public boolean isGoalState(Object state) {
		if (state instanceof WorldState) {
			WorldState s = (WorldState)state;
			if(s.heroPositionRow == s.map.treasure.rowPosition && s.heroPositionColumn == s.map.treasure.columnPosition){
				return true;
			}else{
				return false;
			}
				
		}else{
			return false;
		}
		
	}


	@Override
	public Double calculateStepCost(Object s1, Object s2, String arg2) {
		if (s1 instanceof WorldState && s2 instanceof WorldState) {
			WorldState stateFrom = (WorldState)s1;
			WorldState stateTo = (WorldState)s2;
			if(stateTo.heroPositionColumn > stateFrom.heroPositionColumn){
				return new Double(2);
			}
		}
		return new Double(1);
	}

	public void setFather(Object state){
		if (state instanceof WorldState) {
			WorldState s = (WorldState)state;
			this.father = s;
		}
		
	}
	
	public Object getFather(){
		return father;
	}
	
		
	
}
