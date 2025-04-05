package app.view.forms;

import app.controller.GastoController;
import app.model.Gasto;
import app.view.Overheads;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class EditOverheadForm extends JPanel {

    private JLabel lbTitle;
    private JComboBox<String> cbType;
    private JTextField txtDetail;
    private JTextField txtValue;
    private JButton btnAdd;
    private JButton btnCancel;
    private GastoController controller;
    private Overheads over;
    private Gasto gasto;

    public EditOverheadForm(Overheads over, Gasto gasto) {
        init();
        controller = new GastoController();
        editOverhead();
        closeForm();
        this.over = over;
        this.gasto = gasto;
        setText(this.gasto);
    }

    private void init() {
        configureLayout();
        createTitle();
        createFilterComponents();
        createActionButtons();
        assemblePanel();
    }

    // Configura el diseño principal
    private void configureLayout() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
    }

    // Crea el título del panel
    private void createTitle() {
        lbTitle = new JLabel("Crear un gasto");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
    }

    // Crea los componentes de filtrado
    private void createFilterComponents() {
        cbType = new JComboBox<>(new String[]{"Gastos", "Salida"});
        txtDetail = createFormField("Detalle");
        txtValue = createFormField("");
    }

    // Crea los botones de acción
    private void createActionButtons() {
        btnAdd = createActionButton("Editar");
        btnCancel = createActionButton("Cancelar");
    }

    // Ensambla el panel principal
    private void assemblePanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 600]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" + "arc:20");

        // Agregar título al panel
        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        // Agregar componentes de filtrado
        panel.add(new JLabel("Tipo de salida:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor gasto/salida:"), "gapy 8");
        panel.add(txtValue, "growx, wrap");

        // Agregar botones de acción
        panel.add(btnAdd, "split 2, gapy 20");
        panel.add(btnCancel);

        // Agregar el panel principal al contenedor
        add(panel, "grow");
    }

    // Método auxiliar para crear campos de formulario
    private JTextField createFormField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);"
                        + "foreground:@foreground;"
                        + "arc:10");
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    // Método auxiliar para crear botones de acción
    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);"
                        + "[dark]background:lighten(@background,10%);"
                        + "foreground:@foreground;"
                        + "focusWidth:0;"
                        + "innerFocusWidth:0;"
                        + "arc:10");
        return button;
    }

    private void editOverhead(){
        // Agregar el evento manualmente
        btnAdd.addActionListener(e -> {
            String type = cbType.getSelectedItem().toString();
            String detail = txtDetail.getText();
            String value = txtValue.getText();
            String id = this.gasto.getIdGasto();
            controller.updateGasto(id, type, detail, Double.parseDouble(value));
            over.llenarTabla();
            over.setTotal();
            JOptionPane.showMessageDialog(null, "Gasto editado correctamente!",  "Gasto editado", JOptionPane.INFORMATION_MESSAGE);
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void closeForm(){
        // Agregar el evento manualmente
        btnCancel.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void setText(Gasto gasto) {
        txtDetail.setText(gasto.getDescripcionGasto());
        txtValue.setText(String.valueOf(gasto.getValorGasto()));
        cbType.setSelectedIndex(getIndex(gasto.getTipoDeGasto()));
    }

    private int getIndex(String tipo){
        if (tipo.equals("Gastos")){
            return 0;
        }
        if (tipo.equals("Salida")){
            return 1;
        }
        return -1;
    }

}