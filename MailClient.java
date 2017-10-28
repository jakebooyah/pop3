import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener; 
import java.awt.event.WindowEvent;

public class MailClient 
{
  	JFrame frame;
	JTextArea textArea;
	JTextField textField;
	JScrollPane scrollPane;

	final static int SBAP_PORT = 8888;
	Socket s = new Socket("localhost", SBAP_PORT);
    InputStream instream = s.getInputStream();
    OutputStream outstream = s.getOutputStream();
    Scanner in = new Scanner(instream);
    PrintWriter out = new PrintWriter(outstream);
    String input;
    String output;

  	public static void main(String[] args) throws IOException
   	{
   		MailClient mail = new MailClient();	
   	}

   	//Create the application.
   	public MailClient() throws java.io.IOException
   	{
   		initate();
   	}

   	//Initialize the contents of the frame.
   	private void initate() 
   	{
   		frame = new JFrame();
		frame.setTitle("POP3");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() 
		{
        	@Override
        	public void windowClosing(WindowEvent e) 
        	{
            	try
            	{
            		s.close();
            		//close socket on exit
				}
				catch(IOException io)
				{	
					io.printStackTrace();
				}        	
        	}

    	});
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 394, 540);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.append("S: +OK POP3 server ready\n");
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.WHITE);
		textField.setBounds(0, 541, 394, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.requestFocusInWindow();
		textField.addKeyListener(new KeyAdapter() 
		{
			@Override
			//if enter key pressed
			public void keyPressed(KeyEvent press) 
			{
				if (press.getKeyCode()==KeyEvent.VK_ENTER)
					{
						//Display input
						textArea.append("C: " + textField.getText() + "\n");

						input = textField.getText();

						//sent to sever
						out.println(input);
      					out.flush();

      					//get server respond
      					output = in.nextLine();

      					//display respond
              			textArea.append(output + "\n");

                    	//Reset textField
                		textField.setText("");
					}
				}
			}
		);

   	} 	  	

}
