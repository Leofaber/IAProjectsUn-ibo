package com.aima.ttt;


public class TTTPlayer {

	public String name;
	public String sign;
	
	private static TTTPlayer p1;
	private static TTTPlayer p2;
	
	private TTTPlayer(String n, String s){
		name = n;
		sign = s;
	}
	
	public static TTTPlayer getFirstPlayer(){
		if(p1 == null){
			return new TTTPlayer("Leo","X");
		}else{
			return p1;	
		}
		
	}
	
	public static TTTPlayer getSecondPlayer(){
		if(p2 == null){
			return new TTTPlayer("Cala","O");
		}else{
			return p2;	
		}
		
	}
	
	public static Object[] getPlayers(){
		Object[] playersList = new Object[2];
		if(p1 == null & p2 == null){
			TTTPlayer.p1 = new TTTPlayer("Leo","X");
			TTTPlayer.p2 = new TTTPlayer("Cala","O");
			playersList[0] = (Object) p1;
			playersList[1] = (Object) p2;
			return playersList;
		}else{
			playersList[0] = (Object) TTTPlayer.p1;
			playersList[1] = (Object) TTTPlayer.p2;
			return playersList;
		}
	}
	/*
	public static List<TTTPlayer> getPlayers(){
		List<TTTPlayer> playersList = new ArrayList<TTTPlayer>();
		if(p1 == null & p2 == null){
			p1 = new TTTPlayer("Leo","X");
			p2 = new TTTPlayer("Cala","O");
			playersList.add(p1);
			playersList.add(p2);
			return playersList;
		}else{
			playersList.add(p1);
			playersList.add(p2);
			return playersList;
		}
	}
	*/
	@Override
	public String toString(){
		return "Name: "+name+" Sign: "+sign;
	}
}
