package academtrack;

/**
 * Clase Alumno
 * Responsabilidad: Representar y gestionar la información personal
 * y académica de los alumnos del Instituto "San Ignacio".
 */
public class Alumno {

    private String codigoAlumno;
    private String dni;
    private String nombres;
    private String apellidos;
    private String carrera;
    private int ciclo;

    public Alumno(String codigoAlumno, String dni, String nombres,
                  String apellidos, String carrera, int ciclo) {
        this.codigoAlumno = codigoAlumno;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.carrera = carrera;
        this.ciclo = ciclo;
    }

    // RF01. Registrar alumno (persistencia real se implementará junto con la BD)
    public void registrar() {
        System.out.println("Alumno registrado: " + codigoAlumno + " - " + nombres + " " + apellidos);
    }

    // RF02. Consultar alumno mediante su código
    public void consultar() {
        System.out.println("Código: " + codigoAlumno);
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombres + " " + apellidos);
        System.out.println("Carrera: " + carrera + " - Ciclo: " + ciclo);
    }

    // RF03. Actualizar datos del alumno
    public void actualizar(String nombres, String apellidos, String carrera, int ciclo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.carrera = carrera;
        this.ciclo = ciclo;
    }

    // Getters y setters
    public String getCodigoAlumno() { return codigoAlumno; }
    public void setCodigoAlumno(String codigoAlumno) { this.codigoAlumno = codigoAlumno; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public int getCiclo() { return ciclo; }
    public void setCiclo(int ciclo) { this.ciclo = ciclo; }

    @Override
    public String toString() {
        return codigoAlumno + " | " + nombres + " " + apellidos + " | " + carrera + " - Ciclo " + ciclo;
    }
}
