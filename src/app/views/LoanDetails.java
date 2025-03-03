package app.views;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class LoanDetails extends JPanel {

    public LoanDetails() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        // Título
        JLabel lbTitle = new JLabel("Detalles del Préstamo");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");

        // Descripción
        JLabel description = new JLabel("Consulta los detalles del préstamo seleccionado");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        // Etiquetas y campos de solo lectura
        JLabel lblClient = createDetailLabel("Cliente: Juan Pérez");
        JLabel lblAmount = createDetailLabel("Monto: $10,000");
        JLabel lblInterestRate = createDetailLabel("Tasa de Interés: 5%");
        JLabel lblTerm = createDetailLabel("Plazo: 12 meses");
        JLabel lblStatus = createDetailLabel("Estado: Activo");
        JLabel lblDate = createDetailLabel("Fecha: 2023-10-01");

        // Botón para volver a la lista de préstamos
        JButton cmdBack = new JButton("Volver");
        cmdBack.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        // Agregar componentes al panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(lblClient, "gapy 8");
        panel.add(lblAmount, "gapy 8");
        panel.add(lblInterestRate, "gapy 8");
        panel.add(lblTerm, "gapy 8");
        panel.add(lblStatus, "gapy 8");
        panel.add(lblDate, "gapy 8");
        panel.add(cmdBack, "gapy 20");

        add(panel);
    }

    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,10%);" +
                "[dark]foreground:darken(@foreground,10%);" +
                "font: +2");
        return label;
    }
}