package msgexchanger.objects;

public class User {
	
	int userID;
	String userName;
	String pass;
	int role;

	public User() {
		
	}
	
	public User(int userID, String userName, String pass) {
		this.userID = userID;
		this.userName = userName;
		this.pass = pass;
		this.role = 0; //0=view role
	}
	public User(int userID, String userName, String pass, int role) {
		this.userID = userID;
		this.userName = userName;
		this.pass = pass;
		this.role = role;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}	
	
	public String toString() {
		String  str = pass.replaceAll("(?s).", "*");
		String user = "[ ID: " +userID+ " | USERNAME= '" +userName+ "' | PASSWORD= '" +str+ "' | ROLE= " +role+ " ]\n";
		return user;
	}

}
