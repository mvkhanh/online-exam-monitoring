package pbl4.Server.DTO;

public class ClientModel {
	private long time;
	private int studentNum;

	public ClientModel(int studentNum) {
		super();
		this.time = System.currentTimeMillis();
		this.studentNum = studentNum;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

}
