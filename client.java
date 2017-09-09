import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;
import java.net.*;
import java.io.*;


public class client
{
	static Socket soc = null;
	static DataOutputStream out = null;
	static DataInputStream in = null;
	static BufferedReader br = null;
	static InputStreamReader istrRead = null;

	public static void main(String[] args) throws Exception
	{
		//arg[0] - IP address //arg[1] - Port
		try
		{
			soc = new Socket("127.0.0.1",1234);
			//takes input from terminal
			System.out.println("Connection established");
			//Scjset
			in = new DataInputStream(soc.getInputStream());	
			//sends output to socket
			out = new DataOutputStream(soc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} 
		catch(Exception e)
		{
			System.out.println(e);
		}
		String st = "";
		String st2 = "";

		while(!st.equals("close"))
		{
			try
			{
				st = br.readLine();
				out.writeUTF(st);
				//Flishshsh
				out.flush();
				st2 = in.readUTF();
				System.out.println("Message from server: " + st2);
			}
			catch(Exception ex)
			{
				System.out.println("Server Didn't send a proper message.");
				break;
			}
		}
		System.out.println("Connection closed.");
		try
		{
			out.close();
			in.close();
			soc.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
