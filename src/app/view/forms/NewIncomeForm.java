package app.view.forms;

import app.controller.IncomeController;
import app.view.Incomes;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class NewIncomeForm extends JPanel {

    private JLabel lbTitle;
    private JComboBox<String> cbType;
    private JTextField txtDetail;
    private JTextField txtValue;
    private JButton btnAdd;
    private JButton btnCancel;
    private IncomeController controller;
    private Incomes over;

    public NewIncomeForm(Incomes over) {
        init();
        controller = new IncomeController();
        addIncome();
        closeForm();
        this.over = over;
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
        lbTitle = new JLabel("Crear un ingreso");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
    }

    // Crea los componentes de filtrado
    private void createFilterComponents() {
        cbType = new JComboBox<>(new String[]{"Ingreso", "Pago"});
        txtDetail = createFormField("Detalle");
        txtValue = createFormField("Valor Ingreso/Pago");
    }

    // Crea los botones de acción
    private void createActionButtons() {
        btnAdd = createActionButton("Agregar");
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
        panel.add(new JLabel("Tipo de entrada:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor Ingreso/Pago:"), "gapy 8");
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

    private void addIncome(){
        // Agregar el evento manualmente
        btnAdd.addActionListener(e -> {
            String type = cbType.getSelectedItem().toString();
            String detail = txtDetail.getText();
            String value = txtValue.getText();
            if (type.trim().isEmpty() || detail.trim().isEmpty() || value.trim().isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Campos vacios");
            }
            if (value.matches(".*[a-zA-Z].*")){
                Notifications.getInstance().show(Notifications.Type.WARNING, "Campo numerico con letras");
            }
            else {
                controller.addIncome(type, detail, Double.parseDouble(value));
                over.llenarTabla();
                over.setTotal();
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Ingreso creado correctamente");
                    window.dispose();
                }
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

}