package pbl4.Client.DTO.InContest.Teacher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageModel {
	private int total;
	private Map<Integer, Packet> data = new ConcurrentHashMap<>();
	private long time = System.currentTimeMillis();
	private int studentNum;

	public ImageModel() {

	}

	public ImageModel(int total, long time) {
		this.total = total;
		this.time = time;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Map<Integer, Packet> getData() {
		return data;
	}

	public void setData(Map<Integer, Packet> data) {
		this.data = data;
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
