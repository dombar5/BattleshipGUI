import FlyWeight.Texture;
import FlyWeight.TextureFactory;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.geometry.Insets;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;

public class App extends Application {
	
	public static boolean canMove = true;
        public static boolean canMoveGame = false;
        
        //Server com object
        public static Connection connection;
       
        //Text data
        public static char[][] cpuBord = new char[10][10];
	public static char[][] playerBord = new char[10][10];
	public static char[][] cpuBord2 = new char[10][10]; //board with the *
        
        //UI stuff
        public static Label player;
        public static Label cpu;
        public static Label lifeCount;
        public static Label playerText = new Label("Connecting to server...");
	public static Label cpuText = new Label("Waiting for oponent...");   
	public static StackPane label;
        public static Button[][] boardCPU = new Button[10][10];
	public static Button[][] boardPlayer = new Button[10][10];
        public static Button skip;
        public static Button surrender;
        public static TextField playerMsg;
        public static TextArea MsgText;
        public static Button SendMsg;
        public static String textMessage;
        public static String textToDisplay;
        
        //Graphics
        public static Image[] water = new Image[3];
        public static Image dirt = new Image("file:dirt.jpg");
        
        public static int coordX;
        public static int coordY;
        public static int opponentHealth = 17;
        public static String[] data;
        public static TextureFactory factory;
        //public static String[] message;
        public static List<String> list = new ArrayList<String>();


        
    public static void main(String[] args) throws IOException {
        
        connection = new Connection();
        connection.run();
        factory = new TextureFactory();
 
        
                
        launch(args);

    }
    

