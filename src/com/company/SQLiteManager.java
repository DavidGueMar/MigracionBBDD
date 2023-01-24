package com.company;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager {
    //conexion A para la base de datos de sqlite
    private Connection connection;


    //Método para iniciar la conexión con la base de datos
    public void init() {
        try {
            //Drivers de sqlite
            Class.forName("org.sqlite.JDBC");
            //Conexion de sqlite
            connection = DriverManager.getConnection("jdbc:sqlite:centros_sqlite.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha habido un problema con la conexión a base de datos de sqlite");
            e.printStackTrace();

        }
    }

    //Metodo que tiene la primera consulta que es la de mostrar la tabla profesores
    public void showProfesores() throws SQLException {
        // Se prepara la consulta
        Statement sentencia = connection.createStatement();
        String sql = "SELECT * FROM C1_PROFESORES" ;
        ResultSet rs = sentencia.executeQuery(sql);

        //Se crea la variable en la que vamos a concatenar todos los registros de la tabla de profesores
        String a = "";
        while (rs.next()) {
            //En cada iteración del bucle añadimos un registro a la cadena a
            a += "\n" + rs.getString("COD_PROF") + " " + rs.getString("NOMBRE_APE")
                    + " " + rs.getString("ESPECIALIDAD") + " " + rs.getString("JEFE_DEP") +
                    " " + rs.getString("FECHA_NAC") + " " + rs.getString("SEXO") +
                    " " + rs.getString("COD_CENTRO");
        }
        JOptionPane.showMessageDialog(null, a,
                "PROFESORES", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTablas() throws SQLException {
        Statement sentencia = connection.createStatement();
        String sql = "SELECT * FROM sqlite_master WHERE type = 'table'";
        ResultSet rs = sentencia.executeQuery(sql);

        String a = "Estas son las tablas de la base de datos";
        while (rs.next()) {
            a += "\n" + rs.getString("tbl_name");
        }
        JOptionPane.showMessageDialog(null, a,
                "PROFESORES", JOptionPane.INFORMATION_MESSAGE);
    }


    //Metodo para salir de la ejecución
    public void salir() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void insertProfesor() throws SQLException {
        Integer codProf = null;
        String nombre;
        String especialidad = null;
        Integer jefeDep = null;
        Date fechaNac = null;
        Character sexo = null;
        Integer codCentro = null;
        //Variable que determina si el profesor existe o no
        boolean profExist = true;
        //Bucle por el cual pregunto por el codigo de profesor siempre y cuando el profesor exista en la BBDD
        while (profExist) {
            codProf = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserte el codigo del profesor para comprobar que no existe"));
            profExist = isProfesorExist(codProf);
        }
        nombre = JOptionPane.showInputDialog(null, "Inserte el nombre del profesor");

        //Variable que determina si la especialidad existe o no
        boolean espExist = false;
        //Bucle por el cual pregunto por el codigo de la especialidad siempre y cuando el profesor exista en la BBDD
        while (!espExist) {
            especialidad = JOptionPane.showInputDialog(null, "Inserte el codigo de la especialidad para comprobar que no existe");
            espExist = isEspecialidadExist(especialidad);
        }
//        jefeDep = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserte el codigo del profesor que es jefe de departamento"));
        String date = JOptionPane.showInputDialog(null, "Inserte la fecha con este formato 02/02/1999");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fechaNac = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sexo = (char) JOptionPane.showInputDialog(null, "Seleccione un sexo",
                "Sexo", JOptionPane.QUESTION_MESSAGE, null,
                new Character[]{'H', 'M'}, "Seleccione");


        //Variable que determina si el centro existe o no
        boolean centroExist = false;
        //Bucle por el cual pregunto por el codigo de centro siempre y cuando el centro exista en la BBDD
        while (!centroExist) {
            codCentro = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserte el codigo del centro para comprobar que no existe"));
            centroExist = isCentroExist(codCentro);
        }

        //Comenzamos la inserción

        PreparedStatement st = connection.prepareStatement("INSERT INTO C1_PROFESORES VALUES(?,?,?,?,?,?,?)");
        st.setObject(1, codProf);
        st.setObject(2, nombre);
        st.setObject(3, especialidad);
        st.setObject(4, jefeDep);
        st.setObject(5, fechaNac);
        st.setObject(6, sexo);
        st.setObject(7, codCentro);
        st.executeUpdate();

        JOptionPane.showMessageDialog(null, "El profesor con id " + codProf + " y nombre " + nombre + " ha sido insertado correctamente",
                "Insertar profesor", JOptionPane.INFORMATION_MESSAGE);
    }


    public boolean isCentroExist(int cod) throws SQLException {
        Statement sentencia = connection.createStatement();
        String sql = "SELECT * FROM C1_CENTROS WHERE COD_CENTRO = " + cod;
        ResultSet rs = sentencia.executeQuery(sql);
        return rs.next();
    }

    public boolean isEspecialidadExist(String cod) throws SQLException {
        Statement sentencia = connection.createStatement();
        String sql = "SELECT * FROM C1_ESPECIALIDAD WHERE ESPECIALIDAD = '" + cod + "'";
        ResultSet rs = sentencia.executeQuery(sql);
        return rs.next();
    }

    public boolean isProfesorExist(int cod) throws SQLException {
        Statement sentencia = connection.createStatement();
        String sql = "SELECT * FROM C1_PROFESORES WHERE COD_PROF = " + cod;
        ResultSet rs = sentencia.executeQuery(sql);
        return rs.next();
    }

    public void deleteCentro() throws SQLException {
        int cod = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserte el codigo del centro a borrar"));
        if (isCentroExist(cod)) {
            //Creo la conexión
            Statement sentencia = connection.createStatement();

            //Borro todos los profesores con el cod de centro igual al que he pedido
            String deleteProfesores = "DELETE FROM C1_PROFESORES WHERE COD_CENTRO = " + cod;
            sentencia.execute(deleteProfesores);

            //Borro el centro con el id pedido por pantalla
            String update = "DELETE FROM C1_CENTROS WHERE COD_CENTRO = " + cod;
            sentencia.execute(update);

            JOptionPane.showMessageDialog(null, "El centro se ha borrado correctamente", "Borrar centro", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El centro no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
        ;
    }
}
