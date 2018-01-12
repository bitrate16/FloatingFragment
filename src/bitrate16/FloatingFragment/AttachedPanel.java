package bitrate16.FloatingFragment;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import bitrate16.FloatingFragment.TransparentFrame.TransparentPanel;

public class AttachedPanel extends TransparentPanel implements MouseListener, MouseMotionListener {
	private static final long	serialVersionUID		= -3218864942359170336L;

	private boolean				movable					= true;
	private boolean				dclick_always_on_top	= true;
	private boolean				tclick_close			= true;

	private Point				initialClick;

	public AttachedPanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public boolean isDoubleClickChangeAlwaysOnTop() {
		return dclick_always_on_top;
	}

	public void setDoubleClickChangeAlwaysOnTop(boolean dclick_always_on_top) {
		this.dclick_always_on_top = dclick_always_on_top;
	}

	public boolean isTripleClickCloses() {
		return tclick_close;
	}

	public void setTripleClickCloses(boolean tclick_close) {
		this.tclick_close = tclick_close;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && isDoubleClickChangeAlwaysOnTop()) {
			this.getParentFrame().setAlwaysOnTop(!this.getParentFrame().isAlwaysOnTop());
		} else if (e.getClickCount() == 3 && isTripleClickCloses()) {
			this.getParentFrame().dispatchEvent(new WindowEvent(this.getParentFrame(), WindowEvent.WINDOW_CLOSING));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		initialClick = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!isMovable())
			return;
		JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
		int thisX = parent.getLocation().x;
		int thisY = parent.getLocation().y;

		int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
		int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

		int X = thisX + xMoved;
		int Y = thisY + yMoved;
		parent.setLocation(X, Y);
	}
}
