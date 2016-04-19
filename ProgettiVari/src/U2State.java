import aima.search.framework.GoalTest;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.StepCostFunction;

/*
 * Testo
 * 
 * Il complesso degli U2 sta per fare un concerto a Dublino.
• Mancano 17 minuti all'inizio del concerto ma, per raggiungere il palco,
i membri del gruppo devono attraversare un piccolo ponte che è tutto
al buio, disponendo di una sola torcia elettrica. Sul ponte non possono
andare più di due persone per volta. La torcia è essenziale per
l'attraversamento, per cui deve essere portava avanti e indietro (non
può essere lanciata da una parte all'altra) per consentire a tutti di
passare. Tutti sono dalla stessa parte del ponte.
• Ciascun componente del complesso cammina a una velocità diversa.
I tempi individuali per attraversare il ponte sono:
• Bono, 1 minuto
• Edge, 2 minuti
• Adam, 5 minuti
• Larry, 10 minuti

• Se attraversano in due, la coppia camminerà alla velocità del più
lento.
• Ad esempio: se Bono e Larry attraversano per primi, quando arrivano
dall'altra parte saranno trascorsi 10 minuti. Se Larry torna indietro con
la torcia saranno passati altri dieci minuti, per cui la missione sarà
fallita.
• Si stabilisca una funzione euristica ammissibile per questo problema e
lo si risolva tramite l’algoritmo A* per i grafi, mostrando il grafo
generato. Per limitare la dimensione dello spazio di ricerca, si
supponga che i componenti si muovano sempre in 2 in una direzione
e sempre in 1 nella direzione opposta (quando devono riportare
indietro la torcia per prendere gli altri componenti della band).

• Per definire la funzione euristica h’ si usi la seguente idea:
• Possono spostarsi solo 2 persone alla volta, per cui raggruppiamo le persone
sulla riva di partenza (sinistra) in gruppi da 2. Ordino i tempi di percorrenza
dal più alto al più basso e metto
• nel primo gruppo i primi due
• nel secondo gruppo il terzo ed il quarto.
• Ciascun gruppo si muoverà alla velocità del più lento, per cui per ogni gruppo
prendo il massimo. La funzione h’ è data dalla somma dei tempi di
percorrenza di ciascun gruppo. In altre parole, il tempo di percorrenza totale
sarà almeno il tempo di percorrenza del più lento, più quello del terzo più
lento (il secondo più lento potrebbe essersi mosso con il più lento, per cui il
suo tempo non viene calcolato).
• Nell’algoritmo A* per i grafi si considerano cammini alternativi per arrivare ad
uno stesso stato; in caso di cammini alternativi viene tenuto il costo minore.
Rappresentiamo con una freccia piena il cammino migliore e con una freccia
tratteggiata i cammini peggiori.
• La funzione h’ cosi’ definita e’ ammissibile? L’algoritmo trovera` la strada
migliore?
 */

public class U2State implements GoalTest, StepCostFunction, HeuristicFunction {

	static public final String be = "BonoEdge vanno a sinistra";
	static public final String ba = "BonoAdam vanno a sinistra";
	static public final String bl = "BonoLarry vanno a sinistra";
	static public final String ea = "EdgeAdam vanno a sinistra";
	static public final String el = "EdgeLarry vanno a sinistra";
	static public final String al = "AdamLarry vanno a sinistra";
	static public final String b = "Bono va a destra";
	static public final String e = "Edge va a destra";
	static public final String a = "Adam va a destra";
	static public final String l = "Larry va a destra";
	
	static public final int BE = 2;
	static public final int BA = 5;
	static public final int BL = 10;
	static public final int EA = 5;
	static public final int EL = 10;
	static public final int AL = 10;
	static public final int B = 1;
	static public final int E = 2;
	static public final int A = 5;
	static public final int L = 10;
	
	// true se sono dal lato sinistro del ponte
	// partono dal lato destro, quindi false
	private boolean Bono;	//1 minuto
	private boolean Edge;	//2 minuti
	private boolean Adam;	//5 minuti
	private boolean Larry;	//10 minuti
	
	private int membriSinistra;
	private int membriDestra;
	private int tempo;
	
	// false se è dal lato destro, true se è dal lato sinistro
	private boolean torcia;
	
	public U2State () {
		membriSinistra = 0;
		membriDestra = 4;
		Bono = false;
		Edge = false;
		Adam = false;
		Larry = false;
		torcia = false;
		tempo = 17;
	}
	
	public U2State(boolean b, boolean e, boolean a, boolean l, int sx, int dx, boolean t, int time) {
		Bono = b;
		Edge = e;
		Adam = a;
		Larry = l;
		membriSinistra = sx;
		membriDestra = dx;
		torcia = t;
		tempo = time;
	}
	
	public int getMembriSx() {
		return membriSinistra;
	}
	
	public int getMembriDx() {
		return membriDestra;
	}
	
	public boolean getTorcia() {
		return torcia;
	}
	
	public boolean getBono() {
		return Bono;
	}
	
	public boolean getEdge() {
		return Edge;
	}
	
	public boolean getAdam() {
		return Adam;
	}
	
	public boolean getLarry() {
		return Larry;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public void getState() {
		System.out.println("Al momento ci sono "+membriDestra+" della band a destra e "+membriSinistra+" membri a sinistra");
		if (torcia)
			System.out.print("La torcia è a sinistra");
		else
			System.out.print("La torcia è a destra");
		System.out.println(" Mancano "+tempo+ " al concerto");
	}
	
	public void getMembri() {
		System.out.println("Bono: "+Bono+" -- Edge: "+Edge+" -- Adam: "+Adam+" -- Larry: "+Larry);
	}
	
	@Override
	public double getHeuristicValue(Object state) {
		if (state instanceof U2State) {
			U2State u2State = (U2State) state;
 			int hVal = u2State.membriDestra - (u2State.torcia? 0:1);
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
	//	System.out.println("Check Goal State");
		if (state instanceof U2State) {
			U2State u2State = (U2State) state;
		   return (		(u2State.membriDestra == 0) && 
		    				(u2State.membriSinistra == 4) &&
							(u2State.torcia == true)
			);
		}
		else
			return false;
	}

}
