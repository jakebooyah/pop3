import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MailServer
{  
   //A server that excutes MailService in multiple threads
   public static void main(String[] args) throws IOException
   {  
      DummyMailBox mail = new DummyMailBox();
      final int SBAP_PORT = 8888;
      ServerSocket server = new ServerSocket(SBAP_PORT);
      System.out.println("Waiting for clients to connect...");
      
      while (true)
      {
         Socket s = server.accept();
         System.out.println("Client connected.");
         MailService service = new MailService(s, mail);
         Thread t = new Thread(service);
         t.start();
      }
   }
}