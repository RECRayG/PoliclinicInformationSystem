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
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;

public class receptionTableModel extends AbstractTableModel
{
    private int columnCount = 9; // 9 - пришёл, не пришёл на приём
    private ArrayList dataArrayList = new ArrayList(); // Список списка БД
    private ArrayList columnTypes = new ArrayList(); // Типы данных солонок
    private ArrayList columnNames = new ArrayList(); // Имена колонок, как в БД
    private Vector<String> columnRUSNames = new Vector(); // Имена колонок на русском
    private ListSelectionModel selModel;
    private boolean editable = false;
    private JTable table;
    private JDBCConnection jdbcConnection = new JDBCConnection();

    public receptionTableModel(JTable table)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.table = table;

        columnRUSNames.add("Табличный номер");
        columnRUSNames.add("Специальность");
        columnRUSNames.add("Врач");
        columnRUSNames.add("Дата");
        columnRUSNames.add("Время");
        columnRUSNames.add("Номер карточки");
        columnRUSNames.add("Пациент");
        columnRUSNames.add("Участок");
        columnRUSNames.add("Посещение");
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) // Можно ли редактировать данные в ячейке таблицы
    {
        return editable;
    }

    @Override
    public String getColumnName(int ColumnIndex)
    {
        switch(ColumnIndex)
        {
            case 0: return columnRUSNames.get(0);
            case 1: return columnRUSNames.get(1);
            case 2: return columnRUSNames.get(2);
            case 3: return columnRUSNames.get(3);
            case 4: return columnRUSNames.get(4);
            case 5: return columnRUSNames.get(5);
            case 6: return columnRUSNames.get(6);
            case 7: return columnRUSNames.get(7);
            case 8: return columnRUSNames.get(8);
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
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(0) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 1: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(1) + "DDD"));
                    column.setMaxWidth(305);
                    column.setCellRenderer(cellRenderer);
                    break;
                case 2: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(2) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(2) + "DDDDDDDDDDDDDDDDDDDDDDDDDDDDD"));
                    column.setPreferredWidth(fm.stringWidth(columnRUSNames.get(2) + "DDDDDDDDDDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 3: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(3) + "DDDDDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(3) + "DDDDDD"));
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
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(6) + "DDDDDDDDDDDDDDDDDDDDDDDDDDDDD"));
                    column.setPreferredWidth(fm.stringWidth(columnRUSNames.get(6) + "DDDDDDDDDDDDDDDDDDD"));
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
        ResultSet resSet = jdbcConnection.getAllReception(st);

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

            row.add(resSet.getInt(CONST.RECEPTION_TABLE_NUMBER));
            row.add(resSet.getString(CONST.SPECIALIZATION));
            row.add(resSet.getString("CONCAT(doc." + CONST.DOCTORS_SECONDNAME + ", ' ', doc." + CONST.DOCTORS_FIRSTNAME + ", ' ', doc." + CONST.DOCTORS_THIRDNAME + ")"));
            row.add(resSet.getDate(CONST.RECEPTION_DATE_OF_RECEPTION));
            row.add(resSet.getString(CONST.RECEPTION_TIME_OF_RECEPTION));
            row.add(resSet.getString(CONST.RECEPTION_NUMBER_OF_CARD));
            row.add(resSet.getString("CONCAT(p." + CONST.PATIENT_SECONDNAME + ", ' ', p." + CONST.PATIENT_FIRSTNAME + ", ' ', p." + CONST.PATIENT_THIRDNAME + ")"));
            row.add(resSet.getInt(CONST.PLOTS_NUMBER));
            row.add(resSet.getBoolean(CONST.RECEPTION_IS_VISIT));

            dataArrayList.add(row); // обавление новой строки в таблицу на каждой итерации
        }

        jdbcConnection.dbDisconnectionResSet(resSet);
        jdbcConnection.dbDisconnectionSt(st);
    }

    public void writeInBD(ArrayList doctors, String doctorsFIO, String patientsNumberOfCardANDFIO, String date, String time)
    {
        int tableNumber = 1;
        String numberOfCard = "";
        for(int i = 0; i < doctors.size(); i++)
        {
            String docFIO = ((ArrayList)doctors.get(i)).get(1).toString() + " " + ((ArrayList)doctors.get(i)).get(2).toString() + " " + ((ArrayList)doctors.get(i)).get(3).toString();
            if( docFIO.equals(doctorsFIO) )
            {
                tableNumber = (Integer)((ArrayList)doctors.get(i)).get(0);
                break;
            }
        }

        String[] edit = new Scanner(patientsNumberOfCardANDFIO).nextLine().split(", ");
        numberOfCard = edit[0];

        LocalDate dateBD = LocalDate.of(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10)));

        jdbcConnection.writeReception(tableNumber, numberOfCard, Date.valueOf(dateBD), time);
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
                if( table.getValueAt(selectedRows[i], 0) == ((ArrayList)dataArrayList.get(j)).get(0) &&
                    !Date.valueOf( table.getValueAt(selectedRows[i], 3).toString() ).before(Date.valueOf( ((ArrayList)dataArrayList.get(j)).get(3).toString() )) &&
                    !Date.valueOf( table.getValueAt(selectedRows[i], 3).toString() ).after(Date.valueOf( ((ArrayList)dataArrayList.get(j)).get(3).toString() )) &&
                    table.getValueAt(selectedRows[i], 4).equals( ((ArrayList)dataArrayList.get(j)).get(4) ) )
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
                deleteList.add( "DELETE FROM " + CONST.RECEPTION_TABLE +
                        " WHERE " + CONST.RECEPTION_TABLE_NUMBER + " = " + ((ArrayList)rowForDelete.get(i)).get(0).toString() +
                        " AND " + CONST.RECEPTION_DATE_OF_RECEPTION + " = DATE '" + ((ArrayList)rowForDelete.get(i)).get(3).toString() + "'" +
                        " AND " + CONST.RECEPTION_TIME_OF_RECEPTION + " = '" + ((ArrayList)rowForDelete.get(i)).get(4).toString() + "'" );

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
            deleteList.add("DELETE FROM " + CONST.RECEPTION_TABLE);
        }

        jdbcConnection.executeDB(deleteList);
        fireTableRowsDeleted(table.convertRowIndexToModel(selectedRows[0]), table.convertRowIndexToModel(lastRow));
        fireTableDataChanged(); // Уведомление для класса, что нужно перерисовать таблицу
        return rowForDelete;
    }
}
