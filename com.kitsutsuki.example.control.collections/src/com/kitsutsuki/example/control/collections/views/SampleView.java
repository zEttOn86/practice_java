package com.kitsutsuki.example.control.collections.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;

import com.kitsutsuki.example.control.collections.widgets.CanvasProgressBar;

import org.eclipse.ui.*;
import jakarta.inject.Inject;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {
	public SampleView() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.kitsutsuki.example.control.collections.views.SampleView";

	@Inject
	IWorkbench workbench;

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		/**
		 * Canvas
		 */
		setCanvasProgressBar(composite);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setText("New Button");
		
	}
	
	private void setCanvasProgressBar(Composite parent) {
		CanvasProgressBar progressBar = new CanvasProgressBar(parent, SWT.SMOOTH);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		progressBar.setState(SWT.NORMAL);
		
		new Thread(()->{
			for(int i = 0; i<=100; i++) {
				try {
					Thread.sleep(100);
				}catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				final int finalI = i;
				Display.getDefault().asyncExec(()->{
					progressBar.setSelection(finalI);
				});
			}
		}).start();
		
		
		CanvasProgressBar marqueeProgressBar = new CanvasProgressBar(parent, SWT.INDETERMINATE);
		marqueeProgressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		marqueeProgressBar.setState(SWT.NORMAL);
	}

	@Override
	public void setFocus() {

	}
}
