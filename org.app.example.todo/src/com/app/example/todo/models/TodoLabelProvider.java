package com.app.example.todo.models;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class TodoLabelProvider implements ITableLabelProvider {

	/**
	   * Adds a listener
	   * 
	   * @param listener
	   *            the listener
	   */
	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	   * Disposes any created resources
	   */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/**
	   * Returns whether altering this property on this element will affect the
	   * label
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @return boolean
	   */
	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	   * Removes a listener
	   * 
	   * @param listener
	   *            the listener
	   */
	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	   * Returns the image
	   * 
	   * @param element
	   *            the element
	   * @param columnIndex
	   *            the column index
	   * @return Image
	   */
	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}
	
	/**
	   * Returns the column text
	   * 
	   * @param element
	   *            the element
	   * @param columnIndex
	   *            the column index
	   * @return String
	   * @see https://stackoverflow.com/questions/40605677/how-can-i-create-a-checkbox-in-a-tableviewer-of-jface
	   */
	@Override
	public String getColumnText(Object arg0, int arg1) {
		ToDo toDo = (ToDo) arg0;
		
		switch(arg1) {
		case 0:
			if(toDo.getIsCompleted()) {
				return Character.toString((char)0x2611);
			}
			else {
				return Character.toString((char)0x2610);
			}
		case 1:
			return toDo.getPriorityRank().toString();
		case 2:
			if(toDo.getCompletedDate() == null) {
				return null;
			}
			return toDo.getCompletedDate().toString();
		case 3:
			if(toDo.getCreatedDate() == null) {
				return null;
			}
			return toDo.getCreatedDate().toString();
		case 4:
			return toDo.getToDoDescription();
		}
		return null;
	}

}
