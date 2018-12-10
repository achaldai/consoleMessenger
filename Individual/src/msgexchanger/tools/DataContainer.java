package msgexchanger.tools;

import java.sql.SQLException;
import java.util.ArrayList;

import msgexchanger.objects.Message;
import msgexchanger.objects.User;

public class DataContainer{
	
	private static ArrayList<User> users;
	private static ArrayList<Message> msgs;	
	

		
	public static void initialise() {
		try {
			DataContainer.users = DBConnector.getUsersFromDB();			
			DataContainer.msgs = DBConnector.getMsgsFromDB();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
		
	public static ArrayList<User> getUsers() {
		return users;
	}
	public static ArrayList<Message> getMsgs() {
		return msgs;
	}
	public static boolean userExists(String username) {
		boolean exists=false;
		for(int i=0; i<DataContainer.getUsers().size(); i++) {
			if(username.equals(DataContainer.getUsers().get(i).getUserName())) {
				exists=true;			
				break;
			}
		}
		return exists;
	}
	public static int getUserID(String username) {
		int ID = 0;		
		for(int i=0; i<getUsers().size(); i++) {			
			if(username.equals(getUsers().get(i).getUserName())) {				
				ID = getUsers().get(i).getUserID();				
			}
		}
		return ID;
	}
	public static String getUserName(int ID) {
		String username = "";		
		for(int i=0; i<getUsers().size(); i++) {			
			if(ID == getUsers().get(i).getUserID()) {				
				username = getUsers().get(i).getUserName();				
			}
		}
		return username;
	}
	public static int getUserIndex(String username) {		
		int index=0;
		for(int i=0; i<getUsers().size(); i++) {			
			if(username.equals(getUsers().get(i).getUserName())) {				
				index=i;
				break;
			}
		}
		return index;
	}
	public static String getUserPass(String username) {
		String pass = "";
		for(int i=0; i<getUsers().size(); i++) {
			if(getUsers().get(i).getUserName().equals(username)) {
				pass = getUsers().get(i).getPass();
				break;
			}
		}
		return pass;
	}
	public static int getUserRole(String username) {
		int role = 0;
		for(int i=0; i<getUsers().size(); i++) {
			if(getUsers().get(i).getUserName().equals(username)) {
				role = getUsers().get(i).getRole();
				break;
			}
		}
		return role;
	}
	public static boolean msgExists(int msgid) {
		boolean exists=false;
		for(int i=0; i<DataContainer.getMsgs().size(); i++) {
			if(msgid == getMsgs().get(i).getMsgID()) {
				exists=true;			
				break;
			}			
		}
		return exists;
	}
	public static int getMsgIndex(int msgid) {
		int index=0;
		for(int i=0; i<getMsgs().size(); i++) {			
			if(msgid == getMsgs().get(i).getMsgID()) {				
				index=i;
				break;
			}
		}
		return index;
	}
	public static void getInbox(String username) {
		int rid = getUserID(username);
		boolean inboxExists = false;
		for(int i=0; i<getMsgs().size(); i++) {
			if(rid == getMsgs().get(i).getReceiverID()) {
				int sid = getMsgs().get(i).getSenderID();
				System.out.println("|msg#"+getMsgs().get(i).getMsgID()+"|"+getMsgs().get(i).getDate()+"|sent from: "+getUserName(sid)+"|\n'"+getMsgs().get(i).toString()+"\n");
				inboxExists = true;
			}
		}
		if(inboxExists==false) {
			System.out.println("User does not have inbox messages");
		}		
	}
	public static void getOutbox(String username) {
		int sid = DataContainer.getUserID(username);
		boolean outboxExists = false;
		for(int i=0; i<getMsgs().size(); i++) {
			if(sid == getMsgs().get(i).getSenderID()) {
				int rid = getMsgs().get(i).getReceiverID();
				System.out.println("|msg#"+getMsgs().get(i).getMsgID()+"|"+getMsgs().get(i).getDate()+"|sent to: "+getUserName(rid)+"|\n'"+getMsgs().get(i).toString()+"\n");
				outboxExists = true;
			}
		}
		if(outboxExists==false) {
			System.out.println("User does not have outbox messages");
		}
	}
	public static boolean userHasMsg(String user1, String user2) {
		int u1id = DataContainer.getUserID(user1);
		int u2id = DataContainer.getUserID(user2);
		boolean msgExists = false;
		for(int i=0; i<DataContainer.getMsgs().size(); i++) {
			if((u1id == DataContainer.getMsgs().get(i).getReceiverID()) && (u2id == DataContainer.getMsgs().get(i).getSenderID())||(u1id == DataContainer.getMsgs().get(i).getSenderID()) && (u2id == DataContainer.getMsgs().get(i).getReceiverID())) {
				msgExists = true;
			}
		}
		return msgExists;	
	}
	public static boolean userHasInbox(String username) {
		int u1id = DataContainer.getUserID(username);
		boolean msgExists = false;
		for(int i=0; i<DataContainer.getMsgs().size(); i++) {
			if(u1id == DataContainer.getMsgs().get(i).getReceiverID()) {
				msgExists = true;
			}
		}
		return msgExists;	
	}
	public static boolean userHasOutbox(String username) {
		int u1id = DataContainer.getUserID(username);
		boolean msgExists = false;
		for(int i=0; i<DataContainer.getMsgs().size(); i++) {
			if(u1id == DataContainer.getMsgs().get(i).getSenderID()) {
				msgExists = true;
			}
		}
		return msgExists;	
	}
}
