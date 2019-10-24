#include "bits/stdc++.h"
using namespace std;

int main()
{
	int data[11],rec[11],parity[4];
	
	cout<<"*************Hamming Code***********"<<endl;
	cout<<"Enter the 7 bits by giving space:"<<endl;
	for(int i=10;i>=0;i--)
	{
		if(i==0 || i==1 || i==3 || i==7)
			continue;
		cin>>data[i];
	}
	
	data[0]=data[2]^data[4]^data[6]^data[8]^data[10];
	data[1]=data[2]^data[5]^data[6]^data[9]^data[10];
	data[3]=data[4]^data[5]^data[6];
	data[7]=data[8]^data[9]^data[10];
	
	cout<<"\nInput data:";
	for(int i=10;i>=0;i--)
	{
		if(i==0 || i==1 || i==3 || i==7)
			continue;
		cout<<data[i];
	}
	
	cout<<"\nMessage Frame Sent:";
	for(int i=10;i>=0;i--)
		cout<<data[i];
		
	cout<<"\n\nEnter the Recieved Data seperated by space:"<<endl;
	for(int i=10;i>=0;i--)
	{
		cin>>rec[i];
	}
	
	parity[0]=rec[0]^rec[2]^rec[4]^rec[6]^rec[8]^rec[10];
	parity[1]=rec[1]^rec[2]^rec[5]^rec[6]^rec[9]^rec[10];
	parity[2]=rec[3]^rec[4]^rec[5]^rec[6];
	parity[3]=rec[7]^rec[8]^rec[9]^rec[10];
	
	if(parity[0]==0 && parity[1]==0 && parity[2]==0 && parity[3]==0)
	{
		cout<<"NO ERROR!!!!"<<endl;
	}
	else
	{
		cout<<"\nERROR!!!!!!!!!!!!!!!\n";
		cout<<"Binary Position: ";
		for(int i=3;i>=0;i--)
			cout<<parity[i];
			
		cout<<"\nDecimal Position: ";
		int pos=0;
		for(int i=0;i<4;i++)
		{
			pos=pos+(parity[i]*pow(2,i));
		}
		cout<<pos<<endl;
	}
}		
