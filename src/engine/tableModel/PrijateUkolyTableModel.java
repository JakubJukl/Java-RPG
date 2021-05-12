/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.tableModel;

import engine.model.Ukol;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jakub Jukl
 */
public class PrijateUkolyTableModel extends AbstractTableModel {
    private final String COLUMN1 = "Úkol";
    private final String COLUMN2 = "Možné odevzdat?";
    
    private final ArrayList<Ukol> ukolList;
    private final String[] columnJmena = new String[]{COLUMN1, COLUMN2};
    private final Class[] columnClass = new Class[]{String.class, String.class};

    public PrijateUkolyTableModel(ArrayList<Ukol> ukolList) {
        this.ukolList = ukolList;
    }

    @Override
    public String getColumnName(int column) {
        return columnJmena[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columnJmena.length;
    }

    @Override
    public int getRowCount() {
        return ukolList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ukol row = ukolList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getJmeno();
            case 1:
                return row.isSplnenaKriteria() ? "Ano" : "Ne";
            default:
                break;
        }
        return null;
    }



}
