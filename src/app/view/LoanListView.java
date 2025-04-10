package app.view;

import app.controller.LoanController;
import app.model.Loan;
import app.view.forms.EditLoanForm;
import app.view.forms.NewLoanForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class LoanListView extends JPanel {

    private LoanController loanController;
    private DefaultTableModel model;
    private JTable table;

    public LoanListView() {
        this.loanController = new LoanController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[grow]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[grow]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Gestión de Préstamos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +18");

        // Componentes principales
        JPanel searchPanel = createSearchBar();
        JPanel statsPanel = createStatsPanel();
        JScrollPane scrollPane = createTablePanel();
        JPanel buttonPanel = createButtonPanel();

        // Agregar componentes al panel principal
        panel.add(lbTitle, "span, growx, wrap");
        panel.add(searchPanel, "growx, wrap");
        panel.add(statsPanel, "growx, wrap");
        panel.add(scrollPane, "grow, push, wrap");
        panel.add(buttonPanel, "growx");

        add(panel, "grow");
    }

    // Método para crear la barra de búsqueda
    private JPanel createSearchBar() {
        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE,
                "showClearButton:true;" +
                        "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar préstamos...");

        // Botón de búsqueda
        JButton searchButton = createActionButton("", "app/icon/svg/search-icon.svg");
        searchButton.setPreferredSize(new Dimension(60, 30));
        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            this.filterTable(query);
        });

        // Botón de reset
        JButton resetButton = createActionButton("", "app/icon/svg/reset-icon.svg");
        resetButton.setPreferredSize(new Dimension(60, 30));
        resetButton.addActionListener(e -> {
            searchField.setText("");
            this.resetTable();
        });

        // Panel principal para la barra de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(resetButton, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:10");

        return searchPanel;
    }

    // Método para filtrar la tabla
    private void filterTable(String query) {
        List<Loan> loans = this.loanController.searchLoansByQuery(query);
        model.setRowCount(0); // Limpiar las filas actuales del modelo
        boolean isEmpty = true;

        if (loans != null && !loans.isEmpty()) {
            for (Loan loan : loans) {
                model.addRow(new Object[]{
                        loan.getId(),
                        loan.getClient().getFirstName(),
                        loan.getAmount(),
                        loan.getInterestRate(),
                        loan.getTerm(),
                        loan.isActive() ? "Activo" : "Inactivo",
                        loan.getDate()
                });
            }
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Préstamos encontrados.");
            isEmpty = false;
        }

        if (isEmpty) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "No se encontraron préstamos similares a tu búsqueda.");
        }
    }

    // Método para resetear la tabla
    private void resetTable() {
        List<Loan> loans = this.loanController.getAllLoans();
        model.setRowCount(0); // Limpiar las filas actuales del modelo

        if (loans != null && !loans.isEmpty()) {
            for (Loan loan : loans) {
                model.addRow(new Object[]{
                        loan.getId(),
                        loan.getClient().getFirstName(),
                        loan.getAmount(),
                        loan.getInterestRate(),
                        loan.getTerm(),
                        loan.isActive() ? "Activo" : "Inactivo",
                        loan.getDate()
                });
            }
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Tabla reseteada con éxito.");
        }
    }

    // Método para actualizar la tabla
    public void refreshTable() {
        List<Loan> loans = this.loanController.getAllLoans();
        model.setRowCount(0); // Limpiar las filas actuales del modelo

        if (loans != null && !loans.isEmpty()) {
            for (Loan loan : loans) {
                model.addRow(new Object[]{
                        loan.getId(),
                        loan.getClient().getFirstName() + " " + loan.getClient().getFirstSurname(),
                        loan.getAmount(),
                        loan.getInterestRate(),
                        loan.getTerm(),
                        loan.isActive() ? "Activo" : "Inactivo",
                        loan.getDate()
                });
            }
        }
    }

    // Método para crear las tarjetas de resumen
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statsPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:10");

        // Icono para la sección de préstamos totales
        FlatSVGIcon totalLoansIcon = new FlatSVGIcon("app/icon/svg/bank-money-icon.svg").derive(50, 50);
        List<Loan> allLoans = this.loanController.getAllLoans(); // Obtener la lista de préstamos
        int totalLoansCount = (allLoans == null || allLoans.isEmpty()) ? 0 : allLoans.size();
        JPanel totalLoans = createStatCard("Total de Préstamos", totalLoansIcon, String.valueOf("  " + totalLoansCount));

        // Icono para la sección de préstamos activos
        FlatSVGIcon activeLoansIcon = new FlatSVGIcon("app/icon/svg/money-icon.svg").derive(50, 50);
        int activeLoansCount = this.loanController.getActiveLoansCount() == 0 ? 0 : this.loanController.getActiveLoansCount();
        JPanel activeLoans = createStatCard("Préstamos Activos", activeLoansIcon, String.valueOf("  " + activeLoansCount));

        statsPanel.add(totalLoans);
        statsPanel.add(activeLoans);

        return statsPanel;
    }

    // Método para crear una tarjeta de estadísticas
    private JPanel createStatCard(String title, Icon icon, String value) {
        JPanel card = new JPanel(new MigLayout("wrap, fillx, insets 10", "[grow]"));
        card.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:10");

        // Título
        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +14");

        // Valor con ícono
        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setOpaque(false);
        JLabel iconLabel = new JLabel(icon);
        JLabel valueLabel = new JLabel(value);
        valueLabel.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +18");

        valuePanel.add(iconLabel, BorderLayout.WEST);
        valuePanel.add(valueLabel, BorderLayout.CENTER);

        card.add(titleLabel, "growx");
        card.add(valuePanel, "growx");

        return card;
    }

    // Método para crear la tabla
    private JScrollPane createTablePanel() {
        List<Loan> loans = this.loanController.getAllLoans();
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Cliente");
        model.addColumn("Monto");
        model.addColumn("Tasa de interes");
        model.addColumn("Plazo");
        model.addColumn("Estado");
        model.addColumn("Fecha");

        if (loans != null && !loans.isEmpty()) {
            for (Loan loan : loans) {
                model.addRow(new Object[]{
                        loan.getId(),
                        loan.getClient().getFirstName() + " " + loan.getClient().getFirstSurname(),
                        loan.getAmount(),
                        loan.getInterestRate(),
                        loan.getTerm(),
                        loan.isActive() ? "Activo" : "Inactivo",
                        loan.getDate()
                });
            }
        }

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        // Estilo de la tabla
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        // Header con estilo minimalista
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        return scrollPane;
    }

    // Método para crear el panel de botones
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:10");

        JButton newButton = createActionButton("Nuevo Préstamo", "app/icon/svg/create-icon.svg");
        JButton editButton = createActionButton("Editar Préstamo", "app/icon/svg/update-icon.svg");
        JButton deleteButton = createActionButton("Eliminar Préstamo", "app/icon/svg/delete-icon.svg");

        // Asignar acciones a los botones
        setupNewButtonAction(newButton);
        setupEditButtonAction(editButton);
        setupDeleteButtonAction(deleteButton);

        buttonPanel.add(newButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    // Método para configurar la acción del botón "Nuevo"
    private void setupNewButtonAction(JButton button) {
        button.addActionListener(e -> {
            JFrame frame = new JFrame("Nuevo Préstamo");
            frame.setContentPane(new NewLoanForm(this));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            //frame.isAlwaysOnTop(true);
        });
    }

    // Método para configurar la acción del botón "Editar"
    private void setupEditButtonAction(JButton button) {
        button.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) table.getValueAt(selectedRow, 0);
                System.out.println(id);
                Loan loan = this.loanController.getLoanById(id);
                JFrame frame = new JFrame("Editar Préstamo");
                frame.setContentPane(new EditLoanForm(
                        loan.getId(),
                        loan.getClient(),
                        loan.getAmount(),
                        loan.getInterestRate(),
                        loan.getTerm(),
                        loan.isActive() ? "Activo" : "Inactivo",
                        loan.getDate().toString(),
                        this
                ));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                Notifications.getInstance().show(Notifications.Type.INFO, "Seleccione un préstamo para editar.");
            }
        });
    }

    // Método para configurar la acción del botón "Eliminar"
    private void setupDeleteButtonAction(JButton button) {
        button.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este préstamo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    this.loanController.deleteLoan(id);
                    this.refreshTable();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "El préstamo ha sido eliminado con éxito.");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.INFO, "Seleccione un préstamo para eliminar.");
            }
        });
    }

    // Método para crear un botón con ícono
    private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text, new FlatSVGIcon(iconPath).derive(20, 20));
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "foreground:@foreground;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "arc:10");
        button.setPreferredSize(new Dimension(180, 40));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        return button;
    }
}