import java.rmi.*;


public interface clientinter extends java.rmi.Remote{
 
    public String notifyWinner(String message[]) throws java.rmi.RemoteException;
    public String notifyOwner(String message[]) throws java.rmi.RemoteException;
    public void notifyLoser(String message[]) throws java.rmi.RemoteException;
    public String getEmail() throws java.rmi.RemoteException;
    public boolean ping() throws java.rmi.RemoteException;
}