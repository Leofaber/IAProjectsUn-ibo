package com.aima.ftt.components;

public class FreeCell extends MapObject implements IsWalkable{

	public FreeCell(int row, int col) {
		super(row, col);
		this.symbol = "*";

		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isWalkable() {
		// TODO Auto-generated method stub
		return true;
	}

}
