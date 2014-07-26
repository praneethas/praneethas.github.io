/*
 * File:   main.cpp
 * Author: Praneeth
 *
 * Created on 12 November, 2013, 6:38 PM
 */

/***

    Program calculating the Integer solutions to Linear Programming Problem  in C++
    Created by: Praneeth A S
    Date: 04.11.2013
    Subject: Mathematical Programming


***/

// Header Files
#include<cstdio>
#include<iostream>
#include<cmath>
#include<cstdlib>
#include<climits>

#define NEGMAX -9999   //Negative higher number constant
#define M 500
#define N 500
#define MIN 0.0000000000000000001
#define FLOATINFINITY 100000.00

using namespace std;

int countmaxzterms;  /* Number of terms in Objective Function */
int abruptBreak = 0; /* Breaking if outgoing not there if incoming there */
int abruptBreakDS = 0; /* Breaking if incoming not there if outgoing there */
int infease = 0;       /* to check infeasibility of solution*/
int greatVar[N];      /* to store values of greater condition in charne's m method*/
int constraint;      /* Number of constraints for the problem */
int response;        /* For Max or Min*/
int basic[N];        /* Stores the basic variable */
double c[M];          /* Stores co-efficient of the objective function Max(z) */
double solutionSimplex[M];  /* Contains solution of simplex table */
double solutionDSimplex[M]; /* Contains solution of dual simplex table */
double solutionGomoryCut[M]; /* Final Solution of gomory cut problem */
int checkSolution(int [], double [], int); /* Checks if Solutions for required basic variables are integers */
double fracX(double);                       /* Calculates Fractional part of number */
double fracX1(double);
double fracPart[M];                        /* Stores fractional part of basic variables in array */
double constNum;
int condition[N];                         /* Condition of equations or constraints */
int status = 1;                           /* If simplex gives integer solutions*/
int greaterCond = 0;                      /* If objective function has all -ve co-efficients*/
double temp[M];                            /* Stores the values of Zj-Cj*/
int conditionVar[N];                      /* Conditions of variables */

void dualTableForm(double a[N][M],double [],double [],int,int,int []);  /* Framing the Dual table */

/*** Simplex Class ***/

class simplexProblem{

public:
  void solve(double [][M],double [],double []);                 /* Solves the simplex problem */
  void solveForDS(double a[N][M], double b[N], double c[M]);
  void printFinalSolution(double [][M],double [],double []);    /* Prints Final Solution of Simplex Table */
private:
  double x[M];                                                /* Stores the value of the variables */
  int leavingcol;
  //double temp[M];                                             /* Stores the values of Zj-Cj*/
  int flag = 0;                                              /* Terminating variable */
  int bminpos;                                               /* Stores the minimum valued position of {Zj-Cj} i.e. coming in variable */
  int gooutcol;                                              /* Stores the column number which goes out */
  double minratio[M];                                         /* Stores the value of the ratio Zj-Cj/a[i][j] */
  int minratiominpos;                                        /* Stores the minimum valued position of b[i]/a[i][j] i.e. going out variable */
  int incomingcol;                                           /* Stores the incoming column number */
  double z, key;
  void calctemp(double *,double [][M],double [],int []);        /* Calculates Zj-Cj */
  void modify(double a[N][M], double b[N]);
  int checkgreatVar(int greaterVar[], int);
  void minimum(double *arr,int *arrminpos,int n, double a[N][M]);             /* Calculates the minimum valued position among the array arr having n elements. */
  void minratioval(double [],int *arrminpos,int n);         /* Calculates the minimum valued position among the array arr having n elements. */
  void printSolution(double [][M],double [],double []);         /* Calculates the solution Matrix */
  void display (double c[],double b[],double a[][M],int basic[], double temp[N]);  /* Displays solution in table form*/
  void displayframe(double c[M], double temp[N]);                                /* Displays the frame of the table */
};


void simplexProblem::modify(double a[N][M], double b[N]){
int r,s;
    for(r = 0; r < constraint; r++){
        if(b[r] <= MIN){
            b[r] = 0.0;
        }
    }
    for(r = 0; r < constraint; r++){
        for(s = 0; s < constraint+countmaxzterms; s++){
            if(a[r][s] <= MIN){
            a[r][s] = 0.0;
        }
        }
    }
}

void simplexProblem::display (double c[N],double b[N],double a[N][M],int basic[N], double temp[N]){
    int i,j;
    displayframe(c,temp);
    for(i=0;i<constraint;i++){
        printf("\n%0.3g\tX%d\t%0.3g\t",c[basic[i]],basic[i]+1,b[i]);
        for(j=0;j<constraint+countmaxzterms;j++)
            printf("%0.3g\t",a[i][j]);
        printf("\n");
    }
}

void simplexProblem::displayframe(double c[M], double temp[N]){
    int i;
    printf("\t\tZj-Cj\t");
    for(i=0;i<constraint+countmaxzterms;i++)
        printf("%0.2g\t",temp[i]);
    printf("\n\n");
    printf("\t\tc[j]\t");
    for(i=0;i<constraint+countmaxzterms;i++)
        printf("%0.2g\t",c[i]);
    printf("\n");
    printf("\nc[B]\tB\tb\t");
    for(i=0;i<constraint+countmaxzterms;i++)
        printf("a[%d]\t",i+1);
    printf("\n");
}

