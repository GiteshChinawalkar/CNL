#include <iostream>
#include <fstream>
#include <iomanip>
#include <string>
using namespace std;

int main()
{

	string sr_no,time,source,destination,protocol,len,info;
	int count=-1,choice,i=0;

	do
	{
		count=-1;
		i=0;
		
		cout<<endl<<"**************Packet Analyser*************"<<endl;
		cout<<"1.ARP"<<endl;
		cout<<"2.ICMPv6"<<endl;                                 //select which packets to count.
		cout<<"3.TCP"<<endl;
		cout<<"4.UDP"<<endl;
		cout<<"Enter the Packet choice you want to count:"<<endl;
		cin>>choice;
		
		string protocolChoice;
	
		switch(choice)
		{
			case 1:
			protocolChoice="ARP";
			break;
			
			case 2:
			protocolChoice="ICMPv6";
			break;
			
			case 3:
			protocolChoice="TCP";
			break;
			
			case 4:                                       
			protocolChoice="UDP";
			break;
			
			default:
			protocolChoice="ARP";
			break;
		}
		
		ifstream file("file.csv");
		while(file.good())                   //read file till it has content
		{
			getline(file,sr_no,',');
			getline(file,time,',');
			getline(file,source,',');
			getline(file,destination,',');
			getline(file,protocol,',');
			getline(file,len,',');
			getline(file,info,'\n');
			
			protocol= string(protocol,1,protocol.length()-2);
			
			if(protocol=="Protocol" || protocol==protocolChoice)
			{                                                       //print that record on terminal
				cout<<setw(5)<<left<<i++;
				cout<<setw(15)<<left<<string(time,1,time.length()-2);
				cout<<setw(30)<<left<<string(source,1,source.length()-2);
				cout<<setw(30)<<left<<string(destination,1,destination.length()-2);
				cout<<setw(10)<<left<<protocol;
				cout<<setw(10)<<left<<string(len,1,len.length()-2);
				cout<<string(info,1,info.length()-2)<<endl;
				count++;
			}
		}
		
		file.close();
		cout<<"Total Number of Packets: "<<count<<endl;
	}while(choice!=0);
	
return 0;
}
