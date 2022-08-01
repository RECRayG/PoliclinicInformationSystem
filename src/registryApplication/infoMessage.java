package registryApplication;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class infoMessage extends JDialog
{
    private JPanel errorPanel;
    private JScrollPane scrollErrorPanel;
    private JTextArea errorInfo;
    private JButton buttonOK;

    public infoMessage(JFrame parentFrame, String message, String header)
    {
        super(parentFrame, header, true);
        errorPanel = new JPanel();
        errorPanel.setBackground(new Color(255,255,255));
        errorPanel.setLayout(new MigLayout());

        scrollErrorPanel = new JScrollPane(errorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        errorInfo = new JTextArea(message);
        BufferedImage img = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        FontMetrics fm = g2d.getFontMetrics();
        errorInfo.setBounds(0,0, fm.stringWidth(message + "DDDDDDD"),80);

        buttonOK = new JButton("ОК");
        buttonOK.addActionListener(actionEvent -> {
            this.dispose();
        });

        errorPanel.add(errorInfo, "growx, pushx, al center, wrap push");
        errorPanel.add(buttonOK, "al center");

        this.setLayout(new MigLayout());
        this.setBounds(0, 0, errorInfo.getWidth(), errorInfo.getHeight() + 50); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // При нажатии на X ничего не произойдёт
        this.setFocusable(true); // Фокус на окне
        this.setResizable(false); // Запрет на изменение размеров окна - нет
        //this.setModal(true); // Модальное окно
        this.setLocationRelativeTo(null); // Расположение окна ровно по центру
        //this.setTitle("ОШИБКА!!!"); // Заголовок окна
        this.add(scrollErrorPanel, "grow, push");
        this.setVisible(true); // Делаем окно видимым
    }
}
