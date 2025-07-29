package util;

import java.sql.*;

public class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:db/estudiante.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement()) {

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS planes (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  tipo TEXT NOT NULL,
                  tarifaBase REAL NOT NULL,
                  minutosNacionales INTEGER,
                  costoMinutoNacional REAL,
                  minutosInternacionales INTEGER,
                  costoMinutoInternacional REAL,
                  megasEnGB REAL,
                  costoPorGB REAL,
                  porcentajeDescuento REAL
                );
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS clientes (
                  cedula TEXT PRIMARY KEY,
                  nombre TEXT NOT NULL,
                  ciudad TEXT NOT NULL,
                  email TEXT NOT NULL,
                  fechaNacimiento TEXT NOT NULL,
                  marca TEXT,
                  modelo TEXT,
                  numero TEXT,
                  planPrincipalId INTEGER,
                  planSecundarioId INTEGER,
                  FOREIGN KEY(planPrincipalId) REFERENCES planes(id),
                  FOREIGN KEY(planSecundarioId) REFERENCES planes(id)
                );
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS facturas (
                  numero TEXT PRIMARY KEY,
                  cedulaCliente TEXT NOT NULL,
                  nombreCliente TEXT NOT NULL,
                  total REAL NOT NULL,
                  fechaGeneracion TEXT NOT NULL,
                  FOREIGN KEY(cedulaCliente) REFERENCES clientes(cedula)
                );
            """);

        } catch (SQLException e) {
            System.err.println("Error inicializando la BD: " + e.getMessage());
        }
    }

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
