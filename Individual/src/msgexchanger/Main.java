package msgexchanger;

import java.util.Scanner;

import msgexchanger.passwordmask.PasswordField;
import msgexchanger.tools.DBConnector;
import msgexchanger.tools.DataContainer;
import msgexchanger.tools.FileContainer;
import msgexchanger.usermethods.AdminMethods;
import msgexchanger.usermethods.User1Methods;
import msgexchanger.usermethods.User2Methods;
import msgexchanger.usermethods.User3Methods;

public class Main {

	static DBConnector db;
	private static Scanner sc = new Scanner(System.in);
	static Main m = new Main();
	boolean firstMenuRunning;
	boolean secondMenuRunning;

	public Main() {

	}

	public void firstMenu() {
		firstMenuRunning = true;
		while (firstMenuRunning) {
			printLoginMenu();
			runLoginMenu();
		}
	}

	public void printLoginMenu() {
		System.out.println("[1]Log in");
		System.out.println("[2]Sign up");
		System.out.println("[3]Exit");
	}

	public void runLoginMenu() {
		String answer = sc.next();
		int counter = 0;
		switch (answer) {
		case "1":
			logIn();
			break;
		case "2":
			signUp();
			break;
		case "3":
			firstMenuRunning = false;
			break;
		default:
			System.out.println("You inserted wrong data\n");
			counter++;
			if (counter == 3) {
				firstMenuRunning = false;
			}
		}
	}

	public void signUp() {
		boolean signUpRunning = true;
		boolean wrongPass = true;
		int counter = 0;
		while (signUpRunning) {
			System.out.println("Choose username:");
			String username = sc.next();
			if (username.length() <= 10) {
				if (username.length() >= 4) {
					if (!DataContainer.userExists(username)) {
						String password = PasswordField.readPassword("*Enter password:");
						if (password.length() <= 10) {
							if (password.length() >= 4) {
							while (wrongPass) {
								String verification = PasswordField.readPassword("*Verify password:");
								if (verification.equals(password)) {
									AdminMethods.newUser(username, password, 1);
									String date = User1Methods.getDate();
									String action = "New user sign up";
									FileContainer.writeLogsToFile(date, username, action);
									System.out.println("");
									signUpRunning = false;
									wrongPass = false;
								} else {
									System.out.println("Wrong password\n");
									counter++;
									if (counter == 3) {
										signUpRunning = false;
										wrongPass = false;
									}
								}
							}
						} else {
							System.out.println("Password's min length is 4 characters\n");
							counter++;
							if (counter == 3) {
								signUpRunning = false;
								wrongPass = false;
							}
						}
						} else {
							System.out.println("Password's max length is 10 characters\n");
							counter++;
							if (counter == 3) {
								signUpRunning = false;
								wrongPass = false;
							}
						}						
					} else {
						System.out.println("Username already exists\n");
						counter++;
						if (counter == 3) {
							signUpRunning = false;
							wrongPass = false;
						}
					}
				} else {
					System.out.println("Username's min length is 4 characters\n");
					counter++;
					if (counter == 3) {
						signUpRunning = false;
						wrongPass = false;
					}
				}
			} else {
				System.out.println("Username's max length is 10 characters\n");
				counter++;
				if (counter == 3) {
					signUpRunning = false;
					wrongPass = false;
				}
			}
		}
	}

	public void logIn() {
		boolean loginRunning = true;
		int counter = 0;
		System.out.println("Insert username:");
		String username = sc.next();
		if (DataContainer.userExists(username)) {
			while (loginRunning) {
				String password = PasswordField.readPassword("*Insert password:");
				if (password.equals(DataContainer.getUserPass(username))) {
					System.out.println("\nHello " + username + "!");
					String date = User1Methods.getDate();
					String action = "Log in";
					FileContainer.writeLogsToFile(date, username, action);
					int id = DataContainer.getUserID(username);
					secondMenu(id);
					loginRunning = false;
				} else {
					System.out.println("Wrong password\n");
					counter++;
					if (counter == 3) {
						loginRunning = false;
					}
				}
			}
		} else {
			System.out.println("User does not exist\n");
			if (counter == 3) {
				loginRunning = false;
			}
		}
	}

