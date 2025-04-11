package app.view;

import app.controller.ClientController;
import app.controller.GastoController;
import app.controller.IncomeController;
import app.controller.LoanController;
import app.model.Gasto;
import app.model.Income;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JPanel {

    ClientController clientController = new ClientController();
    LoanController loanController = new LoanController();
    IncomeController incomeController = new IncomeController();
    GastoController gastoController = new GastoController();
    public Dashboard() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, insets 0, wrap", "[left]", "[]20[]"));
        JPanel panel1 = createMainPanel();
        add(panel1, "gapx 20, gapy 20, wrap");

        JPanel panel2 = createSecondaryPanel();
        add(panel2, "gapx 20, gaptop 60");  // Aquí bajamos más el segundo panel

        JPanel panel3 = createCardsContainer();
        add(panel3, "gapx 20, gaptop 40");
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
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");
        panel.add(createSummaryLabel());
        return panel;
    }

    private JPanel createClientCard() {
        JPanel panel = new JPanel(new MigLayout(
                "wrap, insets 30, aligny top",    // alineado arriba y con márgenes
                "[center]",                       // centrar horizontalmente
                "[]20[]20[]"                      // espacio vertical entre componentes
        ));

        // Estilo visual
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)"
        );

        // Ícono SVG más grande
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new FlatSVGIcon("app/icon/svg/info.svg", 48, 48));
        panel.add(iconLabel, "align center");

        // Separador ancho
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(200, 2));
        panel.add(separator, "growx, gapy 10 10");

        // Texto descriptivo
        JLabel textLabel = createClientsLabel();
        JLabel totalLabel = createTotalLabel();
        panel.add(textLabel, "align center");
        panel.add(totalLabel, "align center");
        // Para que crezca más verticalmente:
        panel.setPreferredSize(new Dimension(250, 300)); // menos ancho, más alto
        return panel;
    }

    private JPanel createIncomesCard() {
        JPanel panel = new JPanel(new MigLayout(
                "wrap, insets 30, aligny top",    // alineado arriba y con márgenes
                "[center]",                       // centrar horizontalmente
                "[]20[]20[]"                      // espacio vertical entre componentes
        ));

        // Estilo visual
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)"
        );

        // Ícono SVG más grande
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new FlatSVGIcon("app/icon/svg/finances.svg", 48, 48));
        panel.add(iconLabel, "align center");

        // Separador ancho
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(200, 2));
        panel.add(separator, "growx, gapy 10 10");

        // Texto descriptivo
        JLabel textLabel = createIncomeslabel();
        JLabel totalLabel = getTotalIncomes();
        panel.add(textLabel, "align center");
        panel.add(totalLabel, "align center");
        // Para que crezca más verticalmente:
        panel.setPreferredSize(new Dimension(250, 300)); // menos ancho, más alto
        return panel;
    }

    private JPanel createGastosCard() {
        JPanel panel = new JPanel(new MigLayout(
                "wrap, insets 30, aligny top",    // alineado arriba y con márgenes
                "[center]",                       // centrar horizontalmente
                "[]20[]20[]"                      // espacio vertical entre componentes
        ));

        // Estilo visual
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)"
        );

        // Ícono SVG más grande
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new FlatSVGIcon("app/icon/svg/gasto.svg", 48, 48));
        panel.add(iconLabel, "align center");

        // Separador ancho
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(200, 2));
        panel.add(separator, "growx, gapy 10 10");

        // Texto descriptivo
        JLabel textLabel = createGastosLabel();
        JLabel totalLabel = getTotalGastos();
        panel.add(textLabel, "align center");
        panel.add(totalLabel, "align center");
        // Para que crezca más verticalmente:
        panel.setPreferredSize(new Dimension(250, 300)); // menos ancho, más alto
        return panel;
    }

    private JPanel createLoanCard() {
        JPanel panel = new JPanel(new MigLayout(
                "wrap, insets 30, aligny top",    // alineado arriba y con márgenes
                "[center]",                       // centrar horizontalmente
                "[]20[]20[]"                      // espacio vertical entre componentes
        ));

        // Estilo visual
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)"
        );

        // Ícono SVG más grande
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new FlatSVGIcon("app/icon/svg/bank-money-icon.svg", 48, 48));
        panel.add(iconLabel, "align center");

        // Separador ancho
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(200, 2));
        panel.add(separator, "growx, gapy 10 10");

        // Texto descriptivo
        JLabel textLabel = createLoansLabel();
        JLabel totalLabel = createTotalLoansLabel();
        panel.add(textLabel, "align center");
        panel.add(totalLabel, "align center");
        // Para que crezca más verticalmente:
        panel.setPreferredSize(new Dimension(250, 300)); // menos ancho, más alto
        return panel;
    }

    private JPanel createCardsContainer() {
        JPanel container = new JPanel(new MigLayout(
                "insets 20, fillx",       // Márgenes y que se estire horizontal
                "[grow]20[grow]20[grow]20[grow]",   // 4 columnas que crecen con espacio uniforme
                "[]"
        ));
        container.add(createClientCard(), "growx");
        container.add(createLoanCard(), "growx");
        container.add(createIncomesCard(), "growx");
        container.add(createGastosCard(), "growx");

        return container;
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

    private JLabel createClientsLabel(){
        JLabel lb = new JLabel("Clientes");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        clientsLabel = lb;
        return lb;
    }

    private JLabel createTotalLabel(){
        JLabel lb = new JLabel(String.valueOf(clientController.getAllClients().size()));
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        totalClients = lb;
        return lb;
    }

    private JLabel createLoansLabel(){
        JLabel lb = new JLabel("Prestamos");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        loansLabel = lb;
        return lb;
    }

    private JLabel createTotalLoansLabel(){
        JLabel lb = new JLabel(String.valueOf(loanController.getAllLoans().size()));
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        totalLoansLabel = lb;
        return lb;
    }

    private JLabel getTotalIncomes(){
        List<Income> incomes = incomeController.getIncomes();
        double total = 0;
        for( Income income : incomes){
            total += income.getIncomeAmount();
        }
        JLabel lb = new JLabel(String.valueOf(total));
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        totalIncomesLabel = lb;
        return lb;
    }

    private JLabel getTotalGastos(){
        List<Gasto> gastos = gastoController.getGastos();
        double total = 0;
        for( Gasto gasto : gastos){
            total += gasto.getValorGasto();
        }
        JLabel lb = new JLabel(String.valueOf(total));
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        totalGastosLabel = lb;
        return lb;
    }

    private JLabel createGastosLabel(){
        JLabel lb = new JLabel("Gastos");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        gastosLabel = lb;
        return lb;
    }

    private JLabel createIncomeslabel(){
        JLabel lb = new JLabel("Ingresos");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        incomesLabel = lb;
        return lb;
    }


    private JLabel helloLabel;
    private JLabel summaryLabel;
    private JLabel clientsLabel;
    private JLabel totalClients;
    private JLabel loansLabel;
    private JLabel totalLoansLabel;
    private JLabel totalIncomesLabel;
    private JLabel incomesLabel;
    private JLabel totalGastosLabel;
    private JLabel gastosLabel;

}
