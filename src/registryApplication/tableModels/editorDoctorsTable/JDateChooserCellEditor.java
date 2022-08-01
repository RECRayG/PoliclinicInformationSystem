package registryApplication.tableModels.editorDoctorsTable;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class JDateChooserCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private JDateChooser dateChooser;

    public JDateChooserCellEditor()
    {
        dateChooser = new JDateChooser();
        //dateChooser.setFocusable(false);
        dateChooser.setDateFormatString("yyyy-MM-dd");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) // Настройка и возврат компонента, который нужен для редактирования ячейки
    {
        return dateChooser; // Возвращает компонент для редактирования ячейки
    }

    @Override
    public Object getCellEditorValue() // Возвращает текущее значение JDateChooser
    {
        return new java.sql.Date(dateChooser.getDate().getTime());
    }
}
