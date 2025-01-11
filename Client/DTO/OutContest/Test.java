package pbl4.Client.DTO.OutContest;

import java.util.Date;

public class Test {
	private int id;
	private int user_id;
	private String name;
	private Date created_at;

	// Constructor matching fields
	public Test(int id, int user_id, String name, Date created_at) {
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.created_at = created_at;
	}

	// Getters for each field
	public int getId() {
		return id;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getName() {
		return name;
	}

	public Date getCreated_at() {
		return created_at;
	}

}
