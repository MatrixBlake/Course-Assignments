package AMR17S2;
import java.io.*;
import java.text.*;
import java.util.*;

/** generate result file according to the members
 * @author Kunze Wang
 */
public class Result {
	private Members members= new Members();
	
	public Result(Members m) {
		members=m;
	}
	
	/**
	 * generate result file according to the members
	 * @param resultFile resultFile is the file for the result to be written in.
	 */
	public void generateResult(String resultFile) {
		try {
			File f = new File(resultFile);
			BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
			DecimalFormat df = new DecimalFormat("0.00");
			ArrayList<Member> member = members.getArrayList(); 
			for(int i=0;i<member.size();i++) {
				Member m = member.get(i);
				if(i==0) {output.write("number "+m.getNumber());}
				else {output.write("\r\n"+"number "+m.getNumber());}
				output.write("\r\n"+"name "+m.getName());
				if(m.hasBirthday()) {output.write("\r\n"+"birthday "+m.getBirthday());}
				output.write("\r\n"+"tier "+m.getTier());
				if(m.hasMileage()) {
					output.write("\r\n"+"mileage "+df.format(m.getMileage())+"km");}
				if(m.hasPoints()) {
					output.write("\r\n"+"points "+df.format(m.getPoints()));
				}
				if(m.hasAddress()) {output.write("\r\n"+"address "+m.getAddress());}
				if(i!=(member.size()-1)) {output.write("\r\n");}
			}
			output.close();
		}
		catch(IOException e) {System.out.println(e.getMessage());}
	}

}
