import java.util.*;
public class auctionitem{

	String name;
	Double itemValue;
	long closingtime;

	public auctionitem(){
		name="";
		itemValue=0.0;
		closingtime = 0;
	}
	public void setName(String newName){
		name = newName;
	}
	public void setValue(String newValue){
		try{
			itemValue = Double.parseDouble(newValue);
		}
		catch(Exception e){
			System.out.println("Invalid Value");
		}

	}
	public void setDate(long newTime){
		newTime = newTime/1000;
		closingtime = newTime;
	}
}