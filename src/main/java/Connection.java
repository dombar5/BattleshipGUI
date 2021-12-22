
import GameObjects.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Connection 
    implements Runnable
{
    public Player player;
    public boolean up;
    String data = "";
    public Socket s;
    public PrintWriter pr;
    private InputStreamReader in;
    private BufferedReader bf;
        
    public Connection() throws IOException{
        s = new Socket("localhost", 4000);
        pr = new PrintWriter(s.getOutputStream());
        in = new InputStreamReader(s.getInputStream());
        bf = new BufferedReader(in);
    }

    public void SendData(String data){
         pr.println(data);
         pr.flush();
    }

    @Override
    public void run() {
        Thread t = new Thread() {
        public void run() {
    

            while(true) {
                try {
                    data = bf.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(data);
                if(!data.equals("")){
                    if(data.equals("connected"))
                       App.ConnectedToServer();
                    if(data.equals("opponent connected"))
                       App.OpponentConnected();
                    if(data.equals("first")){
                       try {
                           App.GameStart(true);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    if(data.equals("second")){
                       try {
                           App.GameStart(false);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    if(data.length()>2 && data.length()<4){
                        try {
                            App.EnemyResults(data);
                        } catch (IOException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if(data.length()==1){
                        App.PlayerResult(data);
                    }
                    if(data.equals("setup")){
                        String[] fleet = new String[10];
                        for (int i = 0; i < 10; i++) {
                            try {
                                fleet[i] = bf.readLine();
                            } catch (IOException ex) {
                                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        App.RecieveFleet(fleet);
                    }
                    if (data.contains("sink")) {
                        char letter = data.split(" ")[1].charAt(0);
                        App.checkBoat(letter);
                    }
                    if (data.contains("lifes")) {
                        int health = Integer.parseInt(data.split(" ")[1]);
                        App.SetHealth(health);
                    }
                    if (data.equals("win") || data.equals("lose")) {     
                        App.whenWin(data);
                    }
                    if (data.equals("skip")) {     
                        App.OpponentSkip();
                    }
                    if (data.contains("msg$")) {     
                        App.ReadMsg(data);
                    }
                    if (data.contains("undo")) { 
                        String coords = data.split(" ")[3] + " " + data.split(" ")[4];
                        String letter = data.split(" ")[5];
                        App.getUndo(coords, letter);
                    }
                    if (data.equals("surrender")) {     
                        App.whenWin("surrender");
                    }
                    data = "";    
                }
             }
         }
        };
        t.start();
    }
    
}
