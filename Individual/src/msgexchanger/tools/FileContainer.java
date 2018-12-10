package msgexchanger.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileContainer {
	static PrintWriter writer;

	public FileContainer() {
		
	}
	
	public static void writeMsgToFile(String date, int sid, int rid, String data) {
		String sender = DataContainer.getUserName(sid);		
		String receiver = DataContainer.getUserName(rid);
		String msg = (date+" ~ from: "+ sender +" | to: "+ receiver +" | Message: '"+ data +"'\r\n");		
		try {
		    Files.write(Paths.get("../src/messagekeeper.txt"), msg.getBytes(), StandardOpenOption.APPEND);		    
		}catch (IOException e) {
		    
		}
	}
	
	public static void writeLogsToFile(String date, String username, String action) {				
		String msg = (date+" ~ user: "+ username +" | action: '"+ action+"'\r\n");
		try {
		    Files.write(Paths.get("../src/actionkeeper.txt"), msg.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    
		}
	}

}