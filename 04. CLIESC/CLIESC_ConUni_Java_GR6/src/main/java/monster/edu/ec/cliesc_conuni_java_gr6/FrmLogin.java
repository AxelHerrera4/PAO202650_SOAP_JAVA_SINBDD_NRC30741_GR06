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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmLogin extends JFrame {

    private static final String USUARIO = "monster";
    private static final String PASSWORD = "monster9";

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnSalir;

    public FrmLogin() {
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Monster | Acceso al sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(980, 580));
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnIngresar);
        pack();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(15, 23, 42));
        root.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setContentPane(root);

        JPanel heroPanel = new JPanel(new GridBagLayout());
        heroPanel.setBackground(new Color(15, 23, 42));
        heroPanel.setPreferredSize(new Dimension(430, 0));

        GridBagConstraints heroGc = new GridBagConstraints();
        heroGc.gridx = 0;
        heroGc.insets = new Insets(8, 8, 8, 8);
        heroGc.fill = GridBagConstraints.HORIZONTAL;
        heroGc.weightx = 1;

        JLabel lblMarca = new JLabel("MONSTER", SwingConstants.LEFT);
        lblMarca.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblMarca.setForeground(new Color(96, 165, 250));
        heroGc.gridy = 0;
        heroPanel.add(lblMarca, heroGc);

        JLabel lblTitulo = new JLabel("Sistema de Conversion SOAP", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        heroGc.gridy = 1;
        heroPanel.add(lblTitulo, heroGc);

        JLabel lblDescripcion = new JLabel("Temperatura y longitud en una interfaz limpia, rapida y ordenada.");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDescripcion.setForeground(new Color(191, 219, 254));
        heroGc.gridy = 2;
        heroPanel.add(lblDescripcion, heroGc);

        JLabel lblDetalle = new JLabel("Acceso protegido para usuarios autorizados.");
        lblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDetalle.setForeground(new Color(148, 163, 184));
        heroGc.gridy = 3;
        heroPanel.add(lblDetalle, heroGc);

        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(new Color(248, 250, 252));
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(28, 30, 28, 30)));
        loginCard.setPreferredSize(new Dimension(410, 0));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8, 0, 8, 0);
        gc.weightx = 1;

        JLabel lblAcceso = new JLabel("Iniciar sesion");
        lblAcceso.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAcceso.setForeground(new Color(15, 23, 42));
        gc.gridy = 0;
        loginCard.add(lblAcceso, gc);

        JLabel lblSub = new JLabel("Ingresa tus credenciales para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(71, 85, 105));
        gc.gridy = 1;
        loginCard.add(lblSub, gc);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsuario.setForeground(new Color(51, 65, 85));
        gc.gridy = 2;
        loginCard.add(lblUsuario, gc);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        gc.gridy = 3;
        loginCard.add(txtUsuario, gc);

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(new Color(51, 65, 85));
        gc.gridy = 4;
        loginCard.add(lblPassword, gc);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        gc.gridy = 5;
        loginCard.add(txtPassword, gc);

        JPanel buttonsPanel = new JPanel(new java.awt.GridLayout(1, 2, 12, 0));
        buttonsPanel.setOpaque(false);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(37, 99, 235));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btnIngresar.addActionListener(this::btnIngresarActionPerformed);

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setBackground(new Color(248, 113, 113));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        buttonsPanel.add(btnIngresar);
        buttonsPanel.add(btnSalir);
        gc.gridy = 6;
        loginCard.add(buttonsPanel, gc);

        JPanel content = new JPanel(new java.awt.GridLayout(1, 2, 20, 0));
        content.setOpaque(false);
        content.add(heroPanel);
        content.add(loginCard);

        root.add(content, BorderLayout.CENTER);
    }

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        if (USUARIO.equals(user) && PASSWORD.equals(pass)) {
            JOptionPane.showMessageDialog(this, "Acceso concedido. Bienvenido.", "Monster", JOptionPane.INFORMATION_MESSAGE);
            FrmMenu menu = new FrmMenu();
            menu.setVisible(true);
            menu.setLocationRelativeTo(null);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Intenta de nuevo.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
        txtPassword.setText("");
        txtUsuario.requestFocus();
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FrmLogin().setVisible(true));
    }
}
