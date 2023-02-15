package filmbook.gui;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

import filmbook.*;


import static javax.swing.JOptionPane.showMessageDialog;

public class LoginWindow extends Window {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField txtLogin;
    private JTextField txtClose;
    private JLabel lblNewLabel_1;
    private JLabel label;

    final int WIDTH = 600;
    final int HEIGHT = 400;

    public LoginWindow(Api api){
        super("Login Screen");

        //symperifora tou para8yrou
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(400, 200, 629, 309);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(70, 130, 180));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        usernameField.setForeground(new Color(0, 0, 0));
        usernameField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        usernameField.setBounds(184, 108, 282, 38);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordField.setForeground(new Color(0, 0, 0));
        passwordField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        passwordField.setBounds(184, 166, 282, 38);
        contentPane.add(passwordField);

        JLabel validation = new JLabel("");
        validation.setForeground(Color.WHITE);
        validation.setBounds(184, 205, 282, 14);
        contentPane.add(validation);

        txtLogin = new JTextField();
        txtLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    if(api.userLogin(usernameField.getText(), new String(passwordField.getPassword())) || true ){
                        //showMessageDialog(null, "Login Sucessfull " + usernameField.getText());
                        dispose();
                        MainWindow mwFrame = new MainWindow(api);
                        mwFrame.setVisible(true);
                        mwFrame.setLocationRelativeTo(null);
                    } else {
                        //showMessageDialog(null, "Dystyxws... den einai swsta auta pou edwses");
                        validation.setText("Invalid Username or Password");
                    }
                } catch (Exception ex){
                    showMessageDialog(null, ex.getMessage());
                }
            }
        });
        txtLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
        txtLogin.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
        txtLogin.setForeground(new Color(255, 255, 255));
        txtLogin.setText("LOGIN");
        txtLogin.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        txtLogin.setOpaque(false);
        txtLogin.setEditable(false);
        txtLogin.setBounds(184, 241, 102, 38);
        contentPane.add(txtLogin);
        txtLogin.setColumns(10);

        txtClose = new JTextField();
        txtClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        txtClose.setHorizontalAlignment(SwingConstants.CENTER);
        txtClose.setForeground(new Color(255, 255, 255));
        txtClose.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
        txtClose.setText("CLOSE");
        txtClose.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        txtClose.setOpaque(false);
        txtClose.setEditable(false);
        txtClose.setColumns(10);
        txtClose.setBounds(364, 241, 102, 38);
        contentPane.add(txtClose);

        JLabel lblNewLabel = new JLabel("Filmbook");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        lblNewLabel.setBounds(210, 40, 210, 38);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel(new ImageIcon("icons8-name-40.png"));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        //lblNewLabel_1.setIcon(new ImageIcon(LoginWindow.class.getResource("icons8-name-40.png")));
        lblNewLabel_1.setBounds(134, 108, 40, 40);
        contentPane.add(lblNewLabel_1);

        label = new JLabel(new ImageIcon("icons8-secure-40.png"));
        //label.setIcon(new ImageIcon(LoginWindow.class.getResource("icons8-secure-40.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(134, 166, 40, 38);
        contentPane.add(label);
        setUndecorated(true);
        setLocationRelativeTo ( null );
    }

}
