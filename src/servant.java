import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class servant implements ainter, Runnable{
	static HashMap<String, auctionitem> listofauctions = new HashMap<String, auctionitem>();
	static HashMap<String, auctionitem> currentListofAuction = new HashMap<String, auctionitem>();		
	private HashMap<String, clientinter> clientList = new HashMap<String, clientinter>();
	private HashMap<String, Thread> listofTimer = new HashMap<String, Thread>();
	static String winningmsg[];
	static String ownermsg[];
	boolean status;
	long timertime=0;

	public void run(){
		auctionitem aIt = listofauctions.get(Thread.currentThread().getName());
		
		if(aIt!= null){
		try{
		timertime = listofauctions.get(Thread.currentThread().getName()).closingtime-System.currentTimeMillis()/1000;
		timertime*=1000;
		Thread.sleep(timertime);

		if(listofauctions.get(Thread.currentThread().getName()).winningemail.equals("")){
			String msg[] = new String[3];
			msg[0] = Thread.currentThread().getName();
			msg[1] = Double.toString(listofauctions.get(msg[0]).bidValue);
			msg[2] = listofauctions.get(msg[0]).creatoremail;
			clientinter creator = clientList.get(listofauctions.get(msg[0]).creatoremail);
			creator.notifyOwner(msg);
		}
		else{
			String msg[] = new String[4];
			msg[0] = Thread.currentThread().getName();
			msg[1] = Double.toString(listofauctions.get(msg[0]).bidValue);
			msg[2] = listofauctions.get(msg[0]).winningemail;
			msg[3] = listofauctions.get(msg[0]).creatoremail;
			clientinter winner = clientList.get(listofauctions.get(msg[0]).winningemail);
			winner.notifyWinner(msg);
			clientinter creator = clientList.get(listofauctions.get(msg[0]).creatoremail);
			creator.notifyOwner(msg);
			
			}
		}
		catch(Exception e){}
		}
	}
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
			Thread t1 = new Thread(this, aItem.randomid);
			timertime = aItem.closingtime - System.currentTimeMillis()/1000;
			timertime*=1000;
			t1.start();

			listofTimer.put(aItem.randomid, t1);
			listofauctions.put(aItem.randomid, aItem);
			status=true;
			//System.out.println(listofauctions.get(aItem.name));
			saveState();
			return status;
	}
	public boolean bidAuctionItems(auctionitem aItem) throws java.rmi.RemoteException{
			for(String aucID:currentListofAuction.keySet()){
				if(currentListofAuction.get(aucID).randomid.equals(aItem.randomid)){
					if(currentListofAuction.get(aucID).winningemail.equals("")==false){
						callLoser(currentListofAuction.get(aucID));
					}
					currentListofAuction.get(aucID).setbidValue(Double.toString(aItem.bidValue));
					currentListofAuction.get(aucID).setEmail(aItem.winningemail);
					status=true;

				}
			}
			saveState();
			return status;
	}
	public void callLoser(auctionitem aItem){
		String msg[] = new String[3];
		clientinter c1 = clientList.get(aItem.winningemail);
		msg[0] = aItem.randomid;
		msg[1] = aItem.name;
		msg[2] = String.valueOf(aItem.closingtime);
		try{
			c1.notifyLoser(msg);	
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public HashMap<String, auctionitem> listAuctionItems() throws java.rmi.RemoteException{
		HashMap<String, auctionitem> asd = checkifexpired();
		saveState();
		return asd;
	}
	public HashMap<String, auctionitem> listAllAuctionItems() throws java.rmi.RemoteException{
		return listofauctions;
	}
	public void saveState() throws java.rmi.RemoteException{
		try{
		PrintWriter pw = new PrintWriter(new FileOutputStream("savedstate.csv", false));
        StringBuilder writer = new StringBuilder();
        int count =0;
			for(String key:listofauctions.keySet()){
				if(count > 0){
				writer.append('\n');
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
				count++;
				}
				else{
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
				count++;
				}
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
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Runnable() {
			final ExecutorService executor = Executors.newSingleThreadExecutor();
    		Future<?> lastExecution;
    		public void run(){
    				for(String email:clientList.keySet()){
    					try{
    						clientList.get(email).ping();
    					}
    					catch(Exception e){
    						clientList.remove(email);
    					}
    				}
        	
    }
	}, 10, 10, TimeUnit.SECONDS);
	}
	public void ping(){
			
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
	
	public void registerForCallback(clientinter callbackobj) throws java.rmi.RemoteException{
      // store the callback object into the vector
		boolean status1 = false;
		clientinter c1 = callbackobj;
		
		if(clientList.size() != 0){
		      for(String email:clientList.keySet()){
		   	   	
		      	if(email.equals(c1.getEmail())){
		      		status1= true;
		      	}
		      }
  		}
      
      if(status1==false){
      	
      	clientList.put(callbackobj.getEmail(), c1);
      }
      else{
      	clientList.remove(callbackobj.getEmail());
      	clientList.put(callbackobj.getEmail(), c1);
      }
  }
}