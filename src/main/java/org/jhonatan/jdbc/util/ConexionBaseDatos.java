package org.jhonatan.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    //indicamos la zona horario para evitar el error de la zona horaria

    private static final String ruta = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static final String userName = "root";
    private static final String password = "1234";
    private static Connection connection;

    //patron singleton
    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            //obtenemos la conexion
            connection = DriverManager.getConnection(ruta, userName, password);
        }
        return connection;
    }
}
