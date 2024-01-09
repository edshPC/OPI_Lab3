package database;

import beans.Hit;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class DatabaseManager {
    private final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/studs";
    public static final String DATABASE_URL_HELIOS = "jdbc:postgresql://pg:5432/studs";
    String dbProps = "db.cfg";
    private Connection connection;
    public boolean isConnect = false;

    public DatabaseManager() {
        try {
            this.connectToDatabase(dbProps);
            if (isConnect)
                this.createTables();
        } catch (SQLException exception) {
            System.err.println("Таблицы уже существуют.");
        }
    }

    public void connectToDatabase(String propertiesFile) {
        Properties properties = null;
        try {
            properties = new Properties();
            try (FileReader fr = new FileReader(propertiesFile)) {
                properties.load(fr);
            } catch (IOException exception) {
                System.out.println("Ошибка в чтении конфинга базы данных.");
                isConnect = false;
            }
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, properties);
            System.out.println("Соединение с базой данных установлено.");
            isConnect = true;
        } catch (SQLException exception) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL_HELIOS, properties);
            } catch (SQLException ex) {
                System.err.println("Произошла ошибка при подключении к базе данных.");
                isConnect = false;
            }
        } catch (ClassNotFoundException exception) {
            System.err.println("Драйвер управления базой данных не найден.");
            isConnect = false;
        }
    }

    public void createTables() throws SQLException {
        connection.prepareStatement(SQLRequests.CREATE_TABLES).execute();
        System.out.println("Таблицы успешно созданы.");
    }

    public void addPoint(Hit hit) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.ADD_POINT);
            ps.setFloat(1, hit.getX());
            ps.setFloat(2, hit.getY());
            ps.setFloat(3, hit.getR());
            ps.setBoolean(4, hit.getResult());
            ps.setString(5, hit.getCurrentTime());
            ps.setLong(6, hit.getExecutionTime());
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public LinkedList<Hit> loadCollection() {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.GET_POINTS);
            ResultSet resultSet = ps.executeQuery();
            LinkedList<Hit> collection = new LinkedList<>();
            while (resultSet.next()) {
                collection.add(new Hit(
                        resultSet.getLong("id"),
                        resultSet.getFloat("x"),
                        resultSet.getFloat("y"),
                        resultSet.getFloat("r"),
                        resultSet.getBoolean("result"),
                        resultSet.getString("current_time"),
                        resultSet.getLong("execution_time")
                ));
            }
            return collection;
        } catch (SQLException e) {
            System.err.println("Коллекция пуста либо возникла ошибка при исполнении запроса.");
            return new LinkedList<>();
        }
    }

    public void clearCollection() throws SQLException {
        connection.prepareStatement(SQLRequests.CLEAR_COLLECTION).execute();
        System.out.println("Коллекция успешно очищена.");
    }
}
