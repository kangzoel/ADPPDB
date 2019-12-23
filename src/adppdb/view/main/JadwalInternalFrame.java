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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class JadwalInternalFrame extends javax.swing.JInternalFrame {

    private PreparedStatement pst;
    private ResultSet rs;
    private DefaultTableModel jadwalModel;

    /**
     * Creates new form JadwalInternalFrame
     */
    public JadwalInternalFrame() {
        initComponents();

        // Setup tabel jadwal
        String[] jadwalColumnNames = {
            "No",
            "Kegiatan",
            "Lokasi",
            "Waktu Mulai",
            "Waktu Berakhir",};
        Object[][] data = {null};
        jadwalModel = new DefaultTableModel(null, jadwalColumnNames);

        jadwalTable.setModel(jadwalModel);
        
        tampilJadwal();
        
        setMinMaxTableColumnWidth(jadwalTable, 0, 40);
        setMinMaxTableColumnWidth(jadwalTable, 2, 100);
        setMinMaxTableColumnWidth(jadwalTable, 3, 130);
        setMinMaxTableColumnWidth(jadwalTable, 4, 130);
    }

    private void setMinMaxTableColumnWidth(javax.swing.JTable table, int column, int width) {
        table.getColumnModel().getColumn(column).setMinWidth(width);
        table.getColumnModel().getColumn(column).setMaxWidth(width);
    }

    
    public void tampilJadwal() {

        String sql = "SELECT * FROM jadwal";
        try {

            pst = JDBCAdapter.getConnection().prepareStatement(sql);
            rs = pst.executeQuery();

            int i = 1;
            while (rs.next()) {
                Object[] hasil = {
                    i,
                    rs.getString("kegiatan"),
                    rs.getString("lokasi"),
                    rs.getString("tanggal_mulai").replace("-", "/").split(" ")[0] + "  -  " + rs.getString("waktu_mulai"),
                    rs.getString("tanggal_berakhir").replace("-", "/").split(" ")[0] + "  -  " + rs.getString("waktu_berakhir"),};

                jadwalModel.addRow(hasil);

                i++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(JadwalInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jadwalTable = new javax.swing.JTable();

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Jadwal");

        jadwalTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jadwalTable.setFocusable(false);
        jScrollPane1.setViewportView(jadwalTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jadwalTable;
    // End of variables declaration//GEN-END:variables
}