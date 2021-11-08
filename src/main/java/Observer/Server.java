package Observer;

import Command.PlayerController;
import Command.ShootCommand;
import Command.SkipCommand;
import Command.SurrenderCommand;
import GameObjects.Mine;
import GameObjects.Player;
import java.net.*;
import java.io.*;
import java.util.Random;
public class Server extends Subject  {
   private static Socket s1;
   private static Socket s2;
   private static InputStreamReader in;
   private static BufferedReader bf;
   private static InputStreamReader in2;
   private static BufferedReader bf2;
   private static PrintWriter pr1;
   private static PrintWriter pr2;
   private static Player player1;
   private static Player player2;
   private static final char[][] fleet1 = new char[10][10];
   private static final char[][] fleet2 = new char[10][10];
   private static Subject server = new Server();
   
    public static void main(String[] args) throws IOException{
        
        int playerCount = 1;

         Random random = new Random();
         int island = random.nextInt(6 - 3) + 3;
         int mine = random.nextInt(8 - 3) + 3;
        
        
        ServerSocket ss = new ServerSocket(4000);
        s1 = ss.accept();
        in = new InputStreamReader(s1.getInputStream());
        bf = new BufferedReader(in);
        
        pr1 = new PrintWriter(s1.getOutputStream());
       
        System.out.println("Player " + playerCount + " connected");
        playerCount++;
        pr1.println("connected");
        pr1.flush();
        player1  = new Player(){
            @Override
            public void update(String data) {
            }

            @Override
            public void notifyServer(String result) {
            }

            @Override
            public void setServer(Server server) {
            }
        };
        SetPlayer1(island, mine, fleet1, player1);
        server.attach(player1);
        
        s2 = ss.accept();
       
        in2 = new InputStreamReader(s2.getInputStream());
        bf2 = new BufferedReader(in2);
        System.out.println("Player " + playerCount + " connected");
        
        
        pr2 = new PrintWriter(s2.getOutputStream());
        player2 = new Player(){
            @Override
            public void update(String data) {
            }

            @Override
            public void notifyServer(String result) {
            }

            @Override
            public void setServer(Server server) {
            }     
        };
        SetPlayer2(island, mine, fleet2, player2);
        server.attach(player2);
        pr1.println("opponent connected");
        pr1.flush();
        
        pr2.println("connected");
        pr2.flush();
        pr2.println("opponent connected");
        pr2.flush();
        
        pr2.println("connected");
        pr2.flush();
        bf.readLine();
        System.out.println("Player 1 is ready");
        
        bf2.readLine();
        System.out.println("Player 2 is ready");
       
        
        pr1.println("first");
        pr1.flush();
        pr2.println("second");
        pr2.flush();
        
        System.out.println("Game started");
        while(true){
           ReadPlayer1(bf ,bf2);
           ReadPlayer2(bf2, bf);
        }
        
    }
    public static void ReadPlayer1(BufferedReader bf, BufferedReader bf2) throws IOException{
        String str = bf.readLine();
        System.out.println(str);
        pr2.println(str);
        pr2.flush();
        PlayerController pC = new PlayerController();
        if(str.equals("skip")){
            pC.run(new SkipCommand(player1));
        }
        else if(str.equals("surrender")){
            pC.run(new SurrenderCommand(player1));
        }
        else
            pC.run(new ShootCommand(player1));
        
        if (player1.status.equals("shoot")) {
            String strLetter = CheckHit(str, fleet2, player2);
            System.out.println(strLetter);
            if (strLetter.contains("sink")) {
                pr2.println(strLetter);
                pr2.flush();
                pr1.println(strLetter.split(" ")[1]);
                pr1.flush();
            } else {
                pr1.println(strLetter);
                pr1.flush();
            }
            if (player2.GetHealth() <= 0) {
                pr1.println("win");
                pr1.flush();
                pr2.println("lose");
                pr2.flush();
                server.notifyAll("Game over");
            }
        }
        if(player1.status.equals("skip")){
            pr2.println("skip");
            pr2.flush();
        }
        if(player1.status.equals("surrender")){
            pr2.println("surrender");
            pr2.flush();
            server.notifyAll("Game over");
        }
    }
    