void simplexProblem::printFinalSolution(double [][M],double [],double []){
    int i;
    printf("\n----------------------FINAL SOLUTION OF SIMPLEX PROBLEM-------------------------\n\n");
    for(i = 0; i < countmaxzterms; i++){
        printf("x[%d] = %2.2lf\n",i+1,solutionSimplex[i]);
    }
    double f = 0.0;

    if(response == 1){
        for(i = 0; i < countmaxzterms; i++){
            f = f + c[i]*solutionSimplex[i];
        }
        f += constNum;
        printf("Maximum Value = %2.2lf\n",f);
    }
    if(response == 2){
        for(i = 0; i < countmaxzterms; i++){
            f = f + (-1)*c[i]*solutionSimplex[i];
        }
        f += constNum;
        printf("Minimum Value = %2.2lf\n",f);
    }
    printf("\n--------------------------------------------------------------------------------\n");
}

void simplexProblem::printSolution(double a[N][M],double b[N],double c[M]){
    int i;
    for(i = 0; i < constraint + countmaxzterms; i++){
        solutionSimplex[i] = 0.0;
    }
    for(i = 0; i < constraint; i++){
            solutionSimplex[basic[i]] = b[i];
    }
}

void simplexProblem::calctemp(double *temp,double a[N][M],double c[M],int basic[N]){
    int i,j;
    for(i = 0 ; i < constraint + countmaxzterms ; i++){
        temp[i]=0;
        for(j = 0 ; j < constraint; j++){
            temp[i] = temp[i] + c[basic[j]]*a[j][i];
        }
    temp[i] = temp[i] - c[i];
    }
}

void simplexProblem::minimum(double *arr,int *arrminpos, int n, double a[N][M]){
    *arrminpos = -1;
    double arrmin = FLOATINFINITY;
    int i,val = 0,j;
    for(i = 0; i < n; i++){
        if(arr[i] < arrmin){
            arrmin = arr[i];
            *arrminpos = j = i;
        }
    }
    for(i = 0;i < constraint; i++){
        if(a[i][j] < 0.0){
            val++;
        }
    }
    if(val == constraint){
        *arrminpos = -1;
    }
}

void simplexProblem::minratioval(double arr[],int *arrminpos, int n){
    int i;
    double arrmin;
    arrmin = arr[0];
    *arrminpos = 0;
    for(i = 0; i < n; i++){
        if(arr[i] <= arrmin){
            arrmin = arr[i];
            printf("i = %d, arrmin = %2.2lf ",i,arrmin);
            *arrminpos = i;
        }
    }
    printf("\nminValue = %2.2lf, arrMinPos = %d\n",arrmin,*arrminpos);
}

void simplexProblem::solve(double a[N][M], double b[N], double c[M]){
    int k = 0,i,j;
    calctemp(temp, a, c, basic);

    /*** Calculation for actual table ***/
    do{
        k++;
        z = 0;
        display (c,b,a,basic,temp);
        printf("\n\n");
        getchar();
        printf("-------------------------------------STAGE %d------------------------------------\n",k);

        /*** Printing Zj - Cj Matrix ***/

        printf("\nZj - cj Matrix\n");
        for(i = 0; i < constraint + countmaxzterms ; i++){
            printf("%2.2lf ",temp[i]);
        }

        /*** End of Printing Zj - Cj Matrix ***/

        /*** Some Printing ***/
        printf("\n");
        for(i = 0; i < countmaxzterms; i++){
            if(i == 0)
                printf("x%d >= 0 ",i+1);
            else
                printf(", x%d >= 0 ",i+1);
        }

        /*** End of Some Printing ***/

        printf("\nBasic Matrix\n");
        for(i = 0; i < constraint + countmaxzterms ; i++){
            printf("%d ",basic[i]+1);
        }

        /*** Terminating condition ***/
        for(i = 0; i < constraint + countmaxzterms; i++){
            flag = 1;
            if(temp[i] < 0){
                for(j = 0 ; j < constraint; j++){
                    if(a[j][i] > 0.0){
                        flag = 0;
                        break;
                    }
                }
            }
            if(flag == 0){
                break;
            }
        }

        /*** End of terminating Condition ***/

        /*** Determining the incoming variable ***/
        //int abruptBreak = 0;
        minimum(temp, &bminpos, (constraint + countmaxzterms),a);
        if(bminpos == -1){
            abruptBreak = 1;
        }
        incomingcol = bminpos;
        /*** End of determining the incoming variable ***/

        /*** Determining the outgoing variable ***/

        for(i = 0; i < M; i++){
            minratio[i] = 0.0;
        }

        for(i = 0; i < constraint; i++){
            if(a[i][incomingcol] <= 0.0){
                minratio[i] = FLOATINFINITY;
            }
            else{
                minratio[i] = b[i]/a[i][incomingcol];
            }
        }

        printf("\nMin Ratio Matrix\n");
        for(i = 0; i < constraint; i++){
            printf("%2.2lf ",minratio[i]);
        }
        minratioval(minratio, &minratiominpos, constraint);
        gooutcol = minratiominpos;

        /*** End of determining the outgoing variable ***/

        for(i = 0; i < constraint + countmaxzterms; i++){
            x[i] = 0;
        }
        printf("\n");
        for(i = 0; i < constraint; i++){
            x[basic[i]] = b[i];
            printf("x[%d] = %0.3g\n",basic[i]+1,b[i]);
        }

        for(i = 0; i < constraint; i++){
            z = z + c[i]*x[i];
        }
        if(response == 1){
            printf("Max(z) = %2.2lf",z);
        }
        if(response == 2){
            printf("Min(z) = %2.2lf",z);
        }
        printf("\nBasic Matrix\n");
        for(i = 0; i < constraint + countmaxzterms ; i++){
            printf("%d ",basic[i]+1);
        }
        /*** Changing the basic and non-basic variable ***/
       /* printf("BASIC MATRIX\n");
        for(i = 0; i< constraint; i++){
            printf("%d ",basic[i]);
        }*/
        leavingcol = basic[gooutcol]+1;
        basic[gooutcol]=incomingcol;
        
        printf("\nComing in variable = X%d\t",incomingcol+1);
        printf("Going out variable = X%d, gooutcol = %d\n",leavingcol, gooutcol);

        /*** Performing the operations to bring similar expressions in
        in-coming variable as out-going variable by row operations ***/

        key = a[gooutcol][incomingcol];
        b[gooutcol] = b[gooutcol]/key;
        for(i = 0; i < constraint + countmaxzterms; i++)
            a[gooutcol][i] = a[gooutcol][i]/key;
        for(i = 0; i < constraint; i++){
            if(gooutcol == i){
            continue;
            }
            key = a[i][incomingcol];
            for(j = 0 ; j < (constraint + countmaxzterms) ; j++){
                a[i][j]=a[i][j]-a[gooutcol][j]*key;
            }
            b[i] = b[i] - b[gooutcol]*key;
        }
        
        calctemp(temp, a, c, basic);
        
        printf("\n");
        printf("Temp Matrix(Zj - Cj for next iteration):\n");
        for(i = 0; i < constraint + countmaxzterms; i++){
            printf("%2.2lf ",temp[i]);
        }
        printf("\n");
        flag = 1;
        
        for(i = 0; i < constraint + countmaxzterms; i++){
           if(temp[i] < 0){
                for(j = 0 ; j < constraint; j++){
                    if(a[j][i] > 0.0){
                        flag = 0;
                        break;
                    }
                }
            }
            /*if(flag == 0){
                break;
            }*/
        }
        if(abruptBreak == 1){
            printf("\nUNBOUNDED SOLUTION\n");
            flag = 1;
        }
        
        printf("\n--------------------------------------------------------------------------------\n");
        getchar();
    }while(flag == 0);
    getchar();
    /*printf("BASIC MATRIX\n");
        for(i = 0; i< constraint; i++){
            printf("%d ",basic[i]);
        }
        printf("\n");
    //printf("greaterVar = %d\n",greatVar);
    printf("\nGreater var Array:\n");
    for(i = 0; i < constraint; i++){
        printf("%d ",greatVar[i]);
    }
    printf("\n");*/
    if(abruptBreak != 1){
        for( i = 0; i < constraint; i++){
        if(checkgreatVar(greatVar, basic[i])){
            infease = 1;
            printf("\nINFEASIBLE SOLUTION\n");
            break;
        }
    }
    
    }
    //printf("infease = %d\n",infease);
    if(abruptBreak != 1 && infease != 1){
            display (c,b,a,basic,temp);
            printSolution(a,b,c);
            printFinalSolution(a,b,c);
    }
}

