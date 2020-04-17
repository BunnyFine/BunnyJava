 
package serverapp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServerThread extends Thread{
    ServerForm sf;
    ObjectInputStream oin;
    ObjectOutputStream out;
    ServerSocket serverSocket;
    Socket socket;
    public ServerThread(ServerForm sf)
    {
        this.sf = sf;
        try{
            serverSocket=new ServerSocket(Settings.port);
            JOptionPane.showMessageDialog(sf, "Server Started");
            start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
 
       public void sendMessage(String msg){
        try
        {
            out.writeObject(msg.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
        public void run()
        {
           while(true)
           {
               try{
                   socket=serverSocket.accept();
                   openReader();
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
           }
        }
    
 
    private void openReader() {
       try
       {
           oin=new ObjectInputStream(socket.getInputStream());
           out=new ObjectOutputStream(socket.getOutputStream());
           MsgRecThread mrt = new MsgRecThread(sf,oin,out);
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }
public class MsgRecThread extends Thread{
    ServerForm sf;
    ObjectInputStream oin;
    ObjectOutputStream out;
    public MsgRecThread(ServerForm sf,ObjectInputStream oin,ObjectOutputStream out)
    {
        this.sf=sf;
        this.oin=oin;
        this.out=out;
        start();
    }
    
    @Override
    public void run(){
      while(true){  try{
            sf.jtaRec.append(oin.readObject().toString()+"\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }}
}

}
