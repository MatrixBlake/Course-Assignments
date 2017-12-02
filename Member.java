package AMR17S2;
import java.util.*;

/** member class: this is used to define a member, and define some static method to verify if the information is valid
 @author Kunze Wang
 *
 */

public class Member {
	private String number;
	private String name;
	private String birthday;
	private String tier;
	private double mileage;
	private double points;
	private String address;
	private boolean hasBirthday;
	private boolean hasMileage;
	private boolean hasPoints;
	private boolean hasAddress;
	private boolean hasTier;
	
	public Member(String num, String nam) {
		number=num;
		name=nam;
		tier="Silver";
		hasBirthday=false;
		hasMileage=false;
		hasPoints=false;
		hasAddress=false;
		hasTier=false;
	}
	
	public void setNumber(String s) {number=s;}	
	public void setName(String s) {name=s;}
	public void setBirthday(String s) {birthday=s;}
	public void setTier(String s) {tier=s;}
	public void setMileage(double m) {mileage=m;}
	public void setPoints(double p) {points=p;}
	public void setAddress(String s) {address=s;}
	public void setHasBirthday() {hasBirthday=true;}
	public void setHasMileage() {hasMileage=true;}
	public void setHasPoints() {hasPoints=true;}
	public void setHasAddress() {hasAddress=true;}
	public void setHasTier() {hasTier=true;}
	
	
	public String getNumber() {return number;}
	public String getName() {return name;}
	public String getBirthday() {return birthday;}
	public String getTier() {return tier;}
	public double getMileage(){return mileage;}
	public double getPoints() {return points;}
	public String getAddress() {return address;}
	public boolean hasBirthday() {return hasBirthday;}
	public boolean hasMileage() {return hasMileage;}
	public boolean hasPoints() {return hasPoints;}
	public boolean hasAddress() {return hasAddress;}
	public boolean hasTier() {return hasTier;}
	
	/** 
	 * check whether the number is valid
	 * @param n n is the number string.
	 * @return boolean whether the number is valid
	 */
	public static boolean isValidNumber(String n) {
		if(n.matches("\\d{5}")) {return true;}
		else {return false;}
	}
	
	/** 
	 * check whether the name is valid
	 * @param n n is the name string.
	 * @return boolean whether the name is valid
	 */
	public static boolean isValidName(String n) {
		String[] nn = n.split(" ");
		boolean check=true;
		for(int i=0;i<nn.length;i++) {
			if(!nn[i].matches("[a-zA-Z]+")){check=false;}
			}
		if(nn.length<2) {check=false;}
		return check;
	}
	
	/** 
	 * check whether the birthday is valid
	 * @param s s is the birthday string.
	 * @return boolean whether the birthday is valid
	 */
	public static boolean isValidBirthday(String s) {
		if(!s.matches("(0?[1-9]|[12][0-9]|3[01])(-|/)(0?[1-9]|1[012])(-|/)((19|20)\\d\\d)")) {return false;}
		String[] ss;
		if(s.matches("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)")) {ss=s.split("-");}
		else {ss=s.split("/");}
		int day=Integer.parseInt(ss[0]);
		int month=Integer.parseInt(ss[1]);
		int year=Integer.parseInt(ss[2]);
		boolean isLeapYear=false;
		Calendar now = Calendar.getInstance();  
		if(year>now.get(Calendar.YEAR)) {return false;}
		if(year==now.get(Calendar.YEAR) && month>(1+now.get(Calendar.MONTH))) {return false;}
		if(year==now.get(Calendar.YEAR) && month==(1+now.get(Calendar.MONTH)) && day>now.get(Calendar.DATE)) {return false;}				
		if((year%4==0&&year%100!=0)||year%400==0) {isLeapYear=true;}
		if((month==4||month==6||month==9||month==11)&&day==31) {return false;}
		if(isLeapYear&&month==2&&(day>=30)) {return false;}
		if((!isLeapYear)&&month==2&&(day>=29)) {return false;}
		return true;		
	}
	
	/** 
	 * check whether the tier is valid
	 * @param s s is the tier string.
	 * @return boolean whether the tier is valid
	 */
	public static boolean isValidTier(String s) {
		if(s.equals("Platinum")||s.equals("Gold")||s.equals("Silver")) {return true;}
		else{return false;}
	}
	
	/** 
	 * check whether the mileage is valid
	 * @param s s is the mileage string with km or m
	 * @return boolean whether the mileage is valid
	 */
	public static boolean isValidMileage(String s) {
		if(s.matches("(^\\d+(\\.\\d+)?+(km||m))")) {return true;}
		else {return false;}
	}
	
	/** 
	 * check whether the point is valid
	 * @param s s is the points string
	 * @return boolean whether the point is valid
	 */
	public static boolean isVaildPoints(String s) {
		if(s.matches("(^\\d+(\\.\\d+)?)")) {return true;}
		else {return false;}
	}
	
	/** 
	 * check whether the address is valid
	 * @param s s is the address string
	 * @return boolean whether the address is valid
	 */
	public static boolean isValidAddress(String s) {
		String[] n = s.split("\\W");
		if(n[n.length-1].matches("\\d{4}")) {return true;}
		else {return false;}
	}
	
	/** 
	 * convert string mileage into double, convert m to km
	 * @param m m is the mileage string with km or m
	 * @return mileage double
	 */
	public static double convertToDouble(String m) {
		int n= m.indexOf("k");
		if(n!=-1) {
			m=m.substring(0, m.length()-2);
			return Double.parseDouble(m);
		}
		else {
			m=m.substring(0,m.length()-1);
			return Double.parseDouble(m)/1000;
		}
		
	}
	
	/** 
	 * update the tier according to point
	 */
	public void updateTier() {
		if(points<5000) {tier="Silver";}
		else if (points<10000) {tier="Gold";}
		else {tier="Platinum";}
	}
	
	/** 
	 * compare to member's number in the string type 
	 * @param a a is the first number
	 * @param b b is the second number
	 * @return if a is bigger than b, return true, else return false
	 */
	public static boolean numberSmaller(String a, String b) {
		int aa=Integer.parseInt(a);
		int bb=Integer.parseInt(b);
		return(aa<bb);
	}
	
	/**
	 * compare 2 strings
	 * @param a the first name string
	 * @param b the second name string
	 * @return if a<b, return true, else return false
	 */
	public static boolean nameSmaller(String a, String b) {
		if(a.equals(b)) {return false;}
		int l1=a.length();
		int l2=b.length();
		int l;
		if(l1<l2) {l=l1;}
		else {l=l2;}
		int index=-1;
		for(int i=0;i<l;i++) {
			if(a.charAt(i)!=b.charAt(i)) {index=i;break;}
		}
		if(index<l&&index>=0) {
			char ca=a.charAt(index);
			char cb=b.charAt(index);
			return(ca<cb);}
		else {return true;}
	}
	
	/** 
	 * update birthday into the standard form
	 * @param b b is the birthday string.
	 * @return a new string that updates birthday into the standard form
	 */
	public static String updateBirthday(String b) {
		String output="";
		String d[] = b.split("\\W");
		int dd[] = new int[3];
		dd[0]=Integer.parseInt(d[0]);
		dd[1]=Integer.parseInt(d[1]);
		if(dd[0]<10) {d[0]="0"+dd[0];}
		if(dd[1]<10) {d[1]="0"+dd[1];}
		output=d[0]+"/"+d[1]+"/"+d[2];
		return output;
	}
	
}
	
	
	