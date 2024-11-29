package pbl4.Client.DTO.OutContest;

import java.util.List;

import javax.swing.JLabel;

public class VideoModel {

	public List<byte[]> videoData;
	public Integer totalFrame, fps, typeVideo;
	public JLabel label;
	public String name;

	public VideoModel(List<byte[]> videoData, Integer totalFrame, Integer fps, Integer typeVideo) {
		super();
		this.videoData = videoData;
		this.totalFrame = totalFrame;
		this.fps = fps;
		this.typeVideo = typeVideo;
		
		label = new JLabel();
	}

	public VideoModel() {
		super();
		
		label = new JLabel();
	}

	
}
