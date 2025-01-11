package pbl4.Client.DTO.InContest.Teacher;

public class Packet {
	private int length;
	private byte[] data;

	public Packet(int length, byte[] data) {
		super();
		this.length = length;
		this.data = data;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}