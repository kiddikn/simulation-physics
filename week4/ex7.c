//-1から1の範囲で半径1の円内にある点の集合200組
#include<stdio.h>
#include<stdlib.h>
#define N 500

void makefile(double x0[],double y0[],double x[],double y[]){
  FILE *fo;
 char *fname;
 fname="data.csv";
 if((fo=fopen(fname,"w")) == NULL){
   printf("File[%s] does not open!!\n",fname);
   exit(0);
 }
 int i;
 for(i=0;i<N;i++)
    fprintf(fo,"%f,%f,%f,%f\n",x0[i],y0[i],x[i],y[i]);
        
 fclose(fo);
}


void makestar(double x0[],double y0[]){

  int count,n,i;    //countが200になるまで
  double x,y;
  n = 1000;       //countが200になるように
  count = 0;

  srand(193);     //設定
  for(i=0;i<=n;i++) {
	if(count < N){
                /*-1以上1以下の乱数を生成*/
                x = (2.0*rand()/(double)RAND_MAX)-1.0;  //castしておく必要あり
                y = (2.0*rand()/(double)RAND_MAX)-1.0;

                /*-1<=x,y<=1の範囲にある中心原点半径1の
                扇形の中に乱数による点が入ったらカウントする*/
                if((x*x+y*y)<=1.0) {
                        x0[count]=x;
                        y0[count]=y;
                        count++;
                }
	        if(i==n) printf("we cannot have suitable data\n");
			 
	}else{break;} 
}
}

void simulate(double x0[],double y0[],double H,int org,double dt,int nk){
    double vx[N],vy[N];
    int i,t;
    for(i = 0;i < N;i++){
        vx[i] = H * x0[i],vy[i] = H * y0[i];   //初速度を設定
        x0[i] += org,y0[i] += org;             //全天体の位置を第一象限へ  
    }
    for(t = 0;t <= nk;t++)
       for(i = 0;i < N;i++)
          x0[i] += vx[i]*dt,y0[i] += vy[i]*dt; 
}


int  main(){
    double x0[N],y0[N];
    double x[N],y[N];    
    makestar(x0,y0);
    int i;
    for(i = 0;i < N;i++)
        x[i]=x0[i],y[i]=y0[i];
    simulate(x,y,3.5,50,0.1,20);
    makefile(x0,y0,x,y);
    return 0;
}
