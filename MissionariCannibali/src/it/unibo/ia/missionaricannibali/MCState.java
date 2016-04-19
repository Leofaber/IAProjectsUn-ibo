package it.unibo.ia.missionaricannibali;

public class MCState {
	private int num_cannibali, num_missionari, pos_barca;
	
	public MCState(int num_cannibali, int num_missionari, int pos_barca){
		this.setNum_cannibali(num_cannibali);
		this.setNum_missionari(num_missionari);
		this.setPos_barca(pos_barca);
	}
	
	public MCState(){
		this(3,3,1);
	}

	public int getNum_cannibali() {
		return num_cannibali;
	}

	public void setNum_cannibali(int num_cannibali) {
		this.num_cannibali = num_cannibali;
	}

	public int getNum_missionari() {
		return num_missionari;
	}

	public void setNum_missionari(int num_missionari) {
		this.num_missionari = num_missionari;
	}

	public int getPos_barca() {
		return pos_barca;
	}

	public void setPos_barca(int pos_barca) {
		this.pos_barca = pos_barca;
	}
	/*
	 * Operatori
	 */
	
	public MCState MC(){
		/*if(getPos_barca()==1 && getNum_cannibali()<=getNum_missionari() && getNum_cannibali()>0 && getNum_missionari()>0){
			setNum_cannibali(getNum_cannibali()-1);
			setNum_missionari(getNum_missionari()-1);
		}
		else if(getPos_barca()==0 && getNum_cannibali()<=getNum_missionari() && getNum_cannibali()<3 && getNum_missionari()<3){
			setNum_cannibali(getNum_cannibali()+1);
			setNum_missionari(getNum_missionari()+1);
		}*/
		if (getPos_barca()==1)
			return new MCState(getNum_cannibali()-1,getNum_missionari()-1, 0);
		else
			return new MCState(getNum_cannibali()+1,getNum_missionari()+1, 1);
	}
	
	public MCState MM(){
		if (getPos_barca()==1)
			return new MCState(getNum_cannibali(),getNum_missionari()-2, 0);
		else
			return new MCState(getNum_cannibali(),getNum_missionari()+2, 1);
	}
	
	public MCState CC(){
		if (getPos_barca()==1)
			return new MCState(getNum_cannibali()-2,getNum_missionari(), 0);
		else
			return new MCState(getNum_cannibali()+2,getNum_missionari(), 1);	
	}
	
	public MCState M(){
		if (getPos_barca()==1)
			return new MCState(getNum_cannibali(),getNum_missionari()-1, 0);
		else
			return new MCState(getNum_cannibali(),getNum_missionari()+1, 1);
	}
	
	public MCState C(){
		if (getPos_barca()==1)
			return new MCState(getNum_cannibali()-1,getNum_missionari(), 0);
		else
			return new MCState(getNum_cannibali()+1,getNum_missionari(), 1);
	}
	
	public boolean isValid(){
		if (	(getNum_missionari() == 0) || 
				(getNum_missionari() ==3 || 	((getNum_missionari() >= getNum_cannibali())) &&
 						(((3-getNum_missionari()) >=
 						(3-getNum_cannibali()))))
 				)
		
 			return true;
 		else {
 			return false;
 		}
	}
	
}
