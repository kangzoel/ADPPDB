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

import adppdb.controller.DataSekolahController;
import adppdb.database.JDBCAdapter;
import adppdb.model.DataSekolah;
import adppdb.model.Peserta;
import adppdb.model.SD;
import adppdb.model.SMP;
import java.awt.Dialog;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author samsul
 */
public class DataSekolahInternalFrame extends javax.swing.JInternalFrame {

    private DataSekolahController controller;
    private DefaultTableModel sekolahTujuanTableModel;
    private DefaultTableModel sekolahAsalDialogTableModel;
    private DefaultTableModel sekolahTujuanDialogTableModel;
    private Peserta peserta;
    private int sekolahAsal;
    private ArrayList<String> sekolahTujuan = new ArrayList<String>();

    /**
     * Creates new form DataSekolahInternalFrame
     */
    public DataSekolahInternalFrame(Peserta peserta) {
        initComponents();

        this.peserta = peserta;
        this.controller = new DataSekolahController(this);

        String[] sekolahTujuanTableColumnNames = {
            "No",
            "ID",
            "Status",
            "Nama",};
        String[] sekolahDialogTableColumnNames = {
            "ID",
            "Nama Sekolah",
            "Status",
            "Kecamatan",
            "Alamat",};

        sekolahTujuanTableModel = new DefaultTableModel(null, sekolahTujuanTableColumnNames);
        sekolahAsalDialogTableModel = new DefaultTableModel(null, sekolahDialogTableColumnNames);
        sekolahTujuanDialogTableModel = new DefaultTableModel(null, sekolahDialogTableColumnNames);

        // Cek apakah data sekolah sebelumnya telah diisi        
        DataSekolah dataSekolah = peserta.getDataSekolah();
        SD sekolahAsal = dataSekolah.getSekolahAsal();
        SMP[] sekolahTujuanData = dataSekolah.getSekolahTujuan();

        if (dataSekolah.getSekolahAsal() != null
                && dataSekolah.getSekolahTujuan() != null) {

            asal.setText(sekolahAsal.getNama());

            for (int i = 0; i < sekolahTujuanData.length; i++) {
                SMP sekolahTujuan = sekolahTujuanData[i];

                Object[] smp = new Object[4];
                smp[0] = i + 1;
                smp[1] = sekolahTujuan.getId();
                smp[2] = sekolahTujuan.equals("N") ? "Negeri" : "Swasta";
                smp[3] = sekolahTujuan.getNama();

                sekolahTujuanTableModel.addRow(smp);
            }

            sekolahAsalButton.setEnabled(false);
            sekolahTujuanButton.setEnabled(false);
            sekolahTujuanTable.setEnabled(false);
        }
        // end cek

        setupTables();
    }

