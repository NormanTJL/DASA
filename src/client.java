import java.io.*;
import java.util.*;
import java.rmi.*;
import java.text.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

public class client implements clientinter, Runnable{
static HashMap<String, auctionitem> listofItems = new HashMap<String, auctionitem>();
static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
static String myEmail="";
static auctionitem aItem = new auctionitem(myEmail);
static Boolean status=false;
static ainter s1 = null;
static clientinter c1 = null;
 public client() throws RemoteException {
      super();
   }

	public static void main(String args[]){
        String reg_host = "localhost";
       int reg_port = 1099;
        Boolean exit = false;
       String choice;
        
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
          while(!myEmail.contains("@")){
             System.out.print("Please Enter your email: ");
              myEmail = System.console().readLine();
              if(!myEmail.contains("@")){
                System.out.println("Invalid Email Address");
              }
            }
            // Create the reference to the remote object through the remiregistry     
            s1 = (ainter)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/Auction");
            
            c1 = new client(); 
            UnicastRemoteObject.exportObject(c1,0);
            
            Thread t1 = new Thread(new client());
            
            t1.start();
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(new Runnable() {
                  final ExecutorService executor = Executors.newSingleThreadExecutor();
                    Future<?> lastExecution;
                    public void run(){
                        try{
                          s1.ping();                      
                        }
                        catch(Exception e){
                          System.out.println("\nServer is not online!\nPlease restart the program");
                          System.exit(0);
                        }
                }
              }, 5, 5, TimeUnit.SECONDS);            
          
		      }
        catch(Exception e){
          System.out.println("Server is not up!");
        }
        finally{     
            while(!exit){
              System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) List All past Auctions\n5) Exit\nInput Choice: ");
              
              choice = System.console().readLine();
              if(choice.equals("1")){
                aItem = createAuction();
                if(aItem.name != ""){
                  runProg(s1, choice);
                  if(!status){
                    System.out.println("Successfully Created Auction Item");
                    listofItems.put(aItem.name, aItem);
                    System.out.print("Press Enter key to continue");
                    System.console().readLine();
                  }
                  }
                  else{
                    System.out.println("Item not created, some fields input were incorrect");
                  }
                }
                else if(choice.equals("2")){
                  System.out.print("Item to bid: ");
                  String itemtobid = System.console().readLine();
                  String itembidValue = "";
                  runProg(s1,"3");
                  if(!listofauctions.isEmpty()){
                    for(String itemofauction : listofauctions.keySet()){
                       if(listofauctions.get(itemtobid)!=null){ 
                        if(listofauctions.get(itemofauction).randomid == listofauctions.get(itemtobid).randomid){
                          System.out.print("Current value is: " + listofauctions.get(itemtobid).bidValue + "\nPlease bid higher then the current bid: ");
                          itembidValue = System.console().readLine();
                          aItem = listofauctions.get(itemtobid);
                          try{
                              if(Double.parseDouble(itembidValue) > aItem.bidValue && Double.parseDouble(itembidValue) > aItem.startValue){
                                aItem.setbidValue(itembidValue);
                                aItem.setEmail(myEmail);
                                System.out.println(aItem.bidValue);
                                runProg(s1, choice);
                                System.out.println("You are now the highest bidder\nPress Enter key to continue");
                                System.console().readLine();
                              }
                              else{
                                System.out.println("Bid is too low. Please try bidding again with a higher value");
                              }
                          }
                          catch(Exception e){
                            System.out.println("Item Value is not numerical");
                          }
                          
                          
                        }
                            
                      }

                    else{
                      System.out.println("Invalid Bid ID");
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
              else if(choice.equals("4")){
                runProg(s1, choice);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                if(!listofauctions.isEmpty()){
                  for(int asd=1;asd<=listofauctions.size();asd++){
                    Date resultdate = new Date(listofauctions.get(String.valueOf(asd)).closingtime*1000);
                    String winningemail = listofauctions.get(String.valueOf(asd)).winningemail;
                    if(listofauctions.get(String.valueOf(asd)).winningemail == ""){
                      winningemail = "No Bidder yet";
                    }
                  System.out.println("\nId: " + listofauctions.get(String.valueOf(asd)).randomid + "\nName: "+listofauctions.get(String.valueOf(asd)).name + "\nValue: " + listofauctions.get(String.valueOf(asd)).bidValue + "\nCurrent highest bidder: "+ winningemail +"\nClosing time: " + (sdf.format(resultdate))+"\n") ;
                  } 
                }

              }
              else if (choice.equals("5")){
                System.exit(0);
              }
              else{
                System.out.println("Invalid Choice.");
              }
           }
        }
    }
public String notifyWinner(String message[]) throws java.rmi.RemoteException{
    System.out.println("\n****************Congratulations!****************\nYou have won the bid for item: "+ message[0] + "\nWith the value of: "+message[1]+"\nOwner: " + message[3]+"\n");
    System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) List All past Auctions\n5) Exit\nInput Choice: ");
     //Thread onethread = new Thread(new client());
      // onethread.start();
    return " ";
}
 public String notifyOwner(String message[]) throws java.rmi.RemoteException{
  if(message.length == 4){
    System.out.println("\n********Notification********\nYour item bid: "+ message[0] + " \nHave been completed with the value of: "+message[1]+"\nWinner: " + message[2]+"\n");
    
  }
  else{
    System.out.println("\n********Notification********\nYour item bid: "+ message[0] + " \nHave been completed\nNo one bidded for your item\nPlease Try Again\n");
  }
  System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) List All past Auctions\n5) Exit\nInput Choice: ");
    //Thread onethread = new Thread(new client());
    //onethread.start();
    return " ";
 }
 public void notifyLoser(String message[]) throws java.rmi.RemoteException{
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    Date resultdate = new Date(Long.parseLong(message[2])*1000);
    System.out.println("\n********Bid lost********\nYour bid for item: "+message[0]+"\nitem name: "+message[1]+"\nitem closing time:" + (sdf.format(resultdate))+"has been lost");  
    System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) List All past Auctions\n5) Exit\nInput Choice: ");

 }
 public boolean ping(){
    return true;
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
        else if(choice.equals("4")){
          listofauctions = null;
          listofauctions = s1.listAllAuctionItems();
        }
    }
    catch(Exception e){

    }
    
}

public String getEmail(){
  return myEmail;
}
	private static auctionitem createAuction(){
		auctionitem aItem = new auctionitem(myEmail);
		System.out.print("Please Enter name for New Auction Item: ");
		String nameinput = System.console().readLine();
		System.out.print("Please Enter value for New Auction Item: ");
		String valueInput = System.console().readLine();
    System.out.print("Please Enter Closing time/date in seconds: ");
    String closingTime = System.console().readLine();
    try{
      aItem.setName(nameinput);
      aItem.setValue(valueInput);
      aItem.setStartTime(System.currentTimeMillis()/1000);
      aItem.setDate(Long.parseLong(closingTime));  
    }
		catch(Exception e){

    }
		return aItem;
	}
@Override
public void run(){
  
  try{
    
  s1.registerForCallback(c1);
  }
  catch(Exception e){

  }
}
}


