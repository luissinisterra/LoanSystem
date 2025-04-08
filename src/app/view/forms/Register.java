package app.view.forms;

import app.Application;
import app.controller.UserController;
import app.exception.ApiException;
import app.manager.FormsManager;
import app.model.User;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {
    private UserController userController;

    public Register() {
        init();
        this.userController = new UserController();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        cmdRegister = new JButton("Registrate");

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombres");
        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Apellidos");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingresa tu correo");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingresa tu usuario");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingresa tu clave");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vuelve a ingresar tu clave");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel lbTitle = new JLabel("¡Bienvenido a nuestra app de prestamos!");
        JLabel description = new JLabel("¡Unete para tener un mejor control de tus prestamos y finanzas!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");


        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Nombre completo"), "gapy 10");
        panel.add(txtFirstName, "split 2");
        panel.add(txtLastName);
        panel.add(new JLabel("Genero"), "gapy 8");
        panel.add(createGenderPanel());
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Correo electronico"), "gapy 10");
        panel.add(txtEmail);
        panel.add(new JLabel("Nombre de usuario"));
        panel.add(txtUsername);
        panel.add(new JLabel("Clave"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Confirmar clave"), "gapy 0");
        panel.add(txtConfirmPassword);
        panel.add(cmdRegister, "gapy 20");
        panel.add(createLoginLabel(), "gapy 10");
        add(panel);
        register();
    }

    private Component createGenderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        jrMale = new JRadioButton("Hombre");
        jrFemale = new JRadioButton("Mujer");
        groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);
        jrMale.setSelected(true);
        panel.add(jrMale);
        panel.add(jrFemale);
        return panel;
    }

    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JButton cmdLogin = new JButton("<html><a href=\"#\">Inicia sesión</a></html>");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Login());
        });
        JLabel label = new JLabel("¿Ya tienes una cuenta?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdLogin);
        return panel;
    }

    public boolean isMatchPassword() {
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());
        return password.equals(confirmPassword);
    }

    private void register() {
        cmdRegister.addActionListener(e -> ejecutarRegistro());
    }

    private void ejecutarRegistro() {
        try {
            // Obtener datos
            String password = String.valueOf(txtPassword.getPassword());
            String names = txtFirstName.getText();
            String surnames = txtLastName.getText();
            String email = txtEmail.getText();
            String username = txtUsername.getText();
            String gender = getGender();

            // Validaciones
            if (hayCamposVacios(password, names, surnames, email, username, gender)) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Hay algunos campos vacíos");
                return;
            }

            if (!isMatchPassword()) {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Las contraseñas no coinciden");
                return;
            }

            // Registro
            User user = userController.saveUser(names, surnames, email, password, username, gender);

            // Login automático
            login(user);

            // Confirmación
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "¡Registro exitoso!");

        } catch (ApiException ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }

    private void login(User user) {
        Application.setUserToMainForm(user);
        Application.login();
    }

    private boolean hayCamposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private String getGender() {
        ButtonGroup groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);
        if (jrMale.isSelected()) {
            return "M";
        }
        if (jrFemale.isSelected()) {
            return "F";
        }
        return "";
    }

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private ButtonGroup groupGender;
    private JButton cmdRegister;
}

