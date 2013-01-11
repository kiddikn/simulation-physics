import java.io.*;
import java.util.Random;

public class Ex10{
    public static void main(String args[]){
        int nk = 0,nm = 99,dx = 1,org = 50,M = 1,G = 1,N = 500,T = 50;
        double dt = 0.1,H = 3.5;
        Expansion Grav = new Expansion(500);  //step1
        Grav.setup(N,H,org,dt,nk,nm);         //step2,3,4
        for(int n = 0;n < nk;n++){            //step5
            Grav.grid();                      //step6
            Grav.gauss(G,dx,T);               //step7
            Grav.calculateF(dx);              //step8
            Grav.astro(M,dt);                 //step9
        }
        Grav.output(nk);
    }
}

class Expansion{
    private double[] x0,y0,x,y,vx,vy;
    private double[][] phi,fx,fy;
    private int[][] grid;
    private double G,M,dt,p1,p2,rou,fpx,fpy;
    private int N,ix,iy,nk,nm;

    Random rnd = new Random(141); 
    Expansion(int N){    //step1初期設定
        int count = 0;
        x0 = new double[N];   //変更を加える配列
        y0 = new double[N];
        x  = new double[N];   //初期値を格納する配列
        y  = new double[N];
        for (int k = 0; k < N*4; k++){
            if(count < N){
                double x1 = 10*rnd.nextDouble() - 5;  
                double y1 = 10*rnd.nextDouble() - 5;  
                if(x1*x1+y1*y1<=5*5){
                    x0[count] = x1;
                    y0[count] = y1;
                    x[count]  = x1;
                    y[count]  = y1;
                    count++;
                }
                if(k==N*4) System.out.println("we cannot have suitable data\n");
            }else{break;}
        }
    }

    public void setup(int n,double H,int org,double Dt,int Nk,int Nm){
        N = n;              //step2,3 初速度の計算、平行移動
        dt = Dt;
        nk = Nk;
        nm = Nm;
        vx = new double[N];
        vy = new double[N];
        for(int i = 0;i < N;i++){
            vx[i] = H * x0[i]; //初速度を設定
            vy[i] = H * y0[i];
            x0[i] += org;
            y0[i] += org;             //全天体の位置を第一象限へ  
        }
        phi = new double[N+2][N+2];    //step4ポテンシャルの初期設定
        for(int i=0;i < N+2;i++)      
            for(int j=0;j < N+2;j++)
                phi[i][j] = 0;
    }
    
    //step6rouの計算,NGP
    public void grid(){      
        grid = new int[nm][nm];
        for(int j=0;j < nm;j++)
            for(int k=0;k < nm;k++)
                grid[j][k] = 0;

        for(int i=0;i < N;i++){
                int rx = (int)(x0[i] + 0.5);
                int ry = (int)(y0[i] + 0.5);
                grid[rx][ry]++;
        }
    }

    //step7ガウスザイデル方の計算
    public void gauss(double G,int dx,int T){
        for(int i = 0;i < T;i++)               
            for(ix = 1;ix < nm;ix++)
                for(iy = 1;iy < nm;iy++){
                    p1 = phi[ix+1][iy]+phi[ix-1][iy]+phi[ix][iy+1]+phi[ix][iy-1];
                    rou = grid[ix][iy];
                    p2 =  G * rou * dx * dx;
                    phi[ix][iy] = (p1 - p2)/4;
                }
    }

    //step8重力場Fを計算
    public void calculateF(double dx){         
        fx = new double[N][N];
        fy = new double[N][N];
        for(ix = 1;ix < nm;ix++) 
            for(iy = 1;iy < nm;iy++){
                
            }
        for(ix = 1;ix < nm;ix++)
            for(iy = 1;iy <= nm;iy++){
                fx[ix][iy]=-(phi[ix+1][iy]-phi[ix][iy])/dx;     
                fy[ix][iy]=-(phi[ix][iy+1]-phi[ix][iy])/dx;  
            }
    }

    //step9天体の運動
    public void astro(double M,double dt){
        for(int ip=1;ip<N;ip++){
            int rx = (int)(x0[ip] + 0.5); 
            int ry = (int)(y0[ip] + 0.5);
            fpx = M * fx[rx][ry];
            fpy = M * fy[rx][ry];
            vx[ip] += (fpx/M)*dt;
            vy[ip] += (fpy/M)*dt;        
            x0[ip] += vx[ip]*dt;
            y0[ip] += vy[ip]*dt;
        }
    }

    public void output(int k){
        try{
            //FileWriterクラスのインスタンス作成
            PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter("data" + k + ".csv")));
            //ファイルに書き込み
            for(int i = 0;i < x0.length;i++){
                fw.print(x[i]+",");
                fw.print(y[i]+",");
                fw.print(x0[i]+",");
                fw.println(y0[i]);
            }
            System.out.println("ファイルに書き込みました");
            //ファイルをクローズ
            fw.close();
        }catch(IOException e){
            System.out.println(e + "例外が発生しました");
        }
    }
}


