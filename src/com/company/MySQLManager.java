package com.company;

import java.sql.*;

public class MySQLManager {


    //conexion B para la base de datos de MySQL
    private Connection connection;


    //Método para iniciar la conexión con la base de datos
    public void init() {
        try {
            //Drivers de mysql
            Class.forName("com.mysql.jdbc.Driver");
            //Conexion de MySQL
            connection = DriverManager.getConnection("jdbc:mysql://iescierva.net:9305/aad_11?useSSL=false", "aad_11",
                    "aad_11");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha habido un problema con la conexión a base de datos");
            e.printStackTrace();
        }
    }


    //Metodo que tiene la primera consulta
    public void showEmployees() throws SQLException {
        // Se prepara la consulta
        ResultSet rs = getEmployees();


        //REcorremos el resultado para ver las filas de la consulta
        // con un bucle wile que indica que mientras haya una fila a continuación
        //se vaya visualizando
        while (rs.next()) {
            System.out.println(rs.getString("emp_no") + " " + rs.getString("oficio")
                    + " " + rs.getString("apellido"));
        }
    }

    public ResultSet getEmployees() throws SQLException {
        Statement sentencia = connection.createStatement();
        String sql = "SELECT * FROM empleados";
        ResultSet rs = sentencia.executeQuery(sql);
        return rs;
    }

    //Metodo que sincroniza las dos bases de datos
    public void sincronizar() throws SQLException {
        //Perparamos la consulta
        Statement sentencia = connection.createStatement();
        String sqlA = "SELECT * FROM empleados ";
        String sqlB = "INSERT INTO";


    }

    //Metodo que elimina empleados de MySQL
    public void eliminar() throws SQLException {
        //Preparamos la consulta
        Statement sentencia = connection.createStatement();
        String update = "DELETE FROM empleados WHERE emp_no = 7934";
        Boolean isError = sentencia.execute(update);
    }

    public void deleteAll() throws SQLException {
        //Preparamos la consulta
        Statement sentencia = connection.createStatement();
        String update = "DELETE FROM empleados";
        Boolean isError = sentencia.execute(update);
    }

    //Metodo para salir de la ejecución
    public void salir() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public PreparedStatement getStatementEmployees() throws SQLException {
        return connection.prepareStatement("insert into empleados values(?,?,?,?,?,?,?,?)");
    }
}