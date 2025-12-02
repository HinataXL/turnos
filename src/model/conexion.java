/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    
    // Configuración de tu base de datos (XAMPP por defecto)
    private static final String URL = "jdbc:mysql://localhost:3306/rrhh_db"; // Cambia 'rrhh_db' por el nombre real de tu BD
    private static final String USER = "root";
    private static final String PASS = ""; // En XAMPP suele ser vacío

    public static Connection getConexion() {
        try {
            // Cargar el driver (necesario en versiones antiguas de Java, opcional en nuevas pero recomendado)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer conexión
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}