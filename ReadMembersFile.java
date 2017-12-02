package AMR17S2;
import java.io.*;
import java.util.*;
/** read the members file and store it in the members
 * @author Kunze Wang
 */
public class ReadMembersFile {
	private Members members = new Members();
	
	public Members getMembers() {
		return members;
	}
	
	/**
	 * read the members file and store it in the members
	 * @param membersFile membersFile is the file containing members.
	 */
	public void readMembersFile(String membersFile) {
		ArrayList<String> memberFile = new ArrayList<String>();
		try {
			File file=new File(membersFile);
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String s= reader.nextLine();
				memberFile.add(s);
			}
			reader.close();
		}
		catch(Exception e){
			System.out.println("Error: "+e.getMessage());
		}
		
		int flag=0;
		int total=0;
		while(true) {
			String name="";
			String number="";
			String birthday="";
			String tier="";
			String stringMileage="";
			String stringPoints="";
			String address="";
			String buffer="";
			int addressFlag=0;
			for(int i=flag;i<memberFile.size();i++) {
				total=total+1;
				if(memberFile.get(i).length()==0) {flag=i+1;break;}
				String[] details = memberFile.get(i).split(" ");
				switch(details[0]) {
				case "name" : name = memberFile.get(i).trim().substring(5);addressFlag=0;break;
				case "number" : number=details[1];addressFlag=0;break;
				case "birthday" : birthday=details[1];addressFlag=0;break;
				case "tier" : tier=details[1];addressFlag=0;break;				
				case "mileage": stringMileage=details[1];addressFlag=0;break;
				case "points" : stringPoints=details[1];addressFlag=0;break;
				case "address" : address = memberFile.get(i).trim().substring(8);addressFlag=0;break;
				default : buffer=" "+memberFile.get(i).trim();break;
				}
				if(buffer.length()!=0&&memberFile.get(i-1-addressFlag).substring(0,7).equals("address")) {address=address+buffer;addressFlag++;}
			}
			if(Member.isValidName(name)&&Member.isValidNumber(number)) {	
				Member m = new Member(number,name);
				if(Member.isValidBirthday(birthday)) {m.setHasBirthday();m.setBirthday(Member.updateBirthday(birthday));}
				if(Member.isValidTier(tier)) {m.setHasTier();m.setTier(tier);}
				if(Member.isValidMileage(stringMileage)) {m.setHasMileage();m.setMileage(Member.convertToDouble(stringMileage));}
				if(Member.isVaildPoints(stringPoints)) {m.setHasPoints();m.setPoints(Double.parseDouble(stringPoints));}
				if(Member.isValidAddress(address)) {m.setHasAddress();m.setAddress(address);}
				members.addMember(m);						
			}
			if(total>=memberFile.size()) {break;}
		}
	}
}


