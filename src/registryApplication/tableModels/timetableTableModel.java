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
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class timetableTableModel extends AbstractTableModel implements Serializable
{
    private int columnCount = 9;
    private boolean newDay = false;
    private ArrayList dataArrayList = new ArrayList(); // Список списка БД
    private ArrayList columnTypes = new ArrayList(); // Типы данных солонок
    private ArrayList columnNames = new ArrayList(); // Имена колонок, как в БД
    private ArrayList doctors; // Список врачей
    //private ArrayList timeWeek = new ArrayList(); // Время на каждый день недели
    private Vector<String> columnRUSNames = new Vector(); // Имена колонок на русском
    private ArrayList<weekGetter> currentState = new ArrayList(); // Состояние недели
    private int currentStateID;
    private boolean editable = false;
    private weekGetter wG_NOW = new weekGetter(0);
    private weekGetter wG_NEXT_WEEK = new weekGetter(7);
    private weekGetter wG_NEXT_NEXT_WEEK = new weekGetter(14);
    private JTable table;
    private int dataDoctorsCount;
    private boolean correctWeeks = false;

    public timetableTableModel(JTable table, ArrayList doctors)
    {
        this.table = table;
        this.dataDoctorsCount = doctors.size();
        this.doctors = doctors;

        currentStateID = 0; // 0 - текущая неделя, 1 - следующая неделя, 2 - позаследующая неделя (по умолчанию - 0)
        currentState.add(wG_NOW);
        currentState.add(wG_NEXT_WEEK);
        currentState.add(wG_NEXT_NEXT_WEEK);

        columnRUSNames.add("Врач");
        columnRUSNames.add("Специальность");
        columnRUSNames.add("Кабинет");
        columnRUSNames.add(currentState.get(currentStateID).getPn());
        columnRUSNames.add(currentState.get(currentStateID).getVt());
        columnRUSNames.add(currentState.get(currentStateID).getSr());
        columnRUSNames.add(currentState.get(currentStateID).getCht());
        columnRUSNames.add(currentState.get(currentStateID).getPt());
        columnRUSNames.add(currentState.get(currentStateID).getSb());
    }

    public weekGetter getWeeks(int id)
    {
        if(id > -1 && id < 3) return this.currentState.get(id);
        else return this.currentState.get(0);
    }

    public ArrayList<weekGetter> getCurrentState()
    {
        return this.currentState;
    }

    public ArrayList getDataArrayList()
    {
        return this.dataArrayList;
    }

    public int getCurrentStateID()
    {
        return this.currentStateID;
    }

    public void setCurrentStateID(int state)
    {
        if(state > -1 && state < 3)
        {
            this.currentStateID = state;

            columnRUSNames.setElementAt(currentState.get(currentStateID).getPn(), 3);
            columnRUSNames.setElementAt(currentState.get(currentStateID).getVt(), 4);
            columnRUSNames.setElementAt(currentState.get(currentStateID).getSr(),5 );
            columnRUSNames.setElementAt(currentState.get(currentStateID).getCht(), 6);
            columnRUSNames.setElementAt(currentState.get(currentStateID).getPt(), 7);
            columnRUSNames.setElementAt(currentState.get(currentStateID).getSb(), 8);

            fireTableStructureChanged();
        }
        else return;
    }

    @Override
    public void fireTableStructureChanged()
    {
        super.fireTableStructureChanged();
        setColumnStaticWidth(table);
    }

    @Override
    public int getRowCount()
    {
        return dataArrayList.size();
    }

    public ArrayList getColumnTypes()
    {
        return this.columnTypes;
    }

    @Override
    public int getColumnCount()
    {
        return columnCount;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)  // Какие данные отображать в колонке
    {
        return (Class)columnTypes.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 3: return ((ArrayList)dataArrayList.get(rowIndex)).get(3 + currentStateID).toString().substring(14,25); // Взятие элемента по индексу
            case 4: return ((ArrayList)dataArrayList.get(rowIndex)).get(6 + currentStateID).toString().substring(10,21); // Взятие элемента по индексу
            case 5: return ((ArrayList)dataArrayList.get(rowIndex)).get(9 + currentStateID).toString().substring(8,19); // Взятие элемента по индексу
            case 6: return ((ArrayList)dataArrayList.get(rowIndex)).get(12 + currentStateID).toString().substring(10,21); // Взятие элемента по индексу
            case 7: return ((ArrayList)dataArrayList.get(rowIndex)).get(15 + currentStateID).toString().substring(10,21); // Взятие элемента по индексу
            case 8: return ((ArrayList)dataArrayList.get(rowIndex)).get(18 + currentStateID).toString().substring(10,21); // Взятие элемента по индексу
            default: return ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex); // Взятие элемента по индексу
        }
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

    public void setColumnStaticWidth(JTable tableOfTimetable)
    {
        TableColumnModel tcm = tableOfTimetable.getColumnModel();
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
                case 0: column.setMinWidth(fm.stringWidth(columnRUSNames.get(0) + "DDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(0) + "DDDDDDDDDDDDDDDDDDDDDDDDDDDDD"));
                    column.setPreferredWidth(fm.stringWidth(columnRUSNames.get(0) + "DDDDDDDDDDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 1: column.setMinWidth(fm.stringWidth(columnRUSNames.get(1) + "DDD"));
                    column.setMaxWidth(305);
                    column.setCellRenderer(cellRenderer);
                    break;
                case 2: column.setMinWidth(fm.stringWidth(columnRUSNames.get(2) + "DDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(2) + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 3: column.setMinWidth(fm.stringWidth(columnRUSNames.get(3) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(3) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 4: column.setMinWidth(fm.stringWidth(columnRUSNames.get(4) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(4) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 5: column.setMinWidth(fm.stringWidth(columnRUSNames.get(5) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(5) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 6: column.setMinWidth(fm.stringWidth(columnRUSNames.get(6) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(6) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 7: column.setMinWidth(fm.stringWidth(columnRUSNames.get(7) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(7) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 8: column.setMinWidth(fm.stringWidth(columnRUSNames.get(8) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(8) + "DDDDDDDDDDD"));
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

    public void addData(JDBCConnection jdbcConnection) throws SQLException // Вставка данных в таблицу
    {
        if(!dataArrayList.isEmpty()) dataArrayList.clear();
        if(!columnTypes.isEmpty()) columnTypes.clear();
        if(!columnNames.isEmpty()) columnNames.clear();

        correctWeeks = false;

        //addDate(jdbcConnection, wG_NOW, wG_NEXT_WEEK, wG_NEXT_NEXT_WEEK);

        Statement st = null;
        Statement st2 = null;
        ResultSet resSet2 = null;
        try
        {
            st = jdbcConnection.getDbConnecion().createStatement();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        ResultSet resSet = jdbcConnection.getTimetableForReferenceTable(st);

        int doctorsIdCheck = 3;
        int doctorCount = 0;
        ArrayList temp2 = new ArrayList();
        while(resSet.next())
        {
            if (doctorsIdCheck == 3)
            {
                doctorsIdCheck = 0;
                if (!temp2.isEmpty())
                {
                    doctorCount++;
                    //temp2.add(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
                    dataArrayList.add(temp2);
                    temp2 = new ArrayList();
                }
                temp2.add(resSet.getString("CONCAT(doc." + CONST.DOCTORS_SECONDNAME + ", ' ' , doc." + CONST.DOCTORS_FIRSTNAME + ", ' ', doc." + CONST.DOCTORS_THIRDNAME + ")"));
                temp2.add(resSet.getString(CONST.SPECIALIZATION)); // Специальность 1
                temp2.add(resSet.getInt(CONST.CABINETS_NUMBER)); // Кабинет 2
            }


            switch (resSet.getString(CONST.DAY_DAY))
            {
                case CONST.DAY_MONDAY:
                    temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                    break;
                case CONST.DAY_TUESDAY:
                    temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                    break;
                case CONST.DAY_WEDNESDAY:
                    temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                    break;
                case CONST.DAY_THURSDAY:
                    temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                    break;
                case CONST.DAY_FRIDAY:
                    temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                    break;
                case CONST.DAY_SATURDAY:
                    temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                    doctorsIdCheck++;
                    if (doctorsIdCheck == 3) {
                        temp2.add(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
                    }
                    if ((doctorCount == dataDoctorsCount - 1) && (doctorsIdCheck == 3)) {
                        dataArrayList.add(temp2);
                        break;
                    }
                    break;
            }
        }

        try
        {
            ResultSetMetaData rsmd = resSet.getMetaData();
            for(int i = 0; i < columnCount; i++)
            {
                switch(i)
                {
                    case 3: columnNames.add( "MONDAY" );
                        break;
                    case 4: columnNames.add( "TUESDAY" );
                        break;
                    case 5: columnNames.add( "WEDNESDAY" );
                        break;
                    case 6: columnNames.add( "THURSDAY" );
                        break;
                    case 7: columnNames.add( "FRIDAY" );
                        break;
                    case 8: columnNames.add( "SATURDAY" );
                        break;
                    default: columnNames.add(rsmd.getColumnName(i + 1));
                }
                columnTypes.add(Class.forName( ((ArrayList) dataArrayList.get(0)).get(i).getClass().getName()) );
            }
        } catch (ClassNotFoundException e) { e.printStackTrace(); }

        jdbcConnection.dbDisconnectionResSet(resSet);
        jdbcConnection.dbDisconnectionSt(st);
        jdbcConnection.dbDisconnectionResSet(resSet2);
        jdbcConnection.dbDisconnectionSt(st2);
    }

    public void updateData(JDBCConnection jdbcConnection) throws SQLException
    {
        this.dataDoctorsCount = doctors.size();  // Врачей после
        Statement st = null;
        Statement st2 = null;
        ResultSet resSet2 = null;
        try
        {
            st = jdbcConnection.getDbConnecion().createStatement();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        ResultSet resSet = jdbcConnection.getTimetableForReferenceTable(st);

        int doctorsIdCheckUpdate = 3;
        boolean be = false;
        int doctorCountUpdate = dataArrayList.size(); // Врачей до
        int i = 0;
        ArrayList temp2 = new ArrayList();
        while(resSet.next())
        {
            String val = resSet.getString("CONCAT(doc." + CONST.DOCTORS_SECONDNAME +
                    ", ' ' , doc." + CONST.DOCTORS_FIRSTNAME + ", ' ', doc." +
                    CONST.DOCTORS_THIRDNAME + ")");
            if( val.equals( ((ArrayList)doctors.get(i)).get(1).toString() + " " +
                    ((ArrayList)doctors.get(i)).get(2).toString() + " " +
                    ((ArrayList)doctors.get(i)).get(3).toString()) && !be) { continue; }
            if(!be) i++;
            if( (i < dataArrayList.size()) && !be ) { continue; }
            else if( ((i+1) >= dataArrayList.size()) ) // Если не было ни одного совпадения и цикл на последней итерации
            {
                be = true;
                if (doctorsIdCheckUpdate == 3)
                {
                    doctorsIdCheckUpdate = 0;
                    if (!temp2.isEmpty())
                    {
                        doctorCountUpdate++;
                        temp2.add(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
                        temp2.add(resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        dataArrayList.add(temp2);
                        temp2 = new ArrayList();
                    }
                    temp2.add(resSet.getString("CONCAT(doc." + CONST.DOCTORS_SECONDNAME + ", ' ' , doc." + CONST.DOCTORS_FIRSTNAME + ", ' ', doc." + CONST.DOCTORS_THIRDNAME + ")"));
                    temp2.add(resSet.getString(CONST.SPECIALIZATION)); // Специальность 1
                    temp2.add(resSet.getInt(CONST.CABINETS_NUMBER)); // Кабинет 2
                }

                switch (resSet.getString(CONST.DAY_DAY)) {
                    case CONST.DAY_MONDAY:
                        temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        break;
                    case CONST.DAY_TUESDAY:
                        temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        break;
                    case CONST.DAY_WEDNESDAY:
                        temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        break;
                    case CONST.DAY_THURSDAY:
                        temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        break;
                    case CONST.DAY_FRIDAY:
                        temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        break;
                    case CONST.DAY_SATURDAY:
                        temp2.add(resSet.getString(CONST.DAY_DAY) + " ( " + resSet.getString(5) + "/" + resSet.getString(6) + " )" + "( " + resSet.getDate(7).toString() + " ) " + resSet.getInt(CONST.TIMEOFJOB_ID_WEEK));
                        doctorsIdCheckUpdate++;
                        if (doctorsIdCheckUpdate == 3) {
                            temp2.add(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
                        }
                        if ((doctorCountUpdate == dataDoctorsCount - 1) && (doctorsIdCheckUpdate == 3)) {
                            dataArrayList.add(temp2);
                            break;
                        }
                        break;
                }
            }
        }

        fireTableDataChanged();

        jdbcConnection.dbDisconnectionResSet(resSet);
        jdbcConnection.dbDisconnectionSt(st);
        jdbcConnection.dbDisconnectionResSet(resSet2);
        jdbcConnection.dbDisconnectionSt(st2);
    }

    public void deleteData(ArrayList deleteDoctors)
    {
        for(int i = 0; i < deleteDoctors.size(); i++)
        {
            for(int j = 0; j < this.dataArrayList.size(); j++)
            {
                if (((ArrayList) dataArrayList.get(j)).get(0).equals(((ArrayList) deleteDoctors.get(i)).get(1).toString() + " " +
                        ((ArrayList) deleteDoctors.get(i)).get(2).toString() + " " +
                        ((ArrayList) deleteDoctors.get(i)).get(3).toString()))
                {
                    this.dataArrayList.remove(j);
                    break;
                }
            }
        }

        fireTableRowsUpdated(0, dataArrayList.size());
        fireTableDataChanged(); // Уведомление для класса, что нужно перерисовать таблицу
    }

    public void updateDate(JDBCConnection jdbcConnection)
    {
        String lineConfig = "";

        try
        {
            FileInputStream fis = new FileInputStream("D:\\IntelliJ's projects\\KursachNaEazyBoy\\MySQL_DataBase\\currentDay.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            int bytess;
            char bytess2;
            while ((bytess = isr.read()) != -1)
            {
                bytess2 = (char) bytess;
                lineConfig += bytess2;
            }

            LocalDate day = LocalDate.of(Integer.parseInt(lineConfig.substring(0, 4)), Integer.parseInt(lineConfig.substring(5, 7)), Integer.parseInt(lineConfig.substring(8, 10)));

            weekGetter tempWG = new weekGetter(0); // Текущая неделя

            if( (day.getDayOfWeek().getValue() == LocalDate.now().getDayOfWeek().getValue()) &&
                (day.getMonthValue() == LocalDate.now().getMonthValue()) &&
                (day.getDayOfMonth() == LocalDate.now().getDayOfMonth())) //lineConfig.equals(new SimpleDateFormat("yyyy-MM-dd").format(Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.of("Pacific/Auckland")).toInstant()))))
            {
                this.newDay = false; // Если полное совпадение по дате
            }
            else if(lineConfig.equals("")) // Если пустой файл
            {
                this.newDay = false;

                String newLineConfig = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.of("Pacific/Auckland")).toInstant()));

                FileOutputStream fos = new FileOutputStream("D:\\IntelliJ's projects\\KursachNaEazyBoy\\MySQL_DataBase\\currentDay.txt", false);
                fos.write(newLineConfig.getBytes());
                fos.close();
            }
            else if( day.isBefore(tempWG.getWeeksDays().get(0)) ) // Если предыдущая дата идёт до текущего понедельника
            {
                this.newDay = true;

                String newLineConfig = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.of("Pacific/Auckland")).toInstant()));

                FileOutputStream fos = new FileOutputStream("D:\\IntelliJ's projects\\KursachNaEazyBoy\\MySQL_DataBase\\currentDay.txt", false);
                fos.write(newLineConfig.getBytes());
                fos.close();
            }
            else // Во всех остальных случаях (когда дата стоит раньше текущей)
            {
                this.newDay = false;

                String newLineConfig = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.of("Pacific/Auckland")).toInstant()));

                FileOutputStream fos = new FileOutputStream("D:\\IntelliJ's projects\\KursachNaEazyBoy\\MySQL_DataBase\\currentDay.txt", false);
                fos.write(newLineConfig.getBytes());
                fos.close();
            }

            fis.close();
            isr.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); } catch (UnsupportedEncodingException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
        catch (NumberFormatException e) // Если в файле неаписана гадость
        {
            this.newDay = false;
            String newLineConfig = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.of("Pacific/Auckland")).toInstant()));

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("D:\\IntelliJ's projects\\KursachNaEazyBoy\\MySQL_DataBase\\currentDay.txt", false);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                fos.write(newLineConfig.getBytes());
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if(this.newDay)
        {
            ArrayList<String> updateDate = new ArrayList();

                for (int i = 0; i < this.wG_NEXT_NEXT_WEEK.getOffset() + this.wG_NEXT_WEEK.getOffset(); i++) // 3 недели нужно заполнить с текущей даты (14 + 7 = 21 день (дата))
                {
                    updateDate.add("UPDATE " + CONST.WEEK_TABLE + " SET " + CONST.WEEK_DATE + " = DATE(DATE_ADD(NOW(), INTERVAL " +
                            String.valueOf(i) + " DAY)), " + CONST.WEEK_ID_DAY + " = DAYOFWEEK(DATE(" + CONST.WEEK_DATE + ")) - 1 " +
                            "WHERE " + CONST.WEEK_ID + " = " + String.valueOf(i + 1));
                }

                jdbcConnection.executeDB(updateDate);
        }
    }

    public void updateDateWeek(JDBCConnection jdbcConnection)
    {
        if(newDay)
        {
//                ArrayList<String> updateDate2 = new ArrayList();
//                for (int i = 0; i < doctors.size(); i++) // 3 недели нужно заполнить с текущей даты (14 + 7 = 21 день (дата))
//                {
//                    int tableNumber = (Integer) ((ArrayList) doctors.get(i)).get(0);
//                    for (int j = this.wG_NEXT_NEXT_WEEK.getOffset() + this.wG_NEXT_WEEK.getOffset() - 1; j > 0; j--) {
//                        updateDate2.add("UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = '" + ((ArrayList)((ArrayList)timeWeek.get(i)).get(j)).get(0) + "', " + CONST.TIMEOFJOB_ID_TIME_END + " = '" + ((ArrayList)((ArrayList)timeWeek.get(i)).get(j)).get(1) + "'" +
//                                " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + tableNumber +
//                                " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + String.valueOf(j));
//                    }
//                    updateDate2.add("UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = 22, " + CONST.TIMEOFJOB_ID_TIME_END + " =  22" +
//                            " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + tableNumber +
//                            " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + String.valueOf(this.wG_NEXT_NEXT_WEEK.getOffset() + this.wG_NEXT_WEEK.getOffset()));
//                }

            jdbcConnection.rewriteDaysInWeeks();
            try { addData(jdbcConnection); }
            catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
