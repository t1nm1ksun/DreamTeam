import java.util.Arrays;

public class CsvValidator {
    static boolean validate(){
        boolean isFormatValidated = validateFormat();
        boolean isRefValidated = validateRef();
        boolean areInformationsValidated = validateInformations();
        return isFormatValidated && isRefValidated && areInformationsValidated;
    }

    private static boolean validateFormat(){
        return Read.validateCSVListFormat(Arrays.asList(Main.subjectManager, Main.timetableManager, Main.lectureManager, Main.lectureroomManager, Main.studentManager, Main.teacherManager));
    }

    private static boolean validateInformations(){
        if(!Read.validatePhoneNumberDupliacated(Arrays.asList(Main.studentManager))) return false;
        if(!Read.validateTimetableInfoDuplicated(Main.timetableManager)) return false;
        if(!Read.validateTimetableIdDupliacated(Arrays.asList(Main.timetableManager))) return false;
        if(!Read.validateDivisionCodeDuplicated(Arrays.asList(Main.studentManager))) return false;
        if(!Read.validateDivisionHasOverThanRoomLimit(Main.divisionManager, Main.lectureroomManager, Main.timetableManager)) return false;
        if(!Read.validateStudentHasDuplicatedLecture(Main.studentManager, Main.divisionManager)) return false;
        if(!Read.validateStudentCountOverThanDivisionLimit(Main.studentManager, Main.divisionManager)) return false;
        return true;
    }

    private static boolean validateRef(){
        boolean isValidateRefFromTeacher = validateRefFromTeacher();
        boolean isValidateRefFromStudent = validateRefFromStudent();
        boolean isValidateRefFromTimetable = validateRefFromTimetable();
        boolean isValidateRefFromDivision = validateRefFromDivision();
        boolean isValidateRefFromLecture = validateRefFromLecture();

        return (isValidateRefFromTeacher && isValidateRefFromStudent && isValidateRefFromTimetable && isValidateRefFromDivision && isValidateRefFromLecture);
    }

    private static boolean validateRefFromTeacher(){
        boolean teacherToSubjects = Read.validateCSVRef(Main.teacherManager, Main.subjectManager, "+2", "0");
        return teacherToSubjects;
    }

    private static boolean validateRefFromStudent(){
        boolean studentToDivisions = Read.validateCSVRef(Main.studentManager, Main.divisionManager, "+3", "0");
        return studentToDivisions;
    }

    private static boolean validateRefFromTimetable(){
        boolean timetableToLectureroom = Read.validateCSVRef(Main.timetableManager, Main.lectureroomManager, "1", "0");
        return timetableToLectureroom;
    }

    private static boolean validateRefFromDivision(){
        boolean divisionToLecture = Read.validateCSVRef(Main.divisionManager, Main.lectureManager, "1","0");
        boolean divisionToTeacher = Read.validateCSVRef(Main.divisionManager, Main.teacherManager, "2","0");
        boolean divisionToTimetables = Read.validateCSVRef(Main.divisionManager, Main.timetableManager, "+4", "0");
        return (divisionToLecture && divisionToTeacher && divisionToTimetables);
    }

    private static boolean validateRefFromLecture(){
        boolean lectureToSubject = Read.validateCSVRef(Main.lectureManager, Main.subjectManager, "2", "0");
        return lectureToSubject;
    }
}
