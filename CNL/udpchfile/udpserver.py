import socket
import os
import threading

class Server:
	def create(self):
		self.s = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
		self.port = 5000
		self.s.bind(('',self.port))

	def receive(self):
		while True:
			msg,self.addr = self.s.recvfrom(1024)
			print("Message:"+str(msg.decode("utf-8")))
			if(str(msg.decode("utf-8"))=='bye'):
				print("Closing Server!!")
				self.s.close()
				break
	def send(self):
		while True:
			msg = bytes(input(),"utf-8")
			self.s.sendto(msg,(self.addr))
			if(str(msg.decode("utf-8"))=='bye'):
				print("Closing Server!!")
				self.s.close()
				break

	def ChatWindow(self):
		threadS = threading.Thread(target = self.send)
		threadR = threading.Thread(target = self.receive)
		threadS.start()
		threadR.start()

	def receivefilename(self):
		while True:
			filename1=""
			filename1 , self.addr = self.s.recvfrom(1024)
			filename1 = str(filename1.decode("utf-8"))
			if filename1 != "":
				f = open(filename1,"wb")
				print("Name of File : " + f.name)
				self.filename = f.name
				self.writefile()
			
	def writefile(self):
		f = open(self.filename,'+a')
		print("Filename in writefile" + f.name)
		while True:
			f = open(self.filename,'+a')
			msg , addr = self.s.recvfrom(1024)
			f.write(str(msg.decode("utf-8")))
			print(str(msg.decode("utf-8")))

if __name__ == '__main__':
	server = Server()
	server.create()

	print("1.Chat 2.Receive File")
	choice = int(input())
	if choice==1:
		server.ChatWindow()
	else:
		server.receivefilename()
