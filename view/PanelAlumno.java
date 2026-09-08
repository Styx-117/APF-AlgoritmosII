package view;

import dao.AlumnoDAO;
import model.Alumno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PanelAlumno extends JPanel {

    private final AlumnoDAO alumnoDAO = new AlumnoDAO();

    private JTextField txtCodigo;
    private JTextField txtDni;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtCarrera;
    private JTextField txtCiclo;

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public PanelAlumno() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(construirFormulario(), BorderLayout.NORTH);
        add(construirTabla(), BorderLayout.CENTER);
        add(construirBotones(), BorderLayout.SOUTH);

        listar();
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridLayout(2, 6, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del alumno"));

        txtCodigo = new JTextField();
        txtDni = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtCarrera = new JTextField();
        txtCiclo = new JTextField();

        panel.add(new JLabel("Código:"));
        panel.add(new JLabel("DNI:"));
        panel.add(new JLabel("Nombres:"));
        panel.add(new JLabel("Apellidos:"));
        panel.add(new JLabel("Carrera:"));
        panel.add(new JLabel("Ciclo:"));

        panel.add(txtCodigo);
        panel.add(txtDni);
        panel.add(txtNombres);
        panel.add(txtApellidos);
        panel.add(txtCarrera);
        panel.add(txtCiclo);

        return panel;
    }

    private JScrollPane construirTabla() {
        String[] columnas = {"Código", "DNI", "Nombres", "Apellidos", "Carrera", "Ciclo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
        return new JScrollPane(tabla);
    }

    private JPanel construirBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnConsultar = new JButton("Consultar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar todos");
        JButton btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> registrar());
        btnConsultar.addActionListener(e -> consultar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> listar());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panel.add(btnRegistrar);
        panel.add(btnConsultar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnListar);
        panel.add(btnLimpiar);

        return panel;
    }

    private void registrar() {
        try {
            Alumno alumno = new Alumno(
                    txtCodigo.getText().trim(),
                    txtDni.getText().trim(),
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtCarrera.getText().trim(),
                    Integer.parseInt(txtCiclo.getText().trim())
            );

            if (alumnoDAO.registrar(alumno)) {
                JOptionPane.showMessageDialog(this, "Alumno registrado correctamente.");
                limpiarCampos();
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el alumno (revise que el código no exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ciclo debe ser un número entero.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void consultar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del alumno a consultar.");
            return;
        }
        Alumno alumno = alumnoDAO.consultar(codigo);
        if (alumno == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un alumno con ese código.",
                    "Sin resultados", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mostrarEnFormulario(alumno);
    }

    private void actualizar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del alumno a actualizar.");
            return;
        }
        try {
            Alumno alumno = new Alumno(
                    codigo,
                    txtDni.getText().trim(),
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtCarrera.getText().trim(),
                    Integer.parseInt(txtCiclo.getText().trim())
            );

            if (alumnoDAO.actualizar(alumno)) {
                JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente.");
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo actualizar (verifique que el código exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ciclo debe ser un número entero.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del alumno a eliminar.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar al alumno " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (alumnoDAO.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Alumno eliminado.");
                limpiarCampos();
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar (verifique que el código exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listar() {
        modeloTabla.setRowCount(0);
        List<Alumno> alumnos = alumnoDAO.listar();
        for (Alumno a : alumnos) {
            modeloTabla.addRow(new Object[]{
                    a.getCodigoAlumno(), a.getDni(), a.getNombres(),
                    a.getApellidos(), a.getCarrera(), a.getCiclo()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            return;
        }
        txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtDni.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtNombres.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtApellidos.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtCarrera.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtCiclo.setText(modeloTabla.getValueAt(fila, 5).toString());
    }

    private void mostrarEnFormulario(Alumno alumno) {
        txtCodigo.setText(alumno.getCodigoAlumno());
        txtDni.setText(alumno.getDni());
        txtNombres.setText(alumno.getNombres());
        txtApellidos.setText(alumno.getApellidos());
        txtCarrera.setText(alumno.getCarrera());
        txtCiclo.setText(String.valueOf(alumno.getCiclo()));
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCarrera.setText("");
        txtCiclo.setText("");
    }
}
