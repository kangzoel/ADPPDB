/*
 * Copyright (C) 2019 samsul
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package adppdb.model;

import adppdb.database.JDBCAdapter;
import adppdb.utility.PasswordAuthentication;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class Admin {

    public static boolean login() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int id;
    private String nama;
    private String email;
    private String password;

    public Admin(String email) {
        String sql = "SELECT * FROM admin "
                + "WHERE email = ?";

        try {
            PreparedStatement stmt = JDBCAdapter.getConnection().prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                setId(rs.getInt("id"));
                setNama(rs.getString("nama"));
                setEmail(rs.getString("email"));
                setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Peserta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Admin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordMatch(char[] password) {
        PasswordAuthentication pa = new PasswordAuthentication();

        if (getPassword() != null) {
            return pa.authenticate(password, getPassword());
        }

        return false;
    }
}
