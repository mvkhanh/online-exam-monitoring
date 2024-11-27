package pbl4.Client.DTO.InContest;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ScreenImageDTO {
	public BufferedImage img;
	public Graphics2D g2d;
	
	public ScreenImageDTO(int width, int height) {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	}
}