    private void setupTables() {
        sekolahTujuanTable.setModel(sekolahTujuanTableModel);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 0, 50);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 1, 50);
        setMinMaxTableColumnWidth(sekolahTujuanTable, 2, 80);

        sekolahTujuanDialogTable.setModel(sekolahTujuanDialogTableModel);
        setMinMaxTableColumnWidth(sekolahTujuanDialogTable, 0, 50);
        setMinMaxTableColumnWidth(sekolahTujuanDialogTable, 1, 210);
        setMinMaxTableColumnWidth(sekolahTujuanDialogTable, 2, 80);
        setMinMaxTableColumnWidth(sekolahTujuanDialogTable, 3, 150);

        sekolahAsalDialogTable.setModel(sekolahAsalDialogTableModel);
        setMinMaxTableColumnWidth(sekolahAsalDialogTable, 0, 50);
        setMinMaxTableColumnWidth(sekolahAsalDialogTable, 1, 210);
        setMinMaxTableColumnWidth(sekolahAsalDialogTable, 2, 80);
        setMinMaxTableColumnWidth(sekolahAsalDialogTable, 3, 150);
    }

    private void setMinMaxTableColumnWidth(javax.swing.JTable table, int column, int width) {
        table.getColumnModel().getColumn(column).setMinWidth(width);
        table.getColumnModel().getColumn(column).setMaxWidth(width);
    }

    private void setTableModelData(DefaultTableModel model, String sql, String[] columnNames) {
        PreparedStatement stmt = null;
        int columnCount = columnNames.length;

        try {
            stmt = JDBCAdapter.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] hasil = new Object[columnCount];

                for (int i = 0; i < columnCount; i++) {
                    if (i == 2) {
                        hasil[i] = rs.getString(columnNames[i]).equals("S") ? "Swasta" : "Negeri";
                    } else {
                        hasil[i] = rs.getString(columnNames[i]);
                    }
                }

                model.addRow(hasil);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataSekolahInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataSekolahInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getSekolahAsal() {
        return sekolahAsal;
    }

    public JTable getSekolahTujuanTable() {
        return sekolahTujuanTable;
    }

    public Peserta getPeserta() {
        return peserta;
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
        jLabel4 = new javax.swing.JLabel();
        stCariButton = new javax.swing.JButton();
        namaStButton = new javax.swing.JTextField();
        pilihSekolahTujuanButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        sekolahTujuanDialogTable = new javax.swing.JTable();
        sekolahAsalDialog = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        cariSekolahAsalButton = new javax.swing.JButton();
        cariSekolahAsalTextField = new javax.swing.JTextField();
        pilihsekolahAsalButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        sekolahAsalDialogTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sekolahTujuanTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        asal = new javax.swing.JTextField();
        sekolahAsalButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        sekolahTujuanButton = new javax.swing.JButton();
        hapusButton = new javax.swing.JButton();
        simpanButton = new javax.swing.JButton();

        sekolahTujuanDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        sekolahTujuanDialog.setTitle("Cari Sekolah Tujuan");
        sekolahTujuanDialog.setMinimumSize(new java.awt.Dimension(783, 421));
        sekolahTujuanDialog.setResizable(false);
        sekolahTujuanDialog.setSize(new java.awt.Dimension(783, 446));
        sekolahTujuanDialog.setType(java.awt.Window.Type.POPUP);

        jLabel4.setText("Nama Sekolah");

        stCariButton.setText("Cari");
        stCariButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stCariButtonActionPerformed(evt);
            }
        });

        namaStButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaStButtonActionPerformed(evt);
            }
        });

        pilihSekolahTujuanButton.setText("Pilih");
        pilihSekolahTujuanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihSekolahTujuanButtonActionPerformed(evt);
            }
        });

        sekolahTujuanDialogTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(sekolahTujuanDialogTable);

        javax.swing.GroupLayout sekolahTujuanDialogLayout = new javax.swing.GroupLayout(sekolahTujuanDialog.getContentPane());
        sekolahTujuanDialog.getContentPane().setLayout(sekolahTujuanDialogLayout);
        sekolahTujuanDialogLayout.setHorizontalGroup(
            sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                .addGroup(sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pilihSekolahTujuanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(namaStButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stCariButton))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        sekolahTujuanDialogLayout.setVerticalGroup(
            sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sekolahTujuanDialogLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(sekolahTujuanDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(stCariButton)
                    .addComponent(namaStButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pilihSekolahTujuanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        sekolahAsalDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        sekolahAsalDialog.setTitle("Cari Sekolah Asal");
        sekolahAsalDialog.setMinimumSize(new java.awt.Dimension(783, 421));
        sekolahAsalDialog.setResizable(false);
        sekolahAsalDialog.setType(java.awt.Window.Type.POPUP);

        jLabel5.setText("Nama Sekolah");

        cariSekolahAsalButton.setText("Cari");
        cariSekolahAsalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariSekolahAsalButtonActionPerformed(evt);
            }
        });

        cariSekolahAsalTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariSekolahAsalTextFieldActionPerformed(evt);
            }
        });

        pilihsekolahAsalButton.setText("Pilih");
        pilihsekolahAsalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihsekolahAsalButtonActionPerformed(evt);
            }
        });

        sekolahAsalDialogTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(sekolahAsalDialogTable);

        javax.swing.GroupLayout sekolahAsalDialogLayout = new javax.swing.GroupLayout(sekolahAsalDialog.getContentPane());
        sekolahAsalDialog.getContentPane().setLayout(sekolahAsalDialogLayout);
        sekolahAsalDialogLayout.setHorizontalGroup(
            sekolahAsalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sekolahAsalDialogLayout.createSequentialGroup()
                .addGroup(sekolahAsalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pilihsekolahAsalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sekolahAsalDialogLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(sekolahAsalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sekolahAsalDialogLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cariSekolahAsalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cariSekolahAsalButton))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        sekolahAsalDialogLayout.setVerticalGroup(
            sekolahAsalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sekolahAsalDialogLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(sekolahAsalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cariSekolahAsalButton)
                    .addComponent(cariSekolahAsalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pilihsekolahAsalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Data Sekolah");

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
        sekolahTujuanTable.setFocusable(false);
        sekolahTujuanTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sekolahTujuanTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(sekolahTujuanTable);

        jLabel1.setText("Sekolah Asal");

        asal.setEditable(false);
        asal.setFocusable(false);

        sekolahAsalButton.setText("Pilih");
        sekolahAsalButton.setFocusable(false);
        sekolahAsalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sekolahAsalButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Sekolah Tujuan");

        jTextField2.setEditable(false);
        jTextField2.setFocusable(false);

        sekolahTujuanButton.setText("Pilih");
        sekolahTujuanButton.setFocusable(false);
        sekolahTujuanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sekolahTujuanButtonActionPerformed(evt);
            }
        });

        hapusButton.setText("Hapus");
        hapusButton.setEnabled(false);
        hapusButton.setFocusable(false);
        hapusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusButtonActionPerformed(evt);
            }
        });

        simpanButton.setText("Simpan");
        simpanButton.setEnabled(false);
        simpanButton.setFocusable(false);
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hapusButton)
                        .addGap(18, 18, 18)
                        .addComponent(simpanButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField2)
                                    .addGap(18, 18, 18)
                                    .addComponent(sekolahTujuanButton))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(asal)
                                    .addGap(18, 18, 18)
                                    .addComponent(sekolahAsalButton))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)))))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(asal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sekolahAsalButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sekolahTujuanButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanButton)
                    .addComponent(hapusButton))
                .addContainerGap(350, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sekolahTujuanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sekolahTujuanButtonActionPerformed
        if (sekolahTujuanTableModel.getRowCount() < 3) {
            String[] colNames = {"id", "nama", "status", "kecamatan", "alamat_jalan"};

            setTableModelData(
                    sekolahTujuanDialogTableModel,
                    "SELECT id, nama, status, kecamatan, alamat_jalan FROM smp",
                    colNames
            );

            sekolahTujuanDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            sekolahTujuanDialog.setLocationRelativeTo(null);
            sekolahTujuanDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Tidak boleh memilih lebih dari 3 sekolah!", "Galat", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_sekolahTujuanButtonActionPerformed

    private void sekolahAsalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sekolahAsalButtonActionPerformed
        String[] colNames = {"id", "nama", "status", "kecamatan", "alamat_jalan"};

        setTableModelData(
                sekolahAsalDialogTableModel,
                "SELECT id, nama, status, kecamatan, alamat_jalan FROM sd",
                colNames
        );

        sekolahAsalDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        sekolahAsalDialog.setLocationRelativeTo(null);
        sekolahAsalDialog.setVisible(true);
    }//GEN-LAST:event_sekolahAsalButtonActionPerformed

    private void cariSekolahAsalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariSekolahAsalButtonActionPerformed
        String[] colNames = {"id", "nama", "status", "kecamatan", "alamat_jalan"};

        sekolahAsalDialogTableModel.setRowCount(0);
        setTableModelData(
                sekolahAsalDialogTableModel,
                "SELECT id, nama, status, kecamatan, alamat_jalan FROM sd WHERE nama like '%" + cariSekolahAsalTextField.getText() + "%'",
                colNames
        );
    }//GEN-LAST:event_cariSekolahAsalButtonActionPerformed

    private void pilihsekolahAsalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihsekolahAsalButtonActionPerformed
        int selectedRow = sekolahAsalDialogTable.getSelectedRow();
        String selectedNamaSekolah = sekolahAsalDialogTableModel.getValueAt(selectedRow, 1).toString();
        sekolahAsal = Integer.parseInt(sekolahAsalDialogTable.getValueAt(selectedRow, 0).toString());

        asal.setText(selectedNamaSekolah);

        if (sekolahTujuanTable.getRowCount() == 3 && !asal.getText().equals("")) {
            simpanButton.setEnabled(true);
        } else {
            simpanButton.setEnabled(false);
        }

        sekolahAsalDialog.dispose();
    }//GEN-LAST:event_pilihsekolahAsalButtonActionPerformed

    private void stCariButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stCariButtonActionPerformed
        String[] colNames = {"id", "nama", "status", "kecamatan", "alamat_jalan"};

        sekolahTujuanDialogTableModel.setRowCount(0);
        setTableModelData(
                sekolahTujuanDialogTableModel,
                "SELECT id, nama, status, kecamatan, alamat_jalan FROM smp WHERE nama like '%" + namaStButton.getText() + "%'",
                colNames
        );
    }//GEN-LAST:event_stCariButtonActionPerformed

    private void pilihSekolahTujuanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihSekolahTujuanButtonActionPerformed
        int selectedRow = sekolahTujuanDialogTable.getSelectedRow();
        boolean isSelectedBefore = false;

        String selectedIdSekolah = sekolahTujuanDialogTableModel.getValueAt(selectedRow, 0).toString();

        if (sekolahTujuan.size() > 0) {
            for (String st : sekolahTujuan) {
                if (st.equals(selectedIdSekolah)) {
                    isSelectedBefore = true;
                }
            }
        }

        if (!isSelectedBefore) {
            sekolahTujuan.add(selectedIdSekolah);

            int no = sekolahTujuanTableModel.getRowCount() + 1;

            Object[] sekolahPilihan = {
                no,
                sekolahTujuanDialogTableModel.getValueAt(selectedRow, 0).toString(),
                sekolahTujuanDialogTableModel.getValueAt(selectedRow, 2).toString(),
                sekolahTujuanDialogTableModel.getValueAt(selectedRow, 1).toString()
            };

            sekolahTujuanTableModel.addRow(sekolahPilihan);

            if (sekolahTujuanTable.getRowCount() == 3 && !asal.getText().equals("")) {
                simpanButton.setEnabled(true);
            } else {
                simpanButton.setEnabled(false);
            }

            sekolahTujuanDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "SMP yang anda pilih sudah ditambahkan sebelumnya", "Perhatian!", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_pilihSekolahTujuanButtonActionPerformed

    private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
        int selectedRow = sekolahTujuanTable.getSelectedRow();

        sekolahTujuan.remove(selectedRow);

        sekolahTujuanTableModel.removeRow(selectedRow);

        for (int i = 0; i < sekolahTujuanTable.getRowCount(); i++) {
            sekolahTujuanTableModel.setValueAt(i + 1, i, 0);
        }

        if (sekolahTujuanTable.getRowCount() == 3 && !asal.getText().equals("")) {
            simpanButton.setEnabled(true);
        } else {
            simpanButton.setEnabled(false);
        }

        hapusButton.setEnabled(false);
    }//GEN-LAST:event_hapusButtonActionPerformed

    public void saveSekolahAsal() {
        String sql = "UPDATE peserta SET sekolah_asal = ?";
        try {
            PreparedStatement stmt = JDBCAdapter.getConnection().prepareStatement(sql);
            stmt.setInt(1, sekolahAsal);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataSekolahInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveSekolahTujuan() {
        for (int i = 0; i < sekolahTujuanTable.getRowCount(); i++) {
            String nisn = peserta.getNisn();
            String sekolahTujuan = sekolahTujuanTable.getValueAt(i, 1).toString();

            try {
                PreparedStatement stmt = JDBCAdapter.getConnection()
                        .prepareStatement("INSERT INTO sekolah_tujuan VALUES (?, ?)");
                stmt.setString(1, nisn);
                stmt.setString(2, sekolahTujuan);

                stmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DataSekolahInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        if (sekolahTujuanTable.getRowCount() == 3 && !asal.getText().equals("")) {
            controller.save();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Pemberitahuan", JOptionPane.INFORMATION_MESSAGE);

            sekolahAsalButton.setEnabled(false);
            sekolahTujuanButton.setEnabled(false);
            sekolahTujuanTable.setEnabled(false);
            simpanButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Sebelum menyimpan, pastikan anda telah mengisi sekolah asal dan menambahkan 3 sekolah tujuan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_simpanButtonActionPerformed

    private void sekolahTujuanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sekolahTujuanTableMouseClicked
        if (!sekolahTujuanTable.getSelectionModel().isSelectionEmpty()) {
            hapusButton.setEnabled(true);
        }
    }//GEN-LAST:event_sekolahTujuanTableMouseClicked

    private void namaStButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaStButtonActionPerformed
        stCariButtonActionPerformed(evt);
    }//GEN-LAST:event_namaStButtonActionPerformed

    private void cariSekolahAsalTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariSekolahAsalTextFieldActionPerformed
        cariSekolahAsalButtonActionPerformed(evt);
    }//GEN-LAST:event_cariSekolahAsalTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField asal;
    private javax.swing.JButton cariSekolahAsalButton;
    private javax.swing.JTextField cariSekolahAsalTextField;
    private javax.swing.JButton hapusButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField namaStButton;
    private javax.swing.JButton pilihSekolahTujuanButton;
    private javax.swing.JButton pilihsekolahAsalButton;
    private javax.swing.JButton sekolahAsalButton;
    private javax.swing.JDialog sekolahAsalDialog;
    private javax.swing.JTable sekolahAsalDialogTable;
    private javax.swing.JButton sekolahTujuanButton;
    private javax.swing.JDialog sekolahTujuanDialog;
    private javax.swing.JTable sekolahTujuanDialogTable;
    private javax.swing.JTable sekolahTujuanTable;
    private javax.swing.JButton simpanButton;
    private javax.swing.JButton stCariButton;
    // End of variables declaration//GEN-END:variables
}
