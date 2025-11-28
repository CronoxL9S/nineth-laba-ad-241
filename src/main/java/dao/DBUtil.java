package dao;

import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:sqlite:construction.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            conn.setAutoCommit(false);

            st.executeUpdate("CREATE TABLE IF NOT EXISTS suppliers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "contact TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS materials (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "supplier_id INTEGER, " +
                    "price REAL NOT NULL CHECK (price >= 0), " +
                    "FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE SET NULL)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "specialization TEXT, " +
                    "phone TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS projects (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "client TEXT NOT NULL, " +
                    "start_date TEXT NOT NULL, " +
                    "end_date TEXT, " +
                    "status TEXT NOT NULL)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "project_id INTEGER NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "employee_id INTEGER, " +
                    "material_id INTEGER, " +
                    "material_qty REAL NOT NULL DEFAULT 0, " +
                    "cost REAL NOT NULL CHECK (cost >= 0), " +
                    "status TEXT NOT NULL, " +
                    "start_date TEXT, " +
                    "end_date TEXT, " +
                    "FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL, " +
                    "FOREIGN KEY (material_id) REFERENCES materials(id) ON DELETE SET NULL)");

            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_projects_client ON projects(client)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_materials_name ON materials(name)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_tasks_end_date ON tasks(end_date)");

            conn.commit();
            System.out.println("База даних ініціалізована успішно!");
        } catch (SQLException ex) {
            System.err.println("Помилка ініціалізації БД: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
