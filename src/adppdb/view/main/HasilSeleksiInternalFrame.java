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
package adppdb.view.main;

import adppdb.database.JDBCAdapter;
import adppdb.model.SD;
import java.awt.Dialog.ModalityType;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author samsul
 */
public class HasilSeleksiInternalFrame extends javax.swing.JInternalFrame {

    private JFrame parentFrame;
    private DefaultTableModel sekolahTujuanTableModel;
    private DefaultTableModel hasilSeleksiTableModel;
    private PreparedStatement stmt;
    private ResultSet rs;
    private Integer sekolahTujuan;

    /**
     * Creates new form HasilSeleksiInternalFrame
     */
    public HasilSeleksiInternalFrame(JFrame parentFrame) {
        initComponents();

        setupTables();

    }

    private void setupTables() {
        String[] hasilSeleksiColumnNames = {
            "NISN",
            "Nama",
            "Nilai Rata-Rata",
            "Peringkat",
            "Status",};

        String[] sekolahTujuanColumnNames = {
            "ID",
            "Nama Sekolah",
            "Status",
            "Kecamatan",
            "Alamat"
        };
        
        hasilSeleksiTableModel = new DefaultTableModel(null, hasilSeleksiColumnNames);
        sekolahTujuanTableModel = new DefaultTableModel(null, sekolahTujuanColumnNames);

        hasilSeleksiTable.setModel(hasilSeleksiTableModel);
        setMinMaxTableColumnWidth(hasilSeleksiTable, 0, 70);
        setMinMaxTableColumnWidth(hasilSeleksiTable, 1, 200);
        setMinMaxTableColumnWidth(hasilSeleksiTable, 4, 150);

        try {
            stmt = JDBCAdapter.getConnection()
                    .prepareStatement("SELECT id, nama, status, kecamatan, alamat_jalan "
                            + "FROM SMP ORDER BY id ASC");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String[] st = {
                    rs.getString("id"),
                    rs.getString("nama"),
                    rs.getString("status").equals("N") ? "Negeri" : "Swasta",
                    rs.getString("kecamatan"),
                    rs.getString("alamat_jalan"),};
                sekolahTujuanTableModel.addRow(st);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HasilSeleksiInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        sekolahTujuanTable.setModel(sekolahTujuanTableModel);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 0, 50);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 1, 210);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 2, 80);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 3, 150);
    }

    private void tampilDataHasilSeleksi() {
        if (sekolahTujuan != null) {
            try {
                stmt = JDBCAdapter.getConnection()
                        .prepareStatement(
                                "SELECT "
                                + "p.nisn, "
                                + "p.nama, "
                                + "((bahasa_indonesia + matematika + ipa)/3) as rata_rata, "
                                + "RANK() OVER (ORDER BY rata_rata DESC) ranking, "
                                + "IF(RANK() OVER (ORDER BY rata_rata DESC) < batas_penerimaan, 'Lulus', 'Tidak Lulus') as status "
                                + "FROM peserta p, sekolah_tujuan st, smp, data_nilai dn "
                                + "WHERE smp.id = ? "
                                + "AND p.nisn = st.nisn_peserta "
                                + "AND st.id_smp = smp.id "
                                + "AND p.nisn = dn.nisn_peserta "
                                + "AND NOT ("
                                    + " matematika IS NULL "
                                    + " OR bahasa_indonesia IS NULL "
                                    + " OR ipa IS NULL"
                                + ") "
                                + "ORDER BY rata_rata DESC"
                        );

                stmt.setInt(1, sekolahTujuan);

                rs = stmt.executeQuery();

                while (rs.next()) {
                    String[] hasilSeleksi = {
                        rs.getString("p.nisn"),
                        rs.getString("p.nama"),
                        rs.getString("rata_rata").split("\\.")[0],
                        rs.getString("ranking"),
                        rs.getString("status"),};
                    hasilSeleksiTableModel.addRow(hasilSeleksi);
                }

                sekolahTujuanDialog.dispose();

                nisn.setEnabled(true);
                nisn.setEditable(true);
            } catch (SQLException ex) {
                Logger.getLogger(HasilSeleksiInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setMinMaxTableColumnWidth(javax.swing.JTable table, int column, int width) {
        table.getColumnModel().getColumn(column).setMinWidth(width);
        table.getColumnModel().getColumn(column).setMaxWidth(width);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sekolahTujuanDialog = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        cariB = new javax.swing.JButton();
        cariTF = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        sekolahTujuanTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        hasilSeleksiTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        nisn = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pilihButton = new javax.swing.JButton();
        st = new javax.swing.JTextField();
        info = new javax.swing.JLabel();

        sekolahTujuanDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        sekolahTujuanDialog.setTitle("Cari Sekolah Tujuan");
        sekolahTujuanDialog.setAlwaysOnTop(true);
        sekolahTujuanDialog.setMinimumSize(new java.awt.Dimension(783, 446));
        sekolahTujuanDialog.setResizable(false);
        sekolahTujuanDialog.setSize(new java.awt.Dimension(783, 446));

        jLabel2.setText("Nama Sekolah");

        cariB.setText("Cari");
        cariB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariBActionPerformed(evt);
            }
        });

        cariTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariTFActionPerformed(evt);
            }
        });

        jButton2.setText("Pilih");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        sekolahTujuanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(sekolahTujuanTable);

        javax.swing.GroupLayout sekolahTujuanDialogLayout = new javax.swing.GroupLayout(sekolahTujuanDialog.getContentPane());
        sekolahTujuanDialog.getContentPane().setLayout(sekolahTujuanDialogLayout);
        sekolahTujuanDialogLayout.setHorizontalGroup(
            sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                .addGroup(sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cariTF, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cariB))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        sekolahTujuanDialogLayout.setVerticalGroup(
            sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cariB)
                    .addComponent(cariTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        hasilSeleksiTable.setAutoCreateRowSorter(true);
        hasilSeleksiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        hasilSeleksiTable.setFocusable(false);
        jScrollPane1.setViewportView(hasilSeleksiTable);

        jLabel1.setText("NISN");

        nisn.setEditable(false);
        nisn.setEnabled(false);
        nisn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nisnKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Hasil Seleksi");

        jLabel3.setText("Sekolah Tujuan");

        pilihButton.setText("Pilih");
        pilihButton.setFocusable(false);
        pilihButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihButtonActionPerformed(evt);
            }
        });

        st.setEditable(false);
        st.setFocusable(false);

        info.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        info.setForeground(new java.awt.Color(255, 51, 51));
        info.setText("Silakan pilih sekolah tujuan!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(info)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nisn, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(st, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(pilihButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pilihButton)
                    .addComponent(st, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nisn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(info)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nisnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nisnKeyReleased
        // Ubah isi tabel hasil seleksi ketika nilai combobox berubah
        try {
            hasilSeleksiTableModel.setRowCount(0);

            stmt = JDBCAdapter.getConnection()
                    .prepareStatement(
                            "SELECT "
                            + "p.nisn, "
                            + "p.nama, "
                            + "((bahasa_indonesia + matematika + ipa)/3) as rata_rata, "
                            + "RANK() OVER (ORDER BY rata_rata DESC) ranking, "
                            + "IF(RANK() OVER (ORDER BY rata_rata DESC) < batas_penerimaan, 'Lulus', 'Tidak Lulus') as status "
                            + "FROM peserta p, sekolah_tujuan st, smp, data_nilai dn "
                            + "WHERE smp.id = ? "
                            + "AND p.nisn like ?"
                            + "AND p.nisn = st.nisn_peserta "
                            + "AND st.id_smp = smp.id "
                            + "AND p.nisn = dn.nisn_peserta "
                            + "ORDER BY ranking ASC"
                    );

            stmt.setInt(1, sekolahTujuan);
            stmt.setString(2, nisn.getText() + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                String[] hasilSeleksi = {
                    rs.getString("p.nisn"),
                    rs.getString("p.nama"),
                    rs.getString("rata_rata"),
                    rs.getString("ranking"),
                    rs.getString("status"),};
                hasilSeleksiTableModel.addRow(hasilSeleksi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HasilSeleksiInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_nisnKeyReleased

    private void pilihButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihButtonActionPerformed
        sekolahTujuanDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        sekolahTujuanDialog.setLocationRelativeTo(null);
        sekolahTujuanDialog.setVisible(true);
    }//GEN-LAST:event_pilihButtonActionPerformed

    private void cariBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariBActionPerformed
        sekolahTujuanTableModel.setRowCount(0);

        try {
            stmt = JDBCAdapter.getConnection()
                    .prepareStatement("SELECT id, nama, status, kecamatan, alamat_jalan "
                            + "FROM SMP WHERE nama like ? ORDER BY nama ASC");
            stmt.setString(1, "%" + cariTF.getText() + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                String[] sekolahTujuan = {
                    rs.getString("id"),
                    rs.getString("nama"),
                    rs.getString("status").equals("N") ? "Negeri" : "Swasta",
                    rs.getString("kecamatan"),
                    rs.getString("alamat_jalan"),};
                sekolahTujuanTableModel.addRow(sekolahTujuan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HasilSeleksiInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cariBActionPerformed

    private void cariTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariTFActionPerformed
        cariBActionPerformed(evt);
    }//GEN-LAST:event_cariTFActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = sekolahTujuanTable.getSelectedRow();
        String idSekolahTujuan = sekolahTujuanTable.getModel().getValueAt(row, 0).toString();
        String namaSekolahTujuan = sekolahTujuanTable.getModel().getValueAt(row, 1).toString();

        if (sekolahTujuanTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "Silakan pilih sekolah tujuan!", "Galat", JOptionPane.WARNING_MESSAGE);
        } else {
            sekolahTujuan = Integer.parseInt(idSekolahTujuan);
        }

        st.setText(namaSekolahTujuan);
        info.setText("");

        tampilDataHasilSeleksi();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cariB;
    private javax.swing.JTextField cariTF;
    private javax.swing.JTable hasilSeleksiTable;
    private javax.swing.JLabel info;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField nisn;
    private javax.swing.JButton pilihButton;
    private javax.swing.JDialog sekolahTujuanDialog;
    private javax.swing.JTable sekolahTujuanTable;
    private javax.swing.JTextField st;
    // End of variables declaration//GEN-END:variables
}
