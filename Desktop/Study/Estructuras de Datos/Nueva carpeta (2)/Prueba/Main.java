// Main.java
// Código unificado: Student, Clazz, CourseManager y Main en un único archivo
// Compilar: javac Main.java
// Ejecutar: java Main

// ======================
// Clase Student (nodo)
// ======================
class Student {
    public String firstName;
    public String lastName;
    public String idNumber;
    public int semester;
    public String program;
    public Student next; // puntero al siguiente nodo

    public Student(String firstName, String lastName, String idNumber, int semester, String program) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.semester = semester;
        this.program = program;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.format("%s %s | ID: %s | Sem: %d | Program: %s",
                             firstName, lastName, idNumber, semester, program);
    }
}

// ======================
// Clase Clazz
// ======================
class Clazz {
    private String id;
    private String name;
    private int credits;
    private Student head; // inicio de la lista enlazada simple

    public Clazz(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        setCredits(credits);
        this.head = null;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public Student getHead() { return head; }
    public void setName(String name) { this.name = name; }

    public boolean setCredits(int credits) {
        if (credits <= 0) return false;
        this.credits = credits;
        return true;
    }

    // Buscar alumno
    public Student findStudentById(String idNumber) {
        Student current = head;
        while (current != null) {
            if (current.idNumber.equals(idNumber)) return current;
            current = current.next;
        }
        return null;
    }

    // Comparador auxiliar
    private int compareStudents(Student a, Student b) {
        int nameCmp = a.lastName.compareToIgnoreCase(b.lastName);
        if (nameCmp != 0) return nameCmp;
        return a.idNumber.compareTo(b.idNumber);
    }

    // Insertar alumno ordenado
    public boolean addStudent(Student newStudent) {
        if (findStudentById(newStudent.idNumber) != null) return false;
        if (head == null) {
            head = newStudent;
            return true;
        }
        if (compareStudents(newStudent, head) < 0) {
            newStudent.next = head;
            head = newStudent;
            return true;
        }
        Student prev = head;
        Student curr = head.next;
        while (curr != null && compareStudents(newStudent, curr) >= 0) {
            prev = curr;
            curr = curr.next;
        }
        prev.next = newStudent;
        newStudent.next = curr;
        return true;
    }

    // Actualizar alumno
    public boolean updateStudent(String idNumber, String newFirstName, String newLastName,
                                 Integer newSemester, String newProgram) {
        Student node = findStudentById(idNumber);
        if (node == null) return false;
        if (newFirstName != null) node.firstName = newFirstName;
        if (newLastName != null) node.lastName = newLastName;
        if (newSemester != null) node.semester = newSemester;
        if (newProgram != null) node.program = newProgram;
        if (newLastName != null) {
            Student copy = new Student(node.firstName, node.lastName, node.idNumber, node.semester, node.program);
            removeStudentById(idNumber, false);
            addStudent(copy);
        }
        return true;
    }

    // Eliminar alumno
    public boolean removeStudentById(String idNumber) {
        return removeStudentById(idNumber, true);
    }

    public boolean removeStudentById(String idNumber, boolean preserveObject) {
        if (head == null) return false;
        if (head.idNumber.equals(idNumber)) {
            Student toRemove = head;
            head = head.next;
            if (!preserveObject) toRemove.next = null;
            return true;
        }
        Student prev = head;
        Student curr = head.next;
        while (curr != null) {
            if (curr.idNumber.equals(idNumber)) {
                prev.next = curr.next;
                if (!preserveObject) curr.next = null;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    // Listar alumnos
    public void listStudents() {
        if (head == null) {
            System.out.println("  [Lista vacía]");
            return;
        }
        Student curr = head;
        while (curr != null) {
            System.out.println("  - " + curr.toString());
            curr = curr.next;
        }
    }

    // Liberar lista
    public void clearStudents() {
        Student curr = head;
        while (curr != null) {
            Student next = curr.next;
            curr.next = null;
            curr = next;
        }
        head = null;
    }

    @Override
    public String toString() {
        return String.format("Clazz[id=%s, name=%s, credits=%d]", id, name, credits);
    }
}

// ======================
// Clase CourseManager
// ======================
import java.util.ArrayList;
import java.util.Scanner;

class CourseManager {
    private ArrayList<Clazz> classes = new ArrayList<>();

    public boolean createClass(String id, String name, int credits) {
        if (getClassById(id) != null) return false;
        if (credits <= 0) return false;
        classes.add(new Clazz(id, name, credits));
        return true;
    }

    public Clazz getClassById(String id) {
        for (Clazz c : classes) if (c.getId().equals(id)) return c;
        return null;
    }

    public boolean updateClass(String id, String newName, Integer newCredits) {
        Clazz c = getClassById(id);
        if (c == null) return false;
        if (newName != null) c.setName(newName);
        if (newCredits != null && newCredits > 0) c.setCredits(newCredits);
        return true;
    }

    public boolean deleteClass(String id) {
        Clazz c = getClassById(id);
        if (c == null) return false;
        c.clearStudents();
        classes.remove(c);
        return true;
    }

    public void listClasses() {
        if (classes.isEmpty()) {
            System.out.println("No hay clases.");
            return;
        }
        for (Clazz c : classes) System.out.println("- " + c.toString());
    }

    // CLI de prueba
    public static void demoCLI() {
        CourseManager mgr = new CourseManager();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\\n=== Gestión de Oferta Académica ===");
            System.out.println("1. Crear clase");
            System.out.println("2. Consultar clase");
            System.out.println("3. Actualizar clase");
            System.out.println("4. Eliminar clase");
            System.out.println("5. Listar clases");
            System.out.println("6. Gestionar alumnos de una clase");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    System.out.print("ID clase: "); String id = sc.nextLine();
                    System.out.print("Nombre: "); String name = sc.nextLine();
                    System.out.print("Créditos: "); int cr = Integer.parseInt(sc.nextLine());
                    System.out.println(mgr.createClass(id, name, cr) ? "Clase creada." : "Error.");
                    break;
                case "2":
                    System.out.print("ID clase: "); id = sc.nextLine();
                    Clazz c = mgr.getClassById(id);
                    if (c == null) System.out.println("No existe.");
                    else { System.out.println(c); c.listStudents(); }
                    break;
                case "3":
                    System.out.print("ID clase: "); id = sc.nextLine();
                    System.out.print("Nuevo nombre: "); name = sc.nextLine();
                    System.out.print("Nuevos créditos: "); String crs = sc.nextLine();
                    Integer newCr = crs.isEmpty() ? null : Integer.parseInt(crs);
                    System.out.println(mgr.updateClass(id, name.isEmpty() ? null : name, newCr) ? "Actualizada." : "Error.");
                    break;
                case "4":
                    System.out.print("ID clase: "); id = sc.nextLine();
                    System.out.println(mgr.deleteClass(id) ? "Clase eliminada." : "No encontrada.");
                    break;
                case "5":
                    mgr.listClasses();
                    break;
                case "6":
                    System.out.print("ID clase: "); id = sc.nextLine();
                    c = mgr.getClassById(id);
                    if (c == null) { System.out.println("No existe."); break; }
                    manageStudentsCLI(c, sc);
                    break;
                case "0":
                    sc.close(); return;
            }
        }
    }

    private static void manageStudentsCLI(Clazz c, Scanner sc) {
        while (true) {
            System.out.println("\\n--- Alumnos en: " + c.getName() + " ---");
            System.out.println("1. Inscribir");
            System.out.println("2. Actualizar");
            System.out.println("3. Consultar");
            System.out.println("4. Listar");
            System.out.println("5. Retirar");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opt = sc.nextLine();
            switch (opt) {
                case "1":
                    System.out.print("Nombre: "); String fn = sc.nextLine();
                    System.out.print("Apellido: "); String ln = sc.nextLine();
                    System.out.print("ID: "); String idn = sc.nextLine();
                    System.out.print("Semestre: "); int sem = Integer.parseInt(sc.nextLine());
                    System.out.print("Programa: "); String prog = sc.nextLine();
                    System.out.println(c.addStudent(new Student(fn, ln, idn, sem, prog)) ? "Inscrito." : "Duplicado.");
                    break;
                case "2":
                    System.out.print("ID: "); idn = sc.nextLine();
                    System.out.print("Nuevo nombre: "); fn = sc.nextLine();
                    System.out.print("Nuevo apellido: "); ln = sc.nextLine();
                    System.out.print("Nuevo semestre: "); String ssem = sc.nextLine();
                    Integer semN = ssem.isEmpty() ? null : Integer.parseInt(ssem);
                    System.out.print("Nuevo programa: "); prog = sc.nextLine();
                    System.out.println(c.updateStudent(idn, fn.isEmpty() ? null : fn, ln.isEmpty() ? null : ln, semN, prog.isEmpty() ? null : prog) ? "Actualizado." : "No encontrado.");
                    break;
                case "3":
                    System.out.print("ID: "); idn = sc.nextLine();
                    Student s = c.findStudentById(idn);
                    System.out.println(s == null ? "No encontrado." : s);
                    break;
                case "4":
                    c.listStudents();
                    break;
                case "5":
                    System.out.print("ID: "); idn = sc.nextLine();
                    System.out.println(c.removeStudentById(idn) ? "Eliminado." : "No encontrado.");
                    break;
                case "0":
                    return;
            }
        }
    }
}

// ======================
// Clase Main
// ======================
public class Main {
    public static void main(String[] args) {
        CourseManager.demoCLI();
    }
}
