package com.kitsutsuki.sample.todocosoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {
	private static Scanner scanner = new Scanner(System.in);
	private static List<Task> tasks = new ArrayList<>();
	private static int nextId = 1;

	public static void main(String[] args) {
		while (true) {
			showMenu();
			String choice = scanner.nextLine().trim();
			switch (choice) {
			case "1":
				viewTasks();
				break;
			case "2":
				addTask();
				break;
			case "3":
				editTask();
				break;
			case "4":
				markTaskCompleted();
				break;
			case "5":
				deleteTask();
				break;
			case "6":
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid option. Try again.");
			}
		}
	}

	private static void showMenu() {
		System.out.println("\n==TO-DO List Menu ==");
		System.out.println("1.View tasks");
		System.out.println("2.Add tasks");
		System.out.println("3.Edit tasks");
		System.out.println("4.Mark tasks as completed");
		System.out.println("5.Delete tasks");
		System.out.println("6.Exit");
		System.out.print("Choose an option:");
	}

	private static void viewTasks() {
		if (tasks.isEmpty()) {
			System.out.println("No tasks found.");
			return;
		}
		for (Task task : tasks) {
			System.out.println(task);
		}
	}

	private static void addTask() {
		System.out.println("Enter task title: ");
		String title = scanner.nextLine();
		System.out.println("Enter task description: ");
		String description = scanner.nextLine();
		Task task = new Task(nextId++, title, description);
		tasks.add(task);
		System.out.println("Task added successfully.");
	}

	private static void editTask() {
		Task task = findTaskById();
		if (task == null)
			return;

		System.out.println("Enter new title (leave empty to keep current):");
		String title = scanner.nextLine();
		if (!title.isEmpty()) {
			task.setTitle(title);
		}

		System.out.println("Enter new description (leave empty to keep current):");
		String description = scanner.nextLine();
		if (!description.isEmpty()) {
			task.setDescription(description);
		}

		System.out.println("Task updated!");
	}

	private static void markTaskCompleted() {
		Task task = findTaskById();
		if (task == null)
			return;

		task.setCompleted(true);
		System.out.println("Task marked as completed");
	}

	private static void deleteTask() {
		Task task = findTaskById();
		if (task == null)
			return;

		tasks.remove(task);
		System.out.println("Task deleted");
	}

	private static Task findTaskById() {
		System.out.println("Enter task ID: ");

		try {
			int id = Integer.parseInt(scanner.nextLine());
			for (Task task : tasks) {
				if (task.getId() == id) {
					return task;
				}
			}

			System.out.println("Task not found");
		} catch (NumberFormatException e) {
			System.out.println("Invalid ID format.");
		}

		return null;
	}
}
