import java.io.*;
import java.util.*;

public class servant implements ainter{

	public boolean createAuctionItem(auctionitem aItem) throws java.rmi.RemoteException{
			boolean status=true;
			return status;
	}
	public boolean bidAuctionItems(String nameofItem) throws java.rmi.RemoteException{
			boolean status=true;
			return status;
	}
	public HashMap<String, auctionitem> listAuctionItems() throws java.rmi.RemoteException{
		HashMap<String, auctionitem> hMap = new HashMap<String,auctionitem>();
		return hMap;
	}
	public boolean saveState() throws java.rmi.RemoteException{
			boolean status=true;
			return status;
	}

	public servant() throws java.rmi.RemoteException{
		super();
	}
}