import java.io.*;
import java.util.Random;


public class Simulate{
    public static void main(String args[]){
        int N = 500;
        Expansion simu = new Expansion(N);
        simu.Nogravity(N,3.5,50,0.1,20);
        simu.output();
    }
}

class Expansion{
    private double[] x0,y0,x,y,vx,vy;
    
    Random rnd = new Random(141); 
    Expansion(int N){
        int count = 0;
        x0 = new double[N];
        y0 = new double[N];
        x  = new double[N];
        y  = new double[N];
        for (int k = 0; k < N*4; k++){
         if(count < N){
            double x1 = 2*rnd.nextDouble() - 1;  
            double y1 = 2*rnd.nextDouble() - 1;  
            if(x1*x1+y1*y1<=1){
                x0[count] = x1;
                y0[count] = y1;
                x[count] = x1;
                y[count] = y1;
                count++;
            }
            if(k==N*4) System.out.println("we cannot have suitable data\n");
         }else{break;}
        }
    }

    public void Nogravity(int N,double H,int org,double dt,int nk){
        vx = new double[N];
        vy = new double[N];
        for(int i = 0;i < N;i++){
            vx[i] = H * x0[i]; //初速度を設定
            vy[i] = H * y0[i];
            x0[i] += org;
            y0[i] += org;             //全天体の位置を第一象限へ  
        }
        for(int t = 0;t <= nk;t++)
            for(int i = 0;i < N;i++){
                x0[i] += vx[i]*dt;
                y0[i] += vy[i]*dt; 
            }

    }

    public void output(){
        try{
            //FileWriterクラスのインスタンス作成
            PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter("data1.csv")));
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