	public void secondMenu(int userid) {
		secondMenuRunning = true;
		String username = DataContainer.getUserName(userid);
		int role = DataContainer.getUserRole(username);
		while (secondMenuRunning) {
			if (role == 1) {
				printMenu1();
				runMenu1(userid);
			} else if (role == 2) {
				printMenu2();
				runMenu2(userid);
			} else if (role == 3) {
				printMenu3();
				runMenu3(userid);
			} else if (role == 4) {
				printAdminMenu();
				runAdminMenu();
			}
		}
	}

	public void printMenu1() {
		System.out.println("\nWhat do you want to do?");
		System.out.println("[1]Create message");
		System.out.println("[2]View messages");
		System.out.println("[3]Log out");
	}

	public void printMenu2() {
		System.out.println("\nWhat do you want to do?");
		System.out.println("[1]Create message");
		System.out.println("[2]View messages");
		System.out.println("[3]Edit username");
		System.out.println("[4]Edit password");
		System.out.println("[5]Log out");
	}

	public void printMenu3() {
		System.out.println("\nWhat do you want to do?");
		System.out.println("[1]Create message");
		System.out.println("[2]View messages");
		System.out.println("[3]Delete message");
		System.out.println("[4]Edit username");
		System.out.println("[5]Edit password");
		System.out.println("[6]Log out");
	}

	public void printAdminMenu() {
		System.out.println("\nWhat do you want to do?");
		System.out.println("[1]Create message");
		System.out.println("[2]View messages");
		System.out.println("[3]Delete message");
		System.out.println("[4]View Users");
		System.out.println("[5]Edit user's role");
		System.out.println("[6]Create new user");
		System.out.println("[7]Delete user");
		System.out.println("[8]Log out");
	}

	public void runMenu1(int userid) {
		String answer = sc.next();
		String username = DataContainer.getUserName(userid);
		switch (answer) {
		case "1":
			User1Methods.sendMsg(username);
			break;
		case "2":
			User1Methods.viewMsgs(username);
			break;
		case "3":
			String date = User1Methods.getDate();
			String action = "Log out";
			FileContainer.writeLogsToFile(date, username, action);
			secondMenuRunning = false;
			break;
		default:
			System.out.println("You inserted wrong data\n");
		}
	}

	public void runMenu2(int userid) {
		String answer = sc.next();
		String username = DataContainer.getUserName(userid);
		switch (answer) {
		case "1":
			User1Methods.sendMsg(username);
			break;
		case "2":
			User1Methods.viewMsgs(username);
			break;
		case "3":
			User2Methods.editUsername(username);
			break;
		case "4":
			User2Methods.editPassword(username);
			break;
		case "5":
			String date = User1Methods.getDate();
			String action = "Log out";
			FileContainer.writeLogsToFile(date, username, action);
			secondMenuRunning = false;
			break;
		default:
			System.out.println("You inserted wrong data\n");
		}
	}

	public void runMenu3(int userid) {
		String answer = sc.next();
		String username = DataContainer.getUserName(userid);
		switch (answer) {
		case "1":
			User1Methods.sendMsg(username);
			break;
		case "2":
			User1Methods.viewMsgs(username);
			break;
		case "3":
			User3Methods.deleteMessage(username);
			break;
		case "4":
			User2Methods.editUsername(username);
			break;
		case "5":
			User2Methods.editPassword(username);
			break;
		case "6":
			String date = User1Methods.getDate();
			String action = "Log out";
			FileContainer.writeLogsToFile(date, username, action);
			secondMenuRunning = false;
			break;
		default:
			System.out.println("You inserted wrong data\n");
		}
	}

	public void runAdminMenu() {
		String answer = sc.next();
		String username = "admin";
		switch (answer) {
		case "1":
			User1Methods.sendMsg(username);
			break;
		case "2":
			User1Methods.viewMsgs(username);
			break;
		case "3":
			User3Methods.deleteMessage(username);
			break;
		case "4":
			AdminMethods.viewUsers();
			break;
		case "5":
			AdminMethods.editUserRole();
			break;
		case "6":
			AdminMethods.createUser();
			break;
		case "7":
			AdminMethods.deleteUser();
			break;
		case "8":
			String date = User1Methods.getDate();
			String action = "Log out";
			FileContainer.writeLogsToFile(date, "admin", action);
			secondMenuRunning = false;
			break;
		default:
			System.out.println("You inserted wrong data\n");
		}
	}

	public static void main(String[] args) {

		DBConnector.start();
		DataContainer.initialise();
		System.out.println("Welcome!\n");
		m.firstMenu();

	}

}
