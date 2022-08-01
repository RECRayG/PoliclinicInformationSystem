package registryApplication.tableModels.editorTimetableTable;

import Connector.CONST;
import Connector.JDBCConnection;
//import javafx.scene.control.Cell;
import registryApplication.infoMessage;
import registryApplication.tableModels.editorDoctorsTable.JDateChooserCellEditor;
import registryApplication.tableModels.weekGetter;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class editorTimetableTableModel extends AbstractTableModel
{
    private int columnCount = 9;
    private ArrayList dataArrayList = new ArrayList(); // Список списка редактируемых элементов (при отмене вернуть значения отсюда)
    private ArrayList editDataArrayList = new ArrayList(); // Список табличных номеров, владельцам которых поменяли данные
    private ArrayList columnTypes; // Типы данных колонок
    private ArrayList originalData;
    private Vector<String> columnRUSNames;
    private ArrayList time = new ArrayList();
    private ListSelectionModel selModel;
    private boolean editable = true;
    private JTable table;
    private JFrame parentFrame;
    private JButton apply;
    private JDBCConnection jdbcConnection = new JDBCConnection();
    private JComboBoxCellEditor editor = new JComboBoxCellEditor(table, this);
    private int currentStateID;

    private JComboBox<Integer> cabChooser;

    public editorTimetableTableModel(JFrame parentFrame, JTable table, JButton apply, ArrayList columnTypes, ArrayList columnNames, Vector<String> columnRUSNames, ArrayList originalData)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.columnTypes = columnTypes;
        this.originalData = originalData;
        this.columnRUSNames = columnRUSNames;
        this.table = table;
        this.parentFrame = parentFrame;
        this.apply = apply;
        this.currentStateID = 0;

        this.table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Esc"); // Для отмены изменения значений по нажатию на Esc
        this.table.getActionMap().put("Esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (table.isEditing())
                {
                    if (table.getCellEditor() != null)
                    {
                        if(editor.getPanel().getHierarchyListeners().length != 0)
                        {
                            editor.getPanel().removeHierarchyListener(editor.getPanel().getHierarchyListeners()[0]);
                            table.setRowHeight(editor.getEditingRow(), 15);
                        }
                        table.getCellEditor().cancelCellEditing();
                    }
                    table.changeSelection(table.getEditingRow(), table.getEditingColumn(), false, false);
                }
            }
        });

        time.addAll(jdbcConnection.getAllTime());
        cabChooser = new JComboBox(jdbcConnection.getCabinetsNumbers());
        cabChooser.setFocusable(false);
        cabChooser.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                if (table.isEditing())
                {
                    if (table.getCellEditor() != null) table.getCellEditor().cancelCellEditing();
                    table.changeSelection(table.getEditingRow(), table.getEditingColumn(), false, false);
                }
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                if (table.getCellEditor() != null)
                {
                    table.getCellEditor().cancelCellEditing();
                }
            }
        });
    }

    @Override
    public int getRowCount()
    {
        return dataArrayList.size();
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
        return ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex); // Взятие элемента по индексу
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        apply.setEnabled(true);

        ((ArrayList)dataArrayList.get(rowIndex)).set(columnIndex, aValue);

        if(!editDataArrayList.isEmpty())
        {
            boolean bee = false;
            for(int i = 0; i < editDataArrayList.size(); i++)
            {
                if( ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex) == ((ArrayList)editDataArrayList.get(i)).get(1) ) // Если совпадают табличные номера
                {
                    bee = true;
                    break;
                }

                if( (Integer.parseInt( ((ArrayList)editDataArrayList.get(i)).get(2).toString() ) == rowIndex)
                        && Integer.parseInt( ((ArrayList)editDataArrayList.get(i)).get(3).toString() ) == columnIndex )
                {
                    editDataArrayList.remove(i);
                    bee = false;
                    break;
                }
            }
            if(!bee) // Если не было изменений для текущего доктора
            {
                ArrayList temp = new ArrayList();
                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex) );
                temp.add( rowIndex );
                temp.add( columnIndex );

                editDataArrayList.add(temp);
            }
        }
        else
        {
            ArrayList temp = new ArrayList();
            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex) );
            temp.add( rowIndex );
            temp.add( columnIndex );

            editDataArrayList.add(temp);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) // Можно ли редактировать данные в ячейке таблицы
    {
        switch(columnIndex) // ожно редактировать всё, кроме табличного номера
        {
            case 0: return false;
            case 1: return false;
            default: return editable;
        }
    }

    public void setEditable(boolean editableSet)
    {
        this.editable = editableSet;
    }

    public void setColumnRUSNames(Vector<String> newNames)
    {
        this.columnRUSNames = newNames;
        fireTableStructureChanged();
    }

    @Override
    public void fireTableStructureChanged()
    {
        super.fireTableStructureChanged();
        setColumnStaticWidth(table);
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
                    column.setCellEditor(new DefaultCellEditor(cabChooser));
                    break;
                case 3: column.setMinWidth(fm.stringWidth(columnRUSNames.get(3) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(3) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(editor);
                    break;
                case 4: column.setMinWidth(fm.stringWidth(columnRUSNames.get(4) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(4) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(editor);
                    break;
                case 5: column.setMinWidth(fm.stringWidth(columnRUSNames.get(5) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(5) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(editor);
                    break;
                case 6: column.setMinWidth(fm.stringWidth(columnRUSNames.get(6) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(6) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(editor);
                    break;
                case 7: column.setMinWidth(fm.stringWidth(columnRUSNames.get(7) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(7) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(editor);
                    break;
                case 8: column.setMinWidth(fm.stringWidth(columnRUSNames.get(8) + "DDDDDDDDDDD"));
                    column.setMaxWidth(fm.stringWidth(columnRUSNames.get(8) + "DDDDDDDDDDD"));
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(editor);
                    break;
            }
            i++;
        }
    }

    public void addData(int[] selectedRows, JTable table) // Вставка данных в таблицу
    {
        if(!dataArrayList.isEmpty()) dataArrayList.clear();
        if(!editDataArrayList.isEmpty()) editDataArrayList.clear();

        for(int i = 0; i < selectedRows.length; i++)
        {
            ArrayList row = new ArrayList();
            int selectedIndex = selectedRows[i];
            for(int j = 0; j < columnCount; j++)
            {
                row.add(table.getValueAt(selectedIndex, j));
            }
            dataArrayList.add(row);
        }
        fireTableDataChanged(); // Уведомление для класса, что нужно перерисовать таблицу
    }

    public void editData()
    {
        if(!editDataArrayList.isEmpty()) // Если список табличных номеров для изменений не пуст
        {
            ArrayList<String> updateList = new ArrayList();
            JDBCConnection jdbcConnection = new JDBCConnection();
            for(int i = 0; i < editDataArrayList.size(); i++)
            {
                for(int j = 0; j < originalData.size(); j++)
                {
                    if( ((ArrayList)originalData.get(j)).get(0) == ((ArrayList)editDataArrayList.get(i)).get(0) )
                    {
                        switch( Integer.parseInt(  ((ArrayList)editDataArrayList.get(i)).get(3).toString()  ))
                        {
/* Кабинет */               case 2:
                                updateList.add("UPDATE " + CONST.TIMETABLE_TABLE + " SET " + CONST.TIMETABLE_NUMBER_CABINET + " = " + ((ArrayList)editDataArrayList.get(i)).get(1).toString() +
                                        " WHERE " + CONST.TIMETABLE_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString());
                                break;
/* Понедельник */           case 3:
                                String[] edit3 = new Scanner( ((ArrayList)originalData.get(j)).get(3 + this.currentStateID).toString() ).nextLine().split(" ");
                                int beg3 = 23;
                                int end3 = 23;
                                for(int t3 = 0; t3 < time.size(); t3++)
                                {
                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(0,5).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        beg3 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }

                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(6,11).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        end3 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }
                                }
                                updateList.add( "UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = " + beg3 + ", " +
                                        CONST.TIMEOFJOB_ID_TIME_END + " = " + end3 +
                                        " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString() +
                                        " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + edit3[edit3.length - 1] );
                                break;
/* Вторник */               case 4:
                                String[] edit4 = new Scanner( ((ArrayList)originalData.get(j)).get(6 + this.currentStateID).toString() ).nextLine().split(" ");
                                int beg4 = 23;
                                int end4 = 23;
                                for(int t3 = 0; t3 < time.size(); t3++)
                                {
                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(0,5).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        beg4 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }

                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(6,11).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        end4 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }
                                }
                                updateList.add( "UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = " + beg4 + ", " +
                                        CONST.TIMEOFJOB_ID_TIME_END + " = " + end4 +
                                        " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString() +
                                        " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + edit4[edit4.length - 1] );
                                break;
/* Среда */                 case 5:
                                String[] edit5 = new Scanner( ((ArrayList)originalData.get(j)).get(9 + this.currentStateID).toString() ).nextLine().split(" ");
                                int beg5 = 23;
                                int end5 = 23;
                                for(int t3 = 0; t3 < time.size(); t3++)
                                {
                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(0,5).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        beg5 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }

                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(6,11).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        end5 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }
                                }
                                updateList.add( "UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = " + beg5 + ", " +
                                        CONST.TIMEOFJOB_ID_TIME_END + " = " + end5 +
                                        " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString() +
                                        " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + edit5[edit5.length - 1] );
                                break;
/* Четверг */               case 6:
                                String[] edit6 = new Scanner( ((ArrayList)originalData.get(j)).get(12 + this.currentStateID).toString() ).nextLine().split(" ");
                                int beg6 = 23;
                                int end6 = 23;
                                for(int t3 = 0; t3 < time.size(); t3++)
                                {
                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(0,5).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        beg6 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }

                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(6,11).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        end6 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }
                                }
                                updateList.add( "UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = " + beg6 + ", " +
                                        CONST.TIMEOFJOB_ID_TIME_END + " = " + end6 +
                                        " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString() +
                                        " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + edit6[edit6.length - 1] );
                                break;
/* Пятница */               case 7:
                                String[] edit7 = new Scanner( ((ArrayList)originalData.get(j)).get(15 + this.currentStateID).toString() ).nextLine().split(" ");
                                int beg7 = 23;
                                int end7 = 23;
                                for(int t3 = 0; t3 < time.size(); t3++)
                                {
                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(0,5).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        beg7 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }

                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(6,11).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        end7 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }
                                }
                                updateList.add( "UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = " + beg7 + ", " +
                                        CONST.TIMEOFJOB_ID_TIME_END + " = " + end7 +
                                        " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString() +
                                        " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " +  edit7[edit7.length - 1] );
                                break;
/* Суббота */               case 8:
                                String[] edit8 = new Scanner( ((ArrayList)originalData.get(j)).get(18 + this.currentStateID).toString() ).nextLine().split(" ");
                                int beg8 = 23;
                                int end8 = 23;
                                for(int t3 = 0; t3 < time.size(); t3++)
                                {
                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(0,5).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        beg8 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }

                                    if( ((ArrayList)editDataArrayList.get(i)).get(1).toString().substring(6,11).equals( ((ArrayList)time.get(t3)).get(1) ) )
                                    {
                                        end8 = Integer.parseInt( ((ArrayList)time.get(t3)).get(0).toString() );
                                    }
                                }
                                updateList.add( "UPDATE " + CONST.TIMEOFJOB_TABLE + " SET " + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = " + beg8 + ", " +
                                        CONST.TIMEOFJOB_ID_TIME_END + " = " + end8 +
                                        " WHERE " + CONST.TIMEOFJOB_TABLE_NUMBER + " = " + ((ArrayList)originalData.get(j)).get(21).toString() +
                                        " AND " + CONST.TIMEOFJOB_ID_WEEK + " = " + edit8[edit8.length - 1] );
                                break;
                        }
                        break;
                    }
                }
            }

            jdbcConnection.executeDB(updateList);
        }
        else
        {
            new infoMessage(parentFrame, "Нечего обновлять!!!", "ОШИБКА!!!");
        }
    }

    public JPanel getPanel()
    {
        return editor.getPanel();
    }

    public int getEditingRow()
    {
        return editor.getEditingRow();
    }

    public int getEditingColumn()
    {
        return editor.getEditingColumn();
    }

    public ArrayList getTime()
    {
        return this.time;
    }

    public void setCurrentStateID(int id)
    {
        this.currentStateID = id;
    }
}
