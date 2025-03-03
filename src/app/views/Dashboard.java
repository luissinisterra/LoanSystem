package app.views;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Dashboard extends JPanel {

    public Dashboard() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[fill, 800]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Dashboard");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +18");

        // Panel de resumen
        JPanel summaryPanel = createSummaryPanel();
        panel.add(summaryPanel, "growx, wrap");

        // Panel de gráficos
        JPanel chartsPanel = createChartsPanel();
        panel.add(chartsPanel, "growx, wrap");

        // Panel de últimos préstamos
        JPanel recentLoansPanel = createRecentLoansPanel();
        panel.add(recentLoansPanel, "growx, wrap");

        add(panel, "grow");
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[fill, 250:300]"));

        // Resumen de préstamos
        JLabel lbTotalActiveLoans = createSummaryLabel("Préstamos Activos", "15");
        JLabel lbTotalInactiveLoans = createSummaryLabel("Préstamos Inactivos", "5");
        JLabel lbTotalAmount = createSummaryLabel("Monto Total Prestado", "$250,000");

        panel.add(lbTotalActiveLoans, "growx");
        panel.add(lbTotalInactiveLoans, "growx");
        panel.add(lbTotalAmount, "growx");

        return panel;
    }

    private JLabel createSummaryLabel(String title, String value) {
        JLabel label = new JLabel("<html><div style='text-align:center;'><b>" + title + "</b><br>" + value + "</div></html>");
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +14");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[fill, 400]"));

        // Gráfico de barras: Distribución de préstamos por estado
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(15, "Préstamos", "Activo");
        barDataset.addValue(5, "Préstamos", "Inactivo");
        barDataset.addValue(10, "Préstamos", "Pagado");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Distribución de Préstamos por Estado", // Título
                "Estado", // Etiqueta del eje X
                "Cantidad", // Etiqueta del eje Y
                barDataset, // Datos
                PlotOrientation.VERTICAL, // Orientación de la gráfica
                true,  // Mostrar leyenda
                true,  // Habilitar tooltips
                false  // Habilitar URLs
        );

        ChartPanel barChartPanel = new ChartPanel(barChart);
        panel.add(barChartPanel, "growx");

        // Gráfico circular: Distribución de préstamos por cliente
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Juan Pérez", 10);
        pieDataset.setValue("María López", 5);
        pieDataset.setValue("Carlos Sánchez", 5);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribución de Préstamos por Cliente", // Título
                pieDataset, // Datos
                true, // Leyenda
                true, // Tooltips
                false // URLs
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Juan Pérez", Color.BLUE);
        plot.setSectionPaint("María López", Color.GREEN);
        plot.setSectionPaint("Carlos Sánchez", Color.RED);

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        panel.add(pieChartPanel, "growx");

        return panel;
    }

    private JPanel createRecentLoansPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[fill, 800]"));

        // Título
        JLabel lbTitle = new JLabel("Últimos Préstamos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +14");
        panel.add(lbTitle, "growx, wrap");

        // Tabla de últimos préstamos
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Cliente");
        model.addColumn("Monto");
        model.addColumn("Tasa de Interés");
        model.addColumn("Plazo (meses)");
        model.addColumn("Estado");
        model.addColumn("Fecha");

        // Datos de ejemplo
        model.addRow(new Object[]{"Juan Pérez", "$10,000", "5%", "12", "Activo", "2023-10-01"});
        model.addRow(new Object[]{"María López", "$15,000", "7%", "24", "Activo", "2023-09-15"});
        model.addRow(new Object[]{"Carlos Sánchez", "$20,000", "6%", "36", "Inactivo", "2023-08-20"});

        JTable table = new JTable(model);
        table.setRowHeight(40); // Altura de las filas
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(230, 230, 230));

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;" +
                "background:$Table.background");

        panel.add(scrollPane, "grow, push, wrap");

        return panel;
    }
}