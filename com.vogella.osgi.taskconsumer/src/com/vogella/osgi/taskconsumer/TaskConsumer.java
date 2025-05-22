package com.vogella.osgi.taskconsumer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.vogella.tasks.model.TaskService;

@Component
public class TaskConsumer {
	
	@Reference
	TaskService taskService;
	
	@Activate
	public void activate() {
		System.out.println("Activate called");
		System.out.println("Number of tasks: " + taskService.getAll().size());
	}
}
