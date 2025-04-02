package app.view.forms;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class EditClientForm extends JPanel {

    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtSecondName;
    private JTextField txtFirstSurname;
    private JTextField txtSecondSurname;
    private JTextField txtAge;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JButton cmdUpdate;

    public EditClientForm(String id, String firstName, String secondName, String firstSurname, String secondSurname, int age, String email, String phone, String address) {
        init(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, address);
    }

    private void init(String id, String firstName, String secondName, String firstSurname, String secondSurname, int age, String email, String phone, String address) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        // Campos de texto prellenados con los datos del cliente
        txtId = createTextField("ID del cliente");
        txtId.setText(id);
        txtId.setEditable(false); // El ID no debe ser editable

        txtFirstName = createTextField("Primer nombre");
        txtFirstName.setText(firstName);

        txtSecondName = createTextField("Segundo nombre (opcional)");
        txtSecondName.setText(secondName);

        txtFirstSurname = createTextField("Primer apellido");
        txtFirstSurname.setText(firstSurname);

        txtSecondSurname = createTextField("Segundo apellido (opcional)");
        txtSecondSurname.setText(secondSurname);

        txtAge = createTextField("Edad");
        txtAge.setText(String.valueOf(age));

        txtEmail = createTextField("Correo electrónico");
        txtEmail.setText(email);

        txtPhone = createTextField("Teléfono");
        txtPhone.setText(phone);

        txtAddress = createTextField("Dirección");
        txtAddress.setText(address);

        // Botón Actualizar
        cmdUpdate = new JButton("Actualizar");
        cmdUpdate.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        cmdUpdate.addActionListener(e -> {
            if (validateFields()) {
                updateClient();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cliente actualizado correctamente");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete todos los campos obligatorios");
            }
        });

        // Título y descripción
        JLabel lbTitle = new JLabel("Editar Cliente");
        JLabel description = new JLabel("Actualice los datos del cliente seleccionado.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        // Agregar componentes al panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("ID"), "gapy 10");
        panel.add(txtId);
        panel.add(new JLabel("Nombre completo"), "gapy 8");
        panel.add(txtFirstName, "split 2");
        panel.add(txtSecondName);
        panel.add(new JLabel("Apellidos"), "gapy 8");
        panel.add(txtFirstSurname, "split 2");
        panel.add(txtSecondSurname);
        panel.add(new JLabel("Edad"), "gapy 8");
        panel.add(txtAge);
        panel.add(new JLabel("Correo electrónico"), "gapy 8");
        panel.add(txtEmail);
        panel.add(new JLabel("Teléfono"), "gapy 8");
        panel.add(txtPhone);
        panel.add(new JLabel("Dirección"), "gapy 8");
        panel.add(txtAddress);
        panel.add(cmdUpdate, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private boolean validateFields() {
        return !txtFirstName.getText().isEmpty() &&
                !txtFirstSurname.getText().isEmpty() &&
                !txtAge.getText().isEmpty() &&
                !txtEmail.getText().isEmpty() &&
                !txtPhone.getText().isEmpty() &&
                !txtAddress.getText().isEmpty();
    }

    private void updateClient() {
        // Aquí puedes agregar la lógica para actualizar el cliente en la base de datos o en memoria
        System.out.println("Actualizando cliente...");
        System.out.println("ID: " + txtId.getText());
        System.out.println("Nombre: " + txtFirstName.getText() + " " + txtSecondName.getText());
        System.out.println("Apellidos: " + txtFirstSurname.getText() + " " + txtSecondSurname.getText());
        System.out.println("Edad: " + txtAge.getText());
        System.out.println("Correo: " + txtEmail.getText());
        System.out.println("Teléfono: " + txtPhone.getText());
        System.out.println("Dirección: " + txtAddress.getText());
    }
}