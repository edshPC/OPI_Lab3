package database;

public class SQLRequests {
    public static final String CREATE_TABLES = """
            CREATE TABLE IF NOT EXISTS points(
                id SERIAL PRIMARY KEY,
                x REAL NOT NULL,
                y REAL NOT NULL,
                r REAL NOT NULL,
                result BOOLEAN NOT NULL,
                current_time TEXT NOT NULL,
                execution_time BIGINT NOT NULL
            );
            """;

    public static final String ADD_POINT = """
            INSERT INTO points(x, y, r, result, current_time, execution_time)
            VALUES (?, ?, ?, ?, ?, ?);""";

    public static final String GET_POINTS = """
            SELECT * FROM points;
            """;

    public static final String CLEAR_COLLECTION = """
            TRUNCATE TABLE points;
            """;
}
