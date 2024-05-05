package com.app.example.todo.views;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
	
	public static String Main_btnAddingTodo;
	public static String Main_txtTodoMsg;
	public static String Main_col1DoneStr;
	public static String Main_col2PriorityStr;
	public static String Main_col3CompletedStr;
	public static String Main_col4CreatedStr;
	public static String Main_col5TodoDescriptionStr;
}
