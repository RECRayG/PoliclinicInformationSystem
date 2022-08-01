package Connector;

import Users.Admin;
import Users.Doctor;
import Users.Patient;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.math.BigInteger;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDBCConnection extends Configs
{
    private Connection dbConnecion;

    public Connection getDbConnecion() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver"); // Загрузка класса драйвера, создание мостика
        String sourceURL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName; // Источник данных
        dbConnecion = DriverManager.getConnection(sourceURL, dbUser, dbPassword); // Установка соединения с БД

        return dbConnecion;
    }

    public void dbDisconnectionPrSt(PreparedStatement prSt) throws  SQLException
    {
        if(prSt != null)
        {
            prSt.close();
        }
    }
    public void dbDisconnectionResSet(ResultSet resSet) throws  SQLException
    {
        if(resSet != null)
        {
            resSet.close();
        }
    }
    public void dbDisconnectionSt(Statement st) throws SQLException
    {
        if(st != null)
        {
            st.close();
        }
    }
    public void dbDisconnectionCs(CallableStatement cs) throws SQLException
    {
        if(cs != null)
        {
            cs.close();
        }
    }

    // Методы записи/чтения в/из базы данных
    public void registerAdmin(Admin admin) // Регистрация администратора в БД
    {
        String insert = "INSERT INTO " + CONST.ADMIN_TABLE + "("                 // SQL запрос: в таблицу admins, в следующие поля вставить следующие данные
                + CONST.ADMINS_SECONDNAME + "," + CONST.ADMINS_FIRSTNAME + ","
                + CONST.ADMINS_THIRDNAME + "," + CONST.ADMINS_GENDER + ","
                + CONST.ADMINS_BIRTHDAY + "," + CONST.ADMINS_PASSWORD + ","
                + CONST.ADMINS_MAIL + "," + CONST.ADMINS_CITY + ","
                + CONST.ADMINS_STREET + "," + CONST.ADMINS_BUILDING + ","
                + CONST.ADMINS_APARTMENT + ")" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement prSt = null;

        try
        {
            prSt = getDbConnecion().prepareStatement(insert); // PreparedStatement - служит для выполнения параметризованных запросов в виде ?

            prSt.setString(1, admin.getSecondName());
            prSt.setString(2, admin.getFirstName());
            prSt.setString(3, admin.getThirdName());
            prSt.setString(4, admin.getGender());
            prSt.setDate(5, admin.getBirthday());
            prSt.setString(6, admin.getPassword());
            prSt.setString(7, admin.getMailAddress());
            prSt.setString(8, admin.getCity());
            prSt.setString(9, admin.getStreet());
            prSt.setString(10, admin.getBuilding());
            prSt.setString(11, admin.getApartment());

            prSt.executeUpdate(); // Выполнить SQL запрос о передаче данных в БД
        }
        catch (ClassNotFoundException e) // Если не найдена БД (Не загрузился Driver)
        {
            System.out.println(e);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }
        finally // Обязательное закрытие подключения к БД
        {
            try
            {
                dbDisconnectionPrSt(prSt);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Doctor getDoctor(Doctor doctor) // Возвращает массив данных об администраторе из БД
    {
        PreparedStatement prSt = null;

        String select = "SELECT * FROM " + CONST.DOCTOR_TABLE + " doc INNER JOIN " + CONST.SPECIALIZATION_TABLE + " sp INNER JOIN " + CONST.PLOT_TABLE + " pl" +
                        " ON " + "doc." + CONST.DOCTORS_SPECIALIZATION_CODE + " = sp." + CONST.SPECIALIZATION_CODE + " AND doc." + CONST.DOCTORS_TABLE_NUMBER + " = pl." + CONST.PLOTS_DOCTORS_TABLE_NUMBER +
                        " WHERE " + CONST.DOCTORS_MAIL + "=? AND " + CONST.DOCTORS_PASSWORD + "=?"; // SQL запрос: из таблицы admins вытянуть все данные: почта и пароль, которые равны следующим значениям

        ResultSet resSet = null;

        try
        {
            prSt = getDbConnecion().prepareStatement(select); // PreparedStatement - служит для выполнения параметризованных запросов в виде ?

            prSt.setString(1, doctor.getMailAddress());
            prSt.setString(2, doctor.getPassword());

            resSet = prSt.executeQuery(); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)

            try
            {
                while(resSet.next())
                {
                    doctor.setSecondName(resSet.getString(CONST.DOCTORS_SECONDNAME));
                    doctor.setFirstName(resSet.getString(CONST.DOCTORS_FIRSTNAME));
                    doctor.setThirdName(resSet.getString(CONST.DOCTORS_THIRDNAME));
                    doctor.setMailAddress(resSet.getString(CONST.DOCTORS_MAIL));
                    doctor.setPassword(resSet.getString(CONST.DOCTORS_PASSWORD));
                    doctor.setBirthday(resSet.getDate(CONST.DOCTORS_BIRTHDAY));
                    doctor.setGender(resSet.getString(CONST.DOCTORS_GENDER));
                    doctor.setCity(resSet.getString(CONST.DOCTORS_CITY));
                    doctor.setStreet(resSet.getString(CONST.DOCTORS_STREET));
                    doctor.setBuilding(resSet.getString(CONST.DOCTORS_BUILDING));
                    doctor.setApartment(resSet.getString(CONST.DOCTORS_APARTMENT));
                    doctor.setDateOfStartJob(resSet.getDate(CONST.DOCTORS_DATE_START_JOB));
                    doctor.setSpecialization(resSet.getString(CONST.SPECIALIZATION));
                    doctor.setNumberOfPlot(resSet.getInt(CONST.PLOTS_NUMBER));
                    doctor.setTableNumber(resSet.getInt(CONST.DOCTORS_TABLE_NUMBER));
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e) // Если не найдена БД
        {
            System.out.println(e);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД
        {
            System.out.println(e);
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionPrSt(prSt);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return doctor; // Если администратор не будет найден, то вернётся null в каждом поле
    }

    public Admin getAdmin(Admin admin) // Возвращает массив данных об администраторе из БД
    {
        PreparedStatement prSt = null;

        String select = "SELECT * FROM " + CONST.ADMIN_TABLE + " WHERE "
                + CONST.ADMINS_MAIL + "=? AND " + CONST.ADMINS_PASSWORD + "=?"; // SQL запрос: из таблицы admins вытянуть все данные: почта и пароль, которые равны следующим значениям

        ResultSet resSet = null;

        try
        {
            prSt = getDbConnecion().prepareStatement(select); // PreparedStatement - служит для выполнения параметризованных запросов в виде ?

            prSt.setString(1, admin.getMailAddress());
            prSt.setString(2, admin.getPassword());

            resSet = prSt.executeQuery(); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)

            try
            {
                while(resSet.next())
                {
                    admin.setSecondName(resSet.getString(CONST.ADMINS_SECONDNAME));
                    admin.setFirstName(resSet.getString(CONST.ADMINS_FIRSTNAME));
                    admin.setThirdName(resSet.getString(CONST.ADMINS_THIRDNAME));
                    admin.setMailAddress(resSet.getString(CONST.ADMINS_MAIL));
                    admin.setPassword(resSet.getString(CONST.ADMINS_PASSWORD));
                    admin.setBirthday(resSet.getDate(CONST.ADMINS_BIRTHDAY));
                    admin.setGender(resSet.getString(CONST.ADMINS_GENDER));
                    admin.setCity(resSet.getString(CONST.ADMINS_CITY));
                    admin.setStreet(resSet.getString(CONST.ADMINS_STREET));
                    admin.setBuilding(resSet.getString(CONST.ADMINS_BUILDING));
                    admin.setApartment(resSet.getString(CONST.ADMINS_APARTMENT));
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e) // Если не найдена БД
        {
            System.out.println(e);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД
        {
            System.out.println(e);
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionPrSt(prSt);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return admin; // Если администратор не будет найден, то вернётся null в каждом поле
    }

    public void registerDoctor(Doctor doctor)
    {
        String insert = "INSERT INTO " + CONST.DOCTOR_TABLE + "("                 // SQL запрос: в таблицу doctors, в следующие поля вставить следующие данные
                + CONST.DOCTORS_SECONDNAME + "," + CONST.DOCTORS_FIRSTNAME + ","
                + CONST.DOCTORS_THIRDNAME + "," + CONST.DOCTORS_GENDER + ","
                + CONST.DOCTORS_BIRTHDAY + "," + CONST.DOCTORS_SPECIALIZATION_CODE + ","
                + CONST.DOCTORS_DATE_START_JOB + "," + CONST.DOCTORS_PASSWORD + ","
                + CONST.DOCTORS_MAIL + "," + CONST.DOCTORS_CITY + ","
                + CONST.DOCTORS_STREET + "," + CONST.DOCTORS_BUILDING + "," + CONST.DOCTORS_APARTMENT + ")" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String select = "SELECT * FROM " + CONST.SPECIALIZATION_TABLE + " WHERE " + CONST.SPECIALIZATION + "=?";

        String insert2 = "INSERT INTO "  + CONST.PLOT_TABLE + "(" + CONST.PLOTS_DOCTORS_TABLE_NUMBER + ","
                                                                 + CONST.PLOTS_NUMBER + ")" + " VALUES(?,?)";

        String select2 = "SELECT * FROM " + CONST.DOCTOR_TABLE + " WHERE "
                + CONST.DOCTORS_SECONDNAME + "=? AND " + CONST.DOCTORS_FIRSTNAME + "=? AND "
                + CONST.DOCTORS_THIRDNAME + "=? AND " + CONST.DOCTORS_GENDER + "=? AND "
                + CONST.DOCTORS_BIRTHDAY + "=? AND "
                + CONST.DOCTORS_DATE_START_JOB + "=? AND " + CONST.DOCTORS_PASSWORD + "=? AND "
                + CONST.DOCTORS_MAIL + "=? AND " + CONST.DOCTORS_CITY + "=? AND "
                + CONST.DOCTORS_STREET + "=? AND " + CONST.DOCTORS_BUILDING + "=? AND " + CONST.DOCTORS_APARTMENT + "=?";

        String insert3 = "INSERT INTO "  + CONST.TIMETABLE_TABLE + "(" + CONST.TIMETABLE_TABLE_NUMBER + ","
                + CONST.TIMETABLE_NUMBER_CABINET + ")" + " VALUES(?,?)";

        String insert4 = "INSERT INTO "  + CONST.TIMETABLETIME_TABLE + "(" + CONST.TIMETABLETIME_TABLE_NUMBER + "," +
                CONST.TIMETABLETIME_ID_DAY + ")" + " VALUES(?,?)";

        String insert5 = "INSERT INTO "  + CONST.TIMETABLEDAY_TABLE + "(" + CONST.TIMETABLEDAY_TABLE_NUMBER + "," +
                CONST.TIMETABLEDAY_ID_DAY + ")" + " VALUES(?,?)";

        String insert6 = "INSERT INTO "  + CONST.TIMEOFJOB_TABLE + "(" + CONST.TIMEOFJOB_TABLE_NUMBER + "," +
                CONST.TIMEOFJOB_ID_TIME_BEGIN + "," + CONST.TIMEOFJOB_ID_TIME_END + "," + CONST.TIMEOFJOB_ID_WEEK + ")" + " VALUES(?,?,?,?)";

        PreparedStatement prStIns = null;
        PreparedStatement prStSel = null;
        PreparedStatement prStIns2 = null;
        PreparedStatement prStSel2 = null;
        PreparedStatement prStIns3 = null;
        PreparedStatement prStIns4 = null;
        PreparedStatement prStIns5 = null;
        PreparedStatement prStIns6 = null;
        ResultSet resSet = null;
        ResultSet resSet2 = null;

        try
        {
            prStIns = getDbConnecion().prepareStatement(insert); // PreparedStatement - служит для выполнения параметризованных запросов в виде ?
            prStSel = getDbConnecion().prepareStatement(select);
            prStIns2 = getDbConnecion().prepareStatement(insert2);
            prStSel2 = getDbConnecion().prepareStatement(select2);
            prStIns3 = getDbConnecion().prepareStatement(insert3);
            prStIns4 = getDbConnecion().prepareStatement(insert4);
            prStIns5 = getDbConnecion().prepareStatement(insert5);
            prStIns6 = getDbConnecion().prepareStatement(insert6);

            prStSel.setString(1, doctor.getSpecialization());
            resSet = prStSel.executeQuery();

            prStIns.setString(1, doctor.getSecondName());
            prStIns.setString(2, doctor.getFirstName());
            prStIns.setString(3, doctor.getThirdName());
            prStIns.setString(4, doctor.getGender());
            prStIns.setDate(5, doctor.getBirthday());
            while(resSet.next())
            {
                prStIns.setInt(6, resSet.getInt(CONST.SPECIALIZATION_CODE));
            }
            prStIns.setDate(7, doctor.getDateOfStartJob());
            prStIns.setString(8, doctor.getPassword());
            prStIns.setString(9, doctor.getMailAddress());
            prStIns.setString(10, doctor.getCity());
            prStIns.setString(11, doctor.getStreet());
            prStIns.setString(12, doctor.getBuilding());
            prStIns.setString(13, doctor.getApartment());

            prStIns.executeUpdate(); // Выполнить SQL запрос о передаче данных в БД




            prStSel2.setString(1, doctor.getSecondName());
            prStSel2.setString(2, doctor.getFirstName());
            prStSel2.setString(3, doctor.getThirdName());
            prStSel2.setString(4, doctor.getGender());
            prStSel2.setDate(5, doctor.getBirthday());
            prStSel2.setDate(6, doctor.getDateOfStartJob());
            prStSel2.setString(7, doctor.getPassword());
            prStSel2.setString(8, doctor.getMailAddress());
            prStSel2.setString(9, doctor.getCity());
            prStSel2.setString(10, doctor.getStreet());
            prStSel2.setString(11, doctor.getBuilding());
            prStSel2.setString(12, doctor.getApartment());
            resSet2 = prStSel2.executeQuery();

            int tableNumber = 0;
            while(resSet2.next())
            {
                //prStIns2.setInt(1, resSet2.getInt(CONST.DOCTORS_TABLE_NUMBER));
                tableNumber = resSet2.getInt(CONST.DOCTORS_TABLE_NUMBER);
            }
            prStIns2.setInt(1, tableNumber);
            prStIns2.setInt(2, doctor.getNumberOfPlot());
            prStIns2.executeUpdate();




            prStIns3.setInt(1, tableNumber);
            prStIns3.setInt(2, 0);
            prStIns3.executeUpdate();



            for(int i = 1; i <= 21; i++)
            {
                if(i <= 6)
                {
                    prStIns4.setInt(1, tableNumber);
                    prStIns4.setInt(2, i);

                    prStIns5.setInt(1, tableNumber);
                    prStIns5.setInt(2, i);

                    prStIns6.setInt(1, tableNumber);
                    prStIns6.setInt(2, 23);
                    prStIns6.setInt(3, 23);
                    prStIns6.setInt(4, i);


                    prStIns4.executeUpdate();
                    prStIns5.executeUpdate();
                    prStIns6.executeUpdate();
                }
                else if(i > 6 && i <= 21)
                {
                    prStIns6.setInt(1, tableNumber);
                    prStIns6.setInt(2, 23);
                    prStIns6.setInt(3, 23);
                    prStIns6.setInt(4, i);

                    prStIns6.executeUpdate();
                }
            }
        }
        catch (ClassNotFoundException e) // Если не найдена БД (Не загрузился Driver)
        {
            System.out.println(e);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }
        finally // Обязательное закрытие подключения к БД
        {
            try
            {
                dbDisconnectionPrSt(prStIns);
                dbDisconnectionPrSt(prStSel);
                dbDisconnectionPrSt(prStIns2);
                dbDisconnectionPrSt(prStSel2);
                dbDisconnectionResSet(resSet);
                dbDisconnectionResSet(resSet2);
                dbDisconnectionPrSt(prStIns3);
                dbDisconnectionPrSt(prStIns4);
                dbDisconnectionPrSt(prStIns5);
                dbDisconnectionPrSt(prStIns6);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Vector<String> getAllStrees()
    {
        Vector<String> streets = new Vector();
        String select = "SELECT " + CONST.ADDRESSPLOT_STREET + " FROM " + CONST.ADDRESSPLOT_TABLE;
        ResultSet resSet = null;
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select);
            while(resSet.next())
            {
                streets.add(resSet.getString(CONST.ADDRESSPLOT_STREET));
            }
        }
        catch (SQLException e) { System.out.println(e); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e) { e.printStackTrace(); }
        }

        return streets;
    }

    public ArrayList getStreetsAndPlots()
    {
        ArrayList streets = new ArrayList();

        String select = "SELECT " + CONST.ADDRESSPLOT_NUMBER_OF_PLOT + "," + CONST.ADDRESSPLOT_STREET + " FROM " + CONST.ADDRESSPLOT_TABLE;
        ResultSet resSet = null;
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select);
            while(resSet.next())
            {
                ArrayList temp = new ArrayList();
                temp.add(resSet.getInt(CONST.ADDRESSPLOT_NUMBER_OF_PLOT));
                temp.add(resSet.getString(CONST.ADDRESSPLOT_STREET));

                streets.add(temp);
            }
        }
        catch (SQLException e) { System.out.println(e); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e) { e.printStackTrace(); }
        }

        return streets;
    }

    public void registerPatient(Patient patient)
    {
        String insert = "INSERT INTO " + CONST.PATIENT_TABLE + "("
                + CONST.PATIENT_SECONDNAME + "," + CONST.PATIENT_FIRSTNAME + ","
                + CONST.PATIENT_THIRDNAME + "," + CONST.PATIENT_GENDER + ","
                + CONST.PATIENT_BIRTHDAY + "," + CONST.PATIENT_NUMBER_OF_PLOT + ","
                + CONST.PATIENT_DATE_OF_CREATE_CARD + "," + CONST.PATIENT_NUMBER_OF_CARD + ","
                + CONST.PATIENT_NUMBER_OF_PASPORT + "," + CONST.PATIENT_NUMBER_OF_PHONE + ","
                + CONST.PATIENT_INSURANCE_POLICY + "," + CONST.PATIENT_CITY + "," + CONST.PATIENT_STREET + ","
                + CONST.PATIENT_BUILDING + "," + CONST.PATIENT_APARTMENT + ")" + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String select = "SELECT " + CONST.ADDRESSPLOT_NUMBER_OF_PLOT + "," + CONST.ADDRESSPLOT_STREET + " FROM " + CONST.ADDRESSPLOT_TABLE;

        String select2 = "";

        String update = "UPDATE " + CONST.PATIENT_TABLE + " SET " + CONST.PATIENT_NUMBER_OF_CARD + " = ?" + " WHERE " +
                        CONST.PATIENT_ID + " = ?";

        PreparedStatement prStIns = null;
        PreparedStatement prStUpd = null;
        Statement stSel = null;
        Statement stSel2 = null;
        ResultSet resSet2 = null;
        ResultSet resSet = null;

        try
        {
            prStIns = getDbConnecion().prepareStatement(insert);
            prStUpd = getDbConnecion().prepareStatement(update);
            stSel = getDbConnecion().createStatement();
            stSel2 = getDbConnecion().createStatement();

            resSet = stSel.executeQuery(select);

            int numberOfPlot = 1;
            while(resSet.next())
            {
                if( resSet.getString(CONST.ADDRESSPLOT_STREET).equals(patient.getStreet()) )
                {
                    numberOfPlot = resSet.getInt(CONST.ADDRESSPLOT_NUMBER_OF_PLOT);
                    break;
                }
            }

            select2 = "SELECT * FROM " + CONST.PATIENT_TABLE
                    + " WHERE "
                    + CONST.PATIENT_SECONDNAME + " = '" + patient.getSecondName() + "' AND "
                    + CONST.PATIENT_FIRSTNAME + " = '" + patient.getFirstName() + "' AND "
                    + CONST.PATIENT_THIRDNAME + " = '" + patient.getThirdName() + "' AND "
                    + CONST.PATIENT_GENDER + " = '" + patient.getGender() + "' AND "
                    + CONST.PATIENT_BIRTHDAY + " = DATE '" + patient.getBirthday() + "' AND "
                    + CONST.PATIENT_NUMBER_OF_PLOT + " = " + String.valueOf(numberOfPlot) + " AND "
                    + CONST.PATIENT_DATE_OF_CREATE_CARD + " = DATE '" + patient.getDayOfCreateCard() + "' AND "
                    + CONST.PATIENT_NUMBER_OF_PASPORT + " = '" + patient.getNumberOfPassport() + "' AND "
                    + CONST.PATIENT_NUMBER_OF_PHONE + " = '" + patient.getNumberOfPhone() + "' AND "
                    + CONST.PATIENT_INSURANCE_POLICY + " = '" + patient.getNumberOfInsurancePolicy() + "' AND "
                    + CONST.PATIENT_CITY + " = '" + patient.getCity() + "' AND "
                    + CONST.PATIENT_STREET + " = '" + patient.getStreet() + "' AND "
                    + CONST.PATIENT_BUILDING + " = '" + patient.getBuilding() + "' AND "
                    + CONST.PATIENT_APARTMENT + " = '" + patient.getApartment() + "'";

            prStIns.setString(1, patient.getSecondName());
            prStIns.setString(2, patient.getFirstName());
            prStIns.setString(3, patient.getThirdName());
            prStIns.setString(4, patient.getGender());
            prStIns.setDate(5, patient.getBirthday());
            prStIns.setInt(6, numberOfPlot);
            prStIns.setDate(7, patient.getDayOfCreateCard());
            try
            {
                prStIns.setInt(8, Integer.parseInt(patient.getNumberOfPassport()));
            }catch (NumberFormatException e)
            {
                prStIns.setInt(8, (int)(Math.random()*(999999-100000) + 100000) );
            }

            prStIns.setString(9, patient.getNumberOfPassport());
            prStIns.setString(10, patient.getNumberOfPhone());
            prStIns.setString(11, patient.getNumberOfInsurancePolicy());
            prStIns.setString(12, patient.getCity());
            prStIns.setString(13, patient.getStreet());
            prStIns.setString(14, patient.getBuilding());
            prStIns.setString(15, patient.getApartment());

            prStIns.executeUpdate();


            resSet2 = stSel2.executeQuery(select2);
            while(resSet2.next())
            {
                if( resSet2.getString(CONST.PATIENT_SECONDNAME).equals(patient.getSecondName()) &&
                    resSet2.getString(CONST.PATIENT_FIRSTNAME).equals(patient.getFirstName()) &&
                    resSet2.getString(CONST.PATIENT_THIRDNAME).equals(patient.getThirdName()) &&
                    resSet2.getString(CONST.PATIENT_GENDER).equals(patient.getGender()) &&
                    //resSet2.getDate(CONST.PATIENT_BIRTHDAY).getTime() == patient.getBirthday().getTime() &&
                    resSet2.getInt(CONST.PATIENT_NUMBER_OF_PLOT) == numberOfPlot &&
                    //resSet2.getDate(CONST.PATIENT_DATE_OF_CREATE_CARD).getTime() == patient.getDayOfCreateCard().getTime() &&
                    resSet2.getString(CONST.PATIENT_NUMBER_OF_PASPORT).equals(patient.getNumberOfPassport()) &&
                    resSet2.getString(CONST.PATIENT_NUMBER_OF_PHONE).equals(patient.getNumberOfPhone()) &&
                    resSet2.getString(CONST.PATIENT_INSURANCE_POLICY).equals(patient.getNumberOfInsurancePolicy()) &&
                    resSet2.getString(CONST.PATIENT_CITY).equals(patient.getCity()) &&
                    resSet2.getString(CONST.PATIENT_STREET).equals(patient.getStreet()) &&
                    resSet2.getString(CONST.PATIENT_BUILDING).equals(patient.getBuilding()) &&
                    resSet2.getString(CONST.PATIENT_APARTMENT).equals(patient.getApartment()) )
                {
                    BigInteger local = new BigInteger( String.valueOf(resSet2.getInt(CONST.PATIENT_ID)) +
                            resSet2.getDate(CONST.PATIENT_BIRTHDAY).toString().substring(0,4) +
                            resSet2.getDate(CONST.PATIENT_BIRTHDAY).toString().substring(5,7) +
                            resSet2.getDate(CONST.PATIENT_BIRTHDAY).toString().substring(8,10) );
                    prStUpd.setString(1, local.toString() ); // Передаю на сервер текст, который знает, как обработать BIGINT
                    prStUpd.setInt(2, resSet2.getInt(CONST.PATIENT_ID));
                    prStUpd.executeUpdate();
                    break;
                }
            }
        }
        catch (ClassNotFoundException e) // Если не найдена БД (Не загрузился Driver)
        {
            System.out.println(e);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }
        finally // Обязательное закрытие подключения к БД
        {
            try
            {
                dbDisconnectionPrSt(prStIns);
                dbDisconnectionPrSt(prStUpd);
                dbDisconnectionResSet(resSet);
                dbDisconnectionResSet(resSet2);
                dbDisconnectionSt(stSel);
                dbDisconnectionSt(stSel2);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Vector<String> getNeedPatients()
    {
        Vector<String> needPatients = new Vector();

        String select = "SELECT CONCAT(" + CONST.PATIENT_NUMBER_OF_CARD + ", ', ', " + CONST.PATIENT_SECONDNAME + ", ' ', " +
                        CONST.PATIENT_FIRSTNAME + ", ' ', " + CONST.PATIENT_THIRDNAME + ") FROM " + CONST.PATIENT_TABLE;

        Statement st = null;
        ResultSet resSet = null;
        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)

            while(resSet.next())
            {
                needPatients.add(resSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return needPatients;
    }

    public ArrayList getAllSpecialization()
    {
        ArrayList specializationList = new ArrayList();

        String select = "SELECT " + CONST.SPECIALIZATION + " FROM " + CONST.SPECIALIZATION_TABLE; // SQL запрос: из таблицы admins вытянуть все данные: почта и пароль, которые равны следующим значениям

        Statement st = null;
        try {
            st = getDbConnecion().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ResultSet resSet = null;
        try {
            resSet = st.executeQuery(select); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try
        {
            while(resSet.next())
            {
                specializationList.add(resSet.getString(CONST.SPECIALIZATION));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return specializationList;
    }

    public ArrayList getAllSpecializationCode()
    {
        ArrayList specializationCodeList = new ArrayList();

        String select = "SELECT " + CONST.SPECIALIZATION_CODE + " FROM " + CONST.SPECIALIZATION_TABLE; // SQL запрос: из таблицы admins вытянуть все данные: почта и пароль, которые равны следующим значениям

        Statement st = null;
        try {
            st = getDbConnecion().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ResultSet resSet = null;
        try {
            resSet = st.executeQuery(select); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try
        {
            while(resSet.next())
            {
                specializationCodeList.add(resSet.getInt(CONST.SPECIALIZATION_CODE));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return specializationCodeList;
    }

    public ArrayList getAllSpecializationANDSpecializationCode()
    {
        ArrayList specializationCodeList = new ArrayList();

        String select = "SELECT * FROM " + CONST.SPECIALIZATION_TABLE + " ORDER BY " + CONST.SPECIALIZATION_CODE; // SQL запрос: из таблицы admins вытянуть все данные: почта и пароль, которые равны следующим значениям

        Statement st = null;
        try {
            st = getDbConnecion().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ResultSet resSet = null;
        try {
            resSet = st.executeQuery(select); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try
        {
            while(resSet.next())
            {
                ArrayList temp = new ArrayList();
                temp.add(resSet.getInt(CONST.SPECIALIZATION_CODE));
                temp.add(resSet.getString(CONST.SPECIALIZATION));
                specializationCodeList.add(temp);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return specializationCodeList;
    }

    public Vector<Integer> getCabinetsNumbers()
    {
        Vector<Integer> cabinets = new Vector();
        String select = "SELECT " + CONST.CABINETS_NUMBER + " FROM " + CONST.CABINETS_TABLE;
        ResultSet resSet = null;
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select);
            while(resSet.next())
            {
                cabinets.add(resSet.getInt(CONST.CABINETS_NUMBER));
            }
        }
        catch (SQLException e) { System.out.println(e); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e) { e.printStackTrace(); }
        }

        return cabinets;
    }

    public void writeReception(int tableNumber, String numberOfCard, java.sql.Date date, String time)
    {
        String insert = "INSERT INTO " + CONST.RECEPTION_TABLE + "(" +
                CONST.RECEPTION_TABLE_NUMBER + ", " +
                CONST.RECEPTION_NUMBER_OF_CARD + ", " +
                CONST.RECEPTION_DATE_OF_RECEPTION + ", " +
                CONST.RECEPTION_TIME_OF_RECEPTION + ", " +
                CONST.RECEPTION_IS_VISIT + ")" +
                        " VALUES(" + tableNumber + ", " + numberOfCard + ", DATE '" + date.toString() + "', '" + time + "', false)";

        try
        {
            Statement statement = getDbConnecion().createStatement();
            statement.executeUpdate(insert);
        } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    public String getComplaint(int[] selectedRows, JTable table)
    {
        String complaint = "";

        String select =
                "SELECT " + CONST.COMPLAINTS_COMPLAINTS + " FROM " + CONST.COMPLAINTS_TABLE +
                " WHERE " + CONST.COMPLAINTS_NUMBER_OF_CARD + " = " + table.getValueAt(selectedRows[0], 2) +
                " AND " + CONST.COMPLAINTS_DATE_OF_RECEPTION + " = DATE '" + table.getValueAt(selectedRows[0], 0) + "'" +
                " AND " + CONST.COMPLAINTS_TIME_OF_RECEPTION + " = '" + table.getValueAt(selectedRows[0], 1) + "'";

        ResultSet resSet = null;
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select);

            while(resSet.next())
            {
                complaint = resSet.getString(CONST.COMPLAINTS_COMPLAINTS);
            }
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return complaint;
    }

    public String getRecomendation(int[] selectedRows, JTable table)
    {
        String recomendation = "";

        String select =
                "SELECT recom." + CONST.RECOMENDATION_RECOMENDATION +
                " FROM " + CONST.RECOMENDATION_TABLE + " recom INNER JOIN " + CONST.COMPLAINTS_TABLE + " com" +
                " ON recom." + CONST.RECOMENDATION_RECOMENDATION_CODE + " = com." + CONST.COMPLAINTS_RECOMENDATION_CODE +
                " WHERE " + CONST.COMPLAINTS_NUMBER_OF_CARD + " = " + table.getValueAt(selectedRows[0], 2) +
                " AND " + CONST.COMPLAINTS_DATE_OF_RECEPTION + " = DATE '" + table.getValueAt(selectedRows[0], 0) + "'" +
                " AND " + CONST.COMPLAINTS_TIME_OF_RECEPTION + " = '" + table.getValueAt(selectedRows[0], 1) + "'";

        ResultSet resSet = null;
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select);

            while(resSet.next())
            {
                recomendation = resSet.getString(CONST.RECOMENDATION_RECOMENDATION);
            }
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return recomendation;
    }

    public void writeComplaintsANDRecomendationInBD(String complaint, String recomendation, String numberOfCard, String doctorFIO, String date, String time, int tableNumber)
    {
        String insert = "INSERT INTO " + CONST.COMPLAINTS_TABLE + "(" + CONST.COMPLAINTS_COMPLAINTS + ", " + CONST.COMPLAINTS_NUMBER_OF_CARD + ", " + CONST.COMPLAINTS_DATE_OF_RECEPTION + ", " + CONST.COMPLAINTS_TIME_OF_RECEPTION + ")" +
                        " VALUES ('" + complaint + "', '" + numberOfCard + "', DATE '" + date + "', '" + time + "')";
        String insert2 = "INSERT INTO " + CONST.RECOMENDATION_TABLE + "(" + CONST.RECOMENDATION_RECOMENDATION + ", " + CONST.RECOMENDATION_RECOMENDATION_CODE + ", " + CONST.RECOMENDATION_DOCTORSFIO + ")" +
                " VALUES ('" + recomendation + "', ?, '" + doctorFIO + "')";
        String select = "SELECT * FROM " + CONST.COMPLAINTS_TABLE + " WHERE " + CONST.COMPLAINTS_NUMBER_OF_CARD + " = '" + numberOfCard +
                        "' AND " + CONST.COMPLAINTS_COMPLAINTS + " = '" + complaint + "'";
        String update = "UPDATE " + CONST.RECEPTION_TABLE + " SET " + CONST.RECEPTION_IS_VISIT + " = true" +
                        " WHERE " + CONST.RECEPTION_TABLE_NUMBER + " = " + String.valueOf(tableNumber) + " AND " + CONST.RECEPTION_NUMBER_OF_CARD + " = '" + numberOfCard +
                        "' AND " + CONST.RECEPTION_DATE_OF_RECEPTION + " = DATE '" + date + "' AND " + CONST.RECEPTION_TIME_OF_RECEPTION + " = '" + time + "'";

        PreparedStatement prSt = null;
        Statement st = null;
        Statement st2 = null;
        Statement st3 = null;
        ResultSet resSet = null;
        try
        {
            st = getDbConnecion().createStatement();
            st2 = getDbConnecion().createStatement();
            st3 = getDbConnecion().createStatement();
            prSt = getDbConnecion().prepareStatement(insert2);

            st.executeUpdate(insert);

            resSet = st2.executeQuery(select);
            while(resSet.next())
            {
                prSt.setInt(1, resSet.getInt(CONST.COMPLAINTS_RECOMENDATION_CODE));
            }
            prSt.executeUpdate();

            st3.executeUpdate(update);
        } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally
        {
            try {
                dbDisconnectionResSet(resSet);
                dbDisconnectionSt(st);
                dbDisconnectionSt(st2);
                dbDisconnectionSt(st3);
                dbDisconnectionPrSt(prSt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getComplaintsID(String date, String time, String numberOfCard)
    {
        int complaintID = 0;

        String select =
                "SELECT * FROM " + CONST.COMPLAINTS_TABLE +
                        " WHERE " + CONST.COMPLAINTS_NUMBER_OF_CARD + " = " + numberOfCard +
                        " AND " + CONST.COMPLAINTS_DATE_OF_RECEPTION + " = DATE '" + date + "'" +
                        " AND " + CONST.COMPLAINTS_TIME_OF_RECEPTION + " = '" + time + "'";

        ResultSet resSet = null;
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select);

            while(resSet.next())
            {
                complaintID = resSet.getInt(CONST.COMPLAINTS_RECOMENDATION_CODE);
            }
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return complaintID;
    }

    public void rewriteComplaintsANDRecomendationInBD(String complaint, String recomendation, String numberOfCard, String doctorFIO, String date, String time, int tableNumber)
    {
        String update = "UPDATE " + CONST.COMPLAINTS_TABLE + " SET " + CONST.COMPLAINTS_COMPLAINTS + " = '" + complaint + "'" +
                " WHERE " + CONST.COMPLAINTS_NUMBER_OF_CARD + " = " + numberOfCard +
                " AND " + CONST.COMPLAINTS_DATE_OF_RECEPTION + " = DATE '" + date + "' AND " + CONST.COMPLAINTS_TIME_OF_RECEPTION + " = '" + time + "'";

        String update2 = "UPDATE " + CONST.RECOMENDATION_TABLE + " SET " + CONST.RECOMENDATION_RECOMENDATION + " = '" + recomendation + "'" +
                " WHERE " + CONST.RECOMENDATION_RECOMENDATION_CODE + " = " + getComplaintsID(date, time, numberOfCard);

        Statement st = null;
        Statement st2 = null;
        try
        {
            st = getDbConnecion().createStatement();
            st2 = getDbConnecion().createStatement();

            st.executeUpdate(update);
            st2.executeUpdate(update2);
        } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally
        {
            try {
                dbDisconnectionSt(st);
                dbDisconnectionSt(st2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getAllReceptionDoneToDoctor(Statement statement, int tableNumber)
    {
        String select =
                "SELECT rec." + CONST.RECEPTION_DATE_OF_RECEPTION + ", rec." + CONST.RECEPTION_TIME_OF_RECEPTION + ", rec." + CONST.RECEPTION_NUMBER_OF_CARD + ", CONCAT(p." +
                        CONST.PATIENT_SECONDNAME + ", ' ', p." + CONST.PATIENT_FIRSTNAME + ", ' ', p." + CONST.PATIENT_THIRDNAME + "), pl." + CONST.PLOTS_NUMBER + ", rec." + CONST.RECEPTION_IS_VISIT +
                        " FROM " + CONST.RECEPTION_TABLE + " rec INNER JOIN " + CONST.DOCTOR_TABLE + " doc INNER JOIN " + CONST.SPECIALIZATION_TABLE + " sp INNER JOIN " + CONST.PATIENT_TABLE + " p INNER JOIN " + CONST.PLOT_TABLE + " pl" +
                        " ON doc." + CONST.DOCTORS_TABLE_NUMBER + " = rec." + CONST.RECEPTION_TABLE_NUMBER + " AND sp." + CONST.SPECIALIZATION_CODE + " = doc." + CONST.DOCTORS_SPECIALIZATION_CODE + " AND doc." +
                        CONST.DOCTORS_TABLE_NUMBER + " = pl." + CONST.PLOTS_DOCTORS_TABLE_NUMBER + " AND p." + CONST.PATIENT_NUMBER_OF_CARD + " = rec." + CONST.RECEPTION_NUMBER_OF_CARD +
                        " WHERE rec." + CONST.RECEPTION_TABLE_NUMBER + " = " + String.valueOf(tableNumber) + " AND rec." + CONST.RECEPTION_IS_VISIT + " = true";

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getAllReceptionToDoctor(Statement statement, int tableNumber)
    {
        String select =
                "SELECT rec." + CONST.RECEPTION_DATE_OF_RECEPTION + ", rec." + CONST.RECEPTION_TIME_OF_RECEPTION + ", rec." + CONST.RECEPTION_NUMBER_OF_CARD + ", CONCAT(p." +
                        CONST.PATIENT_SECONDNAME + ", ' ', p." + CONST.PATIENT_FIRSTNAME + ", ' ', p." + CONST.PATIENT_THIRDNAME + "), pl." + CONST.PLOTS_NUMBER + ", rec." + CONST.RECEPTION_IS_VISIT +
                        " FROM " + CONST.RECEPTION_TABLE + " rec INNER JOIN " + CONST.DOCTOR_TABLE + " doc INNER JOIN " + CONST.SPECIALIZATION_TABLE + " sp INNER JOIN " + CONST.PATIENT_TABLE + " p INNER JOIN " + CONST.PLOT_TABLE + " pl" +
                        " ON doc." + CONST.DOCTORS_TABLE_NUMBER + " = rec." + CONST.RECEPTION_TABLE_NUMBER + " AND sp." + CONST.SPECIALIZATION_CODE + " = doc." + CONST.DOCTORS_SPECIALIZATION_CODE + " AND doc." +
                        CONST.DOCTORS_TABLE_NUMBER + " = pl." + CONST.PLOTS_DOCTORS_TABLE_NUMBER + " AND p." + CONST.PATIENT_NUMBER_OF_CARD + " = rec." + CONST.RECEPTION_NUMBER_OF_CARD +
                        " WHERE rec." + CONST.RECEPTION_TABLE_NUMBER + " = " + String.valueOf(tableNumber) + " AND rec." + CONST.RECEPTION_IS_VISIT + " = false";

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getAllReception(Statement statement)
    {
        String select =
                "SELECT rec." + CONST.RECEPTION_TABLE_NUMBER + ", sp." + CONST.SPECIALIZATION + ", CONCAT(doc." + CONST.DOCTORS_SECONDNAME + ", ' ', doc." + CONST.DOCTORS_FIRSTNAME + ", ' ', doc." + CONST.DOCTORS_THIRDNAME + "), rec." +
                CONST.RECEPTION_DATE_OF_RECEPTION + ", rec." + CONST.RECEPTION_TIME_OF_RECEPTION + ", rec." + CONST.RECEPTION_NUMBER_OF_CARD + ", CONCAT(p." +
                CONST.PATIENT_SECONDNAME + ", ' ', p." + CONST.PATIENT_FIRSTNAME + ", ' ', p." + CONST.PATIENT_THIRDNAME + "), pl." + CONST.PLOTS_NUMBER + ", rec." + CONST.RECEPTION_IS_VISIT +
                " FROM " + CONST.RECEPTION_TABLE + " rec INNER JOIN " + CONST.DOCTOR_TABLE + " doc INNER JOIN " + CONST.SPECIALIZATION_TABLE + " sp INNER JOIN " + CONST.PATIENT_TABLE + " p INNER JOIN " + CONST.PLOT_TABLE + " pl" +
                " WHERE doc." + CONST.DOCTORS_TABLE_NUMBER + " = rec." + CONST.RECEPTION_TABLE_NUMBER + " AND sp." + CONST.SPECIALIZATION_CODE + " = doc." + CONST.DOCTORS_SPECIALIZATION_CODE + " AND doc." +
                CONST.DOCTORS_TABLE_NUMBER + " = pl." + CONST.PLOTS_DOCTORS_TABLE_NUMBER + " AND p." + CONST.PATIENT_NUMBER_OF_CARD + " = rec." + CONST.RECEPTION_NUMBER_OF_CARD + " AND rec." + CONST.RECEPTION_IS_VISIT + " = false";

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getReceptionForCheck(int tableNumber, String date, Statement statement)
    {
        String select = "SELECT * FROM " + CONST.RECEPTION_TABLE +
                        " WHERE " + CONST.RECEPTION_TABLE_NUMBER + " = " + tableNumber +
                        " AND " + CONST.RECEPTION_DATE_OF_RECEPTION + " = DATE '" + date + "'";

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ArrayList getAllTime()
    {
        ArrayList timeList = new ArrayList();

        String select = "SELECT * FROM " + CONST.TIME_TABLE;

        Statement st = null;
        ResultSet resSet = null;
        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try
        {
            while(resSet.next())
            {
                ArrayList temp = new ArrayList();
                temp.add(resSet.getInt(CONST.TIME_ID));
                temp.add(resSet.getString(CONST.TIME_HOURS));

                timeList.add(temp);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return timeList;
    }

    public Vector<String> getTime()
    {
        Vector<String> timeList = new Vector();

        String select = "SELECT " + CONST.TIME_HOURS + " FROM " + CONST.TIME_TABLE;

        Statement st = null;
        ResultSet resSet = null;
        try
        {
            st = getDbConnecion().createStatement();
            resSet = st.executeQuery(select); // Выполнить SQL запрос о взятии данных из БД (и записать результат в переменную)
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try
        {
            while(resSet.next())
            {
                timeList.add(resSet.getString(CONST.TIME_HOURS));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionSt(st);
                dbDisconnectionResSet(resSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return timeList;
    }

    public ResultSet getDoctorsForReferenceTable(Statement statement)
    {
        String select = "SELECT " + "doc." + CONST.DOCTORS_TABLE_NUMBER + "," + CONST.DOCTORS_SECONDNAME + "," + CONST.DOCTORS_FIRSTNAME + "," + CONST.DOCTORS_THIRDNAME + "," +
                CONST.DOCTORS_GENDER + "," + CONST.DOCTORS_BIRTHDAY + "," +
                "sp." + CONST.SPECIALIZATION + "," + "sp." + CONST.SPECIALIZATION_CODE + "," +
                "doc." + CONST.DOCTORS_DATE_START_JOB + "," + "pl." + CONST.PLOTS_NUMBER +
                " FROM " + CONST.DOCTOR_TABLE + " doc INNER JOIN " + CONST.SPECIALIZATION_TABLE + " sp INNER JOIN " + CONST.PLOT_TABLE + " pl" +
                " ON doc." + CONST.DOCTORS_TABLE_NUMBER + "=pl." + CONST.PLOTS_DOCTORS_TABLE_NUMBER +
                " WHERE doc." + CONST.DOCTORS_SPECIALIZATION_CODE + "=sp." + CONST.SPECIALIZATION_CODE;

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getPatientsForReferenceTable(Statement statement)
    {
        String select = "SELECT " + CONST.PATIENT_SECONDNAME + ", " + CONST.PATIENT_FIRSTNAME + ", " + CONST.PATIENT_THIRDNAME + ", " +
                        CONST.PATIENT_GENDER + ", " + CONST.PATIENT_BIRTHDAY + ", " + CONST.PATIENT_NUMBER_OF_PLOT + ", " +
                        CONST.PATIENT_DATE_OF_CREATE_CARD + ", " + CONST.PATIENT_NUMBER_OF_CARD + ", " + CONST.PATIENT_NUMBER_OF_PASPORT + ", " +
                        CONST.PATIENT_NUMBER_OF_PHONE + ", " + CONST.PATIENT_INSURANCE_POLICY + ", " +
                        CONST.PATIENT_STREET + ", " + CONST.PATIENT_BUILDING + ", " + CONST.PATIENT_APARTMENT +
                        " FROM " + CONST.PATIENT_TABLE;

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getTimeBeginEnd(Statement statement, int tableNumber)
    {
        String join = "SELECT " + CONST.TIMEOFJOB_ID_TIME_BEGIN + ", " + CONST.TIMEOFJOB_ID_TIME_END + " FROM " + CONST.TIMEOFJOB_TABLE;

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(join);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getTimetableForReferenceTable(Statement statement)
    {
        String join = "SELECT CONCAT(doc." + CONST.DOCTORS_SECONDNAME + ", ' ' , doc." + CONST.DOCTORS_FIRSTNAME + ", ' ', doc." + CONST.DOCTORS_THIRDNAME + "), " +
        "sp." + CONST.SPECIALIZATION + ", tt." + CONST.CABINETS_NUMBER + ", d." + CONST.DAY_DAY + ", t." + CONST.TIME_HOURS + ", ti." + CONST.TIME_HOURS + ", w." + CONST.WEEK_DATE + ", doc." + CONST.DOCTORS_TABLE_NUMBER + ", tj." + CONST.TIMEOFJOB_ID_WEEK +
        " FROM " + CONST.DOCTOR_TABLE + " doc INNER JOIN " + CONST.TIMETABLE_TABLE + " tt INNER JOIN " + CONST.SPECIALIZATION_TABLE + " sp" +
        " INNER JOIN " + CONST.DAY_TABLE + " d INNER JOIN " + CONST.TIMETABLEDAY_TABLE + " ttd INNER JOIN " + CONST.TIMETABLETIME_TABLE + " ttt" +
        " INNER JOIN " + CONST.TIME_TABLE + " t INNER JOIN " + CONST.TIME_TABLE + " ti INNER JOIN " + CONST.CABINETS_TABLE + " cab INNER JOIN " + CONST.WEEK_TABLE + " w INNER JOIN " + CONST.TIMEOFJOB_TABLE + " tj" +
        " ON 	tt." + CONST.TIMETABLE_TABLE_NUMBER + " = doc." + CONST.DOCTORS_TABLE_NUMBER +
        " AND sp." + CONST.SPECIALIZATION_CODE + " = doc." + CONST.DOCTORS_SPECIALIZATION_CODE +
        " AND cab." + CONST.CABINETS_NUMBER + " = tt." + CONST.TIMETABLE_NUMBER_CABINET +
        " AND ttd." + CONST.TIMETABLEDAY_TABLE_NUMBER + " = tt." + CONST.TIMETABLE_TABLE_NUMBER +
        " AND ttd." + CONST.TIMETABLEDAY_ID_DAY + " = d." + CONST.DAY_ID +
        " AND ttt." + CONST.TIMETABLETIME_ID_DAY + " = ttd." + CONST.TIMETABLEDAY_ID_DAY +
        " AND ttt." + CONST.TIMETABLETIME_TABLE_NUMBER + " = tt." + CONST.TIMETABLE_TABLE_NUMBER +
        " AND tj." + CONST.TIMEOFJOB_ID_TIME_BEGIN + " = t." + CONST.TIME_ID +
        " AND tj." + CONST.TIMEOFJOB_ID_TIME_END + " = ti." + CONST.TIME_ID +
        " AND w." + CONST.WEEK_ID_DAY + " = d." + CONST.DAY_ID +
        " AND tj." + CONST.TIMEOFJOB_TABLE_NUMBER + " = ttt." + CONST.TIMETABLETIME_TABLE_NUMBER +
        " AND tj." + CONST.TIMEOFJOB_ID_WEEK + " = w." + CONST.WEEK_ID +
        " ORDER BY doc." + CONST.DOCTORS_TABLE_NUMBER + ", w." + CONST.WEEK_ID_DAY;

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(join);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet getWeek(Statement statement)
    {
        String select = "SELECT * FROM " + CONST.WEEK_TABLE;

        ResultSet resSet = null;
        Statement st = statement;

        try
        {
            resSet = st.executeQuery(select);
        }
        catch (SQLException e) // Если произошла ошибка в самой БД (Не создан Connection)
        {
            System.out.println(e);
        }

        return resSet;
    }

    public void rewriteDaysInWeeks()
    {
        Statement st = null;
        CallableStatement cs = null;
        //String execute = "DROP TABLE IF EXISTS temp; CREATE TABLE temp SELECT * FROM " + CONST.TIMEOFJOB_TABLE + "; SET SQL_SAFE_UPDATES = 0; CALL " + CONST.PROCEDURE_OFFSET_FINAL + "; DEALLOCATE cur; SET SQL_SAFE_UPDATES = 1;";

        try
        {
            cs = getDbConnecion().prepareCall("{ CALL " + CONST.PROCEDURE_OFFSET_FINAL + "}");
            cs.execute();
            //st = getDbConnecion().createStatement();
            //st.executeUpdate(execute);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally // Нужно обязательно закрыть подключение
        {
            try
            {
                dbDisconnectionCs(cs);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean executeDB(ArrayList<String> executeList)
    {
        Statement st = null;

        try
        {
            st = getDbConnecion().createStatement();
            for(String execute : executeList) // Поочерёдно выполнять обновление строк БД
            {
                st.executeUpdate(execute);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                dbDisconnectionSt(st);
            } catch (SQLException ee) { ee.printStackTrace(); return false; }
            return false;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            try
            {
                dbDisconnectionSt(st);
            } catch (SQLException ee) { ee.printStackTrace(); return false; }
            return false;
        }

        return true;
    }
}
