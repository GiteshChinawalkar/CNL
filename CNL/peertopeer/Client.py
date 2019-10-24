import socket
import sys
import threading

sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

server_addr = ('127.0.0.1',5000)

def getmesg():
	while True:
		global data
		global address                                  #Get code from Server
		(data,address) = sock.recvfrom(4096)                  
		print('From Server:{}>{}'.format(address,data.decode(encoding='UTF-8')))
		sys.stdout.flush()
		sys.stdin.flush()
		
def sendmesg():
	while True:
		message = bytes(input(),encoding='UTF-8')
		sock.sendto(message,server_addr)
		sys.stdout.flush()
		sys.stdin.flush()
		
def main():
	thread1 = threading.Thread(target=getmesg,args=())
	thread2 = threading.Thread(target=sendmesg,args=())
	thread1.start()
	thread2.start()
	
if __name__=='__main__':
	main()
		
