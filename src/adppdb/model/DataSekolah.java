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
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author samsul
 */
public class DataSekolah {

    private SD sekolahAsal;
    private SMP[] sekolahTujuan;

    public DataSekolah(String nisn) {
        String sql = "SELECT p.sekolah_asal, st.id_smp FROM peserta p, sekolah_tujuan st "
                + "WHERE p.nisn = ? AND "
                + "st.nisn_peserta = p.nisn";

        try {
            PreparedStatement stmt = JDBCAdapter.getConnection().prepareStatement(sql);
            stmt.setString(1, nisn);

            ResultSet rs = stmt.executeQuery();

            SMP[] st = new SMP[3];

            int i = 0;
            while (rs.next()) {
                if (rs.isFirst()) {
                    setSekolahAsal(new SD(rs.getString("p.sekolah_asal")));
                }

                st[i] = new SMP(rs.getString("st.id_smp"));
                i++;
            }

            setSekolahTujuan(st);
        } catch (SQLException ex) {
            Logger.getLogger(Peserta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataSekolah() {
    }

    public SD getSekolahAsal() {
        return sekolahAsal;
    }

    public void setSekolahAsal(SD sekolahAsal) {
        this.sekolahAsal = sekolahAsal;
    }

    public SMP[] getSekolahTujuan() {
        return sekolahTujuan;
    }

    public void setSekolahTujuan(SMP[] sekolahTujuan) {
        this.sekolahTujuan = sekolahTujuan;
    }

    public void saveSekolahAsal(Peserta peserta, int sekolahAsal) {
        String nisn = peserta.getNisn();

        // Save sekolah asal
        String sql = "UPDATE peserta SET sekolah_asal = ? WHERE nisn= ?";
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection().prepareStatement(sql);
            stmt.setInt(1, sekolahAsal);
            stmt.setString(2, nisn);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data asal sekolah tidak berhasil disimpan" + ex);
        }
    }

    public void saveSekolahTujuan(Peserta peserta, JTable tabelSekolahTujuan) {
        // Save sekolah tujuan
        for (int i = 0; i < tabelSekolahTujuan.getRowCount(); i++) {
            String nisn = peserta.getNisn();
            String st = tabelSekolahTujuan.getValueAt(i, 1).toString();

            try {
                PreparedStatement stmt = JDBCAdapter.getConnection()
                        .prepareStatement("INSERT INTO sekolah_tujuan VALUES (?, ?)");
                stmt.setString(1, nisn);
                stmt.setString(2, st);

                stmt.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Data sekolah tujuan tidak berhasil disimpan");
            }
        }
    }
}
