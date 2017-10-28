//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;

public class DummyMailBox
{
	private String username;
	private String password;
//	private String[] mails = new String[] { };
//	private String[] tempmail = new String[] { };

	//Constructor
	public DummyMailBox()
	{
		username = "test";
		password = "password";

//		mails[0] = "Hey Test, just a test message.";
//		mails[1] = "Hi Test, just another test message.";
//		mails[2] = "Hello Test, this is one special message.";
	}

	//Check username
	public Boolean checkUsername(String user)
	{
		if(user.equals(username))
			return true;
		else
			return false;
	}

	//Check password
	public Boolean checkPassword(String pass)
	{
		if(pass.equals(password))
			return true;
		else
			return false;
	}

/*	public String getMail(int num)
	{
		return mails[num];
	}

	public int getMailnumber()
	{
		return mails.length();
	}

	public int getTotalOctet() 
	{
		int size = 0;
		for(int n=0; n<mails.length(); n++) 
		{
			byte[] aByte = mails[n].getBytes();
			size += aByte.length;	
		}
		return size;
	}

	public int getOctet(int num)
	{
		int size = 0;
		byte aByte = mails[num].getBytes();
		size = aByte.length;

		return size;
	}

	public void labelDelete(int index, String[] z)
	{
    	if(index <= z.length)
    	{
        	String[] tempmail = new String[z.length - 1];

        	int newIndex = 0;

        	for(int i = 0; i < z.length; i++)
        	{
            	if(i != index)
            	{
                	tempmail[newIndex] = z[i];
                	newIndex++;
            	}
        	}
    	}
	}

*/
}