int simplexProblem::checkgreatVar(int greaterVar[], int k){
    int l, result = 0;
    for(l = 0; l < constraint; l++){
        if(greaterVar[l] == k){
            result = 1;
        }
    }
    return result;
}

void simplexProblem::solveForDS(double a[N][M], double b[N], double c[M]){
    int k = 0,i,j;
    calctemp(temp, a, c, basic);

    /*** Calculation for actual table ***/
    do{
        k++;
        z = 0;
        display (c,b,a,basic,temp);
        printf("\n\n");
        getchar();
        printf("-------------------------------------STAGE %d------------------------------------\n",k);

        /*** Printing Zj - Cj Matrix ***/

        printf("\nZj - cj Matrix\n");
        for(i = 0; i < constraint + countmaxzterms ; i++){
            printf("%2.2lf ",temp[i]);
        }

        /*** End of Printing Zj - Cj Matrix ***/

        /*** Some Printing ***/
        printf("\n");
        for(i = 0; i < countmaxzterms; i++){
            if(i == 0)
                printf("x%d >= 0 ",i+1);
            else
                printf(", x%d >= 0 ",i+1);
        }

        /*** End of Some Printing ***/

        printf("\nBasic Matrix\n");
        for(i = 0; i < constraint + countmaxzterms ; i++){
            printf("%d ",basic[i]+1);
        }

        /*** Terminating condition ***/
        for(i = 0; i < constraint + countmaxzterms; i++){
            flag = 1;
            if(temp[i] < 0){
                flag = 0;
                break;
            }
        }

        /*** End of terminating Condition ***/

        /*** Determining the incoming variable ***/

        minimum(temp, &bminpos, (constraint + countmaxzterms),a);
        incomingcol = bminpos;
        /*** End of determining the incoming variable ***/

        /*** Determining the outgoing variable ***/

        for(i = 0; i < M; i++){
            minratio[i] = 0.0;
        }

        for(i = 0; i < constraint; i++){
            if(a[i][incomingcol] <= 0.0){
                minratio[i] = FLOATINFINITY;
            }
            else{
                minratio[i] = b[i]/a[i][incomingcol];
            }
        }

        printf("\nMin Ratio Matrix\n");
        for(i = 0; i < constraint; i++){
            printf("%2.2lf ",minratio[i]);
        }
        minratioval(minratio, &minratiominpos, constraint);
        gooutcol = minratiominpos;

        /*** End of determining the outgoing variable ***/

        for(i = 0; i < constraint + countmaxzterms; i++){
            x[i] = 0;
        }
        printf("\n");
        for(i = 0; i < constraint; i++){
            x[basic[i]] = b[i];
            printf("x[%d] = %0.3g\n",basic[i]+1,b[i]);
        }

        for(i = 0; i < constraint; i++){
            z = z + c[i]*x[i];
        }
        if(response == 1){
            printf("Max(z) = %2.2lf",z);
        }
        if(response == 2){
            printf("Min(z) = %2.2lf",z);
        }
        printf("\nBasic Matrix\n");
        for(i = 0; i < constraint + countmaxzterms ; i++){
            printf("%d ",basic[i]+1);
        }
        /*** Changing the basic and non-basic variable ***/
        leavingcol = basic[gooutcol]+1;
        basic[gooutcol]=incomingcol;
        printf("\nComing in variable = X%d\t",incomingcol+1);
        printf("Going out variable = X%d, gooutcol = %d\n",leavingcol, gooutcol);

        /*** Performing the operations to bring similar expressions in
        in-coming variable as out-going variable by row operations ***/

        key = a[gooutcol][incomingcol];
        b[gooutcol] = b[gooutcol]/key;
        for(i = 0; i < constraint + countmaxzterms; i++)
            a[gooutcol][i] = a[gooutcol][i]/key;
        for(i = 0; i < constraint; i++){
            if(gooutcol == i){
            continue;
            }
            key = a[i][incomingcol];
            for(j = 0 ; j < (constraint + countmaxzterms) ; j++){
                a[i][j]=a[i][j]-a[gooutcol][j]*key;
            }
            b[i] = b[i] - b[gooutcol]*key;
        }
        calctemp(temp, a, c, basic);
        printf("Temp Matrix(Zj - Cj for next iteration):\n");
        for(i = 0; i < constraint + countmaxzterms; i++){
            printf("%2.2lf ",temp[i]);
        }
        printf("\n");
        /*for(i = 0; i < constraint + countmaxzterms; i++){
            flag = 1;
            if(temp[i] < 0){
                flag = 0;
                break;
            }
        }*/
        int checkCond = 0;
        for(i = 0; i < constraint + countmaxzterms; i++){
            flag = 0;
            if(temp[i] >= 0){
                checkCond++;
            }
            if(checkCond == constraint + countmaxzterms){
                printf("\nBreaking out\n");
                flag = 1;
                break;
            }
        }
        printf("\n--------------------------------------------------------------------------------\n");
        getchar();
    }while(flag == 0);
    getchar();
    display (c,b,a,basic,temp);
    printf("\n--------------------------------------------------------------------------------\n");

}

