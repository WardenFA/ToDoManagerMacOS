package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:tasks.db";

    public static Connection connect() {
        Connection conn = null;
        try {

            // Регистрация драйвера SQLite
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(DB_URL);
        }catch (ClassNotFoundException e) {
            System.err.println("Драйвер SQLite не найден! Убедитесь, что зависимость в pom.xml добавлена.");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
        return conn;
    }

    // Инициализация базы данных
    public static void init() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            Statement statement = connection.createStatement();
            // Создание таблицы, если она не существует
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, name TEXT, desc TEXT, status TEXT);");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление задачи в базу данных
    public static void addTask(String name, String desc, String status) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO tasks (name, desc, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, desc);
                pstmt.setString(3, status);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, String> getTaskById(int taskId) {
    String sql = "SELECT * FROM tasks WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, taskId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Map<String, String> task = new HashMap<>();
            task.put("id", String.valueOf(rs.getInt("id")));
            task.put("name", rs.getString("name"));
            task.put("desc", rs.getString("desc"));
            task.put("status", rs.getString("status"));
            return task;
            }
    } catch (SQLException e) {
        e.printStackTrace();
        }
    return null;
    }

    public static void deleteTask(int taskId) {
        String deleteSQL = "DELETE FROM tasks WHERE id = ?";
        String resetSQL = "UPDATE tasks SET id = id - 1 WHERE id > ?";
        String resetSequence = "DELETE FROM sqlite_sequence WHERE name='tasks'"; // Сброс счетчика автоинкремента

        try (Connection conn = connect();
             PreparedStatement pstmtDelete = conn.prepareStatement(deleteSQL);
             PreparedStatement pstmtReset = conn.prepareStatement(resetSQL);
             Statement stmtResetSeq = conn.createStatement()) {

            conn.setAutoCommit(false); // Начинаем транзакцию

            // Удаляем задачу
            pstmtDelete.setInt(1, taskId);
            pstmtDelete.executeUpdate();

            // Сдвигаем ID оставшихся задач
            pstmtReset.setInt(1, taskId);
            pstmtReset.executeUpdate();

            // Сбрасываем автоинкремент (чтобы новый ID выдавался правильно)
            stmtResetSeq.executeUpdate(resetSequence);

            conn.commit(); // Завершаем транзакцию

            System.out.println("Задача удалена, ID обновлены.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении задачи: " + e.getMessage());
        }
    }



    // Получение всех задач из базы данных
    public static List<Map<String, String>> getAllTasks() {
        List<Map<String, String>> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT id, name, desc, status FROM tasks";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Map<String, String> task = new HashMap<>();
                    task.put("id", String.valueOf(rs.getInt("id")));
                    task.put("name", rs.getString("name"));
                    task.put("desc", rs.getString("desc"));
                    task.put("status", rs.getString("status"));
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Обновление статуса задачи
    public static void updateTaskStatus(int taskId, String newStatus) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE tasks SET status = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, newStatus);
                pstmt.setInt(2, taskId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
