package view;

import dao.TramiteDAO;
import model.Tramite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PanelTramite extends JPanel {

    private final TramiteDAO tramiteDAO = new TramiteDAO();

    private JTextField txtCodigo;
    private JTextField txtCodigoAlumno;
    private JComboBox<String> cmbTipo;
    private JTextField txtFechaSolicitud;
    private JTextField txtFechaLimite;
    private JComboBox<String> cmbPrioridad;
    private JComboBox<String> cmbEstado;

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public PanelTramite() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(construirFormulario(), BorderLayout.NORTH);
        add(construirTabla(), BorderLayout.CENTER);
        add(construirBotones(), BorderLayout.SOUTH);

        listar();
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridLayout(2, 7, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Datos del trámite  (fechas en formato AAAA-MM-DD)"));

        txtCodigo = new JTextField();
        txtCodigoAlumno = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{
                "CONSTANCIA", "RETIRO_CURSO", "RECLAMO", "CAMBIO_HORARIO"
        });
        txtFechaSolicitud = new JTextField();
        txtFechaLimite = new JTextField();
        cmbPrioridad = new JComboBox<>(new String[]{"1 - Urgente", "2 - Regular"});
        cmbEstado = new JComboBox<>(new String[]{"PENDIENTE", "ATENDIDO"});

        panel.add(new JLabel("Código trámite:"));
        panel.add(new JLabel("Código alumno:"));
        panel.add(new JLabel("Tipo:"));
        panel.add(new JLabel("Fecha solicitud:"));
        panel.add(new JLabel("Fecha límite:"));
        panel.add(new JLabel("Prioridad:"));
        panel.add(new JLabel("Estado:"));

        panel.add(txtCodigo);
        panel.add(txtCodigoAlumno);
        panel.add(cmbTipo);
        panel.add(txtFechaSolicitud);
        panel.add(txtFechaLimite);
        panel.add(cmbPrioridad);
        panel.add(cmbEstado);

        return panel;
    }

    private JScrollPane construirTabla() {
        String[] columnas = {"Código", "Alumno", "Tipo", "F. Solicitud",
                "F. Límite", "Prioridad", "Estado"};
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
        JButton btnListar = new JButton("Listar (por prioridad)");
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

    private int prioridadSeleccionada() {
        return cmbPrioridad.getSelectedIndex() == 0 ? 1 : 2;
    }

    private void registrar() {
        try {
            Tramite tramite = new Tramite(
                    txtCodigo.getText().trim(),
                    txtCodigoAlumno.getText().trim(),
                    (String) cmbTipo.getSelectedItem(),
                    txtFechaSolicitud.getText().trim(),
                    txtFechaLimite.getText().trim()
            );
            tramite.setPrioridad(prioridadSeleccionada());
            tramite.setEstado((String) cmbEstado.getSelectedItem());

            if (tramiteDAO.registrar(tramite)) {
                JOptionPane.showMessageDialog(this, "Trámite registrado correctamente.");
                limpiarCampos();
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el trámite (revise el código y las fechas).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique el formato de las fechas (AAAA-MM-DD).",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void consultar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del trámite a consultar.");
            return;
        }
        Tramite tramite = tramiteDAO.consultar(codigo);
        if (tramite == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un trámite con ese código.",
                    "Sin resultados", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mostrarEnFormulario(tramite);
    }

    private void actualizar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del trámite a actualizar.");
            return;
        }
        try {
            Tramite tramite = new Tramite(
                    codigo,
                    txtCodigoAlumno.getText().trim(),
                    (String) cmbTipo.getSelectedItem(),
                    txtFechaSolicitud.getText().trim(),
                    txtFechaLimite.getText().trim()
            );
            tramite.setPrioridad(prioridadSeleccionada());
            tramite.setEstado((String) cmbEstado.getSelectedItem());

            if (tramiteDAO.actualizar(tramite)) {
                JOptionPane.showMessageDialog(this, "Trámite actualizado correctamente.");
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo actualizar (verifique que el código exista).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique el formato de las fechas (AAAA-MM-DD).",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del trámite a eliminar.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar el trámite " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (tramiteDAO.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Trámite eliminado.");
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
        // TramiteDAO.listar() ya ordena por prioridad y fecha de solicitud (RF09/RF10)
        List<Tramite> tramites = tramiteDAO.listar();
        for (Tramite t : tramites) {
            modeloTabla.addRow(new Object[]{
                    t.getCodigoTramite(), t.getCodigoAlumno(), t.getTipo(),
                    t.getFechaSolicitud(), t.getFechaLimite(),
                    t.getPrioridad(), t.getEstado()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            return;
        }
        txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtCodigoAlumno.setText(modeloTabla.getValueAt(fila, 1).toString());
        cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
        txtFechaSolicitud.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtFechaLimite.setText(modeloTabla.getValueAt(fila, 4).toString());
        int prioridad = Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString());
        cmbPrioridad.setSelectedIndex(prioridad == 1 ? 0 : 1);
        cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());
    }

    private void mostrarEnFormulario(Tramite tramite) {
        txtCodigo.setText(tramite.getCodigoTramite());
        txtCodigoAlumno.setText(tramite.getCodigoAlumno());
        cmbTipo.setSelectedItem(tramite.getTipo());
        txtFechaSolicitud.setText(tramite.getFechaSolicitud());
        txtFechaLimite.setText(tramite.getFechaLimite());
        cmbPrioridad.setSelectedIndex(tramite.getPrioridad() == 1 ? 0 : 1);
        cmbEstado.setSelectedItem(tramite.getEstado());
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtCodigoAlumno.setText("");
        cmbTipo.setSelectedIndex(0);
        txtFechaSolicitud.setText("");
        txtFechaLimite.setText("");
        cmbPrioridad.setSelectedIndex(1);
        cmbEstado.setSelectedIndex(0);
    }
}
