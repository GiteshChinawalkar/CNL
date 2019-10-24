//package swp;
import java.util.*;
import java.net.*;
import java.io.*;

public class gbnS
{
	public static void main(String args[]) 
	{
		try
		{
			Scanner sc= new Scanner(System.in);
			ServerSocket ss = new ServerSocket(3000);   //
			Socket s= ss.accept();   //
			DataInputStream dis= new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			System.out.println("connected");
			String st=" ";
			System.out.println("Enter value of m");
			int m = sc.nextInt();
			int ws= (int)Math.pow(2, m)-1;
			System.out.println("the window size is: "+ws);
			dos.writeInt(ws);
			System.out.println("Enter the data");
			sc.nextLine();     //
			st=sc.nextLine();
			dos.writeInt(st.length()); //
			char a[]= st.toCharArray(); //
			//System.out.println("char array is "+a);
			int flag=0,fa;
			int cnt=0,fno;
			int i=0,ack;
			int p=0,b=0, o=0;
			while(i<a.length)
			{
				if(cnt==ws || flag==1)
				{
					fa=dis.readInt();
					 flag =0;  //
					 cnt=0;   //
					 if(fa!=1)
					 {
						 fno=dis.readInt();
						 for(int j=p-ws;j<fno;j++)  // loop for sending the frames till the  frame which  is lost 
						 {
							 if(j>a.length)
							 {
								 break;
							 }
							 dos.writeChar(a[j]);
						 }
						 System.out.println("resending");
						 for(int j=fno; j<fno+ws;j++)  // loop for sending the frames from lost frame onwards
						 {

							 if(j>a.length)
							 {
								 break;
							 }
							 dos.writeChar(a[j]);
							 System.out.println("sent frame "+j+":"+a[j]);
						 }
						 i=fno+ws;  // important to reset i to new place
						 flag=1;
					 }
					 else
					 {
						 System.out.print("enter 1(yes).the ack is received 0(no).for not received:-");
						 ack = sc.nextInt();
						 dos.writeInt(ack);
						 
						 if(ack!=1)
						 {
							 int a1;
							 do
							 {
								 	System.out.println("enter no of lost ack :-("+(i-ws)+","+(i-1)+")");
								 	a1 = sc.nextInt();
							 }while(a1<(i-ws) || a1>(i-1));
							 
							 dos.writeInt(a1);
							 
							 for(int j=a1;j<a1+ws;j++)   // resending frames from lost ack frame
							 {
							 	if(j>=a.length)
							 	{
							 		break;	
							 	}
							 	dos.writeChar(a[j]);
							 	System.out.println("sent frame "+j+":-" + a[j]);
							 }
							 i=a1+ws;
							 cnt=ws;
						 }
					 }
				}
				else
				{
					cnt++;
					dos.writeChar(a[i]);
					p=i+1;     //
					System.out.println("Sent Frame "+i+":"+a[i]);
					i++;
				
				}
			}
			System.out.println("----actual data sent is---- ");
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
