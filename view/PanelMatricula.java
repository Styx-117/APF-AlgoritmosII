package view;

import dao.CursoDAO;
import dao.MatriculaDAO;
import model.Curso;
import model.DetalleMatricula;
import model.Matricula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PanelMatricula extends JPanel {

    private final CursoDAO cursoDAO = new CursoDAO();
    private final MatriculaDAO matriculaDAO = new MatriculaDAO();

    private Matricula matriculaActual;

    private JTextField txtCodigoMatricula;
    private JTextField txtCodigoAlumno;
    private JTextField txtFecha;
    private JTextField txtPeriodo;
    private JLabel lblEstado;

    private JTextField txtCodigoCursoBuscar;

    private DefaultTableModel modeloDetalles;
    private JTable tablaDetalles;

    private DefaultTableModel modeloMatriculas;
    private JTable tablaMatriculas;

    public PanelMatricula() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                construirPanelSuperior(), construirPanelListado());
        splitPane.setResizeWeight(0.65);

        add(splitPane, BorderLayout.CENTER);

        nuevaMatricula();
        listarMatriculas();
    }


    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel datos = new JPanel(new GridLayout(2, 5, 5, 5));
        datos.setBorder(BorderFactory.createTitledBorder("Datos de la matrícula"));

        txtCodigoMatricula = new JTextField();
        txtCodigoAlumno = new JTextField();
        txtFecha = new JTextField();
        txtPeriodo = new JTextField();
        lblEstado = new JLabel("PENDIENTE");
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.BOLD));

        datos.add(new JLabel("Código matrícula:"));
        datos.add(new JLabel("Código alumno:"));
        datos.add(new JLabel("Fecha (AAAA-MM-DD):"));
        datos.add(new JLabel("Periodo:"));
        datos.add(new JLabel("Estado:"));

        datos.add(txtCodigoMatricula);
        datos.add(txtCodigoAlumno);
        datos.add(txtFecha);
        datos.add(txtPeriodo);
        datos.add(lblEstado);

        JPanel agregarCurso = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        agregarCurso.setBorder(BorderFactory.createTitledBorder(
                "Agregar curso (arreglo unidimensional DetalleMatricula[])"));
        txtCodigoCursoBuscar = new JTextField(10);
        JButton btnAgregarCurso = new JButton("Buscar y agregar curso");
        JButton btnQuitarCurso = new JButton("Quitar curso seleccionado");
        btnAgregarCurso.addActionListener(e -> agregarCurso());
        btnQuitarCurso.addActionListener(e -> quitarCursoSeleccionado());
        agregarCurso.add(new JLabel("Código de curso:"));
        agregarCurso.add(txtCodigoCursoBuscar);
        agregarCurso.add(btnAgregarCurso);
        agregarCurso.add(btnQuitarCurso);

        String[] columnasDetalle = {"Detalle", "Curso", "Nombre", "Horario"};
        modeloDetalles = new DefaultTableModel(columnasDetalle, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaDetalles = new JTable(modeloDetalles);

        JPanel botonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnNueva = new JButton("Nueva matrícula");
        JButton btnValidarConfirmar = new JButton("Validar y confirmar (guardar en BD)");
        JButton btnBuscar = new JButton("Buscar matrícula existente");
        JButton btnEliminar = new JButton("Eliminar matrícula");

        btnNueva.addActionListener(e -> nuevaMatricula());
        btnValidarConfirmar.addActionListener(e -> validarYConfirmar());
        btnBuscar.addActionListener(e -> buscarMatricula());
        btnEliminar.addActionListener(e -> eliminarMatricula());

        botonesAccion.add(btnNueva);
        botonesAccion.add(btnValidarConfirmar);
        botonesAccion.add(btnBuscar);
        botonesAccion.add(btnEliminar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(datos, BorderLayout.NORTH);
        norte.add(agregarCurso, BorderLayout.SOUTH);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);
        panel.add(botonesAccion, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel construirPanelListado() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Matrículas registradas"));

        String[] columnas = {"Código", "Alumno", "Fecha", "Periodo", "Estado", "N° cursos"};
        modeloMatriculas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMatriculas = new JTable(modeloMatriculas);

        JButton btnActualizarLista = new JButton("Actualizar lista");
        btnActualizarLista.addActionListener(e -> listarMatriculas());

        panel.add(new JScrollPane(tablaMatriculas), BorderLayout.CENTER);
        panel.add(btnActualizarLista, BorderLayout.SOUTH);

        return panel;
    }

   
    private void nuevaMatricula() {
        matriculaActual = null;
        txtCodigoMatricula.setText("");
        txtCodigoAlumno.setText("");
        txtFecha.setText("");
        txtPeriodo.setText("");
        lblEstado.setText("PENDIENTE");
        modeloDetalles.setRowCount(0);
    }

    private boolean asegurarMatriculaEnMemoria() {
        if (matriculaActual == null) {
            String codigo = txtCodigoMatricula.getText().trim();
            String alumno = txtCodigoAlumno.getText().trim();
            String fecha = txtFecha.getText().trim();
            String periodo = txtPeriodo.getText().trim();

            if (codigo.isEmpty() || alumno.isEmpty() || fecha.isEmpty() || periodo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Complete código de matrícula, alumno, fecha y periodo antes de agregar cursos.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            matriculaActual = new Matricula(codigo, alumno, fecha, periodo);
        }
        return true;
    }

    private void agregarCurso() {
        if (!asegurarMatriculaEnMemoria()) {
            return;
        }
        String codigoCurso = txtCodigoCursoBuscar.getText().trim();
        if (codigoCurso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del curso a agregar.");
            return;
        }
        Curso curso = cursoDAO.consultar(codigoCurso);
        if (curso == null) {
            JOptionPane.showMessageDialog(this,
                    "No existe un curso con código " + codigoCurso + " en la base de datos.",
                    "Curso no encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean agregado = matriculaActual.agregarCurso(curso);
        if (!agregado) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo agregar el curso: la matrícula alcanzó su capacidad máxima.",
                    "Matrícula llena", JOptionPane.WARNING_MESSAGE);
            return;
        }
        txtCodigoCursoBuscar.setText("");
        refrescarTablaDetalles();
    }

    private void quitarCursoSeleccionado() {
        if (matriculaActual == null) {
            return;
        }
        int fila = tablaDetalles.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso de la tabla para quitarlo.");
            return;
        }
        String codigoCurso = modeloDetalles.getValueAt(fila, 1).toString();
        matriculaActual.eliminarCurso(codigoCurso);
        refrescarTablaDetalles();
    }

    private void refrescarTablaDetalles() {
        modeloDetalles.setRowCount(0);
        if (matriculaActual == null) {
            return;
        }
        for (int i = 0; i < matriculaActual.getCantidadDetalles(); i++) {
            DetalleMatricula d = matriculaActual.obtenerDetalle(i);
            modeloDetalles.addRow(new Object[]{
                    d.getCodigoDetalle(), d.getCodigoCurso(),
                    d.getCurso().getNombre(), d.getCurso().getHorario()
            });
        }
    }

    private void validarYConfirmar() {
        if (!asegurarMatriculaEnMemoria()) {
            return;
        }
        if (matriculaActual.getCantidadDetalles() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un curso antes de confirmar.");
            return;
        }

       
        matriculaActual.confirmar();
        lblEstado.setText(matriculaActual.getEstado());

        if (!"CONFIRMADA".equals(matriculaActual.getEstado())) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo confirmar la matrícula: hay un curso duplicado o un cruce de horario.\n"
                            + "Revise la consola para el detalle del curso en conflicto.",
                    "Validación fallida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean guardado = matriculaDAO.registrar(matriculaActual);
            if (guardado) {
                JOptionPane.showMessageDialog(this,
                        "Matrícula " + matriculaActual.getCodigoMatricula()
                                + " validada, confirmada y guardada en la base de datos.");
                listarMatriculas();
            } else {
                JOptionPane.showMessageDialog(this,
                        "La matrícula se confirmó en memoria, pero no se pudo guardar en la BD.\n"
                                + "Verifique que el código de matrícula no exista ya, y que el alumno exista.",
                        "Error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "La fecha de la matrícula debe tener el formato AAAA-MM-DD.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarMatricula() {
        String codigo = txtCodigoMatricula.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código de la matrícula a buscar.");
            return;
        }
        Matricula encontrada = matriculaDAO.consultar(codigo);
        if (encontrada == null) {
            JOptionPane.showMessageDialog(this, "No se encontró una matrícula con ese código.",
                    "Sin resultados", JOptionPane.WARNING_MESSAGE);
            return;
        }
        matriculaActual = encontrada;
        txtCodigoMatricula.setText(encontrada.getCodigoMatricula());
        txtCodigoAlumno.setText(encontrada.getCodigoAlumno());
        txtFecha.setText(encontrada.getFecha());
        txtPeriodo.setText(encontrada.getPeriodo());
        lblEstado.setText(encontrada.getEstado());
        refrescarTablaDetalles();
    }

    private void eliminarMatricula() {
        String codigo = txtCodigoMatricula.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código de la matrícula a eliminar.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar la matrícula " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (matriculaDAO.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Matrícula eliminada.");
                nuevaMatricula();
                listarMatriculas();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar (verifique que el código exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarMatriculas() {
        modeloMatriculas.setRowCount(0);
        List<Matricula> matriculas = matriculaDAO.listar();
        for (Matricula m : matriculas) {
            modeloMatriculas.addRow(new Object[]{
                    m.getCodigoMatricula(), m.getCodigoAlumno(), m.getFecha(),
                    m.getPeriodo(), m.getEstado(), m.getCantidadDetalles()
            });
        }
    }
}
