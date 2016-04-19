package Quadrato;

public class PathGrid {
	public String[][] percorsiGriglia;
	
	private static PathGrid INSTANCE;
	
	private PathGrid(){
		percorsiGriglia = new String[10][10];
		percorsiGriglia[0][0] = "A";
		percorsiGriglia[0][1] = "B";
		percorsiGriglia[0][2] = "C";
		percorsiGriglia[0][3] = "A";
		percorsiGriglia[0][4] = "B";
		percorsiGriglia[0][5] = "C";
		percorsiGriglia[0][6] = "A";
		percorsiGriglia[0][7] = "B";
		percorsiGriglia[0][8] = "C";
		percorsiGriglia[0][9] = "A";
		                     
		percorsiGriglia[1][0] = "D";
		percorsiGriglia[1][1] = "E";
		percorsiGriglia[1][2] = "F";
		percorsiGriglia[1][3] = "D";
		percorsiGriglia[1][4] = "E";
		percorsiGriglia[1][5] = "F";
		percorsiGriglia[1][6] = "D";
		percorsiGriglia[1][7] = "E";
		percorsiGriglia[1][8] = "F";
		percorsiGriglia[1][9] = "D";
		                     
		percorsiGriglia[2][0] = "G";
		percorsiGriglia[2][1] = "H";
		percorsiGriglia[2][2] = "I";
		percorsiGriglia[2][3] = "G";
		percorsiGriglia[2][4] = "H";
		percorsiGriglia[2][5] = "I";
		percorsiGriglia[2][6] = "G";
		percorsiGriglia[2][7] = "H";
		percorsiGriglia[2][8] = "I";
		percorsiGriglia[2][9] = "G";
		                     
		percorsiGriglia[3][0] = "A";
		percorsiGriglia[3][1] = "B";
		percorsiGriglia[3][2] = "C";
		percorsiGriglia[3][3] = "A";
		percorsiGriglia[3][4] = "B";
		percorsiGriglia[3][5] = "C";
		percorsiGriglia[3][6] = "A";
		percorsiGriglia[3][7] = "B";
		percorsiGriglia[3][8] = "C";
		percorsiGriglia[3][9] = "A";
		                     
		percorsiGriglia[4][0] = "D";
		percorsiGriglia[4][1] = "E";
		percorsiGriglia[4][2] = "F";
		percorsiGriglia[4][3] = "D";
		percorsiGriglia[4][4] = "E";
		percorsiGriglia[4][5] = "F";
		percorsiGriglia[4][6] = "D";
		percorsiGriglia[4][7] = "E";
		percorsiGriglia[4][8] = "F";
		percorsiGriglia[4][9] = "D";
		                     
		percorsiGriglia[5][0] = "G";
		percorsiGriglia[5][1] = "H";
		percorsiGriglia[5][2] = "I";
		percorsiGriglia[5][3] = "G";
		percorsiGriglia[5][4] = "H";
		percorsiGriglia[5][5] = "I";
		percorsiGriglia[5][6] = "G";
		percorsiGriglia[5][7] = "H";
		percorsiGriglia[5][8] = "I";
		percorsiGriglia[5][9] = "G";
		                     
		percorsiGriglia[6][0] = "A";
		percorsiGriglia[6][1] = "B";
		percorsiGriglia[6][2] = "C";
		percorsiGriglia[6][3] = "A";
		percorsiGriglia[6][4] = "B";
		percorsiGriglia[6][5] = "C";
		percorsiGriglia[6][6] = "A";
		percorsiGriglia[6][7] = "B";
		percorsiGriglia[6][8] = "C";
		percorsiGriglia[6][9] = "A";
		                     
		percorsiGriglia[7][0] = "D";
		percorsiGriglia[7][1] = "E";
		percorsiGriglia[7][2] = "F";
		percorsiGriglia[7][3] = "D";
		percorsiGriglia[7][4] = "E";
		percorsiGriglia[7][5] = "F";
		percorsiGriglia[7][6] = "D";
		percorsiGriglia[7][7] = "E";
		percorsiGriglia[7][8] = "F";
		percorsiGriglia[7][9] = "D";
		                     
		percorsiGriglia[8][0] = "G";
		percorsiGriglia[8][1] = "H";
		percorsiGriglia[8][2] = "I";
		percorsiGriglia[8][3] = "G";
		percorsiGriglia[8][4] = "H";
		percorsiGriglia[8][5] = "I";
		percorsiGriglia[8][6] = "G";
		percorsiGriglia[8][7] = "H";
		percorsiGriglia[8][8] = "I";
		percorsiGriglia[8][9] = "G";
		                     
		percorsiGriglia[9][0] = "A";
		percorsiGriglia[9][1] = "B";
		percorsiGriglia[9][2] = "C";
		percorsiGriglia[9][3] = "A";
		percorsiGriglia[9][4] = "B";
		percorsiGriglia[9][5] = "C";
		percorsiGriglia[9][6] = "A";
		percorsiGriglia[9][7] = "B";
		percorsiGriglia[9][8] = "C";
		percorsiGriglia[9][9] = "A";

	}
	
	public static PathGrid getPathGrid(){
		if(INSTANCE == null){
			return new PathGrid();
		}else{
			return INSTANCE;
		}
	}
}
