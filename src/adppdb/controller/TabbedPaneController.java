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
package adppdb.controller;

import adppdb.model.TabbedPane;
import adppdb.view.peserta.PesertaFrame;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author samsul
 */
public class TabbedPaneController {

    private TabbedPane model;
    private JDesktopPane desktopPane;
    private ChangeListener changeListener;
    private ArrayList<JInternalFrame> tabs;

    public TabbedPaneController(JDesktopPane desktopPane, Object[] tabs) {
        /*
         * Penambahan tab
         *
         * Jika menambahkan tab ke dalam tabbedPane di view PesertaFrame, maka harus
         * menambahkannya ke list tabs ini agar bisa digunakan.
         */
        this.tabs = new ArrayList<JInternalFrame>();
        this.desktopPane = desktopPane;

        for (Object tab : tabs) {
            this.tabs.add((JInternalFrame) tab);
        }

        model = new TabbedPane();

        /*
         * Change Listener untuk tabbedPane
         *
         * Untuk memeriksa apakah ada perubahan tab yang sedang aktif pada
         * tabbedPane di view PesertaFrame.
         */
        changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                JTabbedPane tp = (JTabbedPane) event.getSource();
                int oldIndex = model.getSelectedIndex(),
                        selectedIndex = tp.getSelectedIndex();

                if (oldIndex != selectedIndex) {
                    desktopPane.removeAll();

                    changeTab(selectedIndex);

                    setSelectedIndex(selectedIndex);
                    tp.setSelectedIndex(selectedIndex);
                }
            }
        };

        changeTab(0);
    }

    public ChangeListener getChangeListener() {
        return changeListener;
    }

    public void setSelectedIndex(int selectedIndex) {
        model.setSelectedIndex(selectedIndex);
    }

    public void changeTab(int index) {
        try {
            tabs.get(index).setVisible(true);
        } catch (java.lang.IndexOutOfBoundsException error) {
            System.out.println("Tab dengan index " + index + " belum ditambahkan"
                    + " pada variabel tabs di konstruktor view Main");
        }

//        try {
//            tabs.get(index).setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(PesertaFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
        ((BasicInternalFrameUI) tabs.get(index).getUI()).setNorthPane(null);

        tabs.get(index).setBorder(null);

        this.desktopPane.add(tabs.get(index));
    }
}
