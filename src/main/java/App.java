import FlyWeight.Texture;
import FlyWeight.TextureFactory;
import Interpreter.*;
import Visitor.*;
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
        public static Button undo;
        public static int undoCount = 0;
        
        public static Button cannon;
        public static Button rifle;
        public static Button rocket;
        public static Label weaponSelect;

        public static Label accuracy;
        public static String weapon = "Rifle";

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

        public static float acc = 1;


        
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
            VisitorDamage damage;
            int dmg = 1;
            if(Integer.parseInt(lifeCount.getText().split(" ")[1])<=7){
                damage = new LifesLow();
                System.out.println("this " +  Integer.parseInt(lifeCount.getText().split(" ")[1]));
            }
            else{
                damage = new LifesOk();
                System.out.println("this "  + Integer.parseInt(lifeCount.getText().split(" ")[1]));
            }
            if(weapon.equals("Rifle")){
                dmg = damage.calculateDamage(new Rifle());
            }
            if(weapon.equals("Cannon")){
                dmg = damage.calculateDamage(new Cannon());
            }
            if(weapon.equals("Rocket")){
                dmg = damage.calculateDamage(new Rocket());
            }
            
            connection.SendData(x + " " + y + " " + dmg); 
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
        accuracy = new Label("Accuracy: 1");
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
        
        rifle = new Button();
        cannon = new Button();      
        rocket = new Button();
        weaponSelect = new Label("Rifle selected");
        
        rocket.setText("RO");
        rifle.setText("RI");
        cannon.setText("CA");
        rifle.setMinHeight(43);
        rifle.setMaxHeight(43);
        rifle.setMinWidth(43);
	rifle.setMaxWidth(43);
        
        cannon.setMinHeight(43);
        cannon.setMaxHeight(43);
        cannon.setMinWidth(43);
	cannon.setMaxWidth(43);
        
        rocket.setMinHeight(43);
        rocket.setMaxHeight(43);
        rocket.setMinWidth(43);
	rocket.setMaxWidth(43);
        
        skip = new Button("Skip");
        surrender = new Button("Surrender");
        playerMsg = new TextField();
        MsgText = new TextArea();
        MsgText.setEditable(false);
        SendMsg = new Button("Send");
        undo = new Button("Undo");
        
        


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
            EventHandler<ActionEvent> undoEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    connection.SendData("undo");
                    undoCount++;
                    //undo.setDisable(true);
                }
            };
            
            EventHandler<ActionEvent> Rifle = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    SetText(weaponSelect, "Rifle selected!");
                    weapon = "Rifle";
                }
            };
            EventHandler<ActionEvent> Cannon = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    SetText(weaponSelect, "Cannon selected!");
                    weapon = "Cannon";
                }
            };
            EventHandler<ActionEvent> Rocket = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                   SetText(weaponSelect, "Rocket selected!");
                    weapon = "Rocket";
                }
            };
        skip.setOnAction(skipEvent);
        surrender.setOnAction(surrenderEvent);
        SendMsg.setOnAction(messageEvent);
        undo.setOnAction(undoEvent);
        rifle.setOnAction(Rifle);
        cannon.setOnAction(Cannon);
        rocket.setOnAction(Rocket);
        accuracy.setStyle("-fx-font: 20 arial; -fx-text-fill: #000000");
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
        mainPane.add(undo, 2, 3);
        mainPane.add(lifeCount, 1,0);
        mainPane.add(cannon, 2, 8);
        mainPane.add(rifle, 3, 8);
        mainPane.add(rocket, 2, 9);
        mainPane.add(weaponSelect, 3, 9);

        mainPane.add(playerMsg, 1, 4);
        mainPane.add(MsgText, 1, 5);

        mainPane.add(accuracy, 1,6);

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
        if(undoCount < 3) {
            undo.setDisable(set);
        }
        else {
            undo.setDisable(true);
        }
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
    
    public static void SetTextField(TextField area, String text){
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
        String d = data.split("\\$")[0];
        String msg = data.split("\\$")[1];
        String who = d.split(" ")[0];
        //String mess = "";
        
        if(who.equals("me")) {
            msg = "me: " + msg;
        }
        else if(who.equals("enemy")) {
            msg = "enemy: " + msg;
        }
        list.add(msg);
        msg = "";
        for (String e : list) {
            msg += e + System.lineSeparator();
        }
        SetTextArea(MsgText, msg);
        SetTextField(playerMsg, "");
    }
    
    public static void getUndo(String coords, String letter) {
        int row = Integer.parseInt(coords.split(" ")[0]);
        int col = Integer.parseInt(coords.split(" ")[1]);
        
             boardPlayer[row][col].setStyle("-fx-base: #ffe23b");
             boardPlayer[row][col].setDisable(false); 
             cpuBord[row][col] = letter.charAt(0);
             //playerBord[row][col] = letter.charAt(0);
             //boardCPU[row][col].setDisable(false); 
             
        
    }
    
    public static void ConnectedToServer(){
        SetText(playerText, "Connection successful!");
    }
    
    public static void PlayerResult(String letter){
		if (letter.charAt(0)=='M') {
			cpuBord[coordX][coordY] = 'M';
			SetText(playerText, "You have missed sir!");
			boardCPU[coordX][coordY].setStyle("-fx-base: #ff6666;");
                        Expression e1 = new Numberr(acc);
                        Expression e2 = new Numberr(2);
                        Expression div = new Divide(e1, e2);
                        acc = div.execute();
                        accuracy.setStyle("-fx-font: 20 arial; -fx-text-fill: #ff0000");
                        SetText(accuracy, "Accuracy: " + acc );
		}
                else if (letter.charAt(0)=='I') {
                        cpuBord[coordX][coordY] = 'H';
			SetText(playerText, "You hit an island sir!");
			boardCPU[coordX][coordY].setStyle("-fx-base: #99ff66;");
                        Expression e1 = new Numberr(acc);
                        Expression e2 = new Numberr(2);
                        Expression div = new Multiply(e1, e2);
                        accuracy.setStyle("-fx-font: 20 arial; -fx-text-fill: #22ff00");
                        acc = div.execute();
                        SetText(accuracy, "Accuracy: " + acc);
		}
                else if (letter.charAt(0)=='E') {
                        cpuBord[coordX][coordY] = 'H';
			SetText(playerText, "Ouch! You hit a mine...");
			boardCPU[coordX][coordY].setStyle("-fx-base: #99ff66;");
                        Expression e1 = new Numberr(acc);
                        Expression e2 = new Numberr(2);
                        Expression div = new Divide(e1, e2);
                        accuracy.setStyle("-fx-font: 20 arial; -fx-text-fill: #ff0000");
                        acc = div.execute();
                        SetText(accuracy, "Accuracy: " + acc);
		}
                else if (!letter.equals('*') && !letter.equals('M') && !letter.equals('I') && !letter.equals('E')) {
                        cpuBord[coordX][coordY] = letter.charAt(0);
			SetText(playerText, "Direct hit, nice shot sir!");
                        cpuBord[coordX][coordY] = 'H';
			boardCPU[coordX][coordY].setStyle("-fx-base: #99ff66;");	
                        Expression e1 = new Numberr(acc);
                        Expression e2 = new Numberr(2);
                        Expression div = new Multiply(e1, e2);
                        accuracy.setStyle("-fx-font: 20 arial; -fx-text-fill: #22ff00");
                        acc = div.execute();
                        SetText(accuracy, "Accuracy: " + acc);
		}
                
    }
    
    public static void EnemyResults(String data) throws IOException{
        int letter = Integer.parseInt(data.split(" ")[0]);
        int x = Integer.parseInt(data.split(" ")[1]);
        cpuMove(playerBord, cpuText, letter, x );
    }
       
}