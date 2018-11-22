package railway.userAccount;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import static javax.swing.BorderFactory.*;

public class userLoginInterface extends JFrame {
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

    //Create Account Components
    JLabel RGUserSSN         = new JLabel("User SSN");
    JLabel RGUserPass        = new JLabel("New Password");
    JLabel RGUserPassConfirm = new JLabel("Confirm Password");
    JLabel UserFirstName     = new JLabel("First Name");
    JLabel UserMiddleName    = new JLabel("Middle Name");
    JLabel UserLastName      = new JLabel("Last Name");

    JTextField RGUserSSNField             = new JTextField();
    JPasswordField RGUserPassField        = new JPasswordField();
    JPasswordField RGUserPassConfirmField = new JPasswordField();
    JTextField UserFirstNameField         = new JTextField();
    JTextField UserMiddleNameField        = new JTextField();
    JTextField UserLastNameField          = new JTextField();

    userLoginInterface() {
        setTitle("User Account Login Panel");
        setLayout(null);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        MainPanel.setBounds(0, 0, 450, 600);
        MainPanel.setLayout(null);
        MainPanel.setBackground(new Color(34, 35, 41));

        Error.setOpaque(true);
        ErrorLabel.setOpaque(true);

        RGUserSSN.setVisible(false);
        RGUserPass.setVisible(false);
        RGUserPassConfirm.setVisible(false);
        UserFirstName.setVisible(false);
        UserMiddleName.setVisible(false);
        UserLastName.setVisible(false);
        RGUserSSNField.setVisible(false);
        RGUserPassField.setVisible(false);
        RGUserPassConfirmField.setVisible(false);
        UserFirstNameField.setVisible(false);
        UserMiddleNameField.setVisible(false);
        UserLastNameField.setVisible(false);
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
            WriteFile = new DataOutputStream(new FileOutputStream("C:/programData/RRS/UserSessionData.bin"));
            String TextToBeWritten = "\n" + LoggedUserSSN + "\n" + LoggedUserPass + "\n" + LoggedUserFullName;
            WriteFile.writeUTF(TextToBeWritten);
            WriteFile.close(); 
        } catch(IOException EXC){}
    }
    
    public void actionListeners() {
        ButtonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Error.setVisible(false);
                ErrorLabel.setVisible(false);
                if (LoginLabel.getText().equals("      Login")) {
                    Error.setVisible(false);
                    ErrorLabel.setVisible(false);

                    UserSSN.setVisible(false);
                    UserPass.setVisible(false);
                    userSSNField.setVisible(false);
                    userPassField.setVisible(false);

                    RGUserSSN.setVisible(true);
                    RGUserPass.setVisible(true);
                    RGUserPassConfirm.setVisible(true);
                    UserFirstName.setVisible(true);
                    UserMiddleName.setVisible(true);
                    UserLastName.setVisible(true);
                    RGUserSSNField.setVisible(true);
                    RGUserPassField.setVisible(true);
                    RGUserPassConfirmField.setVisible(true);
                    UserFirstNameField.setVisible(true);
                    UserMiddleNameField.setVisible(true);
                    UserLastNameField.setVisible(true);

                    LoginLabel.setText("    Register");
                    ButtonLogin.setBounds(130, 502, 170, 35);
                    ButtonRegister.setBounds(110, 457, 210, 35);
                } else {
                    registerAction();
                }
            }
        });

        ButtonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Error.setVisible(false);
                ErrorLabel.setVisible(false);
                if (LoginLabel.getText().equals("    Register")) {
                    RGUserSSN.setVisible(false);
                    RGUserPass.setVisible(false);
                    RGUserPassConfirm.setVisible(false);
                    UserFirstName.setVisible(false);
                    UserMiddleName.setVisible(false);
                    UserLastName.setVisible(false);
                    RGUserSSNField.setVisible(false);
                    RGUserPassField.setVisible(false);
                    RGUserPassConfirmField.setVisible(false);
                    UserFirstNameField.setVisible(false);
                    UserMiddleNameField.setVisible(false);
                    UserLastNameField.setVisible(false);

                    LoginLabel.setVisible(true);
                    UserSSN.setVisible(true);
                    UserPass.setVisible(true);
                    userSSNField.setVisible(true);
                    userPassField.setVisible(true);

                    LoginLabel.setText("      Login");
                    ButtonLogin.setBounds(130, 420, 170, 35);
                    ButtonRegister.setBounds(110, 465, 210, 35);
                } else {
                    if (!userSSNField.getText().equals("") && !userPassField.getText().equals("")) {
                        String query = "SELECT * FROM userlogin WHERE userSSN='" + userSSNField.getText() + "' AND userPass='" + userPassField.getText() + "'";
                        int isAvailable = 0;
                        String userState = "NotApproved";
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
                                    userState = RS.getString("userState");
                                    isAvailable = 1;
                                    FullName = RS.getString("userFirstName") + " " + RS.getString("userMiddleName");
                                }

                                if (isAvailable == 1) {
                                    LoggedUserSSN = userSSNField.getText();
                                    makeSessionFile(LoggedUserSSN, userPassField.getText(), FullName);
                                    if(userState.equals("Approved")) {
                                        new userMainInterface(LoggedUserSSN, FullName).setVisible(true);
                                        setVisible(false);
                                    } else {
                                        Error.setVisible(true);
                                        ErrorLabel.setText("   Account is Not Approved");
                                        ErrorLabel.setVisible(true);
                                    }
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
            }
        });
    }

    public void registerAction() {
        String USSN = RGUserSSNField.getText(), UFN = UserFirstNameField.getText(), UPAS = RGUserPassField.getText();
        String UMN = UserMiddleNameField.getText(), ULN = UserLastNameField.getText(), UPC = RGUserPassConfirmField.getText();

        if (!USSN.equals("") && !UFN.equals("") && !UMN.equals("") && !ULN.equals("") && !UPAS.equals("") && !UPC.equals("")) {
            if (UPAS.equals(UPC)) {
                int isAvailable = 0;

                try {
                    Connection conn = DBConnect.getConnection();
                    if (conn == null) {
                        Error.setVisible(true);
                        ErrorLabel.setText("   Unable To Connect To The Server");
                        ErrorLabel.setVisible(true);
                    } else {
                        Statement stmt = conn.createStatement();
                        ResultSet RS, RS1;
                        String Query = "SELECT * FROM useridentity WHERE userSSN='" + USSN + "'";
                        
                        RS = stmt.executeQuery(Query);
                        while (RS.next()) {
                            isAvailable = 1;
                        }
                        
                        if(isAvailable == 1) {
                            Query = "SELECT * FROM userlogin WHERE userSSN='" + USSN + "'";
                            boolean isUserAvail  = false;
                            RS1 = stmt.executeQuery(Query);
                            
                            while(RS1.next()) {
                                isUserAvail = true;
                            }
                            
                            if(isUserAvail == false) {
                                Error.setVisible(false);
                                ErrorLabel.setVisible(false);
                                String QURY = "INSERT INTO userlogin() VALUES('" + USSN + "','" + UPAS + "',"
                                        + "'" + UFN + "','" + UMN + "','" + ULN + "','NotApproved')";
                                stmt.executeUpdate(QURY);
                                JOptionPane.showMessageDialog(null, "Your Account is Not Approved Go to The Branch and Ask For Approval");
                            } else {
                                Error.setVisible(true);
                                ErrorLabel.setText("   The User is Already Registered");
                                ErrorLabel.setVisible(true);
                            }
                        } else {
                            Error.setVisible(true);
                            ErrorLabel.setText("   Registration Not Allowed");
                            ErrorLabel.setVisible(true);
                            JOptionPane.showMessageDialog(null, "You Are Not Allowed To Use This App For Registration\n"
                                                                + "  Please Goto Near Branch and Reserve Manually");
                        }
                    }
                } catch (SQLException ex) {
                    Error.setVisible(true);
                    ErrorLabel.setText("     Error Connecting To The Database");
                    ErrorLabel.setVisible(true);
                }
            } else {
                Error.setVisible(true);
                ErrorLabel.setText("     Password Does Not Matched");
                ErrorLabel.setVisible(true);
            }
        } else {
            Error.setVisible(true);
            ErrorLabel.setText("      Fill All Fields Correctly");
            ErrorLabel.setVisible(true);
        }
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
        MainPanel.add(ButtonRegister);
        MainPanel.add(RGUserSSN);
        MainPanel.add(RGUserPass);
        MainPanel.add(RGUserPassConfirm);
        MainPanel.add(UserFirstName);
        MainPanel.add(UserMiddleName);
        MainPanel.add(UserLastName);
        MainPanel.add(RGUserSSNField);
        MainPanel.add(RGUserPassField);
        MainPanel.add(RGUserPassConfirmField);
        MainPanel.add(UserFirstNameField);
        MainPanel.add(UserMiddleNameField);
        MainPanel.add(UserLastNameField);
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
        ButtonRegister.setBounds(110, 465, 210, 35);

        RGUserSSN.setBounds(50, 220, 330, 20);
        RGUserSSNField.setBounds(50, 239, 330, 30);
        UserFirstName.setBounds(50, 280, 160, 20);
        UserMiddleName.setBounds(220, 280, 160, 20);
        UserLastName.setBounds(50, 339, 330, 20);
        UserFirstNameField.setBounds(50, 299, 160, 30);
        UserMiddleNameField.setBounds(220, 299, 160, 30);
        UserLastNameField.setBounds(50, 358, 330, 30);
        RGUserPass.setBounds(50, 398, 160, 20);
        RGUserPassConfirm.setBounds(220, 398, 160, 20);
        RGUserPassField.setBounds(50, 417, 160, 30);
        RGUserPassConfirmField.setBounds(220, 417, 160, 30);

    }

    private void setComponentBackground() {
        ErrorLabel.setBackground(new Color(240, 81, 82));
        Error.setBackground(new Color(221, 68, 69));
        UserSSN.setBackground(new Color(200, 200, 70));
        UserPass.setBackground(new Color(200, 200, 70));
        userSSNField.setBackground(new Color(34, 35, 41));
        userPassField.setBackground(new Color(34, 35, 41));
        ButtonLogin.setBackground(new Color(89, 179, 105));
        ButtonRegister.setBackground(new Color(89, 179, 105));
        RWBanner.setBackground(new Color(102, 0, 102, 0));

        RGUserSSN.setBackground(new Color(200, 200, 70));
        RGUserPass.setBackground(new Color(200, 200, 70));
        RGUserPassConfirm.setBackground(new Color(200, 200, 70));
        UserFirstName.setBackground(new Color(200, 200, 70));
        UserMiddleName.setBackground(new Color(200, 200, 70));
        UserLastName.setBackground(new Color(200, 200, 70));
        RGUserSSNField.setBackground(new Color(34, 35, 41));
        RGUserPassField.setBackground(new Color(34, 35, 41));
        RGUserPassConfirmField.setBackground(new Color(34, 35, 41));
        UserFirstNameField.setBackground(new Color(34, 35, 41));
        UserMiddleNameField.setBackground(new Color(34, 35, 41));
        UserLastNameField.setBackground(new Color(34, 35, 41));
    }

    private void setComponentForeground() {
        Error.setForeground(Color.WHITE);
        ErrorLabel.setForeground(Color.WHITE);
        UserSSN.setForeground(new Color(240, 81, 82));
        UserPass.setForeground(new Color(240, 81, 82));
        userSSNField.setForeground(Color.WHITE);
        userPassField.setForeground(Color.WHITE);
        ButtonLogin.setForeground(new Color(255, 255, 255));
        ButtonRegister.setForeground(new Color(255, 255, 255));
        RWBanner.setForeground(Color.CYAN);
        LoginLabel.setForeground(Color.WHITE);

        RGUserSSN.setForeground(new Color(240, 81, 82));
        RGUserPass.setForeground(new Color(240, 81, 82));
        RGUserPassConfirm.setForeground(new Color(240, 81, 82));
        UserFirstName.setForeground(new Color(240, 81, 82));
        UserMiddleName.setForeground(new Color(240, 81, 82));
        UserLastName.setForeground(new Color(240, 81, 82));
        RGUserSSNField.setForeground(Color.WHITE);
        RGUserPassField.setForeground(Color.WHITE);
        RGUserPassConfirmField.setForeground(Color.WHITE);
        UserFirstNameField.setForeground(Color.WHITE);
        UserMiddleNameField.setForeground(Color.WHITE);
        UserLastNameField.setForeground(Color.WHITE);
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
        ButtonRegister.setFont(new Font("Byom", 2, 15));

        RGUserSSN.setFont(new Font("Nova Square", 4, 18));
        RGUserPass.setFont(new Font("Nova Square", 4, 18));
        RGUserPassConfirm.setFont(new Font("Nova Square", 4, 18));
        UserFirstName.setFont(new Font("Nova Square", 4, 18));
        UserMiddleName.setFont(new Font("Nova Square", 4, 18));
        UserLastName.setFont(new Font("Nova Square", 4, 18));
        RGUserSSNField.setFont(new Font("Nova Square", 4, 18));
        RGUserPassField.setFont(new Font("Nova Square", 4, 18));
        RGUserPassConfirmField.setFont(new Font("Nova Square", 4, 18));
        UserFirstNameField.setFont(new Font("Nova Square", 4, 18));
        UserMiddleNameField.setFont(new Font("Nova Square", 4, 18));
        UserLastNameField.setFont(new Font("Nova Square", 4, 18));
    }

    private void setComponentBorder() {
        ButtonLogin.setBorder(createEmptyBorder(0, 0, 0, 0));
        ButtonRegister.setBorder(createEmptyBorder(0, 0, 0, 0));
        userSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        userPassField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        RGUserSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        RGUserPassField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        RGUserPassConfirmField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        UserFirstNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        UserMiddleNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
        UserLastNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80, 81, 88)));
    }

    private void removeButtonFocus() {
        ButtonLogin.setFocusPainted(false);
        ButtonRegister.setFocusPainted(false);
    }

    public static void main(String args[]) throws IOException {
        try {
            DataInputStream ReadFile = new DataInputStream(new FileInputStream("C:/programData/RRS/UserSessionData.bin"));

            String EmptyData = ReadFile.readLine();
            String userSSN = ReadFile.readLine();
            String userPass = ReadFile.readLine();
            String userFullName = ReadFile.readLine();
            ReadFile.close();
            
            String query = "SELECT * FROM userlogin WHERE userSSN='" + userSSN + "' AND userPass='" + userPass + "'";
            int isAvailable = 0;
            String userState = "NotApproved";
            try {
                Connection conn = DBConnect.getConnection();
                if (conn == null) {
                    new userLoginInterface().setVisible(true);
                    Error.setVisible(true);
                    ErrorLabel.setText("   Unable To Connect To The Server");
                    ErrorLabel.setVisible(true);
                } else {
                    Statement stmt = conn.createStatement();
                    ResultSet RS;
                    RS = stmt.executeQuery(query);
                    
                    while (RS.next()) {
                        userState = RS.getString("userState");
                        isAvailable = 1;
                    }

                    if (isAvailable == 1) {
                        if(userState.equals("Approved")) {
                            new userMainInterface(userSSN, userFullName).setVisible(true);
                        } else {
                            new userLoginInterface().setVisible(true);
                            Error.setVisible(true);
                            ErrorLabel.setText("   Account is Not Approved");
                            ErrorLabel.setVisible(true);
                        }
                    } else {
                        new userLoginInterface().setVisible(true);
                        Error.setVisible(true);
                        ErrorLabel.setText("   Error Loading Session Data");
                        ErrorLabel.setVisible(true);
                    }
                }
            } catch (SQLException ex) {
                new userLoginInterface().setVisible(true);
                Error.setVisible(true);
                ErrorLabel.setText("     Error Connecting To The Database");
                ErrorLabel.setVisible(true);
            }
        } catch (IOException Ex) {
            new userLoginInterface().setVisible(true);
        }
    }
}