/*** Dual Simples Class ***/


class dualSimplexProblem{

public:
    void solve(double [][M],double [],double [],int,int);              /* Solves Dual Simplex Problem */
    void printFinalSolution(double [][M],double [],double [],int,int); /* Prints Final Solution of dual Simplex Table */
private:
    double x[M];                                                     /* Stores the value of the variables */
    //double temp[M];                                                  /* Stores the values of Zj-Cj*/
    int flag = 0;                                                   /* Terminating variable */
    int bminpos;                                                    /* Stores the minimum valued position of {Zj-Cj} i.e. coming in variable */
    int gooutcol;                                                   /* Stores the column number which goes out */
    double maxratio[M];                                              /* Stores the value of the ratio Zj-Cj/a[i][j] */
    int maxratiomaxpos;                                             /* Stores the minimum valued position of b[i]/a[i][j] i.e. going out variable */
    int incomingcol;
    double z, key;
    void calctemp(double *,double [][M],double [],int [],int,int);     /* Calculates Zj-Cj */
    void minimum(double *arr,int *arrminpos,int n,double a[N][M],int);                  /* Calculates the minimum valued position among the array arr having n elements. */
    void maximum(double *arr,int *arrminpos,int n);                  /* Calculates the minimum valued position among the array arr having n elements. */
    void printSolution(double [][M],double [],double [],int,int);
    void display (double c[],double b[],double a[][M],int basic[], double temp[N]);      /* Display the table */
    void displayframe(double c[M], double temp[N]);                                    /* Displays the frame of the table */
    void modify(double a[N][M], double b[N],int cons, int countz);
};

void dualSimplexProblem::modify(double a[N][M], double b[N], int cons, int countz){
    int r,s;
    for(r = 0; r < cons; r++){
        if(b[r] <= MIN){
            b[r] = 0.0;
        }
    }
    for(r = 0; r < cons; r++){
        for(s = 0; s < cons+countz; s++){
            if(a[r][s] <= MIN){
            a[r][s] = 0.0;
            }
        }
    }
}

void dualSimplexProblem::display(double c[N],double b[N],double a[N][M],int basic[N], double temp[N]){
    int i,j;
    displayframe(c,temp);
    for(i=0;i<constraint;i++){
        printf("\n%0.3g\tX%d\t%0.3g\t",c[basic[i]],basic[i]+1,b[i]);
        for(j=0;j<constraint+countmaxzterms;j++)
            printf("%0.3g\t",a[i][j]);
        printf("\n");
    }
}

void dualSimplexProblem::displayframe(double c[M], double temp[N]){
    int i;
    printf("\t\tZj-Cj\t");
    for(i=0;i<constraint+countmaxzterms;i++)
        printf("%0.2g\t",temp[i]);
    printf("\n\n");
    printf("\t\tc[j]\t");
    for(i=0;i<constraint+countmaxzterms;i++)
        printf("%0.2g\t",c[i]);
    printf("\n");
    printf("\nc[B]\tB\tb\t");
    for(i=0;i<constraint+countmaxzterms;i++)
        printf("a[%d]\t",i+1);
    printf("\n");
}

void dualSimplexProblem::calctemp(double *temp,double a[N][M],double c[M],int basic[N], int cons, int countz){

    int i,j;
    for(i = 0 ; i < cons + countz ; i++){
        temp[i]=0;
        for(j = 0 ; j < cons; j++){
            temp[i] = temp[i] + c[basic[j]]*a[j][i];
        }
    temp[i] = temp[i] - c[i];
    }
}

