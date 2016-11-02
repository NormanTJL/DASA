import java.util.*;
import java.rmi.*;
import java.rmi.Naming;	//Import naming classes to bind to rmiregistry
import java.rmi.server.UnicastRemoteObject;
public class server{
	static int port = 1099;
	
	public server(){
		try {
       	//calculator c = new calculatorimpl();
       	servant s1 = new servant();	
       	ainter a_inter = (ainter)UnicastRemoteObject.exportObject(s1, 0);
       	Naming.rebind("rmi://localhost:" + port + "/Auction", a_inter);
     } 

     catch (Exception e) {
       System.out.println("Server Error: " + e);
     }
	
 }
	public static void main(String args[]){
		new server();
	}

}