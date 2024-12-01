import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();

    private static final Map<String, String> userCredentials = new HashMap<>();
    private static final Map<String, String> userRoles = new HashMap<>();
    private static final Map<String, String> userStudentIdMap = new HashMap<>();

    static {
        // Add credentials and users
        userCredentials.put("admin", "admin1");
        userRoles.put("admin", "Admin");

        userCredentials.put("teacher1", "teacher1");
        userRoles.put("teacher1", "Teacher");

        userCredentials.put("student1", "student1");
        userRoles.put("student1", "Student");
        userStudentIdMap.put("student1", "2024956");

        userCredentials.put("student2", "student2");
        userRoles.put("student2", "Student");
        userStudentIdMap.put("student2", "2024098");

        userCredentials.put("student3", "student3");
        userRoles.put("student3", "Student");
        userStudentIdMap.put("student3", "2024451");

        userCredentials.put("student4", "student4");
        userRoles.put("student4", "Student");
        userStudentIdMap.put("student4", "2024000");

        // Sample courses
        courses.add(new Course("Mathematics", "MATH101"));
        courses.add(new Course("Science", "SCI101"));
        courses.add(new Course("History", "HIST101"));
        courses.add(new Course("English", "ENG101"));
        courses.add(new Course("Computer Science", "CS101"));
        courses.add(new Course("Physics", "PHYS101"));
        courses.add(new Course("Chemistry", "CHEM101"));
        courses.add(new Course("Biology", "BIO101"));
        courses.add(new Course("Art", "ART101"));
        courses.add(new Course("Physical Education", "PE101"));

        // Sample users (dummy accounts)
        students.add(new Student("Arnold Bravo", "2024956"));
        students.add(new Student("Gon Saraza", "2024098"));
        students.add(new Student("Jenny Cruz", "2024451"));
        students.add(new Student("Alice Johnson", "2025001"));
        students.add(new Student("Bob Brown", "2025002"));

        teachers.add(new Teacher("John Smith", "111111"));
        teachers.add(new Teacher("James Gun", "222222"));
        teachers.add(new Teacher("Jane Doe", "333333"));
        teachers.add(new Teacher("Emily Davis", "444444"));
        teachers.add(new Teacher("Michael Scott", "555555"));
        teachers.add(new Teacher("Sarah Connor", "666666"));
        teachers.add(new Teacher("Bruce Wayne", "777777"));
        teachers.add(new Teacher("Clark Kent", "888888"));
        teachers.add(new Teacher("Diana Prince", "999999"));
        teachers.add(new Teacher("Barry Allen", "101010"));

        // Assign subjects to teachers
        teachers.get(0).setSubjects(List.of("Mathematics"));
        teachers.get(1).setSubjects(List.of("History"));
        teachers.get(2).setSubjects(List.of("Science"));
        teachers.get(3).setSubjects(List.of("English"));
        teachers.get(4).setSubjects(List.of("Computer Science"));
        teachers.get(5).setSubjects(List.of("Physics"));
        teachers.get(6).setSubjects(List.of("Chemistry"));
        teachers.get(7).setSubjects(List.of("Biology"));
        teachers.get(8).setSubjects(List.of("Art"));
        teachers.get(9).setSubjects(List.of("Physical Education"));

        // Assign grades to students
        students.get(0).addGrade(courses.get(0), 90, 90, 90, 90, 90, 90, 90.0, "Pass");
        students.get(0).addGrade(courses.get(1), 80, 80, 80, 80, 80, 80, 80.0, "Pass");
        students.get(0).addGrade(courses.get(5), 85, 85, 85, 85, 85, 85, 85.0, "Pass");

        students.get(1).addGrade(courses.get(2), 70, 70, 70, 70, 70, 70, 70.0, "Fail");
        students.get(1).addGrade(courses.get(6), 75, 75, 75, 75, 75, 75, 75.0, "Pass");
        students.get(1).addGrade(courses.get(7), 65, 65, 65, 65, 65, 65, 65.0, "Fail");

        students.get(2).addGrade(courses.get(0), 85, 85, 85, 85, 85, 85, 85.0, "Pass");
        students.get(2).addGrade(courses.get(3), 90, 90, 90, 90, 90, 90, 90.0, "Pass");
        students.get(2).addGrade(courses.get(8), 95, 95, 95, 95, 95, 95, 95.0, "Pass");

        students.get(3).addGrade(courses.get(3), 95, 95, 95, 95, 95, 95, 95.0, "Pass");
        students.get(3).addGrade(courses.get(4), 88, 88, 88, 88, 88, 88, 88.0, "Pass");
        students.get(3).addGrade(courses.get(5), 92, 92, 92, 92, 92, 92, 92.0, "Pass");

        students.get(4).addGrade(courses.get(0), 60, 60, 60, 60, 60, 60, 60.0, "Pass");
        students.get(4).addGrade(courses.get(2), 75, 75, 75, 75, 75, 75, 75.0, "Pass");
        students.get(4).addGrade(courses.get(6), 70, 70, 70, 70, 70, 70, 70.0, "Pass");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        while (true) {
            System.out.println("==========================================");
            System.out.println("||         Welcome to Grading System    ||");
            System.out.println("==========================================");
            System.out.println("|| Please log in to continue            ||");
            System.out.println("==========================================");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            String password;
            if (console == null) {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            } else {
                char[] passwordArray = console.readPassword("Enter Password: ");
                password = new String(passwordArray);
            }
            ClearScreen();

            if (authenticateUser(username, password)) {
                System.out.print("Welcome, " + username + "! ");
                String role = userRoles.get(username);
                switch (role) {
                    case "Admin" -> adminInterface(scanner);
                    case "Teacher" -> teacherInterface(scanner, username);
                    case "Student" -> studentInterface(scanner, username);
                    default -> System.out.println("Invalid role. Exiting system.");
                }
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    private static boolean authenticateUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private static void adminInterface(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=============================");
            System.out.println("====== Admin Interface ======");
            System.out.println("=============================");
            System.out.println("1. Teacher");
            System.out.println("2. Students");
            System.out.println("3. Generate Report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> teacherManagement(scanner);  // Navigate Teacher options
                case 2 -> studentManagement(scanner);  // Navigate Student options
                case 3 -> generateReport();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void teacherManagement(Scanner scanner) {
        int choice;
        do {
            System.out.println("=================================");
            System.out.println("====== Teacher Management =======");
            System.out.println("=================================");
            System.out.println("1. View All Teachers");
            System.out.println("2. Add Teacher");
            System.out.println("3. Remove Teacher");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> viewAllTeachers();
                case 2 -> addTeacher(scanner);
                case 3 -> removeTeacher(scanner);
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void studentManagement(Scanner scanner) {
        int choice;
        do {
            System.out.println("===============================");
            System.out.println("===== Student Management =====");
            System.out.println("===============================");
            System.out.println("1. View All Students with Grades");
            System.out.println("2. Add Student");
            System.out.println("3. Remove Student");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();
    
            switch (choice) {
                case 1 -> viewAllStudents();
                case 2 -> addStudent(scanner);
                case 3 -> removeStudent(scanner);
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void assignGrades(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);
        if (student != null) {
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine();
            Course course = findCourseByCode(courseCode);
            if (course != null) {
                assignGrade(scanner, student, course);
            } else {
                System.out.println("Course not found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void assignGrade(Scanner scanner, Student student, Course course) {
        System.out.print("Enter grade for Quiz 1: ");
        int quiz1 = scanner.nextInt();
        System.out.print("Enter grade for Quiz 2: ");
        int quiz2 = scanner.nextInt();
        System.out.print("Enter grade for Quiz 3: ");
        int quiz3 = scanner.nextInt();
        System.out.print("Enter grade for Project: ");
        int project = scanner.nextInt();
        System.out.print("Enter grade for Summative Exam: ");
        int summativeExam = scanner.nextInt();
        System.out.print("Enter grade for Final Exam: ");
        int finalExam = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        double totalGrade = (quiz1 + quiz2 + quiz3 + project + summativeExam + finalExam) / 6.0;
        String status = totalGrade >= 60 ? "Pass" : "Fail";
        student.addGrade(course, quiz1, quiz2, quiz3, project, summativeExam, finalExam, totalGrade, status);
        System.out.println("Grades assigned to " + student.getName() + " for " + course.getName());
        }

        private static void viewGrades() {
            System.out.println("===============================");
            System.out.println("=== View All Student Grades ===");
            System.out.println("===============================");
            System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", "Student Name", "ID", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Student student : students) {
                boolean firstEntry = true;
                if (student.getGrades().isEmpty()) {
                    // If the student has no grades, still print the student info
                    System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", student.getName(), student.getId(), "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
                } else {
                    for (Map.Entry<Course, Map<String, Integer>> entry : student.getGrades().entrySet()) {
                        Course course = entry.getKey();
                        Map<String, Integer> gradeDetails = entry.getValue();
                        int total = gradeDetails.get("Quiz 1") + gradeDetails.get("Quiz 2") + gradeDetails.get("Quiz 3") + gradeDetails.get("Project") + gradeDetails.get("Summative Exam") + gradeDetails.get("Final Exam");
                        double percentage = total / 6.0;
                        String status = percentage >= 75 ? "Pass" : "Fail";
                        if (firstEntry) {
                            System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", student.getName(), student.getId(), course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                            firstEntry = false;
                        } else {
                            System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", "", "", course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                        }
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    }
                }
            }
        }

        private static void viewStudentGrades(String username) {
    String studentId = userStudentIdMap.get(username);
    Student student = findStudentById(studentId);
    if (student != null) {
        System.out.println("===============================");
        System.out.println("=== View All Student Grades ===");
        System.out.println("===============================");
        System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", "Student Name", "ID", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        boolean firstEntry = true;
        for (Map.Entry<Course, Map<String, Integer>> entry : student.getGrades().entrySet()) {
            Course course = entry.getKey();
            Map<String, Integer> gradeDetails = entry.getValue();
            int total = gradeDetails.get("Quiz 1") + gradeDetails.get("Quiz 2") + gradeDetails.get("Quiz 3") + gradeDetails.get("Project") + gradeDetails.get("Summative Exam") + gradeDetails.get("Final Exam");
            double percentage = total / 6.0;
            String status = percentage >= 75 ? "Pass" : "Fail";
            if (firstEntry) {
                System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", student.getName(), student.getId(), course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                firstEntry = false;
            } else {
                System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", "", "", course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    } else {
        System.out.println("Student not found.");
    }
}

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static void addTeacher(Scanner scanner) {
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter subjects (comma-separated): ");
        String subjects = scanner.nextLine();
        teachers.add(new Teacher(name, id, Arrays.asList(subjects.split(","))));
        System.out.println("Teacher added successfully.");
    }

    private static void removeTeacher(Scanner scanner) {
        System.out.print("Enter teacher ID to remove: ");
        String id = scanner.nextLine();
        Teacher teacher = findTeacherById(id);
        if (teacher != null) {
            teachers.remove(teacher);
            System.out.println("Teacher removed successfully.");
        } else {
            System.out.println("Teacher not found.");
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        students.add(new Student(name, id));
        System.out.println("Student added successfully.");
    }

    private static void removeStudent(Scanner scanner) {
        System.out.print("Enter student ID to remove: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void viewAllTeachers() {
        System.out.println("===============================");
        System.out.println("===== View All Teachers ======");
        System.out.println("===============================");
        System.out.printf("%-20s %-10s %-30s%n", "Teacher Name", "ID", "Subjects");
        System.out.println("--------------------------------------------------------------");
        for (Teacher teacher : teachers) {
            System.out.printf("%-20s %-10s %-30s%n", teacher.getName(), teacher.getId(), teacher.getSubjects());
        }
    }

    

    public static void viewAllStudents() {
        System.out.println("===============================");
        System.out.println("=== View All Students with Grades ===");
        System.out.println("===============================");
        System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", "Student Name", "ID", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Student student : students) {
            boolean firstEntry = true;
            if (student.getGrades().isEmpty()) {
                // If the student has no grades, still print the student info
                System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", student.getName(), student.getId(), "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
            } else {
                for (Map.Entry<Course, Map<String, Integer>> entry : student.getGrades().entrySet()) {
                    Course course = entry.getKey();
                    Map<String, Integer> gradeDetails = entry.getValue();
                    int total = gradeDetails.get("Quiz 1") + gradeDetails.get("Quiz 2") + gradeDetails.get("Quiz 3") + gradeDetails.get("Project") + gradeDetails.get("Summative Exam") + gradeDetails.get("Final Exam");
                    double percentage = total / 6.0;
                    String status = percentage >= 75 ? "Pass" : "Fail";
                    if (firstEntry) {
                        System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", student.getName(), student.getId(), course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                        firstEntry = false;
                    } else {
                        System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", "", "", course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                    }
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    private static Teacher findTeacherById(String id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    private static Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private static void generateReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write("Teachers:\n");
            for (Teacher teacher : teachers) {
                writer.write("Name: " + teacher.getName() + ", ID: " + teacher.getId() + ", Subjects: " + teacher.getSubjects() + "\n");
            }

            writer.write("\nStudents:\n");
            for (Student student : students) {
                writer.write("Name: " + student.getName() + ", ID: " + student.getId() + ", Grades: " + student.getGrades() + "\n");
            }

            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the report.");
            e.printStackTrace();
        }
    }

    public static void teacherInterface(Scanner scanner, String teacherId) {
        int choice;
        do {
            System.out.println("\n===============================");
            System.out.println("====== Teacher Interface ======");
            System.out.println("===============================");
            System.out.println("1. Assign Grades");
            System.out.println("2. View Grades");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> assignGrades(scanner);
                case 2 -> viewGrades();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static void studentInterface(Scanner scanner, String username) {
        int choice = 0;
        do {
            System.out.println("\n===============================");
            System.out.println("=======Student Interface=======");
            System.out.println("===============================");
            System.out.println("1. View Grades");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> viewStudentGrades(username);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}