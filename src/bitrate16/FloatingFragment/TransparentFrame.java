package bitrate16.FloatingFragment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Transparent Frame class created default JFrame with transparency preset. This
 * window it click-through and fully transparent
 * 
 * @author bitrate16
 *
 */
public class TransparentFrame extends JFrame {
	private static final long	serialVersionUID	= -644039683930087964L;
	private JPanel				attachedPanel;

	/**
	 * Creates new Transparent Frame with given panel inside
	 * 
	 * @param panel
	 */
	public TransparentFrame(TransparentPanel panel) {
		panel.parentFrame = this;
		this.attachedPanel = panel;
		// Remove taskbar icon
		this.setType(javax.swing.JFrame.Type.UTILITY);
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void setFrametSize(int width, int height) {
		this.setSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		if (attachedPanel != null) {
			attachedPanel.setSize(getSize());
			attachedPanel.setPreferredSize(getPreferredSize());
		}
	}

	/**
	 * Transparent panel, could be inserted into current frame and painted
	 * 
	 * @author bitrate16
	 *
	 */
	public static class TransparentPanel extends JPanel {
		private static final long	serialVersionUID	= -6377384200748501435L;
		protected JFrame			parentFrame;

		public TransparentPanel() {
			setOpaque(false);
			setLayout(new GridBagLayout());
		}

		public JFrame getParentFrame() {
			return parentFrame;
		}
	}
}
