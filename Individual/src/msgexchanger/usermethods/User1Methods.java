package msgexchanger.usermethods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import msgexchanger.objects.Message;
import msgexchanger.tools.DBConnector;
import msgexchanger.tools.DataContainer;
import msgexchanger.tools.FileContainer;

public abstract class User1Methods {

	private static Scanner sc = new Scanner(System.in);

	public User1Methods() {

	}

	public static void sendMsg(String username) {
		boolean sendMsgRunning = true;
		int counter = 0;
		while (sendMsgRunning) {
			System.out.println("Insert receiver's username");
			String rname = sc.next();
			int sid = DataContainer.getUserID(username);
			int rid = DataContainer.getUserID(rname);
			if (DataContainer.userExists(rname)) {
				System.out.println("Type message");
				String data = sc.next();
				data += sc.nextLine();
				if (data.length()>0 && data.length()<=250) {
					String date = getDate();
					DBConnector.insertIntoMessages(date, sid, rid, data);
					FileContainer.writeMsgToFile(date, sid, rid, data);
					String receiver = DataContainer.getUserName(rid);
					String action = "New message to user: " + receiver;
					FileContainer.writeLogsToFile(date, username, action);
					int msgid = DBConnector.getLastMsgIDfromDB();
					Message msg = new Message(msgid, date, sid, rid, data);
					DataContainer.getMsgs().add(msg);
					System.out.println("Message sent!");
					sendMsgRunning = false;
				} else {
					System.out.println("Message should be 1-250 characters");
				}
			} else {
				System.out.println("Username does not exist");
				counter++;
				if (counter == 3) {
					sendMsgRunning = false;
				}
			}
		}
	}

	public static void viewMsgs(String username) {
		boolean viewMsgsRunning = true;
		int counter = 0;
		while (viewMsgsRunning) {
			System.out.println("View inbox or outbox?\n[1]Inbox\n[2]Outbox");
			String answer = sc.next();
			switch (answer) {
			case "1":
				DataContainer.getInbox(username);
				String date1 = User1Methods.getDate();
				String action1 = "Viewed inbox";
				FileContainer.writeLogsToFile(date1, username, action1);
				viewMsgsRunning = false;
				break;
			case "2":
				DataContainer.getOutbox(username);
				String date2 = User1Methods.getDate();
				String action2 = "Viewed inbox";
				FileContainer.writeLogsToFile(date2, username, action2);
				viewMsgsRunning = false;
				break;
			default:
				System.out.println("You inserted wrong data.");
				counter++;
				if (counter == 3) {
					viewMsgsRunning = false;
				}
			}
		}
	}

	public static String getDate() {
		Date todaysDate = new Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = df.format(todaysDate);
		return date;
	}
}
