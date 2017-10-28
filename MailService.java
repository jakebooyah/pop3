import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MailService implements Runnable
{
	//State 0 = No user
	//State 1 = User valid pending password
	//State 2 = Logged On
	private int state = 0;
	//Quit 0 = Run
	//Quit 1 = Quit
	private int quit = 0;
	//command with uppercase
	private String command;
	//command without uppercase
	private String commandA;
	//argument
    private String arg;

    //Constructor Constructs a service object that processes commands from a socket for mail.

	public MailService(Socket aSocket, DummyMailBox aMail)
   	{	
      	s = aSocket;
      	mail = aMail;
   	}

   	public void run()
   	{
    	try
      	{
         	try
         	{
            	in = new Scanner(s.getInputStream());
            	out = new PrintWriter(s.getOutputStream());
            	doService();            
         	}
         	finally
         	{
         	   s.close();
         	}
      	}
      	catch (IOException exception)
      	{
         	exception.printStackTrace();
      	}
   	}

   	//Executes all commands
   	public void doService() throws IOException
   	{      
      	while (true)
      	{  
         	if (!in.hasNext()) return;
         	String input = in.nextLine();
        	executeCommand(input);
      	}

   	}

   	//Executes a single command.
   	public void executeCommand(String input)
   	{
   		processInput(input);

   		//USER command
      	if (command.equals("USER")&&(state==0))
      	{
         	//Check if user is valid
         	if (mail.checkUsername(arg)==true)
         	{
         		out.println("S: +OK " + arg + " is a valid mailbox");
         		state = 1;
         		out.flush();

         	}
         	else
         	{
         		out.println("S: -ERR never heard of mailbox " + arg);
         		out.flush();

         	}
      	}

      	//PASS command
      	else if (command.equals("PASS")&&(state==1))
      	{
         	switch (state)
			{
				//If no user
				case 0: if(mail.checkPassword(arg)==false)
						{
							out.println("S: -ERR no user");
							out.flush();
						}
						break;

				//If theres user and checking password
				case 1: if(mail.checkPassword(arg)==true)
						{
							out.println("S: +OK test's maildrop has 0 messages");
							state = 2;
							out.flush();
						}
						else
						{
							out.println("S: -ERR invalid password");
							out.flush();
						}
						break;

				default: break;
			}
			out.flush();
      	}

      	//QUIT command
      	else if(command.equals("QUIT"))
		{
			out.println("S: +OK POP3 server signing off");
			out.flush();
			state = 0;
			//Reset state
		}

		//STAT command
      	else if(command.equals("STAT")&&(state==2))
		{
			out.println("S: +OK 0 0");
			out.flush();
		}

		//LIST command
		else if(command.equals("LIST")&&(state==2))
		{
			if(arg==null)
			{	
				out.println("S: +OK 0 0");
				out.flush();
			}
			else if(Integer.parseInt(arg)>0)
			{
				out.println("S: -ERR no such message");
				out.flush();
			}
			out.flush();
		}

		//RETR command
		else if(command.equals("RETR")&&(state==2))
		{
			out.println("S: -ERR inbox empty");
			out.flush();

		}

		//DELE command
		else if(command.equals("DELE")&&(state==2))
		{
			out.println("S: -ERR inbox empty");
			out.flush();
		}

		//NOOP commnad
		else if(command.equals("NOOP")&&(state==2))
		{
			out.println("S: +OK");
			out.flush();
		}

		//RSET command
		else if(command.equals("RSET")&&(state==2))
		{
			out.println("S: +OK");
			out.flush();
		}

		//TOP command
		else if(command.equals("TOP")&&(state==2))
		{
			out.println("S: -ERR no such message");
			out.flush();
		}

		//UIDL commnad
		else if(command.equals("UIDL")&&(state==2))
		{
			out.println("S: -ERR no such message, inbox empty");
			out.flush();
		}

		//default case
		else
		{
			out.println("S: -ERR no such command");
			out.flush();
		}
      
   	}

   	//Seperate input into command and argument
   	public void processInput(String input)
	{	
		//if command has argument
		if(input.matches(".*\\s+.*")==true)
		{	
			String[] seperated = input.split("\\s+");
			commandA = seperated[0];
			arg = seperated[1];			
		}
		else
		{
			commandA = input;
			arg = null;
		}

		//Shift all to upper case
		command = commandA.toUpperCase();

	}

   	private Socket s;
   	private Scanner in;
   	private PrintWriter out;
   	private DummyMailBox mail;
}
