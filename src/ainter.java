import java.util.*;

public interface ainter extends java.rmi.Remote {	

	public boolean createAuctionItem(auctionitem aItem) throws java.rmi.RemoteException;
	public boolean bidAuctionItems(auctionitem aItem) throws java.rmi.RemoteException;
	public HashMap<String, auctionitem>  listAuctionItems() throws java.rmi.RemoteException;
	public void saveState() throws java.rmi.RemoteException;
	public void registerForCallback(clientinter callbackobj) throws java.rmi.RemoteException;
	public void ping() throws java.rmi.RemoteException;
}