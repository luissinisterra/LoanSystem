package app.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class NewOverhead extends JPanel {

    public NewOverhead () {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 600]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;"
                        + "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Opciones de Reporte de Préstamos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        // Componentes de filtrado
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Gastos", "Salida"});
        JTextField txtDetail = createFormField("Detalle");
        JTextField txtValue = createFormField("Valor gasto/salida");

        // Botones de acción
        JButton btnAdd = createActionButton("Agregar");
        JButton btnCancel = createActionButton("Cancelar");

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        panel.add(new JLabel("Tipo de salida:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor gasto/salida:"), "gapy 8");
        panel.add(txtValue, "growx, wrap");

        panel.add(btnAdd, "split 2, gapy 20");
        panel.add(btnCancel);

        add(panel, "grow");
    }

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
}