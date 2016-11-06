import java.io.*;
import java.util.*;

public class servant implements ainter{
	static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
	boolean status;
	public boolean createAuctionItem(auctionitem aItem) throws java.rmi.RemoteException{
			status=false;
			listofauctions.put(aItem.name, aItem);
			status=true;
			System.out.println(listofauctions.get(aItem.name));
			return status;
	}
	public boolean bidAuctionItems(String nameofItem) throws java.rmi.RemoteException{
			status=true;
			return status;
	}
	public HashMap<String, auctionitem> listAuctionItems() throws java.rmi.RemoteException{
		return listofauctions;
	}
	public boolean saveState() throws java.rmi.RemoteException{
			status=true;
			return status;
	}

	public servant() throws java.rmi.RemoteException{
		super();
	}
}