package phoneCompany1;




public class Glavna {
	
	public static final String url = "jdbc:mysql://localhost:3306/PhoneCompany";
	public static final String user = "root";
	public static final String pass = "1axvlada";
	
	public enum Role
	{
		Admin,
		User
	}

	public static void main(String[] args) {
	
		new Login();
	}

}
