import java.io.*;
import java.util.*;
import java.rmi.*;

public class client {
static HashMap<String, auctionitem> listofItems = new HashMap<String, auctionitem>();
static auctionitem aItem = new auctionitem();
static Boolean status=false;
	public static void main(String args[]){

		
		
       Boolean exit = false;
       String choice;
       while(!exit){
       		System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Save State\n5) Exit\nInput choice: ");
       		
       		choice = System.console().readLine();
       		if(choice.equals("1")){
       			aItem = createAuction();
       			runProg(choice);
       			if(!status){
       				System.out.println("Successfully Created Auction Item");
       				listofItems.put(aItem.name, aItem);
       				System.out.print("Press any key to continue");
       				System.console().readLine();
       			}
       		}
       		else if(choice.equals("2")){

       		}
       		else if( choice.equals("3")){

       		}
       		else if(choice.equals("4")){

       		}
       		else if (choice.equals("5")){
       			exit = true;
       		}
       		else{
       			System.out.println("Invalid Choice.");
       		}
       }
	
    }
    private static void runProg(String choice){
    	String reg_host = "localhost";
       int reg_port = 1099;
    try {

	    // Create the reference to the remote object through the remiregistry			
            servant s1 = (servant)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/Auction");
            status = s1.createAuctionItem(aItem);
            System.out.println(status);

	    // Now use the reference c to call remote methods
	   
        }
        catch(Exception e){

        }
        finally{

        }
        // Catch the exceptions that may occur - rubbish URL, Remote exception
	// Not bound exception or the arithmetic exception that may occur in 
	// one of the methods creates an arithmetic error (e.g. divide by zero)

    }

	private static auctionitem createAuction(){
		auctionitem aItem = new auctionitem();
		System.out.print("Please Enter name for New Auction Item: ");
		String nameinput = System.console().readLine();
		System.out.print("Please Enter value for New Auction Item: ");
		String valueInput = System.console().readLine();
		aItem.setName(nameinput);
		aItem.setValue(valueInput);
		aItem.setDate(System.currentTimeMillis());
		return aItem;
	}
}

