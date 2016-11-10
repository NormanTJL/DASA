import java.util.*;
import java.rmi.*;
import java.rmi.Naming;	//Import naming classes to bind to rmiregistry
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
public class server{
	static int port = 1099;
	String reg_host = "localhost";
       int reg_port = 1099;
	public server(){
		try {
       	//calculator c = new calculatorimpl();
		//Runtime.getRuntime().exec("rmiregistry 1099");
		
       	servant s1 = new servant();	
       	File varTmpDir = new File("savedstate.csv");
		boolean exists = varTmpDir.exists();
		if(exists){
			s1.loadState();
		}
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