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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author samsul
 */
public class Peserta {

    private String nisn;
    private String nama;
    private String email;
    private String password;
    private String jenisKelamin;
    private String tempatLahir;
    private String tanggalLahir;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String alamat;
    private String namaWali;
    private String statusWali;
    private String alamatWali;
    private DataSekolah dataSekolah;
    private DataNilai dataNilai;

    public Peserta() {
    }

    public Peserta(String nisnOrEmail) {
        String sql = "SELECT * FROM peserta p LEFT JOIN wali w ON p.nisn = w.nisn_peserta "
                + "WHERE p.nisn = ? OR "
                + "p.email = ?";

        try {
            PreparedStatement stmt = JDBCAdapter.getConnection().prepareStatement(sql);
            stmt.setString(1, nisnOrEmail);
            stmt.setString(2, nisnOrEmail);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                setNisn(rs.getString("p.nisn"));
                setNama(rs.getString("p.nama"));
                setEmail(rs.getString("p.email"));
                setPassword(rs.getString("p.password"));
                setJenisKelamin(rs.getString("p.jenis_kelamin"));
                setTempatLahir(rs.getString("p.tempat_lahir"));
                setTanggalLahir(rs.getString("p.tanggal_lahir"));
                setProvinsi(rs.getString("p.provinsi"));
                setKota(rs.getString("p.kota"));
                setKecamatan(rs.getString("p.kecamatan"));
                setAlamat(rs.getString("p.alamat"));
                setNamaWali(rs.getString("w.nama"));
                setStatusWali(rs.getString("w.status"));
                setAlamatWali(rs.getString("w.alamat"));
                setDataSekolah(new DataSekolah(rs.getString("p.nisn")));
                setDataNilai(new DataNilai(getNisn()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Peserta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataNilai getDataNilai() {
        return dataNilai;
    }

    public void setDataNilai(DataNilai dataNilai) {
        this.dataNilai = dataNilai;
    }

    public DataSekolah getDataSekolah() {
        return dataSekolah;
    }

    public void setDataSekolah(DataSekolah dataSekolah) {
        this.dataSekolah = dataSekolah;
    }

    public String getNamaWali() {
        return namaWali;
    }

    public void setNamaWali(String namaWali) {
        this.namaWali = namaWali;
    }

    public String getStatusWali() {
        return statusWali;
    }

    public void setStatusWali(String statusWali) {
        this.statusWali = statusWali;
    }

    public String getAlamatWali() {
        return alamatWali;
    }

    public void setAlamatWali(String alamatWali) {
        this.alamatWali = alamatWali;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
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

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void register() {
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection()
                    .prepareStatement("INSERT INTO peserta (nisn, nama, email, password)"
                            + "VALUES (?, ?, ?, ?)");

            stmt.setString(1, getNisn());
            stmt.setString(2, getNama());
            stmt.setString(3, getEmail());
            stmt.setString(4, getPassword());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Pendaftaran gagal, nisn atau email sudah digunakan!", "Galat", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean passwordMatch(char[] password) {
        PasswordAuthentication pa = new PasswordAuthentication();

        if (getPassword() != null) {
            return pa.authenticate(password, getPassword());
        }

        return false;
    }

    private boolean waliIsExists() {
        boolean returnValue = false;

        PreparedStatement pstmt;
        try {
            pstmt = JDBCAdapter.getConnection()
                    .prepareStatement("SELECT count(*) as count FROM wali WHERE nisn_peserta = ?");

            pstmt.setString(1, getNisn());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("count") > 0) {
                    returnValue = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Peserta.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnValue;
    }

    public void simpanDataDiri() {
        boolean simpanSukses;
        String exception = "";

        // Simpan data pesertaa
        try {
            PreparedStatement pstmt = JDBCAdapter.getConnection()
                    .prepareStatement("UPDATE peserta SET nama = ?, email = ?, nisn = ?, jenis_kelamin = ?, tempat_lahir = ?, tanggal_lahir = ?, provinsi = ?, kecamatan = ?, kota = ?, alamat = ? WHERE nisn = ?");

            pstmt.setString(1, getNama());
            pstmt.setString(2, getEmail());
            pstmt.setString(3, getNisn());
            pstmt.setString(4, getJenisKelamin());
            pstmt.setString(5, getTempatLahir());
            pstmt.setString(6, getTanggalLahir());
            pstmt.setString(7, getProvinsi());
            pstmt.setString(8, getKecamatan());
            pstmt.setString(9, getKota());
            pstmt.setString(10, getAlamat());
            pstmt.setString(11, getNisn());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            exception += "\n" + ex;
        }

        // Simpan data wali
        if (waliIsExists()) {
            try {
                PreparedStatement pstmt = JDBCAdapter.getConnection()
                        .prepareStatement("UPDATE wali SET nama = ?, status = ?, alamat = ? WHERE nisn_peserta = ?");

                pstmt.setString(1, getNamaWali());
                pstmt.setString(2, getStatusWali());
                pstmt.setString(3, getAlamatWali());
                pstmt.setString(4, getNisn());

                pstmt.executeUpdate();

                simpanSukses = true;
            } catch (SQLException ex) {
                exception += "\n" + ex;

                simpanSukses = false;
            }
        } else {
            try {
                PreparedStatement pstmt = JDBCAdapter.getConnection()
                        .prepareStatement("INSERT INTO wali (nisn_peserta, nama, status, alamat) VALUES (?, ?, ?, ?)");

                pstmt.setString(1, getNisn());
                pstmt.setString(2, getNamaWali());
                pstmt.setString(3, getStatusWali());
                pstmt.setString(4, getAlamatWali());

                pstmt.executeUpdate();

                simpanSukses = true;
            } catch (SQLException ex) {
                exception += "\n" + ex;

                simpanSukses = false;
            }
        }

        if (simpanSukses) {
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan\n" + exception, "Galat", JOptionPane.ERROR_MESSAGE);
        }
    }
}
