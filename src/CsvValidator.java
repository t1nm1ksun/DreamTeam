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

    static boolean validateRef(){
        if (!Read.validateCSVRef(Main.timetableManager, Main.lectureroomManager, "1", "0")) return false;
        if (!Read.validateCSVRef(Main.lectureManager, Main.timetableManager, "+5", "0")) return false;
        if (!Read.validateCSVRef(Main.timetableManager, Main.lectureroomManager, "1", "0")) return false;
        if (!Read.validateCSVRef(Main.lectureManager, Main.subjectManager, "1", "0")) return false;
        if (!Read.validateCSVRef(Main.lectureManager, Main.teacherManager, "2", "0")) return false;
        if (!Read.validateCSVRef(Main.lectureManager, Main.timetableManager, "+5", "0")) return false;
        if (!Read.validateCSVRef(Main.teacherManager, Main.subjectManager, "+2", "0")) return false;
        if (!Read.validateCSVRef(Main.studentManager, Main.lectureManager, "+3", "0")) return false;
        return true;
    }

    static boolean validateInformations(){
        if(!Read.validatePhoneNumberDupliacated(Arrays.asList(Main.studentManager))) return false;
        if(!Read.validateTimetableIdDupliacated(Arrays.asList(Main.lectureManager, Main.teacherManager))) return false;
        if(!Read.validateTimetableInfoDuplicated(Main.timetableManager)) return false;
        if(!Read.validateLectureHasOverStudents(Main.lectureManager)) return false;
        return true;
    }
}
