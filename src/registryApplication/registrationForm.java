package registryApplication;

import Connector.JDBCConnection;
import Users.Admin;
import Users.Doctor;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class registrationForm extends JFrame implements Runnable
{
//    private String date = "";

    private Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); // Создаём переменную, в которой хранится размер экрана

    private JPanel cardPanel = new JPanel();


    private JPanel startRegistrationForm = new JPanel();
    private JLabel welcomeLabel = new JLabel("Добро пожаловать!");
    private JLabel choseYourRollLabel = new JLabel("В качестве кого работать в системе: ");
    private String doctorString = "Врач", adminString = "Администратор";
    private String[] rollMassive = {doctorString, adminString};
    private JComboBox doctor_adminComboBox = new JComboBox(rollMassive);
    private JButton registrationButton = new JButton("Регистрация");
    private JButton enterButton = new JButton("Вход");


    private JPanel enterRegistrationFormAdmin = new JPanel();
    private JPanel enterRegistrationFormDoctor = new JPanel();
    private JLabel enterDataAdminLabel = new JLabel("Введите данные");
    private JLabel enterDataDoctorLabel = new JLabel("Введите данные");
    private JLabel userNameAdminLabel = new JLabel("Логин: ");
    private JLabel userNameDoctorLabel = new JLabel("Логин: ");
    private JLabel passwordAdminLabel = new JLabel("Пароль: ");
    private JLabel passwordDoctorLabel = new JLabel("Пароль: ");
    private JTextField userNameAdminText = new JTextField();
    private JTextField userNameDoctorText = new JTextField();
    private JTextField passwordAdminText = new JTextField();
    private JTextField passwordDoctorText = new JTextField();
    private JLabel adminNotDataLabel = new JLabel("Введите все данные!!!");
    private JLabel doctorNotDataLabel = new JLabel("Введите все данные!!!");
    private JButton loginAdminButton = new JButton("Войти");
    private JButton loginDoctorButton = new JButton("Войти");


    private JPanel regRegistrationFormAdmin = new JPanel();
    private JLabel secondNameAdminLabel = new JLabel("Фамилия: ");
    private JLabel firstNameAdminLabel = new JLabel("Имя: ");
    private JLabel thirdNameAdminLabel = new JLabel("Отчество: ");
    private JLabel sexOfAdminLabel = new JLabel("Пол: ");
    private JLabel maleSexOfAdminLabel = new JLabel("Муж");
    private JLabel femaleSexOfAdminLabel = new JLabel("Жен");
    private JLabel birthdayDayAdminLabel = new JLabel("Дата рождения: ");
    private JLabel newPasswordAdminLabel = new JLabel("Придумайте пароль: ");
    private JLabel newPasswordAgainAdminLabel = new JLabel("Повторите пароль: ");
    private JLabel mailAddressAdminLabel = new JLabel("Введите свою почту: ");
    private JLabel addressOfLiveAdminLabel = new JLabel("Адрес проживания: ");
    private JLabel cityOfAdminLabel = new JLabel("Город: ");
    private JLabel streetOfAdminLabel = new JLabel("Улица: ");
    private JLabel buildingOfAdminLabel = new JLabel("Дом: ");
    private JLabel apartmentOfAdminLabel = new JLabel("Квартира: ");
    private JTextField secondNameAdminText = new JTextField();
    private JTextField firstNameAdminText = new JTextField();
    private JTextField thirdNameAdminText = new JTextField();
    private JCheckBox maleAdminCheckBox = new JCheckBox();
    private JCheckBox femaleAdminCheckBox = new JCheckBox();
    private JDateChooser birthdayDayAdminTable = new JDateChooser();
    private JTextField newPasswordAdminText = new JTextField();
    private JTextField newPasswordAgainAdminText = new JTextField();
    private JTextField mailAddressAdminText = new JTextField();
    private JTextField cityOfAdminText = new JTextField();
    private JTextField streetOfAdminText = new JTextField();
    private JTextField buildingOfAdminText = new JTextField();
    private JTextField apartmentOfAdminText = new JTextField();
    private JLabel notData = new JLabel("Укажите все данные!!!");
    private JButton registerAdminButton = new JButton("Зарегистрироваться");

    private JButton backEnterAdminButton = new JButton("< Назад");
    private JButton backEnterDoctorButton = new JButton("< Назад");
    private JButton backRegAdminButton = new JButton("< Назад");

    private boolean isDoctor = false;
    private boolean isAdmin = false;

    public registrationForm() // Конструктор класса без параметров
    {
////////startRegistrationForm(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        startRegistrationForm.setBackground(new Color(255,255,255));
        startRegistrationForm.setLayout(new MigLayout()); // MigLayout - один из лучших вариантов менеджеров компоновки swing

        doctor_adminComboBox.setSelectedIndex(1);
        doctor_adminComboBox.setFocusable(false);

        startRegistrationForm.add(welcomeLabel,"al center, wrap"); // отступ слева на 110 пикселей, перенос компонентов
        startRegistrationForm.add(choseYourRollLabel, "gapleft 10, gaptop 50, split 2"); // отступ слева, отступ сверху, объеденение со следующим компонентом
        startRegistrationForm.add(doctor_adminComboBox,"wrap push"); // Перенос в самый низ окна
        startRegistrationForm.add(registrationButton,"al center, split 2");
        startRegistrationForm.add(enterButton);
////////startRegistrationForm(end)////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////enterRegistrationForm(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        adminNotDataLabel.setForeground(new Color(230,50,50));
        adminNotDataLabel.setVisible(false);
        enterRegistrationFormAdmin.setBackground(new Color(255,255,255));
        enterRegistrationFormAdmin.setLayout(new MigLayout());

        enterRegistrationFormAdmin.add(backEnterAdminButton, "wrap 8");
        enterRegistrationFormAdmin.add(enterDataAdminLabel, "gapleft 140, wrap");
        enterRegistrationFormAdmin.add(userNameAdminLabel, "gaptop 15, gapleft 25, split 2");
        enterRegistrationFormAdmin.add(userNameAdminText, "pushx, growx, wrap");
        enterRegistrationFormAdmin.add(passwordAdminLabel, "gaptop 10, gapleft 14, split 2");
        enterRegistrationFormAdmin.add(passwordAdminText, "pushx, growx, wrap");
        enterRegistrationFormAdmin.add(adminNotDataLabel, "al center, gaptop 20, wrap push");
        enterRegistrationFormAdmin.add(loginAdminButton, "gaptop 15, gapleft 150");

        doctorNotDataLabel.setForeground(new Color(230,50,50));
        doctorNotDataLabel.setVisible(false);
        enterRegistrationFormDoctor.setBackground(new Color(255,255,255));
        enterRegistrationFormDoctor.setLayout(new MigLayout());

        enterRegistrationFormDoctor.add(backEnterDoctorButton, "wrap 8");
        enterRegistrationFormDoctor.add(enterDataDoctorLabel, "gapleft 140, wrap");
        enterRegistrationFormDoctor.add(userNameDoctorLabel, "gaptop 15, gapleft 25, split 2");
        enterRegistrationFormDoctor.add(userNameDoctorText, "pushx, growx, wrap");
        enterRegistrationFormDoctor.add(passwordDoctorLabel, "gaptop 10, gapleft 14, split 2");
        enterRegistrationFormDoctor.add(passwordDoctorText, "pushx, growx, wrap");
        enterRegistrationFormDoctor.add(doctorNotDataLabel, "al center, gaptop 20, wrap push");
        enterRegistrationFormDoctor.add(loginDoctorButton, "gaptop 15, gapleft 150");
////////enterRegistrationForm(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////regRegistrationForm(begin)/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        regRegistrationFormAdmin.setBackground(new Color(255,255,255));
        regRegistrationFormAdmin.setLayout(new MigLayout());

        notData.setForeground(new Color(230,50,50));
        notData.setVisible(false);

        birthdayDayAdminTable.setDateFormatString("yyyy-MM-dd");
        birthdayDayAdminTable.setDate(new Date());

        regRegistrationFormAdmin.add(backRegAdminButton, "wrap 8");
        regRegistrationFormAdmin.add(secondNameAdminLabel, "al right");
        regRegistrationFormAdmin.add(secondNameAdminText, "w 250!, wrap"); // Ограничение длины текстового поля, отступ
        regRegistrationFormAdmin.add(firstNameAdminLabel, "al right");
        regRegistrationFormAdmin.add(firstNameAdminText, "w 250!, wrap");
        regRegistrationFormAdmin.add(thirdNameAdminLabel, "al right");
        regRegistrationFormAdmin.add(thirdNameAdminText, "w 250!, wrap");
        regRegistrationFormAdmin.add(sexOfAdminLabel, "al right");
        regRegistrationFormAdmin.add(maleSexOfAdminLabel, "split 4");
        regRegistrationFormAdmin.add(maleAdminCheckBox);
        regRegistrationFormAdmin.add(femaleSexOfAdminLabel, "split 2");
        regRegistrationFormAdmin.add(femaleAdminCheckBox, "wrap");
        regRegistrationFormAdmin.add(birthdayDayAdminLabel, "al right");
        regRegistrationFormAdmin.add(birthdayDayAdminTable, "w 150!, wrap"); //pushx, growx,
        regRegistrationFormAdmin.add(newPasswordAdminLabel, "al right");
        regRegistrationFormAdmin.add(newPasswordAdminText, "w 317!, wrap");
        regRegistrationFormAdmin.add(newPasswordAgainAdminLabel, "al right");
        regRegistrationFormAdmin.add(newPasswordAgainAdminText, "w 317!, wrap");
        regRegistrationFormAdmin.add(mailAddressAdminLabel, "al right");
        regRegistrationFormAdmin.add(mailAddressAdminText, "w 317!, wrap");
        regRegistrationFormAdmin.add(addressOfLiveAdminLabel, "span 1 4, top"); // Растянуть компонент 1х4
        regRegistrationFormAdmin.add(cityOfAdminLabel, "gapleft 18, split 2");
        regRegistrationFormAdmin.add(cityOfAdminText, "w 250!, wrap");
        regRegistrationFormAdmin.add(streetOfAdminLabel, "gapleft 18, split 2");
        regRegistrationFormAdmin.add(streetOfAdminText, "w 250!, wrap");
        regRegistrationFormAdmin.add(buildingOfAdminLabel, "gapleft 30, split 2");
        regRegistrationFormAdmin.add(buildingOfAdminText, "w 250!, wrap");
        regRegistrationFormAdmin.add(apartmentOfAdminLabel, "split 2");
        regRegistrationFormAdmin.add(apartmentOfAdminText, "w 250!, wrap");
        regRegistrationFormAdmin.add(notData, "span 2 1, al center, gaptop 20, wrap push");
        regRegistrationFormAdmin.add(registerAdminButton, "span 2 1, al center");
////////regRegistrationForm(end)///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl); // Менеджер компоновки для переключения между панелями
        cardPanel.add(startRegistrationForm, "start"); // добавляю на общую карточную панель одну из рабочих панелей
        cardPanel.add(enterRegistrationFormAdmin, "enterAdmin");
        cardPanel.add(enterRegistrationFormDoctor, "enterDoctor");
        cardPanel.add(regRegistrationFormAdmin, "regAdmin");
        cl.show(cardPanel, "start"); // Выбор панели для первичного отображения

        addRegistrationForm(cardPanel, 390,200);

////////actionListeners(begin)////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        enterButton.addActionListener(actionEvent -> {
            if(doctor_adminComboBox.getSelectedItem().equals(doctorString)) // Форма входа для врача
            {
                cl.show(cardPanel, "enterDoctor");
                refreshRegistrationForm(400,275);
            }
            else // Форма входа для администратора
            {
                cl.show(cardPanel, "enterAdmin");
                refreshRegistrationForm(400,275);
            }
        });
        registrationButton.addActionListener(actionEvent -> {
            if(doctor_adminComboBox.getSelectedItem().equals(adminString)) // Форма регистрации для врача
            {
                cl.show(cardPanel, "regAdmin");
                refreshRegistrationForm(480,500);
            }
        });

        doctor_adminComboBox.addItemListener(actionEvent -> {
            if(actionEvent.getItem().equals(doctorString))
            {
                registrationButton.setEnabled(false);
            }
            else
            {
                registrationButton.setEnabled(true);
            }
        });

        maleAdminCheckBox.addActionListener(acationEvent -> {
            if(maleAdminCheckBox.isSelected())
            {
                femaleAdminCheckBox.setSelected(false);
            }
        });
        femaleAdminCheckBox.addActionListener(acationEvent -> {
            if(femaleAdminCheckBox.isSelected())
            {
                maleAdminCheckBox.setSelected(false);
            }
        });

        backEnterAdminButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "start");
            userNameAdminText.setText(""); // Очистка текстовых полей, заполненных, но не зарегистрированных ранее
            passwordAdminText.setText("");
            adminNotDataLabel.setVisible(false);
            refreshRegistrationForm(390,200);
        });
        backEnterDoctorButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "start");
            userNameDoctorText.setText(""); // Очистка текстовых полей, заполненных, но не зарегистрированных ранее
            passwordDoctorText.setText("");
            doctorNotDataLabel.setVisible(false);
            refreshRegistrationForm(390,200);
        });
        backRegAdminButton.addActionListener(actionEvent -> {
            cl.show(cardPanel, "start");
            birthdayDayAdminTable.setDate(new Date()); // Сброс выбранной ранее даты
            secondNameAdminText.setText(""); // Очистка текстовых полей, заполненных, но не зарегистрированных ранее
            firstNameAdminText.setText("");
            thirdNameAdminText.setText("");
            newPasswordAdminText.setText("");
            newPasswordAgainAdminText.setText("");
            mailAddressAdminText.setText("");
            cityOfAdminText.setText("");
            streetOfAdminText.setText("");
            buildingOfAdminText.setText("");
            apartmentOfAdminText.setText("");
            maleAdminCheckBox.setSelected(false);
            femaleAdminCheckBox.setSelected(false);
            notData.setVisible(false);
            refreshRegistrationForm(390,200);
        });

        registerAdminButton.addActionListener(actionEvent -> {
            registerNewAdmin();
        });

        loginAdminButton.addActionListener(actionEvent -> {
            loginAdmin();
        });
        loginDoctorButton.addActionListener(actionEvent -> {
            loginDoctor();
        });
