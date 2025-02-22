package from_1000_to_1500;

import java.io.*;
import java.util.Arrays;

public class P1006 {

    public static int MAXN = 51;
    public static int[][] arr = new int[MAXN][MAXN];
    public static int m , n;
    public static int[][][] dp = new int[MAXN][MAXN][MAXN];

    public static void main(String[] args) throws IOException {

        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken();
        m = (int)in.nval;
        in.nextToken();
        n = (int)in.nval;

        for(int i = 0 ; i < m ; i ++){

            int[] a = arr[i];
            for(int j = 0 ; j < n ; j++){

                in.nextToken();
                a[j] = (int)in.nval;
                Arrays.fill(dp[i][j] , Integer.MIN_VALUE);
            }
        }//完成输入
        dp[m - 1][n - 1][m - 1] = arr[m - 1][n - 1];
        out.println(Math.max(dp(0 , 0 , 0) , 0));
        out.flush();
        out.close();
    }

    public static int dp(int a , int b , int c) {

        if(dp[a][b][c] != Integer.MIN_VALUE)
            return dp[a][b][c];

        int d = a + b - c;
        int res;
        if(a >= m || b >= n || c >= m || d >= n)
            res = -1;
//        else if(a == m - 1 && b == n - 1)
//            res = arr[a][b];///终点
        else if(a == c && b == d && !(a == 0 && b == 0))//不允许一个点到达两次
            res = -1;
        else {

            int thisStep = arr[a][b] + arr[c][d];
            res = Math.max(
                    Math.max(dp(a + 1 , b , c + 1) , dp(a , b + 1 , c + 1)),
                    Math.max(dp(a + 1 , b  , c) , dp(a , b + 1 , c))
                    );
            if(res != -1)
                res += thisStep;
        }

        dp[a][b][c] = res;
        return res;
    }
}
