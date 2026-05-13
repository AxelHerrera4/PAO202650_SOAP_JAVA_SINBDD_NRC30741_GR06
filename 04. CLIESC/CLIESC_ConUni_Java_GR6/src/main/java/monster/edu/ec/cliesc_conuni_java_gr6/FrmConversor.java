package monster.edu.ec.cliesc_conuni_java_gr6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import monster.edu.ec.cliente.Conversor;
import monster.edu.ec.cliente.ConversorWS;
import monster.edu.ec.cliente.ConversorWS_Service;

public class FrmConversor extends JFrame {

    public enum Categoria {
        TEMPERATURA,
        LONGITUD,
        MASA
    }

    private static class OperacionItem {
        private final String codigo;
        private final String etiqueta;
        private final String descripcion;

        private OperacionItem(String codigo, String etiqueta, String descripcion) {
            this.codigo = codigo;
            this.etiqueta = etiqueta;
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

    private final ConversorWS port;
    private Categoria categoriaActual;

    private JLabel lblTitulo;
    private JLabel lblDescripcion;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<OperacionItem> cbOperacion;
    private JTextField txtValor;
    private JLabel lblResultado;
    private JLabel lblUnidadOrigen;
    private JLabel lblUnidadDestino;
    private JButton btnCalcular;
    private JButton btnLimpiar;
    private JButton btnVolver;

    public FrmConversor() {
        this(Categoria.TEMPERATURA);
    }

    public FrmConversor(Categoria categoriaInicial) {
        this.port = new ConversorWS_Service().getConversorWSPort();
        this.categoriaActual = categoriaInicial;
        initComponents();
        configurarVentana();
        aplicarCategoria(categoriaInicial);
    }

    private void configurarVentana() {
        setTitle("Monster | Conversor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1100, 680));
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnCalcular);
        pack();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(15, 23, 42));
        root.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setContentPane(root);

        JPanel header = new JPanel(new GridBagLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 24, 10));

        GridBagConstraints hg = new GridBagConstraints();
        hg.gridx = 0;
        hg.fill = GridBagConstraints.HORIZONTAL;
        hg.insets = new Insets(4, 4, 4, 4);
        hg.weightx = 1;

