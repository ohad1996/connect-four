import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class MyProjectController {
    @FXML
    private GridPane grid;
    
    @FXML
    private Label labe;
    
    private int noOfCircles = 0;
    private final int RADIUS = 22;
    private final int DEFAULT_CORDX = 20;
    private final int DEFAULT_CORDY = 20;
    private final int REACHED_TO_TOP = -1;
    
    private final int NUM_OF_BTNS = 7;
    
    private boolean player1Turn = true;
    private boolean finishGame = false;
    
    private String player1;
    private String player2;
    
    private int[][] game = new int[6][7];
    private Button[] gamePlay = new Button[NUM_OF_BTNS];
    
    public void initialize() {
    	createBtns();
    	//receives the names of the players
    	player1 = JOptionPane.showInputDialog("Welcome to the game four in a row!\nPlease enter Player's 1 name: \n");
    	player2 = JOptionPane.showInputDialog("Please enter Player's 2 name: \n");
   	
    }
    
	private void createBtns() {
		//creating the buttons
		for(int i =0 ;i<NUM_OF_BTNS;i++) {
    		gamePlay[i] = new Button((i+1)+"");
    		gamePlay[i].setPrefSize(grid.getPrefWidth(), grid.getPrefHeight());
    		grid.add(gamePlay[i], i ,6);
    	
    		gamePlay[i].setOnAction(new EventHandler <ActionEvent>() {
					@Override
					public void handle(ActionEvent event ) {
						buttonOnAction(event);
					}
    		});
    	}
	}  
	
	private void buttonOnAction(ActionEvent event) {
		//creating the button's function
		Button temp = (Button)event.getSource();
		int btnNum = Integer.parseInt(temp.getText());
		if(finishGame == false) 
    		winnerCheckOut(checkAndCreate(btnNum-1),btnNum-1);
	}
    
    public void winnerCheckOut(int i,int j) {
    	//this method checks if the last player who played won the game.
    	
    	//this if is incase we reached to the top which will block the method to enter a circle to the chosen column
    	if(i != REACHED_TO_TOP) {
    		//here we check if the player won by a column
    		int row=i,col = j;
    		if(game.length - (i+1) +1>=4) {
    			if(game[row][col] == game[row+1][col] && game[row+2][col] == game[row+3][col]
    			&& game[row][col] == game[row+3][col] && game[row][col]!=0){
    				winnerRow(row,col,row+1,col,row+2,col,row+3,col);
    				return ;
    			}
    		}
    		
    		//here we check if the player won by a row
    		row=i;col = 0;
    		while(col+3<game[0].length) {
    			if(game[row][col] == game[row][col+1] && game[row][col+2] == game[row][col+3] 
    		    && game[row][col] == game[row][col+3] && game[row][col]!=0){
    				winnerRow(row,col,row,col+1,row,col+2,row,col+3);
    				return ;
    			}
    			col++;
    		}
    		
    		//here we check if the player won by a slant from the left side
    		row=i;col = j;
    		while(row>0 && col>0) {
    		row--;col--;
    		}
    		while(row+3<game.length && col+3<game.length){
    			if(game[row][col] == game[row+1][col+1] && game[row+2][col+2]==game[row+3][col+3]
    			&& game[row][col] == game[row+3][col+3] && game[row][col]!=0){
    				winnerRow(row,col,row+1,col+1,row+2,col+2,row+3,col+3);
    				return ;
    			}
    			row++;col++;
    		}
    		
    		//here we check if the player won by a slant from the right side
    		row=i;col = j;
    		while(row>0 && col<game.length-1) {
    			row--;col++;
    		}
    		while(row+3<game.length && col-3>=0){
    			if(game[row][col] == game[row+1][col-1] && game[row+2][col-2] == game[row+3][col-3]
    			&& game[row][col] == game[row+3][col-3] && game[row][col]!=0){
    				winnerRow(row,col,row+1,col-1,row+2,col-2,row+3,col-3);
    				return ;
    			}
    			row++;col--;
    		}
    		//incase there is a chance for a tie!
    		row =0;col =0;
    		boolean draw = true;
    		for(;row<game.length;row++ ) {
    			for(;col<game[0].length;col++) {
    				//incase there is an empty spot to play at :)
    				if(game[row][col] == 0)
    					draw = false;
    			}
    		}
    		if(draw==true) {
    			labe.setTextFill(Color.BLACK);
     			labe.setText("Its a tie! lol");
    		}
    	}
    }
    
    private void winnerRow(int i1,int j1,int i2,int j2,int i3,int j3,int i4,int j4 ) {
    	//this method marks only one of the ways that our player won the game. 
    	Color fill = Color.GOLD;
		Circle c1 = new Circle(DEFAULT_CORDX,DEFAULT_CORDY,RADIUS-10, fill);
		Circle c2 = new Circle(DEFAULT_CORDX,DEFAULT_CORDY,RADIUS-10, fill);
		Circle c3 = new Circle(DEFAULT_CORDX,DEFAULT_CORDY,RADIUS-10, fill);
		Circle c4 = new Circle(DEFAULT_CORDX,DEFAULT_CORDY,RADIUS-10, fill);
		grid.add(c1,j1,i1);
		grid.add(c2,j2,i2);
		grid.add(c3,j3,i3);
		grid.add(c4,j4,i4);
		noOfCircles+=4;
		//announcement of the winner!
		whoWon();
    }
    
    public void whoWon() {
    	if(!player1Turn) {
 			labe.setTextFill(Color.BLUE);
 			labe.setText("The winner is :  "+player1+" !!! :)");
 		}
 		else if(player1Turn) {
 			labe.setTextFill(Color.RED);
 			labe.setText("The winner is :  "+player2+" !!! :)");
 		}
    	//can not continue the game until we clear the game.
    	finishGame = true; 
    }
	
    public void player1Circle(int i, int j) {
    	//places the circle of the player 1 in the requested spot
    	game[i][j]=1;
		Color fill = Color.BLUE;
		Circle c = new Circle(DEFAULT_CORDX,DEFAULT_CORDY,RADIUS, fill);
		grid.add(c,j,i);
		noOfCircles++;
    }
    
    public void player2Circle(int i, int j) {
    	//places the circle of the player 2 in the requested spot
    	game[i][j]=2;
    	Color fill = Color.RED;
    	Circle c = new Circle(DEFAULT_CORDX,DEFAULT_CORDY,RADIUS, fill);
    	grid.add(c,j,i);
    	noOfCircles++;
    }
    
    public int checkAndCreate(int j) {
    	//entering a circle to the column is avilable only if there are less than 
    	//6 circles!
    	if(game[0][j] == 0) {
    		for(int i = game.length-1; i >= 0;i--) {
    			if(game[i][j] == 0 ) {
    				if(player1Turn) {
    					player1Turn = false;
    					player1Circle(i,j);
    					return i;
    				}
    				else {
    					player1Turn = true;
    					player2Circle(i,j);
    					return i;
    				}
    			}
    		}
    	}
    	return REACHED_TO_TOP;
    }
	
    @FXML
    void clearPress(ActionEvent event) {
    	//starting a new game - resets the game the way it was at the beginning!
    		game = new int[6][7];
    		for (int i = 0; i < noOfCircles; i++)
    			grid.getChildren().remove(grid.getChildren().size() - 1);
    		noOfCircles = 0;
    		finishGame = false;
    		player1Turn = true;
    		labe.setTextFill(Color.BLACK);
 			labe.setText("FOUR IN A ROW");
    	}	 
}
