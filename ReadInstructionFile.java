package AMR17S2;
import java.io.*;
import java.util.*;

/** read the instruction file and store the result into members and report file
 * @author Kunze Wang
 */
		
public class ReadInstructionFile {
	private Members members = new Members();
	
	public ReadInstructionFile(Members m) {
		members=m;
	}
	
	public Members getMembers() {
		return members;
	}
	
	/**
	 * read instruction file and store members into report file
	 * @param instructionFile instructionFile is the file containing instructions.
	 * @param reportFile reportFile is the file for the report to be written in.
	 */
	public void readInstructionFile(String instructionFile, String reportFile) {
		ArrayList<String> instruction = new ArrayList<String>();
		try {
			File file=new File(instructionFile);
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String s= reader.nextLine();
				if(s.length()!=0) {instruction.add(s);}				
			}
			reader.close();	
		}
		catch(Exception e){
			System.out.println("Error: "+e.getMessage());
		}
		for(String s:instruction) {
			String[] q = s.split(" ");
			switch(q[0]){
			case "add": instructionAdd(s.substring(4));break;
			case "delete": instructionDelete(s);break;
			case "earn" : instructionEarn(s);break;
			case "redeem" : instructionRedeem(s);break;
			case "query" : instructionQuery(s,reportFile);break;
			}
		}
		try {
			File f = new File(reportFile);
			BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
			if(f.length()==0) output.write("");
			output.close();
		}catch(Exception e) {System.out.println(e.getMessage());}
	}

	/**
	 * Execute adding a new member
	 * @param s s is the string of adding.
	 */
	public void instructionAdd(String s) {
		String[] add = s.split(";");
		String name="";
		String number="";
		String birthday="";
		String tier="";
		String stringMileage="";
		String stringPoints="";
		String address="";
		for(int i=0;i<add.length;i++) {			
			String[] details = add[i].trim().split(" ");
			switch(details[0]) {
			case "number" : number= add[i].trim().substring(7);break;
			case "name" : name = add[i].trim().substring(5);break;
			case "birthday" : birthday=add[i].trim().substring(9);break;
			case "tier" : tier=add[i].trim().substring(5);break;
			case "mileage": stringMileage=add[i].trim().substring(8);break;
			case "points" : stringPoints=add[i].trim().substring(7);break;
			case "address" : address = add[i].trim().substring(8);break;			
			}
		}
		if(Member.isValidNumber(number)) {
			Member m = new Member(number,"000");
			if(Member.isValidName(name)) {m.setName(name);}
			if(Member.isValidBirthday(birthday)) {m.setHasBirthday();m.setBirthday(Member.updateBirthday(birthday));}
			if(Member.isValidTier(tier)) {m.setHasTier();m.setTier(tier);}
			if(Member.isValidMileage(stringMileage)) {m.setHasMileage();m.setMileage(Member.convertToDouble(stringMileage));}
			if(Member.isVaildPoints(stringPoints)) {m.setHasPoints();m.setPoints(Double.parseDouble(stringPoints));}
			if(Member.isValidAddress(address)) {m.setHasAddress();m.setAddress(address);}
			members.addMember(m);
			}
		}
	
	/**
	 * execute deleting a member
	 * @param s s is the string of deleting.
	 */
	public void instructionDelete(String s) {
		String[] details = s.split(" ");
		if(details[1].equals("number")) {members.deleteMember(details[2]);}
		if(details[1].equals("name")) {members.deleteMember(s.trim().substring(12));}
	}
	
	/**
	 * execute earning points by adding mileage
	 * @param s s is the string of earning.
	 */
	public void instructionEarn(String s) {
		String[] details = s.split("\\W");
		String number = details[2];
		details = s.split(";");
		String mileage = details[1].trim().substring(8);
		members.earnPoints(number, mileage);
	}
	
	/**
	 * execute redeeming points
	 * @param s s is the string of redeeming.
	 */
	public void instructionRedeem(String s) {
		String[] details = s.split(";");
		String number=details[0].substring(14);
		String p= details[1].trim().substring(7);
		if(Member.isVaildPoints(p)) {
		double points=Double.parseDouble(details[1].substring(7));
		members.redeem(number, points);}
	}
	
	/**
	 * execute querying and store the result in to report file
	 * @param s s is the string of querying.
	 * @param report report is the file for the report to written in.
	 */
	public void instructionQuery(String s, String report) {
		String[] details=s.split(" ");
		if(details[1].equals("age")) {members.queryAgeMileage(report);}
		if(details[1].equals("tier")) {members.queryTier(details[2],report);}
	}
}
