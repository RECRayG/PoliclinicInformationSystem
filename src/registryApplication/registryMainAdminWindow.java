package registryApplication;

import Connector.CONST;
import Connector.JDBCConnection;
import Users.Admin;
import Users.Doctor;
import Users.Patient;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import registryApplication.searhModel.searchPatients;
import registryApplication.searhModel.searchTimetable;
import registryApplication.tableModels.*;
import registryApplication.tableModels.editorDoctorsTable.editorDoctorsTableModel;
import registryApplication.searhModel.myRowFilter;
import registryApplication.searhModel.searchDoctors;
import registryApplication.tableModels.editorPatientModel.editorPatientsTableModel;
import registryApplication.tableModels.editorTimetableTable.editorTimetableTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class registryMainAdminWindow extends JFrame
{
    private JDBCConnection jdbcConnection = new JDBCConnection();
    private Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); // Создаём переменную, в которой хранится размер экрана


    private JPanel mainAdminWindowPanel = new JPanel(); // Основная панель
    private JScrollPane scrollMainAdminWindowPanel = new JScrollPane(mainAdminWindowPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel adminTablePanel = new JPanel(); // Табличная панель

    private JPanel cardPanel = new JPanel(); // Рабочая панель

///////////////Рабочая панель(begin)///////////////////////////////////////////////////////////////////////////////////////////////////
    private JPanel voidReferenceAdminTimetablePanel = new JPanel();
    private JLabel voidReferenceAdminTimetableLabel = new JLabel("Администратор: ");
    private JLabel voidReferenceTimetableAdminNLPLabel = new JLabel();
    private JButton backToReferenceFromVoidTimetablePanelButton = new JButton("< Назад");

    private JPanel voidReferenceListOfDoctorsPanel = new JPanel();
    private JLabel voidReferenceListOfDoctorsAdminLabel = new JLabel("Администратор: ");
    private JLabel voidReferenceListOfDoctorsAdminNLPLabel = new JLabel();
    private JButton backToReferenceFromVoidListOfDoctorsPanelButton = new JButton("< Назад");

    private JPanel voidReferenceListOfPatientsPanel = new JPanel();
    private JLabel voidReferenceListOfPatientsAdminLabel = new JLabel("Администратор: ");
    private JLabel voidReferenceListOfPatientsAdminNLPLabel = new JLabel();
    private JButton backToReferenceFromVoidListOfPatientsPanelButton = new JButton("< Назад");

    private JPanel startRegistryMainAdminWindow = new JPanel();
    private JLabel startAdminLabel = new JLabel("Администратор: ");
    private JLabel startAdminNLPLabel = new JLabel();
    private JButton startAdminReferenceButton = new JButton("Справочная");
    private JButton startAdminServiceButton = new JButton("Служебная");
    private JButton startAdminNewDataButton = new JButton("Новые данные");

    private JPanel referenceRegistryMainAdminWindow = new JPanel();
    private JLabel referenceAdminLabel = new JLabel("Администратор: ");
    private JLabel referenceAdminNLPLabel = new JLabel();
    private JButton backToStartFromReferenceRegistryMainAdminWindow = new JButton("< Назад");
    private JButton referenceAdminTimetableButton = new JButton("Расписание");
    private JButton referenceAdminListDoctorButton = new JButton("Список врачей");
    private JButton referenceAdminListPatientButton = new JButton("Список пациентов");

    private JPanel serviceRegistryMainAdminWindow = new JPanel();
    private JLabel serviceAdminLabel = new JLabel("Администратор: ");
    private JLabel serviceAdminNLPLabel = new JLabel();
    private JButton backToStartFromServiceRegistryMainAdminWindow = new JButton("< Назад");
    private JButton serviceAdminDoctorVisitButton = new JButton("Назначить приём");

    private JPanel voidNewDataRegistryMainAdminWindow = new JPanel();
    private JLabel voidNewDataAdminLabel = new JLabel("Администратор: ");
    private JLabel voidNewDataAdminNLPLabel = new JLabel();
    private JButton voidBackToStartFromNewDataRegistryMainAdminWindow = new JButton("< Назад");

    private JPanel newDataRegistryMainAdminWindow = new JPanel();
    private JLabel newDataAdminLabel = new JLabel("Администратор: ");
    private JLabel newDataAdminNLPLabel = new JLabel();
    private JButton backToStartFromNewDataRegistryMainAdminWindow = new JButton("< Назад");
    private JButton newDataAdminAddDoctorButton = new JButton("Добавить врача");
    private JButton newDataAdminAddPatientButton = new JButton("Добавить пациента");
///////////////Рабочая панель(end)///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////Табличная панель(begin)///////////////////////////////////////////////////////////////////////////////////////////////////
    private JPanel voidPanel = new JPanel();
    /////////////////////registerDoctorPanel(begin)//////////////////////////////////////////////////////////////////////////////////////
    private JPanel registerDoctorPanel = new JPanel();
    private JLabel secondNameDoctorLabel = new JLabel("Фамилия: ");
    private JLabel firstNameDoctorLabel = new JLabel("Имя: ");
    private JLabel thirdNameDoctorLabel = new JLabel("Отчество: ");
    private JLabel sexOfDoctorLabel = new JLabel("Пол: ");
    private JLabel maleSexOfDoctorLabel = new JLabel("Муж");
    private JLabel femaleSexOfDoctorLabel = new JLabel("Жен");
    private JLabel birthdayDayDoctorLabel = new JLabel("Дата рождения: ");
    private JLabel numberOfPlotDoctorLabel = new JLabel("Номер участка: ");
    private JLabel specializationOfDoctorLabel = new JLabel("Специальность: ");
    private JLabel dateOfStartJobDoctorLabel = new JLabel("Дата приёма на работу: ");
    private JLabel newPasswordDoctorLabel = new JLabel("Придумайте пароль: ");
    private JLabel mailAddressDoctorLabel = new JLabel("Введите почту врача: ");
    private JLabel addressOfLiveDoctorLabel = new JLabel("Адрес проживания: ");
    private JLabel cityOfDoctorLabel = new JLabel("Город: ");
    private JLabel streetOfDoctorLabel = new JLabel("Улица: ");
    private JLabel buildingOfDoctorLabel = new JLabel("Дом: ");
    private JLabel apartmentOfDoctorLabel = new JLabel("Квартира: ");
    private JTextField secondNameDoctorText = new JTextField();
    private JTextField firstNameDoctorText = new JTextField();
    private JTextField thirdNameDoctorText = new JTextField();
    private JCheckBox maleDoctorCheckBox = new JCheckBox();
    private JCheckBox femaleDoctorCheckBox = new JCheckBox();
    private JDateChooser birthdayDayDoctorTable = new JDateChooser();
    private JComboBox<Integer> numberOfPlotDoctorComboBox;
    private JComboBox<String> specializationOfDoctorComboBox;
    private JDateChooser dateOfStartJobDoctorDate = new JDateChooser();
    private JTextField newPasswordDoctorText = new JTextField();
    private JTextField mailAddressDoctorText = new JTextField();
    private JTextField cityOfDoctorText = new JTextField();
    private JTextField streetOfDoctorText = new JTextField();
    private JTextField buildingOfDoctorText = new JTextField();
    private JTextField apartmentOfDoctorText = new JTextField();
    private JLabel notDoctorData = new JLabel("Укажите все данные!!!");
    private JButton registerDoctorButton = new JButton("Зарегистрировать");
    private JScrollPane scrollRegisterDoctor = new JScrollPane(registerDoctorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    /////////////////////registerDoctorPanel(end)////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////registerPatientPanel(begin)//////////////////////////////////////////////////////////////////////////////////////
    private JPanel registerPatientPanel = new JPanel();
    private JLabel secondNamePatientLabel = new JLabel("Фамилия: ");
    private JLabel firstNamePatientLabel = new JLabel("Имя: ");
    private JLabel thirdNamePatientLabel = new JLabel("Отчество: ");
    private JLabel sexOfPatientLabel = new JLabel("Пол: ");
    private JLabel maleSexOfPatientLabel = new JLabel("Муж");
    private JLabel femaleSexOfPatientLabel = new JLabel("Жен");
    private JLabel birthdayDayPatientLabel = new JLabel("Дата рождения: ");
    private JLabel dateOfCreatePatientCardLabel = new JLabel("Дата создания карточки: ");
    private JLabel numberOfPassportPatientLabel = new JLabel("Номер паспорта: ");
    private JLabel numberOfPhonePatientLabel = new JLabel("Номер телефона: ");
    private JLabel numberOfInsurancePolicyLabel = new JLabel("Номер страхового полиса: ");
    private JLabel addressOfLivePatientLabel = new JLabel("Адрес проживания: ");
    private JLabel cityOfPatientLabel = new JLabel("Город: ");
    private JLabel streetOfPatientLabel = new JLabel("Улица: ");
    private JLabel buildingOfPatientLabel = new JLabel("Дом: ");
    private JLabel apartmentOfPatientLabel = new JLabel("Квартира: ");
    private JTextField secondNamePatientText = new JTextField();
    private JTextField firstNamePatientText = new JTextField();
    private JTextField thirdNamePatientText = new JTextField();
    private JCheckBox malePatientCheckBox = new JCheckBox();
    private JCheckBox femalePatientCheckBox = new JCheckBox();
    private JDateChooser birthdayDayPatientTable = new JDateChooser();
    private JDateChooser dateOfCreatePatientCard = new JDateChooser();
    private JTextField numberOfPassportPatientText = new JTextField();
    private JTextField numberOfPhonePatientText = new JTextField();
    private JTextField numberOfInsurancePolicyText = new JTextField();
    private JTextField cityOfPatientText = new JTextField();
    private JList<String> streetOfPatientText;
    private JTextField buildingOfPatientText = new JTextField();
    private JTextField apartmentOfPatientText = new JTextField();
    private JLabel notPatientData = new JLabel("Укажите все данные!!!");
    private JButton registerPatientButton = new JButton("Зарегистрировать");
    private JScrollPane scrollRegisterPatient = new JScrollPane(registerPatientPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    /////////////////////registerPatientPanel(end)////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////listOfDoctorsPanel(begin)//////////////////////////////////////////////////////////////////////////////////////
    private JPanel propertiesAndListOfDoctorsPanel = new JPanel();
    private JScrollPane scrollPropertyAndListOfDoctorsPanel = new JScrollPane(propertiesAndListOfDoctorsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel listOfDoctorsPanel = new JPanel();
    private JTable tableOfDoctors = new JTable();
    private doctorsTableModel dtm = new doctorsTableModel(tableOfDoctors);
    private TableRowSorter<AbstractTableModel> rowSorter = new TableRowSorter(dtm);
    private JScrollPane scrollTableOfDoctors = new JScrollPane(tableOfDoctors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel propertiesListOfDoctorPanel = new JPanel();
    private JButton editListOfDoctorsButton = new JButton("Редактировать");
    private JButton deleteRowsOfListOfDoctorsButton = new JButton("Удалить");
    private JButton searchListOfDoctorsButton = new JButton("Поиск");
    private JButton throwOffSearchListOfDoctorButton = new JButton("Сбросить поиск");
    private JLabel sortInfo = new JLabel("Для сортировки дважды щёлкните по заголовку необходимой колонки");

    private JPanel propertiesAndEditListOfDoctorsPanel = new JPanel();
    private JScrollPane scrollPropertyAndEditListOfDoctorsPanel = new JScrollPane(propertiesAndEditListOfDoctorsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel editPropertiesListOfDoctorPanel = new JPanel();
    private JButton applyEditListOfDoctorsButton = new JButton("Применить");
    private JButton cancelEditListOfDoctorsButton = new JButton("Отмена");

    private JPanel editorListOfDoctorsPanel = new JPanel();
    private JTable editorTableOfDoctors = new JTable();
    private editorDoctorsTableModel edtm = new editorDoctorsTableModel(this, editorTableOfDoctors, applyEditListOfDoctorsButton, dtm.getColumnTypes(), dtm.getColumnNames());
    private JScrollPane scrollEditorTableOfDoctors = new JScrollPane(editorTableOfDoctors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
/////////////////////listOfDoctorsPanel(end)////////////////////////////////////////////////////////////////////////////////////////

/////////////////////listOfPatientPanel(begin)//////////////////////////////////////////////////////////////////////////////////////
    private JPanel propertiesAndListOfPatientPanel = new JPanel();
    private JScrollPane scrollPropertyAndListOfPatientsPanel = new JScrollPane(propertiesAndListOfPatientPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel listOfPatientsPanel = new JPanel();
    private JTable tableOfPatients = new JTable();
    private patientsTableModel ptm = new patientsTableModel(tableOfPatients);
    private TableRowSorter<AbstractTableModel> rowSorterPatient = new TableRowSorter(ptm);
    private JScrollPane scrollTableOfPatients = new JScrollPane(tableOfPatients, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel propertiesListOfPatientPanel = new JPanel();
    private JButton editListOfPatientsButton = new JButton("Редактировать");
    private JButton deleteRowsOfListOfPatientsButton = new JButton("Удалить");
    private JButton searchListOfPatientsButton = new JButton("Поиск");
    private JButton throwOffSearchListOfPatientButton = new JButton("Сбросить поиск");
    private JLabel sortInfoPatient = new JLabel("Для сортировки дважды щёлкните по заголовку необходимой колонки");

    private JPanel propertiesAndEditListOfPatientsPanel = new JPanel();
    private JScrollPane scrollPropertyAndEditListOfPatientsPanel = new JScrollPane(propertiesAndEditListOfPatientsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel editPropertiesListOfPatientPanel = new JPanel();
    private JButton applyEditListOfPatientsButton = new JButton("Применить");
    private JButton cancelEditListOfPatientsButton = new JButton("Отмена");

    private JPanel editorListOfPatientsPanel = new JPanel();
    private JTable editorTableOfPatients = new JTable();
    private editorPatientsTableModel eptm = new editorPatientsTableModel(this, editorTableOfPatients, applyEditListOfPatientsButton, ptm.getColumnTypes(), ptm.getColumnNames(), ptm.getColumnRUSNames());
    private JScrollPane scrollEditorTableOfPatients = new JScrollPane(editorTableOfPatients, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
/////////////////////listOfPatientPanel(end)////////////////////////////////////////////////////////////////////////////////////////

/////////////////////timetablePanel(begin)//////////////////////////////////////////////////////////////////////////////////////
    private JPanel propertiesAndTimetablePanel = new JPanel();
    private JScrollPane scrollPropertiesAndTimetablePanel = new JScrollPane(propertiesAndTimetablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel timetablePanel = new JPanel();
    private JTable tableOfTimetable = new JTable();
    private timetableTableModel ttm;
    private TableRowSorter<AbstractTableModel> rowSorterTimetable;
    private JScrollPane scrollTableOfTimetable = new JScrollPane(tableOfTimetable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel propertiesTimetablePanel = new JPanel();
    private JButton editTimetableButton = new JButton("Редактировать");
    private JButton searchTimetableButton = new JButton("Поиск");
    private JButton throwOffSearchTimetableButton = new JButton("Сбросить поиск");
    private JButton previousWeekButton = new JButton("< Предыдущая неделя");
    private JButton nextWeekButton = new JButton("Следующая неделя >");


    private JPanel propertiesAndEditTimetablePanel = new JPanel();
    private JScrollPane scrollPropertyAndEditTimetablePanel = new JScrollPane(propertiesAndEditTimetablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel editPropertiesTimetablePanel = new JPanel();
    private JButton applyEditTimetableButton = new JButton("Применить");
    private JButton cancelEditTimetableButton = new JButton("Отмена");

    private JPanel editorTimetablePanel = new JPanel();
    private JTable editorTableOfTimetable = new JTable();
    private editorTimetableTableModel ettm;
    private JScrollPane scrollEditorTableOfTimetable = new JScrollPane(editorTableOfTimetable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
/////////////////////timetablePanel(end)////////////////////////////////////////////////////////////////////////////////////////

/////////////////////receptionPanel(begin)////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////receptionVoidPanel(begin)///////////////////////////////////////////////////////////////////////////////
    private JPanel voidServiceAdminReceptionPanel = new JPanel();
    private JLabel voidServiceAdminReceptionLabel = new JLabel("Администратор: ");
    private JLabel voidServiceReceptionAdminNLPLabel = new JLabel();
    private JButton backToServiceFromVoidReceptionPanelButton = new JButton("< Назад");
    //////////////////////receptionVoidPanel(end)/////////////////////////////////////////////////////////////////////////////////

    //////////////////////receptionPanelMain(begin)///////////////////////////////////////////////////////////////////////////////
    private JPanel receptionPanel = new JPanel();
    private JScrollPane scrollReceptionPanel= new JScrollPane(receptionPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel receptionTablePanel = new JPanel();
    private JTable tableOfReception = new JTable();
    private receptionTableModel rtm;
    private TableRowSorter<AbstractTableModel> rowSorterReception;
    private JScrollPane scrollTableOfReception = new JScrollPane(tableOfReception, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JPanel propertiesReceptionPanel = new JPanel();
    private JLabel chooseSpecializationLabel = new JLabel("Специальность: ");
    private JComboBox chooseSpecializationJComboBox;

    private JLabel chooseDoctorLabel = new JLabel("Доктор: ");
    private DefaultListModel<String> chooseDoctorJListModel = new DefaultListModel<>();
    private JList<String> chooseDoctorJList = new JList(chooseDoctorJListModel);
    private JScrollPane scrollChooseDoctorJList = new JScrollPane(chooseDoctorJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private DefaultComboBoxModel chooseDateJComboBoxModel = new DefaultComboBoxModel();
    private JLabel chooseDateLabel = new JLabel("Дата: ");
    private JComboBox chooseDateJComboBox = new JComboBox(chooseDateJComboBoxModel);

    private JLabel showHoursLabel = new JLabel("Часы приёма: ");
    private JTextArea showHoursText = new JTextArea();

    private DefaultComboBoxModel chooseTimeJComboBoxModel = new DefaultComboBoxModel();
    private JLabel chooseTimeLabel = new JLabel("Свободное время: ");
    private JComboBox chooseTimeJComboBox = new JComboBox(chooseTimeJComboBoxModel);

    private JLabel choosePatientLabel = new JLabel("Пациент: ");
    private DefaultListModel<String> choosePatientJListModel = new DefaultListModel<>();
    private JList<String> choosePatientJList = new JList(choosePatientJListModel);
    private JScrollPane scrollChoosePatientJList = new JScrollPane(choosePatientJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JButton addToReceptionButton = new JButton("Записать на приём");
    private JButton cancelFromReceptionButton = new JButton("Отменить запись(-и)");
    //////////////////////receptionPanelMain(edn)/////////////////////////////////////////////////////////////////////////////////
/////////////////////receptionPanel(end)//////////////////////////////////////////////////////////////////////////////////////////

///////////////Табличная панель(end)///////////////////////////////////////////////////////////////////////////////////////////////////

    public registryMainAdminWindow(Admin admin) // Конструктор класса без параметров
    {
/////////////////newDataRegistryMainAdminWindow(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////newDataRegistryMainAdminWindowSource(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        newDataRegistryMainAdminWindow.setBackground(new Color(255,255,255));
        newDataRegistryMainAdminWindow.setLayout(new MigLayout());
        newDataAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        newDataRegistryMainAdminWindow.add(newDataAdminLabel, "al left, wrap 5");
        newDataRegistryMainAdminWindow.add(newDataAdminNLPLabel, "al left, wrap 5");
        newDataRegistryMainAdminWindow.add(backToStartFromNewDataRegistryMainAdminWindow, "al left, wrap 35");
        newDataRegistryMainAdminWindow.add(newDataAdminAddDoctorButton, "al center, gapleft 50, wrap 15");
        newDataRegistryMainAdminWindow.add(newDataAdminAddPatientButton, "al center, gapleft 50");
        ////////////////////////newDataRegistryMainAdminWindowSource(end)////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////voidNewDataRegistryMainAdminWindow(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        voidNewDataRegistryMainAdminWindow.setBackground(new Color(255,255,255));
        voidNewDataRegistryMainAdminWindow.setLayout(new MigLayout());
        voidNewDataAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        voidNewDataRegistryMainAdminWindow.add(voidNewDataAdminLabel, "al left, wrap 5");
        voidNewDataRegistryMainAdminWindow.add(voidNewDataAdminNLPLabel, "al left, wrap 5");
        voidNewDataRegistryMainAdminWindow.add(voidBackToStartFromNewDataRegistryMainAdminWindow, "al left");
        ////////////////////////voidNewDataRegistryMainAdminWindow(end)////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////newDataRegistryMainAdminWindow(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////serviceRegistryMainAdminWindow(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        serviceRegistryMainAdminWindow.setBackground(new Color(255,255,255));
        serviceRegistryMainAdminWindow.setLayout(new MigLayout());
        serviceAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        serviceRegistryMainAdminWindow.add(serviceAdminLabel, "al left, wrap 5");
        serviceRegistryMainAdminWindow.add(serviceAdminNLPLabel, "al left, wrap 5");
        serviceRegistryMainAdminWindow.add(backToStartFromServiceRegistryMainAdminWindow, "al left, wrap 35");
        serviceRegistryMainAdminWindow.add(serviceAdminDoctorVisitButton, "al center, gapleft 50");
/////////////////serviceRegistryMainAdminWindow(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////referenceRegistryMainAdminWindow(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////referenceSource(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        referenceRegistryMainAdminWindow.setBackground(new Color(255,255,255));
        referenceRegistryMainAdminWindow.setLayout(new MigLayout());
        referenceAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        referenceRegistryMainAdminWindow.add(referenceAdminLabel, "al left, wrap 5");
        referenceRegistryMainAdminWindow.add(referenceAdminNLPLabel, "al left, wrap 5");
        referenceRegistryMainAdminWindow.add(backToStartFromReferenceRegistryMainAdminWindow, "al left, wrap 35");
        referenceRegistryMainAdminWindow.add(referenceAdminTimetableButton, "al center, gapleft 50, wrap 15");
        referenceRegistryMainAdminWindow.add(referenceAdminListDoctorButton, "al center, gapleft 50, wrap 15");
        referenceRegistryMainAdminWindow.add(referenceAdminListPatientButton, "al center, gapleft 50");
        ////////////////////////referenceSource(end)////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////voidReferenceListOfDoctorsPanel(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        voidReferenceListOfDoctorsPanel.setBackground(new Color(255,255,255));
        voidReferenceListOfDoctorsPanel.setLayout(new MigLayout());
        voidReferenceListOfDoctorsAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        voidReferenceListOfDoctorsPanel.add(voidReferenceListOfDoctorsAdminLabel, "al left, wrap 5");
        voidReferenceListOfDoctorsPanel.add(voidReferenceListOfDoctorsAdminNLPLabel, "al left, wrap 5");
        voidReferenceListOfDoctorsPanel.add(backToReferenceFromVoidListOfDoctorsPanelButton, "al left");
        ////////////////////////voidReferenceListOfDoctorsPanel(begin)//////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////voidReferenceListOfDoctorsPanel(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        voidReferenceListOfPatientsPanel.setBackground(new Color(255,255,255));
        voidReferenceListOfPatientsPanel.setLayout(new MigLayout());
        voidReferenceListOfPatientsAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        voidReferenceListOfPatientsPanel.add(voidReferenceListOfPatientsAdminLabel, "al left, wrap 5");
        voidReferenceListOfPatientsPanel.add(voidReferenceListOfPatientsAdminNLPLabel, "al left, wrap 5");
        voidReferenceListOfPatientsPanel.add(backToReferenceFromVoidListOfPatientsPanelButton, "al left");
        ////////////////////////voidReferenceListOfDoctorsPanel(begin)//////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////voidReferenceTimetablePanel(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        voidReferenceAdminTimetablePanel.setBackground(new Color(255,255,255));
        voidReferenceAdminTimetablePanel.setLayout(new MigLayout());
        voidReferenceTimetableAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        voidReferenceAdminTimetablePanel.add(voidReferenceAdminTimetableLabel, "al left, wrap 5");
        voidReferenceAdminTimetablePanel.add(voidReferenceTimetableAdminNLPLabel, "al left, wrap 5");
        voidReferenceAdminTimetablePanel.add(backToReferenceFromVoidTimetablePanelButton, "al left");
        ////////////////////////voidReferenceTimetablePanel(end)////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////voidReferenceReceptionPanel(begin)//////////////////////////////////////////////////////////////////////////////////////////////
        voidServiceAdminReceptionPanel.setBackground(new Color(255,255,255));
        voidServiceAdminReceptionPanel.setLayout(new MigLayout());
        voidServiceReceptionAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        voidServiceAdminReceptionPanel.add(voidServiceAdminReceptionLabel, "al left, wrap 5");
        voidServiceAdminReceptionPanel.add(voidServiceReceptionAdminNLPLabel, "al left, wrap 5");
        voidServiceAdminReceptionPanel.add(backToServiceFromVoidReceptionPanelButton, "al left");
        ////////////////////////voidReferenceReceptionPanel(end)////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////referenceRegistryMainAdminWindow(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////startRegistryMainAdminWindow(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        startRegistryMainAdminWindow.setBackground(new Color(255,255,255));
        startRegistryMainAdminWindow.setLayout(new MigLayout());
        startAdminNLPLabel.setText(admin.getSecondName() + " " + admin.getFirstName() + " " + admin.getThirdName());

        startRegistryMainAdminWindow.add(startAdminLabel, "al left, wrap 5");
        startRegistryMainAdminWindow.add(startAdminNLPLabel, "al left, wrap 66");
        startRegistryMainAdminWindow.add(startAdminReferenceButton, "al center, gapleft 50, wrap 15");
        startRegistryMainAdminWindow.add(startAdminServiceButton, "al center, gapleft 50, wrap 15");
        startRegistryMainAdminWindow.add(startAdminNewDataButton, "al center, gapleft 50");
/////////////////startRegistryMainAdminWindow(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////voidPanel(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////
        registerDoctorPanel.setBackground(new Color(122,122,122));
////////////////voidDoctorPanel(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////registerDoctorPanel(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////
        registerDoctorPanel.setBackground(new Color(255,255,255));
        registerDoctorPanel.setLayout(new MigLayout());
        notDoctorData.setForeground(new Color(230,50,50));
        notDoctorData.setVisible(false);

        Vector<Integer> data = new Vector();
        for(int i = 1; i <= 37; i++)
        {
            data.add(i);
        }
        numberOfPlotDoctorComboBox = new JComboBox(data);
        numberOfPlotDoctorComboBox.setSelectedIndex(0);
        numberOfPlotDoctorComboBox.setFocusable(false);

        Vector vector = new Vector();
        vector.addAll(jdbcConnection.getAllSpecialization());
        specializationOfDoctorComboBox = new JComboBox(vector);
        specializationOfDoctorComboBox.setSelectedIndex(0);
        specializationOfDoctorComboBox.setFocusable(false);

        birthdayDayDoctorTable.setDateFormatString("yyyy-MM-dd");
        birthdayDayDoctorTable.setDate(new Date());
        dateOfStartJobDoctorDate.setDateFormatString("yyyy-MM-dd");
        dateOfStartJobDoctorDate.setDate(new Date());

        registerDoctorPanel.add(secondNameDoctorLabel, "gapleft 250, gaptop 50, al right");
        registerDoctorPanel.add(secondNameDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(firstNameDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(firstNameDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(thirdNameDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(thirdNameDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(sexOfDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(maleSexOfDoctorLabel, "split 4");
        registerDoctorPanel.add(maleDoctorCheckBox);
        registerDoctorPanel.add(femaleSexOfDoctorLabel, "split 2");
        registerDoctorPanel.add(femaleDoctorCheckBox, "wrap");
        registerDoctorPanel.add(birthdayDayDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(birthdayDayDoctorTable, "w 150!, wrap");
        registerDoctorPanel.add(numberOfPlotDoctorLabel, "gapleft 298, al right");
        registerDoctorPanel.add(numberOfPlotDoctorComboBox, "wrap");
        registerDoctorPanel.add(specializationOfDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(specializationOfDoctorComboBox, "w 300!, wrap");
        registerDoctorPanel.add(dateOfStartJobDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(dateOfStartJobDoctorDate, "w 150!, wrap");
        registerDoctorPanel.add(newPasswordDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(newPasswordDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(mailAddressDoctorLabel, "gapleft 250, al right");
        registerDoctorPanel.add(mailAddressDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(addressOfLiveDoctorLabel, "gapleft 250, span 1 4, top, al right"); // Растянуть компонент 1х4
        registerDoctorPanel.add(cityOfDoctorLabel, "gapleft 18, split 2");
        registerDoctorPanel.add(cityOfDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(streetOfDoctorLabel, "gapleft 18, split 2");
        registerDoctorPanel.add(streetOfDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(buildingOfDoctorLabel, "gapleft 30, split 2");
        registerDoctorPanel.add(buildingOfDoctorText, "w 250!, wrap");
        registerDoctorPanel.add(apartmentOfDoctorLabel, "split 2");
        registerDoctorPanel.add(apartmentOfDoctorText, "w 250!, wrap 20");
        registerDoctorPanel.add(notDoctorData, "gapleft 250, span 2 1, al center, wrap 25");
        registerDoctorPanel.add(registerDoctorButton, "gapleft 250, span 2 1, al center");
////////////////registerDoctorPanel(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////registerPatientPanel(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////
        registerPatientPanel.setBackground(new Color(255,255,255));
        registerPatientPanel.setLayout(new MigLayout());
        notPatientData.setForeground(new Color(230,50,50));
        notPatientData.setVisible(false);

        birthdayDayPatientTable.setDateFormatString("yyyy-MM-dd");
        birthdayDayPatientTable.setDate(new Date());
        dateOfCreatePatientCard.setDateFormatString("yyyy-MM-dd");
        dateOfCreatePatientCard.setDate(new Date());

        streetOfPatientText = new JList(jdbcConnection.getAllStrees());
        JScrollPane scrollStreetOfPatientText = new JScrollPane(streetOfPatientText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        registerPatientPanel.add(secondNamePatientLabel, "gapleft 250, gaptop 50, al right");
        registerPatientPanel.add(secondNamePatientText, "w 250!, wrap");
        registerPatientPanel.add(firstNamePatientLabel, "gapleft 250, al right");
        registerPatientPanel.add(firstNamePatientText, "w 250!, wrap");
        registerPatientPanel.add(thirdNamePatientLabel, "gapleft 250, al right");
        registerPatientPanel.add(thirdNamePatientText, "w 250!, wrap");
        registerPatientPanel.add(sexOfPatientLabel, "gapleft 250, al right");
        registerPatientPanel.add(maleSexOfPatientLabel, "split 4");
        registerPatientPanel.add(malePatientCheckBox);
        registerPatientPanel.add(femaleSexOfPatientLabel, "split 2");
        registerPatientPanel.add(femalePatientCheckBox, "wrap");
        registerPatientPanel.add(birthdayDayPatientLabel, "gapleft 250, al right");
        registerPatientPanel.add(birthdayDayPatientTable, "w 150!, wrap");
        registerPatientPanel.add(dateOfCreatePatientCardLabel, "gapleft 250, al right");
        registerPatientPanel.add(dateOfCreatePatientCard, "w 150!, wrap");
        registerPatientPanel.add(numberOfPassportPatientLabel, "gapleft 250, al right");
        registerPatientPanel.add(numberOfPassportPatientText, "w 150!, wrap");
        registerPatientPanel.add(numberOfPhonePatientLabel, "gapleft 250, al right");
        registerPatientPanel.add(numberOfPhonePatientText, "w 200!, wrap");
        registerPatientPanel.add(numberOfInsurancePolicyLabel, "gapleft 250, al right");
        registerPatientPanel.add(numberOfInsurancePolicyText, "w 150!, wrap");
        registerPatientPanel.add(addressOfLivePatientLabel, "gapleft 250, span 1 4, top, al right"); // Растянуть компонент 1х4
        registerPatientPanel.add(cityOfPatientLabel, "gapleft 18, split 2");
        registerPatientPanel.add(cityOfPatientText, "w 250!, wrap");
        registerPatientPanel.add(streetOfPatientLabel, "gapleft 18, split 2");
        registerPatientPanel.add(scrollStreetOfPatientText, "w 250!, h 100!, wrap");
        registerPatientPanel.add(buildingOfPatientLabel, "gapleft 30, split 2");
        registerPatientPanel.add(buildingOfPatientText, "w 250!, wrap");
        registerPatientPanel.add(apartmentOfPatientLabel, "split 2");
        registerPatientPanel.add(apartmentOfPatientText, "w 250!, wrap 20");
        registerPatientPanel.add(notPatientData, "gapleft 250, span 2 1, al center, wrap 25");
        registerPatientPanel.add(registerPatientButton, "gapleft 250, span 2 1, al center");
////////////////registerPatientPanel(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////listOfPatientPanel(begin)///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////propertiesAndListOfDoctorsPanel(begin)///////////////////////////////////////////////////////////////////////////////////
        propertiesAndListOfPatientPanel.setBackground(new Color(255,255,255));
        propertiesAndListOfPatientPanel.setLayout(new MigLayout());

        listOfPatientsPanel.setBackground(new Color(255,255,255));
        listOfPatientsPanel.setLayout(new MigLayout());

        propertiesListOfPatientPanel.setBackground(new Color(100,100,100));
        propertiesListOfPatientPanel.setLayout(new MigLayout());

        propertiesAndListOfPatientPanel.add(listOfPatientsPanel, "pushx, growx, h 600!, wrap");
        propertiesAndListOfPatientPanel.add(propertiesListOfPatientPanel, "growx, pushx, h 60!");

        editListOfPatientsButton.setEnabled(false);
        deleteRowsOfListOfPatientsButton.setEnabled(false);
        throwOffSearchListOfPatientButton.setEnabled(false);
        sortInfoPatient.setForeground(new Color(255,255,255));

        propertiesListOfPatientPanel.add(editListOfPatientsButton, "split 6, gaptop 10, gapleft 45");
        propertiesListOfPatientPanel.add(deleteRowsOfListOfPatientsButton);
        propertiesListOfPatientPanel.add(searchListOfPatientsButton, "split 2, span 1 2, gapleft 120");
        propertiesListOfPatientPanel.add(throwOffSearchListOfPatientButton);
        propertiesListOfPatientPanel.add(sortInfoPatient, "gapleft 60");

        tableOfPatients.setModel(ptm);
        tableOfPatients.setRowSorter(rowSorterPatient); // Для сортировки по столбцам
        tableOfPatients.setColumnSelectionAllowed(false);
        tableOfPatients.getTableHeader().setReorderingAllowed(false);
        try { ptm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
        catch (SQLException e) { e.printStackTrace(); }
        ptm.setColumnStaticWidth(tableOfPatients); // Установка ширины полей
        tableOfPatients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Для добавления полосы прокрутки влево-вправо

        listOfPatientsPanel.add(scrollTableOfPatients, "push, grow, al center");
        ///////////////////propertiesAndListOfDoctorsPanel(end)/////////////////////////////////////////////////////////////////////////////////////

        ///////////////////propertiesAndEditListOfDoctorsPanel(begin)///////////////////////////////////////////////////////////////////////////////////
        propertiesAndEditListOfPatientsPanel.setBackground(new Color(255,255,255));
        propertiesAndEditListOfPatientsPanel.setLayout(new MigLayout());

        editorListOfPatientsPanel.setBackground(new Color(255,255,255));
        editorListOfPatientsPanel.setLayout(new MigLayout());

        editPropertiesListOfPatientPanel.setBackground(new Color(100,100,100));
        editPropertiesListOfPatientPanel.setLayout(new MigLayout());

        applyEditListOfPatientsButton.setEnabled(false);

        propertiesAndEditListOfPatientsPanel.add(editorListOfPatientsPanel, "pushx, growx, h 600!, wrap");
        propertiesAndEditListOfPatientsPanel.add(editPropertiesListOfPatientPanel, "growx, pushx, h 60!");

        editPropertiesListOfPatientPanel.add(applyEditListOfPatientsButton, "gaptop 10, split 2, al center");
        editPropertiesListOfPatientPanel.add(cancelEditListOfPatientsButton, "gaptop 10");

        editorTableOfPatients.setModel(eptm);
        editorTableOfPatients.setColumnSelectionAllowed(false);
        editorTableOfPatients.getTableHeader().setReorderingAllowed(false);
        eptm.setColumnStaticWidth(editorTableOfPatients);
        editorTableOfPatients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        editorListOfPatientsPanel.add(scrollEditorTableOfPatients, "push, grow, al center");
        ///////////////////propertiesAndEditListOfDoctorsPanel(end)/////////////////////////////////////////////////////////////////////////////////////
////////////////listOfPatientPanel(end)/////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////listOfDoctorPanel(begin)///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////propertiesAndListOfDoctorsPanel(begin)///////////////////////////////////////////////////////////////////////////////////
        propertiesAndListOfDoctorsPanel.setBackground(new Color(255,255,255));
        propertiesAndListOfDoctorsPanel.setLayout(new MigLayout());

        listOfDoctorsPanel.setBackground(new Color(255,255,255));
        listOfDoctorsPanel.setLayout(new MigLayout());

        propertiesListOfDoctorPanel.setBackground(new Color(100,100,100));
        propertiesListOfDoctorPanel.setLayout(new MigLayout());

        propertiesAndListOfDoctorsPanel.add(listOfDoctorsPanel, "pushx, growx, h 600!, wrap");
        propertiesAndListOfDoctorsPanel.add(propertiesListOfDoctorPanel, "growx, pushx, h 60!");

        editListOfDoctorsButton.setEnabled(false);
        deleteRowsOfListOfDoctorsButton.setEnabled(false);
        throwOffSearchListOfDoctorButton.setEnabled(false);
        sortInfo.setForeground(new Color(255,255,255));

        propertiesListOfDoctorPanel.add(editListOfDoctorsButton, "split 6, gaptop 10, gapleft 45");
        propertiesListOfDoctorPanel.add(deleteRowsOfListOfDoctorsButton);
        propertiesListOfDoctorPanel.add(searchListOfDoctorsButton, "split 2, span 1 2, gapleft 120");
        propertiesListOfDoctorPanel.add(throwOffSearchListOfDoctorButton);
        propertiesListOfDoctorPanel.add(sortInfo, "gapleft 60");

        tableOfDoctors.setModel(dtm);
        tableOfDoctors.setRowSorter(rowSorter); // Для сортировки по столбцам
        tableOfDoctors.setColumnSelectionAllowed(false);
        tableOfDoctors.getTableHeader().setReorderingAllowed(false);
        try { dtm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
        catch (SQLException e) { e.printStackTrace(); }
        dtm.setColumnStaticWidth(tableOfDoctors); // Установка ширины полей
        tableOfDoctors.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Для добавления полосы прокрутки влево-вправо

        listOfDoctorsPanel.add(scrollTableOfDoctors, "push, grow, al center");
        ///////////////////propertiesAndListOfDoctorsPanel(end)/////////////////////////////////////////////////////////////////////////////////////

        ///////////////////propertiesAndEditListOfDoctorsPanel(begin)///////////////////////////////////////////////////////////////////////////////////
        propertiesAndEditListOfDoctorsPanel.setBackground(new Color(255,255,255));
        propertiesAndEditListOfDoctorsPanel.setLayout(new MigLayout());

        editorListOfDoctorsPanel.setBackground(new Color(255,255,255));
        editorListOfDoctorsPanel.setLayout(new MigLayout());

        editPropertiesListOfDoctorPanel.setBackground(new Color(100,100,100));
        editPropertiesListOfDoctorPanel.setLayout(new MigLayout());

        applyEditListOfDoctorsButton.setEnabled(false);

        propertiesAndEditListOfDoctorsPanel.add(editorListOfDoctorsPanel, "pushx, growx, h 600!, wrap");
        propertiesAndEditListOfDoctorsPanel.add(editPropertiesListOfDoctorPanel, "growx, pushx, h 60!");

        editPropertiesListOfDoctorPanel.add(applyEditListOfDoctorsButton, "gaptop 10, split 2, al center");
        editPropertiesListOfDoctorPanel.add(cancelEditListOfDoctorsButton, "gaptop 10");

        editorTableOfDoctors.setModel(edtm);
        editorTableOfDoctors.setColumnSelectionAllowed(false);
        editorTableOfDoctors.getTableHeader().setReorderingAllowed(false);
        edtm.setColumnStaticWidth(editorTableOfDoctors);
        editorTableOfDoctors.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        editorListOfDoctorsPanel.add(scrollEditorTableOfDoctors, "push, grow, al center");
        ///////////////////propertiesAndEditListOfDoctorsPanel(end)/////////////////////////////////////////////////////////////////////////////////////
////////////////listOfDoctorPanel(end)/////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////timetablePanel(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////propertiesAndTimetablePanel(begin)//////////////////////////////////////////////////////////////////////////////////
        propertiesAndTimetablePanel.setBackground(new Color(255,255,255));
        propertiesAndTimetablePanel.setLayout(new MigLayout());

        timetablePanel.setBackground(new Color(255,255,255));
        timetablePanel.setLayout(new MigLayout());

        propertiesTimetablePanel.setBackground(new Color(100,100,100));
        propertiesTimetablePanel.setLayout(new MigLayout());

        propertiesAndTimetablePanel.add(timetablePanel, "pushx, growx, h 600!, wrap");
        propertiesAndTimetablePanel.add(propertiesTimetablePanel, "growx, pushx, h 60!");

        editTimetableButton.setEnabled(false);
        throwOffSearchTimetableButton.setEnabled(false);
        previousWeekButton.setEnabled(false);

        propertiesTimetablePanel.add(editTimetableButton, "split 6, gaptop 10, gapleft 45");
        propertiesTimetablePanel.add(searchTimetableButton, "split 2, span 1 2, gapleft 60");
        propertiesTimetablePanel.add(throwOffSearchTimetableButton);
        propertiesTimetablePanel.add(previousWeekButton, "split 2, span 1 2, gapleft 60");
        propertiesTimetablePanel.add(nextWeekButton);

        this.ttm = new timetableTableModel(tableOfTimetable, dtm.getDataArrayList());
        tableOfTimetable.setModel(ttm);
        tableOfTimetable.setColumnSelectionAllowed(false); // Запрет на перестановку столбцов
        tableOfTimetable.getTableHeader().setReorderingAllowed(false);
        rowSorterTimetable = new TableRowSorter(ttm);
        tableOfTimetable.setRowSorter(rowSorterTimetable); // Для сортировки по столбцам
        try
        {
            ttm.updateDate(jdbcConnection); // Добавление дат в ДБ или их обновление
            ttm.addData(jdbcConnection); // Добавление в модель таблицы данных из БД
            ttm.updateDateWeek(jdbcConnection); // Смещение дат в БД
        }
        catch (SQLException e) { e.printStackTrace(); }
        ttm.setColumnStaticWidth(tableOfTimetable); // Установка ширины полей
        tableOfTimetable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Для добавления полосы прокрутки влево-вправо

        timetablePanel.add(scrollTableOfTimetable, "push, grow, al center");
        //////////////propertiesAndTimetablePanel(end)////////////////////////////////////////////////////////////////////////////////////

        //////////////propertiesAndEditTimetablePanel(begin)//////////////////////////////////////////////////////////////////////////////////
        propertiesAndEditTimetablePanel.setBackground(new Color(255,255,255));
        propertiesAndEditTimetablePanel.setLayout(new MigLayout());

        editPropertiesTimetablePanel.setBackground(new Color(100, 100, 100));
        editPropertiesTimetablePanel.setLayout(new MigLayout());

        editorTimetablePanel.setBackground(new Color(255,255,255));
        editorTimetablePanel.setLayout(new MigLayout());

        propertiesAndEditTimetablePanel.add(editorTimetablePanel, "pushx, growx, h 600!, wrap");
        propertiesAndEditTimetablePanel.add(editPropertiesTimetablePanel, "growx, pushx, h 60!");

        applyEditTimetableButton.setEnabled(false);
        cancelEditTimetableButton.setEnabled(true);

        editPropertiesTimetablePanel.add(applyEditTimetableButton, "gaptop 10, split 2, al center");
        editPropertiesTimetablePanel.add(cancelEditTimetableButton, "gaptop 10");

        ettm = new editorTimetableTableModel(this, editorTableOfTimetable, applyEditTimetableButton, ttm.getColumnTypes(), ttm.getColumnNames(), ttm.getColumnRUSNames(), ttm.getDataArrayList());
        editorTableOfTimetable.setModel(ettm);
        editorTableOfTimetable.setColumnSelectionAllowed(false);
        editorTableOfTimetable.getTableHeader().setReorderingAllowed(false);
        ettm.setColumnStaticWidth(editorTableOfTimetable);
        editorTableOfTimetable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        editorTimetablePanel.add(scrollEditorTableOfTimetable, "push, grow, al center");
        //////////////propertiesAndEditTimetablePanel(end)////////////////////////////////////////////////////////////////////////////////////
////////////////timetablePanel(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////receptionPanel(begin)////////////////////////////////////////////////////////////////////////////////////////
        receptionPanel.setBackground(new Color(255,255,255));
        receptionPanel.setLayout(new MigLayout());

        receptionTablePanel.setBackground(new Color(255,255,255));
        receptionTablePanel.setLayout(new MigLayout());

        propertiesReceptionPanel.setBackground(new Color(100,100,100));
        propertiesReceptionPanel.setLayout(new MigLayout());

        receptionPanel.add(receptionTablePanel, "pushx, growx, h 500!, wrap");
        receptionPanel.add(propertiesReceptionPanel, "growx, pushx, h 160!");

        addToReceptionButton.setEnabled(false);
        cancelFromReceptionButton.setEnabled(false);

        chooseSpecializationLabel.setForeground(new Color(255,255,255));
        chooseDoctorLabel.setForeground(new Color(255,255,255));
        chooseDateLabel.setForeground(new Color(255,255,255));
        showHoursLabel.setForeground(new Color(255,255,255));
        chooseTimeLabel.setForeground(new Color(255,255,255));
        choosePatientLabel.setForeground(new Color(255,255,255));

        Vector<String> sp = new Vector();
        sp.addAll(jdbcConnection.getAllSpecialization());
        sp.add("______________________");
        chooseSpecializationJComboBox = new JComboBox(sp);
        //chooseSpecializationJComboBox.setFocusable(false);
        chooseSpecializationJComboBox.setSelectedIndex( sp.size() - 1 );
        //chooseDateJComboBox.setFocusable(false);
        //chooseTimeJComboBox.setFocusable(false);
        showHoursText.setEditable(false);

        propertiesReceptionPanel.add(chooseSpecializationLabel, "gaptop 10");
        propertiesReceptionPanel.add(chooseDoctorLabel, "gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(chooseDateLabel, "gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(showHoursLabel, "gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(chooseTimeLabel, "gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(choosePatientLabel, "gaptop 10, gapleft 40, wrap");
        propertiesReceptionPanel.add(chooseSpecializationJComboBox, "gaptop 10");
        propertiesReceptionPanel.add(scrollChooseDoctorJList, "w 200!, gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(chooseDateJComboBox, "w 100!, gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(showHoursText, "w 90!, gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(chooseTimeJComboBox, "w 80!, gaptop 10, gapleft 40");
        propertiesReceptionPanel.add(scrollChoosePatientJList, "w 300!, gaptop 10, gapleft 40, wrap");
        propertiesReceptionPanel.add(addToReceptionButton, "span 2 1, al center");
        propertiesReceptionPanel.add(cancelFromReceptionButton, "span 2 1, al center");

        this.rtm = new receptionTableModel(tableOfReception);
        tableOfReception.setModel(rtm);
        tableOfReception.setRowSorter(rowSorterReception); // Для сортировки по столбцам
        tableOfReception.setColumnSelectionAllowed(false);
        tableOfReception.getTableHeader().setReorderingAllowed(false);
        try { rtm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
        catch (SQLException e) { e.printStackTrace(); }
        rtm.setColumnStaticWidth(tableOfReception); // Установка ширины полей
        rowSorterReception = new TableRowSorter(rtm);
        tableOfReception.setRowSorter(rowSorterReception); // Для сортировки по столбцам
        tableOfReception.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Для добавления полосы прокрутки влево-вправо

        receptionTablePanel.add(scrollTableOfReception, "push, grow, al center");
/////////////////////receptionPanel(end)//////////////////////////////////////////////////////////////////////////////////////////


/////////////////cardPanel(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);
        cardPanel.add(startRegistryMainAdminWindow, "startPage");
        cardPanel.add(voidReferenceListOfDoctorsPanel, "voidListOfDoctorsPanel");
        cardPanel.add(voidReferenceListOfPatientsPanel, "voidListOfPatientsPanel");
        cardPanel.add(voidReferenceAdminTimetablePanel, "voidTimetablePanel");
        cardPanel.add(voidServiceAdminReceptionPanel, "voidReceptionPanel");
        cardPanel.add(referenceRegistryMainAdminWindow, "referencePage");
        cardPanel.add(serviceRegistryMainAdminWindow, "servicePage");
        cardPanel.add(newDataRegistryMainAdminWindow, "newDataPage");
        cardPanel.add(voidNewDataRegistryMainAdminWindow, "voidNewDataPanel");
        cl.show(cardPanel, "startPage");
/////////////////cardPanel(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////adminTablePanel(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        CardLayout cl2 = new CardLayout();
        adminTablePanel.setLayout(cl2);
        adminTablePanel.add(voidPanel, "voidPanel");
        adminTablePanel.add(scrollRegisterDoctor, "registerDoctor");
        adminTablePanel.add(scrollRegisterPatient, "registerPatient");
        adminTablePanel.add(scrollPropertyAndListOfDoctorsPanel, "listOfDoctorsPanel");
        adminTablePanel.add(scrollPropertyAndEditListOfDoctorsPanel, "editListOfDoctors");
        adminTablePanel.add(scrollPropertyAndListOfPatientsPanel, "listOfPatientsPanel");
        adminTablePanel.add(scrollPropertyAndEditListOfPatientsPanel, "editListOfPatients");
        adminTablePanel.add(scrollPropertiesAndTimetablePanel, "timetablePanel");
        adminTablePanel.add(scrollReceptionPanel, "receptionPanel");
        adminTablePanel.add(scrollPropertyAndEditTimetablePanel, "editTimetable");
        cl2.show(adminTablePanel, "voidPanel");
/////////////////adminTablePanel(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////registryMainWindowPanels(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////
        adminTablePanel.setBackground(new Color(255,255,0));
        adminTablePanel.setSize(200, (int) screenSize.getHeight());
        cardPanel.setBackground(new Color(255,0,0));
        cardPanel.setSize(1166, (int) screenSize.getHeight());

        mainAdminWindowPanel.setLayout(new MigLayout());
        mainAdminWindowPanel.add(cardPanel, "w 250!, pushy, growy");
        mainAdminWindowPanel.add(adminTablePanel, "pushx, pushy, growx, growy");
/////////////////registryMainWindowPanels(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////

        addRegistryMainWindow(scrollMainAdminWindowPanel, (int)(screenSize.getWidth()), (int)(screenSize.getHeight()));

/////////////ActionListeners(begin)//////////////////////////////////////////////////////////////////////////////////////////////////
        backToStartFromNewDataRegistryMainAdminWindow.addActionListener(actionEvent -> {
            cl.show(cardPanel, "startPage");
            cl2.show(adminTablePanel, "voidPanel");
        });
        backToStartFromServiceRegistryMainAdminWindow.addActionListener(actionEvent -> {
            cl.show(cardPanel, "startPage");
        });
        backToStartFromReferenceRegistryMainAdminWindow.addActionListener(actionEvent -> {
            cl.show(cardPanel, "startPage");
            cl2.show(adminTablePanel, "voidPanel");
        });
        backToReferenceFromVoidListOfPatientsPanelButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "referencePage");
            cl2.show(adminTablePanel, "voidPanel");
            tableOfPatients.clearSelection();
            editListOfPatientsButton.setEnabled(false);
            deleteRowsOfListOfPatientsButton.setEnabled(false);

            rowSorterPatient.setRowFilter(new myRowFilter("", 10).regexFilter("" ));
            searchListOfPatientsButton.setEnabled(true);
            throwOffSearchListOfPatientButton.setEnabled(false);
        });
        backToReferenceFromVoidListOfDoctorsPanelButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "referencePage");
            cl2.show(adminTablePanel, "voidPanel");
            tableOfDoctors.clearSelection();
            editorTableOfDoctors.clearSelection();
            editorTableOfDoctors.removeEditor();
            editListOfDoctorsButton.setEnabled(false);
            deleteRowsOfListOfDoctorsButton.setEnabled(false);

            rowSorter.setRowFilter(new myRowFilter("", 10).regexFilter("" ));
            searchListOfDoctorsButton.setEnabled(true);
            throwOffSearchListOfDoctorButton.setEnabled(false);
        });
        voidBackToStartFromNewDataRegistryMainAdminWindow.addActionListener(actionEvent -> {
            cl.show(cardPanel, "newDataPage");
            cl2.show(adminTablePanel, "voidPanel");

            secondNameDoctorText.setText("");
            firstNameDoctorText.setText("");
            thirdNameDoctorText.setText("");
            maleDoctorCheckBox.setSelected(false);
            femaleDoctorCheckBox.setSelected(false);
            birthdayDayDoctorTable.setDate(new Date());
            numberOfPlotDoctorComboBox.setSelectedIndex(0);
            specializationOfDoctorComboBox.setSelectedIndex(0);
            dateOfStartJobDoctorDate.setDate(new Date());
            newPasswordDoctorText.setText("");
            mailAddressDoctorText.setText("");
            cityOfDoctorText.setText("");
            streetOfDoctorText.setText("");
            buildingOfDoctorText.setText("");
            apartmentOfDoctorText.setText("");
            notDoctorData.setVisible(false);
        });
        backToReferenceFromVoidTimetablePanelButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "referencePage");
            cl2.show(adminTablePanel, "voidPanel");
            tableOfTimetable.clearSelection();
            editorTableOfTimetable.removeEditor();
            ttm.setCurrentStateID(0);
            editTimetableButton.setEnabled(false);
            searchTimetableButton.setEnabled(true);
            throwOffSearchTimetableButton.setEnabled(false);
            previousWeekButton.setEnabled(false);
            nextWeekButton.setEnabled(true);
            rowSorterTimetable.setRowFilter(new myRowFilter("", 10).regexFilter("" ));
        });
        backToServiceFromVoidReceptionPanelButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "servicePage");
            cl2.show(adminTablePanel, "voidPanel");
            tableOfReception.clearSelection();
            addToReceptionButton.setEnabled(false);
            cancelFromReceptionButton.setEnabled(false);
            rowSorterReception.setRowFilter(new myRowFilter("", 9).regexFilter("" ));

            chooseSpecializationJComboBox.setSelectedIndex(sp.size() - 1);

            chooseDoctorJListModel.removeAllElements();
            chooseDoctorJList.setModel(chooseDoctorJListModel);

            chooseDateJComboBoxModel.removeAllElements();
            chooseDateJComboBox.setModel(chooseDateJComboBoxModel);

            showHoursText.setText("");

            chooseTimeJComboBoxModel.removeAllElements();
            chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);

            choosePatientJListModel.removeAllElements();
            choosePatientJList.setModel(choosePatientJListModel);
        });

        startAdminNewDataButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "newDataPage");
        });
        startAdminServiceButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "servicePage");
        });
        startAdminReferenceButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "referencePage");
        });

        serviceAdminDoctorVisitButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "voidReceptionPanel");
            cl2.show(adminTablePanel, "receptionPanel");
        });

        newDataAdminAddDoctorButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "voidNewDataPanel");
            cl2.show(adminTablePanel, "registerDoctor");
        });
        newDataAdminAddPatientButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "voidNewDataPanel");
            cl2.show(adminTablePanel, "registerPatient");
        });

        registerDoctorButton.addActionListener(actionEvent -> {
            registerNewDoctor();
            try { dtm.updateData(jdbcConnection); } // Добавление в модель таблицы данных из БД (обновление списка)
            catch (SQLException e) { e.printStackTrace(); }
            try { ttm.updateData(jdbcConnection); } // Добавление в модель таблицы данных из БД (обновление списка)
            catch (SQLException e) { e.printStackTrace(); }
        });
        registerPatientButton.addActionListener(actionEvent -> {
            registerNewPatient();
            try { ptm.updateData(jdbcConnection); } // Добавление в модель таблицы данных из БД (обновление списка)
            catch (SQLException e) { e.printStackTrace(); }
        });

        referenceAdminListPatientButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "voidListOfPatientsPanel");
            cl2.show(adminTablePanel, "listOfPatientsPanel");
        });
        referenceAdminListDoctorButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "voidListOfDoctorsPanel");
            cl2.show(adminTablePanel, "listOfDoctorsPanel");
        });
        referenceAdminTimetableButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "voidTimetablePanel");
            cl2.show(adminTablePanel, "timetablePanel");
        });

        maleDoctorCheckBox.addActionListener(actionEvent -> {
            if(maleDoctorCheckBox.isSelected())
            {
                femaleDoctorCheckBox.setSelected(false);
            }
        });
        femaleDoctorCheckBox.addActionListener(actionEvent -> {
            if(femaleDoctorCheckBox.isSelected())
            {
                maleDoctorCheckBox.setSelected(false);
            }
        });
        malePatientCheckBox.addActionListener(actionEvent -> {
            if(malePatientCheckBox.isSelected())
            {
                femalePatientCheckBox.setSelected(false);
            }
        });
        femalePatientCheckBox.addActionListener(actionEvent -> {
            if(femalePatientCheckBox.isSelected())
            {
                malePatientCheckBox.setSelected(false);
            }
        });

        editListOfDoctorsButton.addActionListener(actionEvent -> {
            cl2.show(adminTablePanel, "editListOfDoctors");
            edtm.addData(tableOfDoctors.getSelectedRows(), tableOfDoctors);
            tableOfDoctors.clearSelection();
            editListOfDoctorsButton.setEnabled(false);
            deleteRowsOfListOfDoctorsButton.setEnabled(false);
        });
        deleteRowsOfListOfDoctorsButton.addActionListener(actionEvent -> {
            ttm.deleteData( dtm.deleteData(tableOfDoctors.getSelectedRows(), tableOfDoctors, jdbcConnection)); // Удаление сначала произойдёт в dtm, потом в ttm
            tableOfDoctors.clearSelection();
            editListOfDoctorsButton.setEnabled(false);
            deleteRowsOfListOfDoctorsButton.setEnabled(false);
        });
        applyEditListOfDoctorsButton.addActionListener(actionEvent -> {
            edtm.editData();
            applyEditListOfDoctorsButton.setEnabled(false);
            try { dtm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
            catch (SQLException e) { e.printStackTrace(); }
            try { ttm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
            catch (SQLException e) { e.printStackTrace(); }
        });
        cancelEditListOfDoctorsButton.addActionListener(actionEvent -> {
            cl2.show(adminTablePanel, "listOfDoctorsPanel");
            applyEditListOfDoctorsButton.setEnabled(false);
            editListOfDoctorsButton.setEnabled(false);
            deleteRowsOfListOfDoctorsButton.setEnabled(false);
            tableOfDoctors.clearSelection();
            editorTableOfDoctors.removeEditor();
        });
        searchListOfDoctorsButton.addActionListener(actionEvent -> {
            new searchDoctors(this, throwOffSearchListOfDoctorButton, searchListOfDoctorsButton, rowSorter, dtm);
        });
        throwOffSearchListOfDoctorButton.addActionListener(actionEvent -> {
            rowSorter.setRowFilter(new myRowFilter("", 10).regexFilter("" ));
            searchListOfDoctorsButton.setEnabled(true);
            throwOffSearchListOfDoctorButton.setEnabled(false);
            tableOfDoctors.clearSelection();
            editListOfDoctorsButton.setEnabled(false);
            deleteRowsOfListOfDoctorsButton.setEnabled(false);
        });

        searchTimetableButton.addActionListener(actionEvent -> {
            new searchTimetable(this, throwOffSearchTimetableButton, searchTimetableButton, rowSorterTimetable, ttm);
        });
        throwOffSearchTimetableButton.addActionListener(actionEvent -> {
            rowSorterTimetable.setRowFilter(new myRowFilter("", 10).regexFilter("" )); // Пока rowFilter общий
            searchTimetableButton.setEnabled(true);
            throwOffSearchTimetableButton.setEnabled(false);
            tableOfTimetable.clearSelection();
            editTimetableButton.setEnabled(false);
        });
        previousWeekButton.addActionListener(actionEvent -> {
            ttm.setCurrentStateID(ttm.getCurrentStateID() - 1);
            ettm.setColumnRUSNames(ttm.getColumnRUSNames());

            if(ttm.getCurrentStateID() == 0)
            {
                previousWeekButton.setEnabled(false);
                nextWeekButton.setEnabled(true);
                ettm.setCurrentStateID(ttm.getCurrentStateID());
            }
            else if(ttm.getCurrentStateID() == 1)
            {
                previousWeekButton.setEnabled(true);
                nextWeekButton.setEnabled(true);
                ettm.setCurrentStateID(ttm.getCurrentStateID());
            }
            else if(ttm.getCurrentStateID() == 2)
            {
                previousWeekButton.setEnabled(true);
                nextWeekButton.setEnabled(false);
                ettm.setCurrentStateID(ttm.getCurrentStateID());
            }
        });
        nextWeekButton.addActionListener(actionEvent -> {
            ttm.setCurrentStateID(ttm.getCurrentStateID() + 1);
            ettm.setColumnRUSNames(ttm.getColumnRUSNames());

            if(ttm.getCurrentStateID() == 0)
            {
                previousWeekButton.setEnabled(false);
                nextWeekButton.setEnabled(true);
                ettm.setCurrentStateID(ttm.getCurrentStateID());
            }
            else if(ttm.getCurrentStateID() == 1)
            {
                previousWeekButton.setEnabled(true);
                nextWeekButton.setEnabled(true);
                ettm.setCurrentStateID(ttm.getCurrentStateID());
            }
            else if(ttm.getCurrentStateID() == 2)
            {
                previousWeekButton.setEnabled(true);
                nextWeekButton.setEnabled(false);
                ettm.setCurrentStateID(ttm.getCurrentStateID());
            }
        });
        editTimetableButton.addActionListener(actionEvent -> {
            cl2.show(adminTablePanel, "editTimetable");
            ettm.addData(tableOfTimetable.getSelectedRows(), tableOfTimetable);
            tableOfTimetable.clearSelection();
            editTimetableButton.setEnabled(false);
        });
        applyEditTimetableButton.addActionListener(actionEvent -> {
            ettm.editData();
            applyEditTimetableButton.setEnabled(false);
            try { ttm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
            catch (SQLException e) { e.printStackTrace(); }
        });
        cancelEditTimetableButton.addActionListener(actionEvent -> {
            cl2.show(adminTablePanel, "timetablePanel");
            editorTableOfTimetable.removeEditor();
            ttm.setCurrentStateID(0);
            nextWeekButton.setEnabled(true);
            previousWeekButton.setEnabled(false);
        });

        searchListOfPatientsButton.addActionListener(actionEvent -> {
            new searchPatients(this, throwOffSearchListOfPatientButton, searchListOfPatientsButton, rowSorterPatient, ptm, tableOfPatients);
        });
        throwOffSearchListOfPatientButton.addActionListener(actionEvent -> {
            rowSorterPatient.setRowFilter(new myRowFilter("", 14).regexFilter("" ));
            searchListOfPatientsButton.setEnabled(true);
            throwOffSearchListOfPatientButton.setEnabled(false);
            tableOfPatients.clearSelection();
            editListOfPatientsButton.setEnabled(false);
            deleteRowsOfListOfPatientsButton.setEnabled(false);
        });
        deleteRowsOfListOfPatientsButton.addActionListener(actionEvent -> {
            ptm.deleteData(tableOfPatients.getSelectedRows(), tableOfPatients, jdbcConnection); // Удаление сначала произойдёт в dtm, потом в ttm
            tableOfPatients.clearSelection();
            editListOfPatientsButton.setEnabled(false);
            deleteRowsOfListOfPatientsButton.setEnabled(false);
        });
        editListOfPatientsButton.addActionListener(actionEvent -> {
            cl2.show(adminTablePanel, "editListOfPatients");
            eptm.addData(tableOfPatients.getSelectedRows(), tableOfPatients);
            tableOfPatients.clearSelection();
            editListOfPatientsButton.setEnabled(false);
            deleteRowsOfListOfPatientsButton.setEnabled(false);
        });
        applyEditListOfPatientsButton.addActionListener(actionEvent -> {
            eptm.editData();
            applyEditListOfPatientsButton.setEnabled(false);
            try { ptm.addData(jdbcConnection); } // Добавление в модель таблицы данных из БД
            catch (SQLException e) { e.printStackTrace(); }
        });
        cancelEditListOfPatientsButton.addActionListener(actionEvent -> {
            cl2.show(adminTablePanel, "listOfPatientsPanel");
            applyEditListOfPatientsButton.setEnabled(false);
            editListOfPatientsButton.setEnabled(false);
            deleteRowsOfListOfPatientsButton.setEnabled(false);
            tableOfPatients.clearSelection();
            editorTableOfPatients.removeEditor();
        });

        chooseSpecializationJComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                Vector<String> temp = new Vector();
                if(e.getStateChange() != ItemEvent.DESELECTED)
                {
                    for (int i = 0; i < dtm.getDataArrayList().size(); i++) {
                        if (((ArrayList) dtm.getDataArrayList().get(i)).get(6).equals(e.getItem().toString())) {
                            temp.add(((ArrayList) dtm.getDataArrayList().get(i)).get(1).toString() + " " +
                                    ((ArrayList) dtm.getDataArrayList().get(i)).get(2).toString() + " " +
                                    ((ArrayList) dtm.getDataArrayList().get(i)).get(3).toString());
                        }
                    }
                    if (!temp.isEmpty()) {
                        chooseDoctorJListModel.removeAllElements();
                        chooseDoctorJListModel.addAll(temp /*jdbcConnection.getNeedDoctors( e.getItem().toString() )*/);
                        chooseDoctorJList.setModel(chooseDoctorJListModel);
                        showHoursText.setText("");
                        chooseDateJComboBoxModel.removeAllElements();
                        chooseDateJComboBox.setModel(chooseDateJComboBoxModel);
                        chooseTimeJComboBoxModel.removeAllElements();
                        chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                        if(!choosePatientJListModel.isEmpty())
                        {
                            choosePatientJListModel.removeAllElements();
                            choosePatientJList.setModel(chooseDoctorJListModel);
                        }
                    } else {
                        chooseDoctorJListModel.removeAllElements();
                        chooseDoctorJList.setModel(chooseDoctorJListModel);
                        showHoursText.removeAll();
                    }
                }
                else
                {
                    for (int i = 0; i < dtm.getDataArrayList().size(); i++) {
                        if (((ArrayList) dtm.getDataArrayList().get(i)).get(6).equals(e.getItem().toString())) {
                            temp.add(((ArrayList) dtm.getDataArrayList().get(i)).get(1).toString() + " " +
                                    ((ArrayList) dtm.getDataArrayList().get(i)).get(2).toString() + " " +
                                    ((ArrayList) dtm.getDataArrayList().get(i)).get(3).toString());
                        }
                    }
                    chooseDoctorJListModel.removeAllElements();
                    chooseDoctorJListModel.addAll(temp /*jdbcConnection.getNeedDoctors( e.getItem().toString() )*/);
                    chooseDoctorJList.setModel(chooseDoctorJListModel);
                    showHoursText.setText("");
                    chooseDateJComboBoxModel.removeAllElements();
                    chooseDateJComboBox.setModel(chooseDateJComboBoxModel);
                    chooseTimeJComboBoxModel.removeAllElements();
                    chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                    if(!choosePatientJListModel.isEmpty())
                    {
                        choosePatientJListModel.removeAllElements();
                        choosePatientJList.setModel(chooseDoctorJListModel);
                    }
                    addToReceptionButton.setEnabled(false);
                }
            }
        });
        chooseDoctorJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                Vector<String> temp = new Vector();
                if( e.getValueIsAdjusting() && !chooseDoctorJList.isSelectionEmpty() )
                {
                    for (int i = 0; i < ttm.getCurrentState().size(); i++)
                    {
                        temp.add(ttm.getWeeks(i).getPnDate().toString());
                        temp.add(ttm.getWeeks(i).getVtDate().toString());
                        temp.add(ttm.getWeeks(i).getSrDate().toString());
                        temp.add(ttm.getWeeks(i).getChtDate().toString());
                        temp.add(ttm.getWeeks(i).getPtDate().toString());
                        temp.add(ttm.getWeeks(i).getSbDate().toString());
                    }
                    showHoursText.setText("");
                    chooseDateJComboBoxModel.removeAllElements();
                    chooseDateJComboBox.setModel(chooseDateJComboBoxModel);
                    chooseDateJComboBoxModel.addAll(temp /*jdbcConnection.getNeedDoctors( e.getItem().toString() )*/);
                    chooseDateJComboBox.setModel(chooseDateJComboBoxModel);
                    chooseTimeJComboBoxModel.removeAllElements();
                    chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                    if(!choosePatientJListModel.isEmpty()) {
                        choosePatientJListModel.removeAllElements();
                        choosePatientJList.setModel(chooseDoctorJListModel);
                    }
                    addToReceptionButton.setEnabled(false);
                }
                else if( chooseDoctorJList.isSelectionEmpty() )
                {
                    chooseDateJComboBoxModel.removeAllElements();
                    chooseDateJComboBox.setModel(chooseDateJComboBoxModel);
                    showHoursText.removeAll();
                }
            }
        });
        chooseDateJComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() != ItemEvent.DESELECTED)
                {
                    String temp = "Не принимает";
                    boolean be = false;
                    for (int i = 0; i < ttm.getDataArrayList().size(); i++) {
                        if (((ArrayList) ttm.getDataArrayList().get(i)).get(0).toString().equals(chooseDoctorJList.getSelectedValue())) {
                            for (int j = 3; j < 21; j++) {
                                String[] edit = new Scanner(((ArrayList) ttm.getDataArrayList().get(i)).get(j).toString()).nextLine().split(" ");
                                String chooseDate = chooseDateJComboBox.getSelectedItem().toString().replace(" ", "");
                                if (chooseDate.equals(edit[edit.length - 3]) && !edit[2].equals("--:--/--:--")) {
                                    temp = edit[2];
                                    be = true;
                                    break;
                                }
                            }
                        }
                        if (be) break;
                    }

                    if (chooseDateJComboBoxModel.getSize() != 0 && !temp.isEmpty()) {
                        showHoursText.removeAll();
                        showHoursText.setText(temp);

                        Vector<String> temp2 = new Vector();
                        if (!temp.equals("Не принимает")) {
                            temp2 = addTimeCorrectly(temp2);

                            if (!temp2.isEmpty()) {
                                chooseTimeJComboBoxModel.removeAllElements();
                                chooseTimeJComboBoxModel.addAll(temp2);
                                chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                            } else {
                                temp2.add("Нет времени");
                                chooseTimeJComboBoxModel.removeAllElements();
                                chooseTimeJComboBoxModel.addAll(temp2);
                                chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                                choosePatientJListModel.removeAllElements();
                                choosePatientJList.setModel(choosePatientJListModel);
                                addToReceptionButton.setEnabled(false);
                            }
                        } else {
                            chooseTimeJComboBoxModel.removeAllElements();
                            chooseTimeJComboBoxModel.addElement("Нет времени");
                            choosePatientJListModel.removeAllElements();
                            choosePatientJList.setModel(choosePatientJListModel);
                            chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                            addToReceptionButton.setEnabled(false);
                        }
                    } else {
                        chooseTimeJComboBoxModel.removeAllElements();
                        chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
                        addToReceptionButton.setEnabled(false);
                        showHoursText.removeAll();
                    }
                }
                else
                {

                }
            }
        });
        chooseTimeJComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if(chooseTimeJComboBoxModel.getSize() != 0)
                {
                    choosePatientJListModel.removeAllElements();
                    choosePatientJListModel.addAll( jdbcConnection.getNeedPatients() );
                    choosePatientJList.setModel(choosePatientJListModel);
                }
                else
                {
                    addToReceptionButton.setEnabled(false);
                    choosePatientJListModel.removeAllElements();
                    choosePatientJList.setModel(choosePatientJListModel);
                    showHoursText.removeAll();
                }
            }
        });
        choosePatientJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if( e.getValueIsAdjusting() && !choosePatientJList.isSelectionEmpty() )
                {
                    addToReceptionButton.setEnabled(true);
                }
                else if(choosePatientJList.isSelectionEmpty())
                {

                }
                else if( chooseTimeJComboBoxModel.getSize() == 0 || chooseTimeJComboBoxModel.getElementAt(0).equals("Нет времени") )
                {
                    choosePatientJListModel.removeAllElements();
                    choosePatientJList.setModel(choosePatientJListModel);
                    showHoursText.removeAll();
                    addToReceptionButton.setEnabled(false);
                }
            }
        });
        addToReceptionButton.addActionListener(actionEvent -> {
            rtm.writeInBD(dtm.getDataArrayList(), chooseDoctorJList.getSelectedValue(), choosePatientJList.getSelectedValue(), chooseDateJComboBox.getSelectedItem().toString(), chooseTimeJComboBox.getSelectedItem().toString());
            tableOfReception.clearSelection();
            try { rtm.addData(jdbcConnection); }
            catch (SQLException e) { e.printStackTrace(); }
            rtm.fireTableDataChanged();
            addToReceptionButton.setEnabled(false);
            cancelFromReceptionButton.setEnabled(false);

            chooseTimeJComboBoxModel.removeElementAt( chooseTimeJComboBox.getSelectedIndex() );
            chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
        });
        cancelFromReceptionButton.addActionListener(actionEvent -> {
            rtm.deleteData(tableOfReception.getSelectedRows(), tableOfReception, jdbcConnection);

            if(chooseDateJComboBoxModel.getSize() != 0)
            {
                Vector<String> temp2 = new Vector();
                temp2 = addTimeCorrectly(temp2);
                chooseTimeJComboBoxModel.removeAllElements();
                chooseTimeJComboBoxModel.addAll(temp2);
                chooseTimeJComboBox.setModel(chooseTimeJComboBoxModel);
            }

            tableOfReception.clearSelection();
            cancelFromReceptionButton.setEnabled(false);
        });

        tableOfTimetable.setSelectionModel(new DefaultListSelectionModel() // Модель выбора для удобного интерфейса
        {
            @Override
            public void setSelectionInterval(int startIndex, int endIndex) {
                if (startIndex == endIndex) // Если выбран один элемент
                {
                    if (getMinSelectionIndex() != getMaxSelectionIndex())
                    {
                        clearSelection(); // Убрать выделение
                        editTimetableButton.setEnabled(false);
                    }
                    if (isSelectedIndex(startIndex))
                    {
                        clearSelection(); // Убрать выделение
                        editTimetableButton.setEnabled(false);
                    }
                    else
                    {
                        super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                        editTimetableButton.setEnabled(true);
                    }
                }
                // Если выбраны многие элементы
                else {
                    super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                    editTimetableButton.setEnabled(true);
                }
            }
        });
        tableOfDoctors.setSelectionModel(new DefaultListSelectionModel() // Модель выбора для удобного интерфейса
        {
            @Override
            public void setSelectionInterval(int startIndex, int endIndex) {
                if (startIndex == endIndex) // Если выбран один элемент
                {
                    if (getMinSelectionIndex() != getMaxSelectionIndex())
                    {
                        clearSelection(); // Убрать выделение
                        editListOfDoctorsButton.setEnabled(false);
                        deleteRowsOfListOfDoctorsButton.setEnabled(false);
                    }
                    if (isSelectedIndex(startIndex))
                    {
                        clearSelection(); // Убрать выделение
                        editListOfDoctorsButton.setEnabled(false);
                        deleteRowsOfListOfDoctorsButton.setEnabled(false);
                    }
                    else
                        {
                            super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                            editListOfDoctorsButton.setEnabled(true);
                            deleteRowsOfListOfDoctorsButton.setEnabled(true);
                        }
                }
                // Если выбраны многие элементы
                else {
                        super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                        editListOfDoctorsButton.setEnabled(true);
                        deleteRowsOfListOfDoctorsButton.setEnabled(true);
                     }
            }
        });
        tableOfPatients.setSelectionModel(new DefaultListSelectionModel() // Модель выбора для удобного интерфейса
        {
            @Override
            public void setSelectionInterval(int startIndex, int endIndex) {
                if (startIndex == endIndex) // Если выбран один элемент
                {
                    if (getMinSelectionIndex() != getMaxSelectionIndex())
                    {
                        clearSelection(); // Убрать выделение
                        editListOfPatientsButton.setEnabled(false);
                        deleteRowsOfListOfPatientsButton.setEnabled(false);
                    }
                    if (isSelectedIndex(startIndex))
                    {
                        clearSelection(); // Убрать выделение
                        editListOfPatientsButton.setEnabled(false);
                        deleteRowsOfListOfPatientsButton.setEnabled(false);
                    }
                    else
                    {
                        super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                        editListOfPatientsButton.setEnabled(true);
                        deleteRowsOfListOfPatientsButton.setEnabled(true);
                    }
                }
                // Если выбраны многие элементы
                else {
                    super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                    editListOfPatientsButton.setEnabled(true);
                    deleteRowsOfListOfPatientsButton.setEnabled(true);
                }
            }
        });
        tableOfReception.setSelectionModel(new DefaultListSelectionModel() // Модель выбора для удобного интерфейса
        {
            @Override
            public void setSelectionInterval(int startIndex, int endIndex) {
                if (startIndex == endIndex) // Если выбран один элемент
                {
                    if (getMinSelectionIndex() != getMaxSelectionIndex())
                    {
                        clearSelection(); // Убрать выделение
                        cancelFromReceptionButton.setEnabled(false);
                    }
                    if (isSelectedIndex(startIndex))
                    {
                        clearSelection(); // Убрать выделение
                        cancelFromReceptionButton.setEnabled(false);
                    }
                    else
                    {
                        super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                        cancelFromReceptionButton.setEnabled(true);
                    }
                }
                // Если выбраны многие элементы
                else {
                    super.setSelectionInterval(startIndex, endIndex); // Добавить выделение
                    cancelFromReceptionButton.setEnabled(true);
                }
            }
        });
