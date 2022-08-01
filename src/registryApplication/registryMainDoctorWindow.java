package registryApplication;

import Connector.JDBCConnection;
import Users.Admin;
import Users.Doctor;
import net.miginfocom.swing.MigLayout;
import registryApplication.tableModels.doctorsTableModel;
import registryApplication.tableModels.receptionDoneToDoctorTableModel;
import registryApplication.tableModels.receptionToDoctorTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class registryMainDoctorWindow extends JFrame
{
    private JDBCConnection jdbcConnection = new JDBCConnection();
    private Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); // Создаём переменную, в которой хранится размер экрана

    private JPanel mainDoctorWindowPanel = new JPanel(); // Основная панель
    private JScrollPane scrollMainDoctorWindowPanel = new JScrollPane(mainDoctorWindowPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel doctorTableLeftPanel = new JPanel(); // Диалоговая панель

    private JPanel doctorTableRightPanel = new JPanel(); // Рабочая панель

////////////////////////Диалоговая панель(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private JPanel startRegistryMainDoctorWindow = new JPanel();
    private JLabel startDoctorLabel = new JLabel("Врач: ");
    private JLabel startDoctorLabelText = new JLabel();
    private JLabel startDoctorTableNumberLabel = new JLabel("Табличный номер: ");
    private JLabel startDoctorTableNumberLabelText = new JLabel();
    private JLabel startDoctorSpecializationLabel = new JLabel("Специальность: ");
    private JLabel startDoctorSpecializationLabelText = new JLabel();

    private JButton startDoctorReceptionButton = new JButton("Приёмная");
    private JButton startDoctorPatientButton = new JButton("Пациенты");

    private JPanel receptionDoctorWindow = new JPanel();
    private JLabel receptionStartDoctorLabel = new JLabel("Врач: ");
    private JLabel receptionStartDoctorLabelText = new JLabel();
    private JLabel receptionStartDoctorTableNumberLabel = new JLabel("Табличный номер: ");
    private JLabel receptionStartDoctorTableNumberLabelText = new JLabel();
    private JLabel receptionStartDoctorSpecializationLabel = new JLabel("Специальность: ");
    private JLabel receptionStartDoctorSpecializationLabelText = new JLabel();
    private JButton receptionBackToReceptionButton = new JButton("< Назад");

    private JPanel receptionDoctorWindow2 = new JPanel();
    private JLabel receptionStartDoctorLabel2 = new JLabel("Врач: ");
    private JLabel receptionStartDoctorLabelText2 = new JLabel();
    private JLabel receptionStartDoctorTableNumberLabel2 = new JLabel("Табличный номер: ");
    private JLabel receptionStartDoctorTableNumberLabelText2 = new JLabel();
    private JLabel receptionStartDoctorSpecializationLabel2 = new JLabel("Специальность: ");
    private JLabel receptionStartDoctorSpecializationLabelText2 = new JLabel();
    private JButton receptionBackToReceptionButton2 = new JButton("< Назад");
////////////////////////Диалоговая панель(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////Рабочая панель(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private JPanel voidPanel = new JPanel();

    private JPanel propertiesAndListOfReceptionPanel = new JPanel();
    private JScrollPane scrollPropertyAndListOfReceptionPanel = new JScrollPane(propertiesAndListOfReceptionPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel listOfReceptionPanel = new JPanel();
    private JTable tableOfReceptionToDoctor = new JTable();
    private receptionToDoctorTableModel rtdtm = new receptionToDoctorTableModel(tableOfReceptionToDoctor);
    private TableRowSorter<AbstractTableModel> rowSorter = new TableRowSorter(rtdtm);
    private JScrollPane scrollTableOfReceptionToDoctor = new JScrollPane(tableOfReceptionToDoctor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel propertiesListOfReceptionPanel = new JPanel();
    private JButton protectListOfReceptionButton = new JButton("Принять");


    private JPanel propertiesAndListOfReceptionPanel2 = new JPanel();
    private JScrollPane scrollPropertyAndListOfReceptionPanel2 = new JScrollPane(propertiesAndListOfReceptionPanel2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel listOfReceptionPanel2 = new JPanel();
    private JTable tableOfReceptionToDoctor2 = new JTable();
    private receptionDoneToDoctorTableModel rtdtm2 = new receptionDoneToDoctorTableModel(tableOfReceptionToDoctor2);
    private TableRowSorter<AbstractTableModel> rowSorter2 = new TableRowSorter(rtdtm2);
    private JScrollPane scrollTableOfReceptionToDoctor2 = new JScrollPane(tableOfReceptionToDoctor2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel propertiesListOfReceptionPanel2 = new JPanel();
    private JButton editListOfReceptionButton2 = new JButton("Редактировать");
////////////////////////Рабочая панель(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Doctor doctor;

    public registryMainDoctorWindow(Doctor doctor) // Конструктор класса без параметров
    {
        this.doctor = doctor;
        voidPanel.setBackground(new Color(100,100,100));
/////////////////Рабочая панель(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        startRegistryMainDoctorWindow.setBackground(new Color(255,255,255));
        startRegistryMainDoctorWindow.setLayout(new MigLayout());
        startDoctorLabelText.setText(doctor.getSecondName() + " " + doctor.getFirstName() + " " + doctor.getThirdName());
        startDoctorTableNumberLabelText.setText(String.valueOf(doctor.getTableNumber()));
        startDoctorSpecializationLabelText.setText(doctor.getSpecialization());

        startRegistryMainDoctorWindow.add(startDoctorLabel, "wrap 5, al left");
        startRegistryMainDoctorWindow.add(startDoctorLabelText, "wrap 10, al left");
        startRegistryMainDoctorWindow.add(startDoctorTableNumberLabel, "split 2, al left");
        startRegistryMainDoctorWindow.add(startDoctorTableNumberLabelText, "wrap 10");
        startRegistryMainDoctorWindow.add(startDoctorSpecializationLabel, "wrap 5, al left");
        startRegistryMainDoctorWindow.add(startDoctorSpecializationLabelText, "wrap 50, al left");
        startRegistryMainDoctorWindow.add(startDoctorReceptionButton, "wrap 15, al center");
        startRegistryMainDoctorWindow.add(startDoctorPatientButton, "al center");


        receptionDoctorWindow.setBackground(new Color(255,255,255));
        receptionDoctorWindow.setLayout(new MigLayout());
        receptionStartDoctorLabelText.setText(doctor.getSecondName() + " " + doctor.getFirstName() + " " + doctor.getThirdName());
        receptionStartDoctorTableNumberLabelText.setText(String.valueOf(doctor.getTableNumber()));
        receptionStartDoctorSpecializationLabelText.setText(doctor.getSpecialization());

        receptionDoctorWindow.add(receptionStartDoctorLabel, "wrap 5, al left");
        receptionDoctorWindow.add(receptionStartDoctorLabelText, "wrap 10, al left");
        receptionDoctorWindow.add(receptionStartDoctorTableNumberLabel, "split 2, al left");
        receptionDoctorWindow.add(receptionStartDoctorTableNumberLabelText, "wrap 10");
        receptionDoctorWindow.add(receptionStartDoctorSpecializationLabel, "wrap 5, al left");
        receptionDoctorWindow.add(receptionStartDoctorSpecializationLabelText, "wrap 10, al left");
        receptionDoctorWindow.add(receptionBackToReceptionButton, "al left");



        receptionDoctorWindow2.setBackground(new Color(255,255,255));
        receptionDoctorWindow2.setLayout(new MigLayout());
        receptionStartDoctorLabelText2.setText(doctor.getSecondName() + " " + doctor.getFirstName() + " " + doctor.getThirdName());
        receptionStartDoctorTableNumberLabelText2.setText(String.valueOf(doctor.getTableNumber()));
        receptionStartDoctorSpecializationLabelText2.setText(doctor.getSpecialization());

        receptionDoctorWindow2.add(receptionStartDoctorLabel2, "wrap 5, al left");
        receptionDoctorWindow2.add(receptionStartDoctorLabelText2, "wrap 10, al left");
        receptionDoctorWindow2.add(receptionStartDoctorTableNumberLabel2, "split 2, al left");
        receptionDoctorWindow2.add(receptionStartDoctorTableNumberLabelText2, "wrap 10");
        receptionDoctorWindow2.add(receptionStartDoctorSpecializationLabel2, "wrap 5, al left");
        receptionDoctorWindow2.add(receptionStartDoctorSpecializationLabelText2, "wrap 10, al left");
        receptionDoctorWindow2.add(receptionBackToReceptionButton2, "al left");
/////////////////Рабочая панель(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

        propertiesAndListOfReceptionPanel.setBackground(new Color(255,255,255));
        propertiesAndListOfReceptionPanel.setLayout(new MigLayout());

        listOfReceptionPanel.setBackground(new Color(255,255,255));
        listOfReceptionPanel.setLayout(new MigLayout());

        propertiesListOfReceptionPanel.setBackground(new Color(100,100,100));
        propertiesListOfReceptionPanel.setLayout(new MigLayout());

        propertiesAndListOfReceptionPanel.add(listOfReceptionPanel, "pushx, growx, h 600!, wrap");
        propertiesAndListOfReceptionPanel.add(propertiesListOfReceptionPanel, "growx, pushx, h 60!");

        protectListOfReceptionButton.setEnabled(false);

        propertiesListOfReceptionPanel.add(protectListOfReceptionButton, "gaptop 10, al center");

        tableOfReceptionToDoctor.setModel(rtdtm);
        tableOfReceptionToDoctor.setRowSorter(rowSorter); // Для сортировки по столбцам
        tableOfReceptionToDoctor.setColumnSelectionAllowed(false);
        tableOfReceptionToDoctor.getTableHeader().setReorderingAllowed(false);
        try { rtdtm.addData(jdbcConnection, doctor.getTableNumber()); } // Добавление в модель таблицы данных из БД
        catch (SQLException e) { e.printStackTrace(); }
        rtdtm.setColumnStaticWidth(tableOfReceptionToDoctor); // Установка ширины полей
        tableOfReceptionToDoctor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Для добавления полосы прокрутки влево-вправо

        listOfReceptionPanel.add(scrollTableOfReceptionToDoctor, "push, grow, al center");


        propertiesAndListOfReceptionPanel2.setBackground(new Color(255,255,255));
        propertiesAndListOfReceptionPanel2.setLayout(new MigLayout());

        listOfReceptionPanel2.setBackground(new Color(255,255,255));
        listOfReceptionPanel2.setLayout(new MigLayout());

        propertiesListOfReceptionPanel2.setBackground(new Color(100,100,100));
        propertiesListOfReceptionPanel2.setLayout(new MigLayout());

        propertiesAndListOfReceptionPanel2.add(listOfReceptionPanel2, "pushx, growx, h 600!, wrap");
        propertiesAndListOfReceptionPanel2.add(propertiesListOfReceptionPanel2, "growx, pushx, h 60!");

        editListOfReceptionButton2.setEnabled(false);

        propertiesListOfReceptionPanel2.add(editListOfReceptionButton2, "gaptop 10, al center");

        tableOfReceptionToDoctor2.setModel(rtdtm2);
        tableOfReceptionToDoctor2.setRowSorter(rowSorter2); // Для сортировки по столбцам
        tableOfReceptionToDoctor2.setColumnSelectionAllowed(false);
        tableOfReceptionToDoctor2.getTableHeader().setReorderingAllowed(false);
        try { rtdtm2.addData(jdbcConnection, doctor.getTableNumber()); } // Добавление в модель таблицы данных из БД
        catch (SQLException e) { e.printStackTrace(); }
        rtdtm2.setColumnStaticWidth(tableOfReceptionToDoctor2); // Установка ширины полей
        tableOfReceptionToDoctor2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Для добавления полосы прокрутки влево-вправо

        listOfReceptionPanel2.add(scrollTableOfReceptionToDoctor2, "push, grow, al center");

/////////////////registryMainWindowPanels(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        doctorTableRightPanel.setBackground(new Color(255,255,0));
        doctorTableRightPanel.setSize(200, (int) screenSize.getHeight());
        doctorTableLeftPanel.setBackground(new Color(255,0,0));
        doctorTableLeftPanel.setSize(1166, (int) screenSize.getHeight());

        mainDoctorWindowPanel.setLayout(new MigLayout());
        mainDoctorWindowPanel.add(doctorTableLeftPanel, "w 250!, pushy, growy");
        mainDoctorWindowPanel.add(doctorTableRightPanel, "pushx, pushy, growx, growy");
/////////////////registryMainWindowPanels(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

        addRegistryMainWindow(scrollMainDoctorWindowPanel, (int)(screenSize.getWidth()), (int)(screenSize.getHeight()));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        CardLayout cl = new CardLayout();
        doctorTableLeftPanel.setLayout(cl);
        doctorTableLeftPanel.add(startRegistryMainDoctorWindow, "startPage");
        doctorTableLeftPanel.add(receptionDoctorWindow, "receptionBack");
        doctorTableLeftPanel.add(receptionDoctorWindow2, "receptionBack2");
        cl.show(doctorTableLeftPanel, "startPage");

        CardLayout cl2 = new CardLayout();
        doctorTableRightPanel.setLayout(cl2);
        doctorTableRightPanel.add(scrollPropertyAndListOfReceptionPanel, "listOfReception");
        doctorTableRightPanel.add(scrollPropertyAndListOfReceptionPanel2, "listOfReception2");
        doctorTableRightPanel.add(voidPanel, "voidPanel");
        cl2.show(doctorTableRightPanel, "voidPanel");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////ActionListeners(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        startDoctorPatientButton.addActionListener(actionEvent -> {
            cl.show(doctorTableLeftPanel, "receptionBack2");
            cl2.show(doctorTableRightPanel, "listOfReception2");
        });
        startDoctorReceptionButton.addActionListener(actionEvent -> {
            cl.show(doctorTableLeftPanel, "receptionBack");
            cl2.show(doctorTableRightPanel, "listOfReception");
        });
        receptionBackToReceptionButton.addActionListener(actionEvent -> {
            cl.show(doctorTableLeftPanel, "startPage");
            cl2.show(doctorTableRightPanel, "voidPanel");
            tableOfReceptionToDoctor.clearSelection();
            protectListOfReceptionButton.setEnabled(false);
        });
        receptionBackToReceptionButton2.addActionListener(actionEvent -> {
            cl.show(doctorTableLeftPanel, "startPage");
            cl2.show(doctorTableRightPanel, "voidPanel");
            tableOfReceptionToDoctor2.clearSelection();
            editListOfReceptionButton2.setEnabled(false);
        });
        protectListOfReceptionButton.addActionListener(actionEvent -> {
            new complaintPatients(this, tableOfReceptionToDoctor.getSelectedRows(),
                    tableOfReceptionToDoctor, tableOfReceptionToDoctor2, protectListOfReceptionButton, rtdtm,
                    rtdtm2, doctor.getSecondName() + " " + doctor.getFirstName() + " " + doctor.getThirdName(),
                    doctor.getTableNumber(), "", "");
//            try {
//                rtdtm.addData(jdbcConnection, doctor.getTableNumber());
//                rtdtm2.addData(jdbcConnection, doctor.getTableNumber());
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            tableOfReceptionToDoctor.clearSelection();
//            protectListOfReceptionButton.setEnabled(false);
//            rtdtm.fireTableDataChanged();
//            rtdtm2.fireTableDataChanged();
        });
        editListOfReceptionButton2.addActionListener(actionEvent -> {
            String complaint = jdbcConnection.getComplaint(tableOfReceptionToDoctor2.getSelectedRows(), tableOfReceptionToDoctor2);
            String recomendation = jdbcConnection.getRecomendation(tableOfReceptionToDoctor2.getSelectedRows(), tableOfReceptionToDoctor2);
            new complaintPatients(this, tableOfReceptionToDoctor2.getSelectedRows(),
                    tableOfReceptionToDoctor2, tableOfReceptionToDoctor, editListOfReceptionButton2, rtdtm,
                    rtdtm2, doctor.getSecondName() + " " + doctor.getFirstName() + " " + doctor.getThirdName(),
                    doctor.getTableNumber(), complaint, recomendation);
        });

        tableOfReceptionToDoctor.setSelectionModel(new DefaultListSelectionModel() // Модель выбора для удобного интерфейса
        {
            @Override
            public void setSelectionInterval(int startIndex, int endIndex) {
                if (startIndex == endIndex) // Если выбран один элемент
                {
                    if (getMinSelectionIndex() != getMaxSelectionIndex())
                    {
                        clearSelection(); // Убрать выделение
                        protectListOfReceptionButton.setEnabled(false);
                    }
                    if (isSelectedIndex(startIndex))
                    {
                        clearSelection(); // Убрать выделение
                        protectListOfReceptionButton.setEnabled(false);
                    }
                    else
                    {
                        super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                        protectListOfReceptionButton.setEnabled(true);
                    }
                }
                // Если выбраны многие элементы
                else {
                    super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                    protectListOfReceptionButton.setEnabled(false);
                }
            }
        });
        tableOfReceptionToDoctor2.setSelectionModel(new DefaultListSelectionModel() // Модель выбора для удобного интерфейса
        {
            @Override
            public void setSelectionInterval(int startIndex, int endIndex) {
                if (startIndex == endIndex) // Если выбран один элемент
                {
                    if (getMinSelectionIndex() != getMaxSelectionIndex())
                    {
                        clearSelection(); // Убрать выделение
                        editListOfReceptionButton2.setEnabled(false);
                    }
                    if (isSelectedIndex(startIndex))
                    {
                        clearSelection(); // Убрать выделение
                        editListOfReceptionButton2.setEnabled(false);
                    }
                    else
                    {
                        super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                        editListOfReceptionButton2.setEnabled(true);
                    }
                }
                // Если выбраны многие элементы
                else {
                    super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                    editListOfReceptionButton2.setEnabled(false);
                }
            }
        });
////////////////////ActionListeners(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void addRegistryMainWindow(JScrollPane _panel, int width, int height)
    {
        this.setBounds(0, 0, width, height); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // При нажатии на X произойдёт выход из приложения
        this.setFocusable(true); // Фокус на окне
        this.setVisible(true); // Делаем окно видимым
        this.setResizable(true); // Запрет на изменение размеров окна - нет
        this.setExtendedState(this.MAXIMIZED_BOTH); // Растяжение на весь экран
        this.setTitle("Врач"); // Заголовок окна

        this.add(_panel);
    }
}
