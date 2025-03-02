package from_1500_to_2000;

import java.io.*;
import java.util.Arrays;

public class P1613 {

    public static int MAXN = 51;
    public static int MAXK = 64 + 1;
    public static long[][] path = new long[MAXK][MAXN];
    public static long[] last = new long[MAXN];
    public static int[][] time = new int[MAXN][MAXN];

    public static void main(String[] args) throws IOException {

        for(long[] p : path)
            Arrays.fill(p , 0);


        for(int[] t : time)
            Arrays.fill(t , Integer.MAX_VALUE >> 1);

        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken();
        int nodeNum = (int)in.nval;

        in.nextToken();
        int pathNum = (int)in.nval;

        for(int i = 0 ; i < pathNum ; i ++){

            in.nextToken();
            int from = (int)in.nval;
            in.nextToken();
            int to = (int)in.nval;

            buildPath(from , to , 0);//走1步能到的地方
        }

        //枚举k的个数
        for(int k = 1 ; k <= 64 ; k ++){

            long[] arr = path[k];
            long[] pre = path[k - 1];
            for(int i = 1 ; i <= nodeNum ; i ++){
                //枚举所有点
                long p = pre[i];
                long res = 0;
                for(int j = 1 ; j <= nodeNum ; j ++){

                    //有路
                    if((p & (1L << j)) > 0){

                        res |= pre[j];//之前pre能到的，现在我也能到了
                    }
                }
                arr[i] = res;
            }
        }

        //最后加工，代表所有一步之内能到的点
        for(int i = 1 ; i <= nodeNum; i ++){

            long res = 0;
            for(int k = 0 ; k <= 64 ; k ++){

                res |= path[k][i];//所有能到的点
            }
            last[i] = res;
        }//1 2 4 8 步能到的点

        for(int i = 1 ; i <= nodeNum ; i ++){

            long reach = last[i];
            for(int j = 1 ; j <= nodeNum ; j ++){

                if((reach & (1L << j)) != 0){

                    time[i][j] = 1;
                }
            }
        }

       //使用feloyd算法
        for(int jump = 1 ; jump <= nodeNum ; jump ++){

            for(int begin = 1 ; begin <= nodeNum ; begin++){

                int[] t = time[begin];
                for(int end = 1 ; end <= nodeNum ; end++){

                    //计算两个点之间的最短距离
//                    time[begin][end] = Math.min(time[begin][end] ,
//                            time[begin][jump] + time[jump][end]);
                    t[end] = Math.min(t[end] ,
                            t[jump] + time[jump][end]);
                }
            }
        }

        out.println(time[1][nodeNum]);
        out.flush();
        out.close();
    }

    public static void buildPath(int from , int to , int k){

        long[] arr = path[k];
        arr[from] |= (1L << to);
    }
}
