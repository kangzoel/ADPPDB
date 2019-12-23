/* 
 * Copyright (C) 2019 samsul
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package adppdb.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class JDBCAdapter {

    private static Connection connection;
    private static final String DB = "adppdb";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/ " + DB + "?serverTimezone=CST", USER, PASS);
            } catch (SQLException e) {
                System.out.println("Gagal terkoneksi " + e);
            }
        }

        return connection;
    }

    public static void main(String[] args) {
        if (getConnection().equals(connection)) {
            System.out.println("Sukses Terkoneksi");
        }
    }
}
