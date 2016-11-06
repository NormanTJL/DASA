import java.io.*;
import java.util.*;

public class servant implements ainter{
	static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
	static HashMap<String, auctionitem> currentListofAuction = new HashMap<String, auctionitem>();		
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
			return status;
	}
	public boolean bidAuctionItems(String nameofItem) throws java.rmi.RemoteException{
			status=true;
			return status;
	}
	public HashMap<String, auctionitem> listAuctionItems() throws java.rmi.RemoteException{
		HashMap<String, auctionitem> asd = checkifexpired();
		return asd;
	}
	public boolean saveState() throws java.rmi.RemoteException{
			status=true;
			return status;
	}

	public servant() throws java.rmi.RemoteException{
		super();
	}

	public HashMap<String, auctionitem> checkifexpired(){
		
		for(String id:listofauctions.keySet()){
			if(listofauctions.get(id).closingtime > (System.currentTimeMillis()/1000)){
				currentListofAuction.put(id, listofauctions.get(id));
			}
		}
		return currentListofAuction;
	}
}