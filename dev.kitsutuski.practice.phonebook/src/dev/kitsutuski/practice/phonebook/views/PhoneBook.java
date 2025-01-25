package dev.kitsutuski.practice.phonebook.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import dev.kitsutuski.practice.phonebook.views.model.Person;
import dev.kitsutuski.practice.phonebook.views.model.PhoneGroup;
import dev.kitsutuski.practice.phonebook.views.model.PhoneGroups;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.jface.action.*;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import java.util.List;

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
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;

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

public class PhoneBook extends ViewPart {
	public PhoneBook() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "dev.kitsutuski.practice.phonebook.views.PhoneBook";

	@Inject
	IWorkbench workbench;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Table table;
	private Table table_1;
	private Text m_nameText;
	private Text m_emailText;
	private Text m_phoneText;
	private Text m_mobile1Text;
	private Text m_mobile2Text;
	private PhoneGroups m_groups = new PhoneGroups();
	private TableViewer m_personViewer;
	private TableViewer m_groupViewer;
	private DataBindingContext m_bindingContext;
	private Button newGroupButton;
	private Button deleteGroupButton;
	private Button editGroupButton;
	private Button deletePersonButton;
	private PhoneGroup group3;

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

	public void setDefaultValues() {
		PhoneGroup group1 = new PhoneGroup("Developer Team");
		m_groups.addGroup(group1);
		group1.addPerson(new Person("Konstantin Scheglov", "kosta@nospam.com", "1234567890", "", ""));
		group1.addPerson(new Person("Alexander Mitin", "mitin@nospam.com", "", "0987654321", ""));
		group1.addPerson(new Person("Alexander Lobas", "lobas@nospam.com", "", "", "111-222-333-00"));
		//
		PhoneGroup group2 = new PhoneGroup("Management Team");
		m_groups.addGroup(group2);
		group2.addPerson(new Person("Mike Taylor", "taylor@instantiations.com", "503-598-4900", "", ""));
		group2.addPerson(new Person("Eric Clayberg", "clayberg@instantiations.com", "+1 (503) 598-4900", "", ""));
		group2.addPerson(new Person("Dan Rubel", "dan@instantiations.com", "503-598-4900", "", ""));
		//
		PhoneGroup group3 = new PhoneGroup("Support Team");
		m_groups.addGroup(group3);
		group3.addPerson(new Person("Gina Nebling", "support@instantiations.com", "800-808-3737", "", ""));
	}

	@Override
	public void createPartControl(Composite parent) {
		setDefaultValues();
		createContents(parent);

	}

	protected void createContents(Composite parent) {
		SashForm sashForm = new SashForm(parent, SWT.NONE);

		Composite groupComposite = new Composite(sashForm, SWT.NONE);
		GridLayout gl_groupComposite = new GridLayout(1, false);
		gl_groupComposite.verticalSpacing = 0;
		groupComposite.setLayout(gl_groupComposite);

		Composite groupToolBarComposite = new Composite(groupComposite, SWT.NONE);
		groupToolBarComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groupToolBarComposite.setLayout(new GridLayout(3, false));

		newGroupButton = new Button(groupToolBarComposite, SWT.NONE);
		newGroupButton.setBounds(0, 0, 75, 25);
		newGroupButton.setText("New...");

		editGroupButton = new Button(groupToolBarComposite, SWT.NONE);
		editGroupButton.setText("Edit");

		deleteGroupButton = new Button(groupToolBarComposite, SWT.NONE);
		deleteGroupButton.setText("Delete");

		m_groupViewer = new TableViewer(groupComposite, SWT.NONE);
		table_1 = m_groupViewer.getTable();
		table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		table_1 = new Table(groupComposite, SWT.BORDER | SWT.FULL_SELECTION);
//		table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		table_1.setHeaderVisible(true);
//		table_1.setLinesVisible(true);

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

		deletePersonButton = new Button(personToolBar, SWT.NONE);
		deletePersonButton.setText("Delete");

		m_personViewer = new TableViewer(personComposite, SWT.FULL_SELECTION);
		table = m_personViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

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

		m_mobile2Text = new Text(detailComposite, SWT.BORDER);
		m_mobile2Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		personSashForm.setWeights(new int[] { 1, 1 });
		sashForm.setWeights(new int[] { 220, 617 });

		m_bindingContext = initDataBindings();
	}

