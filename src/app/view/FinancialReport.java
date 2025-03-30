package app.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import app.util.ExportarExcel;

public class FinancialReport extends JPanel {

    public FinancialReport() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal con ancho aumentado
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 50 35 50", "fill,600:800")); // Ancho incrementado
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        // Título
        JLabel lbTitle = new JLabel("Reporte Financiero");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");

        // Descripción
        JLabel description = new JLabel("Resumen ejecutivo - Datos en pesos colombianos (COP)");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        // Tabla con columnas más anchas
        String[] columns = {"Concepto", "Trim. 1", "Trim. 2", "Trim. 3", "Trim. 4"};
        Object[][] data = {
                {"Ingresos", "$120,000", "$135,000", "$142,000", "$160,000"},
                {"Gastos", "$45,000", "$48,000", "$52,000", "$55,000"},
                {"Beneficio Bruto", "$75,000", "$87,000", "$90,000", "$105,000"},
                {"Impuestos", "$18,000", "$21,000", "$23,000", "$26,000"},
                {"Beneficio Neto Final", "$57,000", "$66,000", "$67,000", "$79,000"},
                {"Flujo de Caja Operativo", "$49,000", "$58,000", "$60,000", "$72,000"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.putClientProperty(FlatClientProperties.STYLE, ""
                + "showHorizontalLines:true;"
                + "showVerticalLines:true;"
                + "selectionBackground:@background;"
                + "selectionForeground:@foreground;"
                + "font:+2");

        // Header optimizado para el nuevo ancho
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +2;"
                + "height:40");

        // ScrollPane con ancho completo
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:7,7,7,7;"
                + "background:@background;");

        // Botón con ancho proporcional
        JButton cmdExport = new JButton("Exportar a Excel");
        cmdExport.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "font:+2");

        cmdExport.setPreferredSize(new Dimension(300, 45));// Ancho aumentado

        JButton cmdSapa = new JButton("Opciones de reporte");
        cmdSapa.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "font:+2");
        cmdSapa.setPreferredSize(new Dimension(300, 45));

        // ActionListener para el nuevo botón
        cmdSapa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.view.ReportOptions reportOptions = new ReportOptions();
                JFrame frame = new JFrame("Datos de reporte");
                frame.setContentPane(reportOptions);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        //ActionListener para boton de exportar
        cmdExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportarExcel obj;

                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(table);
                } catch (IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }
        });


        // Composición final
        panel.add(lbTitle, "gapbottom 15");
        panel.add(description, "gapbottom 20");
        panel.add(scrollPane, "grow, h 350!, w 800!"); // Ancho específico
        panel.add(cmdSapa, "gapy 15, split 2, align left, h 45!, w 220!");
        panel.add(cmdExport, "gapy 15, align right, h 45!, w 220!");

        add(panel);
    }
}