package gleideveloper.com.br.genuinecoder.database;

import gleideveloper.com.br.databasefactory.Database;
import gleideveloper.com.br.databasefactory.DatabaseFactory;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Created by Gleides on 08/02/2017.
 */
public final class DatabaseHandler {

    //Atributo para manipulação de dados
    private static Database database;
    private static Connection connection;
    private static Statement stmt;

    public DatabaseHandler() {
        database = DatabaseFactory.getDatabase("mysql");
        connection = database.conectar();
        setupBookTable();
    }

    public void setupBookTable() {
        String TABLE_NAME = "BOOK";
        String sql = null;
        try {
            stmt = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + "already exits. Ready to go!");
            } else {
                sql = "CREATE TABLE " + TABLE_NAME + "("
                        + " id VARCHAR(200) PRIMARY KEY,\n"
                        + " title VARCHAR(200),\n"
                        + " author VARCHAR(200),\n"
                        + " publisher VARCHAR(100),\n"
                        + " isAvail BOOLEAN DEFAULT TRUE"
                        + ")";
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(sql);
            System.err.println(e.getMessage() + " - setupDatabase");

        } finally {
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet resultSet;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execQuery: dataHandle " + e.getLocalizedMessage());
            return null;
        } finally {
        }
        return resultSet;
    }

    public boolean execAction(String query) {
        try {
            stmt = connection.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error " + e.getMessage());
            //System.out.println("Exception at execQuery: dataHandle " + e.getLocalizedMessage());
            alert.showAndWait();
            return false;
        } finally {
        }
    }
}