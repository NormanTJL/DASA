import java.io.*;
import java.util.*;

public class servant implements ainter{
	static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
	boolean status;
	public boolean createAuctionItem(auctionitem aItem) throws java.rmi.RemoteException{
			status=false;
			listofauctions.put(aItem.name, aItem);
			status=true;
			return status;
	}
	public boolean bidAuctionItems(String nameofItem) throws java.rmi.RemoteException{
			status=true;
			return status;
	}
	public HashMap<String, auctionitem> listAuctionItems() throws java.rmi.RemoteException{
		HashMap<String, auctionitem> hMap = listofauctions;
		return hMap;
	}
	public boolean saveState() throws java.rmi.RemoteException{
			status=true;
			return status;
	}

	public servant() throws java.rmi.RemoteException{
		super();
	}
}