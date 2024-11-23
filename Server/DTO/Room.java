package pbl4.Server.DTO;

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
	private String teachername;
	private Map<String, Integer> studentNums = new ConcurrentHashMap<>();
	private Map<Integer, ClientModel> students = new ConcurrentHashMap<>();
	private Map<Integer, Integer> forFocus = new HashMap<>();
	private int focusAddress;
	private ArrayList<String> chatHistory = new ArrayList<>();
	private Queue<Integer> quittedStudents = new LinkedList<>();
	private Queue<Map.Entry<Integer, String>> names = new LinkedList<>();
	private Queue<String> keys = new LinkedList<>();

	public Room(InetAddress address, int udpPort, String name) {
		this.teacher = new ClientModel(0);
		this.address = address;
		this.udpPort = udpPort;
		this.teachername = name;
		this.focusAddress = -1;
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

	public Queue<Map.Entry<Integer, String>> getNames() {
		return names;
	}

	public void setNames(Queue<Map.Entry<Integer, String>> names) {
		this.names = names;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public Queue<String> getKeys() {
		return keys;
	}

	public void setKeys(Queue<String> keys) {
		this.keys = keys;
	}

}
