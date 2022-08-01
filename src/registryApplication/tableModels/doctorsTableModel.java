package registryApplication.tableModels;

import Connector.CONST;
import Connector.JDBCConnection;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class doctorsTableModel extends AbstractTableModel
{
    private int columnCount = 10;
    private ArrayList dataArrayList = new ArrayList(); // Список списка БД
    private ArrayList columnTypes = new ArrayList(); // Типы данных солонок
    private ArrayList columnNames = new ArrayList(); // Имена колонок, как в БД
    private Vector columnRUSNames = new Vector(); // Имена колонок на русском
    private ListSelectionModel selModel;
    private boolean editable = false;
    private JTable table;

    public doctorsTableModel(JTable table)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.table = table;

        columnRUSNames.add("Табличный номер");
        columnRUSNames.add("Фамилия");
        columnRUSNames.add("Имя");
        columnRUSNames.add("Отчество");
        columnRUSNames.add("Пол");
        columnRUSNames.add("Дата рождения");
        columnRUSNames.add("Специальность");
        columnRUSNames.add("Код специальности");
        columnRUSNames.add("Дата устройства на работу");
        columnRUSNames.add("Номер участка");
    }

    @Override
    public int getRowCount() // Возвращает кол-во строк в таблице
    {
        return dataArrayList.size();
    }

    public ArrayList getColumnTypes()
    {
        return this.columnTypes;
    }

    @Override
    public int getColumnCount() // Возвращает кол-во колонок в таблице
    {
        return columnCount;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)  // Какие данные отображать в колонке
    {
        return (Class)columnTypes.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) // Помещает значение в определённую ячейку таблицы
    {
        return ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex); // Взятие элемента по индексу
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) // Можно ли редактировать данные в ячейке таблицы
    {
        return editable;
    }

    public void setCellEditable(boolean editable)
    {
        this.editable = editable;
    }

    @Override
    public String getColumnName(int ColumnIndex)
    {
        switch(ColumnIndex)
        {
            case 0: return (String)columnRUSNames.get(0);
            case 1: return (String)columnRUSNames.get(1);
            case 2: return (String)columnRUSNames.get(2);
            case 3: return (String)columnRUSNames.get(3);
            case 4: return (String)columnRUSNames.get(4);
            case 5: return (String)columnRUSNames.get(5);
            case 6: return (String)columnRUSNames.get(6);
            case 7: return (String)columnRUSNames.get(7);
            case 8: return (String)columnRUSNames.get(8);
            case 9: return (String)columnRUSNames.get(9);
        }
        return "";
    }

    public void setColumnStaticWidth(JTable tableOfDoctors)
    {
        TableColumnModel tcm = tableOfDoctors.getColumnModel();
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
                case 0: column.setMinWidth(fm.stringWidth("Табличный номер" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Табличный номер" + "DDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 1: column.setMinWidth(fm.stringWidth("Фамилия" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Фамилия" + "DDDDDDDDDDDDDDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 2: column.setMinWidth(fm.stringWidth("Имя" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Имя" + "DDDDDDDDDDDDDDD"));
                        column.setPreferredWidth(fm.stringWidth("Имя" + "DDDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 3: column.setMinWidth(fm.stringWidth("Отчество" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Отчество" + "DDDDDDDDDDDDDDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 4: column.setMinWidth(fm.stringWidth("Пол" + "DDDDD"));
                        column.setMaxWidth(fm.stringWidth("Пол" + "DDDDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 5: column.setMinWidth(fm.stringWidth("Дата рождения" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Дата рождения" + "DDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 6: column.setMinWidth(fm.stringWidth("Специальность" + "DDD"));
                        column.setMaxWidth(305);
                        column.setCellRenderer(cellRenderer);
                        break;
                case 7: column.setMinWidth(fm.stringWidth("Код специальности" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Код специальности" + "DDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 8: column.setMinWidth(fm.stringWidth("Дата устройства на работу" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Дата устройства на работу" + "DDD"));
                        column.setCellRenderer(cellRenderer);
                        break;
                case 9: column.setMinWidth(fm.stringWidth("Номер участка" + "DDD"));
                        column.setMaxWidth(fm.stringWidth("Номер участка" + "DDD"));
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
    public Vector getColumnRUSNames()
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
        ResultSet resSet = jdbcConnection.getDoctorsForReferenceTable(st);


        ResultSetMetaData rsmd = resSet.getMetaData();
        columnCount = rsmd.getColumnCount();
        try
        {
            for(int i = 0; i < columnCount; i++)
            {
                columnTypes.add(Class.forName(rsmd.getColumnClassName(i+1)));
                columnNames.add(rsmd.getColumnName(i+1));
            }
        } catch (ClassNotFoundException e) { e.printStackTrace(); }

        while(resSet.next())
        {
            ArrayList row = new ArrayList();
            row.add(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
            row.add(resSet.getString(CONST.DOCTORS_SECONDNAME));
            row.add(resSet.getString(CONST.DOCTORS_FIRSTNAME));
            row.add(resSet.getString(CONST.DOCTORS_THIRDNAME));
            row.add(resSet.getString(CONST.DOCTORS_GENDER));
            row.add(resSet.getDate(CONST.DOCTORS_BIRTHDAY));
            row.add(resSet.getString(CONST.SPECIALIZATION));
            row.add(resSet.getInt(CONST.SPECIALIZATION_CODE));
            row.add(resSet.getDate(CONST.DOCTORS_DATE_START_JOB));
            row.add(resSet.getInt(CONST.PLOTS_NUMBER));

            dataArrayList.add(row); // обавление новой строки в таблицу на каждой итерации
        }

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
        ResultSet resSet = jdbcConnection.getDoctorsForReferenceTable(st);

        boolean be = true;
        while(resSet.next())
        {
            be = true;
            for(int i = 0; i < dataArrayList.size(); i++)
            {
                if(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER) == (Integer)((ArrayList)dataArrayList.get(i)).get(0)) { be = true; break;}
                else if(be) { be = false; continue; }
                else if( (!be) && ((i+1) == dataArrayList.size()) ) // Если не было ни одного совпадения и цикл на последней итерации
                {
                    ArrayList row = new ArrayList();
                    row.add(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
                    row.add(resSet.getString(CONST.DOCTORS_SECONDNAME));
                    row.add(resSet.getString(CONST.DOCTORS_FIRSTNAME));
                    row.add(resSet.getString(CONST.DOCTORS_THIRDNAME));
                    row.add(resSet.getString(CONST.DOCTORS_GENDER));
                    row.add(resSet.getDate(CONST.DOCTORS_BIRTHDAY));
                    row.add(resSet.getString(CONST.SPECIALIZATION));
                    row.add(resSet.getInt(CONST.SPECIALIZATION_CODE));
                    row.add(resSet.getDate(CONST.DOCTORS_DATE_START_JOB));
                    row.add(resSet.getInt(CONST.PLOTS_NUMBER));

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
                if( table.getValueAt(selectedRows[i], 0) == ((ArrayList)dataArrayList.get(j)).get(0) )
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
                deleteList.add( "DELETE " + CONST.DOCTOR_TABLE + ", " + CONST.PLOT_TABLE + ", " + CONST.TIMETABLE_TABLE +
                        " FROM " + CONST.DOCTOR_TABLE + " INNER JOIN " + CONST.PLOT_TABLE + " INNER JOIN " + CONST.TIMETABLE_TABLE + " INNER JOIN " + CONST.TIMETABLETIME_TABLE + " INNER JOIN " + CONST.TIMETABLEDAY_TABLE + " INNER JOIN " + CONST.TIMEOFJOB_TABLE + " INNER JOIN " + CONST.RECEPTION_TABLE +
                        " WHERE " + CONST.DOCTOR_TABLE + "." + CONST.DOCTORS_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() +
                        " AND " + CONST.PLOT_TABLE + "." + CONST.PLOTS_DOCTORS_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() +
                        " AND " + CONST.TIMETABLE_TABLE + "." + CONST.TIMETABLE_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() +
                        " AND " + CONST.TIMETABLETIME_TABLE + "." + CONST.TIMETABLETIME_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() +
                        " AND " + CONST.TIMETABLEDAY_TABLE + "." + CONST.TIMETABLEDAY_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() +
                        " AND " + CONST.TIMEOFJOB_TABLE + "." + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() );
                deleteList.add( "DELETE FROM " + CONST.RECEPTION_TABLE + " WHERE " + CONST.RECEPTION_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() );

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
            deleteList.add("DELETE FROM " + CONST.DOCTOR_TABLE);
            deleteList.add("DELETE FROM " + CONST.PLOT_TABLE);
            deleteList.add("DELETE FROM " + CONST.TIMETABLE_TABLE);
            deleteList.add("DELETE FROM " + CONST.TIMETABLETIME_TABLE);
            deleteList.add("DELETE FROM " + CONST.TIMETABLEDAY_TABLE);
            deleteList.add("DELETE FROM " + CONST.TIMEOFJOB_TABLE);
        }

        jdbcConnection.executeDB(deleteList);
        fireTableRowsDeleted(table.convertRowIndexToModel(selectedRows[0]), table.convertRowIndexToModel(lastRow));
        fireTableDataChanged(); // Уведомление для класса, что нужно перерисовать таблицу
        return rowForDelete;
    }
}
