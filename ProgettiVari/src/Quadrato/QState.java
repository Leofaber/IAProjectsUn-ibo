package Quadrato;

import java.util.Arrays;

import aima.search.framework.GoalTest;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.StepCostFunction;

/*			RIEMPIMENTO DI UN QUADRATO
 * E� dato un quadrato di 10 caselle per 10 (in totale 100 caselle).
� Nello stato iniziale tutte le caselle sono vuote tranne la pi� in alto a
sinistra che contiene il valore 1.
� Problema: assegnare a tutte le caselle un numero consecutivo, a
partire da 1, fino a 100, secondo le seguenti regole:
� A partire da una casella con valore assegnato x, si pu� assegnare il
valore (x+1) solo ad una casella vuota che dista 2 caselle sia in
verticale, che orizzontale, oppure 1 casella in diagonale.
Se il quadrato � ancora vuoto, per una casella generica ci sono 7
possibili caselle vuote su dove andare
� Perch� le caselle sono 7 e non 8 (4 in diagonale e 4 in
orizzontale/verticale) ?
� Man mano che si riempe il quadrato, le caselle libere diminuiscono 
diminuisce il fattore di ramificazione
� La profondit� dell�albero � 100 (dobbiamo assegnare 100 numeri � 99
se il primo � gi� stato assegnato)
 */
public class QState implements GoalTest, StepCostFunction, HeuristicFunction, Cloneable {

	static public final String n = "Nord";
	static public final String s = "Sud";
	static public final String o = "Ovest";
	static public final String e = "Est";
	static public final String ne = "NordEst";
	static public final String no = "NordOvest";
	static public final String se = "SudEst";
	static public final String so = "SudOvest";
	
	static public final int N = 1;
	static public final int S = 2;
	static public final int O = 3;
	static public final int E = 4;
	static public final int NE = 5;
	static public final int NO = 6;
	static public final int SE = 7;
	static public final int SO = 8;
	
	public String[][] griglia;
	public int lastValue, rigaLastValue, colonnaLastValue;
	
	public QState() {
		griglia = new String[10][10];
		int i,j;
		for(i=0;i<10;i++) {
			for (j=0;j<10;j++) {
				griglia[i][j] = "*";
			}
		}
		griglia[0][0] = "1";
		lastValue = 1;
		rigaLastValue = 0;
		colonnaLastValue = 0;
	}
	
	public void addValue(int riga, int colonna, int valore){
		griglia[riga][colonna] = Integer.toString(valore);
	}
	
	public QState(String[][] g) {
		griglia = new String[10][10];
		int i,j;
		for(i=0;i<10;i++) {
			for (j=0;j<10;j++) {
				griglia[i][j] = g[i][j];
			}
		}
				
	/*	griglia = new String[10][];
		for(int i = 0; i < 10; i++)
		{
			//griglia[i] = Arrays.copyOf(g[i], g[i].length);
			griglia[i] = g[i].clone();
		}*/
	}
	
	public QState(String[][] g, int riga, int colonna, int val) {
		griglia = g;
		griglia[riga][colonna] = Integer.toString(val);
		lastValue = val;
		rigaLastValue = riga;
		colonnaLastValue = colonna;
	}
	
	public String[][] getGriglia() {
		return griglia;
	}
	
	public void getState() {
		int i,j;
		for(i=0;i<10;i++) {
			for (j=0;j<10;j++) {
				System.out.print(""+griglia[i][j]);
				System.out.print("  ");
			}
			System.out.println("");
		}
		System.out.println("-----------------------------\n");
	}
	
	public String getValue(int riga, int colonna) {
		return griglia[riga][colonna];
	}
	
	public int getLastValue() {
		int i,j;
		for(i=0;i<10;i++) {
			for (j=0;j<10;j++) {
				if (griglia[i][j] != "*") {
					if(lastValue < Integer.parseInt(griglia[i][j])) {	//da trasformare in intero
						lastValue = Integer.parseInt(griglia[i][j]);
						rigaLastValue = i;
						colonnaLastValue = j;
					}
				}
			}
		}
		return lastValue;
	}
	
	public int getRigaLastValue() {
		return rigaLastValue;
	}
	
	public int getColonnaLastValue() {
		return colonnaLastValue;
	}
	
	@Override
	public double getHeuristicValue(Object state) {
		if (state instanceof QState) {
 			QState qState = (QState) state;
 			int hVal = 100 - qState.getLastValue();
 			return hVal;
 		}
 		else return Integer.MAX_VALUE;
	}

	@Override
	public Double calculateStepCost(Object arg0, Object arg1, String arg2) {
		return new Double(1);
	}

	@Override
	public boolean isGoalState(Object state) {
		if (state instanceof QState) {
			QState qState = (QState) state;
		//	System.out.println("********************Testo il Goal*********   val: "+qState.getLastValue());
			if (qState.getLastValue() == 10) {
				qState.getState();
				return true;
			}else 
				return false;
		}
		else
			return false;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
