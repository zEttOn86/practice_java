package dev.kitsutuski.practice.phonebook.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import javax.inject.Inject;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class PhoneBook extends ViewPart {
	public PhoneBook() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "dev.kitsutuski.practice.phonebook.views.PhoneBook";

	@Inject IWorkbench workbench;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Table table;
	private Table table_1;
	private Text m_nameText;
	private Text m_emailText;
	private Text m_phoneText;
	private Text m_mobile1Text;
	private Text text_3;


	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	@Override
	public void createPartControl(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.NONE);

		Composite composite = new Composite(sashForm, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.verticalSpacing = 0;
		composite.setLayout(gl_composite);

												Composite groupToolBarComposite = new Composite(composite, SWT.NONE);
												groupToolBarComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
												groupToolBarComposite.setLayout(new GridLayout(3, false));

														Button newGroupButton = new Button(groupToolBarComposite, SWT.NONE);
														newGroupButton.setBounds(0, 0, 75, 25);
														newGroupButton.setText("New...");

																Button editGroupButton = new Button(groupToolBarComposite, SWT.NONE);
																editGroupButton.setText("Edit");

																		Button deleteGroupButton = new Button(groupToolBarComposite, SWT.NONE);
																		deleteGroupButton.setText("Delete");

																						table_1 = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
																						table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
																						table_1.setHeaderVisible(true);
																						table_1.setLinesVisible(true);

		SashForm personSashForm = new SashForm(sashForm, SWT.VERTICAL);

		Composite personComposite = new Composite(personSashForm, SWT.NONE);
		GridLayout gl_personComposite = new GridLayout(1, false);
		gl_personComposite.verticalSpacing = 0;
		gl_personComposite.horizontalSpacing = 0;
		personComposite.setLayout(gl_personComposite);

		Composite personToolBar = new Composite(personComposite, SWT.NONE);
		personToolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		personToolBar.setLayout(new GridLayout(2, false));

		Button newPersonButton = new Button(personToolBar, SWT.NONE);
		newPersonButton.setBounds(0, 0, 75, 25);
		newPersonButton.setText("New...");

		Button deletePersonButton = new Button(personToolBar, SWT.NONE);
		deletePersonButton.setText("Delete");

		table = new Table(personComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(123);
		tblclmnNewColumn.setText("Name");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(168);
		tblclmnNewColumn_1.setText("E-mail");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(119);
		tblclmnNewColumn_2.setText("Phone");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("Mobile Phone 1");

		TableColumn tableCoblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tableCoblclmnNewColumn_4.setWidth(100);
		tableCoblclmnNewColumn_4.setText("Mobile Phone 2");

		Composite detailComposite = new Composite(personSashForm, SWT.NONE);
		detailComposite.setLayout(new GridLayout(2, false));

		Label desciptionLabel = new Label(detailComposite, SWT.NONE);
		desciptionLabel.setBounds(0, 0, 55, 15);
		desciptionLabel.setText("Description:");
		new Label(detailComposite, SWT.NONE);

		Label lblName = new Label(detailComposite, SWT.NONE);
		lblName.setText("Name:");

		m_nameText = new Text(detailComposite, SWT.BORDER);
		m_nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label emailLabel = new Label(detailComposite, SWT.NONE);
		emailLabel.setText("E-mail:");

		m_emailText = new Text(detailComposite, SWT.BORDER);
		m_emailText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label phoneLabel = new Label(detailComposite, SWT.NONE);
		phoneLabel.setText("Phone:");

		m_phoneText = new Text(detailComposite, SWT.BORDER);
		m_phoneText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label mobilePhone1Label = new Label(detailComposite, SWT.NONE);
		mobilePhone1Label.setText("Mobile Phone 1:");

		m_mobile1Text = new Text(detailComposite, SWT.BORDER);
		m_mobile1Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label mobilePhone2Label = new Label(detailComposite, SWT.NONE);
		mobilePhone2Label.setText("Mobile Phone 2:");

		text_3 = new Text(detailComposite, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		personSashForm.setWeights(new int[] {1, 1});
		sashForm.setWeights(new int[] {220, 617});
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				PhoneBook.this.fillContextMenu(manager);
			}
		});
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}


	private void hookDoubleClickAction() {
	}

	@Override
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}
