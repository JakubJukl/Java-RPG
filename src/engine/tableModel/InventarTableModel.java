/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.tableModel;

import engine.model.MnozstviItemu;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InventarTableModel extends AbstractTableModel {
    private final String COLUMN1 = "Item";
    private final String COLUMN2 = "Množství";
    
    private final ArrayList<MnozstviItemu> inventarList;
    private final String[] columnJmena = new String[]{COLUMN1, COLUMN2};
    private final Class[] columnClass = new Class[]{String.class, int.class};

    public InventarTableModel(ArrayList<MnozstviItemu> inventarList) {
        this.inventarList = inventarList;
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
        return inventarList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MnozstviItemu row = inventarList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.item.getJmeno();
            case 1:
                return row.mnozstvi;
            default:
                break;
        }
        return null;
    }

}
