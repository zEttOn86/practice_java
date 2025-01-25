package dev.kitsutsuki.practice.gettingstarted.starting;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
//https://github.com/eclipse-platform/eclipse.platform.ui/blob/master/docs/JFaceDataBinding.md
public class GettingStarted {

	static Model model = new Model();

	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			public void run() {
				Shell shell = new Shell(display);
				shell.setSize(201, 160);
				init(shell);
				shell.pack();
				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
			}
		});
		display.dispose();
	}

	static void init(Shell shell) {
		Text text = new Text(shell, SWT.BORDER);
		Label label = new Label(shell, SWT.NONE);

		Button button = new Button(shell, SWT.PUSH);
		button.setText("Double!");
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				model.setAmount(model.getAmount() * 2);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Label errorLabel = new Label(shell, SWT.NONE);

		DataBindingContext dbc = new DataBindingContext();

		IObservableValue modelObservable = BeanProperties.value("amount").observe(model);

		Binding b = dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(text), modelObservable);

		dbc.bindValue(WidgetProperties.text().observe(label), modelObservable, null, null);
		
//		dbc.bindValue(WidgetProperties.text().observe(errorLabel),
//				new AggregateValidationStatus(dbc.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
		dbc.bindValue(WidgetProperties.text().observe(errorLabel),b.getValidationStatus(),null, null);

		GridLayoutFactory.swtDefaults().generateLayout(shell);
	}

	static class Model {
		private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			changeSupport.addPropertyChangeListener(propertyName, listener);
		}

		public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			changeSupport.removePropertyChangeListener(propertyName, listener);
		}

		private int amount = 0;

		public void setAmount(int newAmount) {
			int oldAmount = this.amount;
			this.amount = newAmount;
			changeSupport.firePropertyChange("amount", oldAmount, newAmount);
		}

		public int getAmount() {
			return amount;
		}
	}
}