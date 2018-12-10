package msgexchanger.usermethods;

import java.util.Scanner;

import msgexchanger.passwordmask.PasswordField;
import msgexchanger.tools.DBConnector;
import msgexchanger.tools.DataContainer;
import msgexchanger.tools.FileContainer;

public class User2Methods extends User1Methods {

	static Scanner sc = new Scanner(System.in);

	public User2Methods() {

	}

	public static void editUsername(String user) {

		boolean editNameRunning = true;
		int counter = 0;
		int userid = DataContainer.getUserID(user);
		while (editNameRunning) {
			System.out.println("Insert current username");
			String username = sc.next();
			user = DBConnector.getUserNameFromDB(userid);
			if (user.equals(username)) {
				System.out.println("Insert new username");
				String newusername = sc.next();
				System.out.println("Are you sure you want to change your username?\n[Y]Yes\n[N]No");
				String ans = sc.next();
				switch (ans.toLowerCase()) {
				case "y":
					updateUsername(username, newusername);
					String date = User1Methods.getDate();
					String action = "Changed username to " + newusername;
					FileContainer.writeLogsToFile(date, username, action);					
					editNameRunning = false;
					break;
				case "n":
					editNameRunning = false;
					break;
				default:
					System.out.println("Wrong data\n");
					counter++;
					if (counter == 3) {
						editNameRunning = false;
					}
				}
			} else {
				System.out.println("Wrong data\n");
				counter++;
				if (counter == 3) {
					editNameRunning = false;
				}
			}
		}
	}

	public static void editPassword(String username) {

		boolean editPassRunning = true;
		int counter = 0;
		System.out.println("username " + username);
		int userid = DataContainer.getUserID(username);		
		while (editPassRunning) {
			boolean rightpass = false;
			while (!rightpass) {
				System.out.println("Insert current password");
				String password = PasswordField.readPassword("");
				username = DBConnector.getUserNameFromDB(userid);				
				password = DBConnector.getPassFromDB(userid);
				if (password.equals(DataContainer.getUserPass(username))) {
					System.out.println("Insert new password");
					String newpassword = PasswordField.readPassword("");
					System.out.println("Are you sure you want to change your password?\n[Y]Yes\n[N]No");
					String ans = sc.next();
					switch (ans.toLowerCase()) {
					case "y":
						updatePassword(username, newpassword);
						String date = User1Methods.getDate();
						String action = "Password changed";
						FileContainer.writeLogsToFile(date, username, action);
						rightpass = true;
						editPassRunning = false;
						break;
					case "n":
						rightpass = true;
						editPassRunning = false;
						break;
					default:
						System.out.println("Wrong data\n");
						counter++;
						if (counter == 3) {
							rightpass = true;
							editPassRunning = false;
						}
					}
				} else {
					System.out.println("Wrong password\n");
					counter++;
					if (counter == 3) {
						rightpass = true;
						editPassRunning = false;
					}
				}
			}
		}
	}

	private static void updateUsername(String username, String newusername) {		
		int index = DataContainer.getUserIndex(username);		
		int id = DataContainer.getUsers().get(index).getUserID();		
		DataContainer.getUsers().get(index).setUserName(newusername);		
		DBConnector.updateUserNameInTable(newusername, id);
		System.out.println("Username updated!\n");
	}

	private static void updatePassword(String username, String newpassword) {
		
		int index = DataContainer.getUserIndex(username);		
		int id = DataContainer.getUsers().get(index).getUserID();		
		DataContainer.getUsers().get(index).setPass(newpassword);
		DBConnector.updateUserPassInTable(newpassword, id);
		System.out.println("Password updated!\n");
	}

}