    public static void ReadPlayer2(BufferedReader bf2, BufferedReader bf) throws IOException{
        String str = bf2.readLine();
        System.out.println(str);
        pr1.println(str);
        pr1.flush();
        PlayerController pC = new PlayerController();
        if(str.equals("skip")){
            pC.run(new SkipCommand(player2));
        }
        else if(str.equals("surrender")){
            pC.run(new SurrenderCommand(player2));
        }
        else
            pC.run(new ShootCommand(player2));
        
        if (player2.status.equals("shoot")) {
            String strLetter = CheckHit(str, fleet1, player1);
            System.out.println(strLetter);
            if (strLetter.contains("sink")) {
                pr1.println(strLetter);
                pr1.flush();
                pr2.println(strLetter.split(" ")[1]);
                pr2.flush();
            } else {
                pr2.println(strLetter);
                pr2.flush();
            }
            if (player1.GetHealth() <= 0) {
                pr2.println("win");
                pr2.flush();
                pr1.println("lose");
                pr1.flush();
                server.notifyAll("Game over");
                server.detach(player1);
                server.detach(player2);
            }
        }
        if(player2.status.equals("skip")){
            pr1.println("skip");
            pr1.flush();
        }
        if(player2.status.equals("surrender")){
            pr1.println("surrender");
            pr1.flush();
            server.notifyAll("Game over");
            server.detach(player1);
            server.detach(player2);
        }
    }
    
