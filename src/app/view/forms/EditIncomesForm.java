package app.view.forms;

import app.controller.IncomeController;
import app.exception.ApiException;
import app.model.Income;
import app.view.Incomes;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class EditIncomesForm extends JPanel {

    private final IncomeController controller = new IncomeController();
    private Incomes over;
    private Income income;

    public EditIncomesForm(Incomes over, Income income) {
        this.over = over;
        this.income = income;
        init();
    }

    // ==============================
    // Inicialización y estructura
    // ==============================

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
        add(createMainPanel(), "grow");
        setupActions();
        setIncomeData(income);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 600]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" + "arc:20");

        JLabel lbTitle = new JLabel("Editar un ingreso");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        cbType = new JComboBox<>(new String[]{"Ingreso", "Pago"});
        txtDetail = createFormField("Detalle");
        txtValue = createFormField("Monto del ingreso");

        btnEdit = createActionButton("Editar");
        btnCancel = createActionButton("Cancelar");

        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        panel.add(new JLabel("Tipo de ingreso:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor Ingreso/Pago:"), "gapy 8");
        panel.add(txtValue, "growx, wrap");

        panel.add(btnEdit, "split 2, gapy 20");
        panel.add(btnCancel);

        return panel;
    }

    // ==============================
    // Acciones
    // ==============================

    private void setupActions() {
        btnEdit.addActionListener(e -> handleEditIncome());
        btnCancel.addActionListener(e -> handleCloseForm());
    }

    private void handleEditIncome() {
        try{
            String type = cbType.getSelectedItem().toString();
            String detail = txtDetail.getText();
            String value = txtValue.getText();
            String id = income.getIncomeID();

            if (type.trim().isEmpty() || detail.trim().isEmpty() || value.trim().isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Campos vacíos");
            } else if (value.matches(".*[a-zA-Z].*")) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Campo numérico con letras");
            } else {
                controller.updateIncome(id, type, detail, Double.parseDouble(value));
                over.llenarTabla();
                over.setTotal();
                closeWindow();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Ingreso editado correctamente");
            }
        }catch (ApiException ex){
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
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
    // Helpers
    // ==============================

    private void setIncomeData(Income income) {
        txtDetail.setText(income.getIncomeDescription());
        txtValue.setText(String.valueOf(income.getIncomeAmount()));
        cbType.setSelectedIndex(getTypeIndex(income.getIncomeType()));
    }

    private int getTypeIndex(String tipo) {
        return switch (tipo) {
            case "Ingreso" -> 0;
            case "Pago" -> 1;
            default -> -1;
        };
    }

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

    private JComboBox<String> cbType;
    private JTextField txtDetail;
    private JTextField txtValue;
    private JButton btnEdit;
    private JButton btnCancel;
}
