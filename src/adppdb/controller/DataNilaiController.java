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
package adppdb.controller;

import adppdb.database.JDBCAdapter;
import adppdb.model.DataNilai;
import adppdb.model.Peserta;
import adppdb.view.operator.InputNilaiInternalFrame;
import adppdb.view.peserta.DataNilaiInternalFrame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author samsul
 */
public class DataNilaiController {

    private final DataNilai model = new DataNilai();
    private DataNilaiInternalFrame view = null;
    private InputNilaiInternalFrame inputView = null;

    public DataNilaiController(DataNilaiInternalFrame view) {
        this.view = view;
    }

    public DataNilaiController(InputNilaiInternalFrame inputView) {
        this.inputView = inputView;
    }

    public void uploadSkhun() {
        javax.swing.UIManager.put("FileChooser.readOnly", Boolean.TRUE);

        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fc.setFileFilter(new FileFilter() {
            String[] formats = {
                ".xbm",
                ".tif",
                ".pjp",
                ".pjpeg",
                ".jpg",
                ".jpeg",
                ".ico",
                ".tiff",
                ".gif",
                ".svg",
                ".bmp",
                ".png",
                ".jfif",
                ".heic",
                ".heif",
                ".jfif",
                ".jfif",};

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();

                    boolean filter = filename.endsWith(".jpg");

                    for (String format : formats) {
                        filter = filter || filename.endsWith(format);
                    }

                    return filter;
                }
            }

            @Override
            public String getDescription() {
                String toReturn = "Image Files (";
                for (int i = 0; i < formats.length; i++) {
                    if (i == 0) {
                        toReturn += "*" + formats[i];
                    } else {
                        toReturn += ", *" + formats[i];
                    }
                }
                toReturn += ")";

                return toReturn;
            }
        });

        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            FileInputStream input;
            model.setNisnPeserta(view.getPeserta().getNisn());

            try {
                input = new FileInputStream(file);

                if (view.dataNilaiIsExists()) {
                    model.updateSkhun(input);
                } else {
                    model.insertSkhun(input);
                }
                
                view.setPeserta(new Peserta(view.getPeserta().getNisn()));
                view.getPanelSkhun().removeAll();
                view.tampilSkhun();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "File tidak ditemukan." + ex, "Galat", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void inputNilaiPeserta() {
        model.setNisnPeserta(inputView.getNisnView().getText());
        model.setBahasaIndonesia(inputView.getBahasaView().getText());
        model.setIpa(inputView.getIpaView().getText());
        model.setMatematika(inputView.getMtkView().getText());
        model.insertNilaiPeserta();
    }

}
