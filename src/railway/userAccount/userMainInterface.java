package railway.userAccount;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createMatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class userMainInterface extends JFrame {
    String LoggedUserSSN = "";
    String[] SeatsAvail = {};
    int NOSeatAvailable = 0, totalPendingFee = 0;
    
    JPanel SidePanel         = new JPanel();
    JPanel MainPanel         = new JPanel();
    JLabel userLabel         = new JLabel();
    JLabel RRSLabel          = new JLabel(" Railway Reservation System");
    JLabel NotificationLabel = new JLabel("Notification Label");
    JLabel NumberOfSeatLabel = new JLabel();
    JButton LogoutButton     = new JButton("Logout");
    
    //User Account Action Panels
    JPanel ReserveTrainPanel         = new JPanel();
    JPanel CancelReservationPanel    = new JPanel();
    JPanel UpdateReservationPanel    = new JPanel();
    JPanel ViewTrainInformationPanel = new JPanel();
    JPanel ViewMessagesPanel         = new JPanel();
    JPanel CancelPaymentPanel        = new JPanel();
    JPanel PayRequiredFeePanel       = new JPanel();
    
    //Account Acion Buttons
    JButton ReserveSeat          = new JButton("Reserve Seat      ");
    JButton ReserveTrain         = new JButton("Reseve Train      ");
    JButton CancelReservation    = new JButton("Cancel Reservation");
    JButton UpdateReservation    = new JButton("Update Reservation");
    JButton ViewTrainInformation = new JButton("View Trains       ");
    JButton ViewMessages         = new JButton("View Messages     ");
    JButton PayRequiredFee       = new JButton("Pay Required Fee  ");
    
    //ReserveSeat Components
    JLabel TrainNum           = new JLabel("Train Number");
    JLabel NOSeat             = new JLabel("NO of Seat");
    JLabel RSClass            = new JLabel("Class To Reserve");
    JTextField TrainNumField  = new JTextField();
    JComboBox<String> NOSeatField = new JComboBox<>(SeatsAvail);
    JButton ReserveSeatButton    = new JButton("Reserve Seat");
    ButtonGroup ClassGroup       = new ButtonGroup();
    JRadioButton First           = new JRadioButton("First");
    JRadioButton Second          = new JRadioButton("Second"); 
    
    //ReserveTrain Components
    JLabel PassengerName     = new JLabel("Passenger Name");
    JLabel PassengerAge      = new JLabel("Passenger Age");
    JLabel PassengerSSN      = new JLabel("Passenger SSN");
    JTextField PassNameField = new JTextField();
    JTextField PassAgeField  = new JTextField();
    JTextField PassSSNField  = new JTextField();
    JButton AddPassengerInfo = new JButton("Add Passenger");
    
    //CancelReservation Components
    JLabel PassengerNumber       = new JLabel("Passenger Ticket-NO");
    JTextField PassTicketNOField = new JTextField();
    JButton CancelPassenger      = new JButton("Cancel Passenger");
    
    //UpdateReservation Components
    JLabel UpdatePassTicket          = new JLabel("Passenger Ticket-NO");
    JLabel UpdatePassName            = new JLabel("Passenger Name");
    JLabel UpdatePassAge             = new JLabel("Passenger Age");
    JLabel UpdatePassSSN             = new JLabel("Passenger SSN");
    JTextField UpdatePassTicketField = new JTextField();
    JTextField UpdatePassNameField   = new JTextField();
    JTextField UpdatePassAgeField    = new JTextField();
    JTextField UpdatePassSSNField    = new JTextField();
    JButton UpdatePassengerInfo      = new JButton("Update Passenger");
    
    //PayRequiredFee Components
    JLabel CreditCard          = new JLabel("Credit Card Number");
    JLabel NameOnCard          = new JLabel("Name On Card");
    JLabel ExpiryDate          = new JLabel("Expires Date");
    JLabel CSV                 = new JLabel("CSV");
    JTextField CreditCardField = new JTextField();
    JTextField NameOnCardField = new JTextField();
    JTextField ExpiryDateField = new JTextField();
    JTextField CSVField        = new JTextField();
    JButton PayFeeButton       = new JButton("Process Payment");
    
    //ViewTrain Components
    JScrollPane ViewTrainsPane  = new JScrollPane();
    JTable ViewTrainTable       = new JTable();
    JLabel SearchTrains         = new JLabel("Search Trains......");
    JTextField SearchTrainField = new JTextField();
    JButton SearchTrainsButton  = new JButton("Search Trains");
    
    //ViewMessage Components
    JScrollPane ViewMessagesPane = new JScrollPane();
    JTable ViewMessagesTable     = new JTable();
    
    //View Passengers Table
    JScrollPane ViewPassengerPane = new JScrollPane();
    JTable ViewPassengersTable    = new JTable();
    
    userMainInterface(String UserSSN, String FullName) {
        LoggedUserSSN = UserSSN;
        setTitle("User Account Control Panel");
        setSize(1000,500);
        setLocationRelativeTo(null);
        setResizable(false);
        NotificationLabel.setVisible(false);
        NotificationLabel.setOpaque(true);
        NumberOfSeatLabel.setOpaque(true);
        
        userLabel.setSize(300,70);
        userLabel.setText("   " + FullName);
        
        ClassGroup.add(First);
        ClassGroup.add(Second);
        First.setSelected(true);
        
        setComponentLayout();
        setComponentBounds();
        setComponentBackground();
        setComponentForeground();
        setBorderType();
        removeFocus();
        componentActionListeners();
        setComponentFont();
        numberOnlyFields();
        setAllPanelsInvisible();
        MainPanel.setVisible(true);
        addComponentToFrame();
    }
    
    private void fetchNOSeat(String trainNum, String SeatClass) {
        NOSeatField.removeAllItems();
        Connection conn = DBConnect.getConnection();
        int availSeat = 0;
        if(conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet RS = stmt.executeQuery("SELECT * FROM traininfo WHERE trainNum=" + trainNum+ " and"
                                                + " NOSFirstClass");
                while(RS.next()) {
                    availSeat = RS.getInt("NOS" + SeatClass + "Class");
                    if(availSeat != 0) {
                        int j = 1;
                        for(int i=0; i< availSeat; i++) {
                            NOSeatField.addItem("" + j);
                            j++;
                        }
                        break;
                    }
                }
                
            } catch(Exception Ex){}
        }
    }
    
    private void listPassngers() {
        ViewPassengerPane.setViewportView(ViewPassengersTable);
        ViewPassengersTable.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"Ticket Number","Passenger Name"}
        ));
        
        ViewPassengersTable.getColumnModel().getColumn(0).setMaxWidth(160);
        ViewPassengersTable.getColumnModel().getColumn(0).setMinWidth(160);
        ViewPassengersTable.getColumnModel().getColumn(1).setMaxWidth(150);
        ViewPassengersTable.getColumnModel().getColumn(1).setMinWidth(150);
        
        String query = "SELECT * FROM passengerdetail WHERE reserverSSN='" + LoggedUserSSN + "'";
        Connection conn = DBConnect.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DefaultTableModel model=(DefaultTableModel)ViewPassengersTable.getModel();

            Object[] row;
            int i = 0;
            while(rs.next()) {
                i++;
                row = new Object[2];
                row[0] = rs.getString(3);
                row[1] = rs.getString(6);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
        }
    }
    
    private void numberFields(JTextField Field) {
        Field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,Field);
            }
        });
    }
    
    private void numberOnlyFields() {
        numberFields(TrainNumField);
        numberFields(PassAgeField);
        numberFields(UpdatePassAgeField);
        numberFields(CreditCardField);
        numberFields(CSVField);
    }
    
    private void reserveSeat() throws SQLException {
        String TNF = TrainNumField.getText(), RSC , trainName = "", NOSFLD = (String)NOSeatField.getSelectedItem();
        int NOSF, DAY = 0, MONTH = 0, YEAR = 0, totalFee = 0;
        
        if(First.isSelected()) {
            RSC = "First";
        } else {
            RSC = "Second";
        }
        
        if(!TNF.equals("") && !NOSFLD.equals("")) {
            NOSF = Integer.parseInt((String)NOSeatField.getSelectedItem());
            Connection conn = DBConnect.getConnection();
            if(conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet RS2 = stmt.executeQuery("SELECT * FROM requiredfee WHERE userSSN='" + LoggedUserSSN + "' and NOSeat!=0");
                
                boolean isSeatAvail = false;
                int NOSEATS = 0;
                
                while(RS2.next()) {
                    isSeatAvail = true;
                    NOSEATS = RS2.getInt("NOSeat");
                }
                
                if(isSeatAvail == false) {
                    ResultSet RS1, RS = stmt.executeQuery("SELECT * FROM traininfo WHERE trainNum=" + TNF);

                    int NOSAvail = 0;
                    boolean isTrainAvail = false;
                    while(RS.next()) {
                        if("First".equals(RSC)) {
                            if(NOSF <= RS.getInt("NOSFirstClass")) {
                                NOSAvail = RS.getInt("NOSFirstClass");
                                totalFee = RS.getInt("FeeFirstClass") * NOSF;
                            } else {
                                NOSAvail = -1;
                            }
                        } else {
                            if(NOSF <= RS.getInt("NOSSecondClass")) {
                                NOSAvail = RS.getInt("NOSSecondClass");
                                totalFee = RS.getInt("FeeSecondClass") * NOSF;
                            } else {
                                NOSAvail = -1;
                            }
                        }

                        DAY = RS.getInt("Day");
                        MONTH = RS.getInt("Month");
                        YEAR = RS.getInt("Year");
                        trainName = RS.getString("trainName");
                        isTrainAvail = true;
                        break;
                    }

                    if(isTrainAvail == false) {
                        NotificationLabel.setBackground(new Color(240,81,82));
                        NotificationLabel.setText("          There is No Train Available With This Train Number");
                        NotificationLabel.setVisible(true);
                    } else {
                        if(NOSAvail == -1) {
                            NotificationLabel.setBackground(new Color(240,81,82));
                            NotificationLabel.setText("             There is No Seat Available For Your Need In This Train");
                            NotificationLabel.setVisible(true);
                        } else {
                            String Query = "INSERT INTO requiredfee VALUES('" + LoggedUserSSN + "'," + TNF + ",'" + RSC + "',"
                                        + totalFee + "," + NOSF + ",'" + trainName + "'," + DAY +", " + MONTH + "," + YEAR + ",'NotPayed')";
                            
                            stmt.executeUpdate(Query);
                            NOSAvail = NOSAvail - NOSF;
                            Query = "UPDATE trainInfo SET NOS" + RSC +"Class=" + NOSAvail + " WHERE trainNum=" + TNF;
                            stmt.executeUpdate(Query);
                            NotificationLabel.setBackground(Color.GREEN);
                            NotificationLabel.setText("         You Have Sucessfully Reserved Seat, Pay " + totalFee + " Birr To Reserve Train");
                            NotificationLabel.setVisible(true);
                        }
                    }
                } else {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("            Reserve Train For Available Seats First To Reserve another Seat");
                    NotificationLabel.setVisible(true);
                }
            } else {
                NotificationLabel.setBackground(new Color(240,81,82));
                NotificationLabel.setText("           Unable To Connect To The Server Please Check Your Connection");
                NotificationLabel.setVisible(true);
            }
        } else {
            NotificationLabel.setBackground(new Color(240,81,82));
            NotificationLabel.setText("         Please Enter All Informations Correctly Correctly");
            NotificationLabel.setVisible(true);
        }
    }
    
    private void setBorderType() {
        LogoutButton.setBorder(createEmptyBorder(0, 0, 0, 0));
        SearchTrainsButton.setBorder(createEmptyBorder(0, 0, 0, 0));
        SearchTrainField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        ReserveSeat.setBorder(createMatteBorder(1, 0, 1, 0, new Color(80,81,88)));
        ReserveTrain.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        CancelReservation.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        UpdateReservation.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        ViewTrainInformation.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        ViewMessages.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));  
        PayRequiredFee.setBorder(createMatteBorder(0, 0, 1, 0, new Color(80,81,88)));
        ReserveSeatButton.setBorder(createEmptyBorder(0,0,0,0));
        AddPassengerInfo.setBorder(createEmptyBorder(0,0,0,0));
        
        TrainNumField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        NOSeatField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        
        PassNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        PassAgeField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        PassSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        
        PassTicketNOField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        CancelPassenger.setBorder(createEmptyBorder(0,0,0,0));
        
        UpdatePassTicketField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UpdatePassNameField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UpdatePassAgeField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UpdatePassSSNField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        UpdatePassengerInfo.setBorder(createEmptyBorder(0,0,0,0));
        
        CreditCardField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        NameOnCardField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        ExpiryDateField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        CSVField.setBorder(createMatteBorder(0, 0, 4, 0, new Color(80,81,88)));
        PayFeeButton.setBorder(createEmptyBorder(0,0,0,0));
        First.setBorder(createEmptyBorder(0,0,0,0));
        Second.setBorder(createEmptyBorder(0,0,0,0));
    }
    
    private void setComponentLayout() {
        setLayout(null);
        SidePanel.setLayout(null);
        MainPanel.setLayout(null);
        ReserveTrainPanel.setLayout(null);
        UpdateReservationPanel.setLayout(null);
        CancelReservationPanel.setLayout(null);
        ViewMessagesPanel.setLayout(null);
        ViewTrainInformationPanel.setLayout(null);
        CancelReservationPanel.setLayout(null);
        PayRequiredFeePanel.setLayout(null);
    }
    
    private void setAllPanelsInvisible() {
        ReserveTrainPanel.setVisible(false);
        CancelReservationPanel.setVisible(false);
        UpdateReservationPanel.setVisible(false);
        ViewTrainInformationPanel.setVisible(false);
        ViewMessagesPanel.setVisible(false);
        CancelPaymentPanel.setVisible(false);
        MainPanel.setVisible(false);
        PayRequiredFeePanel.setVisible(false);
    }
    
    private void componentActionListeners() {
        First.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fetchNOSeat(TrainNumField.getText(),"First");
            }
        });
        
        Second.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fetchNOSeat(TrainNumField.getText(),"Second");
            }
        });
        TrainNumField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent Evt) {
                String RSC;
                if(First.isSelected()) {
                    RSC = "First";
                } else {
                    RSC = "Second";
                }
                fetchNOSeat(TrainNumField.getText(),RSC);
            }
        });
        
        ViewPassengersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Evt) {
                int i = ViewPassengersTable.getSelectedRow();

                TableModel model = ViewPassengersTable.getModel();
                PassTicketNOField.setText((String) model.getValueAt(i, 0));
                UpdatePassTicketField.setText((String) model.getValueAt(i, 0));
            }
        });
        
        ReserveSeat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAllPanelsInvisible();
                EmptyAllTextFields();
                MainPanel.add(RRSLabel);
                MainPanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
                MainPanel.setVisible(true);
            }
        });
        
        ReserveTrain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NOSeatAvailable = 0;
                Connection conn = DBConnect.getConnection();
                setAllPanelsInvisible();
                EmptyAllTextFields();
                ReserveTrainPanel.add(RRSLabel);
                ReserveTrainPanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
                ReserveTrainPanel.setVisible(true);
                        
                if(conn == null) {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("    We Are Not Able To Connect To The Server");
                    NotificationLabel.setVisible(true);
                    NOSeatAvailable = -1;
                } else {
                    try {
                        Statement stmt = conn.createStatement();
                        String Query = "SELECT * FROM requiredfee WHERE userSSN='" + LoggedUserSSN + "'";
                        ResultSet RS = stmt.executeQuery(Query);
                        
                        String paymentState = "";
                        int totalFee = 0;
                        while(RS.next()) {
                            if(RS.getInt("NOSeat") != 0) {
                                if(RS.getString("paymentState").equals("Payed")) {
                                    NumberOfSeatLabel.setText("   You Have " + RS.getInt("NOSeat") + " Seat(s) Available");
                                    NOSeatAvailable = RS.getInt("NOSeat");
                                } else {
                                    totalFee = RS.getInt("totalFee");
                                    paymentState = RS.getString("paymentState");
                                }
                                break;
                            }
                        }
                        
                        if(NOSeatAvailable == 0) {
                            NumberOfSeatLabel.setText("   There is No Seat Available To Reserve");
                            if(paymentState.equals("NotPayed")) {
                                NotificationLabel.setBackground(Color.GREEN);
                                NotificationLabel.setText("     You Have " + totalFee + " Birr Pending Payment, Pay First To Reserve");
                                NotificationLabel.setVisible(true);
                            }
                        }
                    } catch(Exception excp){}
                }
            }
        });
        
        CancelReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                setAllPanelsInvisible();
                EmptyAllTextFields();
                CancelReservationPanel.add(RRSLabel);
                CancelReservationPanel.add(NotificationLabel);
                CancelReservationPanel.add(ViewPassengerPane);
                ViewPassengerPane.setVisible(true);
                NotificationLabel.setVisible(false);
                CancelReservationPanel.setVisible(true); 
                listPassngers();
            }
        });
        
        UpdateReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                setAllPanelsInvisible();
                EmptyAllTextFields();
                UpdateReservationPanel.add(RRSLabel);
                UpdateReservationPanel.add(NotificationLabel);
                UpdateReservationPanel.add(ViewPassengerPane);
                ViewPassengerPane.setVisible(true);
                NotificationLabel.setVisible(false);
                UpdateReservationPanel.setVisible(true); 
                listPassngers();
            }
        });
        
        PayRequiredFee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setAllPanelsInvisible();
                EmptyAllTextFields();
                PayRequiredFeePanel.add(RRSLabel);
                PayRequiredFeePanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
                PayRequiredFeePanel.setVisible(true); 
                
                Connection conn = DBConnect.getConnection();
                if(conn == null) {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("    We Are Not Able To Connect To The Server Check Your Connection");
                    NotificationLabel.setVisible(true);
                    NOSeatAvailable = -1;
                } else {
                    try {
                        Statement stmt = conn.createStatement();
                        String Query = "SELECT * FROM requiredfee WHERE userSSN='" + LoggedUserSSN + "'";
                        ResultSet RS = stmt.executeQuery(Query);
                        
                        boolean isAvailable = false;
                        totalPendingFee = 0;
                        while(RS.next()) {
                            if(RS.getString("paymentState").equals("NotPayed")) {
                                NotificationLabel.setBackground(Color.GREEN);
                                NotificationLabel.setText("     You Have " + RS.getInt("totalFee") + " Birr Pending Payment, Enter Your Credit Card Information To Pay");
                                NotificationLabel.setVisible(true);
                                isAvailable = true;
                                totalPendingFee = totalPendingFee + RS.getInt("totalFee");
                            }
                        }
                        
                        if(isAvailable == false) {
                            NotificationLabel.setBackground(Color.GREEN);
                            NotificationLabel.setText("      There is No Pending Payment Check Back After Reserving Seat");
                            NotificationLabel.setVisible(true);
                        }
                    } catch(Exception excp){}
                }
            }
        });
        
        ViewTrainInformation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                setAllPanelsInvisible();
                EmptyAllTextFields();
                ViewTrainInformationPanel.add(RRSLabel);
                ViewTrainInformationPanel.add(NotificationLabel);
                NotificationLabel.setVisible(false);
                ViewTrainInformationPanel.setVisible(true);
                ViewTrains("");
            }
        });
        
        AddPassengerInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                try {
                    AddPassengerInfo();
                } catch (SQLException ex) {}
            }
        });
        
        PassAgeField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,PassAgeField);
            }
        });
        
        CreditCardField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent Evt) {
                KeyTypedEvent(Evt);
            }
            
            public void keyReleased(KeyEvent Evt) {
                KeyReleased(Evt,CreditCardField);
                String CCF = CreditCardField.getText();
                if(CCF.length() > 16) {
                    CreditCardField.setText("");
                    for(int i=0; i<16; i++) {
                        CreditCardField.setText(CreditCardField.getText() + CCF.charAt(i));
                    }
                }
            }
        });
        
        ReserveSeatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                try {
                    reserveSeat();
                } catch (SQLException ex) {}
            }
        });
        
        CancelPassenger.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                try {
                    CancelPassenger();
                    listPassngers();
                } catch (SQLException ex) {}
            }
        });
        
        UpdatePassengerInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                try {
                    UpdatePassengerInfo();
                    listPassngers();
                } catch (SQLException ex) {}
            }
        });
        
        PayFeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                try {
                    PayFeeButton();
                } catch (SQLException ex) {}
            }
        });
        
        ViewMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                setAllPanelsInvisible();
                EmptyAllTextFields();
                ViewMessagesPanel.add(RRSLabel);
                ViewMessagesPanel.setVisible(true);
                viewMessages();
            }
        });
        
        LogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Evt) {
                deleteSessionFile("","","");
                new userLoginInterface().setVisible(true);
            }
        });
        
        SearchTrainField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent Evt) {
                ViewTrains(SearchTrainField.getText());
            }
        });
    }
    
    public void deleteSessionFile(String LoggedUserSSN, String LoggedUserPass, String LoggedUserFullName){
        File RRSDir = new File("C:/programData/RRS/UserSessionData.bin");
        RRSDir.delete();

        this.setVisible(false);
    }

    private void EmptyAllTextFields() {
        NOSeatField.removeAllItems();
        TrainNumField.setText("");
        PassNameField.setText("");
        PassAgeField.setText("");
        PassSSNField.setText("");
        PassTicketNOField.setText("");
        UpdatePassTicketField.setText("");
        UpdatePassNameField.setText("");
        UpdatePassAgeField.setText("");
        UpdatePassSSNField.setText("");
        CreditCardField.setText("");
        NameOnCardField.setText("");
        ExpiryDateField.setText("");
        CSVField.setText("");
        SearchTrainField.setText("");
    }
    
    private void viewMessages() {
        ViewMessagesPane.setViewportView(ViewMessagesTable);
        ViewMessagesTable.setEnabled(false);
        ViewMessagesTable.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"Passenger Ticket-NO","Passenger Name","Age","TR Number","Reserved Class"}
        ));
        
        ViewMessagesTable.getColumnModel().getColumn(0).setMaxWidth(200);
        ViewMessagesTable.getColumnModel().getColumn(0).setMinWidth(200);
        ViewMessagesTable.getColumnModel().getColumn(1).setMaxWidth(200);
        ViewMessagesTable.getColumnModel().getColumn(1).setMinWidth(200);
        ViewMessagesTable.getColumnModel().getColumn(2).setMaxWidth(60);
        ViewMessagesTable.getColumnModel().getColumn(2).setMinWidth(60);
        ViewMessagesTable.getColumnModel().getColumn(3).setMaxWidth(100);
        ViewMessagesTable.getColumnModel().getColumn(3).setMinWidth(100);
        ViewMessagesTable.getColumnModel().getColumn(4).setMaxWidth(183);
        ViewMessagesTable.getColumnModel().getColumn(4).setMinWidth(183);
        
        String query = "SELECT * FROM passengerdetail WHERE reserverSSN='" + LoggedUserSSN + "'";
        Connection conn = DBConnect.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DefaultTableModel model=(DefaultTableModel)ViewMessagesTable.getModel();

            Object[] row;
            while(rs.next()) {
                row = new Object[5];
                row[0] = rs.getString(3);
                row[1] = rs.getString(6);
                row[2] = rs.getInt(7);
                row[3] = rs.getInt(5);
                row[4] = rs.getString(4);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
        }
    }
    
    private void ViewTrains(String QRY) {
        ViewTrainsPane.setViewportView(ViewTrainTable);
        ViewTrainTable.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"TR Num","BD Point","DS Point","First","Fee","Second","Fee","Day","Month","Year"}
        ));
        
        ViewTrainTable.getColumnModel().getColumn(0).setMaxWidth(60);
        ViewTrainTable.getColumnModel().getColumn(0).setMinWidth(70);
        ViewTrainTable.getColumnModel().getColumn(1).setMaxWidth(150);
        ViewTrainTable.getColumnModel().getColumn(1).setMinWidth(150);
        ViewTrainTable.getColumnModel().getColumn(2).setMaxWidth(145);
        ViewTrainTable.getColumnModel().getColumn(2).setMinWidth(145);
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
        ViewTrainTable.getColumnModel().getColumn(9).setMaxWidth(55);
        ViewTrainTable.getColumnModel().getColumn(9).setMinWidth(55);
        
        ViewTrainTable.setEnabled(false);
        String query = null;
        
        if(QRY.equals("")) {
            query = "SELECT * FROM traininfo";
        } else {
            query = "SELECT * FROM traininfo WHERE BPoint='" + QRY + "' or DPoint='" + QRY + "'";
        }
        
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
    
    private void AddPassengerInfo() throws SQLException {
        if(NOSeatAvailable == -1) {
            NotificationLabel.setBackground(new Color(240,81,82));
            NumberOfSeatLabel.setText("   Unable To Connect To The Server");
            NotificationLabel.setVisible(false);
        } else if(NOSeatAvailable == 0) {
            NotificationLabel.setBackground(Color.GREEN);
            NumberOfSeatLabel.setText("   There is No Seat Available To Reserve");
            NotificationLabel.setText("  No More Seat Available To Reserve At This Time");
            NotificationLabel.setVisible(true);
        } else {
            String PN = PassNameField.getText(), PSSN = PassSSNField.getText(), PAGE = PassAgeField.getText(), reservedClass = null;
            int trainNum = 0, MONTH = 0, DAY = 0, YEAR = 0;
            String PassengerTicketNO = "";
            boolean isReg = false;

            if(!PN.equals("") && !PSSN.equals("") && !PAGE.equals("")) {
                Connection conn = DBConnect.getConnection();
                if(conn != null) {
                    Statement stmt = conn.createStatement();
                    ResultSet RS,RS1;
                    String QRY = "SELECT * FROM requiredfee WHERE userSSN='" + LoggedUserSSN + "' and paymentState='Payed'";
                    RS = stmt.executeQuery(QRY);

                    while(RS.next()) {
                        trainNum = RS.getInt("trainNum");
                        reservedClass = RS.getString("reservedClass");
                        DAY = RS.getInt("Day");
                        MONTH = RS.getInt("Month");
                        YEAR = RS.getInt("Year");
                        break;
                    }
                    
                    PassengerTicketNO = trainNum + PSSN + DAY +MONTH + YEAR;
                    String QRY1 = "SELECT * FROM passengerdetail WHERE passengerTicketNO='" + PassengerTicketNO + "'";
                    RS1 = stmt.executeQuery(QRY1);
                    
                    while(RS1.next()) {
                        isReg = true;
                    }
                    
                    if(isReg == false) {
                        NOSeatAvailable = NOSeatAvailable - 1;
                        String Query = "INSERT INTO passengerDetail VALUES('" + LoggedUserSSN + "','" + PSSN + "',"
                                + "'" + PassengerTicketNO + "','" + reservedClass + "'," + trainNum + ",'" + PN + "'," + PAGE + ")";
                        String Query2 = "UPDATE requiredfee SET NOSeat=" + NOSeatAvailable + " WHERE userSSN='" + LoggedUserSSN + "' and "
                                + "Day=" + DAY + " and Month=" + MONTH + " and Year=" + YEAR;

                        stmt.executeUpdate(Query);
                        stmt.executeUpdate(Query2);
                        
                        if(NOSeatAvailable == 0){
                            Query = "UPDATE requiredfee SET paymentState='SeatsFinished' WHERE NOSeat=0";
                            stmt.executeUpdate(Query);
                        }
                        NumberOfSeatLabel.setText("   You Have " + NOSeatAvailable + " Seat(s) Available");
                        EmptyAllTextFields();
                    } else {
                        NotificationLabel.setBackground(new Color(240,81,82));
                        NotificationLabel.setText("           This User is Already Registered With This Train");
                        NotificationLabel.setVisible(true);
                    }
                } else {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("           Unable To Connect To The Server Please Check Your Connection");
                    NotificationLabel.setVisible(true);
                }
            } else { 
                NotificationLabel.setBackground(new Color(240,81,82));
                NotificationLabel.setText("         Please Fill All Information Correctly");
                NotificationLabel.setVisible(true);
            }
        }
    }
    
    private void CancelPassenger() throws SQLException {
        String PTN = PassTicketNOField.getText(), PName = "", TRNum = "";
        int NOSEAT = 0, MONTH = 0, DAY = 0, YEAR = 0;
        boolean isAvail = false;
        if(!PTN.equals("")) {
            Connection conn = DBConnect.getConnection();
            if(conn == null) {
                NotificationLabel.setBackground(new Color(240,81,82));
                NotificationLabel.setText("        Unable To Connect To The Server Try Again");
                NotificationLabel.setVisible(true);
            } else {
                Statement stmt = conn.createStatement();
                ResultSet RS, RS1;
                String Query = "SELECT * FROM passengerdetail WHERE passengerTicketNO='" + PTN + "' and reserverSSN='" + LoggedUserSSN + "'";
                RS = stmt.executeQuery(Query);
                while(RS.next()) {
                    isAvail = true;
                    PName = RS.getString("passengerName");
                    TRNum = RS.getString("trainNum");
                }
                
                if(isAvail == true) {
                    NotificationLabel.setVisible(false);
                    if(JOptionPane.showConfirmDialog(null,"Do You Want To Cancel Reservation For " + PName) == 0) {
                        Query = "SELECT * FROM requiredfee WHERE userSSN='" + LoggedUserSSN + "' and trainNum='" + TRNum + "'";
                        RS1 = stmt.executeQuery(Query);
                        
                        while(RS1.next()) {
                            DAY = RS1.getInt("Day");
                            MONTH = RS1.getInt("Month");
                            YEAR = RS1.getInt("Year");
                            NOSEAT = RS1.getInt("NOSeat") + 1;
                        }
                        Query = "DELETE FROM passengerdetail WHERE passengerTicketNO='" + PTN + "' and reserverSSN='" + LoggedUserSSN +"'";
                        stmt.executeUpdate(Query);
                    
                        Query = "UPDATE requiredfee SET NOSeat='" + NOSEAT + "', paymentState='Payed' WHERE userSSN='" + LoggedUserSSN + "' and "
                                + " Month=" + MONTH + " and Day=" + DAY + " and Year=" + YEAR;

                        stmt.executeUpdate(Query);
                        NotificationLabel.setBackground(new Color(240,81,82));
                        NotificationLabel.setText("      Reservation For " + PName + " is Sucessfully Canceled");
                        NotificationLabel.setVisible(true);
                        EmptyAllTextFields();
                    }
                } else {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("           Invalid Ticket Number! Unable To Find User");
                    NotificationLabel.setVisible(true);
                }
            }
        } else {
            NotificationLabel.setBackground(new Color(240,81,82));
            NotificationLabel.setText("        Enter The Passenger Ticket Number Correctly");
            NotificationLabel.setVisible(true);
        }
    }
    
    private void UpdatePassengerInfo() throws SQLException {
        String PN = UpdatePassNameField.getText(), PSSN = UpdatePassSSNField.getText(), PAGE = UpdatePassAgeField.getText();
        int MONTH = 0, DAY = 0, YEAR = 0,trainNum = 0, NOSeat = 0;
        String PTNO = UpdatePassTicketField.getText(), PassengerTicketNO;
        boolean isAvail = false;
        
        if(!PN.equals("") && !PSSN.equals("") && !PAGE.equals("") && !PTNO.equals("")) {
            Connection conn = DBConnect.getConnection();
            if(conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet RS, RS1;
                String Query = "SELECT * FROM passengerdetail WHERE reserverSSN='" + LoggedUserSSN + "' and passengerTicketNO='" + PTNO + "'";
                RS = stmt.executeQuery(Query);
                while(RS.next()) {
                    isAvail = true;
                }
                
                if(isAvail == true) {
                    Query = "SELECT * FROM requiredfee WHERE userSSN='" + LoggedUserSSN + "'";
                    RS1 = stmt.executeQuery(Query);
                    while(RS1.next()) {
                        trainNum = RS1.getInt("trainNum");
                        DAY = RS1.getInt("Day");
                        MONTH = RS1.getInt("Month");
                        YEAR = RS1.getInt("Year");
                        NOSeat = RS1.getInt("NOSeat") + 1;
                    }
                    
                    PassengerTicketNO = trainNum + PSSN + DAY +MONTH + YEAR;
                    Query = "UPDATE passengerdetail SET passengerTicketNO='" + PassengerTicketNO + "', userSSN='" + PSSN + "',"
                            + " passengerName='" + PN + "', passengerAge=" + PAGE + " WHERE passengerTicketNO='" + PTNO + "'";
                    
                    stmt.executeUpdate(Query);
                    NotificationLabel.setBackground(Color.GREEN);
                    NotificationLabel.setText("         Reservation Detail Sucessfully Updated For TicketNO(" + PTNO + ")");
                    NotificationLabel.setVisible(true);
                    EmptyAllTextFields();
                } else {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("           Unable To Find Passenger Please Enter Ticket Number Correctly");
                    NotificationLabel.setVisible(true);
                }
            
            } else {
                NotificationLabel.setBackground(new Color(240,81,82));
                NotificationLabel.setText("           Unable To Connect To The Server Please Check Your Connection");
                NotificationLabel.setVisible(true);
            }
        } else {
            NotificationLabel.setBackground(new Color(240,81,82));
            NotificationLabel.setText("         Please Fill All Information Correctly");
            NotificationLabel.setVisible(true);
        }
    }
    
    private void PayFeeButton() throws SQLException {
        String CCF = CreditCardField.getText(), NOCF = NameOnCardField.getText(), EXPF = ExpiryDateField.getText(),
               CSVF = CSVField.getText();
        int currentBalance = 0;
        boolean isAvail = false;
        
        if(totalPendingFee == 0) {
            NotificationLabel.setBackground(Color.GREEN);
            NotificationLabel.setText("      There is No Pending Payment Check Back After Reserving Seat");
            NotificationLabel.setVisible(true);
        } else if(!CCF.equals("") && CCF.length() == 16 && !NOCF.equals("") && !EXPF.equals("") && !CSVF.equals("") && CSVF.length() == 3) {
            Connection conn = DBConnect.getConnection();
            if(conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet RS, RS1;
                String Query = "SELECT * FROM creditcardinfo WHERE creditCardNO='" + CCF + "' and "
                               + "nameOnCard='" + NOCF + "' and expiryDate='" + EXPF + "' and CSV=" + CSVF;
                
                RS = stmt.executeQuery(Query);
                
                while(RS.next()) {
                    currentBalance = RS.getInt("currentBalance");
                    isAvail = true;
                }
                
                if(isAvail == true) {
                    if(currentBalance >= totalPendingFee) {
                        currentBalance = currentBalance - totalPendingFee;
                        Query = "UPDATE requiredfee SET paymentState='Payed' WHERE userSSN='" + LoggedUserSSN + "'";
                        stmt.executeUpdate(Query);
                        
                        Query = "UPDATE creditcardinfo SET currentBalance=" + currentBalance + " WHERE creditCardNO='" + CCF + "'";
                        stmt.executeUpdate(Query);
                        
                        NotificationLabel.setBackground(Color.GREEN);
                        NotificationLabel.setText("      Your Payment Has Been Sucessfully Processed Now You Can Reserve Train");
                        NotificationLabel.setVisible(true);
                        totalPendingFee = 0;
                    } else {
                        NotificationLabel.setBackground(new Color(240,81,82));
                        NotificationLabel.setText("            Insufficient Balance To Pay The Required Fee");
                        NotificationLabel.setVisible(true);
                    }
                } else {
                    NotificationLabel.setBackground(new Color(240,81,82));
                    NotificationLabel.setText("         Invalid Credit Card Information Please Check Your Information");
                    NotificationLabel.setVisible(true);
                }
            } else {
                NotificationLabel.setBackground(new Color(240,81,82));
                NotificationLabel.setText("           Unable To Connect To The Server Please Check Your Connection");
                NotificationLabel.setVisible(true);
            }
        } else {
            NotificationLabel.setBackground(new Color(240,81,82));
            NotificationLabel.setText("         Please Enter Your Credit Card Information Correctly");
            NotificationLabel.setVisible(true);
        }
    }
    
    private void removeFocus() {
        LogoutButton.setFocusPainted(false);
        ReserveSeat.setFocusPainted(false);
        ReserveSeatButton.setFocusPainted(false);
        ReserveTrain.setFocusPainted(false);
        CancelReservation.setFocusPainted(false);
        UpdateReservation.setFocusPainted(false);
        ViewTrainInformation.setFocusPainted(false);
        ViewMessages.setFocusPainted(false);    
        PayRequiredFee.setFocusPainted(false);
        AddPassengerInfo.setFocusPainted(false);
        CancelPassenger.setFocusPainted(false);
        UpdatePassengerInfo.setFocusPainted(false);
        PayFeeButton.setFocusPainted(false);
        SearchTrainsButton.setFocusPainted(false);
        First.setFocusPainted(false);
        Second.setFocusPainted(false);
    }
    
    private void setComponentFont() {
        userLabel.setFont(new Font("Byom", 3, 15));
        ViewTrainTable.getTableHeader().setFont(new Font("Byom", 1, 13));
        ViewMessagesTable.getTableHeader().setFont(new Font("Byom", 3, 15));
        ViewPassengersTable.getTableHeader().setFont(new Font("Byom", 3, 15));
        SearchTrainsButton.setFont(new Font("Byom", 3, 15));
        SearchTrainField.setFont(new Font("Nova Square", 4, 18));
        SearchTrains.setFont(new Font("Nova Square", 4, 27));
        PassengerName.setFont(new Font("Nova Square", 4, 18));
        PassengerAge.setFont(new Font("Nova Square", 4, 18));
        PassengerSSN.setFont(new Font("Nova Square", 4, 18));
        PassNameField.setFont(new Font("Nova Square", 2, 18));
        PassAgeField.setFont(new Font("Nova Square", 2, 18));
        PassSSNField.setFont(new Font("Nova Square", 2, 18));
        AddPassengerInfo.setFont(new Font("Byom", 3, 15));
        UpdatePassengerInfo.setFont(new Font("Byom", 3, 15));
        LogoutButton.setFont(new Font("Byom", 3, 15));
        
        First.setFont(new Font("Nova Square", 2, 18));
        Second.setFont(new Font("Nova Square", 2, 18));
                
        NumberOfSeatLabel.setFont(new Font("Nova Square", 4, 18));
        NotificationLabel.setFont(new Font("Nova Square", 4, 18));
        ReserveSeat.setFont(new Font("Nova Square", 4, 18));
        ReserveTrain.setFont(new Font("Nova Square", 4, 18));
        CancelReservation.setFont(new Font("Nova Square", 4, 18));
        UpdateReservation.setFont(new Font("Nova Square", 4, 18));
        ViewTrainInformation.setFont(new Font("Nova Square", 4, 18));
        ViewMessages.setFont(new Font("Nova Square", 4, 18));   
        PayRequiredFee.setFont(new Font("Nova Square", 4, 18));
        RRSLabel.setFont(new Font("TOYZARUX", 3, 28));
        
        TrainNum.setFont(new Font("Nova Square", 4, 18));
        NOSeat.setFont(new Font("Nova Square", 4, 18));
        RSClass.setFont(new Font("Nova Square", 4, 18));
        TrainNumField.setFont(new Font("Consolas Bold Italic", 3, 18));
        NOSeatField.setFont(new Font("Consolas Bold Italic", 3, 18));
        ReserveSeatButton.setFont(new Font("Byom", 3, 15));
        
        PassengerNumber.setFont(new Font("Nova Square", 4, 18));
        PassTicketNOField.setFont(new Font("Consolas Bold Italic", 3, 18));
        CancelPassenger.setFont(new Font("Byom", 3, 15));
        
        UpdatePassTicket.setFont(new Font("Nova Square", 4, 18));
        UpdatePassName.setFont(new Font("Nova Square", 4, 18));
        UpdatePassAge.setFont(new Font("Nova Square", 4, 18));
        UpdatePassSSN.setFont(new Font("Nova Square", 4, 18));
        UpdatePassTicketField.setFont(new Font("Nova Square", 4, 18));
        UpdatePassNameField.setFont(new Font("Nova Square", 4, 18));
        UpdatePassAgeField.setFont(new Font("Nova Square", 4, 18));
        UpdatePassSSNField.setFont(new Font("Nova Square", 4, 18));
        
        CreditCard.setFont(new Font("Nova Square", 4, 18));
        NameOnCard.setFont(new Font("Nova Square", 4, 18));
        ExpiryDate.setFont(new Font("Nova Square", 4, 18));
        CSV.setFont(new Font("Nova Square", 4, 18));
        CreditCardField.setFont(new Font("Nova Square", 4, 18));
        NameOnCardField.setFont(new Font("Nova Square", 4, 18));
        ExpiryDateField.setFont(new Font("Nova Square", 4, 18));
        CSVField.setFont(new Font("Nova Square", 4, 18));
        PayFeeButton.setFont(new Font("Byom", 3, 15));
    }
    
    private void setComponentBounds() {
        SearchTrains.setBounds(70,80,500,20);
        SearchTrainField.setBounds(70,99,500,40);
        SearchTrainsButton.setBounds(575,99,200,40);
        NumberOfSeatLabel.setBounds(370,150,370,50);
        NotificationLabel.setBounds(0,70,750,50);
        RRSLabel.setBounds(0,0,700,70);
        SidePanel.setBounds(0,0,250,500);
        MainPanel.setBounds(250,0,750,500);
        PayRequiredFeePanel.setBounds(250,0,750,500);
        ReserveTrainPanel.setBounds(250,0,750,500);
        UpdateReservationPanel.setBounds(250,0,750,500);
        CancelReservationPanel.setBounds(250,0,750,500);
        ViewTrainInformationPanel.setBounds(250,0,750,500);
        ViewMessagesPanel.setBounds(250,0,750,500);
        CancelPaymentPanel.setBounds(250,0,750,500);
        
        LogoutButton.setBounds(150,0,100,30);
        userLabel.setBounds(0,0,200,70);
        ReserveSeat.setBounds(0,120,300,50);
        ReserveTrain.setBounds(0,170,300,50);
        CancelReservation.setBounds(0,220,300,50);
        UpdateReservation.setBounds(0,270,300,50);
        ViewTrainInformation.setBounds(0,320,300,50);
        ViewMessages.setBounds(0,370,300,50);
        ViewPassengerPane.setBounds(370,150,310,287);
        PayRequiredFee.setBounds(0,420,300,50);
        RSClass.setBounds(50,150,310,20);
        First.setBounds(50,169,70,30);
        Second.setBounds(130,169,85,30);
        TrainNum.setBounds(50,209,150,20);
        NOSeat.setBounds(210,209,150,20);
        TrainNumField.setBounds(50,228,150,30);
        NOSeatField.setBounds(210,228,150,30);
        ReserveSeatButton.setBounds(190,268,170,30);
        
        PassengerSSN.setBounds(50,150,310,20);
        PassengerName.setBounds(50,209,310,20);
        PassengerAge.setBounds(50,268,310,20);
        PassSSNField.setBounds(50,169,310,30);
        PassNameField.setBounds(50,228,310,30);
        PassAgeField.setBounds(50,287,150,30);
        AddPassengerInfo.setBounds(210,287,150,30);
        
        PassengerNumber.setBounds(50,150,310,20);
        PassTicketNOField.setBounds(50,169,310,30);
        CancelPassenger.setBounds(210,209,150,30);
        
        UpdatePassTicket.setBounds(50,150,310,20);
        UpdatePassSSN.setBounds(50,209,310,20);
        UpdatePassName.setBounds(50,268,310,20);
        UpdatePassAge.setBounds(50,327,310,20);
        UpdatePassTicketField.setBounds(50,169,310,30);
        UpdatePassSSNField.setBounds(50,228,310,30);
        UpdatePassNameField.setBounds(50,287,310,30);
        UpdatePassAgeField.setBounds(50,346,150,30);
        UpdatePassengerInfo.setBounds(210,346,150,30);
        
        CreditCard.setBounds(50,150,310,20);
        NameOnCard.setBounds(50,209,310,20);
        ExpiryDate.setBounds(50,268,150,20);
        CSV.setBounds(210,268,310,20);
        CreditCardField.setBounds(50,169,310,30);
        NameOnCardField.setBounds(50,228,310,30);
        ExpiryDateField.setBounds(50,287,150,30);
        CSVField.setBounds(210,287,150,30);
        PayFeeButton.setBounds(210,346,150,30);
        
        ViewTrainsPane.setBounds(0,150,750,350);
        ViewMessagesPane.setBounds(0,70,750,430);
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
    
    private void setComponentBackground() {
        ViewMessagesPane.setBackground(new Color(34,35,41));
        ViewMessagesTable.setBackground(Color.LIGHT_GRAY);
        SearchTrainsButton.setBackground(new Color(89,179,105));
        SearchTrains.setBackground(new Color(200,200,70));
        SearchTrainField.setBackground(new Color(34,35,41));
        NumberOfSeatLabel.setBackground(new Color(89,220,105));
        NotificationLabel.setBackground(new Color(240,81,82));
        SidePanel.setBackground(new Color(52,52,61));
        MainPanel.setBackground(new Color(34,35,41));
        ReserveSeat.setBackground(new Color(52,52,61));
        ReserveTrain.setBackground(new Color(52,52,61));
        CancelReservation.setBackground(new Color(52,52,61));
        UpdateReservation.setBackground(new Color(52,52,61));
        ViewTrainInformation.setBackground(new Color(52,52,61));
        ViewMessages.setBackground(new Color(52,52,61)); 
        PayRequiredFee.setBackground(new Color(52,52,61));
        
        PayRequiredFeePanel.setBackground(new Color(34,35,41));
        ReserveTrainPanel.setBackground(new Color(34,35,41));
        CancelReservationPanel.setBackground(new Color(34,35,41));
        UpdateReservationPanel.setBackground(new Color(34,35,41));
        ViewTrainInformationPanel.setBackground(new Color(34,35,41));
        ViewMessagesPanel.setBackground(new Color(34,35,41));
        CancelPaymentPanel.setBackground(new Color(34,35,41));
        
        TrainNumField.setBackground(new Color(34,35,41));
        NOSeatField.setBackground(new Color(34,35,41));
        ReserveSeatButton.setBackground(new Color(89,179,105));
        
        PassNameField.setBackground(new Color(34,35,41));
        PassAgeField.setBackground(new Color(34,35,41));
        PassSSNField.setBackground(new Color(34,35,41));
        AddPassengerInfo.setBackground(new Color(89,179,105));
        LogoutButton.setBackground(new Color(89,179,105));
        
        PassTicketNOField.setBackground(new Color(34,35,41));
        CancelPassenger.setBackground(new Color(89,179,105));
        
        UpdatePassengerInfo.setBackground(new Color(89,179,105));
        UpdatePassTicketField.setBackground(new Color(34,35,41));
        UpdatePassNameField.setBackground(new Color(34,35,41));
        UpdatePassAgeField.setBackground(new Color(34,35,41));
        UpdatePassSSNField.setBackground(new Color(34,35,41));
        
        CreditCardField.setBackground(new Color(34,35,41));
        NameOnCardField.setBackground(new Color(34,35,41));
        ExpiryDateField.setBackground(new Color(34,35,41));
        CSVField.setBackground(new Color(34,35,41));
        PayFeeButton.setBackground(new Color(89,179,105));
        First.setBackground(new Color(34,35,41));
        Second.setBackground(new Color(34,35,41));
    }
    
    private void setComponentForeground() {
        ViewMessagesTable.setForeground(Color.WHITE);
        SearchTrainsButton.setForeground(Color.WHITE);
        SearchTrains.setForeground(new Color(240,81,82));
        SearchTrainField.setForeground(Color.WHITE);
        NumberOfSeatLabel.setForeground(Color.WHITE);
        RRSLabel.setForeground(new Color(240,81,82));
        userLabel.setForeground(Color.WHITE);
        ReserveSeat.setForeground(Color.WHITE);
        ReserveTrain.setForeground(Color.WHITE);
        CancelReservation.setForeground(Color.WHITE);
        UpdateReservation.setForeground(Color.WHITE);
        ViewTrainInformation.setForeground(Color.WHITE);
        ViewMessages.setForeground(Color.WHITE);    
        PayRequiredFee.setForeground(Color.WHITE);
        NotificationLabel.setForeground(Color.WHITE);
        
        TrainNum.setForeground(new Color(240,81,82));
        NOSeat.setForeground(new Color(240,81,82));
        RSClass.setForeground(new Color(240,81,82));
        TrainNumField.setForeground(Color.WHITE);
        NOSeatField.setForeground(Color.WHITE);
        
        LogoutButton.setForeground(Color.WHITE);
        PassengerName.setForeground(new Color(240,81,82));
        PassengerAge.setForeground(new Color(240,81,82));
        PassengerSSN.setForeground(new Color(240,81,82));
        PassNameField.setForeground(Color.WHITE);
        PassAgeField.setForeground(Color.WHITE);
        PassSSNField.setForeground(Color.WHITE);
        
        PassengerNumber.setForeground(new Color(240,81,82));
        PassTicketNOField.setForeground(Color.WHITE);
        
        UpdatePassTicket.setForeground(new Color(240,81,82));
        UpdatePassName.setForeground(new Color(240,81,82));
        UpdatePassAge.setForeground(new Color(240,81,82));
        UpdatePassSSN.setForeground(new Color(240,81,82));
        UpdatePassTicketField.setForeground(Color.WHITE);
        UpdatePassNameField.setForeground(Color.WHITE);
        UpdatePassAgeField.setForeground(Color.WHITE);
        UpdatePassSSNField.setForeground(Color.WHITE);
        CreditCard.setForeground(new Color(240,81,82));
        NameOnCard.setForeground(new Color(240,81,82));
        ExpiryDate.setForeground(new Color(240,81,82));
        CSV.setForeground(new Color(240,81,82));
        CreditCardField.setForeground(Color.WHITE);
        NameOnCardField.setForeground(Color.WHITE);
        ExpiryDateField.setForeground(Color.WHITE);
        CSVField.setForeground(Color.WHITE);
        First.setForeground(Color.WHITE);
        Second.setForeground(Color.WHITE);
    }
    
    private void addComponentToFrame() {
        SidePanel.add(LogoutButton);
        SidePanel.add(ReserveSeat);
        SidePanel.add(ReserveTrain);
        SidePanel.add(ViewMessages);
        SidePanel.add(UpdateReservation);
        SidePanel.add(ViewTrainInformation);
        SidePanel.add(CancelReservation);
        SidePanel.add(userLabel);
        SidePanel.add(PayRequiredFee);
        
        MainPanel.add(NotificationLabel);
        MainPanel.add(TrainNum);
        MainPanel.add(NOSeat);
        MainPanel.add(RSClass);
        MainPanel.add(TrainNumField);
        MainPanel.add(NOSeatField);
        MainPanel.add(ReserveSeatButton);
        MainPanel.add(RRSLabel);
        MainPanel.add(First);
        MainPanel.add(Second);
        
        ReserveTrainPanel.add(PassengerSSN);
        ReserveTrainPanel.add(PassengerName);
        ReserveTrainPanel.add(PassengerAge);
        ReserveTrainPanel.add(PassSSNField);
        ReserveTrainPanel.add(PassNameField);
        ReserveTrainPanel.add(PassAgeField);
        ReserveTrainPanel.add(AddPassengerInfo);
        ReserveTrainPanel.add(NumberOfSeatLabel);
        
        CancelReservationPanel.add(PassengerNumber);
        CancelReservationPanel.add(PassTicketNOField);
        CancelReservationPanel.add(CancelPassenger);
        
        UpdateReservationPanel.add(UpdatePassTicket);
        UpdateReservationPanel.add(UpdatePassName);
        UpdateReservationPanel.add(UpdatePassAge);
        UpdateReservationPanel.add(UpdatePassSSN);
        UpdateReservationPanel.add(UpdatePassTicketField);
        UpdateReservationPanel.add(UpdatePassNameField);
        UpdateReservationPanel.add(UpdatePassAgeField);
        UpdateReservationPanel.add(UpdatePassSSNField);
        UpdateReservationPanel.add(UpdatePassengerInfo);
        
        PayRequiredFeePanel.add(CreditCard);
        PayRequiredFeePanel.add(NameOnCard);
        PayRequiredFeePanel.add(ExpiryDate);
        PayRequiredFeePanel.add(CSV);
        PayRequiredFeePanel.add(CreditCardField);
        PayRequiredFeePanel.add(NameOnCardField);
        PayRequiredFeePanel.add(ExpiryDateField);
        PayRequiredFeePanel.add(CSVField);
        PayRequiredFeePanel.add(PayFeeButton);
        
        ViewTrainInformationPanel.add(ViewTrainsPane);
        ViewTrainInformationPanel.add(SearchTrains);
        ViewTrainInformationPanel.add(SearchTrainField);
        ViewTrainInformationPanel.add(SearchTrainsButton);
                
        ViewMessagesPanel.add(ViewMessagesPane);
        
        add(PayRequiredFeePanel);
        add(ReserveTrainPanel);
        add(CancelReservationPanel);
        add(UpdateReservationPanel);
        add(ViewTrainInformationPanel);
        add(CancelPaymentPanel);
        add(ViewMessagesPanel);
        add(SidePanel);
        add(MainPanel);
    }
}