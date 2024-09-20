package P1043;

import java.util.*;

public class Main {

    static int[] prefixSum;
    static int[] arr;
    static int sum;

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);


        int n_num = s.nextInt();
        int m_partNum = s.nextInt();

        arr = new int[n_num];
        prefixSum = new int[n_num + 1];
        prefixSum[0] = 0;

        sum = 0;

        for(int i = 0 ; i< n_num ; i ++){

            arr[i] = s.nextInt();
            sum += arr[i];

            prefixSum[i + 1] = sum;//得到前缀和

        }//完成输入

        //枚举分界位置
        //index = i 位置的分解将 i和i - 1位置分开

        //int max = Integer.MIN_VALUE;


//        for(int i1 = 0 ; i1 < n_num - 4;i1 ++){
//            for(int i2 = i1 + 1 ; i2 < n_num - 3;i2 ++) {
//                for(int i3 = i2 + 1 ; i3 < n_num - 2;i3 ++) {
//                    for(int i4 = i3 + 1 ; i4 <= n_num - 1 ;i4 ++) {
//                        //开始执行枚举
//
//                        int sum1 = getSum(i1 , i2 - 1 , n_num ,prefixSum , arr);
//                        int sum2 = getSum(i2 , i3 - 1 , n_num , prefixSum ,arr);
//                        int sum3 = getSum(i3 , i4 - 1 , n_num , prefixSum ,arr);
//
//                        int sum4 = sum - (sum1 + sum2 + sum3);
//
//                        int res = mod(sum1 , 10) + mod(sum2 , 10) + mod(sum3 , 10) + mod(sum4 , 10);
//
//                        max = Math.max(res , max);
//
//                    }
//                }
//            }
//        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0 ; i <= n_num - m_partNum ; i ++ ){

            Node get = dfs(1 , m_partNum , i , 0);

            max = Math.max(max , get.max);
            min = Math.min(min , get.min);
        }

        //System.out.println(dfs(0 , m_partNum , 0 , 0));


        System.out.println(min);
        System.out.println(max);
        s.close();
    }


    static class Node{

        int max;
        int min;

        Node(int max , int min){

            this.max = max;
            this.min = min;
        }
    }


    static HashMap<String , Node> getRes = new HashMap<>();

    static String getString(int thisM , int start , int nowSum){

        return thisM + "|" + start + "|" + nowSum;
    }

    public static Node dfs(int thisM , int m , int start , int nowSum){

        String key = getString(thisM , start , nowSum);

        if(getRes.containsKey(key)){

            return getRes.get(key);
        }


        if(thisM == m){

            //return sum - nowSum;
            int res = sum - nowSum;
            res = mod(res , 10);
            return new Node(res , res);
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(int i = start ; i <= (arr.length - (m - thisM + 1)); i ++){

            int res = getSum(start , i);

            Node get = dfs(thisM + 1 , m , i + 1 , nowSum + res);

            res = mod(res , 10);

            min = Math.min(min , res * get.min);
            max = Math.max(max , res * get.max);
        }



        Node res = new Node(max , min);

        getRes.put(key , res);

        return res;


    }

    static int getSum(int left , int right){

        assert left <= right;

        if(left < right){

            return prefixSum[right + 1] - prefixSum[left];

        }else {

            return arr[left];
        }

    }


    static int mod(int a , int b){

        int res = a % b;

        return res < 0 ? res + b : res;
    }

}
