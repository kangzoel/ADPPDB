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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class SD {

    private String id;
    private String kecamatan;
    private String npsn;
    private String nama;
    private String status;
    private String alamatJalan;

    public SD(String id) {
        String sql = "SELECT * FROM sd "
                + "WHERE id = ?";

        try {
            PreparedStatement stmt = JDBCAdapter.getConnection().prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                setId(rs.getString("id"));
                setKecamatan(rs.getString("kecamatan"));
                setNpsn(rs.getString("npsn"));
                setNama(rs.getString("nama"));
                setStatus(rs.getString("status"));
                setAlamatJalan(rs.getString("alamat_jalan"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Peserta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getNpsn() {
        return npsn;
    }

    public void setNpsn(String npsn) {
        this.npsn = npsn;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlamatJalan() {
        return alamatJalan;
    }

    public void setAlamatJalan(String alamatJalan) {
        this.alamatJalan = alamatJalan;
    }
}
