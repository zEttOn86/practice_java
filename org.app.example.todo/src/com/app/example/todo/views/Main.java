package com.app.example.todo.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.part.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import javax.inject.Inject;
import org.eclipse.swt.widgets.TableColumn;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;

public class Main extends ViewPart {
	public Main() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.app.example.todo.views.Main";

	@Inject
	IWorkbench workbench;
	private Text txtTodo;
	private Button btnAddingTodo;
	private Table table;
	private Label lblHorizonLine;

	@Override
	public void createPartControl(Composite parent) {
		Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(8, true);
		body.setLayout(layout);

		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);

		txtTodo = new Text(body, SWT.BORDER);
		txtTodo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		txtTodo.setMessage(Messages.Main_txtTodoMsg);

		btnAddingTodo = new Button(body, SWT.NONE);
		btnAddingTodo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddingTodo.setText(Messages.Main_btnAddingTodo);
		Image img = SWTResourceManager.getImage(this.getClass(), "/icons/includeMode_filter.png");
		btnAddingTodo.setImage(img);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);

		lblHorizonLine = new Label(body, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label = new GridData(SWT.FILL, SWT.FILL, true, false, 8, 1);
		gd_label.widthHint = 554;
		lblHorizonLine.setLayoutData(gd_label);

		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);
		new Label(body, SWT.NONE);

//		table = new Table(body, SWT.BORDER | SWT.FULL_SELECTION);
//		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
//		table.setHeaderVisible(true);
//		table.setLinesVisible(false);
		TableViewer tv = new TableViewer(body, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		table = tv.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		
		TableColumn col1 = new TableColumn(table, SWT.LEFT);
        col1.setText("名前");
        col1.setWidth(100);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
	}

}
