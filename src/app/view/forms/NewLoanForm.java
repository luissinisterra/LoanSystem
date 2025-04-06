package app.view.forms;

import app.model.Loan;
import app.view.LoanListView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class NewLoanForm extends JPanel {
    private JTextField txtClient;
    private JTextField txtAmount;
    private JTextField txtInterestRate;
    private JTextField txtTerm;
    private JComboBox<String> cbStatus;
    private JTextField txtDate;
    private JButton cmdSave;
    private LoanListView listView;

    public NewLoanForm(LoanListView listView) {
        init();
        this.listView = listView;
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        // Título
        JLabel lbTitle = new JLabel("Nuevo Préstamo");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Descripción
        JLabel description = new JLabel("Ingrese los datos del nuevo préstamo.");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        // Campos de texto
        txtClient = createTextField("Cliente");
        txtAmount = createTextField("Monto");
        txtInterestRate = createTextField("Tasa de Interés");
        txtTerm = createTextField("Plazo (meses)");
        cbStatus = createComboBox();
        txtDate = createTextField("Fecha (YYYY-MM-DD)");

        // Botón Guardar
        cmdSave = new JButton("Guardar");
        cmdSave.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");
        cmdSave.addActionListener(e -> {
            if (validateFields()) {
                saveLoan();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Préstamo guardado correctamente");

                // Cerrar la ventana después de guardar
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete todos los campos obligatorios");
            }
        });

        // Agregar componentes al panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Cliente:"), "gapy 10");
        panel.add(txtClient);
        panel.add(new JLabel("Monto:"), "gapy 8");
        panel.add(txtAmount);
        panel.add(new JLabel("Tasa de Interés:"), "gapy 8");
        panel.add(txtInterestRate);
        panel.add(new JLabel("Plazo (meses):"), "gapy 8");
        panel.add(txtTerm);
        panel.add(new JLabel("Estado:"), "gapy 8");
        panel.add(cbStatus);
        panel.add(new JLabel("Fecha:"), "gapy 8");
        panel.add(txtDate);
        panel.add(cmdSave, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Activo", "Inactivo", "Pagado"});
        comboBox.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");
        return comboBox;
    }

    private boolean validateFields() {
        return !txtClient.getText().isEmpty() &&
                !txtAmount.getText().isEmpty() &&
                !txtInterestRate.getText().isEmpty() &&
                !txtTerm.getText().isEmpty() &&
                !txtDate.getText().isEmpty();
    }

    private void saveLoan() {
        // Aquí puedes agregar la lógica para guardar el préstamo en la base de datos o en memoria
        System.out.println("Guardando préstamo...");
        System.out.println("Cliente: " + txtClient.getText());
        System.out.println("Monto: " + txtAmount.getText());
        System.out.println("Tasa de Interés: " + txtInterestRate.getText());
        System.out.println("Plazo: " + txtTerm.getText());
        System.out.println("Estado: " + cbStatus.getSelectedItem());
        System.out.println("Fecha: " + txtDate.getText());

        // Ejemplo de creación de un objeto Loan
        /*
        Loan loan = new Loan(
                generateUniqueId(), // Generar un ID único para el préstamo
                txtClient.getText(),
                Double.parseDouble(txtAmount.getText()),
                Double.parseDouble(txtInterestRate.getText()),
                Integer.parseInt(txtTerm.getText()),
                cbStatus.getSelectedItem().toString(),
                LocalDate.parse(txtDate.getText())
        );
        loanController.createLoan(loan);
        */
    }
}