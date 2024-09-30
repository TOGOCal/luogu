package P1025;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int k = s.nextInt();

        int[][] f = new int[n + 1][k + 1];

        for (int i = 1;i <= n;i++) {
            f[i][1]=1;//将i分成一份只有一种分法
            f[i][0]=0;//将i分成0份没有分发
        }

        for (int x = 2;x <= k;x++) {
            f[1][x]=0;//不可能将1分成大于等于2份
            f[0][x]=0;//不可能将0分成大于等于2份
        }  // 边界，为了防止炸，把有0的也处理了

        for (int i=2;i<=n;i++){

            for (int x=2;x<=k;x++){

                if (i > x){//如果剩下的数字大于份数

                    f[i][x]=f[i-1][x-1]+f[i-x][x];
                } else {

                    f[i][x]=f[i-1][x-1];//其余情况只能一份一份的分了
                }
            }

        }


        System.out.println(f[n][k]);

        s.close();
    }

    public static int dfs(int n , int k , int beginNum){

        //将某个数分成一份
        if(k == 1){

            if(beginNum > n){

                return 0;//没有分法
            }

            return 1;//有分法
        }

        int sum = 0;

        //至少要给后面刘多少的位置
        for(int i = beginNum ; i <= (n - k) ; i++){

            int res = dfs(n - i , k - 1 , i);//最后参数传递i的原因：可以取相同的

            sum += res;

            if(res == 0){

                break;//有一个出现问题之后，说明其他的也出现了问题，没必要考虑了
            }
        }

        return sum;
    }
}
