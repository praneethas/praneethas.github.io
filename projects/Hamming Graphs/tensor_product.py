# Tensor product of hamming graph
#-------How to run---
# Input Data- 
#1. Enter the value of n1 and d1 for graph G(n1,d1)
#2. Enter the value of n2 and d2 for graph G(n2,d2)

#It will print the number of vertices of the tensor product followed by vertices.
#Then, the edges between the vertices of the tensor product followed by total number of edges.




#decimal to binary conversion
def d2b(x):
    return int(bin(x)[2:])

#calculate the hamming distance between the vertices of the graph
def calculate_hamming_dis(xor_op):
    dis=0
    while(xor_op>0):
            rem=xor_op%10
            xor_op=int(xor_op/10)
            if(rem==1):
                dis=dis+1
    return dis

#give the edgelink between the vertices of the graph
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
                append_zero=''
                l=len(y1)
                if(l<n):
                    diff=n-l
                    for i in range(0,diff):
                        append_zero=append_zero+'0'
                    y1=append_zero+y1
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


#make each vertex of length n
def complete_the_length(x,n):
    x=str(x)
    append_zero=''
    l=len(x)
    if(l<n):
        diff=n-l
        for i in range(0,diff):
            append_zero=append_zero+'0'
        x=append_zero+x
        return x

#def check_for_edge():   

v1=[]
v2=[]
v3=[]
e1=[]
e2=[]

#Enter the value of n1 and d1 for the 1st graph
n1=int(input("Enter the value of n1:"))
num_of_vertices_g1=2**n1
d1=int(input("min distance d1:"))
for i in range(0,num_of_vertices_g1):
    x1=str(d2b(i))
    append_zero=''
    l=len(x1)
    if(l<n1):
        diff=n1-l
        for i in range(0,diff):
            append_zero=append_zero+'0'
        x1=append_zero+x1
    
   # x1=complete_the_length(x1,n1)
    v1.append(x1)
e1=edgelink(num_of_vertices_g1,d1,n1)



#Enter the value of n2 and d2 for the 2nd graph    
n2=int(input("Enter the value of n2:"))
num_of_vertices_g2=2**n2
d2=int(input("min distance d2:"))
for i in range(0,num_of_vertices_g2):
    x2=str(d2b(i))
    
    append_zero=''
    l=len(x2)
    if(l<n2):
        diff=n2-l
        for i in range(0,diff):
            append_zero=append_zero+'0'
        x2=append_zero+x2
    #x2=complete_the_length(x2,n2)
    v2.append(x2)
e2=edgelink(num_of_vertices_g2,d2,n2)



#for tensor product
num_of_vertices_in_tp= 2**(n1+n2)

for i in range(0,num_of_vertices_g1):
    for j in range(0,num_of_vertices_g2):
        tensor_graph_vertex=str(v1[i])+str(v2[j])
        v3.append(tensor_graph_vertex)

#print the vertex set of the tensor product
print("The total number of vertices in tensor product:= ")
print (num_of_vertices_in_tp)
print("The vertices of the tensor product are:")
print(v3)

print ("The edges exist between the following vertices:")

num_of_edges_in_tp=0
#checking for edges in the tensor product
for i in range(0,num_of_vertices_in_tp):
    for j in range(i+1,num_of_vertices_in_tp):
        l1=v3[i][:n1]
        l2=v3[j][:n1]
        r1=v3[i][n1:]
        r2=v3[j][n1:]
        for k in range(0,len(e1)):
               if((str(e1[k][0])==str(l1) and str(e1[k][1])==str(l2)) or (str(e1[k][1])==str(l1) and str(e1[k][0])==str(l2))):
                   for k1 in range(0,len(e2)):
                       if((str(e2[k1][0])==str(r1) and str(e2[k1][1])==str(r2)) or (str(e2[k1][1])==str(r1) and str(e2[k1][0])==str(r2))):
                           tp_l=l1+r1
                           tp_r=l2+r2
                           num_of_edges_in_tp=num_of_edges_in_tp+1
                           print (tp_l,tp_r)
             

print("The total number of edges in tensor product:= ")
print (num_of_edges_in_tp)
    
