package app.views;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewIncome extends JPanel {

    public NewIncome() {
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
        JLabel lbTitle = new JLabel("Registro de Gastos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        // Componentes de ingreso
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Gastos", "Salida"});
        JTextField txtDetail = createFormField("Detalle del gasto/salida");
        JTextField txtValue = createFormField("Valor del gasto/salida");
        JTextField txtCategory = createFormField("Categoría (opcional)");
        JTextField txtNotes = createFormField("Notas adicionales (opcional)");

        // Generar la fecha automáticamente con LocalDate
        JTextField txtDate = new JTextField(generateCurrentDate());
        txtDate.setEditable(false); // Desactivar edición manual
        txtDate.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);"
                        + "foreground:@foreground;"
                        + "arc:10");

        // Botones de acción
        JButton btnAdd = createActionButton("Agregar");
        JButton btnCancel = createActionButton("Cancelar");

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        panel.add(new JLabel("Fecha:"), "gapy 8");
        panel.add(txtDate, "growx, wrap");

        panel.add(new JLabel("Tipo de salida:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle del gasto/salida:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor del gasto/salida:"), "gapy 8");
        panel.add(txtValue, "growx, wrap");

        panel.add(new JLabel("Categoría (opcional):"), "gapy 8");
        panel.add(txtCategory, "growx, wrap");

        panel.add(new JLabel("Notas adicionales (opcional):"), "gapy 8");
        panel.add(txtNotes, "growx, wrap");

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

    // Método actualizado con LocalDate
    private String generateCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(formatter);
    }
}