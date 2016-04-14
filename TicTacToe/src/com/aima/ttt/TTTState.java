package com.aima.ttt;


public class TTTState{

	
	
	public static final String X = "X";
	public static final String O = "O";
	public static final String EMPTY = ".";
	
	public String grid[][];
	public static final int gridRows = 3;
	public static final int gridCols = 3;
	
	
	
	public TTTState(){
		grid = new String[gridRows][gridCols];
		int i,j = 0;
		for(i=0;i<gridRows;i++){
			for(j=0;j<gridCols;j++){
				grid[i][j] = EMPTY;
			}
		}
	}
	
	
	public void printState(){
		int i,j = 0;
		for(i=0;i<gridRows;i++){
			for(j=0;j<gridCols;j++){
				System.out.print(grid[i][j]+"     "); 
			}
			System.out.print("\n\n");
		}
	}
	
	public int getNumberOfSign(){
		int i,j,count = 0;
		for(i=0;i<gridRows;i++){
			for(j=0;j<gridCols;j++){
				if(grid[i][j] == "X" || grid[i][j] == "O"){
					count++;
				}
			}
		}
		return count;
	}
	
	public void addSign(String sign, int rowPos, int colPos){
		System.out.println("Aggiungo il segno "+sign+" alla riga: "+rowPos+" e colonna: "+colPos);
		this.grid[rowPos][colPos] = sign;
		printState();
	}
 
	public boolean checkForTris(String sign){
		// CHECK TRIS ORIZZONTALI
		if( grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2] && grid[0][2] == sign){
			return true;
		}
		if( grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2] && grid[1][2] == sign){
			return true;
		}
		if( grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2] && grid[2][2] == sign){
			return true;
		}
				
		// CHECK TRIS VERTICALI
		if( grid[0][0] == grid[1][0] && grid[1][0] == grid[2][0] && grid[2][0] == sign){
			return true;
		}
		if( grid[0][1] == grid[1][1] && grid[1][1] == grid[2][1] && grid[2][1] == sign){
			return true;
		}
		if( grid[0][2] == grid[1][2] && grid[1][2] == grid[2][2] && grid[2][2] == sign){
			return true;
		}
					
		// CHECK TRIS DIAGONALI
		if( grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[2][2] == sign){
			return true;
		}
		if( grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[2][0] == sign){
			return true;
		}
		
		return false;
		
	}
	
	public boolean checkAllFilled(){
		// CHECK ALL FILLED
		if( grid[0][0]!=EMPTY && 
			grid[0][1]!=EMPTY && 
			grid[0][2]!=EMPTY && 
			grid[1][0]!=EMPTY && 
			grid[1][1]!=EMPTY && 
			grid[1][2]!=EMPTY && 
			grid[2][0]!=EMPTY && 
			grid[2][1]!=EMPTY && 
			grid[2][2]!=EMPTY ){
						
			return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		printState();
		return "";
	}
}
