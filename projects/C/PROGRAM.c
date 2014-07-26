/*THIS PROJECT IS MADE BY:
  PRANEETH A S
  UG201110023
  ROHITH YERAVOTHULA
  UG201110039*/


//defining library functions
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<stdio.h>
#include<time.h>
//DEFINING FUNCTIONS
void crtacc();
void eme(int);
void trns(int,int);
void view(int);
void Aview();
void print(int);
void withdraw(int);
void deposit(int);
void chequep(int);
void tbook(int);


//STRUCTURE DEFINITION
typedef struct {
char Ebook[50];
long int accno;
char name[50];
char sname[50];
char city[20];
char Tbook[50];
float netamount;
     char psd[50];
              }acc;
            acc a[1000];//defining structure variables

//GLOBAL VARIABLES
int i=0,k=1;
float v;
long int j=11001;   //FOR BANK ACCOUNT NO.
long int r;
int h,g,av;

FILE *fo,*fp;        //GlOBAL FILE POINTER

char amd[50],ad[50],Afile[50];
char mpwd[50],epwd[50],*ptr;
time_t rawtime;
struct tm* timeinfo;
main()
  {time(&rawtime);
timeinfo=localtime(&rawtime);
    printf("STATE BANK OF INDIA\n");
    printf("Branch:Jodhpur\n");
    printf("\nEnter the name of administrator(admin):Use capital or small letters\n");
        scanf("%s",amd);
   printf("\nEnter the password below which you wish to set for this admin:\n");
   scanf("%s",mpwd);
        system("clear");
        int d,x;
        char filename[20];
        int c,n,k;
        long int l;
   system("clear");
   
   abc:
   printf("\n=============================\n");
   printf("\nWELCOME TO SBI BANKING\n");
   printf("\nType 1. for administrator\n");
   printf("\n     2. for customer\n");
   printf("\n============================\n");
   scanf("%d",&d);
   if(d==1)                               //administrator function starts
           {
                strcpy(Afile,"Afile");
                printf("\nEnter your name:\n");  //username for the admin
       scanf("%s",ad);
                ptr=getpass("\nEnter the password:\n");  //password for the admin
       strcpy(epwd,ptr);
                if((strcmp(mpwd,epwd)==0)&&(strcmp(amd,ad)==0))  //password check
          {
              system("clear");
              
              printf("\n=============================\n"); //entry into admin successful
                       printf("\nWelcome administrator\n");
                       printf("\nChoose from the following\n");  //listing of available options
              printf("\n1. To open a new account for the customer \n");
              printf("\n2. To delete an account for the existing customer\n");
              printf("\n3. To view all customer's account with their account numbers\n");
                  printf("\n4. To view customers having balance less then minimum balance\n");
                       printf("\n5. To view customer account\n");
                       printf("\n=============================\n");
              printf("\nENTER YOUR OPTION\n");
              scanf("%d",&x);                         //option entering scanning
                  system("clear");
                  system("PAUSE");
                  switch(x)
                            {                                //case evaluation starts
                                case 5:
               Aview();
               goto abc;

                                case 1:
                                        crtacc();
                                        goto abc;

                                case 2:
                                        printf("\nWant to delete a file?Sir,you have choosen to delete an account holder's file\n");
                                        printf("\nEnter the name of the customer's E-account book you desire to remove\n");
scanf("%s",filename);
remove(filename);
                                        printf("\nEnter the name of the customers T-book you desire to remove\n");
                                        scanf("%s",filename);
remove(filename);  
printf("\nFILE DELETED SUCCESSFULLY\n");
system("PAUSE");
system("clear");
g--;
goto abc;

                                case 3:
           for(h=0;h<g;h++)
               {
                   printf("%s   %s    %ld\n",a[h].name,a[h].sname,a[h].accno);
                                            }
                                            goto abc;


                                case 4:

                                        printf("\nNAME           SURNAME   ACCNO       BALANCE\n");
                                        for(k=0;k<h;k++)
{
   if(a[k].netamount<=500)
                                                      {
                                                          printf("%s   %s   %ld  %f\n",a[k].name,a[k].sname,a[k].accno,a[k].netamount);
         }
}
goto abc;
                    }
              }

    else
    {
        printf("\a\a\a\a\a\a\a\a\a\a\a\a\a\a\a\a\a\a");
        printf("\nThe administrator name or the password you've typed is incorrect.Please type another time.Ensure the status of caps lock key\n");
        goto abc;
    }
}














else
     {
/*CUSTOMER ARENA*/ printf("\nWelcome customer\n");

printf("\nEnter your account number\n");
scanf("%ld",&l);
n=(l%1000);
printf("\nEnter your name:\n");
scanf("%s",ad);
ptr=getpass("Enter password:");
strcpy(epwd,ptr);
system("clear");
if((strcmp(a[n-1].psd,epwd)==0)&&(strcmp(a[n-1].name,ad)==0))
                                                                   {

  printf("\nWhich action do you want to perform?\n");
printf("\n=============================\n");
printf("\nMENU LIST\n");
printf("\n1. ATM facility \n");
printf("\n2. E-cheque book facility to withdraw money\n");
printf("\n3. To deposit money\n");
printf("\n4. For inter account transfer\n");
                                    printf("\n5. For EMERGENCY\n");
printf("\nChoose from the above\n");
printf("\nENTER YOUR OPTION\n");
scanf("%d",&c);
       if(c==5)
          {
              printf("\nYou have opted for emergency.Do you want to do it really???????\n");
              printf("1. for yes\n");
                                                                        printf("\nany thing for no\n");

              int ax;
                                           printf("\nYOUR OPTION\n");
                                           scanf("%d",&ax);
              if(ax==1)
                 {
                      eme(n-1);
                      goto abc;
                                              }
      else
      goto abc;
          }

                                    if(c==4)
                                       {
                                             printf("\nEnter the account no. to whom  you want to transfer the money\n");
                                             scanf("%ld",&r);
                                             h=(r%1000);
            trns(n-1,h-1);
            goto abc;
                                      }


            if(c==1)
              {
                  printf("\nYou opted for ATM facility\n");
                                           printf("\nPlease insert your card with the label side up\n");
          system("PAUSE");
          printf("\a\a\a\a\a\a\a\a\a\a\a\a\a\a");
          printf("\nEnter your password\n");
            printf("0. View your Tbook\n");
          printf("1. View your Ebook\n");
          printf("2. Withdraw\n");
                                           printf("3. to know your account balance\n");
          int q;
                  printf("\nYOUR OPTION:\n");
                  scanf("%d",&q);
                        if(q==0)
{tbook(n-1);goto abc;
}



                    if(q==1)
                                    {
                                          view(n-1);
                                                       goto abc;
                                                }

                                            if(q==3)
           {
                                           print(n-1);
           goto abc;
                                                }

                                            if(q==2)
                                                {
           withdraw(n-1);
                                                        goto abc;
           }
       }




                        else if(c==2)
{
printf("\nYou opted for E-cheque book facility\n");
chequep(n-1);
}

          else if(c==3)
                         {

                         deposit(n-1);
                         goto abc;
                         }


                         goto abc;
                        }

                        else
       {
           printf("\nPassword and administrater name did not match please try once again\n");
           goto abc;
                                }

}
goto abc;
}






