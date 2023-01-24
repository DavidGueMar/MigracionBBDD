package com.company;



import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Integer option = -1;
        BBDDManager manager = new BBDDManager();
        manager.init();
        while (option != 0) {
            try {
                String s = JOptionPane.showInputDialog("Ingrese lo que quiere hacer:" +
                        "\n1 - Mostrar profesores" +
                        "\n2 - Insertar profesor" +
                        "\n3 - Eliminar centro" +
                        "\n4 - Mostrar tablas bases de datos" +
                        "\n0 - Salir");
                if (s == null){
                    System.exit(0);
                }
                option = Integer.parseInt(s);
                switch (option){
                    case 0:
                        manager.exit();
                        break;
                    case 1:
                        manager.showProfesores();
                        break;
                    case 2:
                        manager.insertProfesor();
                        break;
                    case 3:
                        manager.deleteCentro();
                        break;
                    case 4:
                        manager.showTablas();
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Inserte un código correcto",
                        "Error al insertar código", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
