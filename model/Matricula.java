package model;

import estructuras.MatrizCruceHorarios;

/**
 * Clase Matrícula
 * Gestiona el proceso de matrícula de un alumno en los cursos
 * seleccionados.

 */
public class Matricula {

    private String codigoMatricula;
    private String codigoAlumno;
    private String fecha;
    private String periodo;
    private String estado; // PENDIENTE, CONFIRMADA, ANULADA

    private DetalleMatricula[] detalles;
    private int cantidadDetalles;
    private static final int CAPACIDAD_DEFECTO = 10;
    private int contadorDetalle;

    public Matricula(String codigoMatricula, String codigoAlumno, String fecha, String periodo) {
        this.codigoMatricula = codigoMatricula;
        this.codigoAlumno = codigoAlumno;
        this.fecha = fecha;
        this.periodo = periodo;
        this.estado = "PENDIENTE";
        this.detalles = new DetalleMatricula[CAPACIDAD_DEFECTO];
        this.cantidadDetalles = 0;
        this.contadorDetalle = 0;
    }

    // RF05. Registrar la matrícula
    public void registrar() {
        System.out.println("Matrícula " + codigoMatricula + " registrada para alumno " + codigoAlumno);
    }

    // Insertar un curso en el arreglo
    public boolean agregarCurso(Curso curso) {
        if (cantidadDetalles == detalles.length) {
            System.out.println("No se pudo agregar " + curso.getCodigoCurso() + ": matrícula llena.");
            return false;
        }
        contadorDetalle++;
        String codigoDetalle = codigoMatricula + "-D" + contadorDetalle;
        detalles[cantidadDetalles] = new DetalleMatricula(codigoDetalle, curso);
        cantidadDetalles++;
        return true;
    }

    // Buscar la posición de un curso en el arreglo, o -1 si no está
    private int buscarIndicePorCurso(String codigoCurso) {
        for (int i = 0; i < cantidadDetalles; i++) {
            if (detalles[i].getCodigoCurso().equals(codigoCurso)) {
                return i;
            }
        }
        return -1;
    }

    public DetalleMatricula buscarDetalle(String codigoCurso) {
        int idx = buscarIndicePorCurso(codigoCurso);
        return (idx == -1) ? null : detalles[idx];
    }


    public boolean actualizarCurso(String codigoCursoActual, Curso nuevoCurso) {
        int idx = buscarIndicePorCurso(codigoCursoActual);
        if (idx == -1) {
            return false;
        }
        String codigoDetalle = detalles[idx].getCodigoDetalle();
        detalles[idx] = new DetalleMatricula(codigoDetalle, nuevoCurso);
        return true;
    }


    public boolean eliminarCurso(String codigoCurso) {
        int idx = buscarIndicePorCurso(codigoCurso);
        if (idx == -1) {
            return false;
        }
        for (int i = idx; i < cantidadDetalles - 1; i++) {
            detalles[i] = detalles[i + 1];
        }
        detalles[cantidadDetalles - 1] = null;
        cantidadDetalles--;
        return true;
    }


    public void recorrerDetalles() {
        if (cantidadDetalles == 0) {
            System.out.println("(matrícula sin cursos registrados)");
            return;
        }
        for (int i = 0; i < cantidadDetalles; i++) {
            System.out.println(detalles[i]);
        }
    }


    public Matricula copiar(String nuevoCodigoMatricula) {
        Matricula copia = new Matricula(nuevoCodigoMatricula, this.codigoAlumno, this.fecha, this.periodo);
        for (int i = 0; i < this.cantidadDetalles; i++) {
            copia.detalles[copia.cantidadDetalles] = this.detalles[i];
            copia.cantidadDetalles++;
        }
        return copia;
    }

    // Clonar: nueva matrícula con cursos creados de nuevo (no los mismos objetos)
    public Matricula clonar(String nuevoCodigoMatricula) {
        Matricula clon = new Matricula(nuevoCodigoMatricula, this.codigoAlumno, this.fecha, this.periodo);
        for (int i = 0; i < this.cantidadDetalles; i++) {
            Curso c = this.detalles[i].getCurso();
            Curso nuevo = new Curso(c.getCodigoCurso(), c.getNombre(), c.getCarrera(),
                    c.getCiclo(), c.getHorario(), c.getCapacidad());
            clon.agregarCurso(nuevo);
        }
        return clon;
    }

    // Comparar: true si ambas matrículas tienen los mismos cursos en el mismo orden
    public boolean compararCursos(Matricula otra) {
        if (otra == null || this.cantidadDetalles != otra.cantidadDetalles) {
            return false;
        }
        for (int i = 0; i < this.cantidadDetalles; i++) {
            if (!this.detalles[i].getCodigoCurso().equals(otra.detalles[i].getCodigoCurso())) {
                return false;
            }
        }
        return true;
    }

    // Fusionar: juntar los cursos de esta matrícula con los de otra, sin repetir cursos
    public Matricula fusionar(Matricula otra, String nuevoCodigoMatricula) {
        Matricula resultado = new Matricula(nuevoCodigoMatricula, this.codigoAlumno, this.fecha, this.periodo);
        for (int i = 0; i < this.cantidadDetalles; i++) {
            resultado.agregarCurso(this.detalles[i].getCurso());
        }
        for (int i = 0; i < otra.cantidadDetalles; i++) {
            Curso c = otra.detalles[i].getCurso();
            if (resultado.buscarIndicePorCurso(c.getCodigoCurso()) == -1) {
                resultado.agregarCurso(c);
            }
        }
        return resultado;
    }

    // RF06. Validar que no haya cursos repetidos ni cruce de horario
    public boolean validar() {
        for (int i = 0; i < cantidadDetalles; i++) {
            for (int j = i + 1; j < cantidadDetalles; j++) {
                if (detalles[i].getCodigoCurso().equals(detalles[j].getCodigoCurso())) {
                    System.out.println("Error: curso duplicado " + detalles[i].getCodigoCurso());
                    return false;
                }
            }
        }

        Curso[] cursosDeLaMatricula = new Curso[cantidadDetalles];
        for (int i = 0; i < cantidadDetalles; i++) {
            cursosDeLaMatricula[i] = detalles[i].getCurso();
        }
        MatrizCruceHorarios matriz = new MatrizCruceHorarios(cursosDeLaMatricula, cantidadDetalles);
        for (int i = 0; i < cantidadDetalles; i++) {
            for (int j = i + 1; j < cantidadDetalles; j++) {
                if (matriz.hayCruce(i, j)) {
                    System.out.println("Error: cruce de horario entre "
                            + cursosDeLaMatricula[i].getCodigoCurso() + " y " + cursosDeLaMatricula[j].getCodigoCurso());
                    return false;
                }
            }
        }
        return true;
    }

    public void confirmar() {
        if (validar()) {
            this.estado = "CONFIRMADA";
            System.out.println("Matrícula " + codigoMatricula + " confirmada.");
        } else {
            System.out.println("No se pudo confirmar la matrícula " + codigoMatricula);
        }
    }

    // Getters
    public String getCodigoMatricula() { return codigoMatricula; }
    public String getCodigoAlumno() { return codigoAlumno; }
    public String getFecha() { return fecha; }
    public String getPeriodo() { return periodo; }
    public String getEstado() { return estado; }
    public int getCantidadDetalles() { return cantidadDetalles; }
    public DetalleMatricula obtenerDetalle(int i) {
        if (i < 0 || i >= cantidadDetalles) return null;
        return detalles[i];
    }

    //Set necesario

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}