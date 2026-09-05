package model;

/**
 * Clase Curso
 * Responsabilidad: Gestionar la información de los cursos disponibles
 * según la carrera y el ciclo.
 */
public class Curso {

    private String codigoCurso;
    private String nombre;
    private String carrera;
    private int ciclo;
    private String horario;
    private int capacidad;

    public Curso(String codigoCurso, String nombre, String carrera,
                 int ciclo, String horario, int capacidad) {
        this.codigoCurso = codigoCurso;
        this.nombre = nombre;
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.horario = horario;
        this.capacidad = capacidad;
    }

    // RF04. Registrar curso según carrera y ciclo
    public void registrar() {
        System.out.println("Curso registrado: " + codigoCurso + " - " + nombre);
    }

    public void consultar() {
        System.out.println("Código: " + codigoCurso);
        System.out.println("Nombre: " + nombre);
        System.out.println("Carrera: " + carrera + " - Ciclo: " + ciclo);
        System.out.println("Horario: " + horario + " | Capacidad: " + capacidad);
    }

    public void actualizar(String nombre, String horario, int capacidad) {
        this.nombre = nombre;
        this.horario = horario;
        this.capacidad = capacidad;
    }

    // Getters y setters
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public int getCiclo() { return ciclo; }
    public void setCiclo(int ciclo) { this.ciclo = ciclo; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    @Override
    public String toString() {
        return codigoCurso + " | " + nombre + " | " + carrera + " - Ciclo " + ciclo + " | " + horario;
    }
}
