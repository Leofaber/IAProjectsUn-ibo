package Quadrato;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class QSuccessorFunction implements SuccessorFunction {

	public int count;
	public QSuccessorFunction() {
		count = 0;
	}
	
	public List getSuccessors(Object state) {
		
		List result = new ArrayList();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		if (state instanceof QState) {
			QState qState = (QState) state;
			count++;
			
			String[][] g = new String[10][10];
			g = qState.getGriglia();
			int lastValue = qState.getLastValue();
			int riga = qState.getRigaLastValue();
			int colonna = qState.getColonnaLastValue();
			
			System.out.println("Valore Attuale: "+lastValue+" e numero di stati espansi: "+count+"\n");
			
	//		qState.getState();

			if (colonna <= 6) { 
				if (isAllowed(qState, qState.E)) {
					QState newState = new QState(g);
					newState.addValue(riga, colonna+3,lastValue+1);
		//			newState.getState();
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
			//		QState newState = moveO(qState, lastValue);
					result.add(new Successor(newState.o, newState));
				}
			}
			
			if (riga <= 6) {
				if (isAllowed(qState, qState.S)) {
					QState newState = new QState(g);
					newState.addValue(riga+3, colonna,lastValue+1);
		//			newState.getState();
//					QState newState = moveS(qState, lastValue);
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
//					QState newState = moveN(qState, lastValue);
					result.add(new Successor(newState.n, newState));
				}
			}
			
			if (colonna <= 7 && riga >= 2) {
				if (isAllowed(qState, qState.NE)) {
					QState newState = new QState(g);
					newState.addValue(riga-2, colonna+2,lastValue+1);
		//			newState.getState();
//					QState newState = moveNE(qState, lastValue);
					result.add(new Successor(newState.ne, newState));
				}
			}
			
			if (colonna >= 2 && riga >= 2) {
				if (isAllowed(qState, qState.NO)) {
					QState newState = new QState(g);
					newState.addValue(riga-2, colonna-2,lastValue+1);
		//			newState.getState();
//					QState newState = moveNO(qState, lastValue);
					result.add(new Successor(newState.no, newState));
				}
			}
			
			if (colonna <= 7 && riga <= 7) {
				if (isAllowed(qState, qState.SE)) {
					QState newState = new QState(g);
					newState.addValue(riga+2, colonna+2,lastValue+1);
		//			newState.getState();
//					QState newState = moveSE(qState, lastValue);
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
//					QState newState = moveSO(qState, lastValue);
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
