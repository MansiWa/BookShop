import java.awt.EventQueue;

import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;
//import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class BookShop {

	private JFrame frame;
	private JTextField txtprice;
	private JTextField txtedition;
	private JTextField txtbname;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
				    BookShop window = new BookShop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookShop() {
		initialize();
		Connect();
		table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookshop","root","Mansi@2001");
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void table_load()
	{
		
		try
		{
			pst = con.prepareStatement("select * from book");
			rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 823, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(321, 0, 170, 61);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel txtfile = new JPanel();
		txtfile.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		txtfile.setBounds(25, 81, 370, 232);
		frame.getContentPane().add(txtfile);
		txtfile.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 34, 112, 35);
		txtfile.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Edition");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(10, 106, 84, 22);
		txtfile.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Price");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3.setBounds(10, 162, 65, 27);
		txtfile.add(lblNewLabel_3);
		
		txtprice = new JTextField();
		txtprice.setBounds(132, 167, 192, 24);
		txtfile.add(txtprice);
		txtprice.setColumns(10);
		
		txtbname = new JTextField();
		txtbname.setColumns(10);
		txtbname.setBounds(132, 45, 192, 24);
		txtfile.add(txtbname);
		
		txtedition = new JTextField();
		txtedition.setBounds(132, 108, 192, 22);
		txtfile.add(txtedition);
		txtedition.setColumns(10);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(273, 323, 111, 61);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String bname,edition,price;
				
				bname=txtbname.getText();
				edition=txtedition.getText();
				price=txtprice.getText();
				
				try
				{
					pst=con.prepareStatement("insert into book(name,edition,price)Values(?,?,?)");
					pst.setString(1,bname);
					pst.setString(2,edition);
					pst.setString(3,price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Addeddd!!!!!");
					table_load();
					
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}
				catch(SQLException el)
				{
					el.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_1.setBounds(25, 323, 111, 61);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_2.setBounds(152, 323, 111, 61);
		frame.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(395, 92, 404, 281);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(25, 394, 361, 69);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("Book Id");
		lblNewLabel_2_1.setBounds(32, 21, 80, 22);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try
				{
					String id =txtbid.getText();
					pst = con.prepareStatement("Select name, edition, price, from book where id =?");
					pst.setString(1, id);
					ResultSet rs=pst.executeQuery();
					if(rs.next()==true)
					{
						String name=rs.getString(1);
						String edition=rs.getString(2);
						String price=rs.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
					}
					else
					{
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				}
				catch(SQLException ex)
				{
					
				}
				
				
				
			}
		});
		txtbid.setBounds(122, 23, 157, 22);
		panel.add(txtbid);
		txtbid.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
                String bname,edition,price,bid;
				
				bname=txtbname.getText();
				edition=txtedition.getText();
				price=txtprice.getText();
				bid=txtbid.getText();
				
				try
				{
					pst=con.prepareStatement("update book set name=?,edition=?,price=? where id=?");
					pst.setString(1,bname);
					pst.setString(2,edition);
					pst.setString(3,price);
					pst.setString(4,bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record updatedd!!!!!");
					table_load();
					
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}
				catch(SQLException el)
				{
					el.printStackTrace();
				}
				
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnUpdate.setBounds(459, 383, 125, 61);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnNewButton_3_1 = new JButton("Delete");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                String bid;
				
				bid=txtbid.getText();
				
				try
				{
					pst=con.prepareStatement("delete from book where id=?");
					
					pst.setString(1,bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record deletedd!!!!!");
					table_load();
					
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}
				catch(SQLException el)
				{
					el.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_3_1.setBounds(594, 383, 122, 65);
		frame.getContentPane().add(btnNewButton_3_1);
	}
}