    //Play field setup
    public static void loadFile(char[][] board) {
       	
        for (int i = 0; i < 10; i++) {
            String[] line = data[i].split(" ");
            for (int j = 0; j < 10; j++) {
                board[i][j] = line[j].charAt(0);
                }
            }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Texture t = factory.getTexture("file:water2.jpg");
                t.draw(boardCPU[i][j]);
                
            }
        }
        
            }

	public static void makeButton(GridPane buttons, Button[][] buttonArray, char[][] textBoard, Label words, Button[][] buttonArray2, char[][] textBoard2, Label words2, int identifier) {
        int y = 1;
        for (int row = 0; row < buttonArray.length; row++) {
	        for (int col = 0; col < buttonArray[row].length; col++) {
				buttonArray[row][col] = new Button("");
                                
      
				buttonArray[row][col].setDisable(true);
				 int x1 = row;
				 int y1 = col;
				if (identifier == 0) {
				 	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
		            public void handle(ActionEvent e) 
			            {
			            	if (canMove) try {
                                            playerMove(textBoard, words, textBoard2, x1, y1);
                                            } catch (IOException ex) {
                                                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                            }	
                                    }
			        };
			        buttonArray[row][col].setOnAction(event);
	       		}
			buttonArray[row][col].setStyle("-fx-background-radius: 0");
			buttonArray[row][col].setMinHeight(43);
		        buttonArray[row][col].setMaxHeight(43);
		        buttonArray[row][col].setMinWidth(43);
		        buttonArray[row][col].setMaxWidth(43);
                        
		        buttons.add(buttonArray[row][col], col, y);
		        if (col == 9) y++;
        	}
		}
	}
	
	public static void playerMove(char[][] refBoard, Label text, char[][] cpuTxt, int x, int y) throws IOException {
            connection.SendData(x + " " + y ); 
            coordX = x;
            coordY = y;
            DisableButtons(true);
	}
	
	public static void cpuMove(char[][] refBoard, Label text, int letter, int x) throws IOException {
                        
			int asciiLetter = letter + 65; //+ 65 to get actual ascii value
	
			if (refBoard[letter][x] != 'M' || refBoard[letter][x] != 'H') {
				if (refBoard[letter][x] == '*') {
					refBoard[letter][x] = 'M';
					boardPlayer[letter][x].setStyle("-fx-base: #99ff66;");
					SetText(text, "The oponent has attacked " + (char) asciiLetter + x + " and missed!");
					boardPlayer[letter][x].setDisable(true);
                                        
				} else if (refBoard[letter][x] != '*' && refBoard[letter][x] != 'H' && refBoard[letter][x] != 'M' && refBoard[letter][x]!='E' && refBoard[letter][x]!='I') {
					String boatHit = (refBoard[letter][x] == 'C' ? "Carrier!" : refBoard[letter][x] == 'B' ? "Battleship!" : refBoard[letter][x] == 'S' ? "Submarine!" : refBoard[letter][x] == 'D' ? "Destroyer!" : "Patrol Boat!");
					SetText(text, "The oponent has attacked " + (char) asciiLetter + x + " and hit your " + boatHit + "\n"); // get what ship the computer hit
					boardPlayer[letter][x].setStyle("-fx-base: #ff6666;");
					boardPlayer[letter][x].setDisable(true);

				}
                                 else if(refBoard[letter][x]=='E'){
                                    SetText(text, "The oponent has attacked " + (char) asciiLetter + x + " and hit your Mine! " + "\n");
                                   // map.MineHit(letter, x);
                                    boardPlayer[letter][x].setStyle("-fx-base: #ff6666;");
                                    refBoard[letter][x] = 'H';
                                }
                                 else if(refBoard[letter][x]=='I'){
                                    SetText(text, "The oponent has attacked " + (char) asciiLetter + x + " and hit your Island! " + "\n");
                                    //map.DamageIsland(letter, x);
                                    boardPlayer[letter][x].setStyle("-fx-base: #ff6666;");
                                    refBoard[letter][x] = 'H';
                                }
			}
                        DisableButtons(false);
		
	}
	
	public static void checkBoat(char letter) {
		
            switch (letter) {
                case 'C':
                    SetText(cpuText, "The oponent has sunk your " + "Carrier!");
                    break;
                case 'B':
                    SetText(cpuText, "The oponent has sunk your " + "Battleship!");
                    break;
                case 'S':
                    SetText(cpuText, "The oponent has sunk your " + "Submarine!");
                    break;
                case 'D':
                    SetText(cpuText, "The oponent has sunk your " + "Destroyer!");
                    break;
                case 'P':
                    SetText(cpuText, "The oponent has sunk your " + "Patrol Boat!");
                    break;
            }
    }
			
		
	

        public static void whenWin(String result) {
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
                canMove = false;
		Stage newWindow = new Stage();
	        newWindow.setTitle("Message");
                String text = "";
                Label label;
                if(result.equals("win"))
	           text = "You won!";
                if(result.equals("lose"))
                   text = "You lost!";
                if(result.equals("surrender"))
                   text = "Opponent surrendered!";
                
                
                label = new Label(text);
	        Button okBtn = new Button("OK");
	        label.setPadding(new Insets(10, 10, 10, 10));
	        BorderPane layout = new BorderPane();
	        layout.setCenter(label);
	        layout.setBottom(okBtn);
	        layout.setAlignment(okBtn, Pos.BOTTOM_CENTER);
	        Scene secondScene = new Scene(layout, 230, 70);
	        
			for (int row = 0; row < boardCPU.length; row++) {
				for (int col = 0; col < boardCPU[row].length; col++) {
					boardCPU[row][col].setStyle("");
					boardPlayer[row][col].setStyle("");
					boardCPU[row][col].setDisable(true);
					boardPlayer[row][col].setDisable(true);
				}
			}
			
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
				public void handle(ActionEvent e) 
		        {
	    				newWindow.close();
				}
			};
		
			okBtn.setOnAction(event);
	        newWindow.setScene(secondScene);
	        newWindow.show();
	}
        });
        }
	

        @Override
	public void start(Stage primaryStage) throws IOException   {

	primaryStage.setTitle("Battleship GUI");
	GridPane mainPane = new GridPane();
        Scene scene = new Scene(mainPane, 980, 650);    
//////////////////  Pictures  //////////////////
	Label rows = new Label("");
        Label cols = new Label("");
        Label rows2 = new Label("");
        Label cols2 = new Label("");
        rows.setGraphic(new ImageView(new Image("file:rows.png", 44.0, 0.0, true, true)));
        rows2.setGraphic(new ImageView(new Image("file:rows.png", 44.0, 0.0, true, true)));
        cols.setGraphic(new ImageView(new Image("file:cols.png", 430.0, 0.0, true, true)));
        cols2.setGraphic(new ImageView(new Image("file:cols.png", 430.0, 0.0, true, true)));
        rows2.setPadding(new Insets(0, 0, 10, 15));
        HBox hbox = new HBox(70, cols);
        HBox hbox2 = new HBox(70, cols2);
        VBox vbox = new VBox(70, rows);
        VBox vbox2 = new VBox(70, rows2);
        
//////////////////  Text msgs  //////////////////
        player = new Label("Your message:");
	player.setStyle("-fx-font: 32 arial; -fx-font-weight: bold; -fx-text-fill: #7070db;");
	player.setPadding(new Insets(0, 0, 6, 0));
        cpu = new Label("Oponent's message:");
        cpu.setStyle("-fx-font: 32 arial; -fx-font-weight: bold; -fx-text-fill: #ff5050");
        cpu.setPadding(new Insets(0, 0, 6, 0));
        
	playerText.setStyle("-fx-font: 16.5 arial; -fx-text-fill: #7070db");
	
	cpuText.setStyle("-fx-font: 16.5 arial; -fx-text-fill: #ff5050");
	label = new StackPane(player);
        
        lifeCount = new Label("Lifes: 17");
        lifeCount.setStyle("-fx-font: 15 arial; -fx-font-weight: bold; -fx-text-fill: #21211f;");
	lifeCount.setPadding(new Insets(0, 0, 0, 0));
                
//////////////////  Buttons  //////////////////
	GridPane buttonsGrid_player = new GridPane();
	GridPane buttonsGrid_cpu = new GridPane();
	makeButton(buttonsGrid_cpu, boardPlayer, playerBord, cpuText, boardCPU,  cpuBord, playerText, 1); //Player board
	makeButton(buttonsGrid_player, boardCPU,  cpuBord, playerText, boardPlayer, playerBord, cpuText, 0); //CPU board
        
        skip = new Button("Skip");
        surrender = new Button("Surrender");
        playerMsg = new TextField();
        MsgText = new TextArea();
        MsgText.setEditable(false);
        SendMsg = new Button("Send");
        


            EventHandler<ActionEvent> skipEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                   connection.SendData("skip");
                   playerText.setText("You skipped!");
                   DisableButtons(true);
                }
            };
            EventHandler<ActionEvent> surrenderEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                   connection.SendData("surrender");
                   playerText.setText("Loser...");
                   DisableButtons(true);
                   whenWin("lose");
                }
            };
            EventHandler<ActionEvent> messageEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                   connection.SendData("msg$" + playerMsg.getText());
                   SendMsg.setDisable(true);
                }
            };
        skip.setOnAction(skipEvent);
        surrender.setOnAction(surrenderEvent);
        SendMsg.setOnAction(messageEvent);
        
        loadFile(playerBord);
        connection.SendData("loaded");
        int times = 0;
        for (int row = 0; row < boardPlayer.length; row++) {
             for (int col = 0; col < boardPlayer[row].length; col++) {
                boardPlayer[row][col].setText(" " + playerBord[row][col] + " ");
                if (times == 2) {
                 boardPlayer[row][col].setDisable(false);
                 boardCPU[row][col].setDisable(false);
                }
             }	
        }
        for (int row = 0; row < boardPlayer.length; row++) {
            for (int col = 0; col < boardPlayer[row].length; col++) {
                if(playerBord[row][col]=='*'){
                   boardPlayer[row][col].setText("");
                   Texture t = factory.getTexture("file:water1.jpg");
                   t.draw(boardPlayer[row][col]);    
                }
                else
                    boardPlayer[row][col].setText(" " + playerBord[row][col] + " ");
                if(playerBord[row][col]!='I' && playerBord[row][col]!='*' && playerBord[row][col]!='E')
                    boardPlayer[row][col].setStyle("-fx-base: #ffe23b");
                else if(playerBord[row][col]=='I'){
                   boardPlayer[row][col].setText("");
                    Texture t = factory.getTexture("file:dirt.jpg");
                    t.draw(boardPlayer[row][col]); 
                }
                else if(playerBord[row][col]=='E')
                    boardPlayer[row][col].setStyle("-fx-base: #ff38e8");
                else if(playerBord[row][col]=='*')
                    boardPlayer[row][col].setStyle("-fx-base: #85fbff");
                                

                  
                boardPlayer[row][col].setDisable(false);
                boardCPU[row][col].setStyle("-fx-base: #85fbff");
                boardCPU[row][col].setDisable(true);
                                
            }
        }
       	
       
                 
        MenuBar mb = new MenuBar();
        Menu m = new Menu("File"); 
        m.setStyle("-fx-padding: 4 4 4 4");
        mb.getMenus().add(m); 
