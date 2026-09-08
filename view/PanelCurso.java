package view;

import dao.CursoDAO;
import model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PanelCurso extends JPanel {

    private final CursoDAO cursoDAO = new CursoDAO();

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCarrera;
    private JTextField txtCiclo;
    private JTextField txtHorario;
    private JTextField txtCapacidad;

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public PanelCurso() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(construirFormulario(), BorderLayout.NORTH);
        add(construirTabla(), BorderLayout.CENTER);
        add(construirBotones(), BorderLayout.SOUTH);

        listar();
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridLayout(2, 6, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Datos del curso  (horario sugerido: 'LUN 08:00-10:00')"));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCarrera = new JTextField();
        txtCiclo = new JTextField();
        txtHorario = new JTextField();
        txtCapacidad = new JTextField();

        panel.add(new JLabel("Código:"));
        panel.add(new JLabel("Nombre:"));
        panel.add(new JLabel("Carrera:"));
        panel.add(new JLabel("Ciclo:"));
        panel.add(new JLabel("Horario:"));
        panel.add(new JLabel("Capacidad:"));

        panel.add(txtCodigo);
        panel.add(txtNombre);
        panel.add(txtCarrera);
        panel.add(txtCiclo);
        panel.add(txtHorario);
        panel.add(txtCapacidad);

        return panel;
    }

    private JScrollPane construirTabla() {
        String[] columnas = {"Código", "Nombre", "Carrera", "Ciclo", "Horario", "Capacidad"};
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
            Curso curso = new Curso(
                    txtCodigo.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCarrera.getText().trim(),
                    Integer.parseInt(txtCiclo.getText().trim()),
                    txtHorario.getText().trim(),
                    Integer.parseInt(txtCapacidad.getText().trim())
            );

            if (cursoDAO.registrar(curso)) {
                JOptionPane.showMessageDialog(this, "Curso registrado correctamente.");
                limpiarCampos();
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el curso (revise que el código no exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ciclo y capacidad deben ser números enteros.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void consultar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del curso a consultar.");
            return;
        }
        Curso curso = cursoDAO.consultar(codigo);
        if (curso == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un curso con ese código.",
                    "Sin resultados", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mostrarEnFormulario(curso);
    }

    private void actualizar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del curso a actualizar.");
            return;
        }
        try {
            Curso curso = new Curso(
                    codigo,
                    txtNombre.getText().trim(),
                    txtCarrera.getText().trim(),
                    Integer.parseInt(txtCiclo.getText().trim()),
                    txtHorario.getText().trim(),
                    Integer.parseInt(txtCapacidad.getText().trim())
            );

            if (cursoDAO.actualizar(curso)) {
                JOptionPane.showMessageDialog(this, "Curso actualizado correctamente.");
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo actualizar (verifique que el código exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ciclo y capacidad deben ser números enteros.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del curso a eliminar.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar el curso " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (cursoDAO.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Curso eliminado.");
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
        List<Curso> cursos = cursoDAO.listar();
        for (Curso c : cursos) {
            modeloTabla.addRow(new Object[]{
                    c.getCodigoCurso(), c.getNombre(), c.getCarrera(),
                    c.getCiclo(), c.getHorario(), c.getCapacidad()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            return;
        }
        txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtCarrera.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtCiclo.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtHorario.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtCapacidad.setText(modeloTabla.getValueAt(fila, 5).toString());
    }

    private void mostrarEnFormulario(Curso curso) {
        txtCodigo.setText(curso.getCodigoCurso());
        txtNombre.setText(curso.getNombre());
        txtCarrera.setText(curso.getCarrera());
        txtCiclo.setText(String.valueOf(curso.getCiclo()));
        txtHorario.setText(curso.getHorario());
        txtCapacidad.setText(String.valueOf(curso.getCapacidad()));
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCarrera.setText("");
        txtCiclo.setText("");
        txtHorario.setText("");
        txtCapacidad.setText("");
    }
}
