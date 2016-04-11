package com.aima.ftt.components;

public class MapObject {

	public int rowPosition;
	public int columnPosition;

	public MapObject(int row , int col){
		this.rowPosition = row;
		this.columnPosition = col;
		symbol = "*";
	}
	
	public String symbol;
	
	public String getSymbol(){
		return symbol;
	}
	
	
}
