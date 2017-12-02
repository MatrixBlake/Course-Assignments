package AMR17S2;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/** store a list of members
 * @author Kunze Wang
 */
public class Members {
	private ArrayList<Member> ms = new ArrayList<Member>();
	
	public ArrayList<Member> getArrayList(){
		return ms;
	}
	
	/** 
	 * check if the new member is already in the list
	 * @param m m is the member to check whether is duplicate
	 * @return if it is in the list, return its position, else, return -1
	 */
	public int isDuplicate(Member m) {
		for(int i=0; i<ms.size();i++) {
			if(ms.get(i).getNumber().equals(m.getNumber())) {return i;}
		}
		return -1;
	}
	
	/** 
	 * add a member into the list
	 * @param m m is the member to add.
	 */
	public void addMember(Member m) {
		int index=isDuplicate(m);
		if((index==-1)&&(!m.getName().equals("000"))) {ms.add(m);}
		if(index!=-1) {
			if(!m.getName().equals("000")) {ms.get(index).setName(m.getName());}
			if(m.hasAddress()) {ms.get(index).setAddress(m.getAddress());ms.get(index).setHasAddress();}
			if(m.hasBirthday()) {ms.get(index).setBirthday(Member.updateBirthday(m.getBirthday()));ms.get(index).setHasBirthday();}
			if(m.hasPoints()) {ms.get(index).setPoints(m.getPoints());ms.get(index).setHasPoints();}
			if(m.hasMileage()) {ms.get(index).setMileage(m.getMileage());ms.get(index).setHasMileage();}
			if(m.hasTier()) {ms.get(index).setTier(m.getTier());ms.get(index).setHasTier();}
		}
	}
	
	/** 
	 * delete a member from the list according to the member or name
	 * @param s s is the name or number.
	 */	
	public void deleteMember(String s) {
		if(Member.isValidNumber(s)) {
			for(int i=0;i<ms.size();i++) {
				if(ms.get(i).getNumber().equals(s)) {ms.remove(i);}
			}
		}
		if(Member.isValidName(s)) {
			for(int i=0;i<ms.size();i++) {
				if(ms.get(i).getName().equals(s)) {ms.remove(i);i=i-1;}
			}
		}
	}
	
	/** 
	 * earn points for a member
	 * @param n n is the member's number.
	 * @param m m is the mileage.
	 */
	public void earnPoints(String n, String m) {
		double mm = Member.convertToDouble(m);
		int index =-1;
		for(int i=0;i<ms.size();i++) {
			if(ms.get(i).getNumber().equals(n)) {
				ms.get(i).setHasMileage();
				ms.get(i).setHasPoints();
				ms.get(i).setMileage(ms.get(i).getMileage()+mm);
				index=i;
			}
		}
		if(index>=0) {
			if(ms.get(index).getTier().equals("Silver")) {ms.get(index).setPoints(ms.get(index).getPoints()+mm/4);}
			if(ms.get(index).getTier().equals("Gold")) {ms.get(index).setPoints(ms.get(index).getPoints()+mm/2);}
			if(ms.get(index).getTier().equals("Platinum")) {ms.get(index).setPoints(ms.get(index).getPoints()+mm);}		
			ms.get(index).updateTier();
		}
	}
	
	/**
	 * redeem points for a member
	 * @param n n is the member's number.
	 * @param p p is the point.
	 */
	public void redeem(String n, double p) {
		int index =-1;
		for(int i=0;i<ms.size();i++) {
			if(ms.get(i).getNumber().equals(n)&&ms.get(i).getPoints()>=p) {
				index=i;
				ms.get(i).setPoints(ms.get(i).getPoints()-p);
			}		
		}
		if(index>=0)ms.get(index).updateTier();
	}
	
