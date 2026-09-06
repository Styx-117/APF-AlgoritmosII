package model;


public class DetalleMatricula {

    private String codigoDetalle;
    private String codigoCurso;
    private Curso curso;

    public DetalleMatricula(String codigoDetalle, Curso curso) {
        this.codigoDetalle = codigoDetalle;
        this.curso = curso;
        this.codigoCurso = curso.getCodigoCurso();
    }

    public String getCodigoDetalle() { return codigoDetalle; }
    public String getCodigoCurso() { return codigoCurso; }
    public Curso getCurso() { return curso; }

    @Override
    public String toString() {
        return codigoDetalle + " | " + curso.toString();
    }
}