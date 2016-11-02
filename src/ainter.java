import java.util.*;

public interface ainter 
          extends java.rmi.Remote {	

	public boolean createAuctionItem(auctionitem aItem) throws java.rmi.RemoteException;
	public boolean bidAuctionItems(String nameofItem) throws java.rmi.RemoteException;
	public HashMap<String, auctionitem>  listAuctionItems() throws java.rmi.RemoteException;
	public boolean saveState() throws java.rmi.RemoteException;
}