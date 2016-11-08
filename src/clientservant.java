import java.rmi.*;
import java.rmi.server.*;

public class clientservant implements clientinter {
  
   public clientservant() throws RemoteException {
      super();
   }

   public String notifyMe(String message[]){
      String returnMessage = "You have won the bid for item: " + message[0]+"for a value of: "+message[1];
      System.out.println(returnMessage);
      return returnMessage;
   }      

}