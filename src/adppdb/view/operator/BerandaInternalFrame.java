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
package adppdb.view.operator;

import adppdb.database.JDBCAdapter;
import adppdb.model.Operator;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class BerandaInternalFrame extends javax.swing.JInternalFrame {

    private Operator operator;

    /**
     * Creates new form BerandaInternalFrame
     */
    public BerandaInternalFrame(Operator loggedInOperator) {
        initComponents();

        operator = loggedInOperator;
        salam.setText("Selamat datang, " + loggedInOperator.getNama() + "!");

        getUsersRegisteredCount();
        getUsersRequirementFullfilledCount();
    }

    public static void main(String[] args) {
        new adppdb.view.operator.OperatorFrame(new adppdb.model.Operator("operator@operator.com")).setVisible(true);
    }

    public void getUsersRegisteredCount() {
        try {
            ResultSet rs = JDBCAdapter
                    .getConnection()
                    .createStatement()
                    .executeQuery("SELECT count(*) as count FROM peserta, sekolah_tujuan "
                            + "WHERE nisn = nisn_peserta AND id_smp = " + operator.getSmp().getId());

            while (rs.next()) {
                all.setText(rs.getString("count"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BerandaInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getUsersRequirementFullfilledCount() {
        try {
            ResultSet rs = JDBCAdapter
                    .getConnection()
                    .createStatement()
                    .executeQuery("SELECT COUNT(DISTINCT p.nama) as count "
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
                            + "    AND st.id_smp = " + operator.getSmp().getId());
            while (rs.next()) {
                fullfill.setText(rs.getString("count"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BerandaInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        salam = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fullfill = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        all = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(960, 570));
        setPreferredSize(new java.awt.Dimension(960, 570));

        salam.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        salam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salam.setText("Halo, Anonymous!");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adppdb/icons/tut-wuri-handayani.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Statistik:");

        jLabel3.setText("Peserta Memenuhi Persyaratan");

        fullfill.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fullfill.setText("397");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fullfill)
                    .addComponent(jLabel3))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fullfill)
                .addGap(20, 20, 20))
        );

        jLabel2.setText("Peserta Mendaftar");

        all.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        all.setText("679");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(all)
                    .addComponent(jLabel2))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(all)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salam, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel2, jPanel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(salam)
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel9)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel all;
    private javax.swing.JLabel fullfill;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel salam;
    // End of variables declaration//GEN-END:variables
}