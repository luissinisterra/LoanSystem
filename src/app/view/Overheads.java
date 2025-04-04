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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Overheads extends JPanel {

    private GastoController controller;
    private JTable tablaGastos;

    public Overheads() {
        controller = new GastoController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 35", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        JLabel lbTitle = new JLabel("Gastos / Salidas");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");

        List<Gasto> gastos = controller.getGastos();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tipo", "Valor", "Fecha"});
        for (int i = 0; i < gastos.size(); i++) {
            model.addRow(new Object[]{
                    gastos.get(i).getTipoDeGasto(),
                    gastos.get(i).getValorGasto(),
                    gastos.get(i).getFechaGasto()
            });
        }

        JTable table = new JTable(model);
        this.tablaGastos = table;

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaVista = table.getSelectedRow();
                if (filaVista >= 0) {
                    int filaModelo = table.convertRowIndexToModel(filaVista);
                    Gasto gastoSeleccionado = gastos.get(filaModelo);
                    String id = gastoSeleccionado.getIdGasto();
                    controller.removeGasto(id);
                }
            }
        });

        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        JLabel lblTotal = new JLabel("Total: $1.175");
        lblTotal.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");

        JButton btnOpciones = new JButton("Agregar gasto");
        JButton btnExportar = new JButton("Eliminar gasto");

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

        panel.add(lbTitle, "gapbottom 15, al center");
        panel.add(scrollPane, "grow, gapbottom 20");
        panel.add(lblTotal, "al right, pad 0 15 0 0, split 3, wmin 150");
        panel.add(btnExportar, "gapy 20");
        panel.add(btnOpciones, "gapy 20, gapright push");
        add(panel);
    }
}