        lblTitulo = new JLabel("CONVERSOR MONSTER", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblTitulo.setForeground(new Color(96, 165, 250));
        hg.gridy = 0;
        header.add(lblTitulo, hg);

        lblDescripcion = new JLabel("Elige categoria, operacion y valor para convertir con el servicio SOAP.", SwingConstants.CENTER);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDescripcion.setForeground(new Color(191, 219, 254));
        hg.gridy = 1;
        header.add(lblDescripcion, hg);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(28, 30, 28, 30)));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8, 0, 8, 0);
        gc.weightx = 1;

        JLabel lblCategoria = new JLabel("Categoria");
        lblCategoria.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCategoria.setForeground(new Color(51, 65, 85));
        gc.gridy = 0;
        card.add(lblCategoria, gc);

        cbCategoria = new JComboBox<>(Categoria.values());
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cbCategoria.addActionListener(e -> aplicarCategoria((Categoria) cbCategoria.getSelectedItem()));
        gc.gridy = 1;
        card.add(cbCategoria, gc);

        JLabel lblOperacion = new JLabel("Operacion");
        lblOperacion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblOperacion.setForeground(new Color(51, 65, 85));
        gc.gridy = 2;
        card.add(lblOperacion, gc);

        cbOperacion = new JComboBox<>();
        cbOperacion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cbOperacion.addActionListener(e -> actualizarDescripcionOperacion());
        gc.gridy = 3;
        card.add(cbOperacion, gc);

        JLabel lblValor = new JLabel("Valor a convertir");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblValor.setForeground(new Color(51, 65, 85));
        gc.gridy = 4;
        card.add(lblValor, gc);

        txtValor = new JTextField();
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtValor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        gc.gridy = 5;
        card.add(txtValor, gc);

        JLabel lblResultadoTitulo = new JLabel("Resultado");
        lblResultadoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblResultadoTitulo.setForeground(new Color(51, 65, 85));
        gc.gridy = 6;
        card.add(lblResultadoTitulo, gc);

        JPanel resultadoPanel = new JPanel(new BorderLayout());
        resultadoPanel.setBackground(new Color(241, 245, 249));
        resultadoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));

        lblResultado = new JLabel("Esperando conversion...");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblResultado.setForeground(new Color(15, 23, 42));
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);

        lblUnidadOrigen = new JLabel("", SwingConstants.CENTER);
        lblUnidadOrigen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUnidadOrigen.setForeground(new Color(71, 85, 105));

        lblUnidadDestino = new JLabel("", SwingConstants.CENTER);
        lblUnidadDestino.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUnidadDestino.setForeground(new Color(71, 85, 105));

        JPanel resultadoTexto = new JPanel(new GridBagLayout());
        resultadoTexto.setOpaque(false);
        GridBagConstraints rg = new GridBagConstraints();
        rg.gridx = 0;
        rg.fill = GridBagConstraints.HORIZONTAL;
        rg.insets = new Insets(2, 2, 2, 2);
        rg.weightx = 1;
        rg.gridy = 0;
        resultadoTexto.add(lblResultado, rg);
        rg.gridy = 1;
        resultadoTexto.add(lblUnidadOrigen, rg);
        rg.gridy = 2;
        resultadoTexto.add(lblUnidadDestino, rg);

        resultadoPanel.add(resultadoTexto, BorderLayout.CENTER);
        gc.gridy = 7;
        card.add(resultadoPanel, gc);

        JPanel buttons = new JPanel(new java.awt.GridLayout(1, 3, 12, 0));
        buttons.setOpaque(false);

        btnCalcular = new JButton("Convertir");
        btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCalcular.setBackground(new Color(37, 99, 235));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btnCalcular.addActionListener(this::btnCalcularActionPerformed);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(14, 165, 233));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setBackground(new Color(100, 116, 139));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        buttons.add(btnCalcular);
        buttons.add(btnLimpiar);
        buttons.add(btnVolver);

        gc.gridy = 8;
        card.add(buttons, gc);

        root.add(header, BorderLayout.NORTH);
        root.add(card, BorderLayout.CENTER);
    }

    private void aplicarCategoria(Categoria categoria) {
        if (categoria == null) {
            categoria = Categoria.TEMPERATURA;
        }

        categoriaActual = categoria;
        if (cbCategoria.getSelectedItem() != categoria) {
            cbCategoria.setSelectedItem(categoria);
        }

        if (categoria == Categoria.TEMPERATURA) {
            lblTitulo.setText("CONVERSOR MONSTER | TEMPERATURA");
            lblDescripcion.setText("Convierte entre Celsius, Fahrenheit y Kelvin.");
            cbOperacion.setModel(new DefaultComboBoxModel<>(new OperacionItem[] {
                new OperacionItem("CtoF", "Celsius a Fahrenheit", "Ingresa grados Celsius"),
                new OperacionItem("FtoC", "Fahrenheit a Celsius", "Ingresa grados Fahrenheit"),
                new OperacionItem("CtoK", "Celsius a Kelvin", "Ingresa grados Celsius"),
                new OperacionItem("KtoC", "Kelvin a Celsius", "Ingresa grados Kelvin"),
                new OperacionItem("FtoK", "Fahrenheit a Kelvin", "Ingresa grados Fahrenheit"),
                new OperacionItem("KtoF", "Kelvin a Fahrenheit", "Ingresa grados Kelvin")
            }));
        } else if (categoria == Categoria.LONGITUD) {
            lblTitulo.setText("CONVERSOR MONSTER | LONGITUD");
            lblDescripcion.setText("Convierte entre kilometros, metros, centimetros, pulgadas, pies y millas.");
            cbOperacion.setModel(new DefaultComboBoxModel<>(new OperacionItem[] {
                new OperacionItem("KmAMetros", "Kilometros a Metros", "Ingresa kilometros"),
                new OperacionItem("MetrosACm", "Metros a Centimetros", "Ingresa metros"),
                new OperacionItem("PulgadasACm", "Pulgadas a Centimetros", "Ingresa pulgadas"),
                new OperacionItem("PiesAMetros", "Pies a Metros", "Ingresa pies"),
                new OperacionItem("MillasAKm", "Millas a Kilometros", "Ingresa millas"),
            }));
        } else {
            lblTitulo.setText("CONVERSOR MONSTER | MASA");
            lblDescripcion.setText("Convierte entre kilogramos, gramos, libras, onzas y toneladas.");
            cbOperacion.setModel(new DefaultComboBoxModel<>(new OperacionItem[] {
                new OperacionItem("KgAGramos", "Kilogramos a Gramos", "Ingresa kilogramos"),
                new OperacionItem("GramosAMg", "Gramos a Miligramos", "Ingresa gramos"),
                new OperacionItem("LibrasAKg", "Libras a Kilogramos", "Ingresa libras"),
                new OperacionItem("OnzasAGramos", "Onzas a Gramos", "Ingresa onzas"),
                new OperacionItem("ToneladasAKg", "Toneladas a Kilogramos", "Ingresa toneladas"),
            }));
        }

        actualizarDescripcionOperacion();
    }

    private void actualizarDescripcionOperacion() {
        OperacionItem item = (OperacionItem) cbOperacion.getSelectedItem();
        if (item != null) {
            lblDescripcion.setText(item.descripcion);
        }
    }

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            OperacionItem operacion = (OperacionItem) cbOperacion.getSelectedItem();
            if (operacion == null) {
                return;
            }

            double valor = Double.parseDouble(txtValor.getText().trim());
            Conversor res = ejecutarOperacion(operacion.codigo, valor);

            if (res != null) {
                lblResultado.setText(String.format("%.6f", res.getValorDestino()));
                lblUnidadOrigen.setText("Origen: " + res.getValorOrigen() + " " + res.getUnidadOrigen());
                lblUnidadDestino.setText("Destino: " + res.getUnidadDestino());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa un numero valido.", "Dato invalido", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo consumir el servicio SOAP. Verifica que el servidor este corriendo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Conversor ejecutarOperacion(String codigo, double valor) {
        return switch (codigo) {
            case "CtoF" -> ConversorClient.convertirCtoF(valor);
            case "FtoC" -> ConversorClient.convertirFtoC(valor);
            case "CtoK" -> ConversorClient.convertirCtoK(valor);
            case "KtoC" -> ConversorClient.convertirKtoC(valor);
            case "FtoK" -> ConversorClient.convertirFtoK(valor);
            case "KtoF" -> ConversorClient.convertirKtoF(valor);
            case "KmAMetros" -> ConversorClient.convertirKmAMetros(valor);
            case "MetrosACm" -> ConversorClient.convertirMetrosACm(valor);
            case "PulgadasACm" -> ConversorClient.convertirPulgadasACm(valor);
            case "PiesAMetros" -> ConversorClient.convertirPiesAMetros(valor);
            case "MillasAKm" -> ConversorClient.convertirMillasAKm(valor);
            case "KgAGramos" -> ConversorClient.convertirKgAGramos(valor);
            case "GramosAMg" -> ConversorClient.convertirGramosAMg(valor);
            case "LibrasAKg" -> ConversorClient.convertirLibrasAKg(valor);
            case "OnzasAGramos" -> ConversorClient.convertirOnzasAGramos(valor);
            case "ToneladasAKg" -> ConversorClient.convertirToneladasAKg(valor);
            default -> null;
        };
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        txtValor.setText("");
        lblResultado.setText("Esperando conversion...");
        lblUnidadOrigen.setText("");
        lblUnidadDestino.setText("");
        txtValor.requestFocus();
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        FrmMenu menu = new FrmMenu();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FrmConversor().setVisible(true));
    }
}

