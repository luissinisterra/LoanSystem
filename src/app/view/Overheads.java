package app.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Overheads extends JPanel {

    public Overheads() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 35", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        // Título modificado
        JLabel lbTitle = new JLabel("Gastos / Salidas");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");

        // Tabla de 2 columnas
        String[] columns = {"Detalles", "Valor"};
        Object[][] data = {
                {"Gasolina", "30.000"},
                {"Gasolina", "30.000"},
                {"Gasolina", "30.00"},
                {"ENTREGA", "400.000"},
                {"Gasolina", "20.000"},
                {"Ajuste crédito sergio", "40.000"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(300); // Ancho para detalles
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        // Header estilo minimalista
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        // Total
        JLabel lblTotal = new JLabel("Total: $1.175");
        lblTotal.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");

        // Configuración de botones idénticos
        // Botones (configuración mejorada)
        JButton btnOpciones = new JButton("Agregar gasto");
        JButton btnExportar = new JButton("Eliminar gasto");

        // Estilo botones
        String buttonStyle =
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "font:+2";

        btnOpciones.putClientProperty(FlatClientProperties.STYLE, buttonStyle);
        btnExportar.putClientProperty(FlatClientProperties.STYLE, buttonStyle);

        btnOpciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.view.NewOverhead newOverhead = new NewOverhead();
                JFrame frame = new JFrame("Agregar gasto");
                frame.setContentPane(newOverhead);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        Dimension buttonSize = new Dimension(100, 20);
        btnOpciones.setPreferredSize(buttonSize);
        btnExportar.setPreferredSize(buttonSize);

        // Distribución CORREGIDA
        panel.add(lbTitle, "gapbottom 15, al center");
        panel.add(scrollPane, "grow, gapbottom 20");
        panel.add(lblTotal, "al right, pad 0 15 0 0, split 3, wmin 150"); // Ancho mínimo de 150px
        panel.add(btnExportar, "gapy 20");
        panel.add(btnOpciones, "gapy 20, gapright push");                // Botón derecho se coloca después
        add(panel);
    }
}