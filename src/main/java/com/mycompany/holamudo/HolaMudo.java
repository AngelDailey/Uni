/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.holamudo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author Dailey
 */
public class HolaMudo {

    public static void main(String[] args) throws SQLException {
        System.out.println("=Ejecutando operaciones en tabla alumnos=");
        
        // Obtener la conexion
        Connection cn = ConexionDB.getConexion();
        if (cn == null) {
            System.out.println("No se pudo establecer la conexion");
            return;
        }

        // Declarar los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        ResultSet rs = null;

        try {
            System.out.println("\n 1. Intentando insertar nuevo Alumno...");
            String psInsert = "INSERT INTO Alumnos(nombre, correo) VALUES(?,?)";
            psInsertar = cn.prepareStatement(psInsert);
            
            // Asignaremos valor a nuestros parametros
            psInsertar.setString(1, "Lebron Jordan");
            psInsertar.setString(2, "lebron.jordan@uth.hn");
            
            // Ejecutar la insercion
            int filasAfectadas = psInsertar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("!Alumno insertado con Exito!");
                
                // Parte B: Seleccionar y visualizar datos en pantalla
                System.out.println("\n2. Consulta de lista de Alumnos en Base de Datos");
                String sqlSelect = "SELECT id, nombre, correo FROM Alumnos"; // Se corrigió 'nombres' a 'nombre'
                psSeleccionar = cn.prepareStatement(sqlSelect);
                rs = psSeleccionar.executeQuery();

                System.out.println("\n==============================================");
                System.out.println("  ID         | Nombre               | Correo    ");
                System.out.println("================================================");
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    
                    // Se cambió println a printf para que funcione el formato
                    System.out.printf("%-12d | %-20s | %s\n", id, nombre, correo);
                }
                System.out.println("================================================");
            }
        } catch (SQLException e) {
            // Se corrigió la coma por punto en System.err.println
            System.err.println("Ocurrio un error con la base de datos: " + e.getMessage());
        } finally {
            // Cerrar los recursos de manera obligatoria
            if (rs != null) {
                rs.close();
            }
            if (psInsertar != null) {
                psInsertar.close();
            }
            if (psSeleccionar != null) {
                psSeleccionar.close();
            }
            ConexionDB.cerrarConexion();
        }
    }
}