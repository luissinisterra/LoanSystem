package app.view;

import app.controller.GastoController;
import app.model.Gasto;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Overheads extends JPanel {

    private GastoController controller;
    private JTable tablaGastos;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JLabel lblTotalLabel;

    public Overheads() {
        controller = new GastoController();
        init();
        llenarTabla();
        addOverhead();
        removeOverhead();
        setTotal();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        JPanel panel = createMainContentPanel();

        panel.add(createTitleLabel(), "gapbottom 15, al center");
        panel.add(createTableComponent(), "grow, gapbottom 20");
        panel.add(createTotalLabel(), "al right, pad 0 15 0 0, split 3, wmin 150");
        panel.add(createEliminarButton(), "gapy 20");
        panel.add(createAgregarButton(), "gapy 20, gapright push");

        add(panel);
    }

    // --- Componentes separados ---

    private JPanel createMainContentPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 25", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");
        return panel;
    }

    private JLabel createTitleLabel() {
        JLabel lbTitle = new JLabel("Gastos / Salidas");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
        return lbTitle;
    }

    private JScrollPane createTableComponent() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tipo", "Valor", "Fecha"});
        JTable table = new JTable(model);
        this.tablaGastos = table;
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        // Configuración del header
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        return scrollPane;
    }

    private JLabel createTotalLabel() {
        JLabel lblTotal = new JLabel();
        lblTotal.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");
        lblTotalLabel = lblTotal;
        return lblTotal;
    }

    private JButton createAgregarButton() {
        JButton btn = createStylizedButton("Agregar gasto");
        btnAdd = btn;
        return btn;
    }

    private JButton createEliminarButton() {
        JButton btn = createStylizedButton("Eliminar gasto");
        btnDelete = btn;
        return btn;
    }

    private JButton createStylizedButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "font:+2");
        button.setPreferredSize(new Dimension(100, 20));
        return button;
    }

    public void llenarTabla() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tipo", "Valor", "Fecha"});
        for (int i = 0; i < controller.getGastos().size(); i++) {
            model.addRow(new Object[]{
                    controller.getGastos().get(i).getTipoDeGasto(),
                    controller.getGastos().get(i).getValorGasto(),
                    controller.getGastos().get(i).getFechaGasto(),
            });
        }
        tablaGastos.setModel(model);
    }

    private void addOverhead(){
        // Agregar el evento manualmente
        btnAdd.addActionListener(e -> {
            NewOverhead newOverhead = new NewOverhead(this);
            JFrame frame = new JFrame("Agregar gasto");
            frame.setContentPane(newOverhead);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void removeOverhead(){
        // Agregar el evento manualmente
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = getIdGastoSeleccionado();
                controller.removeGasto(id);
                llenarTabla();
                setTotal();
            }
        });
    }

    private String getIdGastoSeleccionado() {
        int filaVista = tablaGastos.getSelectedRow();
        if (filaVista >= 0) {
            int filaModelo = tablaGastos.convertRowIndexToModel(filaVista);
            List<Gasto> gastos = controller.getGastos();
            if (filaModelo < gastos.size()) {
                return gastos.get(filaModelo).getIdGasto();
            }
        }
        return null;
    }

    public void setTotal () {
        List<Gasto> gastos = controller.getGastos();
        double total = 0;
        for (Gasto gasto : gastos) {
            total += gasto.getValorGasto();
        }
        lblTotalLabel.setText("Total: "  + total);
    }
}