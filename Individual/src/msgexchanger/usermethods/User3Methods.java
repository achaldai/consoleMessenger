package msgexchanger.usermethods;

import msgexchanger.tools.DBConnector;
import msgexchanger.tools.DataContainer;
import msgexchanger.tools.FileContainer;

public class User3Methods extends User2Methods {

	public User3Methods() {
			
	}
	
	public static void deleteMessage(String username) {
		System.out.println("What do you want to do?\n[1]Delete message by message id\n[2]Delete messages of certain user\n[3]Delete all inbox\n[4]Delete all outbox");
		String answer = sc.next();		
		switch (answer) {
			case "1": delByMsgID(username);
			break;
			case "2": delMsgOfUser(username);
			break;
			case "3": delInbox(username);
			break;
			case "4": delOutbox(username);
			break;
			default:
				System.out.println("You inserted wrong data\n");					
		}
	}
	private static void delByMsgID(String username) {
		int userid = DataContainer.getUserID(username);
		System.out.println("Insert message id");
		
		if(sc.hasNextInt()) {
			int msgid = sc.nextInt();		
			if(DataContainer.msgExists(msgid)) {
				int index = DataContainer.getMsgIndex(msgid);
				if((userid == DataContainer.getMsgs().get(index).getReceiverID())||(userid == DataContainer.getMsgs().get(index).getSenderID())) {
					System.out.println("Are you sure you want to delete the message?\n[Y]Yes\n[N]No");
					String ans = sc.next();
					switch(ans.toLowerCase()) {
					case "y":
						DataContainer.getMsgs().remove(index);
						DBConnector.deleteMessageFromTable(msgid);
						System.out.println("Message deleted!");
						String date = User1Methods.getDate();
						String action = "Deleted message with id #"+msgid;
						FileContainer.writeLogsToFile(date, username, action);
						break;
					case "n":
						break;				
					default:
						System.out.println("Wrong data\n");
					}
				}
				else {
					System.out.println("Wrong message id\n");
				}
			}
			else {
				System.out.println("No messages with such id\n");
			}
		}
		else {
			System.out.println("Wrong input\n");
		}
	}
	private static void delMsgOfUser(String username) {
		int u1id = DataContainer.getUserID(username);
		System.out.println("Insert user's username");
		String user2name = sc.next();
		if(DataContainer.userExists(user2name)) {
			int u2id = DataContainer.getUserID(user2name);
			if(DataContainer.userHasMsg(username, user2name)) {
				System.out.println("Are you sure you want to delete all messages from "+user2name+"?\n[Y]Yes\n[N]No");
				String ans = sc.next();
				switch(ans.toLowerCase()) {
				case "y":
					delUsersMessage(u1id,u2id);
					System.out.println("All message from " +user2name+" deleted!");
					String date = User1Methods.getDate();
					String action = "Deleted all messages from user "+user2name;
					FileContainer.writeLogsToFile(date, username, action);
					break;
				case "n":
					break;				
				default:
					System.out.println("Wrong data\n");
				}			
			}
			else{
				System.out.println("No messages found\n");
			}
		}
		else {
			System.out.println("No user found with this username\n");
		}
	}
	private static void delUsersMessage(int u1id, int u2id) {
		for(int i=0; i<DataContainer.getMsgs().size(); i++) {
			if((u1id == DataContainer.getMsgs().get(i).getReceiverID()) && (u2id == DataContainer.getMsgs().get(i).getSenderID())) {
				int msgid = DataContainer.getMsgs().get(i).getMsgID();
				int index = DataContainer.getMsgIndex(msgid);
				DataContainer.getMsgs().remove(index);								
			}
			else if((u1id == DataContainer.getMsgs().get(i).getSenderID()) && (u2id == DataContainer.getMsgs().get(i).getReceiverID())) {
				int msgid = DataContainer.getMsgs().get(i).getMsgID();
				int index = DataContainer.getMsgIndex(msgid);
				DataContainer.getMsgs().remove(index);				
				System.out.println("Message deleted!\n");								
			}
		}
		DBConnector.deleteMsgOfUserFromTable(u1id, u2id);
	}
	private static void delInbox(String username) {
		int userid = DataContainer.getUserID(username);
		if(DataContainer.userHasInbox(username)) {
			System.out.println("Are you sure you want to delete all inbox messages?\n[Y]Yes\n[N]No");
			String ans = sc.next();
			switch(ans.toLowerCase()) {
			case "y":				
				for(int i=0; i<DataContainer.getMsgs().size(); i++) {
					if(userid == DataContainer.getMsgs().get(i).getReceiverID()) {
						int msgid = DataContainer.getMsgs().get(i).getMsgID();
						int index = DataContainer.getMsgIndex(msgid);
						DataContainer.getMsgs().remove(index);						
					}
				}
				DBConnector.deleteInboxFromTable(userid);
				System.out.println("All inbox deleted!\n");
				String date = User1Methods.getDate();
				String action = "Deleted all inbox";
				FileContainer.writeLogsToFile(date, username, action);
				break;
			case "n":
				break;				
			default:
				System.out.println("Wrong data\n");
			}
		}
		else {
			System.out.println("No inbox messages found\n");
		}		
	}
	private static void delOutbox(String username) {
		int userid = DataContainer.getUserID(username);
		if(DataContainer.userHasOutbox(username)) {
			System.out.println("Are you sure you want to delete all outbox messages?\n[Y]Yes\n[N]No");
			String ans = sc.next();
			switch(ans.toLowerCase()) {
			case "y":				
				for(int i=0; i<DataContainer.getMsgs().size(); i++) {
					if(userid == DataContainer.getMsgs().get(i).getSenderID()) {
						int msgid = DataContainer.getMsgs().get(i).getMsgID();
						int index = DataContainer.getMsgIndex(msgid);
						DataContainer.getMsgs().remove(index);						
					}
				}
				DBConnector.deleteOutboxFromTable(userid);
				System.out.println("All outbox deleted!\n");
				String date = User1Methods.getDate();
				String action = "Deleted all outbox";
				FileContainer.writeLogsToFile(date, username, action);
				break;
			case "n":
				break;				
			default:
				System.out.println("Wrong data\n");
			}
		}
		else {
			System.out.println("No outbox messages found\n");
		}
	}
}