void crtacc()
{
  system("clear");
  printf("\nWant to enter a new record?\n");
  printf("***********************************");
  printf("\nEnter the details of the customer\n");
  printf("\nEnter customer's name:\n");
  scanf("%s",a[i].name);
  printf("\nEnter customer's surname:\n");
  scanf("%s",a[i].sname);
  printf("\nEnter customer's city:\n");
  scanf("%s",a[i].city);
  printf("\nEnter the amount you want to deposit:\n");
  scanf("%f",&v);
  printf("\n**********************************************\n");
  if((v)<500)
  {
      printf("\nYour account requires minimum of Rs.500.So please deposit amount higher than that\n");
      crtacc();
  }
  else
    {
        a[i].netamount=v;
        a[i].accno=j;
        printf("\n***************************************\n");
        printf("\nYour account number is %ld\n",a[i].accno);
        printf("\n***************************************\n");
                q:
                printf("\nEnter a name for your E-account book:\n");
                scanf("%s",a[i].Ebook);
                printf("\nEnter a name for your Tbook\n");
                scanf("%s",a[i].Tbook);  
                printf("\nChoose a password for your account and ATM:\n");
                scanf("%s",a[i].psd);
        if(fo=fopen(a[i].Ebook,"r"))
                {
                       printf("\nThe file with name you've typed already exists.Please type another name\n");
                       goto q;
                }
        else
{
              fo=fopen(a[i].Ebook,"w");
              if(fo==NULL)
                  {
                      printf("\nSorry we could not create the required book.Please try once again\n");
                                goto q;
                           }
        else
        {
                if(fp=fopen(a[i].Tbook,"r"))
                    {
                       printf("name of Tbook already exists try another one\n");
goto q;
}
      else
{
                    fprintf(fo,"NAME                                                  %s\n",a[i].name);
                    fprintf(fo,"SUR_NAME                                              %s\n",a[i].sname);
                    fprintf(fo,"CITY                                                  %s\n",a[i].city);
                    fprintf(fo,"ACCOUNT_NUMBER                                        %ld\n",a[i].accno);
                    fprintf(fo,"ACCOUNT_BALANCE                                       %f\n",a[i].netamount);
                    fclose(fo);
                    printf("\nYour account is successfully created.\n");
                    i++;
                    j++;
                    g++;
                                                              }


                                                                                      }
                                                                                  }
                    }
}


