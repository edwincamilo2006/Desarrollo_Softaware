import java.util.Scanner;

class Student {
    public String firstName;
    public String lastName;
    public String idNumber;
    public int semester;
    public String program;
    public Student next;
    
    public Student(String firstName, String lastName, String idNumber, 
                   int semester, String program) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.semester = semester;
        this.program = program;
        this.next = null;
    }
    
    public void mostrarInfo() {
        System.out.println("ID: " + idNumber + " | Nombre: " + firstName + " " + lastName + 
                          " | Semestre: " + semester + " | Programa: " + program);
    }
}

class Class {
    public String id;
    public String name;
    public int credits;
    public Student head;
    
    public Class(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.head = null;
    }
    
    public boolean inscribirEstudiante(String firstName, String lastName, 
                                      String idNumber, int semester, String program) {
        
        if (credits <= 0) {
            System.out.println("ERROR: La clase debe tener créditos positivos");
            return false;
        }
        
        Student actual = head;
        while (actual != null) {
            if (actual.idNumber.equals(idNumber)) {
                System.out.println("ERROR: El estudiante " + idNumber + " ya está inscrito");
                return false;
            }
            actual = actual.next;
        }
        
        Student nuevoEstudiante = new Student(firstName, lastName, idNumber, semester, program);
        nuevoEstudiante.next = head;
        head = nuevoEstudiante;
        
        System.out.println("Estudiante " + firstName + " " + lastName + " inscrito correctamente");
        return true;
    }
    
    public Student buscarEstudiante(String idNumber) {
        Student actual = head;
        while (actual != null) {
            if (actual.idNumber.equals(idNumber)) {
                return actual;
            }
            actual = actual.next;
        }
        return null;
    }
    
    public boolean actualizarEstudiante(String idNumber, String nuevoNombre, 
                                       String nuevoApellido, int nuevoSemestre, String nuevoPrograma) {
        Student estudiante = buscarEstudiante(idNumber);
        if (estudiante == null) {
            System.out.println("ERROR: Estudiante con ID " + idNumber + " no encontrado");
            return false;
        }
        
        estudiante.firstName = nuevoNombre;
        estudiante.lastName = nuevoApellido;
        estudiante.semester = nuevoSemestre;
        estudiante.program = nuevoPrograma;
        
        System.out.println("Estudiante " + idNumber + " actualizado correctamente");
        return true;
    }
    
    public boolean retirarEstudiante(String idNumber) {
        if (head == null) {
            System.out.println("ERROR: No hay estudiantes en la clase");
            return false;
        }
        
        if (head.idNumber.equals(idNumber)) {
            head = head.next;
            System.out.println("Estudiante " + idNumber + " retirado correctamente");
            return true;
        }
        
        Student actual = head;
        while (actual.next != null && !actual.next.idNumber.equals(idNumber)) {
            actual = actual.next;
        }
        
        if (actual.next == null) {
            System.out.println("ERROR: Estudiante " + idNumber + " no encontrado");
            return false;
        }
        
        actual.next = actual.next.next;
        System.out.println("Estudiante " + idNumber + " retirado correctamente");
        return true;
    }
    
    public void listarEstudiantes() {
        if (head == null) {
            System.out.println("No hay estudiantes inscritos en esta clase");
            return;
        }
        
        System.out.println("\n=== ESTUDIANTES DE LA CLASE " + name + " ===");
        Student actual = head;
        int contador = 1;
        while (actual != null) {
            System.out.print(contador + ". ");
            actual.mostrarInfo();
            actual = actual.next;
            contador++;
        }
    }
    
    public int contarEstudiantes() {
        int contador = 0;
        Student actual = head;
        while (actual != null) {
            contador++;
            actual = actual.next;
        }
        return contador;
    }
    
    public void mostrarInfoClase() {
        System.out.println("\n=== INFORMACIÓN DE LA CLASE ===");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + name);
        System.out.println("Créditos: " + credits);
        System.out.println("Total estudiantes: " + contarEstudiantes());
    }
}

public class SistemaGestionAcademica {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("   SISTEMA DE GESTIÓN ACADÉMICA");
        System.out.println("=========================================");
        
        Class clase1 = new Class("CS101", "Programacion Basica", 3);
        Class clase2 = new Class("CS201", "Estructuras de Datos", 4);
        Class clase3 = new Class("CS301", "Algoritmos", 3);
        
        Class[] clases = {clase1, clase2, clase3, null, null, null, null, null, null, null};
        int totalClases = 3;
        
        int opcion = 0;
        
