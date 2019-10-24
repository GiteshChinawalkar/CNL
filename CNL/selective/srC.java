import java.net.*;
import java.io.*;
import java.util.*;

public class srC{

	public static void main(String[] args) {
		try 
		{
		
			Scanner sc = new Scanner(System.in);
			Socket s = new Socket("localhost", 3333);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			
			int ws = dis.readInt();	
			//System.out.println("FRAME SIZE: 10");
			System.out.println("window size:-" + ws);
			int size = dis.readInt();
            		char a[] = new char[size];
			int fa=0;
			int fno=0;
			int flag =0;
			int cnt=0;
			//(i%ws == 0 && i>0)
			//for (int i=0; i<a.length; i++)
			int i=0;
			int a1=0,ack=1;
			while(i<a.length)
			{
				if(cnt==ws || flag==1)
				{
					//asking for frame loss
					System.out.print("enter 1(yes).for no frame loss 0.(no)for frame loss");
					fa = sc.nextInt();
					dos.writeInt(fa);	//sending to server if ack loss
					cnt=0;
					flag =0;
					if(fa != 1)
					{
						//fno = sc.nextInt();
						do{
							System.out.print("enter lost frame:-("+(i-ws+1)+","+i+")");
							 	//System.out.println("Enter the no of lost ack: ("+(i-ws+1)+","+i+")");
							 	fno = sc.nextInt();
							 }while(fno<(i-ws+1) || fno>i);
						
						dos.writeInt(fno);	//sending frame no to server
						a[fno-1] = dis.readChar();
						System.out.println("----buffered frames:----");
						for(int k=fno;k<i;k++)
						{
							if(k!=fno-1)
							{
								System.out.println(a[k]);
							}
							else
							{
								System.out.println(" ");	
							}
						}
						
						/*for(int j=i;j<i+ws-1;j++)
						{
							a[j]=dis.readChar();
						}*/
						int j=i;
						while(j<i+ws-1&&j<a.length)
						{
							
							a[j]=dis.readChar();
							j++;
						}
						i=i+ws-1;
						flag =1;
					}
					else
					{
						if(fa!=1&&fno>0){
							System.out.println("received frame:-" + a[fno-1]);
						}
						if(ack!=1&&a1>0)
						{
							System.out.println("Received frame:- " + a[a1-1]);

						}
						for(int k=i-ws;k<i;k++)
						{
							//System.out.println(i+"     "+k);
								System.out.println("Received frame:- " + a[k]);
						}
						//System.out.println("ack sent for frame "+i);
						 ack=dis.readInt();
						if(ack == 1)
							System.out.println("Ack received");
						else
						{
							//System.out.println("Ack not received");
							a1 = dis.readInt();
							a[a1-1] = dis.readChar();
							System.out.println("----buffered frames:----");
							for(int k=a1;k<i;k++)
							{
								if(k!=a1-1)
								{
									System.out.println(a[k]);
								}
								else
								{
									System.out.println(" ");	
								}
							}
							
							/*for(int j=i;j<i+ws-1;j++)
							{
								a[j]=dis.readChar();
							}*/
							int j=i;
						while(j<i+ws-1&&j<a.length)
						{
							a[j]=dis.readChar();
							j++;
						}
							i=i+ws-1;
							cnt =ws;
						}
					}
				}
				else
				{	
					cnt++;
					a[i] = dis.readChar();
					i++;
				}
			}
			System.out.println("----actual data received is---- ");
			for(i=0;i<a.length;i++)
			{
				System.out.print(a[i] + " ");
			}
			dis.close();
			dos.close();
			sc.close();
			s.close();			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

