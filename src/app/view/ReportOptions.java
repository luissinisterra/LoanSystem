package app.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ReportOptions extends JPanel {

    public ReportOptions() {
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
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"1 dia", "1 semana", "1 mes", "3 meses", "6 meses", "1 año"});

        // Botones de acción
        JButton btnFilter = createActionButton("Buscar reporte");

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        panel.add(new JLabel("Rango de fecha:"), "gapy 8");
        panel.add(cbStatus, "growx, wrap");
        panel.add(btnFilter, "split 3, gapy 20");

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