package estructuras;

import model.Curso;


public class MatrizCruceHorarios {


    private boolean[][] cruce;
    private Curso[] cursos;
    private int n;

    public MatrizCruceHorarios(Curso[] cursosCiclo, int n) {
        this.n = n;
        this.cursos = new Curso[n];
        for (int i = 0; i < n; i++) {
            this.cursos[i] = cursosCiclo[i];
        }
        this.cruce = new boolean[n][];
        for (int i = 0; i < n; i++) {
            this.cruce[i] = new boolean[i];
        }
        calcular();
    }

    
    private void calcular() {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                cruce[i][j] = cursos[i].getHorario().equals(cursos[j].getHorario());
            }
        }
    }


    public boolean hayCruce(int i, int j) {
        if (i == j) {
            return false;
        }
        int fila = Math.max(i, j);
        int columna = Math.min(i, j);
        return cruce[fila][columna];
    }

    public boolean tieneAlgunCruce(int idx) {
        for (int j = 0; j < n; j++) {
            if (hayCruce(idx, j)) {
                return true;
            }
        }
        return false;
    }

    public void mostrar() {
        System.out.println("Matriz de cruces de horario (triangular inferior):");
        boolean algunCruce = false;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (cruce[i][j]) {
                    System.out.println(cursos[i].getCodigoCurso() + " cruza con " + cursos[j].getCodigoCurso()
                            + " (" + cursos[i].getHorario() + ")");
                    algunCruce = true;
                }
            }
        }
        if (!algunCruce) {
            System.out.println("(sin cruces de horario)");
        }
    }
}