public class Gauss{
    public static void main(String[] args){
        int T = 2000;
    calgauss gauss1 = new calgauss(2);  //オブジェクトの作成、初期化
    for(int i = 0;i < T;i++) 
        gauss1.calculate(1.0,1);
    gauss1.printMatrix();
    }
}

class calgauss{
   private double[][] phi;
   private int ix,iy;
   private double p1,p2,rou;

   calgauss(int N){      //Nは計算したい行列の値
       phi = new double[N+2][N+2];
       for (int k = 0; k < N+2; k++) 
           for (int j = 0; j < N+2; j++) phi[k][j] = 0;
       phi[1][3]=22.5;
       phi[2][3]=36;
       phi[3][1]=-4.5;
       phi[3][2]=9;
   }

   public void calculate(double G,int dx){
       for(ix = 1;ix <= phi.length-2;ix++)
           for(iy = 1;iy <= phi.length-2;iy++){
               p1 = phi[ix+1][iy]+phi[ix-1][iy]+phi[ix][iy+1]+phi[ix][iy-1];
               rou = 6 * ix - 3 * iy;
               p2 =  G * rou * dx * dx;
               phi[ix][iy] = (p1 - p2)/4;
           }
   }

   public void printMatrix(){
        for(int i = phi.length - 2;i >= 1;i--){
            for(int j = 1;j < phi.length - 1;j++)
                 System.out.printf("%7f " ,phi[j][i]);
        System.out.println();
        }
    }
}

