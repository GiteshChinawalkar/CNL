import sys                                             #import system files


(addrString, cidrString)= sys.argv[1].split('/')       #takes address and cidr from argv 1


addr=addrString.split('.')                             # Address
cidr=int(cidrString)


cl=int(addr[0])
if cl<=126 and cl>=1:
	print("Class A")                               #Display CLASS
if cl<=191 and cl>=128:
	print("Class B")
if cl<=223 and cl>=192:
	print("Class C")
if cl<=239 and cl>=224:
	print("Class D")
if cl<=254 and cl>=240:
	print("Class E")


mask=[0,0,0,0]
for i in range(cidr):                                 #Netmask
	mask[i//8]=mask[i//8] + (1<<(7-i%8))


net= []
for i in range(4):
	net.append(int(addr[i]) & mask[i])          #Network


broad= list(net)
brange=32-cidr                
for i in range(brange):                               #Broadcast range
	broad[3-i//8]=broad[3-i//8] + (1<<(i%8))


net_3 = net[3]
broad_3 = broad[3]
diff = broad_3 - net_3                    #Finding Subnets
no_subnets = (1<<(cidr - 24))
max = int((diff + 1) / no_subnets)

ll=[]
ul=[]
for i in range(no_subnets):
	ll.append(0)
	ul.append(0)
                                                     #Finding limits
n_max=0
max1=int(max)
for i in range(no_subnets):
	n_max = n_max + max
	ll[i] = net[3] + n_max - max		
	ul[i] = net[3] + n_max - 1 

lb=[]
ub=[]
for i in range(3):
	lb.append(0)
	ub.append(0)                               #Duplicating First 3 from broadcast

for i in range(3):
	lb[i]=broad[i]
	ub[i]=broad[i]


print("Address: " , addrString)
print("Netmask: " , ".".join(map(str,mask)))
print("Network: " , ".".join(map(str,net)))
print("BRange: " , ".".join(map(str,broad)))
print("SUBNETS:")
for i in range(no_subnets):
	print("Subnet" , i ,":", ".".join(map(str,lb)) , ll[i] , "-->" , ".".join(map(str,ub)) , ul[i])






