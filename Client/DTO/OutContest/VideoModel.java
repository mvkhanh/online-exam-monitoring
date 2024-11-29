package pbl4.Client.DTO.OutContest;

import java.util.List;

public class VideoModel {

	public List<byte[]> videoData;
	public Integer totalFrame, fps, typeVideo;

	public VideoModel(List<byte[]> videoData, Integer totalFrame, Integer fps, Integer typeVideo) {
		super();
		this.videoData = videoData;
		this.totalFrame = totalFrame;
		this.fps = fps;
		this.typeVideo = typeVideo;
	}

	public VideoModel() {
		super();
	}

	
}
