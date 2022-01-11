package phoneCompany1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import javax.swing.*;

public class UserLoged {
	
	JTextArea area;
	JFrame f;
	
	void Status(String userId)
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			String sql = "select * from numbers ";
			
			ResultSet myRs = myStm.executeQuery(sql);
			
			StringBuilder sb = new StringBuilder();
			
			while(myRs.next())
				{
					if(myRs.getString("user_id").equals(userId))
					{
						sb.append("Number: " + myRs.getString("number") + "\n\t\t Minutes left: " + 
						myRs.getString("minutesLeft")+ "\n\t\t SMS left: " + 
						myRs.getString("smsLeft") + "\n\t\t MBs left: " + myRs.getString("mbsLeft") + "\n");
					}
				}
			if(sb.length()!=0)
			area.setText(sb.toString());
			else
				area.setText("You don't have any number registered!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void Active(String userId)
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			String sql = "SELECT numbers.number , offers.name FROM numbers "
					+ "JOIN offers ON numbers.offer_id = offers.id WHERE numbers.user_id="+userId;

			ResultSet myRs = myStm.executeQuery(sql);
			
			StringBuilder sb = new StringBuilder();
			
			while(myRs.next())
				{
					sb.append("Number: " + myRs.getString("number") + "\nActive Service: " +
							myRs.getString("name") + "\n\n");
					area.setText(sb.toString());
				}
			if(sb.length()==0)
				area.setText("You don't have any number registered!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
 	
	void PayUntil(String userId)
	{
		try {
			// 1. Get a connection to database
			 
			Connection myConn = DriverManager.getConnection(Glavna.url,Glavna.user,Glavna.pass);
			
			// 2. Create a statement
			 
			Statement myStm = myConn.createStatement();
			
			String sql = "SELECT numbers.number , numbers.daysUntilNextPay FROM numbers WHERE numbers.user_id="
			+userId;

			ResultSet myRs = myStm.executeQuery(sql);
			
			StringBuilder sb = new StringBuilder();
			
			while(myRs.next())
				{
					sb.append("Number: " + myRs.getString("number") + "\nDays left for payment: " +
							myRs.getString("daysUntilNextPay") + "\n\n");
					area.setText(sb.toString());
				}
			if(sb.length()==0)
				area.setText("You don't have any number registered!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public UserLoged(String user, String userid)
	{
		f=new JFrame();
		JLabel l1 = new JLabel("Hello " + user + "!");
		l1.setBounds(10,5,150,25);
		l1.setFont(new Font("Serif", Font.PLAIN, 30));
		f.add(l1);
		
		JButton bStatus = new JButton("Status"); 
		bStatus.setBounds(20,100,125,40);
		bStatus.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             Status(userid);
			        }  
			    }); 
		
	    f.add(bStatus);
	    
	    
	    JButton bActive = new JButton("Active Services"); 
		bActive.setBounds(20,50,125,40);
		bActive.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             Active(userid);
			        }  
			    });
		
	    f.add(bActive);
	    
	    
	    JButton bPay = new JButton("Pay until"); 
		bPay.setBounds(20,150,125,40);
		bPay.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             PayUntil(userid);
			        }  
			    });
		
	    f.add(bPay);
	    
	    
	    JButton bLogOut = new JButton("Log Out"); 
		bLogOut.setBounds(20,200,125,40);
		bLogOut.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             new Login();
			             f.dispose();
			             return;
			        }  
			    });
	    f.add(bLogOut);
	    
	    JButton bExit = new JButton("Exit"); 
		bExit.setBounds(20,250,125,40);
		bExit.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			             f.dispose();
			             return;
			        }  
			    });
	    f.add(bExit);
	    

	    area=new JTextArea();  
        area.setBounds(160,50, 500,200);  
        area.setEditable(false);
        f.add(area);

        
	    f.setSize(480,340);  
		f.setLayout(null);    
		f.setVisible(true);
	    
	}
	
	
}
