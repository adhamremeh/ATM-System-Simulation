package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.border.Border;

public class jframe extends JFrame implements ActionListener{

    // main labels and text fields for SIGN IN panel
    JLabel Label_ID;
    JTextField Enter_ID;
    JLabel Label_PIN;
    JTextField Enter_PIN;
    JButton SignIN;
    
    // numbers array to simulate numbers panel in ATMs
    JButton[] Numbers = new JButton[10];
    
    JLabel welcome;
    
    // Booleans to choose which text field to write in with numbers panel
    boolean ID_selected = false;
    boolean PIN_selected = false;
    
    // mouse X and Y coordinates, we use them to move the main frame
    int mouseX;
    int mouseY;
    
    // frame full width and height
    int width = 855;
    int height = 600;
    
    // Array List to initialize Users from data base 
    // we use it to know the log-in attemp is a user or admin
    // and take the data row from it to have the full data 
    ArrayList<String> InitializeUser = new ArrayList<String>();
    
    // the jframe constructor, we use it just to initialize users data 
    jframe() throws FileNotFoundException
    {
        File file = new File("database.txt");
        Scanner filedata = new Scanner(file);

        while (filedata.hasNextLine()) 
        {
            InitializeUser.add(filedata.nextLine());
        }
    }

    public void main()
    {
        // initialize SIGN IN labels and text fields
        Label_ID = new JLabel("Enter your ID: ");
        Enter_ID = new JTextField(6);
        Label_PIN = new JLabel("Enter PIN: ");
        Enter_PIN = new JTextField(4);
        Label_ID.setFont(new Font("", Font.PLAIN, 13));
        Label_PIN.setFont(new Font("", Font.PLAIN, 13));
        SignIN = new JButton("SIGN IN");
        
        // main frame which contain all panels
        JFrame frame = new JFrame("ATM");
        frame.setLayout(null);
        frame.setUndecorated(true);
        
        // main panel to repaint and reuse for all purposes
        JPanel ATMPanel = new JPanel()
        {
            ///////////////////////////////////////////////////////////////////
            // all this part just to Add round corners to the ATM main panel //
            ///////////////////////////////////////////////////////////////////
            @Override
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(40,40);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
            
        }};
        
        ATMPanel.setLayout(null);
        ATMPanel.setOpaque(false);
        
        JLabel Background;
        ImageIcon background=new ImageIcon("ATMBackGround.png");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(width,300,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        Background=new JLabel(background);
        Background.setLayout(null);
        Background.setBounds(0,0,width,300);
        
        JLabel Background2;
        ImageIcon background2=new ImageIcon("ATMBackGround2.png");
        Image img2=background2.getImage();
        Image temp2=img2.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        background2=new ImageIcon(temp2);
        Background2=new JLabel(background2);
        Background2.setLayout(null);
        Background2.setBounds(0,0,width,height);

        // numbers panel to sign in frame and remove after sign in 
        JPanel NumbersPanel = new JPanel();
        GridLayout NumbersGrid = new GridLayout(4,4);
        NumbersPanel.setLayout(NumbersGrid);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK,3);
        NumbersPanel.setBorder(blackline);
        
        // Adding ATM Numbers and empty label in index 9 to set number 0 to index 10 without errors // 
        for (int i = 1; i < 10; i++)
        {
            // this final just for adding i iterator to the ID and PIN text fields strings
            final int ii = i;
            Numbers[i] = new JButton(Integer.toString(i));
            NumbersPanel.add(Numbers[i]);
            // initialize the numbers buttons action listeners
            Numbers[i].addActionListener(e -> {
                if (ID_selected)
                {
                    if (Enter_ID.getText().length() < 5)
                        Enter_ID.setText(Enter_ID.getText()+ii);
                }
                else if (PIN_selected)
                {
                    if (Enter_PIN.getText().length() < 4)
                        Enter_PIN.setText(Enter_PIN.getText()+ii);
                }
            });
        }
        JLabel empty= new JLabel();
        NumbersPanel.add(empty, 0, 9);
        ///////////////////////////////////////////////////////////////////////////////////////////
        /// initializing the 0 button after for loop because we want it in a certain grid index ///
        ///////////////////////////////////////////////////////////////////////////////////////////
        Numbers[0] = new JButton(Integer.toString(0));
        NumbersPanel.add(Numbers[0],0,10);
        Numbers[0].addActionListener(e -> {
            if (ID_selected)
            {
                if (Enter_ID.getText().length() < 5)
                    Enter_ID.setText(Enter_ID.getText()+Integer.toString(0));
            }
            else if (PIN_selected)
            {
                if (Enter_PIN.getText().length() < 4)
                    Enter_PIN.setText(Enter_PIN.getText()+Integer.toString(0));
            }
        });
        /////////////////////////////////////////////////////////////////////
       