void dualSimplexProblem::minimum(double *arr,int *arrminpos, int n, double a[N][M], int countz){

    int i, arrmin,j,val = 0;
    arrmin = arr[0];
    *arrminpos = 0;
    for(i = 0; i < n; i++){
        if(arr[i] < arrmin){
            arrmin = arr[i];
            *arrminpos = j = i;
        }
    }
    /*printf("\nminpos = %d\n",j);
    for(i = 0; i < n+countz; i++){
        printf("%2.2lf ",a[j][i]);
    }
    printf("\nMinimum Fn Array row\n");*/
    for(i = 0;i < n+countz; i++){
        if(a[j][i] >= 0.0){
            //printf("%2.2lf ",a[j][i]);
            val++;
        }
    }
    if(val == n+countz){
        *arrminpos = -1;
    }
}

void dualSimplexProblem::maximum(double *arr,int *arrmaxpos, int n){
    int i, arrmax;
    arrmax = NEGMAX;
    *arrmaxpos = 0;
    for(i = 0; i < n; i++){
        if((arr[i] > arrmax) && (arr[i] < 0.0)){
            arrmax = arr[i];
            *arrmaxpos = i;
        }
    }
}

void dualSimplexProblem::solve(double a[N][M], double b[N], double c[M], int cons, int countz){

    /*** Calculation for actual table ***/
    int k = 0,i,j;
    double solution[M];
    do{
        k++;
        /*** Terminating condition ***/
        printf("\n");
        for(i = 0; i < cons; i++){
            flag = 1;
            if(b[i] < 0){
                for(j = 0 ; j < cons+countz; j++){
                    if(a[i][j] < 0.0){
                        flag = 0;
                        break;
                    }
                }
            }
            if(flag == 0){
                break;
            }
        }
    z = 0;
    display(c,b,a,basic,temp);
    getchar();
    printf("------------------------------------STAGE %d-------------------------------------\n",k);
    printf("\nBasic Matrix\n");
    for(i = 0; i < cons + countz ; i++){
        printf("%d ",basic[i]+1);
    }
    printf("\nTemp Matrix\n");
    for(i = 0; i < cons + countz ; i++){
        printf("%.2f ",temp[i]);
    }

    /*** Printing the zj - cj Matrix ***/
    calctemp(temp, a, c, basic, constraint, countmaxzterms);
    printf("\nZj - cj Matrix\n");
    for(i = 0; i < cons + countz ; i++){
        printf("%2.2lf ",temp[i]);
    }

    /*** Determining the outgoing variable ***/
    //int abruptBreak = 0;
    minimum(b,&bminpos,cons,a,countz);
    if(bminpos == -1){
            abruptBreakDS = 1;
    }
    gooutcol = basic[bminpos];

    /*** Determining the incoming column ***/
    for(i = 0; i < M; i++){
      maxratio[i] = NEGMAX;
    }

    for(i = 0; i < cons + countz; i++){
      if(a[bminpos][i] == 0){
        maxratio[i] = FLOATINFINITY;
        continue;
      }
      if(a[bminpos][i] > 0){
        maxratio[i] = FLOATINFINITY;
        continue;
      }
      maxratio[i] = temp[i]/a[bminpos][i];
    }

    maximum(maxratio,&maxratiomaxpos,cons + countz);
    incomingcol = maxratiomaxpos;
    printf("\nMax Ratio Matrix\n");
        for(i = 0; i < cons + countz; i++){
            printf("%2.2lf ",maxratio[i]);
        }
    for(i = 0; i < cons + countz; i++){
      x[i] = 0;
    }
    printf("\n");
    for(i = 0; i < cons; i++){
      x[basic[i]] = b[i];
      printf("x[%d] = %0.3g\n",basic[i]+1,b[i]);
    }

    for(i = 0; i < cons; i++){
      z = z + c[i]*x[i];
    }

    if(response == 1){
        printf("Max(z) = %2.2lf",z);
    }
    if(response == 2){
        printf("Min(z) = %2.2lf",z);
    }
    printf("\nComing in variable = X%d\t",incomingcol+1);
    printf("Going out variable = X%d\n",gooutcol+1);

   /*** Changing the basic and non-basic variable ***/

    basic[bminpos]=incomingcol;

    /*** Performing the operations to bring similar expressions in
    in-coming variable as out-going variable by row operations ***/

    key = a[bminpos][incomingcol];
    b[bminpos] = b[bminpos]/key;
    for(i = 0; i < cons + countz; i++)
      a[bminpos][i] = a[bminpos][i]/key;
    for(i = 0; i < constraint; i++){
      if(bminpos == i){
        continue;
      }
      key = a[i][incomingcol];
      for(j = 0 ; j < (cons + countz) ; j++){
        a[i][j]=a[i][j]-a[bminpos][j]*key;
      }
      b[i] = b[i] - b[bminpos]*key;
    }

    getchar();

    for(i = 0; i < cons; i++){
        flag = 1;
        if(b[i] < 0){
            /*printf("\nRow corresponding to %2.2lf\n",b[i]);
            for(j = 0 ; j < cons+countz; j++){
                printf("%2.2lf ",a[i][j]);
            }
            printf("\n");*/
            for(j = 0 ; j < cons+countz; j++){
                //printf("%2.2lf < 0.0:  ",a[i][j]);
                if(a[i][j] < 0.0){
                    //printf("%d\n",a[i][j] < 0.0);
                    flag = 0;
                    break;
                }
            }
        }
        if(flag == 0){
            break;
        }
    }
    if(abruptBreakDS == 1){
            printf("\nUNBOUNDED SOLUTION\n");
            flag = 1;
    }
    printf("\n------------------------------------------------------------------------------\n");
    printSolution(a,b,c,constraint,countmaxzterms);
    //printf("flag = %d\n",flag);
    }while(flag==0);
    printf("\n");
    if(abruptBreakDS != 1){
            display(c,b,a,basic,temp);
            printFinalSolution(a,b,c,constraint,countmaxzterms);
    }
}

