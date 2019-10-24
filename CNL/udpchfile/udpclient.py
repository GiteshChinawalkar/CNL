import socket
import os
import threading

class Client:
	def create(self):
		self.c= socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
		self.port=5000
		self.ip="127.0.0.1"
		self.c.sendto(bytes("","utf-8"),(self.ip,self.port))
		print("Binded")

	def send(self):
		while True:
			msg = bytes(input(),"utf-8")
			self.c.sendto(msg,(self.ip,self.port))
			if(str(msg.decode("utf-8"))=='bye'):
				print("Closing Client!!")
				self.c.close()
				break
	
	def recieve(self):
		while True:
			msg,self.addr = self.c.recvfrom(1024)
			print("Message from Server : "+str(msg.decode("utf-8")))
			if(str(msg.decode("utf-8"))=='bye'):	
				print("Closing Client!!")
				self.c.close()
				break
			
	def sendfilename(self):
		print("Enter filename")
		self.filename1 = bytes(input(),"utf-8")
		self.c.sendto(self.filename1,(self.ip,self.port))
		self.readfile()

	def readfile(self):
		self.buf = 1024
		filename1 = str(self.filename1.decode("utf-8"))
		f = open(filename1,"r")
		data = f.read(self.buf)
		while data:
			self.c.sendto(bytes(data,"utf-8"),(self.ip,self.port))
			data = f.read(self.buf)
	def ChatWindow(self):
		threadS = threading.Thread(target = self.send)
		threadR = threading.Thread(target = self.recieve)
		threadS.start()
		threadR.start()



if __name__ == '__main__':
	client = Client()
	client.create()
	print("1.Chat 2.Send File")
	choice = int(input())
	if choice==1:
		client.ChatWindow()
	else:
		client.sendfilename()
		
