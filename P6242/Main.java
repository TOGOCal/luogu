package P6242;

import java.io.*;

public class Main {

    public static final int MAXN = 500001;
    public static final long IMPOSSIBLE_MIN_VALUE = Long.MIN_VALUE;
    public static final int[] arr = new int[MAXN];//原数组
    public static int length;
    public static final long[] sum = new long[MAXN << 2];//和
    public static final long[] max = new long[MAXN << 2];//最大值
    public static final long[] cnt = new long[MAXN << 2];//最大值数量
    public static final long[] sec = new long[MAXN << 2];//严格次大值
    public static final long[] maxAdd = new long[MAXN << 2];//最大值加上的值
    public static final long[] otherAdd = new long[MAXN << 2];//其他值加上的值

    public static final long[] maxHistory = new long[MAXN << 2];//最大值的历史
    public static final long[] maxAddHistory = new long[MAXN << 2];//最大值
    public static final long[] otherAddHistory = new long[MAXN << 2];

    public static void build(){
        buildRecursion(0 , length - 1, 1);
    }

//    private static void buildRecursion(int left , int right , int treeIndex){
//
//        if(left == right){
//
//            int value =  arr[left];
//
//            cnt[treeIndex] = 1;
//            sec[treeIndex] = IMPOSSIBLE_MIN_VALUE;
//            sum[treeIndex] = max[treeIndex] = maxHistory[treeIndex] = value;
//            return;
//        }else{
//
//            int mid = getMid(left , right);
//
//            int leftIndex = treeIndex << 1;
//            int rightIndex = treeIndex << 1 | 1;
//            buildRecursion(left , mid , leftIndex);
//            buildRecursion(mid + 1 , right , rightIndex);
//            up(treeIndex);
//        }
//        maxAdd[treeIndex] = otherAdd[treeIndex] = maxAddHistory[treeIndex] = otherAddHistory[treeIndex] = 0;
//    }

    public static void buildRecursion(int l, int r, int i) {
        if (l == r) {
            sum[i] = max[i] = maxHistory[i] = arr[l];
            sec[i] = IMPOSSIBLE_MIN_VALUE;
            cnt[i] = 1;
        } else {
            int mid = (l + r) >> 1;
            buildRecursion(l, mid, i << 1);
            buildRecursion(mid + 1, r, i << 1 | 1);
            up(i);
        }
        maxAdd[i] = otherAdd[i] = maxAddHistory[i] = otherAddHistory[i] = 0;
    }

    private static int getMid(int left , int right){

        return (left + right) >> 1;
    }

    public static void addRecursion(int left , int right , int value ,
                          int nowLeft , int nowRight , int treeIndex){

        if(left <= nowLeft && nowRight <= right){
            lazy(treeIndex , nowRight - nowLeft + 1 ,value , value , value , value);
            return;
        }

        int mid = getMid(nowLeft , nowRight);
        down(treeIndex , mid - nowLeft + 1 , nowRight - mid);
        if(left <= mid)
            addRecursion(left , right , value,
                    nowLeft , mid , treeIndex << 1);
        if(mid < right)
            addRecursion(left , right, value,
                    mid + 1 , nowRight , treeIndex << 1 | 1);
        up(treeIndex);
    }



    private static void up(int treeIndex){

        int leftIndex = treeIndex << 1;
        int rightIndex = treeIndex << 1 | 1;
        sum[treeIndex] = sum[leftIndex] + sum[rightIndex];
        maxHistory[treeIndex] = Math.max(maxHistory[leftIndex], maxHistory[rightIndex]);
        max[treeIndex] = Math.max(max[leftIndex] , max[rightIndex]);

        if(max[leftIndex] > max[rightIndex]){

            cnt[treeIndex] = cnt[leftIndex];
            sec[treeIndex] = Math.max(sec[leftIndex], max[rightIndex]);
        }else if(max[leftIndex] < max[rightIndex]){

            cnt[treeIndex] = cnt[rightIndex];
            sec[treeIndex] = Math.max(max[leftIndex] , sec[rightIndex]);
        }else{

            cnt[treeIndex] = cnt[leftIndex] + cnt[rightIndex];
            sec[treeIndex] = Math.max(sec[leftIndex] , sec[rightIndex]);
        }


    }



    private static void down(int treeIndex , int leftLength , int rightLength){

        int leftIndex = treeIndex << 1;
        int rightIndex = treeIndex << 1 | 1;

        long realMax = Math.max(max[leftIndex] , max[rightIndex]);//得到真的最大值

        if(realMax == max[leftIndex])//最大值在左边
            lazy(leftIndex , leftLength ,
                    maxAdd[treeIndex] , otherAdd[treeIndex],
                    maxAddHistory[treeIndex] , otherAddHistory[treeIndex]);
        else
            lazy(leftIndex , leftLength,
                    otherAdd[treeIndex], otherAdd[treeIndex],
                    otherAddHistory[treeIndex], otherAddHistory[treeIndex]);

        if(realMax == max[rightIndex])
            lazy(rightIndex , rightLength ,
                    maxAdd[treeIndex] , otherAdd[treeIndex],
                    maxAddHistory[treeIndex] , otherAddHistory[treeIndex]);
        else
            lazy(rightIndex , rightLength,
                    otherAdd[treeIndex], otherAdd[treeIndex],
                    otherAddHistory[treeIndex], otherAddHistory[treeIndex]);

        maxAdd[treeIndex] = otherAdd[treeIndex] = maxAddHistory[treeIndex] = otherAddHistory[treeIndex] = 0;
    }



