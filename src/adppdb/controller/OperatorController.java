/*
 * Copyright (C) 2019 Zarkasih28
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

import adppdb.model.Operator;
import adppdb.model.SMP;
import adppdb.view.admin.BuatOperatorFrame;
import static javax.swing.UIManager.getString;

/**
 *
 * @author Zarkasih28
 */
public class OperatorController {

    private BuatOperatorFrame BO;
    private Operator OP;
    private SMP smp;

    public OperatorController(BuatOperatorFrame BO) {
        this.BO = BO;
        OP = new Operator();
    }

    public void buat() {

        OP.setNama(BO.getNamaOp().getText());
        OP.setEmail(BO.getEmailOp().getText());
        OP.setPassword(String.valueOf(BO.getPassOp().getPassword()));
        OP.setIdSmp(BO.getIdSmp());

        OP.buatOperator();
    }

    public void cari() {

    }
}
