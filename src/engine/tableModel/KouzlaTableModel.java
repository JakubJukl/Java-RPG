/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.tableModel;

import engine.model.Item;
import engine.model.MnozstviItemu;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jakub Jukl
 */
public class KouzlaTableModel extends AbstractTableModel {
    private final String COLUMN1 = "Kouzlo";
    private final String COLUMN2 = "Cena many";
    
    private final ArrayList<MnozstviItemu> kouzloList;
    private final String[] columnJmena = new String[]{COLUMN1, COLUMN2};
    private final Class[] columnClass = new Class[]{String.class, int.class};

    public KouzlaTableModel(ArrayList<MnozstviItemu> kouzloList) {
        this.kouzloList = new ArrayList<>(kouzloList.stream().filter(i -> i.item.getDruh()
                == Item.ItemDruh.Kouzlo).collect(Collectors.toList()));
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
        return kouzloList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MnozstviItemu row = kouzloList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.item.getJmeno();
            case 1:
                return row.item.getAkce().cenaProvedeni();
            default:
                break;
        }
        return null;
    }

}
