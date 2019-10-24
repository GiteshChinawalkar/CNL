import java.io.*;
import java.net.*;
import java.util.*;
public class srS {
	
	public static void main(String[] args) {
		try 
		{
			System.out.println("----waiting for connection----");
			Scanner sc=new Scanner(System.in);
	        ServerSocket ss = new ServerSocket(3333);
			Socket s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			String st="";
			//System.out.println("FRAME SIZE: 10");
			System.out.println("----connection established----");
			System.out.print("enter m:- ");
		    int m = sc.nextInt();
		    int ws = (int)Math.pow(2,m-1);
		    System.out.println("window size:-"+ws);
			dos.writeInt(ws);
			System.out.println("enter the data");
			sc.nextLine();
			st=sc.nextLine(); 
			dos.writeInt(st.length());
			System.out.println(st.length());
			char a[] = st.toCharArray();
			int ack,fa,fno;
			int flag=0;
			//(i%ws == 0 && i>0)
			int cnt=0,i=0;
			//for (int i=0;i<a.length;i++) 
			while(i<a.length){
				 if(cnt==ws || flag ==1)
	             		{
					 fa=dis.readInt();
					 flag =0;
					 cnt=0;
					 if(fa != 1)
					 {
						 fno = dis.readInt();
						 System.out.println("----resending frames----");
						 dos.writeChar(a[fno-1]);
					     System.out.println("sent frame "+(fno-1)+":-" + a[fno-1]);
						 for(int j=i;j<i+ws-1;j++)
						 {
						 	if(j>=a.length)
							 	{
							 		break;	
							 	}
						 	dos.writeChar(a[j]);
						 	System.out.println("sent frame "+j+":-" + a[j]);
						 }
						 i+= ws-1;
						 
						 flag=1;
					 }
					 else
					 {	
					 	 //System.out.println("ack recieved for frame  " + i);
						 System.out.print("enter 1(yes).the ack is received 0(no).for not received:- ");
						 ack = sc.nextInt();
						 dos.writeInt(ack);


						 if(ack != 1)
						 {
							 int a1;
							 do{
							 	System.out.println("enter the no of lost ack: ("+(i-ws+1)+","+i+")");
							 	a1 = sc.nextInt();
							 }while(a1<(i-ws+1) || a1>i);
							 dos.writeInt(a1);
							 System.out.println("----resending frames----");
						 	 dos.writeChar(a[a1-1]);
					     	 System.out.println("sent frame "+(a1-1)+":-" + a[a1-1]);	
							 for(int j=i;j<i+ws-1;j++)
							 {
							 	if(j>=a.length)
							 	{
							 		break;	
							 	}
							 	dos.writeChar(a[j]);
							 	System.out.println("sent frame "+j+":-" + a[j]);
							 }
							 i =i+ws-1;
						
							 cnt=ws;
						 }
					 }
	             }
				
				 else
				 {
					 cnt++;
					 dos.writeChar(a[i]);
					 System.out.println("sent frame "+i+":- " + a[i]);
					 i++;
				 }
				}
				System.out.println("----actual data sent is----");
				for(i=0;i<a.length;i++)
				{
					System.out.print(a[i] + " ");
				}
			dis.close();
			dos.close();
			s.close();
			ss.close();
			sc.close();
		} 
		catch (IOException e)
		{
			System.out.println(e);
		} 
	}
}