/////////////ActionListeners(end)////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private Vector<String> addTimeCorrectly(Vector<String> temp2)
    {
        Statement statement = null;
        ResultSet resSet = null;
        try
        {
            for (int i = 0; i < ttm.getDataArrayList().size(); i++)
            {
                if (((ArrayList) ttm.getDataArrayList().get(i)).get(0).toString().equals(chooseDoctorJList.getSelectedValue()))
                {
                    statement = jdbcConnection.getDbConnecion().createStatement();
                    resSet = jdbcConnection.getReceptionForCheck((Integer)((ArrayList) ttm.getDataArrayList().get(i)).get(21), chooseDateJComboBox.getSelectedItem().toString(), statement);

                    String[] edit = new Scanner(showHoursText.getText()).nextLine().split("/");
                    String[] editBegin = new Scanner(edit[0]).nextLine().split(":");
                    String[] editEnd = new Scanner(edit[1]).nextLine().split(":");
                    int b1 = Integer.parseInt(editBegin[0]); int b2 = Integer.parseInt(editBegin[1]);
                    int e1 = Integer.parseInt(editEnd[0]); int e2 = Integer.parseInt(editEnd[1]);

                    int minutes = b2;
                    for(int hours = b1; hours <= e1; hours++)
                    {
                        if(hours > b1) minutes = 0;
                        for(; minutes < 60; minutes += 10)
                        {
                            if(hours == e1)
                            {
                                if(minutes <= e2)
                                {
                                    if (hours - 10 >= 0 && minutes > 0) temp2.add(String.valueOf(hours) + ":" + String.valueOf(minutes));
                                    else if (hours - 10 >= 0 && minutes == 0) temp2.add(String.valueOf(hours) + ":" + String.valueOf(minutes) + "0");
                                    else if (hours - 10 < 0 && minutes > 0) temp2.add("0" + String.valueOf(hours) + ":" + String.valueOf(minutes));
                                    else if (hours - 10 < 0 && minutes == 0) temp2.add("0" + String.valueOf(hours) + ":" + String.valueOf(minutes) + "0");
                                }
                            }
                            else
                            {
                                if (hours - 10 >= 0 && minutes > 0) temp2.add(String.valueOf(hours) + ":" + String.valueOf(minutes));
                                else if (hours - 10 >= 0 && minutes == 0) temp2.add(String.valueOf(hours) + ":" + String.valueOf(minutes) + "0");
                                else if (hours - 10 < 0 && minutes > 0) temp2.add("0" + String.valueOf(hours) + ":" + String.valueOf(minutes));
                                else if (hours - 10 < 0 && minutes == 0) temp2.add("0" + String.valueOf(hours) + ":" + String.valueOf(minutes) + "0");
                            }
                        }
                    }

                    while(resSet.next())
                    {
                        for (int ii = 0; ii < temp2.size(); ii++)
                        {
                            if (resSet.getString(CONST.RECEPTION_TIME_OF_RECEPTION).equals(temp2.get(ii)))
                            {
                                temp2.removeElementAt(ii);
                                break;
                            }
                        }
                    }

                    if(!resSet.next())
                    {
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        finally
        {
            try {
                jdbcConnection.dbDisconnectionSt(statement);
                jdbcConnection.dbDisconnectionResSet(resSet);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return temp2;
    }

    private void registerNewPatient()
    {
        if(!secondNamePatientText.getText().equals("") && !firstNamePatientText.getText().equals("") &&
                !thirdNamePatientText.getText().equals("") && (malePatientCheckBox.isSelected() || femalePatientCheckBox.isSelected()) &&
                dateOfCreatePatientCard.getDate().before(new Date()) && !numberOfInsurancePolicyText.getText().equals("") &&
                !numberOfPhonePatientText.getText().equals("") && !numberOfPassportPatientText.getText().equals("") &&
                !cityOfPatientText.getText().equals("") && !streetOfPatientText.isSelectionEmpty() &&
                !buildingOfPatientText.getText().equals("") && !apartmentOfPatientText.getText().equals("") &&
                birthdayDayPatientTable.getDate().before(new Date())) // Если все регистрационные поля заполнены
        {
            notPatientData.setVisible(false);

            java.sql.Date date = new java.sql.Date(birthdayDayPatientTable.getDate().getTime());
            java.sql.Date date2 = new java.sql.Date(dateOfCreatePatientCard.getDate().getTime());

            String male_feemale = "";
            if (malePatientCheckBox.isSelected()) male_feemale = "Мужской";
            else male_feemale = "Женский";

            Patient patient = new Patient(secondNamePatientText.getText().trim(), firstNamePatientText.getText().trim(), thirdNamePatientText.getText().trim(),
                                            male_feemale, date, date2, numberOfPassportPatientText.getText().trim(), numberOfPhonePatientText.getText().trim(),
                                            numberOfInsurancePolicyText.getText().trim(), cityOfPatientText.getText().trim(), streetOfPatientText.getSelectedValue(),
                                            buildingOfPatientText.getText().trim(), apartmentOfPatientText.getText().trim());

            jdbcConnection.registerPatient(patient);

            secondNamePatientText.setText("");
            firstNamePatientText.setText("");
            thirdNamePatientText.setText("");
            malePatientCheckBox.setSelected(false);
            femalePatientCheckBox.setSelected(false);
            dateOfCreatePatientCard.setDate(new Date());
            numberOfInsurancePolicyText.setText("");
            numberOfPhonePatientText.setText("");
            numberOfPassportPatientText.setText("");
            cityOfPatientText.setText("");
            streetOfPatientText.setSelectedIndex(0);
            buildingOfPatientText.setText("");
            apartmentOfPatientText.setText("");
            birthdayDayPatientTable.setDate(new Date());

            new infoMessage(this, "Новый пациент зарегистрирован в системе", "УВЕДОМЛЕНИЕ");
        }
        else
        {
            notPatientData.setVisible(true);
        }
    }

    private void registerNewDoctor()
    {
        if(!secondNameDoctorText.getText().equals("") && !firstNameDoctorText.getText().equals("") &&
                !thirdNameDoctorText.getText().equals("") && (maleDoctorCheckBox.isSelected() || femaleDoctorCheckBox.isSelected()) &&
                dateOfStartJobDoctorDate.getDate().before(new Date()) &&
                !newPasswordDoctorText.getText().equals("") && !mailAddressDoctorText.getText().equals("") &&
                !cityOfDoctorText.getText().equals("") && !streetOfDoctorText.getText().equals("") &&
                !buildingOfDoctorText.getText().equals("") && !apartmentOfDoctorText.getText().equals("") &&
                birthdayDayDoctorTable.getDate().before(new Date())) // Если все регистрационные поля заполнены
        {
            notDoctorData.setVisible(false);

            java.sql.Date date = new java.sql.Date(birthdayDayDoctorTable.getDate().getTime());

            java.sql.Date date2 = new java.sql.Date(dateOfStartJobDoctorDate.getDate().getTime());

            String male_feemale = "";
            if (maleDoctorCheckBox.isSelected()) male_feemale = "Мужской";
            else male_feemale = "Женский";

            Doctor doctor = new Doctor(secondNameDoctorText.getText().trim(), firstNameDoctorText.getText().trim(),
                                       thirdNameDoctorText.getText().trim(), male_feemale, date,
                                       specializationOfDoctorComboBox.getItemAt(specializationOfDoctorComboBox.getSelectedIndex()),
                                       numberOfPlotDoctorComboBox.getItemAt(numberOfPlotDoctorComboBox.getSelectedIndex()),
                                       date2, newPasswordDoctorText.getText(), mailAddressDoctorText.getText().trim(),
                                       cityOfDoctorText.getText().trim(), streetOfDoctorText.getText().trim(),
                                       buildingOfDoctorText.getText().trim(), apartmentOfDoctorText.getText().trim());

            jdbcConnection.registerDoctor(doctor);

            secondNameDoctorText.setText("");
            firstNameDoctorText.setText("");
            thirdNameDoctorText.setText("");
            maleDoctorCheckBox.setSelected(false);
            femaleDoctorCheckBox.setSelected(false);
            birthdayDayDoctorTable.setDate(new Date());
            numberOfPlotDoctorComboBox.setSelectedIndex(0);
            specializationOfDoctorComboBox.setSelectedIndex(0);
            dateOfStartJobDoctorDate.setDate(new Date());
            newPasswordDoctorText.setText("");
            mailAddressDoctorText.setText("");
            cityOfDoctorText.setText("");
            streetOfDoctorText.setText("");
            buildingOfDoctorText.setText("");
            apartmentOfDoctorText.setText("");
            notDoctorData.setVisible(false);

            new infoMessage(this, "Новый врач зарегистрирован в системе", "УВЕДОМЛЕНИЕ");
        }
        else
        {
            notDoctorData.setVisible(true);
        }
    }

    private void addRegistryMainWindow(JScrollPane _panel, int width, int height)
    {
        this.setBounds(0, 0, width, height); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // При нажатии на X произойдёт выход из приложения
        this.setFocusable(true); // Фокус на окне
        this.setVisible(true); // Делаем окно видимым
        this.setResizable(true); // Запрет на изменение размеров окна - нет
        this.setExtendedState(this.MAXIMIZED_BOTH); // Растяжение на весь экран
        this.setTitle("Регистратура"); // Заголовок окна

        this.add(_panel);
    }

    private  void refreshRegistryMainWindow(int width, int height)
    {
        this.setBounds(0, 0, width, height); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setLocationRelativeTo(null); // Расположение окна ровно по центру
    }
}
