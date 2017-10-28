Changes from previous code:

Code from Command Interpreter's method handleinput now integrated into MailService 
to ease creating sockets and communication between classes.

Database classes from first part of the coursework code is now all compiled into a class call DummyMailBox.
Since database backend is not the main prirority now. It is only for testing purposes.

Created a client for testing the server. Main GUI from previous code is now in MailClient.

Created a server that will start MailService in multiple threads. 


To run:

Compile MailServer
Compile MailClient

Run MailServer and MailClient in seperate terminal.

