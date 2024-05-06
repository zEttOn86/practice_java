package com.app.example.todo.models;

import java.util.Date;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.app.example.todo.models.ToDo.PriorityRank;

/**
* データとセルエディタを関連付けるためのクラス
* @see https://thinkit.co.jp/cert/tech/11/4/2.htm
*/
public class TodoCellModifier implements ICellModifier {
	
	private TableViewer viewer_;
	private String[] priorityItems_;
	
	public TodoCellModifier(TableViewer viewer, String[] evaluationItems) {
		viewer_ = viewer;
		priorityItems_ = evaluationItems;
	}


	/**
	 * 編集可能かを返すメソッド
	 * @return true = 編集可能, false = 編集負荷
	 */
	@Override
	public boolean canModify(Object arg0, String arg1) {
		switch (arg1) {
		case "is_completed": 
			return true;
		case "priority":
			return true;
		case "created_date":
			return false;
		case "completed_date":
			return false;
		case "todo_desc":
			return true;
		default:
			throw new IllegalArgumentException("Unexpected value: " + arg1);
		}
	}

	/**
	 * エディタに表示するデータを返す
	 * @param arg0 選択された行に対応するデータ
	 * @param arg1 選択されたカラムのプロパティ
	 * @return 表示する値
	 */
	@Override
	public Object getValue(Object arg0, String arg1) {
		ToDo todoData = (ToDo)arg0;

		switch (arg1) {
		case "is_completed": 
			return todoData.getIsCompleted();
		case "priority":
			return todoData.getPriorityRank().getId();
		case "created_date":
			if(todoData.getCompletedDate() == null) {
				return null;
			}
			return todoData.getCompletedDate().toString();
		case "completed_date":
			if(todoData.getCreatedDate() == null) {
				return null;
			}
			return todoData.getCreatedDate().toString();
		case "todo_desc":
			return todoData.getToDoDescription();
		default:
			throw new IllegalArgumentException("Unexpected value: " + arg1);
		}
	}

	/**
	 * エディタの変更通知を受け取る
	 * @param arg0 エレメント
	 * @param arg1 プロパティ
	 * @param arg2　value
	 */
	@Override
	public void modify(Object arg0, String arg1, Object arg2) {
		TableItem tableItem = (TableItem)arg0;
		ToDo todoData = (ToDo) tableItem.getData();
		
		// プロパティに応じて返す値を設定する
	    // プロパティはtableViewer.setColumnProperties(properties);で設定したものに対応する
		switch (arg1) {
		case "is_completed": 
			todoData.setIsCompleted((boolean) arg2);
			break;
		case "priority":
			String priorityStr = priorityItems_[((Integer)arg2).intValue()];
			todoData.setPriority(PriorityRank.valueOf(priorityStr));
			break;
		case "created_date":
			try {
				todoData.setCreatedDate((Date) arg2);
			}catch (Exception e){
				e.printStackTrace();
			}
			break;
		case "completed_date":
			try {
				todoData.setCompletedDate((Date) arg2);
			}catch (Exception e){
				e.printStackTrace();
			}
			break;
		case "todo_desc":
			todoData.setToDoDescription((String) arg2);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + arg1);
		}
		
		// テーブルビューアを更新
		viewer_.update(todoData, null);
	}

}