void print( int p)
{
printf("\nYour account balance is %f\n",a[p].netamount);
}

void withdraw(int p)
{
float u;
printf("\nEnter the amount you want to withdraw\n");
scanf("%f",&u);
if(u<=(a[p].netamount))
                {
if(u<=(a[p].netamount-500))
printf("\nWarning your balance is getting lower than minimum\n");
                        printf("\nYour transaction is being processed.please wait\n");
a[p].netamount=a[p].netamount-u;
fo=fopen(a[p].Ebook,"w");
printf("\nPlease collect your cash\n");
fprintf(fo,"NAME                                                  %s\n",a[p].name);
fprintf(fo,"SUR_NAME                                              %s\n",a[p].sname);
fprintf(fo,"CITY                                                  %s\n",a[p].city);
fprintf(fo,"ACCOUNT_NUMBER                                        %ld\n",a[p].accno);
fprintf(fo,"ACCOUNT_BALANCE                                       %f\n",a[p].netamount);
fclose(fo);
printf("\nYour account is successfully updated.");
fo=fopen(a[p].Tbook,"a+");
fprintf(fo,"you have withdrawn %f Rs on %s \n",u,asctime(timeinfo));
fclose(fo);

                }
if(u>a[p].netamount)
    {
        printf("\nYou don't have sufficient balance\n");

            }


}

void chequep(int p)
{
char b[50];
float v;
FILE *z;
printf("\nYou opted for E-cheque book facility\n");
printf("\nEnter the name to whom cheque is to be given\n");
scanf("%s",b);
printf("\nEnter the amount you want to withdraw\n");
scanf("%f",&v);
printf("\n========================================================\n");
         printf("         E-CHEQUE              \n");
printf("\n========================================================\n");
printf("                                                          date %s\n",asctime(timeinfo));
printf("\n");
printf("\n");
printf("pay  %f                          rupees  \n",v);
printf("to MR/MRS     %s                              \n",b);
printf("                                acc number  1100%d\n",p);
printf("                                  cheque processing number 110000%d\n",k);

printf("\n");
printf("  STATE BANK OF INDIA                                      signature\n");
printf("payable at all national and international banks\n");
printf("\n");
printf("\n========================================================\n");
printf("\n========================================================\n");
fo=fopen(a[p].Tbook,"a+");
fprintf(fo,"\nYou have withdrawn Rs  %f on %s\n",v,asctime(timeinfo));
k++;
printf("\nTHANKYOU\n");

}