void dualSimplexProblem::printFinalSolution(double [][M],double [],double [], int cons, int countz){
    int i;
    printf("\n-----------------FINAL SOLUTION OF DUAL SIMPLEX PROBLEM-------------------------\n\n");
    for(i = 0; i < countz; i++){
        printf("x[%d] = %2.2lf\n",i+1,solutionDSimplex[i]);
    }
    double f = 0.0;
    if(response == 1){
        for(i = 0; i < countmaxzterms; i++){
        f = f + c[i]*solutionDSimplex[i];
        }
        f += constNum;
        printf("Maximum Value = %2.2lf\n",f);
    }
    if(response == 2){
        for(i = 0; i < countmaxzterms; i++){
        f = f + (-1)*c[i]*solutionDSimplex[i];
        }
        f += constNum;
        printf("Minimum Value = %2.2lf\n",f);
    }
    printf("\n--------------------------------------------------------------------------------\n");
}

void dualSimplexProblem::printSolution(double a[N][M],double b[N],double c[M], int cons, int countz){
    int i;
    for(i = 0; i < cons + countz; i++){
        solutionDSimplex[i] = 0.0;
    }
    for(i = 0; i < cons; i++){
            solutionDSimplex[basic[i]] = b[i];
    }
}


int main(){

    double a[N][M];    /* Stores the co-efficent of the constraints */
    double b[N];      /* Stores the values on RHS of constraints */
    int i,j;
    fflush(stdout);
    cout << "SOLVING OPTIMIZATION PROBLEM USING GOMORY CUT METHOD TO OBTAIN INTEGER SOLUTIONS\n\n";
    for(i = 0; i < M ; i++){
        c[i] = fracPart[i] = temp[i] = 0.0;
    }
    for(i = 0; i < M ; i++){
        greatVar[i] = -5;
    }
    for(i = 0; i < N ; i++){
        for(j = 0 ; j < M ; j++){
            a[i][j] = 0.0;
        }
    }
    printf("\nEnter number of terms in objective function\n"); //
    fflush(stdout);
    scanf("%d",&countmaxzterms);

    /*** End of taking number of terms of objective function ***/

    /*** Example: z = 3*x1 + 2*x2 - 5*x3 Enter the values : c[0]=3 , c[1]=2 , c[2] = -5 ***/
    printf("\nEnter the co-efficients of the objective function\n"); //
    for(i = 0; i < countmaxzterms ; i++){
        fflush(stdout);
        scanf("%lf",&c[i]) ;
    }
    printf("\nEnter the constant value in the function:\n");
    fflush(stdout);
    scanf("%lf",&constNum);

    /*** End of entering co-efficients of Objective functions ***/

    /*** Max or Min ***/

    printf("\nDo you want to Maximize or Minimize?(1. Maximize 2. Minimize):\n");
    fflush(stdout);
    scanf("%d",&response);

    /*** End of Max or Min ***/

    /*** Printing the Objective Function ***/

    printf("\nYou have entered the function as follows:-\n");
    if(response == 1){
        printf("\nMax z = ");
    }
    if(response == 2){
        printf("\nMin z = ");
    }
    for(i = 0; i < countmaxzterms ; i++){
        if(i == 0)
            printf("%g*x%d",c[i],i+1);
        else
            printf(" + %g*x%d",c[i],i+1);
    }
    printf(" + %g",constNum);

    /*** End of Printing Objective Function ***/

    if(response == 1){
        for(i = 0; i < countmaxzterms;i++){
            c[i] = c[i];
        }
    }
    if(response == 2){
        for(i = 0; i < countmaxzterms;i++){
            c[i] = c[i]*(-1);
        }
    }

    for(i = 0; i < countmaxzterms;i++){
        if(c[i] < 0){
            greaterCond++;
        }
    }

    /*** Entering the constraints assuming all xi's are greater than or equal to 0 ***/

    printf("\n\nEnter number of constraints\n");
    fflush(stdout);
    scanf("%d",&constraint);

    printf("\nEnter the co-efficient of constraints\n");
    for(i = 0 ; i < constraint ; i++){
        for(j = 0 ; j < countmaxzterms ; j++){
            fflush(stdout);
            scanf("%lf",&a[i][j]);
        }
    }


    printf("\nEnter the Condition (1. Less than  2. More than 3. Equal to):\n");
    for(i = 0; i < constraint; i++){
        fflush(stdout);
        scanf("%d",&condition[i]);
        if(condition[i] == 2){
            status = 0;
            //greaterCond++;
        }
        if(condition[i] == 3){
            status = 2;
        }
    }

    for(i = 0; i < constraint; i++){
        if(condition[i] == 3 || condition[i] == 2){
            status = 2;
        }
    }
    printf("\nEnter values of bi's\n");
    for(i = 0 ; i < constraint ; i++){
        fflush(stdout);
        scanf("%lf",&b[i]);
    }
    for(i = 0 ; i < countmaxzterms + constraint ; i++){
        basic[i] = i + countmaxzterms;
    }

    printf("\nEnter condition for variables   (1. Greater than or equal to \n\t\t\t\t 2. Lesser than or equal to)\n");
    for(i =0 ; i < countmaxzterms;i++){
        fflush(stdout);
        scanf("%d",&conditionVar[i]);
        if(conditionVar[i] == 2){
            c[i] = (-1)*c[i];
            for(j = 0; j < constraint; j++){
                a[j][i] = (-1)*a[j][i];
            }
        }
    }
    /*** End of entering the constraints assuming all xi's are greater than or equal to 0 ***/

    /*** Printing the Problem ***/
    fflush(stdout);
    printf("\n\n--------------------------------------------------------------------------------\n\n");
    if(response == 1){
        printf("\nThe Problem is :\nMax z = ");
        for(i = 0; i < countmaxzterms ; i++){
            if(i == 0)
                printf("%g*x%d",c[i],i+1);
            else
                printf(" + %g*x%d",c[i],i+1);
        }
        printf(" + %g",constNum);
    }
    if(response == 2){
        printf("\nThe Problem is :\nMin z = ");
        for(i = 0; i < countmaxzterms ; i++){
            if(i == 0)
                printf("%g*x%d",(-1)*c[i],i+1);
            else
                printf(" + %g*x%d",(-1)*c[i],i+1);
        }
        printf(" + %g",constNum);
    }

    printf("\n");
    for(i = 0 ; i < constraint ; i++){
        for(j = 0 ; j < countmaxzterms ; j++){
            if(j == 0)
                printf(" %g*x%d ",a[i][j],j+1);
            else
                printf(" + %g*x%d ",a[i][j],j+1);
        }
        if(condition[i] == 2){
            printf(" >= %g\n",b[i]);
        }
        else if(condition[i] == 3){
            printf(" = %g\n",b[i]);
        }
        else{
            printf(" <= %g\n",b[i]);
        }
    }

    for(i = 0; i < countmaxzterms; i++){
        if(i == countmaxzterms-1){
            if(conditionVar[i] == 1){
                printf("x%d >= 0\n",i+1);
            }
            else{
                printf("x%d <= 0\n",i+1);
            }
        }
        else{
            if(conditionVar[i] == 1){
                printf("x%d >= 0, ",i+1);
            }
            else{
                printf("x%d <= 0, ",i+1);
            }
        }
    }
    printf("\n\n--------------------------------------------------------------------------------\n\n");

    simplexProblem simple;
    dualSimplexProblem dsp1;
    if(status == 0 && greaterCond == countmaxzterms){
        printf("\nSolving by Dual Simplex\n\n");
        for(i = 0 ; i < constraint ; i++){
            a[i][j++] = 1;
        }
        for(i = 0; i < constraint; i++){
            if(condition[i] == 2){
                b[i] = (-1)*b[i];
                for(j = 0; j < countmaxzterms; j++){
                    a[i][j] = (-1)*a[i][j];
                }
            }
        }
        dsp1.solve(a,b,c,constraint,countmaxzterms);
        for(i = 0; i < countmaxzterms; i++){
            solutionGomoryCut[i] = solutionDSimplex[i];
        }
    }
    if(status == 0 && greaterCond < countmaxzterms){
        printf("\nSolving by Dual Simplex using Simplex Method\n\n");
        for(i = 0 ; i < constraint ; i++){
            a[i][j++] = 1;
        }
        for(i = 0; i < constraint; i++){
            if(condition[i] == 2){
                b[i] = (-1)*b[i];
                for(j = 0; j < countmaxzterms; j++){
                    a[i][j] = (-1)*a[i][j];
                }
            }
        }
        simple.solveForDS(a,b,c);
        dsp1.solve(a,b,c,constraint,countmaxzterms);
        for(i = 0; i < countmaxzterms; i++){
            solutionGomoryCut[i] = solutionDSimplex[i];
        }
    }
    if(status == 1){
        printf("\nSolving by Simplex\n\n");
        for(i = 0 ; i < constraint ; i++){
            a[i][j++] = 1;
        }
        simple.solve(a,b,c);
        //printf("abruptBreak = %d",abruptBreak);
        for(i = 0; i < countmaxzterms; i++){
            solutionGomoryCut[i] = solutionSimplex[i];
        }
    }
    if(status == 2){
        printf("\nSolving by Charne's M Method\n\n");
        int r = countmaxzterms,p = 0;
        for(i = 0 ; i < constraint ; i++){
            if(condition[i] == 2){
                a[i][j++] = -1;
                greatVar[p++] = j;
                //printf("greaterVar = %d\n",j);
                c[j] = -100000.00;
                a[i][j++] = 1;
                basic[i] = r+1;
                countmaxzterms++;
                r++;
            }
            else if(condition[i] == 3){
                c[j] = -100000.00;
                a[i][j++] = 1;
                basic[i] = r;
            }
            else{
                a[i][j++] = 1;
                basic[i] = r;
            }
            r++;
        }
        for(i = constraint; i < N; i++){
            basic[i] = r;
            r++;
        }
        simple.solve(a,b,c);
        if(infease != 1){
            for(i = 0; i < countmaxzterms; i++){
            solutionGomoryCut[i] = solutionSimplex[i];
                }
        }
        
    }
    dualSimplexProblem dsp;
    getchar();
    //printf("abruptBreak = %d, abruptBreakDS = %d, infease = %d\n",abruptBreak,abruptBreakDS,infease);
    //printf("Condition = %d",(abruptBreak != 1) && (infease != 1));
    if((abruptBreak != 1) && (infease != 1)){
        dualTableForm(a,b,fracPart,constraint,countmaxzterms,basic);
        constraint++;

        int status1 = 0;
        while(checkSolution(basic,fracPart,constraint) == 0){
            status1 = 1;
            dsp.solve(a,b,c,constraint,countmaxzterms);
            getchar();
            dualTableForm(a,b,fracPart,constraint,countmaxzterms,basic);
            constraint++;
        }
        if(status1 == 1){
            for(i = 0; i < countmaxzterms; i++){
                solutionGomoryCut[i] = solutionDSimplex[i];
            }
        }
    }

    getchar();
    printf("\n--------------------------------FINAL SOLUTION----------------------------------\n\n");
    if(abruptBreak == 1 && infease != 1){
        printf("\nUNBOUNDED SOLUTION\n");
    }
    if(abruptBreakDS == 1 && infease != 1){
        printf("\nUNBOUNDED SOLUTION\n");
    }
    if(infease == 1){
        printf("\nINFEASIBLE SOLUTION\n");
    }
    if(abruptBreak != 1 && infease != 1 && abruptBreakDS != 1){
        for(i = 0; i < countmaxzterms; i++){
             printf("x[%d] = %2.2lf\n",i+1,solutionGomoryCut[i]);
        }
        double f = 0.0;
        if(response == 1){
            for(i = 0; i < countmaxzterms; i++){
                f = f + c[i]*solutionGomoryCut[i];
            }
            f += constNum;
            printf("Maximum Value = %2.2lf\n",f);
        }
        if(response == 2){
            for(i = 0; i < countmaxzterms; i++){
                f = f + (-1)*c[i]*solutionGomoryCut[i];
                f += constNum;
            }
            printf("Minimum Value = %2.2lf\n",f);
        }
    }
    printf("\n--------------------------------------------------------------------------------\n");
    

    printf("\nPress any key for exit...\n");
    getchar();
    return 0;
}


