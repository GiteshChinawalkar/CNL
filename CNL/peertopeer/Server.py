import socket
import sys                        #Libraries
import threading

sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)               #Socket Creation

server_address = ('127.0.0.1',5000)                     #Server creation
print('Starting Up on {} port {} '.format(*server_address))                   # *IMPPPP
sock.bind(server_address)      
data=0                               #bindind addr to socket
address=()

def getmesg():
	while True:              #T always capital
		global data
		global address      
		(data,address)=sock.recvfrom(4096)               #Get message code from client
		print('From Client:{}>{}'.format(address,data.decode(encoding='UTF-8')))
		sys.stdout.flush()
		sys.stdin.flush()
		
def sendmesg():
	while True:
		message = bytes(input(),encoding='UTF-8')
		sock.sendto(message,address)
		sys.stdout.flush()
		sys.stdin.flush()
		
def main():
	thread1 = threading.Thread(target=getmesg,args=())
	thread2 = threading.Thread(target=sendmesg,args=())
	thread1.start()
	thread2.start()
	
if __name__=='__main__':
	main()