    private static void lazy(int treeIndex, int length,
                             long maxAddValue , long otherAddValue ,
                             long maxAddHistoryValue , long otherAddHistoryValue){

        maxHistory[treeIndex] = Math.max(maxHistory[treeIndex] , max[treeIndex] + maxAddHistoryValue);
        maxAddHistory[treeIndex] = Math.max(maxAddHistory[treeIndex] , maxAdd[treeIndex] +  maxAddHistoryValue);
        otherAddHistory[treeIndex] = Math.max(otherAddHistory[treeIndex] , otherAdd[treeIndex] + otherAddHistoryValue);


        sum[treeIndex] += maxAddValue * cnt[treeIndex] + otherAddValue * (length - cnt[treeIndex]);
        max[treeIndex] += maxAddValue;
        maxAdd[treeIndex] += maxAddValue;

        sec[treeIndex] += (sec[treeIndex] == IMPOSSIBLE_MIN_VALUE ? 0 : otherAddValue);
        otherAdd[treeIndex] += otherAddValue;
   }



   public static void setMinRecursion(int left , int right , int value ,
                             int nowLeft , int nowRight , int treeIndex){

        if(value >= max[treeIndex])
            return;

        if((left <= nowLeft && nowRight <= right)
            && sec[treeIndex] < value){
            lazy(treeIndex , nowRight - nowLeft + 1 , value - max[treeIndex] , 0 , value - max[treeIndex] , 0);
            return;
        }

        int mid = getMid(nowLeft , nowRight);
        down(treeIndex , mid - nowLeft + 1 , nowRight - mid);
        if(left <= mid)
            setMinRecursion(left , right ,value ,
                    nowLeft , mid , treeIndex << 1);
        if(mid < right)
            setMinRecursion(left , right , value ,
                    mid + 1 , nowRight , treeIndex << 1 | 1);

        up(treeIndex);
   }



    public static long sumRecursion(int left , int right ,
                                    int nowLeft , int nowRight , int treeIndex){

        if(left <= nowLeft && nowRight <= right)
            return sum[treeIndex];

        int mid = getMid(nowLeft,  nowRight);
        down(treeIndex , mid - nowLeft + 1 , nowRight - mid);
        long res = 0;
        if(left <= mid)
            res += sumRecursion(left , right ,
                    nowLeft , mid , treeIndex << 1);
        if(mid < right)
            res += sumRecursion(left , right ,
                    mid + 1 , nowRight , treeIndex << 1 | 1);
        return res;
    }



    public static long maxRecursion(int left , int right ,
                                    int nowLeft , int nowRight , int treeIndex){

        if(left <= nowLeft && nowRight <= right)
            return max[treeIndex];

        int mid = getMid(nowLeft , nowRight);
        down(treeIndex , mid - nowLeft + 1 , nowRight - mid);
        long res = Long.MIN_VALUE;
        if(left <= mid)
            res = Math.max(maxRecursion(left , right, nowLeft, mid , treeIndex << 1) , res);
        if(mid < right)
            res = Math.max(maxRecursion(left , right , mid + 1 , nowRight  ,treeIndex << 1 | 1) , res);

        return res;
    }


    public static long historyRecursion(int left , int right ,
                                        int nowLeft , int nowRight , int treeIndex){

        if(left <= nowLeft && nowRight <= right)
            return maxHistory[treeIndex];

        int mid = getMid(nowLeft , nowRight);
        down(treeIndex , mid - nowLeft + 1 , nowRight - mid);
        long res = Long.MIN_VALUE;
        if(left <= mid)
            res = Math.max(historyRecursion(left , right, nowLeft , mid , treeIndex << 1) , res);
        if(mid < right)
            res = Math.max(historyRecursion(left , right, mid + 1 , nowRight , treeIndex << 1 | 1) , res);

        return res;
    }


    public static void main(String[] args) throws IOException {

        StreamTokenizer in = new StreamTokenizer(
                new BufferedReader(new InputStreamReader(System.in))
        );
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken();
        length = (int)in.nval;

        in.nextToken();
        int functionTime = (int)in.nval;

        for(int i = 0 ; i < length ; i++){

            in.nextToken();
            arr[i] = (int)in.nval;
        }

        build();

        for(int t = 0 ; t < functionTime ; t ++){

            in.nextToken();
            int functionType = (int)in.nval;
            in.nextToken();
            int left = (int)in.nval;
            in.nextToken();
            int right = (int)in.nval;
            int value;
            switch (functionType){
                case 1 :
                    in.nextToken();
                    value = (int)in.nval;
                    addRecursion(left , right , value , 1 , length , 1);
                    break;
                case 2 :
                    in.nextToken();
                    value = (int)in.nval;
                    setMinRecursion(left , right, value , 1 , length , 1);
                    break;
                case 3:
                    out.println(sumRecursion(left , right , 1, length , 1));
                    break;
                case 4:
                    out.println(maxRecursion(left , right , 1 , length , 1));
                    break;
                case 5:
                    out.println(historyRecursion(left , right , 1 , length , 1));
                    break;
            }
        }


        out.flush();
        out.close();
    }

}
