package app.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class Dashboard extends JPanel {

    public Dashboard() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, insets 0, wrap", "[left]", "[]20[]"));
        JPanel panel1 = createMainPanel();
        add(panel1, "gapx 20, gapy 20, wrap");

        JPanel panel2 = createSecondaryPanel();
        add(panel2, "gapx 20, gaptop 50");  // Aquí bajamos más el segundo panel
    }


    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 25", "fill,500:400"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");
        panel.add(createHelloLabel());
        return panel;
    }

    private JPanel createSecondaryPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 25", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,2%);" +
                        "[dark]background:lighten(@background,2%)");
        panel.add(createSummaryLabel());
        return panel;
    }


    private JLabel createHelloLabel() {
        JLabel lb = new JLabel("¡Hola, usuario!");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +20");
        helloLabel = lb;
        return lb;
    }

    private JLabel createSummaryLabel(){
        JLabel lb = new JLabel("Aqui hay un pequeño resumen de tu actividad en la app");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        summaryLabel = lb;
        return lb;
    }


    private JLabel helloLabel;
    private JLabel summaryLabel;
}
