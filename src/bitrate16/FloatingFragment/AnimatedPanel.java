package bitrate16.FloatingFragment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

public class AnimatedPanel extends AttachedPanel implements ActionListener {
	private static final long	serialVersionUID	= 1L;
	private int					delayFrames			= 000;
	// Current frame index
	private int					frame				= 0;
	private double				scale				= 1;
	private BufferedImage[]		source				= null;
	private Timer				renderTimer;

	public AnimatedPanel() {
		setFramesDelay(delayFrames);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		int fWidth = (int) (source[frame].getWidth() * scale);
		int fHeight = (int) (source[frame].getHeight() * scale);
		if (source != null && frame < source.length)
			g2d.drawImage(source[frame], 0, 0, fWidth, fHeight, null);

		if (source.length == 0) {
			g2d.setColor(Color.RED);
			g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}

		g2d.dispose();

		frame++;
		if (frame >= source.length)
			frame = 0;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			if (arg0.getSource() == renderTimer)
				repaint();
		} catch (Exception e) {}
	}

	/**
	 * Set's source array of images, automatically generates flipped copy
	 * 
	 * @param source
	 */
	public void setSource(BufferedImage[] source) {
		this.source = source;
		if (this.source != null && this.source.length <= 1) {
			if (this.renderTimer != null)
				this.renderTimer.stop();
			this.repaint();
			return;
		}
		frame = 0;
		if (source != null && source.length > 0)
			setPreferredSize(new Dimension((int) (source[0].getWidth() * scale), (int) (source[0].getWidth() * scale)));
	}

	/**
	 * Set's delay between animation frames
	 * 
	 * @param delayFrames
	 */
	public void setFramesDelay(int delayFrames) {
		this.delayFrames = delayFrames;
		if (this.source != null && this.source.length <= 1) {
			this.repaint();
			return;
		}
		if (this.renderTimer != null)
			this.renderTimer.stop();
		this.renderTimer = new Timer(delayFrames, this);
		this.renderTimer.start();
	}

	/**
	 * Set's animation glyph scale
	 * 
	 * @param scale
	 */
	public void setScale(double scale) {
		this.scale = scale <= 0 ? 1 : scale;
		if (source != null && source.length > 0)
			setPreferredSize(new Dimension((int) (source[0].getWidth() * scale), (int) (source[0].getWidth() * scale)));
	}

	/**
	 * Set size from dimension, assuming dimension is resolution of the original
	 * image
	 * 
	 * @param size
	 */
	@Override
	public void setSize(Dimension size) {
		if (size == null)
			return;
		super.setSize(new Dimension((int) (size.getWidth() * scale), (int) (size.getHeight() * scale)));
	}

	/**
	 * Set size from dimension, assuming dimension is resolution of the original
	 * image
	 * 
	 * @param size
	 */
	@Override
	public void setPreferredSize(Dimension size) {
		if (size == null)
			return;
		super.setPreferredSize(new Dimension((int) (size.getWidth() * scale), (int) (size.getHeight() * scale)));
	}

	public void dispose() {
		renderTimer.stop();
		source = null;
	}
}
