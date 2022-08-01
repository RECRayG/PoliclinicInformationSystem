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

public class receptionToDoctorTableModel extends AbstractTableModel
{
    private int columnCount = 6; // 6 - пришёл, не пришёл на приём
    private ArrayList dataArrayList = new ArrayList(); // Список списка БД
    private ArrayList columnTypes = new ArrayList(); // Типы данных солонок
    private ArrayList columnNames = new ArrayList(); // Имена колонок, как в БД
    private Vector<String> columnRUSNames = new Vector(); // Имена колонок на русском
    private ListSelectionModel selModel;
    private boolean editable = false;
    private JTable table;
    private JDBCConnection jdbcConnection = new JDBCConnection();

    public receptionToDoctorTableModel(JTable table)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.table = table;

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
                case 0: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(0) + "DDDDDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(0) + "DDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 1: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(1) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(1) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 2: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(2) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(2) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 3: column.setMinWidth(fm.stringWidth(this.columnRUSNames.get(3) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(this.columnRUSNames.get(3) + "DDDDDDDDDDDDDDDDDDDDDDDDDDDDD"));
                    column.setPreferredWidth(fm.stringWidth(columnRUSNames.get(3) + "DDDDDDDDDDDDDDDDDDD"));
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

    public void addData(JDBCConnection jdbcConnection, int tableNumber) throws SQLException // Вставка данных в таблицу
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
        ResultSet resSet = jdbcConnection.getAllReceptionToDoctor(st, tableNumber);

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
}
