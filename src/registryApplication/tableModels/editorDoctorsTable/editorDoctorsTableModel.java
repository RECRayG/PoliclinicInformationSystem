package registryApplication.tableModels.editorDoctorsTable;

import Connector.CONST;
import Connector.JDBCConnection;
import registryApplication.infoMessage;
import registryApplication.tableModels.editorTimetableTable.JComboBoxCellEditor;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class editorDoctorsTableModel extends AbstractTableModel
{
    private int columnCount = 10;
    private ArrayList dataArrayList = new ArrayList(); // Список списка редактируемых элементов (при отмене вернуть значения отсюда)
    private ArrayList editDataArrayList = new ArrayList(); // Список табличных номеров, владельцам которых поменяли данные
    private ArrayList columnTypes = new ArrayList(); // Типы данных колонок
    private ArrayList columnNames = new ArrayList(); // имена колонок, как в БД
    private ListSelectionModel selModel;
    private boolean editable = true;
    private JTable table;
    private JFrame parentFrame;
    private JButton apply;
    private JDBCConnection jdbcConnection = new JDBCConnection();

    private JComboBox<String> male_female;
    private JComboBox<String> specialization;
    private ArrayList spec = new ArrayList();

    public editorDoctorsTableModel(JFrame parentFrame, JTable table, JButton apply, ArrayList columnTypes, ArrayList columnNames)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.columnTypes = columnTypes;
        this.columnNames = columnNames;
        this.table = table;
        this.parentFrame = parentFrame;
        this.apply = apply;

        this.table.setDefaultEditor(Date.class, new JDateChooserCellEditor()); // Для всех столбцов типа данных Date будет применяться этот редактор
        this.table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Esc"); // Для отмены изменения значений по нажатию на Esc
        this.table.getActionMap().put("Esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (table.isEditing())
                {
                    if (table.getCellEditor() != null) table.getCellEditor().cancelCellEditing();
                    table.changeSelection(table.getEditingRow(), table.getEditingColumn(), false, false);
                }
            }
        });
        male_female = new JComboBox(new String[] { "Мужской", "Женский"});
        male_female.setFocusable(false);
        male_female.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (table.isEditing())
                {
                    if (table.getCellEditor() != null) table.getCellEditor().cancelCellEditing();
                    table.changeSelection(table.getEditingRow(), table.getEditingColumn(), false, false);
                }
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                if (table.getCellEditor() != null)
                {
                    table.getCellEditor().cancelCellEditing();
                }
            }
        });
        spec.addAll(jdbcConnection.getAllSpecializationANDSpecializationCode());

        Vector vector = new Vector();
        for(int i = 0; i < spec.size(); i++)
        {
            vector.add( ((ArrayList)spec.get(i)).get(1) );
        }
        specialization = new JComboBox(vector);
        specialization.setFocusable(false);
        specialization.addPopupMenuListener(new PopupMenuListener() {
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
    public int getRowCount() // Возвращает кол-во строк в таблице
    {
        return dataArrayList.size();
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
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 1: if(isFakeString((String)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        ((ArrayList)dataArrayList.get(rowIndex)).set(1, aValue);
                        apply.setEnabled(true);
                        boolean be = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(i) ) // Если совпадают табличные номера
                                {
                                    be = true;
                                    break;
                                }
                            }
                            if(!be) // Если не было изменений для текущего доктора
                            {
                                editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                            }
                        }
                        else
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    break;
            case 2: if(isFakeString((String)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!"); }
                    else
                    {
                        ((ArrayList)dataArrayList.get(rowIndex)).set(2, aValue);
                        apply.setEnabled(true);
                        boolean be = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(i) ) // Если совпадают табличные номера
                                {
                                    be = true;
                                    break;
                                }
                            }
                            if(!be) // Если не было изменений для текущего доктора
                            {
                                editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                            }
                        }
                        else
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    break;
            case 3: if(isFakeString((String)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!"); }
                    else
                    {
                        ((ArrayList)dataArrayList.get(rowIndex)).set(3, aValue);
                        apply.setEnabled(true);
                        boolean be = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(i) ) // Если совпадают табличные номера
                                {
                                    be = true;
                                    break;
                                }
                            }
                            if(!be) // Если не было изменений для текущего доктора
                            {
                                editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                            }
                        }
                        else
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    break;
            case 4: ((ArrayList)dataArrayList.get(rowIndex)).set(4, aValue);
                    apply.setEnabled(true);
                    boolean be = false;
                    if(!editDataArrayList.isEmpty())
                    {
                        for(int i = 0; i < editDataArrayList.size(); i++)
                        {
                            if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(i) ) // Если совпадают табличные номера
                            {
                                be = true;
                                break;
                            }
                        }
                        if(!be) // Если не было изменений для текущего доктора
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    else
                    {
                        editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                    }
                    break;
            case 5: if(isFakeDate((java.sql.Date)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!"); }
                    else
                    {
                        ((ArrayList)dataArrayList.get(rowIndex)).set(5, aValue);
                        apply.setEnabled(true);
                        boolean bee = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(i) ) // Если совпадают табличные номера
                                {
                                    bee = true;
                                    break;
                                }
                            }
                            if(!bee) // Если не было изменений для текущего доктора
                            {
                                editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                            }
                        }
                        else
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    break;
            case 6: int i = 0; /////////////////////////////////////////////////////// Вставка значения в 7 ячейку, исходя из 6
                    ((ArrayList)dataArrayList.get(rowIndex)).set(6, aValue);
                    while(i < spec.size())
                    {
                        if(((ArrayList)spec.get(i)).get(1).equals(aValue)) break;
                        i++;
                    }
                    ((ArrayList)dataArrayList.get(rowIndex)).set(7, ((ArrayList)spec.get(i)).get(0));
                    apply.setEnabled(true);

                    boolean bee = false;
                    if(!editDataArrayList.isEmpty())
                    {
                        for(int j = 0; j < editDataArrayList.size(); j++)
                        {
                            if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(j) ) // Если совпадают табличные номера
                            {
                                bee = true;
                                break;
                            }
                        }
                        if(!bee) // Если не было изменений для текущего доктора
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    else
                    {
                        editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                    }

                    fireTableCellUpdated(rowIndex, 6);
                    fireTableCellUpdated(rowIndex, 7);
                    break;
            case 8: if(isFakeDate((java.sql.Date)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!"); }
                    else
                    {
                        ((ArrayList)dataArrayList.get(rowIndex)).set(8, aValue);
                        apply.setEnabled(true);
                        boolean bee2 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int ii = 0; ii < editDataArrayList.size(); ii++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(ii) ) // Если совпадают табличные номера
                                {
                                    bee2 = true;
                                    break;
                                }
                            }
                            if(!bee2) // Если не было изменений для текущего доктора
                            {
                                editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                            }
                        }
                        else
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    break;
            case 9: if(isFakePlot((Integer)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!! Всего участков: 37", "ОШИБКА!!!"); }
                    else
                    {
                        ((ArrayList)dataArrayList.get(rowIndex)).set(9, aValue);
                        apply.setEnabled(true);
                        boolean bee3 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int jj = 0; jj < editDataArrayList.size(); jj++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(0) == editDataArrayList.get(jj) ) // Если совпадают табличные номера
                                {
                                    bee3 = true;
                                    break;
                                }
                            }
                            if(!bee3) // Если не было изменений для текущего доктора
                            {
                                editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                            }
                        }
                        else
                        {
                            editDataArrayList.add( ((ArrayList)dataArrayList.get(rowIndex)).get(0) );
                        }
                    }
                    break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) // Можно ли редактировать данные в ячейке таблицы
    {
        switch(columnIndex) // ожно редактировать всё, кроме табличного номера
        {
            case 0: return false;
            case 7: return false;
            default: return editable;
        }
    }

    @Override
    public String getColumnName(int ColumnIndex)
    {
        switch(ColumnIndex)
        {
            case 0: return "Табличный номер";
            case 1: return "Фамилия";
            case 2: return "Имя";
            case 3: return "Отчество";
            case 4: return "Пол";
            case 5: return "Дата рождения";
            case 6: return "Специальность";
            case 7: return "Код специальности";
            case 8: return "Дата устройства на работу";
            case 9: return "Номер участка";
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
                    column.setCellEditor(new DefaultCellEditor(male_female));
                    break;
                case 5: column.setMinWidth(fm.stringWidth("Дата рождения" + "DDD"));
                    column.setMaxWidth(fm.stringWidth("Дата рождения" + "DDD"));
                    column.setCellRenderer(cellRenderer);
                    break;
                case 6: column.setMinWidth(fm.stringWidth("Специальность" + "DDD"));
                    column.setMaxWidth(305);
                    column.setCellRenderer(cellRenderer);
                    column.setCellEditor(new DefaultCellEditor(specialization));
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
                for(int j = 0; j < dataArrayList.size(); j++)
                {
                    if( ((ArrayList)dataArrayList.get(j)).get(0) == editDataArrayList.get(i) )
                    {
                        updateList.add("UPDATE " + CONST.DOCTOR_TABLE + " SET " + columnNames.get(1).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(1).toString() + "', " +
                                                                                  columnNames.get(2).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(2).toString() + "', " +
                                                                                  columnNames.get(3).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(3).toString() + "', " +
                                                                                  columnNames.get(4).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(4).toString() + "', " +
                                                                                  columnNames.get(5).toString() + " = DATE '" + ((ArrayList)dataArrayList.get(j)).get(5).toString() + "', " +
                                                                                  columnNames.get(7).toString() + " = " + ((ArrayList)dataArrayList.get(j)).get(7).toString() + ", " +
                                                                                  columnNames.get(8).toString() + " = DATE '" + ((ArrayList)dataArrayList.get(j)).get(8).toString() + "'" +
                                                                                  " WHERE " + columnNames.get(0).toString() + " = " + editDataArrayList.get(i).toString());
                        updateList.add("UPDATE " + CONST.PLOT_TABLE + " SET " + columnNames.get(9).toString() + " = " + ((ArrayList)dataArrayList.get(j)).get(9).toString() +
                                       " WHERE " + columnNames.get(0).toString() + " = " + editDataArrayList.get(i).toString());
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

    private boolean isFakeString(String aValue)
    {
        boolean isFakeString = false;

        if(aValue != null && !aValue.isEmpty())
        {
            for(char c : aValue.toCharArray())
            {
                if(isFakeString = Character.isDigit(c)) // Вернёт true, если символ - цифра
                {
                    break;
                }
            }
        }

        return isFakeString;
    }

    private boolean isFakePlot(int aValue)
    {
        if(aValue >= 1 && aValue <= 37) return false;
        else return true;
    }

    private boolean isFakeDate(java.sql.Date aValue)
    {
        return aValue.after(new Date());
    }
}
