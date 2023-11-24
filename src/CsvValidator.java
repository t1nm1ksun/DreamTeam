import java.util.Arrays;

public class CsvValidator {
    static boolean validate(){
        boolean isFormatValidated = validateFormat();
        boolean isRefValidated = validateRef();
        boolean areInformationsValidated = validateInformations();
        return isFormatValidated && isRefValidated && areInformationsValidated;
    }

    static boolean validateFormat(){
        return Read.validateCSVListFormat(Arrays.asList(Main.subjectManager, Main.timetableManager, Main.lectureManager, Main.lectureroomManager, Main.studentManager, Main.teacherManager));
    }

    static boolean validateInformations(){
        if(!Read.validatePhoneNumberDupliacated(Arrays.asList(Main.studentManager))) return false;
        if(!Read.validateTimetableInfoDuplicated(Main.timetableManager)) return false;
        if(!Read.validateTimetableIdDupliacated(Arrays.asList(Main.timetableManager))) return false;

        return true;
    }

    static boolean validateRef(){
        boolean isValidateRefFromTeacher = validateRefFromTeacher();
        boolean isValidateRefFromStudent = validateRefFromStudent();
        boolean isValidateRefFromTimetable = validateRefFromTimetable();
        boolean isValidateRefFromDivision = validateRefFromDivision();
        boolean isValidateRefFromLecture = validateRefFromLecture();

        return (isValidateRefFromTeacher && isValidateRefFromStudent && isValidateRefFromTimetable && isValidateRefFromDivision && isValidateRefFromLecture);
    }

    static boolean validateRefFromTeacher(){
        boolean teacherToSubjects = Read.validateCSVRef(Main.teacherManager, Main.subjectManager, "+2", "0");
        return teacherToSubjects;
    }

    static boolean validateRefFromStudent(){
        boolean studentToDivisions = Read.validateCSVRef(Main.studentManager, Main.divisionManager, "+3", "0");
        return studentToDivisions;
    }

    static boolean validateRefFromTimetable(){
        boolean timetableToLectureroom = Read.validateCSVRef(Main.timetableManager, Main.lectureroomManager, "1", "0");
        return timetableToLectureroom;
    }

    static boolean validateRefFromDivision(){
        boolean divisionToLecture = Read.validateCSVRef(Main.divisionManager, Main.lectureManager, "1","0");
        boolean divisionToTeacher = Read.validateCSVRef(Main.divisionManager, Main.teacherManager, "2","0");
        boolean divisionToTimetables = Read.validateCSVRef(Main.divisionManager, Main.timetableManager, "+4", "0");
        return (divisionToLecture && divisionToTeacher && divisionToTimetables);
    }

    static boolean validateRefFromLecture(){
        boolean lectureToSubject = Read.validateCSVRef(Main.lectureManager, Main.subjectManager, "2", "0");
        return lectureToSubject;
    }
}
