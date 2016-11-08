import java.io.*;
import java.util.*;

public class servant implements ainter{
	static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
	static HashMap<String, auctionitem> currentListofAuction = new HashMap<String, auctionitem>();		
	private HashMap<String, clientinter> clientList = new HashMap<String, clientinter>();
	boolean status;
	public boolean createAuctionItem(auctionitem aItem) throws java.rmi.RemoteException{
			status=false;
			int id=0;
			if(listofauctions.isEmpty()){
				id=1;
			}
			else{
				id = listofauctions.size()+1;
			}
			aItem.randomid = Integer.toString(id);
			listofauctions.put(aItem.randomid, aItem);
			status=true;
			System.out.println(listofauctions.get(aItem.name));
			saveState();
			return status;
	}
	public boolean bidAuctionItems(auctionitem aItem) throws java.rmi.RemoteException{
			for(String aucID:currentListofAuction.keySet()){
				if(currentListofAuction.get(aucID).randomid.equals(aItem.randomid)){
					currentListofAuction.get(aucID).setbidValue(Double.toString(aItem.bidValue));
					currentListofAuction.get(aucID).setEmail(aItem.winningemail);
					status=true;
				}
			}
			saveState();
			return status;
	}
	public HashMap<String, auctionitem> listAuctionItems() throws java.rmi.RemoteException{
		HashMap<String, auctionitem> asd = checkifexpired();
		saveState();
		return asd;
	}
	public void saveState() throws java.rmi.RemoteException{
		try{
		PrintWriter pw = new PrintWriter(new FileOutputStream("savedstate.csv", false));
        StringBuilder writer = new StringBuilder();

			for(String key:listofauctions.keySet()){
				writer.append(listofauctions.get(key).randomid);
				writer.append(",");
				writer.append(listofauctions.get(key).name);	
				writer.append(",");
				writer.append(listofauctions.get(key).startValue);	
				writer.append(",");
				writer.append(listofauctions.get(key).starttime);	
				writer.append(",");
				writer.append(listofauctions.get(key).closingtime);	
				writer.append(",");
				writer.append(listofauctions.get(key).bidValue);	
				writer.append(",");
				writer.append(listofauctions.get(key).winningemail);	
				writer.append(",");
				writer.append(listofauctions.get(key).creatoremail);	
				writer.append('\n');
			}
			pw.write(writer.toString());
        	pw.close();
        }
        catch(Exception e){

        }
	}
	public void loadState(){
		BufferedReader is = null;
		String s;
		String perline[];
		auctionitem aItem;
		try{

			is = new BufferedReader(new FileReader("savedstate.csv"));
			while((s=is.readLine())!=null){
				perline = s.split(",");
				aItem = new auctionitem(perline[7]);
				aItem.setName(perline[1]);
				aItem.randomid = perline[0];
				aItem.startValue = Double.parseDouble(perline[2]);
				aItem.starttime = Long.parseLong(perline[3]);
				aItem.setEmail(perline[6]);
				aItem.setClosetime(Long.parseLong(perline[4]));
				aItem.bidValue = Double.parseDouble(perline[5]);
				listofauctions.put(aItem.randomid, aItem);
			}
			currentListofAuction = checkifexpired();
		}
		catch(Exception e){

		}
	}
	public servant() throws java.rmi.RemoteException{
		super();
	}

	public HashMap<String, auctionitem> checkifexpired(){
		currentListofAuction = new HashMap<String, auctionitem>();
		for(String id:listofauctions.keySet()){
			if(listofauctions.get(id).closingtime > (System.currentTimeMillis()/1000)){
				currentListofAuction.put(id, listofauctions.get(id));
			}
		}
		return currentListofAuction;
	}
	
	public synchronized void registerForCallback(clientinter callbackobj) throws java.rmi.RemoteException{
      // store the callback object into the vector
      if ((clientList.get("1")!=null)) {
         clientList.put("1", callbackobj);
      System.out.println("Registered new client ");
      doCallbacks();
    } // end if
  }
  public synchronized void unregisterForCallback(clientinter callbackobj) throws java.rmi.RemoteException{
    /*if (clientList.remove("1")) {
      System.out.println("Unregistered client ");
    } else {
       System.out.println(
         "unregister: client wasn't registered.");
    }*/
  }   
  private synchronized void doCallbacks( ) throws java.rmi.RemoteException{
    // make callback to each registered client
    String msg[] = new String[2];
    for (int i = 0; i < clientList.size(); i++){
      System.out.println("doing "+ i +"-th callback\n");    
      // convert the vector object to a callback object
      //clientinter nextClient = (clientinter)clientList.elementAt(i);
      // invoke the callback method
      	msg[0] = currentListofAuction.get(Integer.toString(i)).randomid;
      	msg[1] = Double.toString(currentListofAuction.get(Integer.toString(i)).bidValue);
        //nextClient.notifyMe(msg);
    }// end for
    System.out.println("********************************\n" + "Server completed callbacks ---");
  } // doCallbacks
}