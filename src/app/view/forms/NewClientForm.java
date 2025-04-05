package app.view.forms;

import app.model.Address;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class NewClientForm extends JPanel {

    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtSecondName;
    private JTextField txtFirstSurname;
    private JTextField txtSecondSurname;
    private JTextField txtAge;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtCountry;
    private JTextField txtDepartment;
    private JTextField txtCity;
    private JTextField txtStreet;
    private JTextField txtPostalCode;
    private JButton cmdSave;

    public NewClientForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        // Campos de texto
        txtId = createTextField("ID del cliente");
        txtFirstName = createTextField("Primer nombre");
        txtSecondName = createTextField("Segundo nombre (opcional)");
        txtFirstSurname = createTextField("Primer apellido");
        txtSecondSurname = createTextField("Segundo apellido (opcional)");
        txtAge = createTextField("Edad");
        txtEmail = createTextField("Correo electrónico");
        txtPhone = createTextField("Teléfono");
        txtCountry = createTextField("País");
        txtDepartment = createTextField("Departamento");
        txtCity = createTextField("Ciudad");
        txtStreet = createTextField("Calle");
        txtPostalCode = createTextField("Código postal");

        // Botón Guardar
        cmdSave = new JButton("Guardar");
        cmdSave.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        cmdSave.addActionListener(e -> {
            if (validateFields()) {
                saveClient();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cliente guardado correctamente");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete todos los campos obligatorios");
            }
        });

        // Título y descripción
        JLabel lbTitle = new JLabel("Nuevo Cliente");
        JLabel description = new JLabel("Ingrese los datos del cliente a registrar.");
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
        panel.add(new JLabel("País"), "gapy 8");
        panel.add(txtCountry);
        panel.add(new JLabel("Departamento"), "gapy 8");
        panel.add(txtDepartment);
        panel.add(new JLabel("Ciudad"), "gapy 8");
        panel.add(txtCity);
        panel.add(new JLabel("Calle"), "gapy 8");
        panel.add(txtStreet);
        panel.add(new JLabel("Código postal"), "gapy 8");
        panel.add(txtPostalCode);
        panel.add(cmdSave, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private boolean validateFields() {
        return !txtId.getText().isEmpty() &&
                !txtFirstName.getText().isEmpty() &&
                !txtFirstSurname.getText().isEmpty() &&
                !txtAge.getText().isEmpty() &&
                !txtEmail.getText().isEmpty() &&
                !txtPhone.getText().isEmpty() &&
                !txtCountry.getText().isEmpty() &&
                !txtDepartment.getText().isEmpty() &&
                !txtCity.getText().isEmpty() &&
                !txtStreet.getText().isEmpty() &&
                !txtPostalCode.getText().isEmpty();
    }

    private void saveClient() {
        // Crear un objeto Address con los datos ingresados
        Address address = new Address(
                txtCountry.getText(),
                txtDepartment.getText(),
                txtCity.getText(),
                txtStreet.getText(),
                txtPostalCode.getText()
        );

        // Aquí puedes agregar la lógica para guardar el cliente en la base de datos o en memoria
        System.out.println("Guardando cliente...");
        System.out.println("ID: " + txtId.getText());
        System.out.println("Nombre: " + txtFirstName.getText() + " " + txtSecondName.getText());
        System.out.println("Apellidos: " + txtFirstSurname.getText() + " " + txtSecondSurname.getText());
        System.out.println("Edad: " + txtAge.getText());
        System.out.println("Correo: " + txtEmail.getText());
        System.out.println("Teléfono: " + txtPhone.getText());
        System.out.println("Dirección:");
        System.out.println("  País: " + address.getCountry());
        System.out.println("  Departamento: " + address.getDeparment());
        System.out.println("  Ciudad: " + address.getCity());
        System.out.println("  Calle: " + address.getStreet());
        System.out.println("  Código postal: " + address.getPostalCode());
    }
}