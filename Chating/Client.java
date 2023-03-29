import java.net.*;
import java.io.*;

public class Client {
    
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try{
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection Done");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
    
    public void startReading(){
        Runnable r1=()->{
            System.out.println("Reading started");
            while(true) {
                try {
                    String msg=br.readLine();
                    if(msg == null) {
                        System.err.println("Error reading message from server: null message received.");
                        break;
                    }
                    if(msg.equals("Exit")) {
                        System.out.println("Chat terminated");
                        break;
                    }
                    System.out.println("Server:"+msg);
                } catch(SocketTimeoutException e) {
                    System.err.println("Socket timed out while waiting for message from server: " + e.getMessage());
                    break;
                } catch(IOException e) {
                    System.err.println("Error reading message from server: " + e.getMessage());
                    break;
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting(){
        Runnable r2=()->{
            System.out.println("Writing is started");
            while(true) {
                try {
                    BufferedReader Br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=Br1.readLine();
                    if(content == null) {
                        System.err.println("Error reading user input: null content received.");
                        break;
                    }
                    out.println(content);
                    out.flush();
                } catch(IOException e) {
                    System.err.println("Error sending message to server: " + e.getMessage());
                    break;
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String args[]){
        new Client();
    }
}
