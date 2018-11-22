package railway.adminAccount;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;

import railway.adminAccount.AdminAccount;

import static javax.swing.BorderFactory.*;

public class MainAppInterface extends JFrame {
    JPanel MainPanel         = new JPanel();
    JLabel RWBanner          = new JLabel("  Railway RS");
    JLabel LoginLabel        = new JLabel("      Login");
    static JLabel Error      = new JLabel("     Error");
    static JLabel ErrorLabel = new JLabel("      Please Correct Your Information");

    //Login Components
    JLabel UserSSN               = new JLabel("User SSN");
    JLabel UserPass              = new JLabel("Password");
    JTextField userSSNField      = new JTextField();
    JPasswordField userPassField = new JPasswordField();
    JButton ButtonLogin          = new JButton("Sign In");
    JButton ButtonRegister       = new JButton("Create Account");

    MainAppInterface() {
        setTitle("Admin Account Login Panel");
        setLayout(null);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        MainPanel.setBounds(0, 0, 450, 600);
        MainPanel.setLayout(null);
        MainPanel.setBackground(new Color(34, 35, 41));

        Error.setOpaque(true);
        ErrorLabel.setOpaque(true);
        ErrorLabel.setVisible(false);
        Error.setVisible(false);

        setComponentBounds();
        setComponentBackground();
        setComponentForeground();
        setComponentBorder();
        setComponentFont();
        removeButtonFocus();
        actionListeners();
        addComponentToFrame();
    }
    
    public void makeSessionFile(String LoggedUserSSN, String LoggedUserPass, String LoggedUserFullName){
        File RRSDir = new File("C:/programData/RRS/");
        RRSDir.mkdirs();

        DataOutputStream WriteFile;
        try {
            WriteFile = new DataOutputStream(new FileOutputStream("C:/programData/RRS/AdminSessionData.bin"));
            String TextToBeWritten = "\n" + LoggedUserSSN + "\n" + LoggedUserPass + "\n" + LoggedUserFullName;
            WriteFile.writeUTF(TextToBeWritten);
            WriteFile.close(); 
        } catch(IOException EXC){}
    }
    
