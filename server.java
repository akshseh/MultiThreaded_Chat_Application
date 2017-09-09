import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;
import java.net.*;
import java.net.ServerSocket;
import java.io.*;

public class server extends Thread
{
	static Socket sock = null;
	static ServerSocket socketServ = null;
	DataInputStream inp = null;
	public static server[] thread = new server[10];
	private PrintStream os = null;
	private int name = 0;
	public static int len = 0;
	static DataOutputStream out = null;

	public server(Socket sock, server[] thread, int index)
	{
		this.sock = sock;
		this.thread = thread;
		this.name = index;
		if(len < index){
			len = index;
		}
	}
	
	public void run()
	{
		server[] thread = this.thread;
		try
		{	
			inp = new DataInputStream(sock.getInputStream());			
			os = new PrintStream(sock.getOutputStream());
			String strin1 = "";
			os.println("Index + total "+this.name+ " " + len);

			while(!strin1.equals("exit"))
			{
				try
				{
					strin1 = inp.readLine();
					System.out.println("Message from Client "+ this.name +": " + strin1);
		    		String[] words = strin1.split("\\s");
		    		if(strin1.startsWith("List All"))
		    		{
						//System.out.println("he - list all");
		    			for (int k = 0; k <= this.len; ++k) 
		    			{	
		    				if(thread[k] != null)
		    				{
			    				thread[this.name].os.println("-> Client "+ k);
		    				}
		    			}
		    		}
		    		if(strin1.startsWith("All:"))
		    		{
			   		int j;
		    			String[] str = strin1.split("\\s",2);
					for (j= 0 ;j <= this.len; ++j) 
		    			{
		    				if(thread[j] != this && thread[j] != null)
		    				{
		    					thread[j].os.println("@All - Client "+ this.name + " says: "+ str[1]);
		    				}
		    			}
		    		}
				if(strin1.startsWith("Client"))
		    		{
					String[] word = strin1.split(":");
		    			String[] ste1 = word[0].split("\\s");
		    			String[] sr = ste1[1].split(",");
		    			for (String w:sr) {
							int i = Integer.parseInt(w);
							if(thread[i]!=null && i <=  this.len && thread[i]!=this){ //i <=  this.len
								thread[i].os.println("Personal Message from client "+ this.name + " : " + word[1]);    				
							}
							else if(thread[i]==this){
								thread[i].os.println("ERROR: You are sending yourself a message.");    				
							}
							else{
								thread[this.name].os.println("ERROR: Client " + i +" does not exist");
							}
		    			}
		    		}
		    	}
	    		catch(Exception ex)
	    		{
	    			System.out.println("Client disconnected...");
	    			break;	    		
	    		}
			}
			System.out.println("Connection has been closed !! ");
			thread[this.name] = null;
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
		}
		catch(IOException e){
			System.out.println("Exit...");
		}
	}

	public static void main(String[] args) throws Exception
	{
		final int port = 1234;
		ServerSocket socketServ = new ServerSocket(port);
		int i = 0;
		System.out.println("Creating server socket on port: "+ port);
		while(true)
		{	
			Socket soc = socketServ.accept();
			System.out.println("Accept Client "+i);
			System.out.println("Connected...");
			(thread[i] = new server(soc, thread, i)).start();
			i = i+1; 
		}
	}
}
