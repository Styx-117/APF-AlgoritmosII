package view;

import javax.swing.*;


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
      
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
