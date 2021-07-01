package com.jdbc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main( String args[] ) {
        Connection c = null; // соединение
        Statement stmt = null; // поток работы с БД

        try {
            Class.forName("org.sqlite.JDBC");  // формат работы бд
            c = DriverManager.getConnection("jdbc:sqlite:test.db"); // сама бд, подключение к файлу
            c.setAutoCommit(false);  // отключение авто сохронения
            System.out.println("Открытие бд, успех!");

            // ----------------------------------------------------- Создание самой таблицы
            try {
                stmt = c.createStatement(); // бд в поток
                String sql = "CREATE TABLE COMPANY " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " AGE            INT     NOT NULL, " +
                        " ADDRESS        CHAR(50), " +
                        " SALARY         REAL)"; // создание таблицы в sql
                stmt.executeUpdate(sql); // обновить бд
                //c.commit();
            }catch (Exception e){};

            // ------------------------------------------------------- Добавление данных
            stmt = c.createStatement(); // бд в поток
            String sql;
            /*
            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql); // обновление действий по запросу sql, втавка полей в бд / INSERT(вставка)
             */

            // ------------------------------------------------------- Скролинг
            Statement statement = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // или
            //PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM COMPANY", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  // фильтация входных данных в sql

            ResultSet resultSet = statement.executeQuery("SELECT * FROM COMPANY");
            if (resultSet.next()){ // шаг 1
                System.out.println(resultSet.getString("NAME"));
            }
            if (resultSet.next()){// шаг 1
                System.out.println(resultSet.getString("NAME"));
            }
            if (resultSet.previous()){// шаг -1
                System.out.println(resultSet.getString("NAME"));
            }
            if (resultSet.relative(2)){ // шаг 2
                System.out.println(resultSet.getString("NAME"));
            }
            if (resultSet.relative(-2)){ // шаг -2
                System.out.println(resultSet.getString("NAME"));
            }
            if (resultSet.first()){// в начало
                System.out.println(resultSet.getString("NAME"));
            }
            if (resultSet.last()){// в конец
                System.out.println(resultSet.getString("NAME"));
            }



            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() ); // ошибка
            System.exit(0);
        }
        System.out.println("Хранимые процедуры, успех!");
    }
}