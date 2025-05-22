package com.vogella.tasks.services.internal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;

import com.vogella.tasks.model.Task;
import com.vogella.tasks.model.TaskService;

@Component(service = TaskService.class)
public class TransientTaskServiceImpl implements TaskService {

	private static AtomicInteger current = new AtomicInteger(1);
	private List<Task> tasks;

	public TransientTaskServiceImpl() {
		tasks = createTestData();
	}

	@Override
	public List<Task> getAll() {
		return tasks.stream().map(Task::copy).collect(Collectors.toList());
	}

	// create or update an existing instance of a task object
	@Override
	public synchronized boolean update(Task newTask) {

		// hold the Optional object as reference to determine, it the object is newly
		// created or not
		Optional<Task> taskOptional = findById(newTask.getId());

		// get the actual object or create a new one
		Task task = taskOptional.orElse(new Task(current.getAndIncrement()));
		task.setSummary(newTask.getSummary());
		task.setDescription(newTask.getDescription());
		task.setDone(newTask.isDone());
		task.setDueDate(newTask.getDueDate());

		if (!taskOptional.isPresent()) {
			tasks.add(task);
		}
		return true;
	}

	@Override
	public Optional<Task> get(long id) {

		return findById(id).map(Task::copy);
	}

	@Override
	public boolean delete(long id) {
		Optional<Task> deleteTask = findById(id);
		deleteTask.ifPresent(t -> tasks.remove(t));
		return deleteTask.isPresent();
	}

	@Override
	public void consume(Consumer<List<Task>> tasksConsumer) {
		// always pass a new copy of the data
		tasksConsumer.accept(tasks.stream().map(Task::copy).collect(Collectors.toList()));

	}

	private Task create(String summary, String description) {
		return new Task(current.getAndIncrement(), summary, description, false,
				LocalDate.now().plusDays(current.longValue()));
	}
	
	private Optional<Task> findById(long id){
		return tasks.stream().filter(t -> t.getId() == id).findAny();
	}
	
	// Example data, change if you like
    private List<Task> createTestData() {
        List<Task> list = List.of(
                create("Application model", "Dynamics"), create("Application model", "Flexible and extensible"),
                create("DI", "@Inject as programming mode"), create("OSGi", "Services"),
                create("SWT", "Widgets"), create("JFace", "Especially Viewers!"),
                create("CSS Styling", "Style your application"),
                create("Eclipse services", "Selection, model, Part"),
                create("Renderer", "Different UI toolkit"), create("Compatibility Layer", "Run Eclipse 3.x"));
        return new ArrayList<>(list);
    }

}