        do {
            mostrarMenuPrincipal();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    if (totalClases < 10) {
                        System.out.print("ID de la clase: ");
                        String id = scanner.next();
                        scanner.nextLine();
                        System.out.print("Nombre de la clase: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Créditos: ");
                        int creditos = scanner.nextInt();
                        
                        if (creditos > 0) {
                            clases[totalClases] = new Class(id, nombre, creditos);
                            totalClases++;
                            System.out.println("Clase creada correctamente");
                        } else {
                            System.out.println("ERROR: Los créditos deben ser positivos");
                        }
                    } else {
                        System.out.println("ERROR: Máximo 10 clases permitidas");
                    }
                    break;
                    
                case 2:
                    listarClases(clases, totalClases);
                    break;
                    
                case 3:
                    System.out.print("ID de la clase: ");
                    String idClase = scanner.next();
                    Class claseEncontrada = buscarClase(clases, totalClases, idClase);
                    
                    if (claseEncontrada != null) {
                        System.out.print("Nombre del estudiante: ");
                        String nombre = scanner.next();
                        System.out.print("Apellido: ");
                        String apellido = scanner.next();
                        System.out.print("ID del estudiante: ");
                        String idEstudiante = scanner.next();
                        System.out.print("Semestre: ");
                        int semestre = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Programa: ");
                        String programa = scanner.nextLine();
                        
                        claseEncontrada.inscribirEstudiante(nombre, apellido, idEstudiante, semestre, programa);
                    } else {
                        System.out.println("ERROR: Clase no encontrada");
                    }
                    break;
                    
                case 4:
                    System.out.print("ID de la clase: ");
                    idClase = scanner.next();
                    claseEncontrada = buscarClase(clases, totalClases, idClase);
                    
                    if (claseEncontrada != null) {
                        System.out.print("ID del estudiante: ");
                        String idEstudiante = scanner.next();
                        Student estudiante = claseEncontrada.buscarEstudiante(idEstudiante);
                        
                        if (estudiante != null) {
                            System.out.println("\n=== ESTUDIANTE ENCONTRADO ===");
                            estudiante.mostrarInfo();
                        } else {
                            System.out.println("ERROR: Estudiante no encontrado");
                        }
                    } else {
                        System.out.println("ERROR: Clase no encontrada");
                    }
                    break;
                    
                case 5:
                    System.out.print("ID de la clase: ");
                    idClase = scanner.next();
                    claseEncontrada = buscarClase(clases, totalClases, idClase);
                    
                    if (claseEncontrada != null) {
                        System.out.print("ID del estudiante a actualizar: ");
                        String idEstudiante = scanner.next();
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.next();
                        System.out.print("Nuevo apellido: ");
                        String nuevoApellido = scanner.next();
                        System.out.print("Nuevo semestre: ");
                        int nuevoSemestre = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nuevo programa: ");
                        String nuevoPrograma = scanner.nextLine();
                        
                        claseEncontrada.actualizarEstudiante(idEstudiante, nuevoNombre, nuevoApellido, 
                                                           nuevoSemestre, nuevoPrograma);
                    } else {
                        System.out.println("ERROR: Clase no encontrada");
                    }
                    break;
                    
                case 6:
                    System.out.print("ID de la clase: ");
                    idClase = scanner.next();
                    claseEncontrada = buscarClase(clases, totalClases, idClase);
                    
                    if (claseEncontrada != null) {
                        System.out.print("ID del estudiante a retirar: ");
                        String idEstudiante = scanner.next();
                        claseEncontrada.retirarEstudiante(idEstudiante);
                    } else {
                        System.out.println("ERROR: Clase no encontrada");
                    }
                    break;
                    
                case 7:
                    System.out.print("ID de la clase: ");
                    idClase = scanner.next();
                    claseEncontrada = buscarClase(clases, totalClases, idClase);
                    
                    if (claseEncontrada != null) {
                        claseEncontrada.mostrarInfoClase();
                        claseEncontrada.listarEstudiantes();
                    } else {
                        System.out.println("ERROR: Clase no encontrada");
                    }
                    break;
                    
                case 8:
                    System.out.println("Gracias por usar el sistema");
                    break;
                    
                default:
                    System.out.println("Opción inválida");
            }
            
            if (opcion != 8) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
            
        } while (opcion != 8);
        
        scanner.close();
    }
    
    public static void mostrarMenuPrincipal() {
        System.out.println("\n========================================");
        System.out.println("              MENÚ PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1. Crear nueva clase");
        System.out.println("2. Listar todas las clases");
        System.out.println("3. Inscribir estudiante");
        System.out.println("4. Buscar estudiante");
        System.out.println("5. Actualizar estudiante");
        System.out.println("6. Retirar estudiante");
        System.out.println("7. Listar estudiantes de una clase");
        System.out.println("8. Salir");
        System.out.println("========================================");
    }
    
    public static void listarClases(Class[] clases, int total) {
        if (total == 0) {
            System.out.println("No hay clases registradas");
            return;
        }
        
        System.out.println("\n=== LISTADO DE CLASES ===");
        for (int i = 0; i < total; i++) {
            if (clases[i] != null) {
                System.out.println((i+1) + ". ID: " + clases[i].id + 
                                  " | Nombre: " + clases[i].name + 
                                  " | Créditos: " + clases[i].credits + 
                                  " | Estudiantes: " + clases[i].contarEstudiantes());
            }
        }
    }
    
    public static Class buscarClase(Class[] clases, int total, String id) {
        for (int i = 0; i < total; i++) {
            if (clases[i] != null && clases[i].id.equals(id)) {
                return clases[i];
            }
        }
        return null;
    }
}