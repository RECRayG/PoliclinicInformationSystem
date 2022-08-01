package registryApplication.tableModels;

import Connector.CONST;
import Connector.JDBCConnection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class patientsTableModel extends AbstractTableModel
{
    private int columnCount = 14;
    private ArrayList dataArrayList = new ArrayList(); // Список списка БД
    private ArrayList columnTypes = new ArrayList(); // Типы данных солонок
    private ArrayList columnNames = new ArrayList(); // Имена колонок, как в БД
    private Vector<String> columnRUSNames = new Vector(); // Имена колонок на русском
    private ListSelectionModel selModel;
    private boolean editable = false;
    private JTable table;

    public patientsTableModel(JTable table)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.table = table;

        columnRUSNames.add("Фамилия");
        columnRUSNames.add("Имя");
        columnRUSNames.add("Отчество");
        columnRUSNames.add("Пол");
        columnRUSNames.add("Дата рождения");
        columnRUSNames.add("Участок");
        columnRUSNames.add("Дата регистрации");
        columnRUSNames.add("Номер карты");
        columnRUSNames.add("Номер паспорта");
        columnRUSNames.add("Номер телефона");
        columnRUSNames.add("Страховой полис");
        columnRUSNames.add("Улица");
        columnRUSNames.add("Дом");
        columnRUSNames.add("Квартира");
    }

    @Override
    public int getRowCount() {
        return dataArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex);
    }

    public ArrayList getColumnTypes()
    {
        return this.columnTypes;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)  // Какие данные отображать в колонке
    {
        return (Class)columnTypes.get(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) // Можно ли редактировать данные в ячейке таблицы
    {
        return editable;
    }

    @Override
    public String getColumnName(int ColumnIndex)
    {
        switch (ColumnIndex)
        {
            case 0: return (String) columnRUSNames.get(0);
            case 1: return (String) columnRUSNames.get(1);
            case 2: return (String) columnRUSNames.get(2);
            case 3: return (String) columnRUSNames.get(3);
            case 4: return (String) columnRUSNames.get(4);
            case 5: return (String) columnRUSNames.get(5);
            case 6: return (String) columnRUSNames.get(6);
            case 7: return (String) columnRUSNames.get(7);
            case 8: return (String) columnRUSNames.get(8);
            case 9: return (String) columnRUSNames.get(9);
            case 10: return (String) columnRUSNames.get(10);
            case 11: return (String) columnRUSNames.get(11);
            case 12: return (String) columnRUSNames.get(12);
            case 13: return (String) columnRUSNames.get(13);
        }
        return "";
    }

    public void setColumnStaticWidth(JTable tableOfPatients)
    {
        TableColumnModel tcm = tableOfPatients.getColumnModel();
        Enumeration<TableColumn> e = tcm.getColumns();
        int i = 0;

        BufferedImage img = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        FontMetrics fm = g2d.getFontMetrics();

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(0); // Выровнять значение по центру колонки

        while(e.hasMoreElements())
        {
            TableColumn column = e.nextElement();
            switch(i)
            {
                case 0: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(0) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(0) + "DDDDDDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 1: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(1) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(1) + "DDDDDDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 2: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(2) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(2) + "DDDDDDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 3: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(3) + "DDDDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(3) + "DDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 4: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(4) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(4) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 5: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(5) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(5) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 6: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(6) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(6) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 7: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(7) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(7) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 8: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(8) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(8) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 9: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(9) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(9) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 10: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(10) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(10) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 11: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(11) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(11) + "DDDDDDDDDDDDDDDDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 12: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(12) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(12) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 13: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(13) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(13) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
            }
            i++;
        }
    }

    public ArrayList getColumnNames()
    {
        return this.columnNames;
    }
    public Vector<String> getColumnRUSNames()
    {
        return this.columnRUSNames;
    }
    public ArrayList getDataArrayList()
    {
        return this.dataArrayList;
    }

    public void addData(JDBCConnection jdbcConnection) throws SQLException // Вставка данных в таблицу
    {
        if(!dataArrayList.isEmpty()) dataArrayList.clear();
        if(!columnTypes.isEmpty()) columnTypes.clear();
        if(!columnNames.isEmpty()) columnNames.clear();

        Statement st = null;
        try
        {
            st = jdbcConnection.getDbConnecion().createStatement();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        ResultSet resSet = jdbcConnection.getPatientsForReferenceTable(st);


        ResultSetMetaData rsmd = resSet.getMetaData();
        columnCount = rsmd.getColumnCount();

        while(resSet.next())
        {
            ArrayList row = new ArrayList();
            row.add(resSet.getString(CONST.PATIENT_SECONDNAME));
            row.add(resSet.getString(CONST.PATIENT_FIRSTNAME));
            row.add(resSet.getString(CONST.PATIENT_THIRDNAME));
            row.add(resSet.getString(CONST.PATIENT_GENDER));
            row.add(resSet.getDate(CONST.PATIENT_BIRTHDAY));
            row.add(resSet.getInt(CONST.PATIENT_NUMBER_OF_PLOT));
            row.add(resSet.getDate(CONST.PATIENT_DATE_OF_CREATE_CARD));
            row.add(resSet.getString(CONST.PATIENT_NUMBER_OF_CARD));
            row.add(resSet.getString(CONST.PATIENT_NUMBER_OF_PASPORT));
            row.add(resSet.getString(CONST.PATIENT_NUMBER_OF_PHONE));
            row.add(resSet.getString(CONST.PATIENT_INSURANCE_POLICY));
            row.add(resSet.getString(CONST.PATIENT_STREET));
            row.add(resSet.getInt(CONST.PATIENT_BUILDING));
            row.add(resSet.getInt(CONST.PATIENT_APARTMENT));

            dataArrayList.add(row); // обавление новой строки в таблицу на каждой итерации
        }

        try
        {
            int i = 0;
            for(; i < columnCount-2; i++)
            {
                if(i == 7)
                {
                    columnTypes.add(Class.forName( ((ArrayList)dataArrayList.get(0)).get(i).getClass().getName()) );
                    columnNames.add(rsmd.getColumnName(i+1));
                    continue;
                }

                columnTypes.add(Class.forName(rsmd.getColumnClassName(i+1)));
                columnNames.add(rsmd.getColumnName(i+1));
            }
            columnTypes.add( Class.forName( ((ArrayList)dataArrayList.get(0)).get(i).getClass().getName()) );
            columnTypes.add( Class.forName( ((ArrayList)dataArrayList.get(0)).get(i+1).getClass().getName()) );
            columnNames.add(rsmd.getColumnName(i));
            columnNames.add(rsmd.getColumnName(i+1));
        } catch (ClassNotFoundException e) { e.printStackTrace(); }

        jdbcConnection.dbDisconnectionResSet(resSet);
        jdbcConnection.dbDisconnectionSt(st);
    }

    public void updateData(JDBCConnection jdbcConnection) throws SQLException
    {
        Statement st = null;
        try
        {
            st = jdbcConnection.getDbConnecion().createStatement();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        ResultSet resSet = jdbcConnection.getPatientsForReferenceTable(st);

        boolean be = true;
        while(resSet.next())
        {
            be = true;
            for(int i = 0; i < dataArrayList.size(); i++)
            {
                if(resSet.getString(CONST.PATIENT_NUMBER_OF_CARD).equals( ((ArrayList)dataArrayList.get(i)).get(7).toString() ) ) { be = true; break;}
                else if(be) { be = false; continue; }
                else if( (!be) && ((i+1) == dataArrayList.size()) ) // Если не было ни одного совпадения и цикл на последней итерации
                {
                    ArrayList row = new ArrayList();
                    row.add(resSet.getString(CONST.PATIENT_SECONDNAME));
                    row.add(resSet.getString(CONST.PATIENT_FIRSTNAME));
                    row.add(resSet.getString(CONST.PATIENT_THIRDNAME));
                    row.add(resSet.getString(CONST.PATIENT_GENDER));
                    row.add(resSet.getDate(CONST.PATIENT_BIRTHDAY));
                    row.add(resSet.getInt(CONST.PATIENT_NUMBER_OF_PLOT));
                    row.add(resSet.getDate(CONST.PATIENT_DATE_OF_CREATE_CARD));
                    row.add(resSet.getString(CONST.PATIENT_NUMBER_OF_CARD));
                    row.add(resSet.getString(CONST.PATIENT_NUMBER_OF_PASPORT));
                    row.add(resSet.getString(CONST.PATIENT_NUMBER_OF_PHONE));
                    row.add(resSet.getString(CONST.PATIENT_INSURANCE_POLICY));
                    row.add(resSet.getString(CONST.PATIENT_STREET));
                    row.add(resSet.getString(CONST.PATIENT_BUILDING));
                    row.add(resSet.getString(CONST.PATIENT_APARTMENT));

                    dataArrayList.add(row); // Добавление новой строки в таблицу
                    break;
                }
            }
        }

        jdbcConnection.dbDisconnectionResSet(resSet);
        jdbcConnection.dbDisconnectionSt(st);
        fireTableDataChanged();
    }

    public ArrayList deleteData(int[] selectedRows, JTable table, JDBCConnection jdbcConnection)
    {
        ArrayList rowForDelete = new ArrayList();
        ArrayList<String> deleteList = new ArrayList();
        int lastRow = 0;

        for(int i = 0; i < selectedRows.length; i++)
        {
            for(int j = 0; j < dataArrayList.size(); j++)
            {
                if( table.getValueAt(selectedRows[i], 7) == ((ArrayList)dataArrayList.get(j)).get(7) )
                {
                    rowForDelete.add(dataArrayList.get(j)); // Список строк, подлежащих удалению
                    break;
                }
            }
            lastRow = selectedRows[i];
        }

        if(rowForDelete.size() != dataArrayList.size())
        {
            for(int i = 0; i < rowForDelete.size(); i++)
            {
                deleteList.add( "DELETE FROM " + CONST.PATIENT_TABLE +
                        " WHERE " + CONST.PATIENT_NUMBER_OF_CARD + " = " + ((ArrayList)rowForDelete.get(i)).get(7).toString() );
                deleteList.add( "DELETE FROM " + CONST.RECEPTION_TABLE + " WHERE " + CONST.RECEPTION_NUMBER_OF_CARD + " = " + ((ArrayList)rowForDelete.get(i)).get(7).toString());
                deleteList.add( "DELETE FROM " + CONST.COMPLAINTS_TABLE + " WHERE " + CONST.RECEPTION_NUMBER_OF_CARD + " = " + ((ArrayList)rowForDelete.get(i)).get(7).toString());

                for(int k = 0; k < dataArrayList.size(); k++)
                {
                    if( ((ArrayList)dataArrayList.get(k)).get(0) == ((ArrayList)rowForDelete.get(i)).get(0) )
                    {
                        dataArrayList.remove(k);
                        break;
                    }
                }
            }
        }
        else // Если были выбраны все имеющиеся строки
        {
            deleteList.add("DELETE FROM " + CONST.PATIENT_TABLE);
        }

        jdbcConnection.executeDB(deleteList);
        fireTableRowsDeleted(table.convertRowIndexToModel(selectedRows[0]), table.convertRowIndexToModel(lastRow));
        fireTableDataChanged(); // Уведомление для класса, что нужно перерисовать таблицу
        return rowForDelete;
    }

}
