package phoneCompany1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class Login {
	

	JFrame f;
	final JTextField tf;
	JPasswordField value;
	
	public Login()
	{
		f = new JFrame();
		
		JRadioButton r1=new JRadioButton(Glavna.Role.Admin.toString());    
		JRadioButton r2=new JRadioButton(Glavna.Role.User.toString() , true);    
		r1.setBounds(20,15,100,30);    
		r2.setBounds(20,35,100,30);    
		ButtonGroup bg=new ButtonGroup();    
		bg.add(r1);
		bg.add(r2);    
		f.add(r1);
		f.add(r2);
		
		JLabel l1,l2;  
	    l1=new JLabel("User name:");  
	    l1.setBounds(20,70, 100,30);  
	    l2=new JLabel("Password:");  
	    l2.setBounds(20,90, 100,30);  
	    f.add(l1);
	    f.add(l2);
	    
	    tf=new JTextField();  
	    tf.setBounds(100,75,120,20);  
	    f.add(tf);
	    
	    value = new JPasswordField();
	    value.setBounds(100,95,120,20);
	    f.add(value);
	    
	    JButton bExit = new JButton("Exit"); 
		bExit.setBounds(30,150,80,40);
		bExit.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             f.dispose();
			             return;
			        }  
			    });
		f.add(bExit);
		
		JButton bLog = new JButton("Log In"); 
		bLog.setBounds(135,150,80,40);
	    f.add(bLog);
		
		f.setSize(270,300);    
		f.setLayout(null);    
		f.setVisible(true);    
		
		
		bLog.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				

				if (tf.getText().isEmpty()||value.getPassword().length==0)
				{
					JOptionPane.showMessageDialog(bLog, "Please enter username and password.");
					return;
				}
				
				if(r1.isSelected())			// if admin is selected
				{	 
					LogIn(Glavna.Role.Admin.toString());
				}
				else						// if user is selected
				{
					LogIn(Glavna.Role.User.toString());
				}
			        }  
			    }); 
	}
	
	
	
	void LogIn(String check)
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
		
		String pass = new String(value.getPassword());
		
		String userName = tf.getText().toString();
		 
		if(check.equals(Glavna.Role.Admin.toString()))				// admin
		{
			String sql = "SELECT userName,pass FROM admins WHERE userName='"+userName+"' AND pass='"+pass+"'";
			
			ResultSet myRs = myStm.executeQuery(sql);
					
		if(myRs.next())
			{
				JOptionPane.showMessageDialog(f, "Logged in!");
					f.dispose();
					pass=null;
					new AdminLogged();
					return;
					
					}
			
			JOptionPane.showMessageDialog(f, "There is no such admin!");
		}
		else
		{
			String sql = "SELECT userName,pass,id FROM users WHERE userName='"
						+userName+"' AND pass='"+pass+"'";

			ResultSet myRs = myStm.executeQuery(sql);
			if(myRs.next())
			{
				JOptionPane.showMessageDialog(f, "Logged in!");
				f.dispose();
				pass=null;
				
				new UserLoged(myRs.getString("userName"),myRs.getString("id"));
				return;
			}
			else
			JOptionPane.showMessageDialog(f, "There is no such user!");
		}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
