package msgexchanger.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import msgexchanger.objects.Message;
import msgexchanger.objects.User;

public class DBConnector {
	private static Connection conn;

	public static void start() {
		try {

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/message_exchanger?autoReconnect=true&useSSL=false", "root",
					"123456789");
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void closeDBConnection() throws SQLException {
		conn.close();
	}

	public static Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		DBConnector.conn = conn;
	}

	public static ArrayList<Message> getMsgsFromDB() throws SQLException {

		String selectSQL = "SELECT * FROM message_exchanger.messages;";
		PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(selectSQL);
		ResultSet rs = preparedStatement.executeQuery(selectSQL);
		ArrayList<Message> msgList = new ArrayList<Message>();
		while (rs.next()) {
			int ID = rs.getInt("msg_id");
			String date = rs.getString("date_submit"); // lise provlima datetime
			int senderid = rs.getInt("sender_id");
			int receiverid = rs.getInt("receiver_id");
			String msgdata = rs.getString("msg_data");
			Message msg = new Message(ID, date, senderid, receiverid, msgdata);
			msgList.add(msg);
		}
		return msgList;
	}

	public static ArrayList<User> getUsersFromDB() throws SQLException {

		String selectSQL = "SELECT * FROM message_exchanger.users;";
		PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(selectSQL);
		ResultSet rs = preparedStatement.executeQuery(selectSQL);
		ArrayList<User> userList = new ArrayList<User>();
		while (rs.next()) {
			int ID = rs.getInt("user_id");
			String username = rs.getString("user_name");
			String pass = rs.getString("user_pass");
			int role = rs.getInt("user_role");
			User user = new User(ID, username, pass, role);
			userList.add(user);
		}
		return userList;
	}

	public static void insertIntoUsers(String username, String password, int role) {

		String insertTableSQL = "insert into users\r\n" + "values (null, ?, ?, ?);";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = DBConnector.getConn().prepareStatement(insertTableSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setInt(3, role);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static int getLastUserIDfromDB() {

		String lastuseridSQL = "SELECT max(user_id) from message_exchanger.users;";
		int ID = -1;
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(lastuseridSQL);
			ResultSet rs = preparedStatement.executeQuery(lastuseridSQL);
			while (rs.next()) {
				int userID = rs.getInt("max(user_id)");
				ID = userID;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return ID;
	}

	public static String getUserNameFromDB(int userid) {
		String useridSQL = "select user_name from message_exchanger.users where user_id = ? ;";
		String username = "";
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(useridSQL);
			preparedStatement.setInt(1, userid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("user_name");
				username = name;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return username;
	}
	
	public static String getPassFromDB(int userid) {
		String useridSQL = "select user_pass from message_exchanger.users where user_id = ? ;";
		String password = "";
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(useridSQL);
			preparedStatement.setInt(1, userid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pass = rs.getString("user_pass");
				password = pass;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return password;
	}

	public static void insertIntoMessages(String date, int senderid, int receiverid, String data) {

		String insertTableSQL = "insert into messages\r\n" + "values (null, ?, ?, ?, ?);";
		PreparedStatement preparedStatement;

		try {
			preparedStatement = DBConnector.getConn().prepareStatement(insertTableSQL);
			preparedStatement.setString(1, date);
			preparedStatement.setInt(2, senderid);
			preparedStatement.setInt(3, receiverid);
			preparedStatement.setString(4, data);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static int getLastMsgIDfromDB() {

		String lastmessageidSQL = "SELECT max(msg_id) from message_exchanger.messages;";
		int ID = -1;
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(lastmessageidSQL);
			ResultSet rs = preparedStatement.executeQuery(lastmessageidSQL);
			while (rs.next()) {
				int msgID = rs.getInt("max(msg_id)");
				ID = msgID;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return ID;
	}

	public static void deleteUserFromTable(String username) {
		String deleteSQL = "delete from users where user_id = ?;";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = DBConnector.getConn().prepareStatement(deleteSQL);
			preparedStatement.setInt(1, DataContainer.getUserID(username));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void deleteMessageFromTable(int msgid) {
		String deleteSQL = "delete from messages where msg_id = ?;";
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(deleteSQL);
			preparedStatement.setInt(1, msgid);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public static void deleteMsgOfUserFromTable(int user1id, int user2id) {
		String deleteSQL = "delete from message_exchanger.messages where (sender_id=? and receiver_id=?) or (sender_id=? and receiver_id=?);";
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(deleteSQL);
			preparedStatement.setInt(1, user1id);
			preparedStatement.setInt(2, user2id);
			preparedStatement.setInt(3, user2id);
			preparedStatement.setInt(4, user1id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void deleteInboxFromTable(int userid) {
		String deleteSQL = "delete from messages where receiver_id = ? ;";
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(deleteSQL);
			preparedStatement.setInt(1, userid);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public static void deleteOutboxFromTable(int userid){	
		String deleteSQL = "delete from messages where sender_id = ? ;";				
		try {
			PreparedStatement preparedStatement = DBConnector.getConn().prepareStatement(deleteSQL);			
			preparedStatement.setInt(1, userid);	
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}							
	}

	public static void updateUsersTable(int role, int id) {
		String updateSQL = "update users\r\n" + "set user_role = ?\r\n" + "where user_id = ? ;";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = DBConnector.getConn().prepareStatement(updateSQL);
			preparedStatement.setInt(1, role);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void updateUserNameInTable(String newusername, int id) {
		String updateSQL = "update users set user_name = ? where user_id = ? ;";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = DBConnector.getConn().prepareStatement(updateSQL);
			preparedStatement.setString(1, newusername);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void updateUserPassInTable(String newpass, int id) {
		String updateSQL = "update users set user_pass = ? where user_id = ? ;";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = DBConnector.getConn().prepareStatement(updateSQL);
			preparedStatement.setString(1, newpass);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
