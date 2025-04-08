package app.view;

import app.controller.GastoController;
import app.exception.ApiException;
import app.model.Gasto;
import app.view.forms.EditOverheadForm;
import app.view.forms.NewOverheadForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.management.Notification;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Overheads extends JPanel {

    private GastoController controller;

    public Overheads() {
        controller = new GastoController();
        init();
    }

    // ==============================
    // Inicialización
    // ==============================

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        JPanel panel = createMainContentPanel();

        // Agregar título y tabla
        panel.add(createTitleLabel(), "gapbottom 15, al center");
        panel.add(createTableComponent(), "grow, gapbottom 20");

        // Agregar el label del total
        panel.add(createTotalLabel(), "al right, span, wrap"); // Alinea el total a la derecha y salta a la siguiente fila

        //separador
        panel.add(new JSeparator(), "gapy 5 5");

        // Agregar botones alineados horizontalmente
        panel.add(createAgregarButton(), "split 4, growx, gapright 10"); // Botón "Eliminar gasto"
        panel.add(createEliminarButton(), "growx, gapright 10"); // Botón "Editar gasto"
        panel.add(createEditarButton(), "growx, gapright 10"); // Botón "Ver detalles"
        panel.add(createDetallesButton(), "growx, wrap"); // Botón "Agregar gasto"

        add(panel);
        llenarTabla();
        configurarAcciones();
        setTotal();
    }

    private void configurarAcciones() {
        btnAdd.addActionListener(e -> addOverhead());
        btnDelete.addActionListener(e -> removeOverhead());
        btnEdit.addActionListener(e -> editOverhead());
        btnDetails.addActionListener(e -> detailsOverhead());
    }

    // ==============================
    // Construcción visual
    // ==============================

    private JPanel createMainContentPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 25", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");
        return panel;
    }

    private JLabel createTitleLabel() {
        JLabel lbTitle = new JLabel("Gastos / Salidas");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
        return lbTitle;
    }

    private JScrollPane createTableComponent() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Tipo", "Valor", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        this.tablaGastos = table;
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.putClientProperty(FlatClientProperties.STYLE, "showHorizontalLines:true;"
                + "showVerticalLines:true;"
                + "selectionBackground:@background;"
                + "selectionForeground:@foreground;"
                + "font:+2");

        // Header optimizado para el nuevo ancho
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;"
                + "height:40");

        // ScrollPane con ancho completo
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, "border:7,7,7,7;"
                + "background:@background;");

        scrollPane.setPreferredSize(new Dimension(750, 400));
        return scrollPane;
    }

    private JLabel createTotalLabel() {
        JLabel lblTotal = new JLabel();
        lblTotal.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");
        lblTotalLabel = lblTotal;
        return lblTotal;
    }

    private JButton createAgregarButton() {
        JButton btn = createStyledButton("Agregar gasto", "app/icon/svg/gasto.svg");
        btnAdd = btn;
        return btn;
    }

    private JButton createEliminarButton() {
        JButton btn = createStyledButton("Eliminar gasto", "app/icon/svg/remove.svg");
        btnDelete = btn;
        return btn;
    }

    private JButton createEditarButton() {
        JButton btn = createStyledButton("Editar gasto", "app/icon/svg/edit.svg");
        btnEdit = btn; // Asignar el botón a la variable de instancia
        return btn;
    }

    private JButton createDetallesButton() {
        JButton btn = createStyledButton(" Ver detalles", "app/icon/svg/info.svg");
        btnDetails = btn;
        return btn;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton btn = new JButton(text, new FlatSVGIcon(iconPath).derive(20, 20));
        btn.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "font:+2");
        btn.setPreferredSize(new Dimension(90, 35));
        return btn;
    }

    // ==============================
    // Datos / Tabla
    // ==============================

    public void llenarTabla() {
        try{
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Tipo", "Valor", "Fecha"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Ninguna celda editable
                }
            };
            for (int i = 0; i < controller.getGastos().size(); i++) {
                model.addRow(new Object[]{
                        controller.getGastos().get(i).getTipoDeGasto(),
                        controller.getGastos().get(i).getValorGasto(),
                        controller.getGastos().get(i).getFechaGasto(),
                });
            }
            tablaGastos.setModel(model);
        }catch (ApiException ex){
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }

    public void setTotal () {
        try{
            List<Gasto> gastos = controller.getGastos();
            double total = 0;
            for (Gasto gasto : gastos) {
                total += gasto.getValorGasto();
            }
            lblTotalLabel.setText("Total: "  + total);
        }catch (ApiException ex){
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }

    // ==============================
    // Funcionalidad de botones
    // ==============================

    private void addOverhead(){
        NewOverheadForm newOverheadForm = new NewOverheadForm(this);
        JFrame frame = new JFrame("Agregar gasto");
        frame.setContentPane(newOverheadForm);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    private void editOverhead(){
        String id = getIdGastoSeleccionado();
        if (id == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Seleccione un gasto para editar");
        }
        else {
            Gasto gasto = controller.getByID(id);
            EditOverheadForm editOverheadForm = new EditOverheadForm(this, gasto);
            JFrame frame = new JFrame("Editar gasto");
            frame.setContentPane(editOverheadForm);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
        }
    }

    private void removeOverhead(){
        String id = getIdGastoSeleccionado();
        if (id == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Selecciona un gasto para eliminar");
        }
        else {
            controller.removeGasto(id);
            llenarTabla();
            setTotal();
        }
    }

    private void detailsOverhead(){
        String id = getIdGastoSeleccionado();
        if  (id == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Selecciona un gasto para ver sus detalles");
        }
        else {
            JOptionPane.showMessageDialog(null, controller.getByID(id));
        }
    }

    // ==============================
    // Helpers
    // ==============================

    private String getIdGastoSeleccionado() {
        int filaVista = tablaGastos.getSelectedRow();
        if (filaVista >= 0) {
            int filaModelo = tablaGastos.convertRowIndexToModel(filaVista);
            List<Gasto> gastos = controller.getGastos();
            if (filaModelo < gastos.size()) {
                return gastos.get(filaModelo).getIdGasto();
            }
        }
        return null;
    }

    // ==============================
    // Atributos
    // ==============================

    private JTable tablaGastos;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDetails;
    private JButton btnDelete;
    private JLabel lblTotalLabel;
}