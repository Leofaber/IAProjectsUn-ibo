package Quadrato;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class QSuccessorFunction implements SuccessorFunction {

	public int count;
	public static int max;
	Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	public QSuccessorFunction() {
		count = 0;	//contatore dei nodi espansi
		max = 0;
	}
	
	public String getPathChar(Object state,int r,int c){
		QState qState = (QState) state;
		return qState.pathGrid.percorsiGriglia[r][c];
	}
	
	public List getSuccessors(Object state) {
		
		List result = new ArrayList();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		if (state instanceof QState) {
			QState qState = (QState) state;
			count++;
			
			
			
			String[][] g = new String[10][10];	//griglia temporanea
			g = qState.getGriglia();
			int lastValue = qState.getLastValue();	//ultimo valore inserito (nonch� il maggiore)
			int riga = qState.getRigaLastValue();	//riga dell'ultimo valore inserito
			int colonna = qState.getColonnaLastValue();	//colonna dell'ultimo valore inserito
			
	/*		System.out.println("Sto calcolando i successori di "+getPathChar(qState,riga,colonna)+". Row: "+riga+" Col: "+colonna+" Valore Attuale: "+lastValue+" e numero di stati espansi: "+count+"\n");
			try {
				br.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	*/
	//		qState.getState();

			if (lastValue > max) {
				max = lastValue;
				System.out.println("Siamo al: "+max+" dopo aver espanso: "+count);
	//il tempo non funziona, non sappiamo perch� e non abbiamo troppa voglia di pensarci
	//			System.out.println(" al tempo: "+sdf.format(cal.getTime()) );
				if (lastValue == 99)
					System.out.println("******************UOOOOOOOOOOOOOOOO quasi");
			}
			
			//In ogni if controllo di non uscire mai dalla griglia 
			
			if (colonna <= 6) {
				if (isAllowed(qState, qState.E)) {
					QState newState = new QState(g);
					newState.addValue(riga, colonna+3,lastValue+1);
		//			newState.getState();
		//			QState newState = new QState(g,riga,colonna+3,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.e, newState));
				}
			}
/*qState.getState();
try {
	System.out.print("Last Value: "+lastValue+"  ");
	System.out.println("press enter");
	br.readLine();
} catch (IOException e) {
	e.printStackTrace();
}*/
			if (colonna >= 3) {
				if (isAllowed(qState, qState.O)) {
					QState newState = new QState(g);
					newState.addValue(riga, colonna-3,lastValue+1);
		//			newState.getState();
		//			QState newState = moveO(qState, lastValue);
		//			QState newState = new QState(g,riga,colonna-3,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.o, newState));
				}
			}
			
			if (riga <= 6) {
				if (isAllowed(qState, qState.S)) {
					QState newState = new QState(g);
					newState.addValue(riga+3, colonna,lastValue+1);
		//			newState.getState();
		//			QState newState = moveS(qState, lastValue);
		//			QState newState = new QState(g,riga+3,colonna,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.s, newState));
				}
			}
/*			qState.getState();
			try {
				System.out.println("press enter");
				br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	*/		
			if (riga >= 3) {
				if (isAllowed(qState, qState.N)) {
					QState newState = new QState(g);
					newState.addValue(riga-3, colonna,lastValue+1);
		//			newState.getState();
		//			QState newState = moveN(qState, lastValue);
		//			QState newState = new QState(g,riga-3,colonna,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.n, newState));
				}
			}
			
			if (colonna <= 7 && riga >= 2) {
				if (isAllowed(qState, qState.NE)) {
					QState newState = new QState(g);
					newState.addValue(riga-2, colonna+2,lastValue+1);
		//			newState.getState();
		//			QState newState = moveNE(qState, lastValue);
		//			QState newState = new QState(g,riga-2,colonna+2,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.ne, newState));
				}
			}
			
			if (colonna >= 2 && riga >= 2) {
				if (isAllowed(qState, qState.NO)) {
					QState newState = new QState(g);
					newState.addValue(riga-2, colonna-2,lastValue+1);
		//			newState.getState();
		//			QState newState = moveNO(qState, lastValue);
		//			QState newState = new QState(g,riga-2,colonna-2,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.no, newState));
				}
			}
			
			if (colonna <= 7 && riga <= 7) {
				if (isAllowed(qState, qState.SE)) {
					QState newState = new QState(g);
					newState.addValue(riga+2, colonna+2,lastValue+1);
		//			newState.getState();
		//			QState newState = moveSE(qState, lastValue);
		//			QState newState = new QState(g,riga+2,colonna+2,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.se, newState));
				}
			}
		/*	qState.getState();
			try {
				System.out.println("press enter");
				br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		*/	
			if (colonna >= 2 && riga <= 7) {
				if (isAllowed(qState, qState.SO)) {
					QState newState = new QState(g);
					newState.addValue(riga+2, colonna-2,lastValue+1);
		//			newState.getState();
		//			QState newState = moveSO(qState, lastValue);
		//			QState newState = new QState(g,riga+2,colonna-2,lastValue+1,getPathChar(qState,riga,colonna));
					result.add(new Successor(newState.so, newState));
				}
			}
		}
		/*
		try {
			System.out.println("press enter");
			br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
 		return result;
	}

	/*
	* Funzioni per il movimento
	* che alla fine ho deciso di integrare nell'if
	* 
	private QState moveE(QState s, int v) {
		String [][] g = new String[10][10]; 
		g = s.getGriglia();
		g[s.getRigaLastValue()][s.getColonnaLastValue()+3] = Integer.toString(v+1);
	//	System.out.println(("Creo il nuovo qstate che va a est"));
		return new QState(g);
	}

	private QState moveN(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()-3][s.getColonnaLastValue()] = Integer.toString(v+1);
		return new QState(g);
	}
	
	private QState moveO(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()][s.getColonnaLastValue()-3] = Integer.toString(v+1);
		return new QState(g);
	}
	
	private QState moveS(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()+3][s.getColonnaLastValue()] = Integer.toString(v+1);
		return new QState(g);
	}
	
	private QState moveNE(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()-2][s.getColonnaLastValue()+2] = Integer.toString(v+1);
		return new QState(g);
	}
	
	private QState moveNO(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()-2][s.getColonnaLastValue()-2] = Integer.toString(v+1);
		return new QState(g);
	}
	
	private QState moveSE(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()+2][s.getColonnaLastValue()+2] = Integer.toString(v+1);
		return new QState(g);
	}
	
	private QState moveSO(QState s, int v) {
		String [][] g = s.getGriglia();
		g[s.getRigaLastValue()+2][s.getColonnaLastValue()-2] = Integer.toString(v+1);
		return new QState(g);
	}
	
	*/
	
	// controlla che la mia futura casella sia vuota
	private boolean isAllowed(QState s, int dir) {
		boolean flag = false;
		switch (dir) {
			case 0 : System.out.println("ERRORE!!!");
			//nord
			case 1: if (s.getValue(s.getRigaLastValue()-3, s.getColonnaLastValue()) == "*")
						flag = true;
					break;
			//sud
			case 2: if (s.getValue(s.getRigaLastValue()+3, s.getColonnaLastValue()) == "*")
						flag = true;
					break;
			//ovest
			case 3: if (s.getValue(s.getRigaLastValue(), s.getColonnaLastValue()-3) == "*")
						flag = true;
					break;
			//est
			case 4: if (s.getValue(s.getRigaLastValue(), s.getColonnaLastValue()+3) == "*")
			//		System.out.println("Posso andare a Est");
						flag = true;
					break;
			//nordEst
			case 5: if (s.getValue(s.getRigaLastValue()-2, s.getColonnaLastValue()+2) == "*")
						flag = true;
					break;
			//nordOvest
			case 6: if (s.getValue(s.getRigaLastValue()-2, s.getColonnaLastValue()-2) == "*")
						flag = true;
					break;
			//sudEst
			case 7: if (s.getValue(s.getRigaLastValue()+2, s.getColonnaLastValue()+2) == "*")
						flag = true;
					break;
			//sudOvest
			case 8: if (s.getValue(s.getRigaLastValue()+2, s.getColonnaLastValue()-2) == "*")
						flag = true;
					break;
		}
		//System.out.println("esco da isAllowed");
		return flag;
	}
	
}
