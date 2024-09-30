package P1018;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    static BigInteger[][] dp;


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int length = s.nextInt();
        int num = s.nextInt();
        s.nextLine();

        String str = s.nextLine();
        int[] arr = new int[length];

        for(int i = 0 ; i < length ; i++){

            arr[i] = str.charAt(i) - '0';
        }//完成输入存储

        dp = new BigInteger[length + 1][num + 1];

        //定义：i位置的乘号能将i位置与i+1位置分开

        BigInteger max = dfs(num , 0 , arr);

        System.out.println(max);


        s.close();
    }

    static BigInteger zero = new BigInteger("0");

    //剩下的乘号，系啊一个能选择的位置，arr数组
    public static BigInteger dfs(int restNum , int canChoosePlace , int[] arr){

        if(dp[canChoosePlace][restNum] != null){

            return dp[canChoosePlace][restNum];
        }

        if(restNum > arr.length - 1 - canChoosePlace){

            dp[canChoosePlace][restNum] = zero;
            return zero;//这不是一种合理的情况
        }

        if(arr[canChoosePlace] == 0){

            dp[canChoosePlace][restNum] = zero;
            return zero;//这不是一种合理的情况
        }

        //没有剩下的乘号了，直接导入剩下的数字即可
        if(restNum == 0){
            StringBuilder sb = new StringBuilder();

            if(arr[canChoosePlace] == 0){

                dp[canChoosePlace][restNum] = zero;
                return zero;//这不是一种合理的情况
            }

            for(int i = canChoosePlace ; i < arr.length ; i++){

                sb.append(arr[i]);
            }

            dp[canChoosePlace][restNum] = new BigInteger(sb.toString());
            return dp[canChoosePlace][restNum];
        }//返回数据


        BigInteger max = zero;

        StringBuilder sb = new StringBuilder();

        for(int i = canChoosePlace ; i < arr.length - restNum ; i++){

            sb.append(arr[i]);

            BigInteger thisInt = new BigInteger(sb.toString());

            BigInteger temp = dfs(restNum - 1 , i + 1 , arr);

            temp = thisInt.multiply(temp);

            if(temp.compareTo(max) > 0){

                max = temp;
            }
        }

        dp[canChoosePlace][restNum] = max;
        return max;
    }
}
