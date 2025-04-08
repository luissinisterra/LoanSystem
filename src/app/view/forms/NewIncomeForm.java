package app.view.forms;

import app.controller.IncomeController;
import app.exception.ApiException;
import app.view.Incomes;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class NewIncomeForm extends JPanel {

    private final IncomeController controller = new IncomeController();
    private Incomes over;

    public NewIncomeForm(Incomes over) {
        this.over = over;
        init();
        setupActions();
    }

    // ==============================
    // Inicialización y estructura
    // ==============================

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
        add(createMainPanel(), "grow");
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 600]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" + "arc:20");

        lbTitle = new JLabel("Crear un ingreso");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        cbType = new JComboBox<>(new String[]{"Ingreso", "Pago"});
        txtDetail = createFormField("Detalle");
        txtValue = createFormField("Valor Ingreso/Pago");

        btnAdd = createActionButton("Agregar");
        btnCancel = createActionButton("Cancelar");

        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        panel.add(new JLabel("Tipo de entrada:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor Ingreso/Pago:"), "gapy 8");
        panel.add(txtValue, "growx, wrap");

        panel.add(btnAdd, "split 2, gapy 20");
        panel.add(btnCancel);

        return panel;
    }

    // ==============================
    // Acciones
    // ==============================

    private void setupActions() {
        btnAdd.addActionListener(e -> handleAddIncome());
        btnCancel.addActionListener(e -> handleCloseForm());
    }

    private void handleAddIncome() {
        String type = cbType.getSelectedItem().toString();
        String detail = txtDetail.getText();
        String value = txtValue.getText();

        if (type.trim().isEmpty() || detail.trim().isEmpty() || value.trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Campos vacíos");
        } else if (value.matches(".*[a-zA-Z].*")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Campo numérico con letras");
        } else {
            try {
                controller.addIncome(type, detail, Double.parseDouble(value));
                over.llenarTabla();
                over.setTotal();
                closeWindow();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Ingreso creado correctamente");
            } catch (ApiException ex) {
                Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
            }
        }
    }

    private void handleCloseForm() {
        closeWindow();
    }

    private void closeWindow() {
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
                "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "foreground:@foreground;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "arc:10");
        return button;
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
