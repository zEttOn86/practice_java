package com.app.example.todo.models;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class ToDo {
	
	private boolean isCompleted;    //0
	private PriorityRank priority;  //1
	private Date completedDate;     //2
	private Date createdDate;       //3
	private String toDoDescription; //4

	public ToDo(String toDoDescription) {
		this.isCompleted = false;
		this.priority = PriorityRank.D;
		this.createdDate = new Date();
		this.completedDate = null;
		this.toDoDescription = toDoDescription;
	}


	/**
	 * 同期しなければいけないパラメータが変更された場合、呼ばれる。
	 * 
	 * @param index 変更された変数のインデックス
	 */
	public void syncParamChanged(int index) {
		
		switch (index) {
		case 0: // when isCompleted is changed call
			if(isCompleted == true) {
				completedDate = new Date();
			}else {
				completedDate = null;
			}
			break;
		case 2: // when completedDate is changed call
			if(completedDate != null) {
				isCompleted = true;
			}else {
				isCompleted = false;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Set isCompleted value
	 * @param isCompleted
	 */
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
		syncParamChanged(0);
	}

	public void setPriority(PriorityRank priority) {
		this.priority = priority;
	}
	
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
		syncParamChanged(2);
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public void setToDoDescription(String toDoDesc) {
		this.toDoDescription = toDoDesc;
	}
	
	/**
	 * Get isCompleted value
	 * @return isCompleted
	 */
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public PriorityRank getPriorityRank() {
		return priority;
	}
	
	/**
	 * 
	 * @return null or completedDate
	 */
	public Date getCompletedDate() {
		return completedDate;
	}
	
	/*
	 * 
	 * @return null or createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public String getToDoDescription() {
		return toDoDescription;
	}
	
	
	public enum PriorityRank {
		A(0),
		B(1),
		C(2),
		D(3);

	   /**
	    * Priority id. 0 means the urgent task.
	    */
	    private int id;
	    
	    private PriorityRank(int id) {
	        this.id = id;
	    }
	    
	    public int getId() {
	        return id;
	    }
	}
}