	/**
	 *  using Tier to query a list and store the result into a reportFile
	 * @param q q is the tier to be sort.
	 * @param reportFile reportFile is the file to be written in.
	 */
	public void queryTier(String q, String reportFile){
		if(Member.isValidTier(q)) {
			ArrayList<Member> filted = new ArrayList<Member>();
			for(Member s: ms) {
				if(s.getTier().equals(q)) {filted.add(s);}
			}
			ArrayList<Member> sortedByNumber=Members.sortByNumber(filted);
			ArrayList<Member> sortedByNameAndNumber=Members.sortByName(sortedByNumber);
			try {
				File f = new File(reportFile);
				BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
				if(f.length()==0) {output.write("----query tier "+q+"----"+"\r\n");}
				else {output.write("\r\n"+"----query tier "+q+"----"+"\r\n");}			
				DecimalFormat df = new DecimalFormat("0.00");
				for(Member m: sortedByNameAndNumber) {
					output.write("number "+m.getNumber()+"\r\n");
					output.write("name "+m.getName()+"\r\n");
					if(m.hasBirthday()) {output.write("birthday "+m.getBirthday()+"\r\n");}
					output.write("tier "+m.getTier()+"\r\n");
					if(m.hasMileage()) {
						output.write("mileage "+df.format(m.getMileage())+"km"+"\r\n");}
					if(m.hasPoints()) {
						output.write("points "+df.format(m.getPoints())+"\r\n");
					}
					if(m.hasAddress()) {
						output.write("address "+m.getAddress()+"\r\n");
					}
					if(sortedByNameAndNumber.indexOf(m)!=sortedByNameAndNumber.size()-1) {output.write("\r\n");}
				}
				output.write("-----------------------");
				output.write("\r\n");
				output.close();
			}
			catch(IOException e) {System.out.println(e.getMessage());}
		}
	}
	
	/**
	 * sort the member according to number
	 * @param s is the list of members to be sorted
	 * @return a list that has been sorted by number
	 */
	public static ArrayList<Member> sortByNumber(ArrayList<Member> s) {
		ArrayList<Member> output = new ArrayList<Member>();
		while(s.size()>0) {
			int minNo=0;
			String min=s.get(0).getNumber();
			for(int i=0;i<s.size();i++) {
				if(Member.numberSmaller(s.get(i).getNumber(),min)) {
					min=s.get(i).getNumber();
					minNo=i;
				}				
			}
			output.add(s.get(minNo));
			s.remove(s.get(minNo));
		}
		return output;
	}
	
	/**
	 * sort the list according to name
	 * @param s s is the list of members to be sort
	 * @return a list that has been sorted by name
	 */
	public static ArrayList<Member> sortByName(ArrayList<Member> s){
		ArrayList<Member> output = new ArrayList<Member>();
		while(s.size()>0) {
			int minNo=0;
			String min=s.get(0).getName();
			for(int i=0;i<s.size();i++) {
				if(Member.nameSmaller(s.get(i).getName(), min)) {					
					min=s.get(i).getName();
					minNo=i;
				}				
			}
			output.add(s.get(minNo));
			s.remove(s.get(minNo));
		}
		return output;
	}
	
	/**
	 * using Age Mileage query and store the result into reportFile
	 * @param reportFile reportFile is the file to be written in.
	 */
	public void queryAgeMileage(String reportFile){
		double[] p= new double[5];
		int total=0;
		Calendar now = Calendar.getInstance();
		int thisYear = now.get(Calendar.YEAR);
		for(Member s:ms) {
			total=total+1;
			if(s.hasBirthday()) {
				int age;
				String[] dmy = s.getBirthday().split("\\W");
				int year = Integer.parseInt(dmy[2]);
				age=thisYear-year;
				if(age<=8) {p[0]=p[0]+s.getMileage();}
				else if(age<=18){p[1]=p[1]+s.getMileage();}
				else if(age<=65){p[2]=p[2]+s.getMileage();}
				else {p[3]=p[3]+s.getMileage();}					
			}
			else {p[4]=p[4]+s.getMileage();}
		}
		try {
			File f = new File(reportFile);
			DecimalFormat df = new DecimalFormat("0");
			BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
			if(f.length()==0) {output.write("----query age mileage----"+"\r\n");}
			else {output.write("\r\n"+"----query age mileage----"+"\r\n");}
			output.write("Total Airline Members: "+total+"\r\n");
			output.write("Age based mileage distribution"+"\r\n");
			output.write("(0,8]: "+df.format(p[0])+"\r\n");
			output.write("(8,18]: "+df.format(p[1])+"\r\n");
			output.write("(18,65]: "+df.format(p[2])+"\r\n");
			output.write("(65,-): "+df.format(p[3])+"\r\n");
			output.write("Unknown: "+df.format(p[4])+"\r\n");
			output.write("-------------------------");
			output.write("\r\n");
			output.close();
		}
		catch(IOException e) {System.out.println(e.getMessage());}
		
	}
	
}
