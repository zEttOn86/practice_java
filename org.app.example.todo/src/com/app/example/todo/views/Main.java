package com.app.example.todo.views;



import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.part.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import com.app.example.todo.models.ToDo;
import com.app.example.todo.models.TodoCellModifier;
import com.app.example.todo.models.TodoLabelProvider;

import org.eclipse.wb.swt.ResourceManager;

public class Main extends ViewPart {
	public Main() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.app.example.todo.Main"; //$NON-NLS-1$

	@Inject
	IWorkbench workbench;
	private Text txtTodo;
	private Button btnAddingTodo;
	private Table table;
	private Label lblHorizonLine;
	private TableViewer tv;

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
		btnAddingTodo.setText(Messages.Main_btnAddingTodo); //$NON-NLS-1$
		Image img = SWTResourceManager.getImage(this.getClass(), "/icons/includeMode_filter.png"); //$NON-NLS-1$
		btnAddingTodo.setImage(img);
		btnAddingTodo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String txt = txtTodo.getText();
				if(txt.equals("")) {
					return;
				}
				// テキストボックスをクリア
				// TODO: Mediatorパターンの実装でもいいのかもしれない。
				txtTodo.setText("");
				
				// 新しいTODOを追加する。
				tv.add((Object)new ToDo(txt));
			}
		});
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

		tv = new TableViewer(body, SWT.MULTI | SWT.VIRTUAL | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		// テーブルの生成
		table = tv.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		tableLayout.addColumnData(new ColumnWeightData(100, 50, false));
		tableLayout.addColumnData(new ColumnWeightData(100, 50, false));
		tableLayout.addColumnData(new ColumnWeightData(200, 100, false));
		tableLayout.addColumnData(new ColumnWeightData(200, 100, false));
		tableLayout.addColumnData(new ColumnWeightData(700, 500, true));

		// セルの境界線を可視にする
		table.setLinesVisible(true);
		// ヘッダを可視にする
		table.setHeaderVisible(true);

		TableColumn col = new TableColumn(table, SWT.LEFT);
		col.setText(Messages.Main_col1DoneStr);
		
		col = new TableColumn(table, SWT.LEFT);
		col.setText(Messages.Main_col2PriorityStr);

		col = new TableColumn(table, SWT.LEFT);
		col.setText(Messages.Main_col3CompletedStr);

		col = new TableColumn(table, SWT.LEFT);
		col.setText(Messages.Main_col4CreatedStr);

		col = new TableColumn(table, SWT.LEFT | SWT.FILL);
		col.setText(Messages.Main_col5TodoDescriptionStr);

		// コンテンツプロバイダの設定
		// コンテントプロバイダは、TableViewerのsetInputで与えられたオブジェクトを元にテーブルの行に相当する配列を返す処理をします。
		tv.setContentProvider(new ArrayContentProvider());
		
		// ラベルプロバイダの設定
		// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/DemonstratesTableViewers.htm
        tv.setLabelProvider(new TodoLabelProvider());
        
        // カラムを識別するためのプロパティ
        // https://thinkit.co.jp/cert/tech/11/4/2.htm
        String[] properties = new String [] {
        		"is_completed", "priority", "created_date", "completed_date", "todo_desc"
        };
        tv.setColumnProperties(properties);
        
        // ComboBoxCelEditorに表示するアイテム
        String[] priorityItems = new String[] {
        		"A", "B", "C", "D"
        };

        // セルエディタの生成
        CellEditor[] cellEditors = new CellEditor[] {
        		new CheckboxCellEditor(table),
        		new ComboBoxCellEditor(table, priorityItems),
        		new TextCellEditor(table),
        		new TextCellEditor(table),
        		new TextCellEditor(table),
        };
        
        // セルエディタの設定
        tv.setCellEditors(cellEditors);
        
        // セルモディファイアの設定
        tv.setCellModifier(new TodoCellModifier(tv, priorityItems));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
	}

}