////////actionListeners(end)//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void loginDoctor()
    {
        if(!userNameDoctorText.getText().equals("") && !passwordDoctorText.getText().equals(""))
        {
            isDoctor = true;
            doctorNotDataLabel.setVisible(false);

            JDBCConnection jdbcConnection = new JDBCConnection(); // Создаём бъект класса для работы с БД

            Doctor doctor = new Doctor(); // Вписываем в объект класса необходимые данные (Объект сразу удалится после выполнения метода)
            doctor.setMailAddress(userNameDoctorText.getText().trim()); // Считал введенные данные пользователем
            doctor.setPassword(passwordDoctorText.getText()); // Считал введенные данные пользователем

            doctor = jdbcConnection.getDoctor(doctor); // Получаем массив данных обо всех администраторах

            if(!(doctor.getSecondName() == null)) // Вход в систему в качестве администратора
            {
                doctorNotDataLabel.setVisible(false);

                this.dispose(); // Закрытие окна регистрации
                new registryMainDoctorWindow(doctor); // Создание основного рабочего пространства для текущего админа
            }
            else // Не найден администратор в БД
            {
                doctorNotDataLabel.setText("Врач не найден!!!");
                doctorNotDataLabel.setVisible(true);
            }
        }
        else
        {
            adminNotDataLabel.setText("Введите все данные!!!");
            doctorNotDataLabel.setVisible(true);
        }
    }

    private void loginAdmin()
    {
        if(!userNameAdminText.getText().equals("") && !passwordAdminText.getText().equals("")) // Если регистрационные поля не пусты
        {
            isAdmin = true;
            adminNotDataLabel.setVisible(false);

            JDBCConnection jdbcConnection = new JDBCConnection(); // Создаём бъект класса для работы с БД

            Admin admin = new Admin(); // Вписываем в объект класса необходимые данные (Объект сразу удалится после выполнения метода)
            admin.setMailAddress(userNameAdminText.getText().trim()); // Считал введенные данные пользователем
            admin.setPassword(passwordAdminText.getText()); // Считал введенные данные пользователем

            admin = jdbcConnection.getAdmin(admin); // Получаем массив данных обо всех администраторах

            if(!(admin.getSecondName() == null)) // Вход в систему в качестве администратора
            {
                adminNotDataLabel.setVisible(false);

                this.dispose(); // Закрытие окна регистрации
                new registryMainAdminWindow(admin); // Создание основного рабочего пространства для текущего админа
            }
            else // Не найден администратор в БД
            {
                adminNotDataLabel.setText("Администратор не найден!!!");
                adminNotDataLabel.setVisible(true);
            }
        }
        else // Не все регистрационные поля введены
        {
            adminNotDataLabel.setText("Введите все данные!!!");
            adminNotDataLabel.setVisible(true);
        }
    }

    private void registerNewAdmin()
    {
        if(!secondNameAdminText.getText().equals("") && !firstNameAdminText.getText().equals("") &&
                !thirdNameAdminText.getText().equals("") && (maleAdminCheckBox.isSelected() || femaleAdminCheckBox.isSelected()) &&
                (newPasswordAdminText.getText().equals(newPasswordAgainAdminText.getText()) && !newPasswordAdminText.getText().equals("")) &&
                !mailAddressAdminText.getText().equals("") && !cityOfAdminText.getText().equals("") && !streetOfAdminText.getText().equals("") &&
                !buildingOfAdminText.getText().equals("") && !apartmentOfAdminText.getText().equals("") &&
                birthdayDayAdminTable.getDate().before(new Date())) // Если все регистрационные поля заполнены
        {
            notData.setVisible(false);

            JDBCConnection jdbcConnection = new JDBCConnection(); // Создание экземпляра класса для работы с БД

            java.util.Date d = birthdayDayAdminTable.getDate();
            java.sql.Date d2 = new java.sql.Date(d.getTime());

            String male_feemale = "";
            if (maleAdminCheckBox.isSelected()) male_feemale = "Мужской";
            else male_feemale = "Женский";

            Admin admin = new Admin(secondNameAdminText.getText().trim(), firstNameAdminText.getText().trim(),
                    thirdNameAdminText.getText().trim(), male_feemale, d2,
                    newPasswordAdminText.getText(), mailAddressAdminText.getText().trim(),
                    cityOfAdminText.getText().trim(), streetOfAdminText.getText().trim(),
                    buildingOfAdminText.getText().trim(), apartmentOfAdminText.getText().trim());

            jdbcConnection.registerAdmin(admin);

            birthdayDayAdminTable.setDate(new Date()); // Сброс выбранной ранее даты
            secondNameAdminText.setText(""); // Очистка текстовых полей, заполненных, но не зарегистрированных ранее
            firstNameAdminText.setText("");
            thirdNameAdminText.setText("");
            newPasswordAdminText.setText("");
            newPasswordAgainAdminText.setText("");
            mailAddressAdminText.setText("");
            cityOfAdminText.setText("");
            streetOfAdminText.setText("");
            buildingOfAdminText.setText("");
            apartmentOfAdminText.setText("");
            maleAdminCheckBox.setSelected(false);
            femaleAdminCheckBox.setSelected(false);
            notData.setVisible(false);

            new infoMessage(this, "Новый администратор зарегистрирован в системе", "УВЕДОМЛЕНИЕ");
        }
        else
        {
            notData.setVisible(true);
        }
    }

    private void addRegistrationForm(JPanel _carPanel, int width, int height)
    {
        this.setBounds(0, 0, width, height); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // При нажатии на X произойдёт выход из приложения
        this.setFocusable(true); // Фокус на окне
        this.setVisible(true); // Делаем окно видимым
        this.setResizable(false); // Запрет на изменение размеров окна
        this.setTitle("Форма регистрации"); // Заголовок окна
        this.setLocationRelativeTo(null); // Расположение окна ровно по центру

        this.add(_carPanel);
    }

    public boolean isAdmin()
    {
        return this.isAdmin;
    }

    public boolean isDoctor()
    {
        return this.isDoctor;
    }

    private  void refreshRegistrationForm(int width, int height)
    {
        this.setBounds(0, 0, width, height); // Устанавливаем оптимальные размеры окна, учитывая размеры экрана
        this.setLocationRelativeTo(null); // Расположение окна ровно по центру
    }

    @Override
    public void run()
    {

    }
}
