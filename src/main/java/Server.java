import java.net.*;
import java.io.*;
public class Server  {
   private static Socket s1;
   private static Socket s2;
   private static InputStreamReader in;
   private static BufferedReader bf;
   private static InputStreamReader in2;
   private static BufferedReader bf2;
   private static PrintWriter pr1;
    private static PrintWriter pr2;
   private static int fileCount = 0;
    public static void main(String[] args) throws IOException{
        int playerCount = 1;
        ServerSocket ss = new ServerSocket(4000);
        s1 = ss.accept();
        in = new InputStreamReader(s1.getInputStream());
        bf = new BufferedReader(in);
        
        pr1 = new PrintWriter(s1.getOutputStream());
       
        System.out.println("Player " + playerCount + " connected");
        playerCount++;
        pr1.println("connected");
        pr1.flush();
        s2 = ss.accept();
       
        in2 = new InputStreamReader(s2.getInputStream());
        bf2 = new BufferedReader(in2);
        System.out.println("Player " + playerCount + " connected");
        
        
        pr2 = new PrintWriter(s2.getOutputStream());
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
        str = bf2.readLine();
        System.out.println(str);
        pr1.println(str);
        pr1.flush();
       
    }
    
    public static void ReadPlayer2(BufferedReader bf2, BufferedReader bf) throws IOException{
        String str = bf2.readLine();
        System.out.println(str);
        pr1.println(str);
        pr1.flush();
        str = bf.readLine();
        System.out.println(str);
        pr2.println(str);
        pr2.flush();
      
    }
}
