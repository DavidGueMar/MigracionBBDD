package com.company;

import java.sql.SQLException;

public class BBDDManager {
    private SQLiteManager sqLiteManager;

    public void init() {
        sqLiteManager = new SQLiteManager();
        sqLiteManager.init();
    }

    public void exit() throws SQLException {
        sqLiteManager.salir();
    }

    public void showProfesores() throws SQLException {
        sqLiteManager.showProfesores();
    }

    public void insertProfesor() throws SQLException {
        sqLiteManager.insertProfesor();
    }

    public void deleteCentro() throws SQLException {
        sqLiteManager.deleteCentro();
    }
    public void showTablas() throws SQLException {
        sqLiteManager.showTablas();
    }
}

