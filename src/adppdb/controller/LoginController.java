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
import adppdb.model.Admin;
import adppdb.model.Operator;
import adppdb.model.Peserta;
import adppdb.view.admin.BerandaFrame;
import adppdb.view.operator.OperatorFrame;
import adppdb.view.peserta.PesertaFrame;
import adppdb.view.main.LoginInternalFrame;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author samsul
 */
public class LoginController {

    private Peserta peserta;
    private Admin admin;
    private Operator operator;
    private LoginInternalFrame view;

    String id;
    char[] pass;

    public LoginController(LoginInternalFrame view) {
        this.view = view;
    }

    public void loginUser() {
        id = view.getEmail().getText();
        pass = view.getPassword().getPassword();

        peserta = new Peserta(id);
        admin = new Admin(id);
        operator = new Operator(id);

        if (peserta.passwordMatch(pass)) {
            view.getParentFrame().dispose();
            new PesertaFrame(peserta).setVisible(true);
        } else if (admin.passwordMatch(pass)) {
            view.getParentFrame().dispose();
            new BerandaFrame(admin).setVisible(true);
        } else if (operator.passwordMatch(pass)) {
            view.getParentFrame().dispose();
            new OperatorFrame(operator).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Username atau Password salah", "Galat", JOptionPane.ERROR_MESSAGE);
        }
    }
}