        // ID and PIN text fields focus listeners >>> focus gained to obtain focus and lose focus, but we don't use focus lost 
        Enter_ID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ID_selected = true;
                PIN_selected = false;
            }
            @Override
            public void focusLost(FocusEvent e) {   
            }
        });
        Enter_PIN.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                PIN_selected = true;
                ID_selected = false;
            }
            @Override
            public void focusLost(FocusEvent e) {   
            }
        });
        /////////////////////// end of focus listeners ///////////////////////
        
        // adding components for SIGN-IN in ATMPanel
        ATMPanel.add(Label_ID);
        ATMPanel.add(Enter_ID);
        ATMPanel.add(Label_PIN);
        ATMPanel.add(Enter_PIN);
        ATMPanel.add(SignIN);
        ATMPanel.add(Background);

        // adding color to text labels >>> for reference
        Label_ID.setForeground(Color.white);
        Label_PIN.setForeground(Color.white);
        
        ATMPanel.setVisible(true);
        NumbersPanel.setVisible(true);
        
        //////////// set bounds for SIGN-IN panel /////////////
        Label_ID.setBounds(200, 20, 90, 50);
        Enter_ID.setBounds(350, 32, 100, 30);
        Label_PIN.setBounds(200, 80, 90, 50);
        Enter_PIN.setBounds(350, 92, 100, 30);
        SignIN.setBounds(350, 200, 80, 50);
        
        ///////////////////////////////////////////////////////
        Label_ID.setFont(new Font("", Font.PLAIN, 13));
        Label_PIN.setFont(new Font("", Font.PLAIN, 13));

        SignIN.setBackground(Color.decode("#F2F4F4"));
        SignIN.setForeground(Color.decode("#468092"));
        /////////////////////////////////////////////////////
        
        // Adding panels to frame and set their bounds >> ATMPanel (main panel) >> NumbersPanel (just for sign in)
        ATMPanel.setBounds(0, 0, width, 300);
        frame.getContentPane().add(ATMPanel);
        NumbersPanel.setBounds(width/3, 297, width/3, 200);
        frame.getContentPane().add(NumbersPanel);
        
        // here we set a to 0 to make the jframe transparent
        // because we have the ATMPanel as the main panel which contains all components
        frame.setBackground(new Color(0, 0, 0, 0));
        
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SignIN.addActionListener((ActionEvent e) -> {
            try {
                if (checkAccount(Enter_ID.getText(), Enter_PIN.getText()))
                {
                    StringTokenizer tokenizer;
                    
                    for (int i = 0; i < InitializeUser.size(); i++)
                    {
                        tokenizer = new StringTokenizer( InitializeUser.get(i) , "," );

                        if (tokenizer.nextToken().equals(Enter_ID.getText()))
                        {
                            tokenizer.nextToken();
                            welcome = new JLabel("Welcome " + tokenizer.nextToken());
                            welcome.setForeground(Color.white);
                            welcome.setFont(new Font("", Font.PLAIN, 14));

                            welcome.setBounds(350, 100, 150, 50);
                            break;
                        }
                    }
                    
                    encryptData();
                    
                    // remove the numbers panel because we don't want it after log in
                    frame.getContentPane().remove(NumbersPanel);
                    frame.repaint();
                    
                    // remove all components from ATMPanel, repaint it, set new bounds to fill the screen and add welcome label to it
                    ATMPanel.removeAll();
                    ATMPanel.repaint();
                    ATMPanel.setBounds(0, 0, width, height);
                    ATMPanel.add(welcome);
                    
                    JButton Button_logout;
                    Button_logout = new JButton("logout");
                    Button_logout.setBounds(width/2+280,500, 110, 50);
                    Button_logout.setBackground(Color.decode("#F2F4F4"));
                    Button_logout.setForeground(Color.decode("#468092"));
                    
                    // removing current components, resize ATMPanel, add back the numbers panel and add ATM components
                    // to make it like first panel
                    Button_logout.addActionListener(LogOutE -> {
                        ATMPanel.removeAll();
                        ATMPanel.repaint();
                        ATMPanel.setBounds(0, 0, width, 300);
                        
                        ATMPanel.add(Label_ID);
                        ATMPanel.add(Enter_ID);
                        ATMPanel.add(Label_PIN);
                        ATMPanel.add(Enter_PIN);
                        ATMPanel.add(SignIN);
                        ATMPanel.add(Background);

                        Enter_ID.setText("");
                        Enter_PIN.setText("");
                        
                        NumbersPanel.setBounds(width/3, 297, width/3, 200);
                        frame.getContentPane().add(NumbersPanel);
                    });
                    
                    ///////////////////////// Check if the id for an Admin or user ///////////////////////
                    if (Enter_ID.getText().equals("00001") && Enter_PIN.getText().equals("1234"))
                    {
                        // go here if admin and make a new object of Admin class
                        Admin admin = new Admin();
                        
                        JButton Button_addAccount;
                    	JButton Button_DeletAccount;
                    	JButton Button_ShowData;
                        JButton Button_editAccount;
                       
                    	Button_addAccount = new JButton("AddAccount");
                    	Button_DeletAccount = new JButton("DeletAccount");
                        Button_editAccount = new JButton("Update");
                    	Button_ShowData = new JButton("Show DataBase");
                        
                        // Labels and buttons for ADD account button >>> they will display after pressing add button
                        JLabel LabelADD_PIN = new JLabel("Enter PIN to your new account*: ");
                        JTextField EnterADD_PIN = new JTextField();
                        

                        JLabel LabelADD_NAME = new JLabel("Enter Name to your new account*: ");
                        JTextField EnterADD_NAME = new JTextField();
                        
                     

                        JLabel LabelADD_ADDRESS = new JLabel("Enter address to add*: ");
                        JTextField EnterADD_ADDRESS = new JTextField();

                        JLabel LabelADD_PHONE = new JLabel("Enter phone number to add*: ");
                        JTextField EnterADD_PHONE = new JTextField();

                        JLabel LabelADD_BALANCE = new JLabel("Enter initial Balance (Optional): ");
                        JTextField EnterADD_BALANCE = new JTextField();

                        JButton AddAccount = new JButton("ADD account");
                        
                        /////////////////////////////////////////////////////////////
                        
                        // Labels and button for delete account button >>> they will display after pressing delete button
                        JLabel LabelID_delete = new JLabel("Enter ID to delete: ");
                        JTextField EnterID_delete = new JTextField();
                        JButton deleteID = new JButton("Delete");
                        /////////////////////////////////////////////////////////////
                        
                        // Labels and buttons for update account >>> they will display after pressing update button
                        JLabel LabelADD_ID_1 = new JLabel("Enter user ID you want to update*:");
                        JTextField EnterADD_ID_1 = new JTextField();
                        
                        
                                    
                        JLabel LabelADD_PIN_1 = new JLabel("Enter new PIN you want to update: ");
                        JTextField EnterADD_PIN_1 = new JTextField();
                        

                        JLabel LabelADD_ADDRESS_1 = new JLabel("Enter new  address to update: ");
                        JTextField EnterADD_ADDRESS_1 = new JTextField();

                        JLabel LabelADD_PHONE_1 = new JLabel("Enter new phone number to update: ");
                        JTextField EnterADD_PHONE_1 = new JTextField();
                        
                        JButton save = new JButton("save");
                        
 
                        /////////////////////////////////////////////////////////////
                        
                    	ATMPanel.add(Button_addAccount);
                    	ATMPanel.add(Button_DeletAccount);
                    	ATMPanel.add(Button_ShowData);
                        ATMPanel.add(Button_editAccount);
                        ATMPanel.add(Button_logout);
                        ATMPanel.add(Background2);

                    	Button_addAccount.setBounds(width/6, 220, 150, 50);
                    	Button_DeletAccount.setBounds(width/6, 320, 150, 50);
                    	Button_ShowData.setBounds(width/6, 420, 150, 50);
                        Button_editAccount.setBounds(width/6, 520, 150, 50);
                        /////////////////////////////////////////////////////////////////////////
                        Button_addAccount.setBackground(Color.decode("#F2F4F4"));
                        Button_addAccount.setForeground(Color.decode("#468092"));
                        Button_DeletAccount.setBackground(Color.decode("#F2F4F4"));
                        Button_DeletAccount.setForeground(Color.decode("#468092"));
                        Button_editAccount.setBackground(Color.decode("#F2F4F4"));
                        Button_editAccount.setForeground(Color.decode("#468092"));
                        Button_ShowData.setBackground(Color.decode("#F2F4F4"));
                        Button_ShowData.setForeground(Color.decode("#468092"));
                        Button_logout.setBackground(Color.decode("#F2F4F4"));
                        Button_logout.setForeground(Color.decode("#468092"));
                        /////////////////////////////////////////////////////////////////////////
                        
                        ////////////////////////////////////////////////////////////////////////////////////
                        // adding action listener for action buttons outside the navigating buttons to    //
                        // prevent adding multiple action listeners and do a certain function mult. times //
                        ////////////////////////////////////////////////////////////////////////////////////
                        // delete id button to delete a user from the database with text-field's matched input
                        deleteID.addActionListener(eDeleteID -> {
                            try
                            {
                                Integer.parseInt(EnterID_delete.getText());
                                if (EnterID_delete.getText().length() > 5 || EnterID_delete.getText().length() < 5)
                                {
                                    JOptionPane.showMessageDialog(frame,"ID field must be 5 numbers");
                                }
                                else
                                {
                                    try {
                                        admin.deleteAccount(EnterID_delete.getText());
                                        JOptionPane.showMessageDialog(frame,"Account Deleted");
                                    } catch (IOException ex) {
                                        Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                                    }   
                                }
                            }
                            catch (Exception exc)
                            {
                                JOptionPane.showMessageDialog(frame,"ID field must be numbers");
                            }
                        });
                        
                        // add account button to add a user to our database with text-fields' matched inputs
                        AddAccount.addActionListener(eAddAcount -> {
                            try {
                                // Store the return value from add function in admin class to use it in IF/ELSE condition
                                String adminCheck = admin.add(EnterADD_PIN.getText(), EnterADD_NAME.getText(), EnterADD_ADDRESS.getText(), EnterADD_PHONE.getText(), EnterADD_BALANCE.getText());
                                if (adminCheck.equals("true"))
                                {
                                    JOptionPane.showMessageDialog(frame,"Account Added");
                                }
                                else
                                {
                                    // print the returned value from add funtion, in this case the return value 
                                    // is an error handled with (try catch)s 
                                    JOptionPane.showMessageDialog(frame,adminCheck);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                            }                        
                        });
                        
                        save.addActionListener(saveEvent -> {
                            try {
                                if (admin.Update(EnterADD_ID_1.getText(), EnterADD_PIN_1.getText(), EnterADD_ADDRESS_1.getText(), EnterADD_PHONE_1.getText()))
                                    JOptionPane.showMessageDialog(frame, "Account updated");
                                else 
                                    JOptionPane.showMessageDialog(frame, "Failed to update account");
                            } catch (IOException ex) {
                                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        });
                        
                        Button_ShowData.addActionListener(eShowData -> {
                            try {
                                // print the value returned from decryptData function which is the full database but decrypted
                                JOptionPane.showMessageDialog(frame, admin.decryptData());
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        
                        // delete account navigator
                        Button_DeletAccount.addActionListener(eDelete -> {
                            // removing add account components
                            ATMPanel.remove(LabelADD_PIN);
                            ATMPanel.remove(EnterADD_PIN);
                            ATMPanel.remove(LabelADD_NAME);
                            ATMPanel.remove(EnterADD_NAME);
                            ATMPanel.remove(LabelADD_ADDRESS);
                            ATMPanel.remove(EnterADD_ADDRESS);
                            ATMPanel.remove(LabelADD_PHONE);
                            ATMPanel.remove(EnterADD_PHONE);
                            ATMPanel.remove(LabelADD_BALANCE);
                            ATMPanel.remove(EnterADD_BALANCE);
                            ATMPanel.remove(AddAccount);
                            ATMPanel.remove(Background2);
                            ATMPanel.remove(LabelADD_ID_1);
                            ATMPanel.remove(EnterADD_ID_1);
                            ATMPanel.remove(LabelADD_PIN_1);
                            ATMPanel.remove(EnterADD_PIN_1);
                            ATMPanel.remove(LabelADD_ADDRESS_1);
                            ATMPanel.remove(EnterADD_ADDRESS_1);
                            ATMPanel.remove(LabelADD_PHONE_1);
                            ATMPanel.remove(EnterADD_PHONE_1);
                            ATMPanel.remove(save); 
                            ATMPanel.repaint();

                            // adding delete account components 
                            ATMPanel.add(LabelID_delete);
                            ATMPanel.add(EnterID_delete);
                            ATMPanel.add(deleteID);
                            ATMPanel.add(Background2);

                            // setting bounds for delete account components
                            LabelID_delete.setBounds(450, 250, 150, 50);
                            EnterID_delete.setBounds(450, 300, 150, 20);
                            deleteID.setBounds(450, 350, 150, 50);
                            
                            deleteID.setBackground(Color.decode("#F2F4F4"));
                            deleteID.setForeground(Color.decode("#468092"));
                            
                            LabelID_delete.setForeground(Color.white);
                            LabelID_delete.setFont(new Font("", Font.PLAIN, 14));
                        });
                        
                        // add account navigator 
                        Button_addAccount.addActionListener(eAdd -> {
                            // removing delete account components
                            ATMPanel.remove(LabelID_delete);
                            ATMPanel.remove(EnterID_delete);
                            ATMPanel.remove(deleteID);
                            ATMPanel.remove(Background2);
                            ATMPanel.remove(LabelADD_ID_1);
                            ATMPanel.remove(EnterADD_ID_1);
                            ATMPanel.remove(LabelADD_PIN_1);
                            ATMPanel.remove(EnterADD_PIN_1);
                            ATMPanel.remove(LabelADD_ADDRESS_1);
                            ATMPanel.remove(EnterADD_ADDRESS_1);
                            ATMPanel.remove(LabelADD_PHONE_1);
                            ATMPanel.remove(EnterADD_PHONE_1);
                            ATMPanel.remove(save); 
                            ATMPanel.repaint();

                            // adding (add account) components
                            ATMPanel.add(LabelADD_PIN);
                            ATMPanel.add(EnterADD_PIN);
                            ATMPanel.add(LabelADD_NAME);
                            ATMPanel.add(EnterADD_NAME);
                            ATMPanel.add(LabelADD_ADDRESS);
                            ATMPanel.add(EnterADD_ADDRESS);
                            ATMPanel.add(LabelADD_PHONE);
                            ATMPanel.add(EnterADD_PHONE);
                            ATMPanel.add(LabelADD_BALANCE);
                            ATMPanel.add(EnterADD_BALANCE);
                            ATMPanel.add(AddAccount);
                            ATMPanel.add(Background2);

                            // setting bounds for all add account components 
                            LabelADD_PIN.setBounds(350, 200, 180, 30);
                            LabelADD_PIN.setForeground(Color.white);
                            LabelADD_PIN.setFont(new Font("", Font.PLAIN, 12));            
                            EnterADD_PIN.setBounds(600, 200, 120, 25);
                            
                            LabelADD_NAME.setBounds(350, 240, 180, 30);
                            LabelADD_NAME.setForeground(Color.white);
                            LabelADD_NAME.setFont(new Font("", Font.PLAIN, 12));
                            EnterADD_NAME.setBounds(600, 240, 120, 25);
                            
                            
                            LabelADD_ADDRESS.setBounds(350, 280, 180, 30);
                            LabelADD_ADDRESS.setForeground(Color.white);
                            LabelADD_ADDRESS.setFont(new Font("", Font.PLAIN, 12));
                            EnterADD_ADDRESS.setBounds(600, 280, 120, 25);
                            
                            LabelADD_PHONE.setBounds(350, 320, 180, 30);
                            LabelADD_PHONE.setForeground(Color.white);
                            LabelADD_PHONE.setFont(new Font("", Font.PLAIN, 12));
                            
                            EnterADD_PHONE.setBounds(600, 320, 120, 25);
                            LabelADD_BALANCE.setBounds(350, 360, 180, 30);
                            LabelADD_BALANCE.setForeground(Color.white);
                            LabelADD_BALANCE.setFont(new Font("", Font.PLAIN, 12));
                            EnterADD_BALANCE.setBounds(600, 360, 120, 25);
                            
                            AddAccount.setBounds(width/2+280,430, 110, 50);
                            AddAccount.setBackground(Color.decode("#F2F4F4"));
                            AddAccount.setForeground(Color.decode("#468092"));
                            
                        });
                        
                        Button_editAccount.addActionListener(editA -> {
                            ATMPanel.remove(LabelID_delete);
                            ATMPanel.remove(EnterID_delete);
                            ATMPanel.remove(deleteID);
                            ATMPanel.remove(Background2);
                            ATMPanel.remove(LabelADD_PIN);
                            ATMPanel.remove(EnterADD_PIN);
                            ATMPanel.remove(LabelADD_NAME);
                            ATMPanel.remove(EnterADD_NAME);
                            ATMPanel.remove(LabelADD_ADDRESS);
                            ATMPanel.remove(EnterADD_ADDRESS);
                            ATMPanel.remove(LabelADD_PHONE);
                            ATMPanel.remove(EnterADD_PHONE);
                            ATMPanel.remove(LabelADD_BALANCE);
                            ATMPanel.remove(EnterADD_BALANCE);
                            ATMPanel.remove(AddAccount);
                            ATMPanel.repaint();
                            
                            
                            save.setBounds(width/2+280,430, 110, 50);
                            save.setBackground(Color.decode("#F2F4F4"));
                            save.setForeground(Color.decode("#468092"));
                            
                            LabelADD_ID_1.setBounds(350, 200, 180, 30);
                            LabelADD_ID_1.setForeground(Color.white);
                            LabelADD_ID_1.setFont(new Font("", Font.PLAIN, 12));            
                            EnterADD_ID_1.setBounds(600, 200, 120, 25);
                            
                            
                            LabelADD_PIN_1.setBounds(350, 240, 180, 30);
                            LabelADD_PIN_1.setForeground(Color.white);
                            LabelADD_PIN_1.setFont(new Font("", Font.PLAIN, 12));            
                            EnterADD_PIN_1.setBounds(600, 240, 120, 25);

                            LabelADD_ADDRESS_1.setBounds(350, 280, 180, 30);
                            LabelADD_ADDRESS_1.setForeground(Color.white);
                            LabelADD_ADDRESS_1.setFont(new Font("", Font.PLAIN, 12));
                            EnterADD_ADDRESS_1.setBounds(600, 280, 120, 25);

                            LabelADD_PHONE_1.setBounds(350, 320, 180, 30);
                            LabelADD_PHONE_1.setForeground(Color.white);
                            LabelADD_PHONE_1.setFont(new Font("", Font.PLAIN, 11));                          
                            EnterADD_PHONE_1.setBounds(600, 320, 120, 25);
                            
                            ATMPanel.add(LabelADD_ID_1);
                            ATMPanel.add(EnterADD_ID_1);
                            ATMPanel.add(LabelADD_PIN_1);
                            ATMPanel.add(EnterADD_PIN_1);
                            ATMPanel.add(LabelADD_ADDRESS_1);
                            ATMPanel.add(EnterADD_ADDRESS_1);
                            ATMPanel.add(LabelADD_PHONE_1);
                            ATMPanel.add(EnterADD_PHONE_1);
                            ATMPanel.add(save);
                            ATMPanel.add(Background2);
                            //////////////////////////////////////////////////////////
                        });
                    }
                    else
                    {
                        // go here if user and make a new object of User class
                        User user = new User(Enter_ID.getText());
                       
                        JButton deposit;
                        JButton balance;
                        JButton Withdrawl;

                        deposit = new JButton("deposit");
                        balance = new JButton("balance");
                        Withdrawl = new JButton("Withdrawl");                        

                        JTextField AddBalance_FIELD = new JTextField();
                        JButton AddBalance_B = new JButton("Add balance");
                        
                        JTextField Withdrawl_FIELD = new JTextField();
                        JButton Withdrawl_B = new JButton("Withdraw");
                        
                        JLabel LabelBalance = new JLabel();
                        
                        deposit.setBounds(width/6, 220, 150, 50);
                    	balance.setBounds(width/6, 320, 150, 50);
                    	Withdrawl.setBounds(width/6, 420, 150, 50);  

                        ATMPanel.add(deposit);
                        ATMPanel.add(balance);
                        ATMPanel.add(Withdrawl);
                        ATMPanel.add(Button_logout);
                        ATMPanel.add(Background2);
                        /////////////////////////////////////////////////////////////////////////////////////////////////
                        deposit.setBackground(Color.decode("#F2F4F4"));
                        deposit.setForeground(Color.decode("#468092"));
                        balance.setBackground(Color.decode("#F2F4F4"));
                        balance.setForeground(Color.decode("#468092"));
                        Withdrawl.setBackground(Color.decode("#F2F4F4"));
                        Withdrawl.setForeground(Color.decode("#468092"));
                        ////////////////////////////////////////////////////////////////////////////////////////////////

                        AddBalance_B.addActionListener(AddBalanceE_Action ->{
                            try {
                                user.deposit(Integer.parseInt(AddBalance_FIELD.getText()));
                                JOptionPane.showMessageDialog(frame,"Balance added");
                            } catch (IOException ex) {
                                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            catch (Exception exception)
                            {
                                JOptionPane.showMessageDialog(frame,"Input must be numbers");
                            }
                        });
                        
                        Withdrawl_B.addActionListener(WithdrawlE_Action ->{
                            try {
                                user.withdrawal(Integer.parseInt(Withdrawl_FIELD.getText()));
                                JOptionPane.showMessageDialog(frame,"Withdrawal done");
                            } catch (IOException ex) {
                                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            catch (Exception exception)
                            {
                                JOptionPane.showMessageDialog(frame,"Input must be numbers");
                            }
                        });
                        
                        deposit.addActionListener(depositE ->{
                            ATMPanel.remove(Withdrawl_FIELD);
                            ATMPanel.remove(Withdrawl_B);
                            ATMPanel.remove(LabelBalance);
                            ATMPanel.remove(Background2);
                            ATMPanel.repaint();

                            ATMPanel.add(AddBalance_FIELD);
                            ATMPanel.add(AddBalance_B);
                            ATMPanel.add(Background2);

                            AddBalance_FIELD.setBounds(500,280,150,50);
                            AddBalance_B.setBounds(500,350,150,50);
                            AddBalance_B.setBackground(Color.decode("#F2F4F4"));
                            AddBalance_B.setForeground(Color.decode("#468092"));
                        });
                        
                        Withdrawl.addActionListener(WithdrawlE ->{
                            ATMPanel.remove(AddBalance_FIELD);
                            ATMPanel.remove(AddBalance_B);
                            ATMPanel.remove(LabelBalance);
                            ATMPanel.remove(Background2);
                            ATMPanel.repaint();
                            
                            ATMPanel.add(Withdrawl_FIELD);
                            ATMPanel.add(Withdrawl_B);
                            ATMPanel.add(Background2);
                            
                            Withdrawl_FIELD.setBounds(500,280,150,50);
                            Withdrawl_B.setBounds(500,350,150,50);
                            Withdrawl_B.setBackground(Color.decode("#F2F4F4"));
                            Withdrawl_B.setForeground(Color.decode("#468092"));
                        });
                        
                        balance.addActionListener(ShowE -> {
                            ATMPanel.remove(AddBalance_FIELD);
                            ATMPanel.remove(AddBalance_B);
                            ATMPanel.remove(Withdrawl_FIELD);
                            ATMPanel.remove(Withdrawl_B);
                            ATMPanel.remove(Background2);
                            ATMPanel.repaint();
                            LabelBalance.setForeground(Color.white);
                            LabelBalance.setFont(new Font("", Font.PLAIN, 20));
                            
                            try {
                                LabelBalance.setText("Your Balance now is:  " + user.CheckAccount());
                            } catch (UnsupportedEncodingException ex) {
                                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            LabelBalance.setBounds(width/3+90,350,250,30);
                            
                            ATMPanel.add(LabelBalance);
                            ATMPanel.add(Background2);
                        });
                    }
                    ///////////////////////// end of checking if Admin or User /////////////////////////
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(jframe.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // mouse listener get the mouse X and Y position 
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        }
        );
        
        // mouse motion listener to drag the frame from anywhere using mouse instead of toolbar 
        frame.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                frame.setLocation(frame.getX() + e.getX() - mouseX, frame.getY() + e.getY() - mouseY);
            }
        }
        );
    }
    
    // this boolean function is checking account by passing ID and PIN from GUI and return true ->
    // -> if the account with it's correct PIN are entered and return false if the ->
    // -> PIN is incorrect or the account is not in the database
    private boolean checkAccount(String id, String PIN) throws FileNotFoundException
    {
        decryptData();

        // base case admin 
        if (id.equals("00001") && PIN.equals("1234"))
            return true;
        
        StringTokenizer tokenizer;
        
        // check if the ID is in the database and if true check it's PIN 
        // and if both were entered correctly the user will log in 
        for (int i = 0; i < InitializeUser.size(); i++)
        {
            tokenizer = new StringTokenizer( InitializeUser.get(i) , "," );
            
            if (tokenizer.nextToken().equals(id))
            {
                if (tokenizer.nextToken().equals(PIN))
                {
                    return true;
                }       
            }
        }
        
        encryptData();
        return false;
    }
    
    private void decryptData()
    {
        String userDecryptedString = "";
        for (int i = 0; i < InitializeUser.size(); i++)
        {
            for (int j = 0; j < InitializeUser.get(i).length(); j++)
            {
                char tempChar = InitializeUser.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar - 2;
                char dataChar = (char)tempASCII;
                userDecryptedString = userDecryptedString + dataChar;
            }
            InitializeUser.set(i, userDecryptedString);
            userDecryptedString = "";
        }
    }
    
    private void encryptData()
    {
        String userEncryptedString = "";
        for (int i = 0; i < InitializeUser.size(); i++)
        {
            for (int j = 0; j < InitializeUser.get(i).length(); j++)
            {
                char tempChar = InitializeUser.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar + 2;
                char dataChar = (char)tempASCII;
                userEncryptedString = userEncryptedString + dataChar;
            }
            InitializeUser.set(i, userEncryptedString);
            userEncryptedString = "";
        }
    }
    
    private void calculate(){
        try{
            double miles = Double.parseDouble(textbox.getText()) * 0.6214;
            JOptionPane.showMessageDialog(frame,"Input must be numbers");
        }
        catch (Exception e)
        {
            
        }
        

    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
    }   
    
}