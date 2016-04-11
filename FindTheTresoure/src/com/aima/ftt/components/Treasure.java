package com.aima.ftt.components;

public class Treasure extends MapObject implements IsWalkable{

	public Treasure(int row, int col) {
		super(row, col);
		this.symbol = "T";

	}
	
	

	@Override
	public boolean isWalkable() {
		return true;
	}

	
	
}