//////////////////  Adding stuff to mainpane  //////////////////
        mainPane.add(mb, 0, 0);
        mainPane.add(vbox, 0, 2);
        mainPane.add(vbox2, 2, 2);
        mainPane.add(hbox, 1, 1);
        mainPane.add(hbox2, 3, 1);
     	mainPane.add(buttonsGrid_player, 3, 2);
        mainPane.add(buttonsGrid_cpu, 1, 2);
        mainPane.add(player, 1, 3);
        mainPane.add(playerText, 1, 4);
        mainPane.add(cpu, 3, 3);
        mainPane.add(cpuText, 3, 4);
        mainPane.add(skip, 2, 5);
        mainPane.add(surrender, 2, 6);
        mainPane.add(SendMsg, 2, 4);
        mainPane.add(lifeCount, 1,0);
        mainPane.add(playerMsg, 1, 4);
        mainPane.add(MsgText, 1, 5);
        primaryStage.setScene(scene);
        primaryStage.show();
         
    }
    
    public static void DisableButtons(boolean set){
        for (int row = 0; row < boardCPU.length; row++) {
            for (int col = 0; col < boardCPU[row].length; col++) {
                boardCPU[row][col].setDisable(set);     
                if(cpuBord[row][col]=='H' || cpuBord[row][col]=='M')
                   boardCPU[row][col].setDisable(true);   
            }
        }
        skip.setDisable(set);
        surrender.setDisable(set);
        SendMsg.setDisable(set);
    }

    public static void SetText(Label label, String text){
       Platform.runLater(new Runnable() {
        @Override
        public void run() {
          label.setText(text);
        }
        });  
    }

    public static void SetTextArea(TextArea area, String text){
       Platform.runLater(new Runnable() {
        @Override
        public void run() {
          area.setText(text);
        }
        });  
    }        
    
    //Server comunication stuff
    public static void GameStart(boolean first) throws IOException{
         
      System.out.println("start");
        if(first){
          canMoveGame = true;
          DisableButtons(false); 
        }
        else{
          canMoveGame = false;
          DisableButtons(true); 
        }
 
    }
    
    public static void SetHealth(int health){
        SetText(lifeCount, "Health " + health);
    }
    
    public static void RecieveFleet(String[] fleet){
        data = fleet;
    }
    
    public static void OpponentConnected(){
        SetText(cpuText, "Opponent connected!");
    }
    
    public static void OpponentSkip(){
        SetText(cpuText, "Opponent skipped the move!");
        DisableButtons(false);
    }
    
    public static void ReadMsg(String data){
        String msg = data.split("\\$")[1];
        list.add(msg);
        msg = "";
        for (String e : list) {
            msg += e + System.lineSeparator();
        }
        SetTextArea(MsgText, msg);
        System.out.println(list); 
    }
    
    public static void ConnectedToServer(){
        SetText(playerText, "Connection successful!");
    }
    
    public static void PlayerResult(String letter){
		if (letter.charAt(0)=='M') {
			cpuBord[coordX][coordY] = 'M';
			SetText(playerText, "You have missed sir!");
			boardCPU[coordX][coordY].setStyle("-fx-base: #ff6666;");
		}
                else if (letter.charAt(0)=='I') {
                        cpuBord[coordX][coordY] = 'H';
			SetText(playerText, "You hit an island sir!");
			boardCPU[coordX][coordY].setStyle("-fx-base: #99ff66;");
		}
                else if (letter.charAt(0)=='E') {
                        cpuBord[coordX][coordY] = 'H';
			SetText(playerText, "Ouch! You hit a mine...");
			boardCPU[coordX][coordY].setStyle("-fx-base: #99ff66;");
		}
                else if (!letter.equals('*') && !letter.equals('M') && !letter.equals('I') && !letter.equals('E')) {
                        cpuBord[coordX][coordY] = letter.charAt(0);
			SetText(playerText, "Direct hit, nice shot sir!");
                        cpuBord[coordX][coordY] = 'H';
			boardCPU[coordX][coordY].setStyle("-fx-base: #99ff66;");	
		}
                
    }
    
    public static void EnemyResults(String data) throws IOException{
        int letter = Integer.parseInt(data.split(" ")[0]);
        int x = Integer.parseInt(data.split(" ")[1]);
        cpuMove(playerBord, cpuText, letter, x );
    }
       
}