import java.util.Random;

public class Grid{
    public static void main(String[] args){
        int N = 200;
    MakeGrid grid1 = new MakeGrid(0,4);  //オブジェクトの作成
    for(int i = 0;i < N;i++) grid1.makeMatrix();
    grid1.printMatrix();
    }
}

class MakeGrid{
   public int range;
   private int[][] grid;
   //初期化コンストラクタrangeを与えてgridを初期化
   MakeGrid(int min,int max){
       range = max - min + 1;
       grid = new int[range][range];
       for (int k = 0; k < 5; k++) {
           for (int j = 0; j < 5; j++) grid[k][j] = 0;
       }
   }
   Random rnd = new Random(141);                                                                                                                                      
   public void makeMatrix(){
       double x = rnd.nextDouble()*4;
       double y = rnd.nextDouble()*4;
       int a = (int)(x + 0.5);
       int b = (int)(y + 0.5);
        grid[a][b]++;
    }
    public void printMatrix(){
        System.out.println("grid = ");
        System.out.println("↑y");
        for(int i = grid.length - 1;i >= 0;i--){
            System.out.print("|");
            for(int j = 0;j < grid.length;j++) System.out.printf("%3d " ,grid[j][i]);
            System.out.println();
        }
        System.out.println("----------------------→x");
    }
}

