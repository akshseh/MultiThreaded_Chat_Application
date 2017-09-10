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
		// for (int i=0; i<index; i++ ) {
		// 	if(index>i)
		// 		thread[i].len = index;
		// }
	}

//	to find out num of clients : thread.length;

	public void run()
	{
		server[] thread = this.thread;
		try
		{	
			//System.out.println("heyy");
			inp = new DataInputStream(sock.getInputStream());			
			//out = new DataOutputStream(this.sock.getOutputStream());	
			os = new PrintStream(sock.getOutputStream());
			String strin1 = "";
		//	os.println("Index + Len"+this.name+len);
			//String strin2= "";
			//String[] a ;
		//	System.out.println("hey len : "+len);
		//	for (int j = 0; j < len; j++) {
        // if (thread[j] != null && thread[j] != this) {
        //   thread[j].os.println("*** A new user " + this.name
        //       + " entered the chat room !!! ***");
        // }
      //}

			while(!strin1.equals("exit"))
			{
				try
				{
				//	System.out.println("hey3y");

					//strin1 = (String)inp.readLine();
					strin1 = inp.readLine();

					System.out.println("Message from Client "+ this.name +": " + strin1);
		    		//Server message
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
			   			//System.out.println("he all");
		    			int j;
		    			String[] str = strin1.split("\\s",2);
						//System.out.println("len "+ this.len +" | "+this.name);

		    			for (j= 0 ;j <= this.len; ++j) 
		    			{
		    				//System.out.println("heyo"+j);
		    				//System.out.println(thread[j]==null);
		    				if(thread[j] != this && thread[j] != null)
		    				{
		    					thread[j].os.println("Client "+ this.name + " says: "+ str[1]);
		    				}
		    			}
		    		}
				//	System.out.println("outside if");
		    		if(strin1.startsWith("Client"))
		    		{
						//System.out.println("heyy clients");
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
								thread[this.name].os.println("ERROR:Client " + i +" does not exist");
							}
		    			}
		    		}

					//out.flush();
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
				socketServ.close();//myservice
				//try : and remove socketServ.close()
				//soc.close();
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
		final int port = 2222;
		ServerSocket socketServ = new ServerSocket(port);
		int i = 0;
		System.out.println("Creating server socket on port: "+ port);
		while(true)
		{	
			Socket soc = socketServ.accept();
			System.out.println("Accept Client "+i);
			System.out.println("Connected...");
			//thread[i] = new server(soc, thread, i);
			(thread[i] = new server(soc, thread, i)).start();
			i = i+1; 
		}
	}
}
