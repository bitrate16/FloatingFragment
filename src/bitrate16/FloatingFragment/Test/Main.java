package bitrate16.FloatingFragment.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import bitrate16.FloatingFragment.AnimatedPanel;
import bitrate16.FloatingFragment.TransparentFrame;
import bitrate16.FloatingFragment.utils.GIFUnpacker;

public class Main {
	public static final void main(String[] args) throws IOException {

		// Default scale is 1

		double scale = 1;

		try {
			scale = Double.parseDouble(System.getProperty("frame_size"));
		} catch (Exception e) {}

		// Default animation delay is 100

		int frame_delay = 100;
		boolean auto_frame_delay = false;

		try {
			String prop = System.getProperty("frame_delay");
			if (prop == "auto" || prop == "-1")
				auto_frame_delay = true;
			frame_delay = prop == "auto" ? 100 : Integer.parseInt(prop);
		} catch (Exception e) {}

		// Default location is /res directory

		String source_location = System.getProperty("source_location");
		source_location = source_location == null ? "res" : source_location;

		// Default source type is single image

		String source_type = System.getProperty("source_type");
		BufferedImage[] source_frames = null;

		if ("image_set".equals(source_type)) {
			// Image set
			source_frames = null;

			ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

			File[] list = new File(source_location).listFiles();
			int i = 0;

			while (i < list.length) {
				try {
					images.add(ImageIO.read(list[i++]));
				} catch (Exception e) {}
			}
			images.toArray(source_frames = new BufferedImage[images.size()]);

			if (source_frames == null || source_frames[0] == null)
				error("No input images");

			for (i = 0; i < source_frames.length; i++) {
				AnimatedPanel a = new AnimatedPanel();
				a.setSource(new BufferedImage[] { source_frames[i] });
				a.setScale(scale);
				a.setFramesDelay(frame_delay);
				TransparentFrame f = new TransparentFrame(a);

				f.setFrametSize(source_frames[i].getWidth(), source_frames[i].getHeight());
				f.setAlwaysOnTop(true);
			}
		} else {
			if (source_type == null || source_type.equals("image")) {
				// Single image
				source_frames = new BufferedImage[1];

				File[] list = new File(source_location).listFiles();
				int i = 0;

				while (source_frames[0] == null && i < list.length) {
					try {
						source_frames[0] = ImageIO.read(list[i++]);
					} catch (Exception e) {}
				}
			} else if (source_type.equals("gif")) {
				// Gif animation
				source_frames = new BufferedImage[1];

				File[] list = new File(source_location).listFiles(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return f.getName().endsWith(".gif");
					}
				});
				int i = 0;

				while (source_frames[0] == null && i < list.length) {
					try {
						source_frames = GIFUnpacker.unpackGIF(list[i]);
						if (auto_frame_delay)
							frame_delay = GIFUnpacker.frameDelay(list[i]);
					} catch (Exception e) {}
				}
			} else if (source_type.equals("frame_set")) {
				// Frame set
				source_frames = new BufferedImage[1];

				ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

				File[] list = new File(source_location).listFiles();
				int i = 0;

				while (i < list.length) {
					try {
						images.add(ImageIO.read(list[i++]));
					} catch (Exception e) {}
				}

				images.toArray(source_frames = new BufferedImage[images.size()]);
			}

			if (source_frames == null || source_frames[0] == null)
				error("No input images");

			AnimatedPanel a = new AnimatedPanel();
			a.setSource(source_frames);
			a.setScale(scale);
			a.setFramesDelay(frame_delay);
			TransparentFrame f = new TransparentFrame(a);

			f.setFrametSize(source_frames[0].getWidth(), source_frames[0].getHeight());
			f.setAlwaysOnTop(true);
		}
	}

	public static void error(String message) {
		System.err.println(message);
		System.exit(1);
	}
}
