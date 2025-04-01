package app.view.forms;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Raven
 */
public class PanelLogin extends JPanel {

    public PanelLogin() {
        setLayout(new MigLayout("fillx,wrap,insets 30 40 50 40, width 320", "[fill]", "[]20[][]15[][]30[]"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "arc:20;");
    }

    public static class NewLoanForm extends JPanel {

        public NewLoanForm() {
            init();
        }

        private void init() {
            setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

            // Panel principal con bordes redondeados
            JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 360]"));
            panel.putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:$Menu.background;" +
                    "arc:20");

            // Título
            JLabel lbTitle = new JLabel("Nuevo Préstamo");
            lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                    + "font:bold +16");

            // Campos del formulario
            JTextField txtClient = createFormField("Cliente");
            JTextField txtAmount = createFormField("Monto");
            JTextField txtInterestRate = createFormField("Tasa de Interés");
            JTextField txtTerm = createFormField("Plazo (meses)");
            JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Activo", "Inactivo", "Pagado"});
            JTextField txtDate = createFormField("Fecha");

            // Botones de acción
            JButton btnSave = createActionButton("Guardar");
            JButton btnCancel = createActionButton("Cancelar");

            // Agregar componentes al panel
            panel.add(lbTitle, "growx, wrap");
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
            panel.add(btnSave, "split 2, gapy 20");
            panel.add(btnCancel);

            add(panel, "grow");
        }

        private JTextField createFormField(String placeholder) {
            JTextField field = new JTextField();
            field.putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:lighten(@background,5%);" +
                    "foreground:@foreground;" +
                    "arc:10");
            field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
            return field;
        }

        private JButton createActionButton(String text) {
            JButton button = new JButton(text);
            button.putClientProperty(FlatClientProperties.STYLE, ""
                    + "[light]background:darken(@background,10%);" +
                    "[dark]background:lighten(@background,10%);" +
                    "foreground:@foreground;" +
                    "borderWidth:0;" +
                    "focusWidth:0;" +
                    "innerFocusWidth:0;" +
                    "arc:10");
            return button;
        }
    }
}
