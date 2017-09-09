import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;
import java.net.*;
import java.io.*;


public class client extends Thread 
{
	static Socket soc = null;
	static DataOutputStream out = null;
	static DataInputStream in = null;
	static PrintStream priOstr = null;
	static BufferedReader br = null;
	static int flag = 0;
		

	public void run(){
		try{
			String str = in.readLine();
			while(str!=null && str.indexOf("close")< 0){
				str = in.readLine();
			}
		flag = 1;
		}
		catch(IOException r){
			System.out.println("Exceptionn");
		}
	}

	public static void main(String[] args) throws Exception
	{
		try
		{
			soc = new Socket("127.0.0.1",1234);
			System.out.println("Connection established");
			in = new DataInputStream(soc.getInputStream());	
			priOstr = new PrintStream(soc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} 
		catch(IOException e)
		{
			System.out.println("IO problem ");
		}
		new Thread(new client()).start();
		while(flag == 0)
		{
			try
			{
				priOstr.println(br.readLine());
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
			priOstr.close();
			in.close();
			soc.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
