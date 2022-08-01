package Connector;

public class CONST // Класс для упрощения обращения к соответствующим таблицам и полям в БД
{
    public static final String DB = "registry";

    public static final String PROCEDURE_OFFSET_FINAL = "offsetFinal()";
//////////////ADMIN_TABLE(begin)//////////////////////////////////////////////////////////////////////////////////////
    public static final String ADMIN_TABLE = "admins"; // Использование без создания экземпляра класса

    public static final String ADMINS_ID = "idAdmins";
    public static final String ADMINS_SECONDNAME = "secondName";
    public static final String ADMINS_FIRSTNAME = "firstName";
    public static final String ADMINS_THIRDNAME = "thirdName";
    public static final String ADMINS_GENDER = "gender";
    public static final String ADMINS_BIRTHDAY = "birthday";
    public static final String ADMINS_PASSWORD = "password";
    public static final String ADMINS_MAIL = "mailAddress";
    public static final String ADMINS_CITY = "city";
    public static final String ADMINS_STREET = "street";
    public static final String ADMINS_BUILDING = "building";
    public static final String ADMINS_APARTMENT = "apartment";
//////////////ADMIN_TABLE(end)////////////////////////////////////////////////////////////////////////////////////////

//////////////DOCTOR_TABLE(begin)//////////////////////////////////////////////////////////////////////////////////////
    public static final String DOCTOR_TABLE = "doctors"; // Использование без создания экземпляра класса

    public static final String DOCTORS_SECONDNAME = "secondName";
    public static final String DOCTORS_FIRSTNAME = "firstName";
    public static final String DOCTORS_THIRDNAME = "thirdName";
    public static final String DOCTORS_GENDER = "gender";
    public static final String DOCTORS_BIRTHDAY = "birthday";
    public static final String DOCTORS_TABLE_NUMBER = "tableNumber";
    public static final String DOCTORS_SPECIALIZATION_CODE = "specializationCode";
    public static final String DOCTORS_DATE_START_JOB = "dateOfStartJob";
    public static final String DOCTORS_PASSWORD = "password";
    public static final String DOCTORS_MAIL = "mailAddress";
    public static final String DOCTORS_CITY = "city";
    public static final String DOCTORS_STREET = "street";
    public static final String DOCTORS_BUILDING = "building";
    public static final String DOCTORS_APARTMENT = "apartment";
//////////////DOCTOR_TABLE(end)////////////////////////////////////////////////////////////////////////////////////////

//////////////PATIENT_TABLE(begin)//////////////////////////////////////////////////////////////////////////////////////
    public static final String PATIENT_TABLE = "patient"; // Использование без создания экземпляра класса

    public static final String PATIENT_ID = "idPatient";
    public static final String PATIENT_SECONDNAME = "secondName";
    public static final String PATIENT_FIRSTNAME = "firstName";
    public static final String PATIENT_THIRDNAME = "thirdName";
    public static final String PATIENT_GENDER = "gender";
    public static final String PATIENT_BIRTHDAY = "birthday";
    public static final String PATIENT_NUMBER_OF_PLOT = "numberOfPlot";
    public static final String PATIENT_DATE_OF_CREATE_CARD = "dateOfCreateCard";
    public static final String PATIENT_NUMBER_OF_CARD = "numberOfCard";
    public static final String PATIENT_NUMBER_OF_PASPORT = "numberOfPasport";
    public static final String PATIENT_NUMBER_OF_PHONE = "numberOfPhone";
    public static final String PATIENT_INSURANCE_POLICY = "insurancePolicy";
    public static final String PATIENT_CITY = "city";
    public static final String PATIENT_STREET = "street";
    public static final String PATIENT_BUILDING = "building";
    public static final String PATIENT_APARTMENT = "apartment";
//////////////PATIENT_TABLE(end)////////////////////////////////////////////////////////////////////////////////////////

//////////////ADDRESSPLOT_TABLE(begin)//////////////////////////////////////////////////////////////////////////////////////
    public static final String ADDRESSPLOT_TABLE = "addressplot"; // Использование без создания экземпляра класса

    public static final String ADDRESSPLOT_ID = "idAddress";
    public static final String ADDRESSPLOT_NUMBER_OF_PLOT = "numberOfPlot";
    public static final String ADDRESSPLOT_STREET = "street";
    public static final String ADDRESSPLOT_BUILDING_COUNT = "buildingCount";
//////////////ADDRESSPLOT_TABLE(end)////////////////////////////////////////////////////////////////////////////////////////

//////////////PLOT_TABLE(begin)//////////////////////////////////////////////////////////////////////////////////////
    public static final String PLOT_TABLE = "plots"; // Использование без создания экземпляра класса

