
import java.util.*;
import java.io.*;
import java.net.*;
public class gbnC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner sc= new Scanner(System.in);
			Socket s= new Socket("localhost",3000);
			DataInputStream dis= new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			int ws= dis.readInt();
			System.out.println("the window size is:" + ws);
			int size= dis.readInt();
			char a[] = new char[size];
			int flag=0,fa=0;
			int cnt=0,fno;
			int i=0,p=0;
			while(i<a.length)
			{
				if(cnt==ws || flag==1)
				{
					System.out.print("enter 1(yes).for no frame loss 0.(no)for frame loss ");
					fa = sc.nextInt();
					dos.writeInt(fa);
					cnt=0;
					flag =0;
					if(fa!=1)
					{
						do 
						{
							System.out.print("enter lost frame:-("+(i-ws)+","+(i-1)+")");
							fno=sc.nextInt();
							
						}while(fno<(i-ws) || fno>(i-1));
						dos.writeInt(fno);
						//dd
							 //System.out.println("ppppp"+ p+"iiiiii"+i);
						for(int j=i-ws;j<fno;j++)
						{
							if(j>=a.length)
						 	{
						 		break;	
						 	}
							a[j]=dis.readChar();
							System.out.println("RECEIVED FRAME"+j+":-"+ a[j]);
						}
						//
						for(int j=fno;j<fno+ws;j++)
						{
							if(j>=a.length)
						 	{
						 		break;	
						 	}
							a[j]=dis.readChar();
						}
						i=fno+ws;
						flag=1;
						
					}
					else
					{

						//int k=0;
						for( int k=i-ws;k<i;k++)
						{

							System.out.println("RECEIVED FRAME"+k+":-"+ a[k]);
						}

						int ack=dis.readInt();
						if(ack == 1)
							System.out.println("ack received");
						else
						{
							//System.out.println("Ack not received");
							int a1 = dis.readInt();
						
							for(int j=a1;j<a1+ws;j++)
							{
								if(j>=a.length)
							 	{
							 		break;	
							 	}
								a[j]=dis.readChar();
								//System.out.println("received frame"+j+":-" + a[j]);
							}
							i=a1+ws;
							cnt =ws;
							
						}
					}
				}
				else
				{
					cnt++;
					a[i]=dis.readChar();
					i++;
					p++;
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
