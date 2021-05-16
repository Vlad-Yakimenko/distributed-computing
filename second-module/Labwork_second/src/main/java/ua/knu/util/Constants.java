package ua.knu.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException(Other.UTILITY_CLASS);
    }

    public static class Student {
        private Student() {
            throw new IllegalStateException(Other.UTILITY_CLASS);
        }

        public static final String STUDENT_ENTITY = "student";
        public static final String STUDENTS = "students";
        public static final String STUDENT_TITLE = "studentsTitle";

        public static final String STUDENT_ID = "studentId";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";

        public static final String UPDATED_FIRST_NAME = "updatedFirstName";
        public static final String UPDATED_LAST_NAME = "updatedLastName";
        public static final String UPDATED_GROUP_ID = "updatedGroupId";
    }

    public static class Group {
        private Group() {
            throw new IllegalStateException(Other.UTILITY_CLASS);
        }

        public static final String GROUP_ENTITY = "group";
        public static final String GROUPS = "groups";
        public static final String GROUPS_TITLE = "groupsTitle";

        public static final String GROUP_ID = "groupId";
        public static final String GROUP_NAME = "groupName";
        public static final String COURSE = "course";
        public static final String UPDATED_GROUP_NAME = "updatedGroupName";
        public static final String UPDATED_GROUP_COURSE = "updatedGroupCourse";
    }

    public static class Other {
        private Other() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final String ENTITY = "entity";
        public static final String UTILITY_CLASS = "Utility class!";
        public static final String INDEX = "/index.jsp";
    }
}
