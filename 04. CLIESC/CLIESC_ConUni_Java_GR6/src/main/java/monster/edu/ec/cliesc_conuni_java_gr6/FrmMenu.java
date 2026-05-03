package monster.edu.ec.cliesc_conuni_java_gr6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FrmMenu extends JFrame {

    private JButton btnTemperatura;
    private JButton btnLongitud;
    private JButton btnSalir;

    public FrmMenu() {
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Monster | Menu principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1040, 620));
        setResizable(false);
        setLocationRelativeTo(null);
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

        JLabel lblMarca = new JLabel("MONSTER", SwingConstants.CENTER);
        lblMarca.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblMarca.setForeground(new Color(96, 165, 250));
        hg.gridy = 0;
        header.add(lblMarca, hg);

        JLabel lblTitulo = new JLabel("Seleccione una categoria de conversion", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        hg.gridy = 1;
        header.add(lblTitulo, hg);

        JLabel lblDescripcion = new JLabel("Temperatura y longitud con acceso directo a las operaciones SOAP.", SwingConstants.CENTER);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDescripcion.setForeground(new Color(191, 219, 254));
        hg.gridy = 2;
        header.add(lblDescripcion, hg);

        JPanel cards = new JPanel(new java.awt.GridLayout(1, 2, 20, 20));
        cards.setOpaque(false);

        cards.add(crearTarjeta(
                "Temperatura",
                "Celsius, Fahrenheit y Kelvin",
                new Color(37, 99, 235),
                e -> abrirConversor(FrmConversor.Categoria.TEMPERATURA)));

        cards.add(crearTarjeta(
                "Longitud",
                "Kilometros, metros, pulgadas, pies y millas",
                new Color(14, 165, 233),
                e -> abrirConversor(FrmConversor.Categoria.LONGITUD)));

        JPanel footer = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));

        btnSalir = new JButton("Cerrar sesion");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalir.setBackground(new Color(248, 113, 113));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnSalir.addActionListener(e -> cerrarSesion());
        footer.add(btnSalir);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(cards, BorderLayout.CENTER);
        center.add(footer, BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String titulo, String detalle, Color accent, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(15, 23, 42));

        JLabel lblDetalle = new JLabel("<html><div style='width: 290px;'>" + detalle + "</div></html>");
        lblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDetalle.setForeground(new Color(71, 85, 105));

        JPanel barra = new JPanel();
        barra.setBackground(accent);
        barra.setPreferredSize(new Dimension(0, 10));

        JPanel contenido = new JPanel(new BorderLayout(0, 14));
        contenido.setOpaque(false);
        contenido.add(lblTitulo, BorderLayout.NORTH);
        contenido.add(lblDetalle, BorderLayout.CENTER);

        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAbrir.setBackground(accent);
        btnAbrir.setForeground(Color.WHITE);
        btnAbrir.setFocusPainted(false);
        btnAbrir.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));
        btnAbrir.addActionListener(action);

        JPanel pie = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        pie.setOpaque(false);
        pie.add(btnAbrir);

        card.add(barra, BorderLayout.NORTH);
        card.add(contenido, BorderLayout.CENTER);
        card.add(pie, BorderLayout.SOUTH);
        return card;
    }

    private void abrirConversor(FrmConversor.Categoria categoria) {
        FrmConversor conversor = new FrmConversor(categoria);
        conversor.setVisible(true);
        conversor.setLocationRelativeTo(null);
        dispose();
    }

    private void cerrarSesion() {
        FrmLogin login = new FrmLogin();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FrmMenu().setVisible(true));
    }
}
