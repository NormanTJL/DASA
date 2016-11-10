import java.util.*;
import java.io.*;
public class auctionitem implements java.io.Serializable{
	private static final long serialVersionUID = 123L;
	String randomid;
	String name;
	double startValue;
	long starttime;
	long closingtime;
	double bidValue=0.0;
	String winningemail = "";
	String creatoremail="";
	public auctionitem(String myEmail){
		name="";
		randomid= "";
		startValue=0.0;
		starttime = 0;
		closingtime = 0;
		bidValue = 0.0;
		creatoremail = myEmail;
	}
	public void setEmail(String newemail){
		winningemail = newemail;
	}
	public void setName(String newName){
		name = newName;
	}
	public void setValue(String newValue){
		try{
			startValue = Double.parseDouble(newValue);
			bidValue = startValue;
		}
		catch(Exception e){
			System.out.println("Invalid Value");
		}

	}
	public void setStartTime(long newstime){
		starttime = newstime;
	}
	public void setDate(long newTime){
		closingtime = newTime + starttime;

	}
	public void setbidValue(String newValue){
		double newdvalue=startValue;
		try{
			newdvalue = Double.parseDouble(newValue);
		}
		catch(Exception e){
			System.out.println("Invalid Value");
		}
		if(newdvalue > startValue && newdvalue > bidValue){
			bidValue = newdvalue;
			System.out.println("you are now the highest bidder.");
		}
		else{
			System.out.println("Your bid is lower than the current bid, bid not accepted.");
		}
	}
	public void setClosetime(long closeTime){
		closingtime = closeTime;
	}
	
}