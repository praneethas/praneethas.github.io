def calculate_hamming_dis(xor_op):
    dis=0
    while(xor_op>0):
            rem=xor_op%10
            xor_op=int(xor_op/10)
            if(rem==1):
                dis=dis+1
    return dis

def d2b(x):
    return int(bin(x)[2:])

def edgelink(num_of_vertices,d,n):
    e=[]
    l=[]
    for i in range(0,num_of_vertices):
        y1=d2b(i)
        y11=y1
        y1=str(y1)
        for j in range(i+1,num_of_vertices):
            dis=0
            y2=d2b(j)
            y22=y2
            y2=str(y2)
            xor_op=y11^y22
            dis= calculate_hamming_dis(xor_op)
            if(dis>=d):
                #y1=complete_the_length(y1,n)
                append_zero=''
                l=len(y1)
                if(l<n):
                    diff=n-l
                    for i in range(0,diff):
                        append_zero=append_zero+'0'
                    y1=append_zero+y1
               # y2=complete_the_length(y2,n)
                append_zero=''
                l=len(y2)
                if(l<n):
                    diff=n-l
                    for i in range(0,diff):
                        append_zero=append_zero+'0'
                    y2=append_zero+y2
                l=[y1,y2]
                e.append(l)
    return e


n=int(input("Enter the value of n:"))
num_of_vertices=2**n
d=int(input("min distance:"))
v=[]
num_of_edge=0

#for vertices of the hamming graph
for i in range(0,num_of_vertices):
    x=str(d2b(i))
    append_zero=''
    l=len(x)
    if(l<n):
        diff=n-l
        for i in range(0,diff):
            append_zero=append_zero+'0'
        x=append_zero+x
    
   # x1=complete_the_length(x1,n1)
    v.append(x)
print("The number of the vertices in hamming graph:")
print(num_of_vertices)
print("The vetices of the hamming graph are:")
print (v)
e= edgelink(num_of_vertices,d,n)
print("The edges exists between the following edges: ")
for i in range(0,len(e)):
    print (e[i])
    num_of_edge=num_of_edge+1
print("The total number of edges in the hamming graph:")
print(num_of_edge)
