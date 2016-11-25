import java.io.*;
import java.util.*;
import java.rmi.*;
import java.text.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

public class runmultiple implements Runnable{
	static HashMap<String, auctionitem> hmap = new HashMap<String, auctionitem>();
	static long start[], end[];
	static ainter s1;
	static String reg_host = "localhost";
    static int reg_port = 1099;
    static Double total=0.0;
    static long result[];
    static auctionitem aItem = new auctionitem("1@gmail.com");
    static int count=1;
    static double bidvalue[];
    static String randomid;
    @Override
    public void run(){
    	
 		try{
 		aItem.randomid=randomid;
        aItem.setbidValue(String.valueOf(bidvalue[Integer.parseInt(Thread.currentThread().getName())]));
        aItem.setEmail((count)+"@gmail.com");
 		s1.bidAuctionItems(aItem);
 		}
 		catch(Exception e){

 		}
 		end[Integer.parseInt(Thread.currentThread().getName())] = System.currentTimeMillis();
 		result[Integer.parseInt(Thread.currentThread().getName())] = end[Integer.parseInt(Thread.currentThread().getName())]-start[Integer.parseInt(Thread.currentThread().getName())];
 		total += result[Integer.parseInt(Thread.currentThread().getName())];
 		

    }
	public static void main(String args[]){
		 try {
		 	s1 = (ainter)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/Auction");
		 	Thread t1 = null;
		 	start = new long[Integer.parseInt(args[1])];
		 	end = new long[Integer.parseInt(args[1])];
		 	result = new long[Integer.parseInt(args[1])];
		 	bidvalue = new double[Integer.parseInt(args[1])];
		 	randomid = args[0];
		 	hmap = s1.listAuctionItems();

		 	for(int i=0;i<Integer.parseInt(args[1]);i++){
		 		start[i] = System.currentTimeMillis();
		 		double a = i+1;
		 		bidvalue[i] = hmap.get(randomid).bidValue+a;
		 		
		 		t1 = new Thread(new runmultiple(), Integer.toString(i));
		 		t1.start();		 		

		 	}

		 	t1.join();

		 	System.out.println("Average time: " + total/Double.parseDouble(args[1]) + "ms");

		 }
		 catch(Exception e){

		 }
		 finally{
		 }
	}
	public runmultiple(){
		super();
	}
}	