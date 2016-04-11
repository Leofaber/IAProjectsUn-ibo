package com.aima.ftt.components;

import java.util.List;

public class Coords {
	public int x;
	public int y;
	
	public Coords(int x, int y){
		this.x=x;
		this.y=y;
	}
 
	public boolean equalsTo(Coords c1){
		if(this.x == c1.x && this.y == c1.y){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "(x:"+x+" y:"+y+")";
		
	}
	
	public static boolean isInList(Coords c, List<Coords> l){
		
		 for (int i = 0; i < l.size(); i++) {
	         if(c.equalsTo(l.get(i))){
	        	 return true;
	         }
			 
	     }
		 return false;
		
	}
}
