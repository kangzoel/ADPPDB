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

import adppdb.controller.DataNilaiController;
import adppdb.database.JDBCAdapter;
import adppdb.model.Operator;
import adppdb.view.peserta.DataNilaiInternalFrame;
import com.mysql.cj.xdevapi.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import hu.kazocsaba.imageviewer.*;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.UIManager.getString;

/**
 *
 * @author samsul
 */
public class InputNilaiInternalFrame extends javax.swing.JInternalFrame {

    private Point origin;
    private DefaultTableModel model;
    private DataNilaiController dC;

    /**
     * Creates new form BerandaInternalFrame
     */
    public InputNilaiInternalFrame(Operator loggedInOperator) {
        initComponents();

        model = new DefaultTableModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        dC = new DataNilaiController(this);

        pesertaTable.setModel(model);
        model.addColumn("nisn");
        model.addColumn("nama");

        tampilPeserta();

        pesertaTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting() && pesertaTable.getSelectedRow() != -1) {

                String nama = pesertaTable.getValueAt(pesertaTable.getSelectedRow(), 1).toString();
                namaTextField.setText(nama);
                nisnView.setText(pesertaTable.getValueAt(pesertaTable.getSelectedRow(), 0).toString());

                //menampilkan skhun
                String sql1 = "SELECT * FROM data_nilai where nisn_peserta = '" + pesertaTable.getValueAt(pesertaTable.getSelectedRow(), 0).toString() + "'";

                try {
                    PreparedStatement stmt = (PreparedStatement) JDBCAdapter.getConnection().prepareStatement(sql1);

                    ResultSet rs = stmt.executeQuery(sql1);

                    while (rs.next()) {
                        bahasaView.setText(rs.getString(3));
                        mtkView.setText(rs.getString(5));
                        ipaView.setText(rs.getString(4));
                        java.sql.Blob blob = rs.getBlob("skhun");
                        InputStream in = blob.getBinaryStream();
                        BufferedImage image = ImageIO.read(in);
                        JDialog nilaiDialog = scrollableImageViewer(new JDialog(), nama, image);
                        nilaiDialog.setVisible(true);

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(InputNilaiInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "gambar skhun belum mungkin belum di upload mohon bersabar");
                }
            }
        });

        setMinMaxTableColumnWidth(pesertaTable, 0, 150);
    }

    public JTextField getCariPesertaView() {
        return cariPesertaView;
    }

    public JTextField getNisnView() {
        return nisnView;
    }

    public JTextField getBahasaView() {
        return bahasaView;
    }

    public JTextField getIpaView() {
        return ipaView;
    }

    public JTextField getMtkView() {
        return mtkView;
    }

    public void tampilPeserta() {
        String sql = "SELECT * FROM peserta p,data_nilai dn where p.nisn = dn.nisn_peserta and p.nisn like '" + getCariPesertaView().getText() + "%' and skhun is not null";
        try {

            Statement stmt = (Statement) JDBCAdapter.getConnection().createStatement();
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Object[] hasil;
                hasil = new Object[2];
                hasil[0] = res.getString("nisn");
                hasil[1] = res.getString("nama");
                model.addRow(hasil);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private JDialog scrollableImageViewer(JDialog jDialog, String name, BufferedImage image) {

        ImageViewer imageViewer = new ImageViewer(image, true);

        JDialog dialog = new JDialog();
        dialog.setTitle("SKHUN " + name);
        dialog.setSize(960, 720);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().add(imageViewer.getComponent());

        return dialog;
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

        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        cariPesertaView = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pesertaTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        namaTextField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        nisnView = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bahasaView = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        mtkView = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        ipaView = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(960, 570));
        setPreferredSize(new java.awt.Dimension(960, 570));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Input Nilai Peserta");

        jPanel3.setBackground(new java.awt.Color(224, 224, 224));

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Cari Peserta (NISN)");

        pesertaTable.setModel(new javax.swing.table.DefaultTableModel(
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
        pesertaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pesertaTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pesertaTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(27, 27, 27)
                .addComponent(cariPesertaView, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addContainerGap(49, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cariPesertaView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Nilai Peserta");

        jLabel19.setText("Nama");

        namaTextField.setEnabled(false);
        namaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaTextFieldActionPerformed(evt);
            }
        });

        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        nisnView.setEnabled(false);
        nisnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nisnViewActionPerformed(evt);
            }
        });

        jLabel20.setText("NISN");

        jLabel1.setText("Bahasa Indonesia");

        jLabel14.setText("Matematika");

        jLabel17.setText("IPA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ipaView)
                    .addComponent(bahasaView)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nisnView)
                            .addComponent(namaTextField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addGap(272, 272, 272))
                    .addComponent(mtkView)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel14)
                            .addComponent(jLabel1)
                            .addComponent(jButton2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(113, 113, 113))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nisnView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bahasaView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mtkView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ipaView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO cari peserta:
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        tampilPeserta();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void namaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaTextFieldActionPerformed

    private void pesertaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesertaTableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_pesertaTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dC.inputNilaiPeserta();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void nisnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nisnViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nisnViewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bahasaView;
    private javax.swing.JTextField cariPesertaView;
    private javax.swing.JTextField ipaView;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mtkView;
    private javax.swing.JTextField namaTextField;
    private javax.swing.JTextField nisnView;
    private javax.swing.JTable pesertaTable;
    // End of variables declaration//GEN-END:variables
}
