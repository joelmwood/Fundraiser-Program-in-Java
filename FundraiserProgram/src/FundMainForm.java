/**
 * @author Learning Team C
 */
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FundMainForm extends JFrame {
    private JLabel addCharityJL;
    private JTextField charityTextField;
    
    private JLabel addIndividualJL;
    private JTextField addIndividTextField;
    
    private JLabel addPledgeAmountJL;
    private JTextField addPledgeAmountTextField;
    
    static final String DB_URL = "jdbc:derby://localhost:1527/fundraiser";
    static final String USER = "guest";
    static final String PASS = "guest";
    
    public FundMainForm(){
        super("Fundraiser Program");
        
        try{
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{            
            
        }
       
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
         
        
        JMenu View = new JMenu("View");
         menuBar.add(View);
        
          
        JMenu Tools = new JMenu("Tools");
        menuBar.add(Tools);
         
        JMenu Window = new JMenu("Window");
        menuBar.add(Window);
          
        JMenu Help = new JMenu("Help");
        menuBar.add(Help);
        
        JMenu Edit = new JMenu("Edit");
        menuBar.add(Edit);
                 
        String[] colNames = {"Charity Name", "Individual Name", "Pledge"};
        
        DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(tableModel);
        
              
        // menu items for "File"  
        JMenuItem open = new JMenuItem("Open File...");
        fileMenu.add(open);
        
        JMenuItem save = new JMenuItem("Save File...");
        fileMenu.add(save);
       
        fileMenu.addSeparator();  
        
        JMenuItem clear = new JMenuItem("Clear Fields");
        fileMenu.add(clear);
        
        fileMenu.addSeparator();  
               
        // menu items for "Edit" 
        JMenuItem Cut = new JMenuItem("Cut");
        Edit.add(Cut);
      
        Edit.addSeparator(); 
                     
        JMenuItem Paste = new JMenuItem("Paste");
        Edit.add(Paste);
        
        JMenuItem exitFileMenu = new JMenuItem("Exit");
        fileMenu.add(exitFileMenu);
        
        JMenuItem view = new JMenuItem("Toolbars");
        View.add(view);
        
        JMenuItem window = new JMenuItem("Project");
        Window.add(window);
        
        JMenuItem tools = new JMenuItem("Hammer");
        Tools.add(tools);
        
        JMenuItem help = new JMenuItem("Contact Numbers");
        Help.add(help);
         
        KeyListener listener = new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                char c=e.getKeyChar();
                if (!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE))){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        
        };
        //=============================================================
        addCharityJL = new JLabel();
        addCharityJL.setText("Add Charity");
        addCharityJL.setBounds(16, 16, 120, 21 );
        contentPane.add(addCharityJL);
        charityTextField = new JTextField(15);
        charityTextField.setBounds(146, 16, 120, 21);
        contentPane.add(charityTextField);

        addIndividualJL = new JLabel();
        addIndividualJL.setText("Add Individual");
        addIndividualJL.setBounds(16, 46, 120, 21 );
        contentPane.add(addIndividualJL);
        addIndividTextField = new JTextField(15);
        addIndividTextField.setBounds(146, 46, 120, 21);
        contentPane.add(addIndividTextField);
        
        addPledgeAmountJL = new JLabel();
        addPledgeAmountJL.setText("Add Pledge Amount");
        addPledgeAmountJL.setBounds( 16, 76, 120, 21 );
        contentPane.add(addPledgeAmountJL);
        addPledgeAmountTextField = new JTextField(10);        
        addPledgeAmountTextField.setBounds(146, 76, 120, 21 );
        contentPane.add(addPledgeAmountTextField);
        addPledgeAmountTextField.addKeyListener(listener);
       
        
        
        JButton addInfoButton = new JButton("Add info"); 
        addInfoButton.setBounds( 16, 126, 120, 21 );
        contentPane.add(addInfoButton);     
        
        JScrollPane scrollPane = new JScrollPane(table); 
        scrollPane.setBounds(16, 226, 320, 101 );
        contentPane.add(scrollPane);
        scrollPane.setVisible(true);
        
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "Select * FROM DONORS";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String charity = rs.getString("Charity");
                String individual = rs.getString("Individual");
                String pledge = rs.getString("Pledge");
                String test[] ={charity,individual,pledge};
                tableModel.addRow(test);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception se){
            //Handle errors for Class.forName
            se.printStackTrace();                 
        }
        
        addInfoButton.addActionListener((ActionEvent e) -> {
            if((charityTextField.getText().length()==0)||
                    (addIndividTextField.getText().length()==0)||
                    (addPledgeAmountTextField.getText().length()==0)){
                JOptionPane.showMessageDialog
                    (null,"Please make sure information is " +
                     "in each entry field.","Missing Information",
                     JOptionPane.WARNING_MESSAGE);
            }else{
                String a = charityTextField.getText();
                String b = addIndividTextField.getText();
                String c = addPledgeAmountTextField.getText();
                String test[]={a,b,c};
                tableModel.addRow(test);
                
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "INSERT INTO DONORS (Charity, Individual, Pledge)"
                            + "VALUES ('"+a+"', '"+b+"', '"+c+"')";
                    stmt.executeUpdate(sql);
                    
                    stmt.close();
                    conn.close();
                }catch(SQLException se){
                    //Handle errors for JDBC
                    se.printStackTrace();
                }catch(Exception se){
                     //Handle errors for Class.forName
                     se.printStackTrace();                 
                }
                charityTextField.setText(null);
                addIndividTextField.setText(null);
                addPledgeAmountTextField.setText(null);

            }            
                scrollPane.setVisible(true);                
        });
       
        JButton deleteRowButton = new JButton("Delete Row");
        deleteRowButton.setBounds( 146, 126, 120, 21 );       
        contentPane.add(deleteRowButton);
        
        deleteRowButton.addActionListener((ActionEvent e) -> {
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                String charityX = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
                String sql = "DELETE FROM DONORS WHERE Charity='"+charityX+"'";
                
                stmt.executeUpdate(sql);
                 
                stmt.close();
                conn.close();
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception se){
                //Handle errors for Class.forName
                se.printStackTrace();                 
            }    
            tableModel.removeRow(table.getSelectedRow());    
        });
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
		charityTextField.setText(null);
                addIndividTextField.setText(null);
                addPledgeAmountTextField.setText(null);
            }
	});
                       
        exitFileMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
				System.exit(0);
            }
	});
        
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
            }
	});
        
        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               
            }               
	});
    }   
    
    private void addAmountTextFieldKeyTyped(java.awt.event.KeyEvent evt) {
        char c=evt.getKeyChar();
        if (!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE))){
            evt.consume();
        }
    }
}