    public void actionListeners() {
        ButtonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Error.setVisible(false);
                ErrorLabel.setVisible(false);
                    if (!userSSNField.getText().equals("") && !userPassField.getText().equals("")) {
                        String query = "SELECT * FROM adminaccount WHERE adminUserSSN='" + userSSNField.getText() + "' AND adminUserPass='" + userPassField.getText() + "'";
                        int isAvailable = 0;
                        try {
                            Connection conn = DBConnect.getConnection();
                            if (conn == null) {
                                Error.setVisible(true);
                                ErrorLabel.setText("   Unable To Connect To The Server");
                                ErrorLabel.setVisible(true);
                            } else {
                                Statement stmt = conn.createStatement();
                                ResultSet RS;
                                RS = stmt.executeQuery(query);
                                String FullName = "";
                                String LoggedUserSSN = "";
                                while (RS.next()) {
                                    isAvailable = 1;
                                    FullName = RS.getString("adminFullName");
                                }

                                if (isAvailable == 1) {
                                    LoggedUserSSN = userSSNField.getText();
                                    makeSessionFile(LoggedUserSSN, userPassField.getText(), FullName);
                                    new AdminAccount().setVisible(true);
                                    setVisible(false);
                                } else {
                                    Error.setVisible(true);
                                    ErrorLabel.setText("   SSN or Password Does Not Matched");
                                    ErrorLabel.setVisible(true);
                                }
                            }
                        } catch (SQLException ex) {
                            Error.setVisible(true);
                            ErrorLabel.setText("     Error Connecting To The Database");
                            ErrorLabel.setVisible(true);
                        }
                    } else {
                        Error.setVisible(true);
                        ErrorLabel.setText(" Please Fill all Information Correctly");
                        ErrorLabel.setVisible(true);
                    }
                }
        });
    }

    private void addComponentToFrame() {
        MainPanel.add(Error);
        MainPanel.add(ErrorLabel);
        MainPanel.add(userSSNField);
        MainPanel.add(userPassField);
        MainPanel.add(ButtonLogin);
        MainPanel.add(RWBanner);
        MainPanel.add(UserSSN);
        MainPanel.add(UserPass);
        MainPanel.add(LoginLabel);
        add(MainPanel);
    }

    private void setComponentBounds() {
        Error.setBounds(0, 0, 100, 40);
        ErrorLabel.setBounds(100, 0, 350, 40);
        RWBanner.setBounds(5, 80, 390, 50);
        LoginLabel.setBounds(100, 140, 390, 50);
        UserSSN.setBounds(50, 220, 330, 20);
        UserPass.setBounds(50, 306, 330, 20);
        userSSNField.setBounds(50, 239, 330, 30);
        userPassField.setBounds(50, 325, 330, 30);
        ButtonLogin.setBounds(130, 420, 170, 35);
    }

    private void setComponentBackground() {
        ErrorLabel.setBackground(new Color(240, 81, 82));
        Error.setBackground(new Color(221, 68, 69));
        UserSSN.setBackground(new Color(200, 200, 70));
        UserPass.setBackground(new Color(200, 200, 70));
        userSSNField.setBackground(new Color(34, 35, 41));
        userPassField.setBackground(new Color(34, 35, 41));
        ButtonLogin.setBackground(new Color(89, 179, 105));
        RWBanner.setBackground(new Color(102, 0, 102, 0));
    }

    private void setComponentForeground() {
        Error.setForeground(Color.WHITE);
        ErrorLabel.setForeground(Color.WHITE);
        UserSSN.setForeground(new Color(240, 81, 82));
        UserPass.setForeground(new Color(240, 81, 82));
        userSSNField.setForeground(Color.WHITE);
        userPassField.setForeground(Color.WHITE);
        ButtonLogin.setForeground(new Color(255, 255, 255));
        RWBanner.setForeground(Color.CYAN);
        LoginLabel.setForeground(Color.WHITE);
    }

    private void setComponentFont() {
        Error.setFont(new Font("Nova Square", 4, 18));
        ErrorLabel.setFont(new Font("Nova Square", 4, 18));
        LoginLabel.setFont(new Font("Byom", 3, 30));
        RWBanner.setFont(new Font("TOYZARUX", 3, 35));
        UserSSN.setFont(new Font("Nova Square", 4, 18));
        UserPass.setFont(new Font("Nova Square", 4, 18));
        userSSNField.setFont(new Font("Nova Square", 4, 18));
        userPassField.setFont(new Font("Nova Square", 4, 18));
        ButtonLogin.setFont(new Font("Byom", 2, 15));
    }

    private void setComponentBorder() {
        ButtonLogin.setBorder(createEmptyBorder(0, 0, 0, 0));
        userSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        userPassField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
    }

    private void removeButtonFocus() {
        ButtonLogin.setFocusPainted(false);
    }

    public static void main(String args[]) throws IOException {
        try {
            DataInputStream ReadFile = new DataInputStream(new FileInputStream("C:/programData/RRS/AdminSessionData.bin"));

            String EmptyData = ReadFile.readLine();
            String userSSN = ReadFile.readLine();
            String userPass = ReadFile.readLine();
            String userFullName = ReadFile.readLine();
            ReadFile.close();
            
            String query = "SELECT * FROM adminaccount WHERE adminUserSSN='" + userSSN + "' AND adminUserPass='" + userPass + "'";
            int isAvailable = 0;
            try {
                Connection conn = DBConnect.getConnection();
                if (conn == null) {
                    new MainAppInterface().setVisible(true);
                    Error.setVisible(true);
                    ErrorLabel.setText("   Unable To Connect To The Server");
                    ErrorLabel.setVisible(true);
                } else {
                    Statement stmt = conn.createStatement();
                    ResultSet RS;
                    RS = stmt.executeQuery(query);
                    
                    while (RS.next()) {
                        isAvailable = 1;
                    }

                    if (isAvailable == 1) {
                        new AdminAccount().setVisible(true);
                    } else {
                        new MainAppInterface().setVisible(true);
                        Error.setVisible(true);
                        ErrorLabel.setText("   Error Loading Session Data");
                        ErrorLabel.setVisible(true);
                    }
                }
            } catch (SQLException ex) {
                new MainAppInterface().setVisible(true);
                Error.setVisible(true);
                ErrorLabel.setText("     Error Connecting To The Database");
                ErrorLabel.setVisible(true);
            }
        } catch (IOException Ex) {
            new MainAppInterface().setVisible(true);
        }
    }
}
