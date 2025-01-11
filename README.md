# Online Exam Monitoring System

**A desktop application built with Java Swing for online exam monitoring.**  
This project is designed to showcase my programming skills in networking, multithreading, and desktop application development.  

---

## **Overview**

The **Online Exam Monitoring System** is a multithreaded Java application that uses **TCP** and **UDP** protocols to enable real-time monitoring of online exams. It features a client-server architecture to facilitate secure and efficient communication between **invigilators** and **students**. 

### **Key Features**
- **Real-time Monitoring**:
  - Invigilators can view students' screen shares and camera feeds in real-time.
  - Keyboard logs are captured and sent to the server every 5 minutes for analysis.
- **Data Recording & Playback**:
  - Exam sessions are recorded, including screen shares, camera feeds, and keyboard logs.
  - Invigilators can replay past sessions, view recorded data, and simulate students' keystroke logs.
- **Client-Server Architecture**:
  - **Server**:
    - Centralized data processing and storage.
    - Manages connections and data streams from multiple students and invigilators.
  - **Client**:
    - **Invigilator Interface**: Access monitoring tools and playback features.
    - **Student Interface**: Screen, camera, and keyboard monitoring components.

---

## **Technical Highlights**

- **Java Swing**:  
  Developed a user-friendly desktop application with a responsive graphical interface.
  
- **Networking**:  
  Utilized **TCP** for reliable messaging and **UDP** for high-speed data transmission.

- **Multithreading**:  
  Implemented multithreaded handlers to manage concurrent client connections, ensuring smooth and scalable communication.

- **Data Handling**:  
  - Captured screen images and camera frames using **OpenCV**.
  - Logged keystrokes in real-time with **JNativeHook**.
  - Handled media storage and replay with **FFmpeg**.

- **Scalable Design**:  
  Designed to support multiple clients (students and invigilators) simultaneously.

---

## **Project Architecture**

1. **Server**:  
   - Manages TCP/UDP communication with clients.  
   - Stores and organizes monitoring data (screenshots, video feeds, and keyboard logs).

2. **Client**:  
   - **Invigilator**:
     - Real-time monitoring of students.  
     - Access to recorded data with playback and keystroke simulation tools.  
   - **Student**:
     - Sends screen captures, camera feed, and keyboard logs to the server.

---

## **How to Run**

1. **Requirements**:
   - Java 11+ installed.
   - OpenCV, JNativeHook, and FFmpeg libraries included in the classpath.

2. **Setup**:
   - Clone the repository:
     ```bash
     git clone https://github.com/your-username/online-exam-monitoring.git
     cd online-exam-monitoring
     ```
   - Compile and run the server:
     ```bash
     java -cp lib/*: src/server/ServerMain.java
     ```
   - Compile and run the client (as an invigilator or student):
     ```bash
     java -cp lib/*: src/client/ClientMain.java
     ```
     
---
