package com.aima.ftt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.aima.ftt.components.Coords;
import com.aima.ftt.components.Map;

 
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class WorldStateSuccessorFunction implements SuccessorFunction {

	
	
	/*
	 * 	L'eroe non può muoversi dove ci sono i fossi.
	 * 
	 * */
	@Override
	public List getSuccessors(Object state) {

		List result = new ArrayList();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if (state instanceof WorldState) {
			WorldState worldState = (WorldState) state;
		
			if(Main.printPathFunc){
		
				/**/try {
					br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(printPath(worldState));
			}else{
				
				System.out.println(worldState.toString());
				
			}
			//System.out.println(worldState);
			
			/*
			 * if ((numMissionari > 0) && (numCannibali > 0)) {
		  		MCState newState = moveMC(mcState);
		  		if (isAllowed(newState))
		  			result.add(new Successor( MCState.mc,newState));
		  	}
			 */
			// SE SI VERIFICANO QUESTE CONDIZIONI , PUOI FARE QUESTA MOSSA
			 

//			if(! Map.isPit(worldState.heroPositionRow - 1, worldState.heroPositionColumn)){
//				WorldState newState = moveNORTH(worldState);
//				if(isAllowed(newState)){
//					result.add(new Successor("Move North",newState));
// 				}
//			}
			if(! Map.isPit(worldState.heroPositionRow + 1, worldState.heroPositionColumn)){
				WorldState newState = moveSOUTH(worldState);
				if(isAllowed(newState)){
					newState.setFather(worldState);
					result.add(new Successor("Move South",newState));
 
				}
			}
			if(! Map.isPit(worldState.heroPositionRow, worldState.heroPositionColumn+1)){
				WorldState newState = moveEAST(worldState);
				if(isAllowed(newState)){
					newState.setFather(worldState);
					result.add(new Successor("Move East",newState));
 
				}
			}
//			if(! Map.isPit(worldState.heroPositionRow, worldState.heroPositionColumn -1 )){
//				WorldState newState = moveWEST(worldState);
//				if(isAllowed(newState)){
//				newState.setFather(worldState);
//					result.add(new Successor("Move West",newState));
// 
//				}
//			}
			return result;
			
		}
		return null;
	}
	
	

	public String printPath(Object state){
		if (state instanceof WorldState) {
			List<Coords> lc = new LinkedList<Coords>();
			WorldState worldState = (WorldState) state;
			lc.add(new Coords(worldState.heroPositionRow,worldState.heroPositionColumn));
			while(worldState.getFather() != null){
				worldState = (WorldState)worldState.getFather();
				lc.add(new Coords(worldState.heroPositionRow,worldState.heroPositionColumn));	
			} 
			
		//System.out.println(lc);
			
//			 for (int i = 0; i < lc.size(); i++) {
//		            System.out.println(lc.get(i).toString());
//		     }
//			
			
			WorldState thisState = (WorldState) state;
			int x,y=0;
			String s="\n**************************";
			for(x=0;x<Map.maxHeight;x++){
				s = s + "\n\n";
				for(y=0;y<Map.maxWidth;y++){
					Coords c = new Coords(x,y);
					if( Coords.isInList(c,lc) ){
						s = s +"@     ";
					}else{
						s = s + thisState.map.map[x][y].getSymbol()+"     "; 
					}
					
				}
			}
	 
			return s;
		}
		return null;
	}
	
	 
	
 	// uno stato non è ammissibile se porta l'eroe fuori dalla mappa.
 	public static boolean isAllowed(WorldState state) {
  		if ( state.heroPositionColumn < 0 || state.heroPositionRow < 0 || state.heroPositionColumn > Map.maxHeight-1 || state.heroPositionRow > Map.maxWidth-1	)
 			return false;
 		else {
 			return true;
 		}
 	}
	
	
	/*
	 *  **************************************************** ACTIONS
	 */
	
//	public static WorldState moveNORTH(WorldState state) {
//		if (state instanceof WorldState) {
//			WorldState s = (WorldState)state;
//			WorldState newState = new WorldState(s.heroPositionRow-1,s.heroPositionColumn,false);
//			
//			return newState;
//		}
//		else{
//			return null;
//		}
//	}
	public static WorldState moveSOUTH(WorldState state) {
		if (state instanceof WorldState) {
			WorldState s = (WorldState)state;
			WorldState newState = new WorldState(s.heroPositionRow+1,s.heroPositionColumn,false);
			return newState;
		}
		else{
			return null;
		}
	}
	public static WorldState moveEAST(WorldState state) {
		if (state instanceof WorldState) {
			WorldState s = (WorldState)state;
			WorldState newState = new WorldState(s.heroPositionRow,s.heroPositionColumn+1,false);
			return newState;
		}
		else{
			return null;
		}
	}
//	public static WorldState moveWEST(WorldState state) {
//		if (state instanceof WorldState) {
//			WorldState s = (WorldState)state;
//			WorldState newState = new WorldState(s.heroPositionRow,s.heroPositionColumn-1,false);
//			return newState;
//		}
//		else{
//			return null;
//		}
//	}





 
}