	protected DataBindingContext initDataBindings() {
		// Set up the data binding
		DataBindingContext bindingContext = new DataBindingContext();

		// Bind single selection of m_personViewer to text fields
//		PhoneGroup group1 = (PhoneGroup) m_groups.getGroups().get(0);
//		ViewerSupport.bind(m_personViewer, new WritableList<>(group1.getPersons(), Person.class),
//				BeanProperties.value(Person.class, "name"));
		IObservableValue<Person> personViewerSelection = ViewerProperties.singleSelection(Person.class)
				.observe(m_personViewer);
		bindingContext.bindValue(BeanProperties.value("name").observeDetail(personViewerSelection),
				WidgetProperties.text(SWT.Modify).observe(m_nameText));
		bindingContext.bindValue(BeanProperties.value("email").observeDetail(personViewerSelection),
				WidgetProperties.text(SWT.Modify).observe(m_emailText));
		bindingContext.bindValue(BeanProperties.value("phone").observeDetail(personViewerSelection),
				WidgetProperties.text(SWT.Modify).observe(m_phoneText));
		bindingContext.bindValue(BeanProperties.value("mobilePhone1").observeDetail(personViewerSelection),
				WidgetProperties.text(SWT.Modify).observe(m_mobile1Text));
		bindingContext.bindValue(BeanProperties.value("mobilePhone2").observeDetail(personViewerSelection),
				WidgetProperties.text(SWT.Modify).observe(m_mobile2Text));
//            
		// Bind selection index of tables to button enabled states
//            bindingContext.bindValue(
//                WidgetProperties.singleSelectionIndex().observe(table_1),
//                WidgetProperties.enabled().observe(editGroupButton),
//                new SelectionUpdateValueStrategy(),
//                new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)
//            );
//            bindingContext.bindValue(
//                WidgetProperties.singleSelectionIndex().observe(table_1),
//                WidgetProperties.enabled().observe(deleteGroupButton),
//                new SelectionUpdateValueStrategy(),
//                new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)
//            );
//            bindingContext.bindValue(
//                WidgetProperties.singleSelectionIndex().observe(table),
//                WidgetProperties.enabled().observe(deletePersonButton),
//                new SelectionUpdateValueStrategy(),
//                new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)
//            );
//
//            // Set up content provider and input for m_groupViewer
            ObservableListContentProvider groupContentProvider = new ObservableListContentProvider();
            m_groupViewer.setContentProvider(groupContentProvider);

            IValueProperty  groupLabelProperty = BeanProperties.value("name");
            IObservableMap groupLabelMaps = groupLabelProperty.observeDetail(groupContentProvider.getKnownElements());
            m_groupViewer.setLabelProvider(new ObservableMapLabelProvider(groupLabelMaps));

            IObservableList<?> groupList = BeanProperties.list("groups").observe(m_groups);
            m_groupViewer.setInput(groupList);

            // Set up content provider and input for m_personViewer
            ObservableListContentProvider personContentProvider = new ObservableListContentProvider();
            m_personViewer.setContentProvider(personContentProvider);

            IValueProperty[] personLabelProperty = BeanProperties.values(new String[] {
                  "name", "email", "phone", "mobilePhone1", "mobilePhone2"}
                  );
            IObservableMap nameLabelMap = personLabelProperty[0].observeDetail(personContentProvider.getKnownElements());
            IObservableMap emailLabelMap = personLabelProperty[1].observeDetail(personContentProvider.getKnownElements());
            IObservableMap phoneLabelMap = personLabelProperty[2].observeDetail(personContentProvider.getKnownElements());
            IObservableMap mobilePhone1LabelMap = personLabelProperty[3].observeDetail(personContentProvider.getKnownElements());
            IObservableMap mobilePhone2LabelMap = personLabelProperty[4].observeDetail(personContentProvider.getKnownElements());
            IObservableMap[] personLabelMaps = {nameLabelMap, emailLabelMap, phoneLabelMap, mobilePhone1LabelMap, mobilePhone2LabelMap};

            m_personViewer.setLabelProvider(new ObservableMapLabelProvider(personLabelMaps));

            IObservableList<?> personList = BeanProperties.list("persons").observeDetail(
                ViewerProperties.singleSelection().observe(m_groupViewer)
            );
            m_personViewer.setInput(personList);

		return bindingContext;
	}

	@Override
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}
