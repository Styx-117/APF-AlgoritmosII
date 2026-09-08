package view;

import javax.swing.*;

/**
 * Ventana principal de AcademTrack.
 * Interfaz gráfica inicial del sistema (Avance 1): permite registrar,
 * consultar, actualizar y eliminar Alumnos, Cursos, Matrículas y Trámites,
 * mostrando en pantalla la validación de duplicidad y cruce de horario
 * (arreglo bidimensional MatrizCruceHorarios) al confirmar una matrícula.
 */
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("AcademTrack - Instituto Superior Tecnológico \"San Ignacio\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Alumnos", new PanelAlumno());
        tabs.addTab("Cursos", new PanelCurso());
        tabs.addTab("Matrícula", new PanelMatricula());
        tabs.addTab("Trámites", new PanelTramite());

        add(tabs);
    }

    public static void main(String[] args) {
        // Se ejecuta en el hilo de eventos de Swing (buena práctica en GUI)
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
