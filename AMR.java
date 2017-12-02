package AMR17S2;


/** the main method is here
 * @author Kunze Wang
 */
public class AMR {
	
	/**
	 * main method
	 * @param args args is the list of files.
	 */
	public static void main(String[] args) {
		ReadMembersFile read = new ReadMembersFile();
		read.readMembersFile(args[0]);
		Members stored = read.getMembers();	
		ReadInstructionFile readed = new ReadInstructionFile(stored);
		readed.readInstructionFile(args[1],args[3]);
		Members changed = readed.getMembers();
		Result r = new Result(changed);
		r.generateResult(args[2]);
	}
}

