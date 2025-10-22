public class ParcialOne {

    static class Student {
        int age;
        int semester;
        int socioEconomicStratum;
        int coursesCompleted;
        int coursesPending;
        String program;
        int programId;

        public Student(int age, int semester, int socioEconomicStratum, int coursesCompleted, int coursesPending, String program, int programId) {
            this.age = age;
            this.semester = semester;
            this.socioEconomicStratum = socioEconomicStratum;
            this.coursesCompleted = coursesCompleted;
            this.coursesPending = coursesPending;
            this.program = program;
            this.programId = programId;
        }

        @Override
        public String toString() {
            return "Age: " + age +
                   ", Semester: " + semester +
                   ", Stratum: " + socioEconomicStratum +
                   ", Completed: " + coursesCompleted +
                   ", Pending: " + coursesPending +
                   ", Program: " + program +
                   ", ProgramId: " + programId;
        }
    }

    public static void bubbleSortByAge(Student[] students) {
        int n = students.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students[j].age > students[j+1].age) {
                    Student temp = students[j];
                    students[j] = students[j+1];
                    students[j+1] = temp;
                }
            }
        }
    }

    public static void selectionSortBySemester(Student[] students) {
        int n = students.length;
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (students[j].semester > students[maxIndex].semester) {
                    maxIndex = j;
                }
            }
            Student temp = students[maxIndex];
            students[maxIndex] = students[i];
            students[i] = temp;
        }
    }

    public static Student linearSearchByProgramId(Student[] students, int id) {
        for (Student s : students) {
            if (s.programId == id) {
                return s;
            }
        }
        return null;
    }

    public static Student binarySearchByAge(Student[] students, int age) {
        int left = 0, right = students.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (students[mid].age == age) {
                return students[mid];
            } else if (students[mid].age < age) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Student[] students = {
            new Student(20, 3, 2, 12, 8, "Ingeniería", 101),
            new Student(22, 5, 3, 20, 5, "Medicina", 102),
            new Student(18, 1, 1, 5, 10, "Derecho", 103),
            new Student(25, 8, 4, 30, 2, "Psicología", 104),
            new Student(19, 2, 2, 8, 9, "Economía", 105),
            new Student(21, 4, 3, 18, 6, "Arquitectura", 106),
            new Student(23, 6, 5, 24, 4, "Administración", 107),
            new Student(24, 7, 4, 28, 3, "Contaduría", 108),
            new Student(26, 9, 6, 35, 1, "Odontología", 109),
            new Student(20, 3, 2, 15, 7, "Biología", 110)
        };

        System.out.println("Listado original:");
        for (Student s : students) System.out.println(s);

        bubbleSortByAge(students);
        System.out.println("\nOrdenado por edad (BubbleSort):");
        for (Student s : students) System.out.println(s);

        selectionSortBySemester(students);
        System.out.println("\nOrdenado por semestre (SelectionSort):");
        for (Student s : students) System.out.println(s);

        Student found = linearSearchByProgramId(students, 106);
        System.out.println("\nResultado Linear Search por programId=106:");
        System.out.println(found != null ? found : "No encontrado");

        bubbleSortByAge(students);
        Student foundAge = binarySearchByAge(students, 22);
        System.out.println("\nResultado Binary Search por age=22:");
        System.out.println(foundAge != null ? foundAge : "No encontrado");
    }
}