int checkSolution(int basic[N], double fracPart[], int constraint){
    int flag = 1,i,condition1,condition2,condition3,condition4,condition5;
    printf("\n");
    for(i = 0; i < constraint; i++){
        if(basic[i] < countmaxzterms && (fracPart[basic[i]] != 0.0)){
            condition1 = basic[i] <= countmaxzterms && ( (fracPart[basic[i]] != 0.0) || (fracPart[basic[i]] != -0.0) );
            condition2 = basic[i] <= countmaxzterms;
            condition3 = (fracPart[basic[i]] != 0.0);
            condition4 = (fracPart[basic[i]] != -0.0);
            condition5 = (fracPart[basic[i]] == -0.0);
            //printf("fracpart = %2.2lf\n",fracPart[basic[i]]);
            //printf("i = %d, condition1 = %d, condition2 = %d, condition3 = %d, condition4 = %d,  condition5 = %d\n",i,condition1,condition2,condition3,condition4,condition5);
            flag = 0;
            break;
        }
    }
    return flag;
}


void dualTableForm(double a[N][M], double b[N], double fracPart[N], int cons, int countz, int basic[N]){
    int i,j,posFrac;
    double maxFrac;
    printf("\nBasic Matrix:\n");
    for(j = 0; j < cons+countz; j++){
        printf("%d ",basic[j]);
    }
    printf("\nb Matrix:\n");
    for(j = 0; j < cons+countz; j++){
        printf("%2.2lf ",b[j]);
    }
    //printf("\n");
    for(i = 0; i < cons+countz; i++){
        if(basic[i] < countz){
            fracPart[basic[i]] = fracX(b[i]);
            printf("\ni = %d, fracPart[%d] = %2.2lf, fracX(%2.2lf) = %2.2lf\n",i,basic[i],fracPart[basic[i]],b[i],fracX(b[i]));
        }
    }
    printf("\nFracPart Matrix:\n");
    for(j = 0; j < cons+countz; j++){
        printf("%2.2lf ",fracPart[j]);
    }
    printf("\n");
    maxFrac = 0.0;
    for(i = 0; i < cons+countz; i++){
        if(fracPart[i] > maxFrac){
            posFrac = i;
            maxFrac = fracPart[i];
        }
    }
    printf("maxFrac = %2.2lf, posFrac= %d\n",maxFrac,posFrac);
    printf("\nEntering fraction column:\n");
    for(i = 0; i < cons; i++){
    if(basic[i] == posFrac){

        for(j = 0; j < countz + cons; j++){
                if(fracX(a[i][j]) == 0.0){
                    a[cons][j] = 0.0;
                    printf("%2.2lfx%d + ",a[cons][j],j+1);
                }
                else{
                    a[cons][j] = (-1)*fracX(a[i][j]);
                     printf("%2.2lfx%d + ",a[cons][j],j+1);
                }
        }
        a[cons][countz+cons] = 1.0;
        //printf("%2.2lfx%d",a[cons][countz+cons],countz+cons+1);
        if(fracX(b[i]) == 0.0){
            b[cons] = 0.0;
        }
        else{
            b[cons] = (-1)*fracX(b[i]);
            printf(" <= %2.2lf",b[cons]);
        }
        break;
    }
    else{continue;}
  }
  printf("\nIn Equation:\n");
  for(i = 0; i < cons; i++){
    if(basic[i] == posFrac){

        for(j = 0; j < countz + cons; j++){
                if(fracX(a[i][j]) == 0.0){
                    a[cons][j] = 0.0;
                    printf("%2.2lfx%d + ",a[cons][j],j+1);
                }
                else{
                    a[cons][j] = (-1)*fracX(a[i][j]);
                     printf("%2.2lfx%d + ",a[cons][j],j+1);
                }
        }
        a[cons][countz+cons] = 1.0;
        printf("%2.2lfx%d",a[cons][countz+cons],countz+cons+1);
        if(fracX(b[i]) == 0.0){
            b[cons] = 0.0;
        }
        else{
            b[cons] = (-1)*fracX(b[i]);
            printf(" == %2.2lf",b[cons]);
        }
        break;
    }
    else{continue;}
  }
  j = cons;
  for( i = cons+countz ; i < N; i++){
    basic[j] = i;
    j++;
  }
  /*printf("\nNew Basic Matrix\n");
  for(i = 0; i < 10; i++){
    printf("%d ",basic[i]);
  }*/
}


double fracX1(double x){
    double i,result;
    if(x < 0.0){
        i = ceil(x);
    }
    else{
        i = floor(x);
    }
    //printf("\nx = %2.2lf, i = %2.2lf, floor(%2.2lf) = %2.2lf\n",x,i,x,floor(x));
    result = x - i;
    return result;
}

double fracX(double x){
    double i,result;
    double y;
    y = fmod(x, 1.0);
    if(y < 0.0){
        y = 1.0 + y;
    }
    return y;
}
