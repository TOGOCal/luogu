package P4310;

import java.io.*;

public class Main {

    public static int MAXN = 32;
    public static int[] pre = new int[MAXN];//最后一个数字的弟j位是1的最长子序列长度

    public static int MAXL = 100001;
    public static int[] arr = new int[MAXL];

    public static void main(String[] args) throws IOException {

        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken();
        int realNum = (int)in.nval;

        for(int i = 0 ; i < realNum ; i ++){

            in.nextToken();
            arr[i] = (int)in.nval;
        }

        //初始判定
        int first = arr[0];
        for(int i = 0 ; i < MAXN ; i ++){

            if((first & (1 << i)) != 0)
                pre[i] = 1;
            else
                pre[i] = 0;
        }

        for(int i = 1 ; i < realNum ; i ++){

            int num = arr[i];
            int max = 0;
            for(int j = 0 ; j < MAXN ; j ++){

                //当前位置向前寻找对应位置不为1的
                if((num & (1 << j)) != 0){

                    max = Math.max(max , pre[j]);
                }
            }

            max += 1;
            for(int j = 0 ; j < MAXN ; j ++){

                //当前位置向前寻找对应位置不为1的
                if((num & (1 << j)) != 0){

                    pre[j] = Math.max(max , pre[j]);
                }
            }
        }

        int max = 0;
        for(int i = 0 ; i < MAXN ; i++)
            max = Math.max(max , pre[i]);

        out.println(max);
        out.flush();
        out.close();
    }
}
