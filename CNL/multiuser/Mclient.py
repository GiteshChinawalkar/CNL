import socket
import os
import threading

class Client:
	def create(self):
		self.ip='127.0.0.1'
		self.port= 25000
		self.c= socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
		print("Socket Binded Successfully!!!")
		self.c.sendto(bytes('',"utf-8"),(self.ip,self.port))
		self.chatwindow()
		
	def sender(self):
		while True:
			msg= bytes(input(),"utf-8")
			self.c.sendto(msg,(self.ip,self.port))
			
	def reciever(self):
		while True:
			(msg,addr)=self.c.recvfrom(1024)
			if("|||" in msg.decode("utf-8")):
				(msg,addr)=msg.decode("utf-8").split("|||")
				print("Message From"+ str(addr)+">"+ str (msg))
			else:
				print("Message From"+ str(addr)+">"+ str (msg.decode("utf-8")))
		
	def chatwindow(self):
		print("Chat Client Initiated\n")
		threadS= threading.Thread(target= self.sender)
		threadR= threading.Thread(target= self.reciever)
		threadS.start()
		threadR.start()
		
if __name__=='__main__':
	client= Client()
	client.create()
	client.chatwindow()
	
