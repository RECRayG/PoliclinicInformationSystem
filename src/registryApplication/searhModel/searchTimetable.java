package registryApplication.searhModel;

import net.miginfocom.swing.MigLayout;
import registryApplication.infoMessage;
import registryApplication.tableModels.timetableTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Vector;

public class searchTimetable extends JDialog
{
    private JPanel searchPanel;
    private JScrollPane scrollSearchPanel;
    private JLabel textLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel choiceLabel;
    private JComboBox columnNames;

    public searchTimetable(JFrame parentFrame, JButton throwSearchButton, JButton searchMainButton, TableRowSorter<AbstractTableModel> rowSorter, timetableTableModel ttm)
    {
        super(parentFrame, "Поиск", true);

        Vector data = new Vector();
        data.addAll(ttm.getColumnRUSNames());
        data.add("Все столбцы");
        columnNames = new JComboBox(data);
        columnNames.setFocusable(false);
        columnNames.setSelectedIndex(9);
        searchPanel = new JPanel();
        searchPanel.setBackground(new Color(255,255,255));
        searchPanel.setLayout(new MigLayout());

        scrollSearchPanel = new JScrollPane(searchPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        searchTextField = new JTextField();
        searchTextField.setBounds(0,0, 350,100);

        textLabel = new JLabel("Введите значение: ");
        choiceLabel = new JLabel("Выберите столбец для поиска: ");

        searchButton = new JButton("Найти");
        searchButton.addActionListener(actionEvent -> {
            this.dispose();
            throwSearchButton.setEnabled(true);
            searchMainButton.setEnabled(false);

            if(searchTextField.getText().trim().equals(""))
            {
                new infoMessage(parentFrame, "Вы пытаетесь найти пустую строку!!!", "ПРЕДУПРЕЖДЕНИЕ!");
                searchMainButton.setEnabled(true);
                throwSearchButton.setEnabled(false);
            }
            else
            {
                switch (columnNames.getSelectedIndex())
                {
                    case 0:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 0).regexFilter("(?iu)" + searchTextField.getText().trim(), 0)); // "(?iu)" - игнор регистра
                        break;
                    case 1:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 1).regexFilter("(?iu)" + searchTextField.getText().trim(), 1)); // "(?iu)" - игнор регистра
                        break;
                    case 2:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 2).regexFilter("(?iu)" + searchTextField.getText().trim(), 2)); // "(?iu)" - игнор регистра
                        break;
                    case 3:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 3).regexFilter("(?iu)" + searchTextField.getText().trim(), 3)); // "(?iu)" - игнор регистра
                        break;
                    case 4:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 4).regexFilter("(?iu)" + searchTextField.getText().trim(), 4)); // "(?iu)" - игнор регистра
                        break;
                    case 5:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 5).regexFilter("(?iu)" + searchTextField.getText().trim(), 5)); // "(?iu)" - игнор регистра
                        break;
                    case 6:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 6).regexFilter("(?iu)" + searchTextField.getText().trim(), 6)); // "(?iu)" - игнор регистра
                        break;
                    case 7:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 7).regexFilter("(?iu)" + searchTextField.getText().trim(), 7)); // "(?iu)" - игнор регистра
                        break;
                    case 8:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 8).regexFilter("(?iu)" + searchTextField.getText().trim(), 8)); // "(?iu)" - игнор регистра
                        break;
                    case 9:
                        rowSorter.setRowFilter(new myRowFilter(searchTextField.getText().trim(), 9).regexFilter("(?iu)" + searchTextField.getText().trim())); // "(?iu)" - игнор регистра
                        break;
                }
            }
        });

        searchPanel.add(choiceLabel, "split 2");
        searchPanel.add(columnNames, "al center, wrap 15");
        searchPanel.add(textLabel, "split 2");
        searchPanel.add(searchTextField, "growx, pushx, al center, wrap push");
        searchPanel.add(searchButton, "span 2 1, al center");

        this.setLayout(new MigLayout());
        this.setBounds(0, 0, searchTextField.getWidth() + 80, searchTextField.getHeight() + columnNames.getHeight() + 70); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // При нажатии на X ничего не произойдёт
        this.setFocusable(true); // Фокус на окне
        this.setResizable(false); // Запрет на изменение размеров окна - нет
        this.setLocationRelativeTo(null); // Расположение окна ровно по центру
        this.add(scrollSearchPanel, "grow, push");
        this.setVisible(true); // Делаем окно видимым
    }
}
