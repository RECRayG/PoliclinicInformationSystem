package registryApplication;

import Connector.JDBCConnection;
//import javafx.scene.layout.Border;
import net.miginfocom.swing.MigLayout;
import registryApplication.tableModels.receptionDoneToDoctorTableModel;
import registryApplication.tableModels.receptionToDoctorTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class complaintPatients extends JDialog
{
    private JPanel complaintPanel;
    private JScrollPane scrollComplaintPanel;
    private JTextArea complaint;
    private JScrollPane scrollComplaint;
    private JTextArea recomendation;
    private JScrollPane scrollRecomendation;
    private JButton buttonEnd;
    private JButton buttonCancel;
    private JLabel com;
    private JLabel rec;
    private int row;
    private JTable table;

    private JDBCConnection jdbcConnection = new JDBCConnection();

    public complaintPatients(JFrame parentFrame, int[] selectedRows, JTable table, JTable table2, JButton button, receptionToDoctorTableModel rtdtm, receptionDoneToDoctorTableModel rtdtm2, String doctorFIO, int tableNumber, String textComplain, String textRecomendation)
    {
        super(parentFrame, "История болезней", true);
        this.row = selectedRows[0];
        this.table = table;
        complaintPanel = new JPanel();
        complaintPanel.setBackground(new Color(255,255,255));
        complaintPanel.setLayout(new MigLayout());

        complaint = new JTextArea(textComplain);
        complaint.setBackground(new Color(100,100,100));
        recomendation = new JTextArea(textRecomendation);
        recomendation.setBackground(new Color(100,100,100));

        scrollComplaintPanel = new JScrollPane(complaintPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollComplaint = new JScrollPane(complaint, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollRecomendation = new JScrollPane(recomendation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        com = new JLabel("ЖАЛОБЫ");
        rec = new JLabel("РЕКОМЕНДАЦИИ");

        buttonEnd = new JButton("Завершить приём");
        buttonEnd.addActionListener(actionEvent -> {
            if(textComplain.equals("") || textRecomendation.equals(""))
            {
                jdbcConnection.writeComplaintsANDRecomendationInBD(complaint.getText().trim(), recomendation.getText().trim(), table.getValueAt(this.row, 2).toString(), doctorFIO, table.getValueAt(this.row, 0).toString(), table.getValueAt(this.row, 1).toString(), tableNumber);
                try {
                    rtdtm.addData(jdbcConnection, tableNumber);
                    rtdtm2.addData(jdbcConnection, tableNumber);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                table.clearSelection();
                button.setEnabled(false);
                rtdtm.fireTableDataChanged();
                rtdtm2.fireTableDataChanged();
            }
            else
            {
                jdbcConnection.rewriteComplaintsANDRecomendationInBD(complaint.getText().trim(), recomendation.getText().trim(), table.getValueAt(this.row, 2).toString(), doctorFIO, table.getValueAt(this.row, 0).toString(), table.getValueAt(this.row, 1).toString(), tableNumber);
                table.clearSelection();
                button.setEnabled(false);
            }

            this.dispose();
        });
        buttonCancel = new JButton("Отмена");
        buttonCancel.addActionListener(actionEvent -> {
            this.dispose();
        });

        complaintPanel.add(com, "gapleft 150");
        complaintPanel.add(rec, "gapleft 290, wrap");
        complaintPanel.add(scrollComplaint, "h 450!, w 370!, split 2, span 2 1");
        complaintPanel.add(scrollRecomendation, "h 450!, w 370!, wrap push");
        complaintPanel.add(buttonEnd, "split 2, span 2 1, al center");
        complaintPanel.add(buttonCancel);

        this.setLayout(new MigLayout());
        this.setBounds(0, 0, 800, 700); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // При нажатии на X ничего не произойдёт
        this.setFocusable(true); // Фокус на окне
        this.setResizable(true); // Запрет на изменение размеров окна - нет
        //this.setModal(true); // Модальное окно
        this.setLocationRelativeTo(null); // Расположение окна ровно по центру
        //this.setTitle("ОШИБКА!!!"); // Заголовок окна
        this.add(scrollComplaintPanel, "grow, push");
        this.setVisible(true); // Делаем окно видимым
    }
}
