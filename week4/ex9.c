#include<stdio.h>
#include<stdlib.h>
#define N 2    //表示する行列の大きさ
#define T 2000 //ガウスザイデル法反復回数

double ro(double x,double y){
    return (6*x-3*y);
}


void gauss(double a[][N+2],double G,int dx){
    int ix,iy;
    double p1,p2,rou;
    for(ix = 1;ix <= N;ix++)
        for(iy = 1;iy <= N;iy++){
            p1 = a[ix+1][iy]+a[ix-1][iy]+a[ix][iy+1]+a[ix][iy-1];
            rou = ro(ix,iy);
            p2 =  G * rou * dx * dx;
            a[ix][iy] = (p1 - p2)/4;
        }
            
}

void init(double a[][N+2]){
    int i,j;
    for(i = 0;i <= N+1;i++)
        for(j = 0;j <= N+1;j++)
           a[i][j]=0.0;   
}

void disp(double a[][N+2]){
    int i,j;
    for(i = N;i >= 1;i--){
       for(j = 1;j <= N;j++)
            printf(" %13f",a[j][i]);
    printf("\n");
    }
}

int main(){
    double phi[N+2][N+2];
    int t;
    init(phi);
    phi[1][3]=22.5;
    phi[2][3]=36; 
    phi[3][1]=-4.5; 
    phi[3][2]=9; 

    for(t = 0;t < T;t++)
       gauss(phi,1.0,1); 

    disp(phi);
    return 0;
}
