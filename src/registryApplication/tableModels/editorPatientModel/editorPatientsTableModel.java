package registryApplication.tableModels.editorPatientModel;

import Connector.CONST;
import Connector.JDBCConnection;
import registryApplication.infoMessage;
import registryApplication.tableModels.editorDoctorsTable.JDateChooserCellEditor;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class editorPatientsTableModel extends AbstractTableModel
{
    private int columnCount = 14;
    private ArrayList dataArrayList = new ArrayList(); // Список списка редактируемых элементов (при отмене вернуть значения отсюда)
    private ArrayList editDataArrayList = new ArrayList(); // Список табличных номеров, владельцам которых поменяли данные
    private ArrayList oldNumbersOfCard = new ArrayList(); // Для update в БД
    private ArrayList columnTypes; // Типы данных колонок
    private ArrayList columnNames; // имена колонок, как в БД
    private Vector<String> columnRUSNames;
    private ListSelectionModel selModel;
    private boolean editable = true;
    private JTable table;
    private JFrame parentFrame;
    private JButton apply;
    private JDBCConnection jdbcConnection = new JDBCConnection();
    private boolean first = true;

    private JComboBox<String> male_female;
    private ArrayList streetsList;
    private JComboBox<String> streets;

    public editorPatientsTableModel(JFrame parentFrame, JTable table, JButton apply, ArrayList columnTypes, ArrayList columnNames, Vector<String> columnRUSName)
    {
        selModel = table.getSelectionModel(); // Слушатель выделения
        this.columnTypes = columnTypes;
        this.columnNames = columnNames;
        this.table = table;
        this.parentFrame = parentFrame;
        this.apply = apply;
        this.columnRUSNames = columnRUSName;

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

        streetsList = new ArrayList(jdbcConnection.getStreetsAndPlots());
        streets = new JComboBox(jdbcConnection.getAllStrees());
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
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return ((ArrayList)dataArrayList.get(rowIndex)).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: if(isFakeString((String)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(0, aValue);
                        apply.setEnabled(true);
                        boolean be = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be = true;
                                    oldNumbersOfCard.remove( oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!be) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                    break;
            case 1: if(isFakeString((String)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(1, aValue);
                        apply.setEnabled(true);
                        boolean be = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be = true;
                                    oldNumbersOfCard.remove( oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!be) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                        break;
            case 2: if(isFakeString((String)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(2, aValue);
                        apply.setEnabled(true);
                        boolean be = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!be) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                    break;
            case 3: oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                    ((ArrayList)dataArrayList.get(rowIndex)).set(3, aValue);
                    apply.setEnabled(true);
                    boolean be = false;
                    if(!editDataArrayList.isEmpty())
                    {
                        for(int i = 0; i < editDataArrayList.size(); i++)
                        {
                            if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                            {
                                be = true;
                                oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                break;
                            }
                        }
                        if(!be) // Если не было изменений для текущего доктора
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                    else
                    {
                        ArrayList temp = new ArrayList();
                        temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        editDataArrayList.add( temp );
                    }
                    break;
            case 4: if(isFakeDate((java.sql.Date)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!"); }
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        String idPatient = ((ArrayList)dataArrayList.get(rowIndex)).get(7).toString().replace( ((ArrayList)dataArrayList.get(rowIndex)).get(4).toString().substring(0,4) +
                                                                                                                        ((ArrayList)dataArrayList.get(rowIndex)).get(4).toString().substring(5,7) +
                                                                                                                            ((ArrayList)dataArrayList.get(rowIndex)).get(4).toString().substring(8,10), "");
                        apply.setEnabled(true);

                        boolean bee = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    bee = true;
                                    ((ArrayList)editDataArrayList.get(i)).set(0, idPatient + aValue.toString().substring(0,4) + aValue.toString().substring(5,7) + aValue.toString().substring(8,10));
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1);
                                    break;
                                }
                            }
                            if(!bee) // Если не было изменений для текущего доктора
                            {
                                ((ArrayList)dataArrayList.get(rowIndex)).set(4, aValue);
                                ((ArrayList)dataArrayList.get(rowIndex)).set(7, idPatient + aValue.toString().substring(0,4) + aValue.toString().substring(5,7) + aValue.toString().substring(8,10));
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                                break;
                            }
                        }
                        else
                        {
                            ((ArrayList)dataArrayList.get(rowIndex)).set(4, aValue);
                            ((ArrayList)dataArrayList.get(rowIndex)).set(7, idPatient + aValue.toString().substring(0,4) + aValue.toString().substring(5,7) + aValue.toString().substring(8,10));
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                            break;
                        }

                        ((ArrayList)dataArrayList.get(rowIndex)).set(4, aValue);
                        ((ArrayList)dataArrayList.get(rowIndex)).set(7, idPatient + aValue.toString().substring(0,4) + aValue.toString().substring(5,7) + aValue.toString().substring(8,10));
                    }
                    fireTableCellUpdated(rowIndex, 7);
                        break;
            case 6: if(isFakeDate((java.sql.Date)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!"); }
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(6, aValue);
                        apply.setEnabled(true);
                        boolean bee = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    bee = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!bee) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                break;
            case 8: if(isFakeInteger2((String) aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(8, aValue);
                        apply.setEnabled(true);
                        boolean be2 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be2 = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1);
                                    break;
                                }
                            }
                            if(!be2) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                break;
            case 9: if( !aValue.toString().substring(0,2).equals("+7") ||
                        !aValue.toString().substring(2,3).equals("(") || !aValue.toString().substring(6,7).equals(")") ||
                        !aValue.toString().substring(10,11).equals("-") || !aValue.toString().substring(13,14).equals("-") ) { new infoMessage(parentFrame, "Неверный формат!!! Нужно: +7(...)...-..-..", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(9, aValue);
                        apply.setEnabled(true);
                        boolean be2 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be2 = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1);
                                    break;
                                }
                            }
                            if(!be2) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                break;
            case 10: if(isFakeInteger2((String) aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(10, aValue);
                        apply.setEnabled(true);
                        boolean be2 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int i = 0; i < editDataArrayList.size(); i++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be2 = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!be2) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                break;
            case 11: oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                    int i = 0; /////////////////////////////////////////////////////// Вставка значения в 7 ячейку, исходя из 6
                    ((ArrayList)dataArrayList.get(rowIndex)).set(11, aValue);
                    while(i < streetsList.size())
                    {
                        if(((ArrayList)streetsList.get(i)).get(1).equals(aValue)) break;
                        i++;
                    }
                    ((ArrayList)dataArrayList.get(rowIndex)).set(5, ((ArrayList)streetsList.get(i)).get(0));
                    apply.setEnabled(true);

                    boolean bee = false;
                    if(!editDataArrayList.isEmpty())
                    {
                        for(int j = 0; j < editDataArrayList.size(); j++)
                        {
                            if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(j)).get(0)) ) // Если совпадают табличные номера
                            {
                                bee = true;
                                oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                break;
                            }
                        }
                        if(!bee) // Если не было изменений для текущего доктора
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                    else
                    {
                        ArrayList temp = new ArrayList();
                        temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        editDataArrayList.add( temp );
                    }

                    fireTableCellUpdated(rowIndex, 5);
                    fireTableCellUpdated(rowIndex, 11);
                break;
            case 12: if(isFakeInteger((Integer)aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(12, aValue);
                        apply.setEnabled(true);
                        boolean be2 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int j = 0; j < editDataArrayList.size(); j++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(j)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be2 = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!be2) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                break;
            case 13: if(isFakeInteger((Integer) aValue)) { new infoMessage(parentFrame, "Введены некорректные данные!!!", "ОШИБКА!!!");}
                    else
                    {
                        oldNumbersOfCard.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                        ((ArrayList)dataArrayList.get(rowIndex)).set(13, aValue);
                        apply.setEnabled(true);
                        boolean be2 = false;
                        if(!editDataArrayList.isEmpty())
                        {
                            for(int j = 0; j < editDataArrayList.size(); j++)
                            {
                                if( ((ArrayList)dataArrayList.get(rowIndex)).get(7).equals(((ArrayList)editDataArrayList.get(j)).get(0)) ) // Если совпадают табличные номера
                                {
                                    be2 = true;
                                    oldNumbersOfCard.remove(oldNumbersOfCard.size() - 1 );
                                    break;
                                }
                            }
                            if(!be2) // Если не было изменений для текущего доктора
                            {
                                ArrayList temp = new ArrayList();
                                temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                                editDataArrayList.add( temp );
                            }
                        }
                        else
                        {
                            ArrayList temp = new ArrayList();
                            temp.add( ((ArrayList)dataArrayList.get(rowIndex)).get(7) );
                            editDataArrayList.add( temp );
                        }
                    }
                break;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)  // Какие данные отображать в колонке
    {
        return (Class)columnTypes.get(columnIndex);
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
                    column.setCellEditor(new DefaultCellEditor(male_female));
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
                    column.setCellEditor(new DefaultCellEditor(streets));
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) // Можно ли редактировать данные в ячейке таблицы
    {
        switch(columnIndex) // ожно редактировать всё, кроме табличного номера
        {
            case 5: return false;
            case 7: return false;
            default: return editable;
        }
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
            case 9: return columnRUSNames.get(9);
            case 10: return columnRUSNames.get(10);
            case 11: return columnRUSNames.get(11);
            case 12: return columnRUSNames.get(12);
            case 13: return columnRUSNames.get(13);
        }
        return "";
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
                    if( ((ArrayList)dataArrayList.get(j)).get(7).equals(((ArrayList)editDataArrayList.get(i)).get(0)) )
                    {
                        updateList.add( "UPDATE " + CONST.PATIENT_TABLE + " SET " + columnNames.get(0).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(0).toString() + "', " +
                                columnNames.get(1).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(1).toString() + "', " +
                                columnNames.get(2).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(2).toString() + "', " +
                                columnNames.get(3).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(3).toString() + "', " +
                                columnNames.get(4).toString() + " = DATE '" + ((ArrayList)dataArrayList.get(j)).get(4).toString() + "', " +
                                columnNames.get(5).toString() + " = " + ((ArrayList)dataArrayList.get(j)).get(5).toString() + ", " +
                                columnNames.get(6).toString() + " = DATE '" + ((ArrayList)dataArrayList.get(j)).get(6).toString() + "', " +
                                columnNames.get(7).toString() + " = " + ((ArrayList)dataArrayList.get(j)).get(7).toString() + ", " +
                                columnNames.get(8).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(8).toString() + "', " +
                                columnNames.get(9).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(9).toString() + "', " +
                                columnNames.get(10).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(10).toString() + "', " +
                                columnNames.get(11).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(11).toString() + "', " +
                                columnNames.get(12).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(12).toString() + "', " +
                                columnNames.get(13).toString() + " = '" + ((ArrayList)dataArrayList.get(j)).get(13).toString() + "'" +
                                " WHERE " + columnNames.get(7).toString() + " = " + oldNumbersOfCard.get(i).toString() );
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
    private boolean isFakeDate(java.sql.Date aValue)
    {
        return aValue.after(new Date());
    }
    private boolean isFakeInteger(Integer aValue)
    {
        try {
            Integer.parseInt(aValue.toString());
            return false;
        } catch(NumberFormatException e)
        {
            return true;
        }
    }
    private boolean isFakeInteger2(String aValue)
    {
        try {
            Integer.parseInt(aValue);
            return false;
        } catch(NumberFormatException e)
        {
            return true;
        }
    }
}