    //Game logic
        public static String CheckHit(String data, char[][] refBoard, Player player){
        int letter = Integer.parseInt(data.split(" ")[0]);
        int x = Integer.parseInt(data.split(" ")[1]);
        String result = "";

        if (refBoard[letter][x] != 'M' || refBoard[letter][x] != 'H') {
            if (refBoard[letter][x] == '*') {
                refBoard[letter][x] = 'M';
                result = String.valueOf('M');   
            } 
            else if (refBoard[letter][x] != '*' && refBoard[letter][x] != 'H' && refBoard[letter][x] != 'M' && refBoard[letter][x] != 'E' && refBoard[letter][x] != 'I') {
              char ship = checkBoat(refBoard[letter][x], player);
              if(ship!='n')
                result = "sink " + ship;
              else
                result = String.valueOf(refBoard[letter][x]);
                
            } else if (refBoard[letter][x] == 'E') {   
                player.map.MineHit(letter, x);
                result = String.valueOf('E');
            } 
            else if (refBoard[letter][x] == 'I') {
                player.map.DamageIsland(letter, x);
                result = String.valueOf('I');
            }
        }
        return result;
    }
    
    
    public static char checkBoat(char ship, Player player) {
        char deadShip = 'n';
                    switch (ship) {
                        case 'C':
                            player.map.DamageShip(0, 1);
                            System.out.println("Lifes " + player.map.CheckShip(0));
                            if (player.map.CheckShip(0) == 0) {
                                deadShip = 'C';
                            }
                            break;
                        case 'B':
                            player.map.DamageShip(1, 1);
                            if (player.map.CheckShip(1) == 0) {
                                deadShip = 'B';
                            }
                            break;
                        case 'S':
                            player.map.DamageShip(2, 1);
                            if (player.map.CheckShip(2) == 0) {
                                deadShip = 'S';
                            }
                            break;
                        case 'D':
                            player.map.DamageShip(3, 1);
                            if (player.map.CheckShip(3) == 0) {
                                deadShip = 'D';
                            }
                            break;
                        case 'P':
                            player.map.DamageShip(4, 1);
                            if (player.map.CheckShip(4) == 0) {
                                deadShip = 'P';
                            }
                            break;
                    
                }
            return deadShip;
        }
    
    
    //Send map to players
    public static void SetPlayer1(int island, int mine, char[][] fleet1, Player player){
        String[] fleet = BuilFleet(island, mine, fleet1, player);
        pr1.println("setup");
        pr1.flush();
        for (int i = 0; i < 10; i++) {
            pr1.println(fleet[i]);
            pr1.flush();
        }
    }
    public static void SetPlayer2(int island, int mine, char[][] fleet2, Player player){
        String[] fleet = BuilFleet(island, mine, fleet2, player);
        pr2.println("setup");
        pr2.flush();
        for (int i = 0; i < 10; i++) {
            pr2.println(fleet[i]);
            pr2.flush();
        }
    }
    //Ship, Island, Mine generation
    public static void ResetFleet(char[][] fleet){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fleet[i][j] = '*';
            }
        }
    }   
    public static boolean CheckSpace(int size, int direction, char[][] fleet, int x, int y){
        for (int i = 1; i < size; i++) {
          if(direction==0){  
             if(fleet[x+i][y]!='*'){
                 return false;
             }
          }
          if(direction==1){  
             if(fleet[x-i][y]!='*'){
                 return false;
             }
          }
          if(direction==2){  
             if(fleet[x][y+i]!='*'){
                 return false;
             }
          }
          if(direction==3){  
             if(fleet[x][y-i]!='*'){
                 return false;
             }
          }
        }
        
        return true;
    }
    public static int SetDirection(int size, char[][] fleet, int x, int y){
       Random random = new Random();
       int direction;
       int a=0,b=0,c=0,d=0;
       
       while(true){
            direction = random.nextInt(4 - 0) + 0; 
            if(direction==0){
                if(x+size-1<=9)
                    return direction;
                a = 1;
            }
            if(direction==1){
                if(x-size+1>=0)
                    return direction;
                b = 1;
            }
            if(direction==2){
                if(y+size-1<=9)
                    return direction;
                c = 1;
            }
            if(direction==3){
                if(y-size+1>=0)
                    return direction;
                d = 1;
            }
            if(a==1 && b==1 && c==1 && d==1){
                return -1;
            } 
       }
    }   
    public static void GenerateShip(char type, int size, char[][] fleet, Player player){
        int x, y;
        int direction;
        while(true){
            Random random = new Random();
            x = random.nextInt(10 - 0) + 0;
            y = random.nextInt(10 - 0) + 0;       
            if(fleet[x][y]=='*'){
                direction = SetDirection(size, fleet, x,y);         
                if(direction>=0){
                    if(CheckSpace(size, direction, fleet, x, y)==true){
                        fleet[x][y] = type;
                        player.map.AddShipPart(type, x, y);
                        for (int i = 1; i < size; i++) {
                            if(direction==0){
                                fleet[x+i][y] = type;
                                player.map.AddShipPart(type, x, y);
                            }
                            if(direction==1){
                                fleet[x-i][y] = type;
                                player.map.AddShipPart(type, x, y);   
                            }
                            if(direction==2){
                                fleet[x][y+i] = type;
                                player.map.AddShipPart(type, x, y);
                            }
                            if(direction==3){
                                fleet[x][y-i] = type;
                                player.map.AddShipPart(type, x, y);
                            }
                                
                        }
                        break;
                    }   
                }
            }
        }
    }    
    public static void GenerateIsland(int size, char[][] fleet, Player player){
        int x=0, y=0;
        int prevX=0, prevY=0;
        int direction;
        int counter = 0;
        boolean reset = true;
        while(counter<size){
            if(reset){
                Random random = new Random();
                x = random.nextInt(10 - 0) + 0;
                y = random.nextInt(10 - 0) + 0;
                prevX = x;
                prevY = y;
                reset = false;
            }
            if(fleet[x][y]=='*'){
                fleet[x][y]= 'I';
                player.map.island.Add(x, y);
                counter++;
            }
            else if(counter==0){
                reset = true;
            }
            else {
                x = prevX;
                y = prevY;
            }        
            direction = SetDirection(2, fleet, x,y);
                if(direction>=0){
                 if(direction==0){   
                    prevX = x; 
                    x = x+1;
                 }
                 if(direction==1){
                    prevX = x; 
                    x = x-1;
                 }
                 if(direction==2){
                    prevY = y; 
                    y = y+1;
                 }
                 if(direction==3){
                    prevY = y; 
                    y = y-1; 
                 }
                }

        }
        
    }   
    public static void GenerateMines(int amount, char[][] fleet, Player player){
        int counter = 0;
        int x, y;
        
        while(counter<amount){
            Random random = new Random();
            x = random.nextInt(10 - 0) + 0;
            y = random.nextInt(10 - 0) + 0;
            if(fleet[x][y]=='*'){
                fleet[x][y] = 'E';
                Mine mine = new Mine(x,y);
                player.map.mines.add(mine);
                counter++;
            }
        }
        
    }  
    public static String[] BuilFleet(int islandNum, int mineNum, char[][] fleet, Player player){
        
        ResetFleet(fleet);
        GenerateShip('C', 5, fleet, player);
        GenerateShip('B', 4, fleet, player);
        GenerateShip('S', 3, fleet, player);
        GenerateShip('D', 3, fleet, player);
        GenerateShip('P', 2, fleet, player);
        GenerateIsland(islandNum,fleet, player);
        GenerateIsland(islandNum,fleet, player);
        GenerateIsland(islandNum,fleet, player);
        GenerateMines(mineNum, fleet, player);

        String[] fleetString = new String[10];
        for (int i = 0; i < 10; i++) {
            fleetString[i] = "";
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
             if(j<9)   
                fleetString[i] += fleet[i][j] + " ";   
             else if(j==9)
                fleetString[i] += fleet[i][j];  
            }
        }
        return fleetString;
    }
}
