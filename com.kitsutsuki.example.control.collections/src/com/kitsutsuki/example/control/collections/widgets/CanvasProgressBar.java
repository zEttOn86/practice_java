package com.kitsutsuki.example.control.collections.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * https://github.com/eclipse-platform/eclipse.platform.swt/blob/master/bundles/org.eclipse.swt/Eclipse%20SWT/gtk/org/eclipse/swt/widgets/ProgressBar.java
 * https://github.com/diffblue/eclipse-cbmc/blob/master/org.eclipse.cbmc.ui/src/org/eclipse/internal/cbmc/view/ProgressBar.java
 */
public class CanvasProgressBar extends Canvas {
	private static final int DEFAULT_WIDTH = 160;
	private static final int DEFAULT_HEIGHT = 20;

	private int state = SWT.NORMAL;
	private int style = SWT.SMOOTH;
	private int progressBarColor = SWT.COLOR_GREEN;

	/**
	 * Variables for smooth style progress bar.
	 */
	private int maximum = 100;
	private int minimum = 0;
	private int selection = 0;

	/**
	 * Variable for marquee style progress bar
	 */
	private int offset = 0;
	private final int MARQUEE_WIDTH = 120;
	private final int UPDATE_MS_DELAY = 50;
	private final int UPDATE_STEP = 5;

	public CanvasProgressBar(Composite parent, int style) {
		super(parent, SWT.NO_FOCUS | SWT.DOUBLE_BUFFERED);

		setStyle(style);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				drawProgressBar(e);
			}
		});

		if (style == SWT.INDETERMINATE) {
			Runnable animator = new Runnable() {
				@Override
				public void run() {
					if (isDisposed()) {
						return;
					}
					offset += UPDATE_STEP;
					if (offset % (getSize().x + MARQUEE_WIDTH) == 0) {
						offset = 0;
					}
					redraw();
					getDisplay().timerExec(UPDATE_MS_DELAY, this);
				}
			};

			getDisplay().timerExec(UPDATE_MS_DELAY, animator);
		}
	}

	private void drawProgressBar(PaintEvent e) {
		Rectangle area = getClientArea();
		int width = area.width;
		int height = area.height;

		// Draw background;
		e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		e.gc.fillRectangle(0, 0, width, height);

		// Calculate fill width based on maximum
		Color fillColor = getDisplay().getSystemColor(progressBarColor);
		e.gc.setBackground(fillColor);
		switch (style) {
		case SWT.SMOOTH:
			double ratio = Math.min(1.0, selection / (double) maximum);
			int fillWidth = (int) Math.round(width * ratio);
			e.gc.fillRectangle(0, 0, fillWidth, height);
			break;
		case SWT.INDETERMINATE:
			int x = offset % (width + MARQUEE_WIDTH);
			e.gc.fillRectangle(x - MARQUEE_WIDTH, 0, MARQUEE_WIDTH, height);
			break;
		}

		// Draw Border
		e.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		e.gc.drawRectangle(0, 0, width - 1, height - 1);
	}

	public int getSelection() {
		checkWidget();
		return selection;
	}

	public void setSelection(int value) {
		checkWidget();
		this.selection = Math.max(minimum, Math.min(maximum, value));
		redraw();
	}

	public int getMinimum() {
		checkWidget();
		return minimum;
	}

	public void setMinimum(int value) {
		checkWidget();
		if (value < 0 || value >= maximum)
			return;

		this.minimum = value;
		this.selection = Math.max(selection, minimum);
		redraw();
	}

	public int getMaximum() {
		checkWidget();
		return maximum;
	}

	public void setMaximum(int value) {
		checkWidget();
		if (value <= minimum)
			return;

		this.maximum = value;
		this.selection = Math.min(selection, maximum);
		redraw();
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();

		int width = (wHint == SWT.DEFAULT) ? DEFAULT_WIDTH : wHint;
		int height = (hHint == SWT.DEFAULT) ? DEFAULT_HEIGHT : hHint;
		return new Point(width, height);
	}

	public void setState(int state) {
		checkWidget();
		switch (state) {
		case SWT.NORMAL:
			this.state = state;
			this.progressBarColor = SWT.COLOR_GREEN;
			break;
		case SWT.ERROR:
			this.state = state;
			this.progressBarColor = SWT.COLOR_RED;
			break;
		case SWT.PAUSED:
			this.state = state;
			this.progressBarColor = SWT.COLOR_YELLOW;
			break;
		}
	}

	public int getState() {
		checkWidget();
		return state;
	}

	private void setStyle(int style) {
		checkWidget();
		switch (style) {
		case SWT.SMOOTH:
			this.style = style;
			break;
		case SWT.INDETERMINATE:
			this.style = style;
			break;
		default:
			throw new RuntimeException("Unsupport style are selected");
		}
	}

}
