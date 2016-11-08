import java.io.*;
import java.util.*;
import java.rmi.*;
import java.text.*;

public class client {
static HashMap<String, auctionitem> listofItems = new HashMap<String, auctionitem>();
static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
static String myEmail="";
static auctionitem aItem = new auctionitem(myEmail);
static Boolean status=false;

	public static void main(String args[]){
    System.out.print("Please Enter your email: ");
      myEmail = System.console().readLine();
        String reg_host = "localhost";
       int reg_port = 1099;
        Boolean exit = false;
       String choice;
        ainter s1 = null;
        if(args.length!= 0){
          reg_host = args[0];
              if(args.length < 2){
                reg_port = 1099;
              }
              if(args.length == 2){
                reg_port = Integer.parseInt(args[1]);
              }

         } 
		     try {

      // Create the reference to the remote object through the remiregistry     
            s1 = (ainter)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/Auction");
		      }
        catch(Exception e){

        }
        finally{     
            while(!exit){
              System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Exit\nInput choice: ");
              
              choice = System.console().readLine();
              if(choice.equals("1")){
                aItem = createAuction();
                runProg(s1, choice);
                if(!status){
                  System.out.println("Successfully Created Auction Item");
                  listofItems.put(aItem.name, aItem);
                  System.out.print("Press any key to continue");
                  System.console().readLine();
                }
              }
              else if(choice.equals("2")){
                System.out.print("Item to bid: ");
                String itemtobid = System.console().readLine();
                String itembidValue = "";
                runProg(s1,"3");
                if(!listofauctions.isEmpty()){
                  for(String itemofauction : listofauctions.keySet()){
                    if(listofauctions.get(itemofauction).randomid == listofauctions.get(itemtobid).randomid){
                      System.out.print("Current value is: " + listofauctions.get(itemtobid).bidValue + "\nPlease bid higher then the current bid: ");
                      itembidValue = System.console().readLine();
                      aItem = listofauctions.get(itemtobid);
                      aItem.setbidValue(itembidValue);
                      aItem.setEmail(myEmail);
                      runProg(s1, choice);
                      System.out.println("Press any key to continue");
                      System.console().readLine();
                    }
                        
                  } 

                }                
                else{
                      System.out.println("Item not found, please check the list of current bids again to check if the auction has been closed.");
                }
              }
              else if(choice.equals("3")){
                runProg(s1, choice);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");    

                if(!listofauctions.isEmpty()){
                  for(String asd : listofauctions.keySet()){
                    Date resultdate = new Date(listofauctions.get(asd).closingtime*1000);
                    String winningemail = listofauctions.get(asd).winningemail;
                    if(listofauctions.get(asd).winningemail == ""){
                      winningemail = "No Bidder yet";
                    }
                  System.out.println("\nId: " + listofauctions.get(asd).randomid + "\nName: "+listofauctions.get(asd).name + "\nValue: " + listofauctions.get(asd).bidValue + "\nCurrent highest bidder: "+ winningemail +"\nClosing time: " + (sdf.format(resultdate))+"\n") ;
                  } 
                }
                else{
                  System.out.println("No auctions available right now");
                } 
              }
              else if (choice.equals("4")){
                exit = true;
              }
              else{
                System.out.println("Invalid Choice.");
              }
           }
        }
    }
    
private static void runProg(ainter s1, String choice){
    try{
        if(choice.equals("1")){
        status = s1.createAuctionItem(aItem);
        }
        else if(choice.equals("2")){
          status = s1.bidAuctionItems(aItem);
        }
        else if(choice.equals("3")){
          listofauctions = null;
          listofauctions = s1.listAuctionItems();
        }
    }
    catch(Exception e){

    }
    
}

	private static auctionitem createAuction(){
		auctionitem aItem = new auctionitem(myEmail);
		System.out.print("Please Enter name for New Auction Item: ");
		String nameinput = System.console().readLine();
		System.out.print("Please Enter value for New Auction Item: ");
		String valueInput = System.console().readLine();
    System.out.print("Please Enter Closing time/date in seconds: ");
    String closingTime = System.console().readLine();

		aItem.setName(nameinput);
		aItem.setValue(valueInput);
    aItem.setStartTime(System.currentTimeMillis()/1000);
		aItem.setDate(Long.parseLong(closingTime));
		return aItem;
	}
}


