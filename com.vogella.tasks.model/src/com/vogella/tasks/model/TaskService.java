package com.vogella.tasks.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface TaskService {
	/**
	 * Get all tasks
	 */
	List<Task> getAll();
	
	/**
	 * Updates existing task or create new task
	 * 
	 * @param task
	 * @return
	 */
	boolean update(Task task);
	
	/**
	 * Returns an Optional wrapping a task or wrapping null if not task exists for the given id
	 * 
	 * @param task
	 * @return empty optional if not found, otherwise Optional holder the task
	 */
	Optional<Task> get(long id);
	
	/**
	 * Deletes the task with the id
	 * @param id
	 * @return true if task existed and was deleted, false otherwise
	 */
	boolean delete(long id);
	
	/**
	 * Allow to specify a consumer which retrieves all tasks
	 * @param tasksConsumer
	 */
	void consume(Consumer<List<Task>> tasksConsumer);
}
