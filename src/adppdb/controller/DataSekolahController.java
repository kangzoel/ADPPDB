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
import adppdb.model.DataSekolah;
import adppdb.model.SMP;
import adppdb.view.operator.AturSekolahInternalFrame;
import adppdb.view.peserta.DataSekolahInternalFrame;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsul
 */
public class DataSekolahController {

    DataSekolah model = new DataSekolah();
    SMP smp = new SMP();

    public AturSekolahInternalFrame aV;
    public DataSekolahInternalFrame view;

    public DataSekolahController(DataSekolahInternalFrame view) {
        this.view = view;
    }

    public DataSekolahController(AturSekolahInternalFrame aV) {
        this.aV = aV;
    }

    public void save() {
        model.saveSekolahAsal(view.getPeserta(), view.getSekolahAsal());
        model.saveSekolahTujuan(view.getPeserta(), view.getSekolahTujuanTable());
    }

    public void simpanAturSekolah() {
        smp = new SMP();
        smp.setKecamatan(aV.getKecamatanView().getText());
        smp.setNpsn(aV.getNpsnView().getText());
        smp.setNama(aV.getNamaView().getText());
        String radio;
        if (aV.getNegeri().isSelected()) {
            smp.setStatus("N");
        } else if (aV.getSwasta().isSelected()) {
            smp.setStatus("S");
        }
        smp.setBatasPenerimaan(aV.getBatasView().getText());
        smp.setAlamatJalan(aV.getAlamatView().getText());
        smp.aturSekolah(aV.getOperator().getId());
    }

}
