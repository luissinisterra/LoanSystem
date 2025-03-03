package app.views;

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
        JTextField txtClient = createFormField("Buscar por cliente");
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Todos", "Activo", "Inactivo", "Pagado"});
        JTextField txtDateStart = createFormField("Fecha inicial (dd/mm/aaaa)");
        JTextField txtDateEnd = createFormField("Fecha final (dd/mm/aaaa)");
        JTextField txtMinAmount = createFormField("Monto mínimo");
        JTextField txtMaxAmount = createFormField("Monto máximo");

        // Botones de acción
        JButton btnFilter = createActionButton("Aplicar Filtros");
        JButton btnReset = createActionButton("Restablecer");

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        panel.add(new JLabel("Cliente: (Opcional)"), "gapy 8");
        panel.add(txtClient, "growx, wrap");

        panel.add(new JLabel("Estado:"), "gapy 8");
        panel.add(cbStatus, "growx, wrap");

        panel.add(new JLabel("Rango de fechas:"), "gapy 8");
        panel.add(txtDateStart, "split 2, growx");
        panel.add(txtDateEnd, "growx, wrap");

        panel.add(new JLabel("Rango de montos:"), "gapy 8");
        panel.add(txtMinAmount, "split 2, growx");
        panel.add(txtMaxAmount, "growx, wrap");

        panel.add(btnFilter, "split 3, gapy 20");
        panel.add(btnReset);

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