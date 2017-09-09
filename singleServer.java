//import java.util.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;
import java.net.*;
import java.net.ServerSocket;
import java.io.*;

public class Server //implements Runnable
{
	//static DataInputStream inp = null;
	//static Socket sock = null;
	//static ServerSocket socketServ = null;
	//static DataOutputStream out = null;
	//static InputStreamReader ia = null;
	//static BufferedReader read = null;
	static OutputStream ostream = null;
	//static InputStreamReader input = null;

	// Server(Socket sock)
	// {
	// 	this.sock = sock;
	// }

	// public void run()
	// {
	// 	try
	// 	{	
	// 		//accept client
	// 		//Input data from client

	// }

	public static void main(String[] args) throws Exception
	{
		final int port = 1234;
		ServerSocket socketServ = new ServerSocket(port);
		System.out.println("Creating Server socket on port: "+ port);
//		while(true)
//		{	
		System.out.println("Creating Server socket on port: ");

			Socket sock = socketServ.accept();
			System.out.println("Accept Client.");
			System.out.println("Connected...");
//			new Thread(new Server(soc)).start();
//		}
				//System.out.println("heyy");
			DataInputStream inp = new DataInputStream(sock.getInputStream());
			
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());	

			String strin1 = "";
			String strin2= "";
			String[] a ;

			while(!strin2.equals("close"))
			{
				try
				{
					strin1 = (String)inp.readUTF();
					System.out.println("Message from Client: " + strin1);
		    	
		    		 StringBuilder temp = new StringBuilder(strin1.length() + 1);
					if(strin1.length() > 1){
						a = strin1.split(" ");
						for (int i = a.length - 1; i > -1; i--) {
						    temp.append(a[i]).append(' ');
						}
						temp.setLength(temp.length() - 1);  // Strip trailing space
						strin2 = temp.toString();
					}
					else{
						strin2 = strin1;
					}
					//Write to Socket for Client
					out.writeUTF(strin2);
					out.flush();
		    	}
	    		catch(Exception ex)
	    		{
	    			System.out.println("Client didnt send a proper msg");
	    			break;	
	    		}
			}
			System.out.println("Connection has been closed !! ");
			try
			{
				sock.close();
				inp.close();
				socketServ.close();
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
		//catch(IOException e){
		//	System.out.println("Excepeppeep");
		//}
	}
}
