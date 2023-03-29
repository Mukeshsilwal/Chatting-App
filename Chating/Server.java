import java.io.*;
import java.net.*;
class Server{
   
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    

    public Server(){

       try{ 
        server=new ServerSocket(7777);
        System.out.println("Server is ready to accept connection");
        System.out.println("Waiting.....");
        socket=server.accept();

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream());
  startReading();
  startWriting();
    }
       catch(Exception e){
        e.printStackTrace();
       }
    
    }
    public void startReading(){
        Runnable r1=()->{
            System.out.println("Reading started");
            while(true)
            {
              try{  String msg=br.readLine();
                
                if(msg.equals("Exit")){
                    System.out.println("Chat terminated");
                    break;
                }
                System.out.println("Client:"+msg);
            }
            catch(Exception e){
                e.printStackTrace();
            }
    
        }
           
            

        };
         new Thread(r1).start();
    }

    public void startWriting(){
        Runnable r2=()->{
            System.out.println("Writing is started");
           
            while(true){
                try{
                BufferedReader Br1=new BufferedReader(new InputStreamReader(System.in));
                String content=Br1.readLine();
                out.println(content);
                out.flush();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        
           
        };
         new Thread(r2).start();
         

    }

    public static void main(String[] args)throws Exception {
       new Server();

        
    }
}
