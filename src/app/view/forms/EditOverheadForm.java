package app.view.forms;

import app.controller.GastoController;
import app.exception.ApiException;
import app.model.Gasto;
import app.view.Overheads;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class EditOverheadForm extends JPanel {

    private final GastoController controller = new GastoController();
    private Overheads over;
    private Gasto gasto;

    public EditOverheadForm(Overheads over, Gasto gasto) {
        init();
        this.over = over;
        this.gasto = gasto;
        setText(this.gasto);
    }

    // ==============================
    // Inicialización
    // ==============================

    private void init() {
        configureLayout();
        createTitle();
        createFilterComponents();
        createActionButtons();
        assemblePanel();
        initButtons();
    }

    private void initButtons(){
        btnAdd.addActionListener(e -> editOverhead());
        btnCancel.addActionListener(e -> closeForm());
    }

    // ==============================
    // Construcción visual
    // ==============================

    private void configureLayout() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
    }

    private void createTitle() {
        lbTitle = new JLabel("Editar un gasto");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
    }

    private void createFilterComponents() {
        cbType = new JComboBox<>(new String[]{"Gasto", "Salida"});
        txtDetail = createFormField("Detalle");
        txtValue = createFormField("");
    }

    private void createActionButtons() {
        btnAdd = createActionButton("Editar");
        btnCancel = createActionButton("Cancelar");
    }

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

    // ==============================
    // Funcionalidad de botones
    // ==============================

    private void editOverhead() {
        try {
            String type = cbType.getSelectedItem().toString();
            String detail = txtDetail.getText();
            String value = txtValue.getText();
            String id = this.gasto.getIdGasto();
            if (type.trim().isEmpty() || detail.trim().isEmpty() || value.trim().isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Campos vacios");
            } else if (value.matches(".*[a-zA-Z].*")) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Campo numerico con letras");
            } else {
                controller.updateGasto(id, type, detail, Double.parseDouble(value));
                over.llenarTabla();
                over.setTotal();
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Gasto editado correctamente");
                    window.dispose();
                }
            }
        }catch (ApiException ex){
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }

    private void closeForm() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    // ==============================
    // Helpers UI
    // ==============================

    private JTextField createFormField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);"
                        + "foreground:@foreground;"
                        + "arc:10");
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

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

    private void setText(Gasto gasto) {
        txtDetail.setText(gasto.getDescripcionGasto());
        txtValue.setText(String.valueOf(gasto.getValorGasto()));
        cbType.setSelectedIndex(getIndex(gasto.getTipoDeGasto()));
    }

    private int getIndex(String tipo){
        if (tipo.equals("Gasto")){
            return 0;
        }
        if (tipo.equals("Salida")){
            return 1;
        }
        return -1;
    }

    // ==============================
    // Atributos
    // ==============================

    private JLabel lbTitle;
    private JComboBox<String> cbType;
    private JTextField txtDetail;
    private JTextField txtValue;
    private JButton btnAdd;
    private JButton btnCancel;
}