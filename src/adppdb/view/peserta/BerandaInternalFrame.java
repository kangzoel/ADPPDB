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
package adppdb.view.peserta;

import adppdb.database.JDBCAdapter;
import adppdb.model.Peserta;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class BerandaInternalFrame extends javax.swing.JInternalFrame {

    private Peserta peserta;

    /**
     * Creates new form BerandaInternalFrame
     */
    public BerandaInternalFrame(Peserta peserta) {
        initComponents();
        
        this.peserta = peserta;

        salam.setText("Halo, " + peserta.getNama() + '!');
        getStatus();
    }

    public void getStatus() {
        try {
            ResultSet rs = JDBCAdapter
                    .getConnection()
                    .createStatement()
                    .executeQuery("SELECT * "
                            + "FROM peserta p "
                            + "LEFT JOIN wali w ON p.nisn = w.nisn_peserta "
                            + "LEFT JOIN data_nilai dn ON p.nisn = dn.nisn_peserta "
                            + "LEFT JOIN sekolah_tujuan st ON p.nisn = st.nisn_peserta "
                            + "WHERE"
                            + "    NOT ("
                            + "            p.sekolah_asal IS NULL"
                            + "            OR p.jenis_kelamin IS NULL"
                            + "            OR p.tempat_lahir IS NULL"
                            + "            OR p.tanggal_lahir IS NULL"
                            + "            OR p.provinsi IS NULL"
                            + "            OR p.kota IS NULL"
                            + "            OR p.kecamatan IS NULL"
                            + "            OR p.alamat IS NULL"
                            + "        )"
                            + "    AND NOT ("
                            + "        w.nama IS NULL"
                            + "        OR w.status IS NULL"
                            + "        OR w.alamat IS NULL"
                            + "    )"
                            + "    AND NOT ("
                            + "        dn.skhun IS NULL"
                            + "    )"
                            + "    AND p.nisn = " + peserta.getNisn());
            
            while (rs.next()) {
                if (
                    rs.getString("dn.bahasa_indonesia") == null
                    || rs.getString("dn.matematika") == null
                    || rs.getString("dn.ipa") == null
                ) {
                    status.setText("Menunggu Input Nilai oleh Operator");
                    status.setForeground(new Color(164, 77, 15));                    
                } else {
                    status.setText("Lengkap");
                    status.setForeground(new Color(31, 164, 15));                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(adppdb.view.operator.BerandaInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new adppdb.view.peserta.PesertaFrame(new adppdb.model.Peserta("999001")).setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        salam = new javax.swing.JLabel();
        status = new javax.swing.JLabel();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Status Akun");
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adppdb/icons/tut-wuri-handayani.png"))); // NOI18N

        salam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salam.setText("Halo, John Doe!");
        salam.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status.setText("Belum lengkap");
        status.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        status.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
            .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
            .addComponent(salam, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel10)
                .addGap(50, 50, 50)
                .addComponent(salam)
                .addGap(88, 88, 88)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(status)
                .addContainerGap(172, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel salam;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
