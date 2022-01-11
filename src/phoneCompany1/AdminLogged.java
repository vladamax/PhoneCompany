package phoneCompany1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AdminLogged {

	JFrame f;
	JTextArea area;
	JPasswordField value;
	JTextField tf,tf1,tf2,tf3;
	DefaultListModel<String> list1;
	
	//void find()
	
	void OfferRS()
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			String sql = "select name from offers";
			
			ResultSet myRs = myStm.executeQuery(sql);
			
			while(myRs.next())
		    {
		    	list1.addElement(myRs.getString("name"));
		    }
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void Search()
	{
		JFrame f = new JFrame();
		JLabel l1,l2;
		l1=new JLabel("EGN:");  
	    l1.setBounds(20,30, 100,30);
	    f.add(l1);

	    list1 = new DefaultListModel<>();
	    OfferRS();

	    JList<String> list = new JList<>(list1);  
        list.setBounds(20,75, 75,75);  
        f.add(list);  
	    
	    JTextField tf,tf1,tf2;
	    tf=new JTextField();  
	    tf.setBounds(100,35,120,20);  
	    f.add(tf);
	    

	    
	    JButton b = new JButton("Search"); 
		b.setBounds(130,135,110,40); 
		b.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			
				//find(tf.getText(),list.getSelectedValue());
			}  
			    }); 
		f.add(b);
		 f.setSize(270,230);    
			f.setLayout(null);    
			f.setVisible(true); 
		    
	    
	}
	
	void AddService(String numb,String minutesAdd , String smsAdd , String mbsAdd)
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			ResultSet myRs = myStm.executeQuery("SELECT number,minutesLeft,smsLeft,mbsLeft FROM numbers WHERE number='" + numb + "'");
			
			if(myRs.next())
			{
				
				
				
				int min = Integer.parseInt(myRs.getString("minutesLeft"));
				if(!minutesAdd.isEmpty())
				min = min + Integer.valueOf(minutesAdd);
				int sms = Integer.parseInt(myRs.getString("smsLeft"));
				if(!smsAdd.isEmpty())
				sms = sms + Integer.parseInt(smsAdd);
				int mbs = Integer.parseInt(myRs.getString("mbsLeft"));
				if(!mbsAdd.isEmpty())
				mbs = mbs + Integer.parseInt(mbsAdd);
				
				
				String sql = "UPDATE numbers"
				+ " set minutesLeft = " + min + 
				 " , smsLeft = " + sms + " , mbsLeft = " + mbs + " WHERE number ='" + numb + "'";
		
				myStm.executeUpdate(sql);
				
			JOptionPane.showMessageDialog(f, "Services added successfully!");
				return;
			}
				else
				{
					JOptionPane.showMessageDialog(f, "Such number doesn't exist!");
					return;
				}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void CheckForAddService()
	{
JFrame f = new JFrame();
		
		JLabel l1,l2,l3,l4,l5;  
	    l1=new JLabel("Number:");  
	    l1.setBounds(20,30, 100,30);  
	    l2=new JLabel("Minutes:");  
	    l2.setBounds(20,50, 100,30);  
	    f.add(l1);
	    f.add(l2);
	    
	    l3=new JLabel("SMS: ");  
	    l3.setBounds(20,70, 100,30);  
	    l4=new JLabel("MBs: ");  
	    l4.setBounds(20,90, 100,30);  
	    f.add(l3);
	    f.add(l4);
	    
	    JTextField tf,tf1,tf2,tf3;
		
	    tf=new JTextField();  
	    tf.setBounds(100,35,120,20);  
	    f.add(tf);
	    

	    tf1=new JTextField();  
	    tf1.setBounds(100,55,120,20);  
	    f.add(tf1);
	    

	    tf2=new JTextField();  
	    tf2.setBounds(100,75,120,20);  
	    f.add(tf2);
	    

	    tf3=new JTextField();  
	    tf3.setBounds(100,95,120,20);  
	    f.add(tf3);
	    
	    
	    JButton b = new JButton("Add service"); 
		b.setBounds(130,135,110,40); 
		b.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						if(tf.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(f, "Please enter number!");
							return;
						}
						AddService(tf.getText().toString(),tf1.getText().toString(),tf2.getText().toString(),tf3.getText().toString());
			}  
			    }); 
		
		f.add(b);
	    

	    f.setSize(270,230);    
		f.setLayout(null);    
		f.setVisible(true); 
	    
	}
	
	void Depts()
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			ResultSet myRs = myStm.executeQuery("SELECT numbers.number,users.firstName,users.lastName FROM users JOIN numbers ON users.id=numbers.user_id WHERE numbers.nextMonthPaid=0");
			
			StringBuilder sb = new StringBuilder();
			
			while(myRs.next()) 
			{
				sb.append("Name: " + myRs.getString("firstName") + " " + myRs.getString("lastName")+"\nNumber: "
						+ myRs.getString("number")+"\n\n");
				area.setText(sb.toString());
			}
			
			if(sb.length()==0)
				area.setText("Everyone paid their dues!");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	void AddUser()
	{
		
		JFrame f = new JFrame();
		
		JLabel l1,l2,l3,l4,l5;  
	    l1=new JLabel("User name:");  
	    l1.setBounds(20,30, 100,30);  
	    l2=new JLabel("Password:");  
	    l2.setBounds(20,50, 100,30);  
	    f.add(l1);
	    f.add(l2);
	    
	    l3=new JLabel("First name: ");  
	    l3.setBounds(20,70, 100,30);  
	    l4=new JLabel("Last name: ");  
	    l4.setBounds(20,90, 100,30);  
	    f.add(l3);
	    f.add(l4);
	    
	    l5=new JLabel("EGN: ");  
	    l5.setBounds(20,110, 100,30); 
	    f.add(l5);
		
	    tf=new JTextField();  
	    tf.setBounds(100,35,120,20);  
	    f.add(tf);
	    
	    
	    value = new JPasswordField();
	    value.setBounds(100,55,120,20);
	    f.add(value);
	    

	    tf1=new JTextField();  
	    tf1.setBounds(100,75,120,20);  
	    f.add(tf1);
	    

	    tf2=new JTextField();  
	    tf2.setBounds(100,95,120,20);  
	    f.add(tf2);
	    

	    tf3=new JTextField();  
	    tf3.setBounds(100,115,120,20);  
	    f.add(tf3);
	    
	    JButton bAddU = new JButton("Add user"); 
	    bAddU.setBounds(135,140,90,30);
		bAddU.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				String pass = new String(value.getPassword());
				if (tf.getText().isEmpty()||pass.length()==0)
				{
					JOptionPane.showMessageDialog(f, "Please enter username and password.");
					return;
				}
						
			             checkIfUserExists(tf.getText(),pass);
			         
			        }  
			    });
	    f.add(bAddU);
	    
	
	    f.setSize(270,250);    
		f.setLayout(null);    
		f.setVisible(true); 
		
		
		
	}
	
	void checkIfUserExists(String user , String pass)
	{
		
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			ResultSet myRs = myStm.executeQuery("SELECT userName,egn FROM users WHERE userName='"+user+"' OR "
					+ "egn='"+tf3.getText()+"'");

			if(myRs.next())
			{
				if(myRs.getString("userName").equals(user))
				{
				JOptionPane.showMessageDialog(f, "User with such username already exists!");
				return;
				}
				else
				{
					JOptionPane.showMessageDialog(f, "User with such egn already exists!");
					return;
				}
			}
			else
			{
				String sql = "INSERT INTO users (userName,pass,firstName,lastName,egn) VALUES "
						+ "('" + user + "','" + pass + "','" +tf1.getText() + "','" + tf2.getText() + "','" + tf3.getText()+"')";

				myStm.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(f, "User successfully added!");
			}
		
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public AdminLogged()
	{
		f = new JFrame();
		
		area=new JTextArea();  
        area.setBounds(160,10, 300,290);  
        area.setEditable(false);
        f.add(area);
		
		
		
		JButton bDepts = new JButton("This Month Depts"); 
		bDepts.setBounds(20,10,125,40);
		bDepts.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             Depts();
			        }  
			    });
		
	    f.add(bDepts);
	
		JButton bSearch = new JButton("Search"); 
		bSearch.setBounds(20,60,125,40);
		bSearch.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             Search();
			        }  
			    });
		
	    f.add(bSearch);
		
	    JButton bAddU = new JButton("Add user"); 
	    bAddU.setBounds(20,110,125,40);
	    bAddU.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             AddUser();
			        }  
			    });
		
	    f.add(bAddU);
	    
	    JButton bAddS = new JButton("Add service"); 
	    bAddS.setBounds(20,160,125,40);
	    bAddS.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             CheckForAddService();
			        }  
			    });
		
	    f.add(bAddS);
		
	    JButton bLogOut = new JButton("Log Out"); 
		bLogOut.setBounds(20,210,125,40);
		bLogOut.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             new Login();
			             f.dispose();
			             return;
			        }  
			    });
	    f.add(bLogOut);
	    
	    JButton bExit = new JButton("Exit"); 
		bExit.setBounds(20,260,125,40);
		bExit.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             f.dispose();
			             return;
			        }  
			    });
	    f.add(bExit);
	    
	    
	    f.setSize(500,350);  
		f.setLayout(null);    
		f.setVisible(true);
		
	}
	
}