void deposit(int p)
{
printf("\nEnter the amount you will deposit\n");
float y;
scanf("%f",&y);
printf("your request is being processed\n");


                              a[p].netamount=(a[p].netamount+y);
                                                                                               fo=fopen(a[p].Ebook,"w");

                                              fprintf(fo,"NAME                                                  %s\n",a[p].name);
                              fprintf(fo,"SUR_NAME                                              %s\n",a[p].sname);
                              fprintf(fo,"CITY                                                  %s\n",a[p].city);
                              fprintf(fo,"ACCOUNT_NUMBER                                        %ld\n",a[p].accno);
                              fprintf(fo,"ACCOUNT_BALANCE                                       %f\n",a[p].netamount);
      fclose(fo);
      printf("your account has been successfully updated.");
fo=fopen(a[p].Tbook,"a");
fprintf(fo,"you have deposited %f Rs on %s\n",y,asctime(timeinfo));
fclose(fo);

}

void view(int p)
{
                                                                                               printf("NAME                                                  %s\n",a[p].name);
                              printf("SUR_NAME                                              %s\n",a[p].sname);
                              printf("CITY                                                  %s\n",a[p].city);
                              printf("ACCOUNT_NUMBER                                        %ld\n",a[p].accno);
                              printf("ACCOUNT_BALANCE                                       %f\n",a[p].netamount);


}

void trns(int p,int f)
{
float w;
lk:
printf("enter the amount you would like to transfer\n");
scanf("%f",&w);
if(a[p].netamount<(w-500))
{printf("you dont have enough money to transfer\n");
goto lk;
}
else
{a[f].netamount=(a[f].netamount+w);
a[p].netamount=(a[p].netamount-w);
                                                                                              fo=fopen(a[p].Ebook,"w");
                                              fprintf(fo,"NAME                                                  %s\n",a[p].name);
                              fprintf(fo,"SURNAME                                              %s\n",a[p].sname);
                              fprintf(fo,"CITY                                                  %s\n",a[p].city);
                              fprintf(fo,"ACCOUNT_NUMBER                                        %ld\n",a[p].accno);
                              fprintf(fo,"ACCOUNT_BALANCE                                       %f\n",a[p].netamount);
      fclose(fo);
fo=fopen(a[p].Tbook,"a+");
fprintf(fo,"you have transferred Rs %f on %s to %s accno %ld\n",w,asctime(timeinfo),a[f].name,a[f].accno);
fclose(fo);
                                                                                                
                                                                                                fo=fopen(a[f].Ebook,"w");
                                              fprintf(fo,"NAME                                                  %s\n",a[f].name);
                              fprintf(fo,"SURNAME                                              %s\n",a[f].sname);
                              fprintf(fo,"CITY                                                  %s\n",a[f].city);
                              fprintf(fo,"ACCOUNT_NUMBER                                        %ld\n",a[f].accno);
                              fprintf(fo,"ACCOUNT_BALANCE                                       %f\n",a[f].netamount);
      fclose(fo); 
fp=fopen(a[f].Tbook,"a+");
fprintf(fp,"you have recived Rs %f on %s from %s accno %ld\n",w,asctime(timeinfo),a[p].name,a[p].accno);
fclose(fp);




}
}
void eme(int p)
{
printf("your total money is being given to you\n");
fo=fopen(a[p].Tbook,"a+");
fprintf(fo,"you had an emergency withdrawl on %s of rs %f\n",asctime(timeinfo),a[p].netamount); 
fclose(fo);
a[p].netamount=0;
fo=fopen(a[p].Ebook,"w");
                                              fprintf(fo,"NAME                                                  %s\n",a[p].name);
                              fprintf(fo,"SUR_NAME                                              %s\n",a[p].sname);
                              fprintf(fo,"CITY                                                  %s\n",a[p].city);
                              fprintf(fo,"ACCOUNT_NUMBER                                        %ld\n",a[p].accno);
                              fprintf(fo,"ACCOUNT_BALANCE                                       %f\n",a[p].netamount);
      fclose(fo);


}



void Aview()
{
system("clear");
printf("\nEnter the account number:\n");
scanf("%ld",&r);
av=((r%1000)-1);
   printf("NAME                                                  %s\n",a[av].name);
   printf("SUR_NAME                                              %s\n",a[av].sname);
   printf("CITY                                                  %s\n",a[av].city);
   printf("ACCOUNT_NUMBER                                        %ld\n",a[av].accno);
   printf("ACCOUNT_BALANCE                                       %f\n",a[av].netamount);

}

void tbook(int p)
{
fo=fopen(a[p].Tbook,"r");
while(!feof(fo))
putchar(getc(fo));
}















