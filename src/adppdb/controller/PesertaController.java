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

import adppdb.model.Peserta;
import adppdb.utility.PasswordAuthentication;
import adppdb.view.peserta.PesertaFrame;
import adppdb.view.main.DaftarInternalFrame;
import adppdb.view.peserta.DataDiriInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author samsul
 */
public class PesertaController {

    private Peserta model = new Peserta();
    private DaftarInternalFrame daftarView;
    private DataDiriInternalFrame dataDiriView;

    public PesertaController(DaftarInternalFrame view) {
        this.daftarView = view;
    }

    public PesertaController(DataDiriInternalFrame view) {
        this.dataDiriView = view;
    }

    public void register() {
        if (!daftarView.getNisn().getText().equals("")
                && !daftarView.getNama().getText().equals("")
                && !daftarView.getEmail().getText().equals("")
                && !String.valueOf(daftarView.getPassword().getPassword()).equals("")) {

            String pass = String.valueOf(daftarView.getPassword().getPassword());
            String passConf = String.valueOf(daftarView.getPasswordConfirmation().getPassword());

            if (pass.equals(passConf)) {
                PasswordAuthentication pa = new PasswordAuthentication();

                model.setNama(daftarView.getNama().getText());
                model.setNisn(daftarView.getNisn().getText());
                model.setEmail(daftarView.getEmail().getText());
                model.setPassword(pa.hash(daftarView.getPassword().getPassword()));
                model.register();

                JOptionPane.showMessageDialog(null, "Peserta " + model.getNama() + " berhasil dibuat dengan detail login:\n"
                        + "NISN : " + model.getNisn() + "\n"
                        + "Email : " + model.getEmail() + "\n"
                        + "Password :  " + String.valueOf(daftarView.getPassword().getPassword()) + "\n"
                        + "Password harap disimpan baik-baik", "Peserta berhasil dibuat!", JOptionPane.INFORMATION_MESSAGE);

                daftarView.getParentFrame().dispose();

                model = new Peserta(model.getNisn());

                new PesertaFrame(model).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Konfirmasi password salah!", "Galat", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Harap isi formulir secara lengkap!", "Galat", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void simpanDataDiri() {
        model.setNama(dataDiriView.getNamaLengkap().getText());
        model.setEmail(dataDiriView.getEmail().getText());
        model.setNisn(dataDiriView.getNisn().getText());

        if (dataDiriView.getLakiLaki().isSelected()) {
            model.setJenisKelamin("Laki-laki");
        } else if (dataDiriView.getPerempuan().isSelected()) {
            model.setJenisKelamin("Perempuan");
        }

        model.setTempatLahir(dataDiriView.getTempatLahir().getText());
        model.setTanggalLahir(dataDiriView.getTanggalLahir().getDateStringOrEmptyString());
        model.setProvinsi(dataDiriView.getProvinsi().getText());
        model.setKecamatan(dataDiriView.getKecamatan().getText());
        model.setKota(dataDiriView.getKota().getText());
        model.setAlamat(dataDiriView.getAlamat().getText());
        model.setNamaWali(dataDiriView.getNamaWali().getText());
        model.setStatusWali((String) dataDiriView.getStatusWali().getSelectedItem());
        model.setAlamatWali(dataDiriView.getAlamatWali().getText());
        model.simpanDataDiri();
    }
}
