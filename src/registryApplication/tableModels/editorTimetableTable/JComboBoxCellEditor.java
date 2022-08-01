package registryApplication.tableModels.editorTimetableTable;

import Connector.JDBCConnection;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor, PopupMenuListener
{
    private JPanel panel;
    private JComboBox timeChooserBegin;
    private JComboBox timeChooserEnd;
    private JTable table;
    private editorTimetableTableModel tableModel;
    private int editingRow;
    private int editingColumn;

    public JComboBoxCellEditor(JTable table, editorTimetableTableModel tableModel)
    {
        this.table = table;
        this.tableModel = tableModel;
        JDBCConnection jdbcConnection = new JDBCConnection();

        panel = new JPanel();
        panel.setLayout(new MigLayout());

        timeChooserBegin = new JComboBox(jdbcConnection.getTime());
        timeChooserBegin.setFocusable(false);

        timeChooserEnd = new JComboBox();
        for(int i = 0; i < timeChooserBegin.getItemCount(); i++)
        {
            timeChooserEnd.addItem(timeChooserBegin.getItemAt(i));
        }
        timeChooserEnd.setFocusable(false);

        panel.add(timeChooserBegin, "split 2, gapleft 5");
        panel.add(timeChooserEnd);
    }

    public JPanel getPanel()
    {
        return this.panel;
    }

    public int getEditingRow()
    {
        return this.editingRow;
    }

    public int getEditingColumn()
    {
        return this.editingColumn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        //Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
        //table.setRowHeight(row, panel.getHeight());//comp.getPreferredSize().height);
        this.editingRow = row;
        this.editingColumn = column;
        //panel.setBounds(0,0,table.getColumnModel().getTotalColumnWidth(),40);

        panel.addHierarchyListener(new HierarchyListener()
        {
            @Override
            public void hierarchyChanged(HierarchyEvent e)
            {
                if(table.isEditing())
                {
                    table.setRowHeight(row, 15);
                }
                else
                    {
                        table.setRowHeight(row, 40);
                    }
            }
        });
        return panel;
    }

    @Override
    public Object getCellEditorValue()
    {
        return timeChooserBegin.getSelectedItem().toString() + "/" + timeChooserEnd.getSelectedItem().toString();
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
    {
        if (table.isEditing())
        {
            if (table.getCellEditor() != null) table.getCellEditor().cancelCellEditing();
            table.changeSelection(table.getEditingRow(), table.getEditingColumn(), false, false);
            timeChooserBegin.setFocusable(false);
            timeChooserEnd.setFocusable(false);
            panel.setFocusable(true);
        }
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
