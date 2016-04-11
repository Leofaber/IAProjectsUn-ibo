package com.aima.ftt.components;

import java.util.Random;

public class Map {
	  private static Map worldMap;

	  public static   int maxWidth;
	  public static   int maxHeight;
	   
	  public static MapObject map[][];

	  public Treasure treasure;
	  
	  private Map(boolean random){
		  	if(! random){
		  	maxWidth = 5;
		  	maxHeight = 5;
		  	treasure = new Treasure(3,4);
 		    this.map = new MapObject[maxWidth][maxHeight];
		    this.map[0][0] = new FreeCell(0,0);
		    this.map[1][0] = new FreeCell(1,0);
			this.map[2][0] = new FreeCell(2,0);
			this.map[3][0] = new FreeCell(3,0);
			this.map[4][0] = new FreeCell(4,0);
			this.map[0][1] = new FreeCell(0,1);
			this.map[1][1] = new Pit(1,1);
			this.map[2][1] = new FreeCell(2,1);
			this.map[3][1] = new Pit(3,1);
			this.map[4][1] = new FreeCell(3,1);
			this.map[0][2] = new FreeCell(0,2);
			this.map[1][2] = new Pit(1,2);
			this.map[2][2] = new FreeCell(2,2);
			this.map[3][2] = new FreeCell(3,2);
			this.map[4][2] = new FreeCell(4,2);
			this.map[0][3] = new FreeCell(0,3);
			this.map[1][3] = new FreeCell(1,3);
			this.map[2][3] = new FreeCell(2,3);
			this.map[3][3] = new Pit(3,3);
			this.map[4][3] = new FreeCell(4,3);
			this.map[0][4] = new FreeCell(0,4);
			this.map[1][4] = new FreeCell(1,4);
			this.map[2][4] = new FreeCell(2,4);
			this.map[3][4] = treasure;
			this.map[4][4] = new FreeCell(4,4);
			
		  	}
		  	else{
		  		
		  		
		  		maxWidth = 12;
			  	maxHeight = 12;
			  	this.map = new MapObject[maxWidth][maxHeight];
			  	
			  	Random rand = new Random();
			  	
			  	int r = rand.nextInt(maxWidth-1 - 0) + 0;
			  	int c = rand.nextInt(maxHeight-1 - 0) + 0;

			  	System.out.println("Tresoure r:"+r+" c:"+c);
			  	treasure = new Treasure(r,c);
			  	
			    int x,y;
				for(x=0;x<Map.maxHeight;x++){
					for(y=0;y<Map.maxWidth;y++){
						   int talka = rand.nextInt(4-0) + 0;
 					        switch(talka){

					        case 0:
					        {
					        	map[x][y] = new FreeCell(x,y);
					            break;
					        }
					        case 1:
					        {
					        	map[x][y] = new Pit(x,y);
					            break;
					        }
					        case 2:
					        {
					        	map[x][y] = new FreeCell(x,y);
					            break;
					        }
 					        case 3:
 					        {
 					        	map[x][y] = new FreeCell(x,y);
					            break;
 					        }
 					        case 4:
 					        {
 					        	map[x][y] = new FreeCell(x,y);
					            break;
 					        }
 					        
 					       }
					}
				}
				map[r][c] = treasure;
		     
			  	
			  	
		  	}

	  }
	  
	  

	  public static Map getInstance(){
	    if (worldMap == null){
	    	worldMap = new Map(false);
	    }

	    return worldMap; 
	  }
	  
	  public static Map getRandomedInstance(){
		  if (worldMap == null){
		   	worldMap = new Map(true);
		   }
  	    return worldMap; 
	  }
	  
	  public static boolean isPit(int row, int col){
  		  if(row < 0 || row > maxHeight-1 || col < 0 || col > maxWidth-1){
			  return false;
		  }
		  if(map[row][col] instanceof Pit){
			  return true;
		  }
		  return false;
	  }
	  
	  public Coords getTresoureCoords(){
		  return new Coords(this.treasure.rowPosition,this.treasure.columnPosition);
	  }
}
