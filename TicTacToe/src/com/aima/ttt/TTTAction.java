package com.aima.ttt;


public class TTTAction{

	public int row;
	public int col;
	//public String sign;
	
	public TTTAction(int r, int c/*, String s*/){
		row = r;
		col = c;
	//	sign = s;
	}
	
	@Override
	public String toString(){
		return "Azione: segno in ( "+row+" , "+col+" )";
	}

	
	
}
