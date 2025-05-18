package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        DatabaseManager.init(); // Инициализация базы данных
        copyright.developer();


        System.out.println("Добро пожаловать в Менеджер Задач!");


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1 - Вывести задачи");
            System.out.println("2 - Добавить задачу");
            System.out.println("3 - Изменить статус");
            System.out.println("4 - Удалить задачу");
            System.out.println("0 - Выход");
            System.out.print("> "); // указатель на ввод пользователя

            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1" -> {
                    System.out.println("Все задачи");
                    List<Map<String, String>> tasks = DatabaseManager.getAllTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("Задачи отсутствуют");
                    } else {
                        for (Map<String, String> task : tasks) {
                            System.out.println("№ " + task.get("id"));
                            System.out.println("Имя: " + task.get("name"));
                            System.out.println("Описание: " + task.get("desc"));
                            System.out.println("Статус: " + task.get("status"));
                        }
                    }
                }
                case "2" -> {
                    System.out.println("Создание задачи");
                    System.out.print("Введите имя задачи: ");
                    String taskName = scanner.nextLine();

                    System.out.print("Введите описание задачи: ");
                    String taskDesc = scanner.nextLine();

                    String taskStatus = "";
                    boolean flagStatus = false;
                    while (flagStatus != true) {
                        System.out.println("Выберите статус задачи: ");
                        System.out.println("1 - OPEN");
                        System.out.println("2 - CLOSED");
                        System.out.print("> ");
                        String userStatus = scanner.nextLine();
                        switch (userStatus) {
                            case "1" -> {
                                taskStatus = "OPEN";
                                flagStatus = true;
                            }
                            case "2" -> {
                                taskStatus = "CLOSED";
                                flagStatus = true;
                            }
                            default -> {
                                System.out.println("Такая команда не поддерживается");
                            }
                        }
                    }

                    // Добавление задачи в базу данных
                    DatabaseManager.addTask(taskName, taskDesc, taskStatus);
                }
                case "3" -> {
                    System.out.println("Обновление задачи");
                    System.out.print("Введите номер задачи для обновления: ");
                    int taskId = scanner.nextInt();
                    scanner.nextLine();  // Считывание символа новой строки

                    System.out.print("Введите новый статус задачи: ");
                    String taskStatus = scanner.nextLine();

                    // Обновление статуса задачи в базе данных
                    DatabaseManager.updateTaskStatus(taskId, taskStatus);
                }
                case "4" -> {
                    System.out.println("Удаление задачи");
                    System.out.print("Введите номер нужной задачи: ");

                    int taskId = scanner.nextInt();
                    scanner.nextLine();  // Очистка буфера после nextInt()

                    // Получаем задачу из БД
                    Map<String, String> task = DatabaseManager.getTaskById(taskId);

                    if (task == null) {
                        System.out.println("Задача с таким номером не найдена.");
                    } else {
                        // Вывод информации о задаче
                        System.out.println("Найденная задача:");
                        System.out.println("№ " + task.get("id"));
                        System.out.println("Имя: " + task.get("name"));
                        System.out.println("Описание: " + task.get("desc"));
                        System.out.println("Статус: " + task.get("status"));

                        // Подтверждение удаления
                        System.out.print("Вы уверены, что хотите удалить эту задачу? (Y/N): ");
                        String confirmation = scanner.nextLine().trim().toUpperCase();

                        if (confirmation.equals("Y")) {
                            // Удаление из базы данных
                            DatabaseManager.deleteTask(taskId);
                            System.out.println("Задача успешно удалена.");
                        } else {
                            System.out.println("Удаление отменено.");
                        }
                    }
                }

                case "0" -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Такая команда не поддерживается!");
            }
        }
    }
}
