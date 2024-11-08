package PBL4.Server.Model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Room {
	private AtomicInteger newStudentId = new AtomicInteger(0);
	private ClientModel teacher;
	private InetAddress address;
	private int udpPort;
	private int id;
	private Map<String, Integer> studentNums = new ConcurrentHashMap<>(); //UDP: InetAddress + udpPort -> studentNum
	private Map<Integer, ClientModel> students = new ConcurrentHashMap<>(); //TCP: InetAddress + tcpPort -> ClientModel
	private Map<Integer, Integer> forFocus = new HashMap<>(); //studentNum -> InetAddress + tcpPort
	private int focusAddress; //InetAddress + tcpPort
	private ArrayList<String> chatHistory = new ArrayList<>();
	private Queue<Integer> quittedStudents = new LinkedList<>();

	public Room(InetAddress address, int udpPort, String name, int id) {
		this.teacher = new ClientModel(0);
		this.address = address;
		this.udpPort = udpPort;
		this.id = id;
	}

	public AtomicInteger getNewStudentId() {
		return newStudentId;
	}

	public void setNewStudentId(AtomicInteger newStudentId) {
		this.newStudentId = newStudentId;
	}

	public ArrayList<String> getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(ArrayList<String> chatHistory) {
		this.chatHistory = chatHistory;
	}

	public Queue<Integer> getQuittedStudents() {
		return quittedStudents;
	}

	public void setQuittedStudents(Queue<Integer> quittedStudents) {
		this.quittedStudents = quittedStudents;
	}

	public ClientModel getTeacher() {
		return teacher;
	}

	public void setTeacher(ClientModel teacher) {
		this.teacher = teacher;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, Integer> getStudentNums() {
		return studentNums;
	}

	public void setStudentNums(Map<String, Integer> studentNums) {
		this.studentNums = studentNums;
	}

	public Map<Integer, ClientModel> getStudents() {
		return students;
	}

	public void setStudents(Map<Integer, ClientModel> students) {
		this.students = students;
	}

	public Map<Integer, Integer> getForFocus() {
		return forFocus;
	}

	public void setForFocus(Map<Integer, Integer> forFocus) {
		this.forFocus = forFocus;
	}

	public int getFocusAddress() {
		return focusAddress;
	}

	public void setFocusAddress(int focusAddress) {
		this.focusAddress = focusAddress;
	}

}
