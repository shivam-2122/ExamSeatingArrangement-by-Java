
import java.util.*;

class ExamSeatingSystem {
    private final List<Student> students;
    private final int totalStudents;
    private final int availableClasses;
    private final int seatsPerClass;
    private final Map<String, List<Student>> studentsByBranch;
    private final Map<Integer, List<Student>> studentsBySemester;
    private final Map<Integer, List<Student>> studentsByYear;

    // Constructor
    public ExamSeatingSystem(int totalStudents, int availableClasses, int seatsPerClass) {
        this.students = new ArrayList<>();
        this.totalStudents = totalStudents;
        this.availableClasses = availableClasses;
        this.seatsPerClass = seatsPerClass;
        this.studentsByBranch = new HashMap<>();
        this.studentsBySemester = new HashMap<>();
        this.studentsByYear = new HashMap<>();
    }

    // Add student to the system
    public void addStudent(Student student) {
        this.students.add(student);
        // Add student to maps for easy lookup by branch, semester, and year
        if (!studentsByBranch.containsKey(student.getBranch())) {
            studentsByBranch.put(student.getBranch(), new ArrayList<>());
        }
        studentsByBranch.get(student.getBranch()).add(student);

        if (!studentsBySemester.containsKey(student.getSemester())) {
            studentsBySemester.put(student.getSemester(), new ArrayList<>());
        }
        studentsBySemester.get(student.getSemester()).add(student);

        if (!studentsByYear.containsKey(student.getYear())) {
            studentsByYear.put(student.getYear(), new ArrayList<>());
        }
        studentsByYear.get(student.getYear()).add(student);
    }
    // Generate seating arrangement
    public void generateSeatingArrangement() {
        int totalSeats = availableClasses * seatsPerClass;
        if (totalStudents > totalSeats) {
            System.out.println("Error: Not enough seats available");
            return;
        }

        // Sort students by roll number
        students.sort(new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                return s1.getRollNumber().compareTo(s2.getRollNumber());
            }
        });

        int seatCount = 0;
        int rowNumber = 1;
        for (Student student : students) {
            int seatNumber = seatCount % seatsPerClass + 1;
            student.setSeatNumber(seatNumber);
            student.setRowNumber(rowNumber);
            seatCount++;
            if (seatCount % seatsPerClass == 0) {
                rowNumber++;
            }
        }

        System.out.println("Seating arrangement generated:");
        for (int classNumber = 1; classNumber <= availableClasses; classNumber++) {
            System.out.println("Class " + classNumber + ":");
            for (int seatNumber = 1; seatNumber <= seatsPerClass; seatNumber++) {
                System.out.print("Seat " + seatNumber + ": ");
                for (Student student : students) {
                    if (student.getSeatNumber() == seatNumber && student.getRowNumber() == classNumber) {
                        System.out.print(student.getName() + " (" + student.getRollNumber() + ")");
                    }
                }
                System.out.println();
            }
        }
    }
}