    public static final String PLOTS_ID = "idPlots";
    public static final String PLOTS_DOCTORS_TABLE_NUMBER = "tableNumber";
    public static final String PLOTS_NUMBER = "numberOfPlot";
//////////////PLOT_TABLE(end)////////////////////////////////////////////////////////////////////////////////////////

//////////////SPECIALIZATION_TABLE(begin)//////////////////////////////////////////////////////////////////////////////////////
    public static final String SPECIALIZATION_TABLE = "specialization"; // Использование без создания экземпляра класса

    public static final String SPECIALIZATION_CODE = "specializationCode";
    public static final String SPECIALIZATION = "specialization";
//////////////SPECIALIZATION_TABLE(end)////////////////////////////////////////////////////////////////////////////////////////

//////////////CABINETS_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String CABINETS_TABLE = "cabinets";

    public static final String CABINETS_ID = "idcabinets";
    public static final String CABINETS_NUMBER = "numberOfCabinet";
//////////////CABINETS_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////TIMETABLE_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String TIMETABLE_TABLE = "timetable";

    public static final String TIMETABLE_ID = "idTimetable";
    public static final String TIMETABLE_TABLE_NUMBER = "tableNumber";
    public static final String TIMETABLE_NUMBER_CABINET = "numberOfCabinet";
//////////////TIMETABLE_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////TIMETABLETIME_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String TIMETABLETIME_TABLE = "timetabletime";

    public static final String TIMETABLETIME_ID = "idTimetableTime";
    public static final String TIMETABLETIME_TABLE_NUMBER = "tableNumber";
    public static final String TIMETABLETIME_ID_DAY = "idDay";
//////////////TIMETABLETIME_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////TIMETABLEDAY_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String TIMETABLEDAY_TABLE = "timetableday";

    public static final String TIMETABLEDAY_ID = "idTimetableDay";
    public static final String TIMETABLEDAY_TABLE_NUMBER = "tableNumber";
    public static final String TIMETABLEDAY_ID_DAY = "idDay";
//////////////TIMETABLEDAY_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////TIME_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String TIME_TABLE = "time";

    public static final String TIME_ID = "idTime";
    public static final String TIME_HOURS = "Hours";
//////////////TIME_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////DAY_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String DAY_TABLE = "day";

    public static final String DAY_ID = "idDay";
    public static final String DAY_DAY = "Day";

    public static final String DAY_MONDAY = "Понедельник";
    public static final String DAY_TUESDAY = "Вторник";
    public static final String DAY_WEDNESDAY = "Среда";
    public static final String DAY_THURSDAY = "Четверг";
    public static final String DAY_FRIDAY = "Пятница";
    public static final String DAY_SATURDAY = "Суббота";
//////////////DAY_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////WEEK_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String WEEK_TABLE = "weeks";

    public static final String WEEK_ID = "idWeek";
    public static final String WEEK_DATE = "dateWeeks";
    public static final String WEEK_ID_DAY = "idDay";
//////////////WEEK_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////TIMEOFJOB_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String TIMEOFJOB_TABLE = "timeofjob";

    public static final String TIMEOFJOB_ID = "idTimeOfJob";
    public static final String TIMEOFJOB_TABLE_NUMBER = "tableNumber";
    public static final String TIMEOFJOB_ID_TIME_BEGIN = "idTimeBegin";
    public static final String TIMEOFJOB_ID_TIME_END = "idTimeEnd";
    public static final String TIMEOFJOB_ID_WEEK = "idWeek";
//////////////TIMEOFJOB_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////RECEPTION_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String RECEPTION_TABLE = "reception";

    public static final String RECEPTION_TABLE_NUMBER = "tableNumber";
    public static final String RECEPTION_NUMBER_OF_CARD = "numberOfCard";
    public static final String RECEPTION_DATE_OF_RECEPTION = "dateOfReception";
    public static final String RECEPTION_TIME_OF_RECEPTION = "timeOfReception";
    public static final String RECEPTION_IS_VISIT = "isVisit";
//////////////RECEPTION_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////COMPLAINTS_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String COMPLAINTS_TABLE = "complaints";

    public static final String COMPLAINTS_COMPLAINTS = "complaints";
    public static final String COMPLAINTS_RECOMENDATION_CODE = "recomendationCode";
    public static final String COMPLAINTS_NUMBER_OF_CARD = "numberOfCard";
    public static final String COMPLAINTS_DATE_OF_RECEPTION = "dateOfReception";
    public static final String COMPLAINTS_TIME_OF_RECEPTION = "timeOfReception";
//////////////COMPLAINTS_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////

//////////////COMPLAINTS_TABLE(begin)////////////////////////////////////////////////////////////////////////////////////////
    public static final String RECOMENDATION_TABLE = "recomendation";

    public static final String RECOMENDATION_RECOMENDATION = "recomendation";
    public static final String RECOMENDATION_RECOMENDATION_CODE = "recomendationCode";
    public static final String RECOMENDATION_DOCTORSFIO = "doctorsFIO";
//////////////COMPLAINTS_TABLE(end)//////////////////////////////////////////////////////////////////////////////////////////
}
