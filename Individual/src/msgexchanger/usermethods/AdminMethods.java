package msgexchanger.usermethods;

import java.util.Scanner;

import msgexchanger.objects.User;
import msgexchanger.passwordmask.PasswordField;
import msgexchanger.tools.DBConnector;
import msgexchanger.tools.DataContainer;
import msgexchanger.tools.FileContainer;

public class AdminMethods extends User1Methods {

	static Scanner sc = new Scanner(System.in);

	public AdminMethods() {

	}

	public static void createUser() {
		boolean createUserRunning = true;
		int counter = 0;
		while (createUserRunning) {
			System.out.println("Insert new user's username");
			String username = sc.next();
			if (username.length() <= 10) {
				if (username.length() >= 4) {
					if (DataContainer.userExists(username)) {
						System.out.println("Choose different username\n");
						counter++;
						if (counter == 3) {
							createUserRunning = false;
						}
					} else {
						System.out.println("*Insert new user's password");
						String password = PasswordField.readPassword("");
						if (password.length() <= 10) {
							if (password.length() >= 4) {
								System.out.println("*Verify password");
								String verify = PasswordField.readPassword("");
								if (verify.equals(password)) {
									System.out.println(
											"Insert new user's role\n[1]View User\n[2]View-Edit User\n[3]View-Edit-Delete User");
									if (sc.hasNextInt()) {
										int role = sc.nextInt();
										switch (role) {
										case 1:
											newUser(username, password, role);
											createUserRunning = false;
											break;
										case 2:
											newUser(username, password, role);
											createUserRunning = false;
											break;
										case 3:
											newUser(username, password, role);
											createUserRunning = false;
											break;
										default:
											System.out.println("Wrong data\n");
											counter++;
											if (counter == 3) {
												createUserRunning = false;
											}
										}
									} else {
										System.out.println("Wrong input\n");
										counter++;
										if (counter == 3) {
											createUserRunning = false;
										}

									}

								} else {
									System.out.println("Wrong data\n");
									counter++;
									if (counter == 3) {
										createUserRunning = false;
									}
								}
							} else {
								System.out.println("Password's min length is 4 characters\n");
								counter++;
								if (counter == 3) {
									createUserRunning = false;
								}
							}
						} else {
							System.out.println("Password's max length is 10 characters\n");
							counter++;
							if (counter == 3) {
								createUserRunning = false;
							}
						}
					}
				} else {
					System.out.println("Username's min length is 4 characters\n");
					counter++;
					if (counter == 3) {
						createUserRunning = false;
					}
				}
			} else {
				System.out.println("Username's max length is 10 characters\n");
				counter++;
				if (counter == 3) {
					createUserRunning = false;
				}
			}
		}
	}

	public static void deleteUser() {
		boolean delUserRunning = true;
		int counter = 0;
		while (delUserRunning) {
			System.out.println("Insert username of user you want to delete\n");
			String username = sc.next();
			if (DataContainer.userExists(username)) {
				System.out.println("Are you sure you want to delete user?\n[Y]Yes\n[N]No");
				String ans = sc.next();
				switch (ans.toLowerCase()) {
				case "y":
					DBConnector.deleteUserFromTable(username);
					DataContainer.getUsers().remove(DataContainer.getUserIndex(username));
					System.out.println("Record is deleted!");
					String date = User1Methods.getDate();
					String action = "Deleted user " + username;
					FileContainer.writeLogsToFile(date, "admin", action);
					delUserRunning = false;
					break;
				case "n":
					break;
				default:
					System.out.println("Wrong data\n");
					counter++;
					if (counter == 3) {
						delUserRunning = false;
					}
				}
			} else {
				System.out.println("Wrong data\n");
				counter++;
				if (counter == 3) {
					delUserRunning = false;
				}
			}
		}
	}

	public static void viewUsers() {
		for (int i = 0; i < DataContainer.getUsers().size(); i++) {
			System.out.println(DataContainer.getUsers().get(i).toString());
		}
		String date = User1Methods.getDate();
		String action = "Viewed all users";
		FileContainer.writeLogsToFile(date, "admin", action);
	}

	public static void editUserRole() {
		boolean editRoleRunning = true;
		int counter = 0;
		while (editRoleRunning) {
			System.out.println("Insert username of user you want to update");
			String username = sc.next();
			if (DataContainer.userExists(username)) {
				System.out.println("Choose new role:\n[1]View User\n[2]View-Edit User\n[3]View-Edit-Delete User");
				;
				if (sc.hasNextInt()) {
					int role = sc.nextInt();
					switch (role) {
					case 1:
						updateRole(username, role);
						editRoleRunning = false;
						break;
					case 2:
						updateRole(username, role);
						editRoleRunning = false;
						break;
					case 3:
						updateRole(username, role);
						editRoleRunning = false;
						break;
					default:
						System.out.println("Wrong data");
						counter++;
						if (counter == 3) {
							editRoleRunning = false;
						}
					}
				} else {
					System.out.println("Wrong input");
					counter++;
					if (counter == 3) {
						editRoleRunning = false;
					}
				}
			} else {
				System.out.println("Wrong data.");
				counter++;
				if (counter == 3) {
					editRoleRunning = false;
				}
			}
		}
	}

	public static void newUser(String username, String password, int role) {
		DBConnector.insertIntoUsers(username, password, role);
		int id = DBConnector.getLastUserIDfromDB();
		User n = new User(id, username, password, role);
		DataContainer.getUsers().add(n);
		System.out.println("New user created!");
		String date = User1Methods.getDate();
		String action = "Created new user with username: " + username + " and role #" + role;
		FileContainer.writeLogsToFile(date, "admin", action);
	}

	private static void updateRole(String username, int newrole) {
		int index = DataContainer.getUserIndex(username);
		int id = DataContainer.getUsers().get(index).getUserID();
		DataContainer.getUsers().get(index).setRole(newrole);
		DBConnector.updateUsersTable(newrole, id);
		System.out.println("User role updated!");
		String date = User1Methods.getDate();
		String action = "Updated user's: " + username + " role to #" + newrole;
		FileContainer.writeLogsToFile(date, "admin", action);
	}

}
