package app.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Incomes extends JPanel {

    public Incomes() {
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
        JLabel lbTitle = new JLabel("Ingresos / Entradas");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");

        // Tabla de 2 columnas
        String[] columns = {"Detalles", "Valor"};
        Object[][] data = {
                {"Venta de producto", "2.500.000"},
                {"Donación", "500.000"},
                {"Servicio prestado", "1.200.000"},
                {"Inversión", "3.000.000"},
                {"Venta de producto", "1.800.000"},
                {"Ajuste crédito", "400.000"}
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
        JLabel lblTotal = new JLabel("Total: $9.400.000");
        lblTotal.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");

        // Configuración de botones idénticos
        JButton btnOpciones = new JButton("Agregar ingreso");
        JButton btnExportar = new JButton("Eliminar ingreso");

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
                app.view.NewIncome newIncome = new NewIncome();
                JFrame frame = new JFrame("Agregar ingreso");
                frame.setContentPane(newIncome);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        Dimension buttonSize = new Dimension(120, 20); // Aumenté el ancho para texto más largo
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