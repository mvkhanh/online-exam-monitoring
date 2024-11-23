package pbl4.Client.DTO.OutContest;

public class Participant {
	private int id;
	private int test_id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTest_id() {
		return test_id;
	}
	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Participant(int id, int test_id, String name) {
		super();
		this.id = id;
		this.test_id = test_id;
		this.name = name;
	}
	
	
}
