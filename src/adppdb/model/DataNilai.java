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
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.imageio.ImageIO;

/**
 *
 * @author samsul
 */
public class DataNilai {

    private String nisnPeserta;
    private BufferedImage skhun;
    private String bahasaIndonesia;
    private String ipa;
    private String matematika;

    public DataNilai() {
    }

    public DataNilai(String nisnPeserta) {
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection()
                    .prepareStatement("SELECT * FROM data_nilai WHERE nisn_peserta = ?");

            stmt.setString(1, nisnPeserta);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                java.sql.Blob blob = rs.getBlob("skhun");
                InputStream in = blob.getBinaryStream();
                BufferedImage skhun = ImageIO.read(in);
                setSkhun(skhun);

                setBahasaIndonesia(rs.getString("bahasa_indonesia"));
                setMatematika(rs.getString("matematika"));
                setIpa(rs.getString("ipa"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataNilai.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataNilai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNisnPeserta() {
        return nisnPeserta;
    }

    public void setNisnPeserta(String nisnPeserta) {
        this.nisnPeserta = nisnPeserta;
    }

    public BufferedImage getSkhun() {
        return skhun;
    }

    public void setSkhun(BufferedImage skhun) {
        this.skhun = skhun;
    }

    public String getBahasaIndonesia() {
        return bahasaIndonesia;
    }

    public void setBahasaIndonesia(String bahasaIndonesia) {
        this.bahasaIndonesia = bahasaIndonesia;
    }

    public String getIpa() {
        return ipa;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }

    public String getMatematika() {
        return matematika;
    }

    public void setMatematika(String matematika) {
        this.matematika = matematika;
    }

    public void updateSkhun(FileInputStream input) {
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection()
                    .prepareStatement("UPDATE data_nilai SET skhun = ? WHERE nisn_peserta = ?");

            stmt.setBinaryStream(1, input);
            stmt.setString(2, getNisnPeserta());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "File berhasil diupload.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Pastikan gambar yang Anda pilih tersedia di komputer Anda!\nJika anda menggunakan software untuk sinkronisasi data, harap download terlebih dahulu gambar yang ingin diupload!", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertSkhun(FileInputStream input) {
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection()
                    .prepareStatement("INSERT INTO data_nilai (nisn_peserta, skhun) "
                            + "VALUES (?, ?)");
            
            stmt.setString(1, getNisnPeserta());
            stmt.setBinaryStream(2, input);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "File berhasil diupload.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Pastikan gambar yang Anda pilih tersedia di komputer Anda!\nJika anda menggunakan software untuk sinkronisasi data, harap download terlebih dahulu gambar yang ingin diupload!", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertNilaiPeserta() {
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection()
                    .prepareStatement("UPDATE data_nilai SET bahasa_indonesia = '" + getBahasaIndonesia() + "'"
                            + ",  ipa = '" + getIpa() + "'"
                            + ",  matematika = '" + getMatematika() + "' WHERE nisn_peserta = '" + getNisnPeserta() + "'");
            stmt.execute();
            JOptionPane.showMessageDialog(null, "nilai berhasil disimpan ");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "nilai gagal disimpan ");
        }
    }

}
