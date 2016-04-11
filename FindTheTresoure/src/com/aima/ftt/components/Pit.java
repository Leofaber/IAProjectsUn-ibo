package com.aima.ftt.components;

public class Pit extends MapObject implements IsWalkable{

	public Pit(int row, int col) {
		super(row, col);
		this.symbol = "X";

		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isWalkable() {
		return false;
	}
	
	
}
