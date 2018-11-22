package railway.adminAccount;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createMatteBorder;
import javax.swing.table.DefaultTableModel;

public class AdminAccount extends JFrame {
    boolean TableState = false;
    String[] Days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
                     "17","18","19","20","21","22","23","24","25","26","27","28","29","30"},
             Years = {"2018","2019","2020","2021","2022","2023","2024","2025"},
             Months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    
    JButton AddTrains        = new JButton("Add Train      ");
    JButton ViewPassengers   = new JButton("View Passengers");
    JButton ViewTrains       = new JButton("View Trains    ");
    JButton ButtonRegister   = new JButton("Register User  ");
    JLabel NotificationLabel = new JLabel("");
    JButton LogoutButton     = new JButton("Logout");
    JButton ApproveUsers     = new JButton("Approve Users");
    
    //User Components
    JLabel UserSSN                 = new JLabel("User SSN");
    JLabel UserFirstName           = new JLabel("First Name");
    JLabel UserMiddleName          = new JLabel("Middle Name");
    JLabel UserLastName            = new JLabel("Last Name");
    JLabel RRSLabel                = new JLabel("Railway Reservation System");
    JTextField UserSSNField        = new JTextField("");
    JTextField UserFirstNameField  = new JTextField("");
    JTextField UserMiddleNameField = new JTextField("");
    JTextField UserLastNameField   = new JTextField("");
    
    //Approve Users Component
    JLabel userImage        = new JLabel();
    JLabel ApproveUserSSN          = new JLabel("Enter User SSN");
    JTextField ApproveUserSSNField = new JTextField("");
    JButton ApproveButton   = new JButton("Approve User");
    
    //Train Components
    JLabel TrainNum     = new JLabel("Train Number");
    JLabel TrainName    = new JLabel("Train Name");
    JLabel Boarding     = new JLabel("Boarding City");
    JLabel Destination  = new JLabel("Destination City");
    JLabel NOSFirst     = new JLabel("NO of Seat First Class");
    JLabel FeeFirst     = new JLabel("Fee Per Seat");
    JLabel NOSSecond    = new JLabel("NO of Seat Second Class");
    JLabel FeeSecond    = new JLabel("Fee Per Seat");
    JLabel DateOfTravel = new JLabel("Date of Travel");
    JLabel Day          = new JLabel("Day");
    JLabel Month        = new JLabel("Month");
    JLabel Year         = new JLabel("Year");
    
    JButton RegisterTrain        = new JButton("Register Train");
    JTextField TrainNumField     = new JTextField();
    JTextField TrainNameField    = new JTextField();
    JTextField BoardingField     = new JTextField();
    JTextField DestinationField  = new JTextField();
    JTextField NOSFirstField     = new JTextField();
    JTextField FeeFirstField     = new JTextField();
    JTextField NOSSecondField    = new JTextField();
    JTextField FeeSecondField    = new JTextField();
    
    JComboBox<String> DayField = new JComboBox<>(Days);
    JComboBox<String> MonthField = new JComboBox<>(Months);
    JComboBox<String> YearField = new JComboBox<>(Years);
    
    JTable InformationViewer = new JTable();
    JScrollPane ScrollPane   = new JScrollPane();
    
    
    //ViewTrain Components
    JScrollPane ViewTrainsPane  = new JScrollPane();
    JTable ViewTrainTable       = new JTable();
    JLabel SearchTrains         = new JLabel("Search Trains......");
    JTextField SearchTrainField = new JTextField();
    JButton SearchTrainsButton  = new JButton("Search Trains");
    
    JPanel SidePanel                 = new JPanel();
    JPanel MainPanel                 = new JPanel();
    JPanel AddTrainPanel             = new JPanel();
    JPanel ViewTrainInformationPanel = new JPanel();
    JPanel ApproveUsersPanel         = new JPanel();
    
    public AdminAccount() {
        setSize(1000,476);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("Railway Reservation System Admin Panel");
        
        userImage.setOpaque(true);
        SidePanel.setLayout(null);
        NotificationLabel.setOpaque(true);
        NotificationLabel.setVisible(false);
        
        AddTrainPanel.setLayout(null);
        AddTrainPanel.setVisible(false);
        
        ViewTrainInformationPanel.setLayout(null);
        ViewTrainInformationPanel.setVisible(false);
                
        ApproveUsersPanel.setLayout(null);
        ApproveUsersPanel.setVisible(false);
        MainPanel.setLayout(null);
        ScrollPane.setViewportView(InformationViewer);
        
        setComponentForeground();
        setComponentBound();
        setComponentBackground();
        setComponentFont();
        RemoveBorder();
        RemoveFocus();
        setContentProperty();
        ComponentActionListeners();
        AcceptOnlyNumbersField();
        viewAllUsers();
        AddComponentToFrame();
    }
    
    public void deleteSessionFile(String LoggedUserSSN, String LoggedUserPass, String LoggedUserFullName){
        File RRSDir = new File("C:/programData/RRS/AdminSessionData.bin");
        RRSDir.delete();

        this.setVisible(false);
    }

    private void AcceptOnlyNumbersField() {
        TrainNumField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,TrainNumField);
            }
        });
        
        NOSFirstField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,NOSFirstField);
            }
        });
        
        FeeFirstField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,FeeFirstField);
            }
        });
        
        NOSSecondField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,NOSSecondField);
            }
        });
        
        FeeSecondField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,FeeSecondField);
            }
        });
        
        
        
    }
    
    public void KeyTypedEvent(KeyEvent Evt) {
        if(!Character.isDigit(Evt.getKeyChar())) {
            Evt.consume();
        }
    }
    
    public void KeyReleased(KeyEvent evt, JTextField TextField) {
        String age = TextField.getText();
        String newAge = "";
        char chAge;
        int index = age.length() - 1;
        
        for(int i=0; i<= index; i++) {
            chAge = age.charAt(i);
            if((int)chAge >= 48 && (int)chAge <= 57) {
                newAge = newAge + age.charAt(i);
            }
        }
        
        TextField.setText(newAge);
    }
    
    public void EmptyTrainFields() {
        TrainNumField.setText("");
        TrainNameField.setText("");            
        BoardingField.setText("");
        DestinationField.setText("");
        NOSFirstField.setText("");        
        FeeFirstField.setText("");        
        NOSSecondField.setText("");        
        FeeSecondField.setText("");
        ApproveUserSSNField.setText("");
    }
    
    private void ValidateTrain() {
        try {
            String TRN = TrainNameField.getText(), BP = BoardingField.getText(), DP = DestinationField.getText();
            int NOSF = Integer.parseInt(NOSFirstField.getText()),
                FF = Integer.parseInt(FeeFirstField.getText()),
                TRNum = Integer.parseInt(TrainNumField.getText()),
                NOSS = Integer.parseInt(NOSSecondField.getText()),
                FS = Integer.parseInt(FeeSecondField.getText()),
                DF = Integer.parseInt((String) DayField.getSelectedItem()),
                MF = Integer.parseInt((String) MonthField.getSelectedItem()),
                YF = Integer.parseInt((String) DayField.getSelectedItem());
                
            if(TRNum != 0 && !TRN.equals("") && !BP.equals("") && !DP.equals("") && NOSF != 0 &&
               FF != 0 && NOSS != 0 && FS != 0 && DF != 0 && MF != 0 && YF != 0) {
                 try {
                    String Query = "SELECT * FROM traininfo WHERE trainNum=" + TRNum + " and Day=" + DF + " and Month=" + MF + " and Year="+YF;
                    Connection conn = DBConnect.getConnection();
                    if(conn == null) {
                        NotificationLabel.setText("          Unable to Connect To The Server Please Check Your Connection");
                        NotificationLabel.setVisible(true);
                    } else {
                        Statement stmt = conn.createStatement();
                        ResultSet RS = stmt.executeQuery(Query);
                        boolean isAvailable = false;

                        while(RS.next()) {
                            isAvailable = true;
                        }

                        if(isAvailable == true) {
                            NotificationLabel.setText("         This Train is Already Registered With The Current Day");
                            NotificationLabel.setVisible(true);
                        } else {
                            String QRY = "INSERT INTO trainInfo VALUES(" + TRNum + ",'" + TRN + "',"
                                    + "'" + BP + "','" + DP + "'," + NOSF + "," + FF + "," + NOSS + ","
                                    + FS + "," + DF + "," + MF + "," + YF + ")";
                            try {
                                stmt.executeUpdate(QRY);
                                NotificationLabel.setText("                Train Sucessfully Inserted To The Database");
                                NotificationLabel.setVisible(true);
                                EmptyTrainFields();
                            } catch(Exception excep){
                                NotificationLabel.setText("       !Error Occured Unable To Register The Train at This Time");
                                NotificationLabel.setVisible(true);
                            }
                        }
                    }
                 } catch(Exception exc){
                     NotificationLabel.setText("                !Error Occured Unable To Register The Train at This Time");
                     NotificationLabel.setVisible(true);
                 }
            } else {
                NotificationLabel.setText("     Please Enter The Informations Correctly");
                NotificationLabel.setVisible(true);
            }
        } catch(Exception exp){
            NotificationLabel.setText("        Please Fill All Fields Or Numeric Fields Correctly");
            NotificationLabel.setVisible(true);
        }
    }
    
    public final ImageIcon ResizeImage(byte[] pic) {
        ImageIcon MyImage = new ImageIcon(pic);
        Image img = MyImage.getImage();
        Image NewImg = img.getScaledInstance(userImage.getWidth(), userImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(NewImg);
        return image;
    }
    
    private void viewAllUsers() {
        InformationViewer.setEnabled(false);
        InformationViewer.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"User SSN","Passenger Name","Age","TR Number","Class"}
        ));
        
        InformationViewer.getColumnModel().getColumn(0).setMaxWidth(130);
        InformationViewer.getColumnModel().getColumn(0).setMinWidth(130);
        InformationViewer.getColumnModel().getColumn(1).setMaxWidth(300);
        InformationViewer.getColumnModel().getColumn(1).setMinWidth(300);
        InformationViewer.getColumnModel().getColumn(2).setMaxWidth(80);
        InformationViewer.getColumnModel().getColumn(2).setMinWidth(80);
        InformationViewer.getColumnModel().getColumn(3).setMaxWidth(130);
        InformationViewer.getColumnModel().getColumn(3).setMinWidth(130);
        InformationViewer.getColumnModel().getColumn(4).setMaxWidth(135);
        InformationViewer.getColumnModel().getColumn(4).setMinWidth(135);
        
        String query = "SELECT * FROM passengerdetail";
        Connection conn = DBConnect.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DefaultTableModel model=(DefaultTableModel)InformationViewer.getModel();

            Object[] row;
            while(rs.next()) {
                row = new Object[5];
                row[0] = rs.getString(2);
                row[1] = rs.getString(6);
                row[2] = rs.getInt(7);
                row[3] = rs.getInt(5);
                row[4] = rs.getString(4);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
        }
    }    

    private void setContentProperty() {
        //Table To View Information
        
        UserSSN.setVisible(false);
        UserFirstName.setVisible(false);
        UserMiddleName.setVisible(false);
        UserLastName.setVisible(false);
        UserSSNField.setVisible(false);
        UserFirstNameField.setVisible(false);
        UserMiddleNameField.setVisible(false);
        UserLastNameField.setVisible(false);
        ButtonRegister.setVisible(false);
        
        UserSSN.setOpaque(true);
        UserFirstName.setOpaque(true);
        UserMiddleName.setOpaque(true);
        UserLastName.setOpaque(true);
    }
    
    private void ComponentActionListeners() {
        ViewPassengers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                UserSSN.setVisible(false);
                UserFirstName.setVisible(false);
                UserMiddleName.setVisible(false);
                UserLastName.setVisible(false);
                UserSSNField.setVisible(false);
                UserFirstNameField.setVisible(false);
                UserMiddleNameField.setVisible(false);
                UserLastNameField.setVisible(false);
                ButtonRegister.setVisible(false);
                AddTrainPanel.setVisible(false);
                MainPanel.add(RRSLabel);
                MainPanel.setVisible(true);
                ScrollPane.setVisible(true);
                viewAllUsers();
                InformationViewer.setVisible(true);
                AddTrainPanel.setVisible(false);
                ViewTrainInformationPanel.setVisible(false);
                ApproveUsersPanel.setVisible(false);
                MainPanel.setVisible(true);
                NotificationLabel.setVisible(false);
            }
        });
        
        AddTrains.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewTrainInformationPanel.setVisible(false);
                MainPanel.setVisible(false);
                ApproveUsersPanel.setVisible(false);
                AddTrainPanel.setVisible(true);
                AddTrainPanel.add(RRSLabel);
                AddTrainPanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
            }
        });
        
        RegisterTrain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ValidateTrain();
            }
        });
        
        ViewTrains.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                MainPanel.setVisible(false);
                ApproveUsersPanel.setVisible(false);
                AddTrainPanel.setVisible(false);
                ViewTrainInformationPanel.add(RRSLabel);
                ViewTrainInformationPanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
                ViewTrainInformationPanel.setVisible(true);
                ViewTrains();
            }
        });
        
        LogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                deleteSessionFile("","","");
                new MainAppInterface().setVisible(true);
            }
        });
        
        ApproveUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ViewTrainInformationPanel.setVisible(false);
                MainPanel.setVisible(false);
                AddTrainPanel.setVisible(false);
                ApproveUsersPanel.setVisible(true);
                ApproveUsersPanel.add(RRSLabel);
                ApproveUsersPanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
            }
        });
        
        ApproveUserSSNField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                try {
                    Connection conn = DBConnect.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs;
                    
                    String AUSSF = ApproveUserSSNField.getText();
                    String query = "SELECT * FROM useridentity WHERE userSSN='" + AUSSF + "'";
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        if(rs.getBytes("userImage") != null) {
                            userImage.setIcon(ResizeImage(rs.getBytes("userImage")));
                        }
                    }
                } catch(SQLException se) {
                }
            }
        });
        
        ApproveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                String AUSSF = ApproveUserSSNField.getText();
                if(!AUSSF.equals("")) {
                    Connection conn = DBConnect.getConnection();
                    String Query = "SELECT * FROM userlogin WHERE userSSN='" + AUSSF + "'";
                    if(conn != null) {
                        try {
                            Statement stmt = conn.createStatement();
                            ResultSet RS = stmt.executeQuery(Query);
                            boolean isAvailable = false;
                            String userState = "";

                            while(RS.next()) {
                                userState = RS.getString("userState");
                                isAvailable = true;
                            }

                            if(isAvailable == true) {
                                if(userState.equals("NotApproved")) {
                                    Query = "UPDATE userlogin SET userState='Approved' WHERE userSSN='" + AUSSF + "'";
                                    stmt.executeUpdate(Query);
                                    NotificationLabel.setText("  The User is Sucessfully Approved Now He/She Can Use The Service");
                                    NotificationLabel.setVisible(true);
                                } else {
                                    NotificationLabel.setText("         The User is Already Approved");
                                    NotificationLabel.setVisible(true);
                                }
                            } else {
                                NotificationLabel.setText("         No Registered User is Available With This SSN");
                                NotificationLabel.setVisible(true);
                            }
                        }catch(Exception ex){
                            System.out.println("Some Error Occured");
                        }
                    } else {
                        NotificationLabel.setText("    Unable To Connect To The Server Please Check Your Connection");
                        NotificationLabel.setVisible(true);
                    }
                } else {
                    NotificationLabel.setText("             Please Enter The User Social Security Number Correctly");
                    NotificationLabel.setVisible(true);
                }
            }
        });
    }
    
    private void RemoveFocus() {
        ApproveButton.setFocusPainted(false);
        ApproveUsers.setFocusPainted(false);
        LogoutButton.setFocusPainted(false);
        ButtonRegister.setFocusPainted(false);
        ViewTrains.setFocusPainted(false);
        ViewPassengers.setFocusPainted(false);
        AddTrains.setFocusPainted(false);
        RegisterTrain.setFocusPainted(false);
        SearchTrainsButton.setFocusPainted(false);
    }
    
    public void ViewTrains() {
        ViewTrainsPane.setViewportView(ViewTrainTable);
        ViewTrainTable.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"TR Num","BD Point","DS Point","First","Fee","Second","Fee","Day","Month","Year"}
        ));
        
        ViewTrainTable.getColumnModel().getColumn(0).setMaxWidth(65);
        ViewTrainTable.getColumnModel().getColumn(0).setMinWidth(70);
        ViewTrainTable.getColumnModel().getColumn(1).setMaxWidth(165);
        ViewTrainTable.getColumnModel().getColumn(1).setMinWidth(165);
        ViewTrainTable.getColumnModel().getColumn(2).setMaxWidth(165);
        ViewTrainTable.getColumnModel().getColumn(2).setMinWidth(165);
        ViewTrainTable.getColumnModel().getColumn(3).setMaxWidth(60);
        ViewTrainTable.getColumnModel().getColumn(3).setMinWidth(60);
        ViewTrainTable.getColumnModel().getColumn(4).setMaxWidth(60);
        ViewTrainTable.getColumnModel().getColumn(4).setMinWidth(60);
        ViewTrainTable.getColumnModel().getColumn(5).setMaxWidth(60);
        ViewTrainTable.getColumnModel().getColumn(5).setMinWidth(60);
        ViewTrainTable.getColumnModel().getColumn(6).setMaxWidth(60);
        ViewTrainTable.getColumnModel().getColumn(6).setMinWidth(60);
        ViewTrainTable.getColumnModel().getColumn(7).setMaxWidth(50);
        ViewTrainTable.getColumnModel().getColumn(7).setMinWidth(50);
        ViewTrainTable.getColumnModel().getColumn(8).setMaxWidth(50);
        ViewTrainTable.getColumnModel().getColumn(8).setMinWidth(50);
        ViewTrainTable.getColumnModel().getColumn(9).setMaxWidth(50);
        ViewTrainTable.getColumnModel().getColumn(9).setMinWidth(50);
        
        ViewTrainTable.setEnabled(false);
        String query = "SELECT * FROM traininfo";
        Connection conn = DBConnect.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DefaultTableModel model=(DefaultTableModel)ViewTrainTable.getModel();

            Object[] row;
            while(rs.next()) {
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(3);
                row[2] = rs.getString(4);
                row[3] = rs.getInt(5);
                row[4] = rs.getInt(6);
                row[5] = rs.getInt(7);
                row[6] = rs.getInt(8);
                row[7] = rs.getInt(9);
                row[8] = rs.getInt(10);
                row[9] = rs.getInt(11);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
        }
    }
    
    private void setComponentForeground() {
        ApproveUserSSN.setForeground(new Color(240,81,82));
        ApproveUserSSNField.setForeground(Color.WHITE);
        LogoutButton.setForeground(Color.WHITE);
        NotificationLabel.setForeground(Color.WHITE);
        RRSLabel.setForeground(new Color(240,81,82));
        InformationViewer.setForeground(Color.WHITE);
        ButtonRegister.setForeground(Color.WHITE);
        ApproveUsers.setForeground(Color.WHITE);
        RegisterTrain.setForeground(Color.WHITE);
        AddTrains.setForeground(Color.WHITE);
        ViewTrains.setForeground(Color.WHITE);
        ViewPassengers.setForeground(Color.WHITE);
        UserSSN.setForeground(new Color(240,81,82));
        UserFirstName.setForeground(new Color(240,81,82));
        UserMiddleName.setForeground(new Color(240,81,82));
        UserLastName.setForeground(new Color(240,81,82));
        UserSSNField.setForeground(Color.WHITE);
        UserFirstNameField.setForeground(Color.WHITE);
        UserMiddleNameField.setForeground(Color.WHITE);
        UserLastNameField.setForeground(Color.WHITE);
        
        TrainNum.setForeground(new Color(240,81,82));
        TrainName.setForeground(new Color(240,81,82));
        Boarding.setForeground(new Color(240,81,82));
        Destination.setForeground(new Color(240,81,82));
        NOSFirst.setForeground(new Color(240,81,82));
        FeeFirst.setForeground(new Color(240,81,82));
        NOSSecond.setForeground(new Color(240,81,82));
        FeeSecond.setForeground(new Color(240,81,82));
        DateOfTravel.setForeground(new Color(240,81,82));
        Day.setForeground(new Color(240,81,82));
        Month.setForeground(new Color(240,81,82));
        Year.setForeground(new Color(240,81,82));
        
        TrainNumField.setForeground(Color.WHITE);
        TrainNameField.setForeground(Color.WHITE);
        BoardingField.setForeground(Color.WHITE);
        DestinationField.setForeground(Color.WHITE);
        NOSFirstField.setForeground(Color.WHITE);
        FeeFirstField.setForeground(Color.WHITE);
        NOSSecondField.setForeground(Color.WHITE);
        FeeSecondField.setForeground(Color.WHITE);
        DayField.setForeground(Color.WHITE);
        MonthField.setForeground(Color.WHITE);
        YearField.setForeground(Color.WHITE);
        SearchTrainField.setForeground(Color.WHITE);
        SearchTrainsButton.setForeground(Color.WHITE);
        SearchTrains.setForeground(new Color(240,81,82));
    }
    
    private void setComponentFont() {
        ApproveUserSSN.setFont(new Font("Nova Square", 4, 18));
        ApproveUserSSNField.setFont(new Font("Nova Square", 4, 18));
        LogoutButton.setFont(new Font("Byom", 3, 15));
        ViewTrainTable.getTableHeader().setFont(new Font("Byom", 1, 13));
        NotificationLabel.setFont(new Font("Nova Square", 4, 18));
        InformationViewer.setFont(new Font("Consolas Bold Italic", 3, 14));
        RegisterTrain.setFont(new Font("Byom", 3, 15));
        ApproveUsers.setFont(new Font("Nova Square", 4, 18));
        ButtonRegister.setFont(new Font("Byom", 3, 15));
        UserSSN.setFont(new Font("Nova Square", 4, 18));
        UserFirstName.setFont(new Font("Nova Square", 4, 18));
        UserMiddleName.setFont(new Font("Nova Square", 4, 18));
        UserLastName.setFont(new Font("Nova Square", 4, 18));
        UserSSNField.setFont(new Font("Nova Square", 4, 18));
        UserFirstNameField.setFont(new Font("Nova Square", 4, 18));
        UserMiddleNameField.setFont(new Font("Nova Square", 4, 18));
        UserLastNameField.setFont(new Font("Nova Square", 4, 18));
        InformationViewer.getTableHeader().setFont(new Font("Byom", 3, 16));
        AddTrains.setFont(new Font("Nova Square", 4, 18));
        ViewTrains.setFont(new Font("Nova Square", 4, 18));
        ViewPassengers.setFont(new Font("Nova Square", 4, 18));
        RRSLabel.setFont(new Font("TOYZARUX", 3, 33));
        
        TrainNum.setFont(new Font("Nova Square", 4, 18));
        TrainName.setFont(new Font("Nova Square", 4, 18));
        Boarding.setFont(new Font("Nova Square", 4, 18));
        Destination.setFont(new Font("Nova Square", 4, 18));
        NOSFirst.setFont(new Font("Nova Square", 4, 18));
        FeeFirst.setFont(new Font("Nova Square", 4, 18));
        NOSSecond.setFont(new Font("Nova Square", 4, 18));
        FeeSecond.setFont(new Font("Nova Square", 4, 18));
        DateOfTravel.setFont(new Font("Nova Square", 4, 22));
        Day.setFont(new Font("Nova Square", 4, 18));
        Month.setFont(new Font("Nova Square", 4, 18));
        Year.setFont(new Font("Nova Square", 4, 18));
                
        TrainNumField.setFont(new Font("Nova Square", 4, 18));
        TrainNameField.setFont(new Font("Nova Square", 4, 18));
        BoardingField.setFont(new Font("Nova Square", 4, 18));
        DestinationField.setFont(new Font("Nova Square", 4, 18));
        NOSFirstField.setFont(new Font("Nova Square", 4, 18));
        FeeFirstField.setFont(new Font("Nova Square", 4, 18));
        NOSSecondField.setFont(new Font("Nova Square", 4, 18));
        FeeSecondField.setFont(new Font("Nova Square", 4, 18));
        DayField.setFont(new Font("Nova Square", 4, 18));
        MonthField.setFont(new Font("Nova Square", 4, 18));
        YearField.setFont(new Font("Nova Square", 4, 18));
        
        SearchTrainsButton.setFont(new Font("Byom", 3, 15));
        SearchTrainField.setFont(new Font("Nova Square", 4, 18));
        SearchTrains.setFont(new Font("Nova Square", 4, 27));
    }
    
    private void setComponentBackground() {
        userImage.setBackground(new Color(34,35,41));
        ApproveButton.setBackground(new Color(89,179,105));
        ApproveUserSSNField.setBackground(new Color(34,35,41));
        LogoutButton.setBackground(new Color(89,179,105));
        MainPanel.setBackground(new Color(34,35,41));
        AddTrainPanel.setBackground(new Color(34,35,41));
        ApproveUsersPanel.setBackground(new Color(34,35,41));
        ViewTrainInformationPanel.setBackground(new Color(34,35,41));
        SidePanel.setBackground(new Color(52,52,61));
        NotificationLabel.setBackground(new Color(240,81,82));
        RegisterTrain.setBackground(new Color(89,179,105));
        ButtonRegister.setBackground(new Color(89,179,105));
        ScrollPane.setBackground(new Color(34,35,41));
        InformationViewer.setBackground(Color.LIGHT_GRAY);
        UserSSN.setBackground(new Color(34,35,41));
        UserFirstName.setBackground(new Color(34,35,41));
        UserMiddleName.setBackground(new Color(34,35,41));
        UserLastName.setBackground(new Color(34,35,41));
        UserSSNField.setBackground(new Color(34,35,41));
        UserFirstNameField.setBackground(new Color(34,35,41));
        UserMiddleNameField.setBackground(new Color(34,35,41));
        UserLastNameField.setBackground(new Color(34,35,41));
        AddTrains.setBackground(new Color(52,52,61));
        ViewTrains.setBackground(new Color(52,52,61));
        ApproveUsers.setBackground(new Color(52,52,61));
        ViewPassengers.setBackground(new Color(52,52,61));
        
        SearchTrains.setBackground(new Color(200,200,70));
        TrainNum.setBackground(new Color(200,200,70));
        TrainName.setBackground(new Color(200,200,70));
        Boarding.setBackground(new Color(200,200,70));
        Destination.setBackground(new Color(200,200,70));
        NOSFirst.setBackground(new Color(200,200,70));
        FeeFirst.setBackground(new Color(200,200,70));
        NOSSecond.setBackground(new Color(200,200,70));
        FeeSecond.setBackground(new Color(200,200,70));
        DateOfTravel.setBackground(new Color(200,200,70));
        Day.setBackground(new Color(200,200,70));
        Month.setBackground(new Color(200,200,70));
        Year.setBackground(new Color(200,200,70));
        
        SearchTrainsButton.setBackground(new Color(89,179,105));
        SearchTrainField.setBackground(new Color(34,35,41));
        TrainNumField.setBackground(new Color(34,35,41));
        TrainNameField.setBackground(new Color(34,35,41));
        BoardingField.setBackground(new Color(34,35,41));
        DestinationField.setBackground(new Color(34,35,41));
        NOSFirstField.setBackground(new Color(34,35,41));
        FeeFirstField.setBackground(new Color(34,35,41));
        NOSSecondField.setBackground(new Color(34,35,41));
        FeeSecondField.setBackground(new Color(34,35,41));
        DayField.setBackground(new Color(34,35,41));
        MonthField.setBackground(new Color(34,35,41));
        YearField.setBackground(new Color(34,35,41));
    }
    
    private void setComponentBound() {
        ApproveUserSSN.setBounds(60,200,250,20);
        ApproveUserSSNField.setBounds(60,219,250,30);
        ApproveButton.setBounds(160,259,150,30);
        userImage.setBounds(340,130,250,250);
        LogoutButton.setBounds(0,0,210,30);
        MainPanel.setBounds(210,0,787,447);
        ViewTrainInformationPanel.setBounds(210,0,787,447);
        AddTrainPanel.setBounds(210,0,787,447);
        ApproveUsersPanel.setBounds(210,0,787,447);
        SidePanel.setBounds(0,0,210,447);
        NotificationLabel.setBounds(0,397,787,50);
        ViewPassengers.setBounds(0,130,210,60);
        AddTrains.setBounds(0,190,210,60);
        ViewTrains.setBounds(0,250,210,60);
        ApproveUsers.setBounds(0,310,210,60);
        ScrollPane.setBounds(0,70,783,377);
        UserSSNField.setBounds(10,115,250,30);
        UserFirstNameField.setBounds(350,115,250,30);
        UserMiddleNameField.setBounds(10,188,250,30);
        UserLastNameField.setBounds(350,188,250,30);
        UserSSN.setBounds(10,95,230,20);
        UserFirstName.setBounds(350,95,230,20);
        UserMiddleName.setBounds(10,170,230,20);
        UserLastName.setBounds(350,170,230,20);
        ButtonRegister.setBounds(350,225,250,30);
        RRSLabel.setBounds(0,0,787,70);
        
        //Add Train Components
        TrainNum.setBounds(10,100,120,20);
        TrainName.setBounds(140,100,200,20);
        Boarding.setBounds(350,100,210,20);
        Destination.setBounds(570,100,210,20);
        NOSFirst.setBounds(10,170,225,20);
        FeeFirst.setBounds(245,170,135,20);
        NOSSecond.setBounds(390,170,245,20);
        FeeSecond.setBounds(645,170,135,20);
        DateOfTravel.setBounds(10,236,230,20);
        Day.setBounds(10,256,156,20);
        Month.setBounds(176,256,156,20);
        Year.setBounds(342,256,156,20);
        RegisterTrain.setBounds(510,270,250,35);
                
        TrainNumField.setBounds(10,119,120,30);
        TrainNameField.setBounds(140,119,200,30);
        BoardingField.setBounds(350,119,210,30);
        DestinationField.setBounds(570,119,210,30);
        NOSFirstField.setBounds(10,189,225,30);
        FeeFirstField.setBounds(245,189,120,30);
        NOSSecondField.setBounds(390,189,245,30);
        FeeSecondField.setBounds(645,189,115,30);
        DayField.setBounds(10,275,156,30);
        MonthField.setBounds(176,275,156,30);
        YearField.setBounds(342,275,156,30);
        
        ViewTrainsPane.setBounds(0,150,785,350);
        SearchTrains.setBounds(70,80,500,20);
        SearchTrainField.setBounds(70,99,500,40);
        SearchTrainsButton.setBounds(575,99,200,40);
    }
    
    private void RemoveBorder() {
        LogoutButton.setBorder(createEmptyBorder(0, 0, 0, 0));
        SearchTrainsButton.setBorder(createEmptyBorder(0, 0, 0, 0));
        RegisterTrain.setBorder(createEmptyBorder(0, 0, 0, 0));
        ButtonRegister.setBorder(createEmptyBorder(0, 0, 0, 0));
        AddTrains.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        ViewPassengers.setBorder(createMatteBorder(1, 0, 1, 0, new Color(80,81,88)));
        ViewTrains.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        ApproveUsers.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        ApproveButton.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        
        ApproveUserSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UserFirstNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UserMiddleNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UserLastNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UserSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        SearchTrainField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        
        TrainNumField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        TrainNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        BoardingField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        DestinationField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        NOSFirstField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        FeeFirstField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        NOSSecondField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        FeeSecondField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        DayField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        MonthField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        YearField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
    }
    
    private void AddComponentToFrame() {
        SidePanel.add(ViewTrains);
        SidePanel.add(ViewPassengers);
        SidePanel.add(AddTrains);
        SidePanel.add(LogoutButton);
        SidePanel.add(ApproveUsers);
        
        MainPanel.add(UserSSN);
        MainPanel.add(UserFirstName);
        MainPanel.add(UserMiddleName);
        MainPanel.add(UserLastName);
        MainPanel.add(UserSSNField);
        MainPanel.add(UserFirstNameField);
        MainPanel.add(UserMiddleNameField);
        MainPanel.add(UserLastNameField);
        MainPanel.add(ButtonRegister);
        MainPanel.add(ScrollPane);
        MainPanel.add(RRSLabel);
        MainPanel.add(NotificationLabel);
        
        AddTrainPanel.add(TrainNum);
        AddTrainPanel.add(TrainName);
        AddTrainPanel.add(Boarding);
        AddTrainPanel.add(Destination);
        AddTrainPanel.add(NOSFirst);
        AddTrainPanel.add(FeeFirst);
        AddTrainPanel.add(NOSSecond);
        AddTrainPanel.add(FeeSecond);
        AddTrainPanel.add(DateOfTravel);
        AddTrainPanel.add(Day);
        AddTrainPanel.add(Month);
        AddTrainPanel.add(Year);
        AddTrainPanel.add(RegisterTrain);
        
        AddTrainPanel.add(TrainNumField);
        AddTrainPanel.add(TrainNameField);
        AddTrainPanel.add(BoardingField);
        AddTrainPanel.add(DestinationField);
        AddTrainPanel.add(NOSFirstField);
        AddTrainPanel.add(FeeFirstField);
        AddTrainPanel.add(NOSSecondField);
        AddTrainPanel.add(FeeSecondField);
        AddTrainPanel.add(DayField);
        AddTrainPanel.add(MonthField);
        AddTrainPanel.add(YearField);
        
        ViewTrainInformationPanel.add(ViewTrainsPane);
        ViewTrainInformationPanel.add(SearchTrains);
        ViewTrainInformationPanel.add(SearchTrainField);
        ViewTrainInformationPanel.add(SearchTrainsButton);
        
        ApproveUsersPanel.add(ApproveUserSSN);
        ApproveUsersPanel.add(ApproveUserSSNField);
        ApproveUsersPanel.add(ApproveButton);
        ApproveUsersPanel.add(userImage);
        
        add(ApproveUsersPanel);
        add(ViewTrainInformationPanel);
        add(AddTrainPanel);
        add(SidePanel);
        add(MainPanel);
    }
}
