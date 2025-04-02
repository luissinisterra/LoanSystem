package app.view.forms;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class EditLoanForm extends JPanel {

    private JTextField txtClient;
    private JTextField txtAmount;
    private JTextField txtInterestRate;
    private JTextField txtTerm;
    private JComboBox<String> cbStatus;
    private JTextField txtDate;
    private JButton cmdUpdate;

    public EditLoanForm(String client, String amount, String interestRate, String term, String status, String date) {
        init(client, amount, interestRate, term, status, date);
    }

    private void init(String client, String amount, String interestRate, String term, String status, String date) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        // Título
        JLabel lbTitle = new JLabel("Editar Préstamo");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Descripción
        JLabel description = new JLabel("Actualice los datos del préstamo seleccionado.");
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        // Campos del formulario prellenados con los datos del préstamo
        txtClient = createTextField("Cliente");
        txtClient.setText(client);

        txtAmount = createTextField("Monto");
        txtAmount.setText(amount);

        txtInterestRate = createTextField("Tasa de Interés");
        txtInterestRate.setText(interestRate);

        txtTerm = createTextField("Plazo (meses)");
        txtTerm.setText(term);

        cbStatus = createComboBox();
        cbStatus.setSelectedItem(status);

        txtDate = createTextField("Fecha");
        txtDate.setText(date);

        // Botón Actualizar
        cmdUpdate = new JButton("Actualizar");
        cmdUpdate.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        cmdUpdate.addActionListener(e -> {
            if (validateFields()) {
                updateLoan();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Préstamo actualizado correctamente");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete todos los campos obligatorios");
            }
        });

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap");
        panel.add(description, "growx, wrap");
        panel.add(new JLabel("Cliente:"), "gapy 8");
        panel.add(txtClient, "growx, wrap");
        panel.add(new JLabel("Monto:"), "gapy 8");
        panel.add(txtAmount, "growx, wrap");
        panel.add(new JLabel("Tasa de Interés:"), "gapy 8");
        panel.add(txtInterestRate, "growx, wrap");
        panel.add(new JLabel("Plazo (meses):"), "gapy 8");
        panel.add(txtTerm, "growx, wrap");
        panel.add(new JLabel("Estado:"), "gapy 8");
        panel.add(cbStatus, "growx, wrap");
        panel.add(new JLabel("Fecha:"), "gapy 8");
        panel.add(txtDate, "growx, wrap");
        panel.add(cmdUpdate, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Activo", "Inactivo", "Pagado"});
        comboBox.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
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

    private void updateLoan() {
        // Aquí puedes agregar la lógica para actualizar el préstamo en la base de datos o en memoria
        System.out.println("Actualizando préstamo...");
        System.out.println("Cliente: " + txtClient.getText());
        System.out.println("Monto: " + txtAmount.getText());
        System.out.println("Tasa de Interés: " + txtInterestRate.getText());
        System.out.println("Plazo: " + txtTerm.getText());
        System.out.println("Estado: " + cbStatus.getSelectedItem());
        System.out.println("Fecha: " + txtDate.getText());
    }
}