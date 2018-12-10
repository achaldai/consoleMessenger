package msgexchanger.objects;

public class Message {
	
	int msgID;
	String date;
	int senderID;
	int receiverID;
	String data;
	

	public Message() {
		
	}
	
	public Message(int msgID, String date, int senderID, int receiverID, String data) {
		this.msgID = msgID;
		this.date = date;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.data = data;
	}

	public int getMsgID() {
		return msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;		
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String toString() {
		String message = data+"'\n";
		return message;
	}
}
