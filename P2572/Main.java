package P2572;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static int length;
    public static int MAXN = 100001;
    public static boolean[] arr = new boolean[MAXN];
    public static int[] num = new int[MAXN << 2];
    public static int[] maxLen1 = new int[MAXN << 2];
    public static int[] preLen1 = new int[MAXN << 2];
    public static int[] aftLen1 = new int[MAXN << 2];

    public static int[] maxLen0 = new int[MAXN << 2];
    public static int[] preLen0 = new int[MAXN << 2];
    public static int[] aftLen0 = new int[MAXN << 2];

    public static boolean[] isReverse = new boolean[MAXN << 2];//是否翻转
    public static boolean[] isSame = new boolean[MAXN << 2];//是否被刷成一样的了
    public static boolean[] whatNum = new boolean[MAXN << 2];//如果是一样的，被刷成什么了（true:1 , false : 0

    //初始化
    public static void init(){

        Arrays.fill(num , 0);
        Arrays.fill(maxLen1, 0);
        Arrays.fill(preLen1, 0);
        Arrays.fill(aftLen1, 0);
        Arrays.fill(isReverse , false);
        Arrays.fill(isSame , false);
        initDfs(1 , length , 1);
    }

    public static int initDfs(int nowLeftIndex , int nowRightIndex , int treeIndex){

        if(nowLeftIndex == nowRightIndex){

            int n = arr[nowLeftIndex] ? 1 : 0;
            int m = arr[nowLeftIndex] ? 0 : 1;
            num[treeIndex] = n;
            maxLen1[treeIndex] = n;
            preLen1[treeIndex] = n;
            aftLen1[treeIndex] = n;

            maxLen0[treeIndex] = m;
            preLen0[treeIndex] = m;
            aftLen0[treeIndex] = m;
            return n;
        }

        int res = 0;
        int mid = getMid(nowLeftIndex , nowRightIndex);
        res += initDfs(nowLeftIndex , mid , treeIndex << 1);
        res += initDfs(mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        num[treeIndex] = res;//总共有这么多个1

//        maxLen1[treeIndex] = Math.max(
//                Math.max(maxLen1[treeIndex << 1], maxLen1[treeIndex << 1 | 1]),
//                aftLen1[treeIndex << 1] + preLen1[treeIndex << 1 | 1]
//                //左侧的后面 和 右侧的前面
//        );
//
//        maxLen0[treeIndex] = Math.max(
//                Math.max(maxLen0[treeIndex << 1], maxLen0[treeIndex << 1 | 1]),
//                aftLen0[treeIndex << 1] + preLen0[treeIndex << 1 | 1]
//                //左侧的后面 和 右侧的前面
//        );
//
////        if(preLen[treeIndex << 1] == mid - nowLeftIndex + 1)//左侧被贯通了
////            preLen[treeIndex] = preLen[treeIndex << 1] + preLen[treeIndex << 1 | 1];
////        else
////            preLen[treeIndex] = preLen[treeIndex << 1];
////
////        if(aftLen[treeIndex << 1 | 1] ==  nowRightIndex - (mid + 1) + 1)
////            aftLen[treeIndex] = aftLen[treeIndex << 1 | 1] + aftLen[treeIndex << 1];
////        else
////            aftLen[treeIndex] = aftLen[treeIndex << 1 | 1];
//
//        preLen1[treeIndex] = preLen1[treeIndex << 1];
//        if(preLen1[treeIndex << 1] == mid - nowLeftIndex + 1)
//            preLen1[treeIndex] += preLen1[treeIndex << 1 | 1];
//
//        aftLen1[treeIndex] = aftLen1[treeIndex << 1 | 1];
//        if(aftLen1[treeIndex << 1 | 1] == nowRightIndex - mid)
//            aftLen1[treeIndex] += aftLen1[treeIndex << 1];
//
//        preLen0[treeIndex] = preLen0[treeIndex << 1];
//        if(preLen0[treeIndex << 1] == mid - nowLeftIndex + 1)
//            preLen0[treeIndex] += preLen0[treeIndex << 1 | 1];
//
//        aftLen0[treeIndex] = aftLen0[treeIndex << 1 | 1];
//        if(aftLen0[treeIndex << 1 | 1] == nowRightIndex - mid)
//            aftLen0[treeIndex] += aftLen0[treeIndex << 1];
        getMaxPreAft(treeIndex , maxLen1 , preLen1 , aftLen1 , mid - nowLeftIndex + 1 , nowRightIndex - mid);
        getMaxPreAft(treeIndex , maxLen0 , preLen0 , aftLen0 , mid - nowLeftIndex + 1 , nowRightIndex - mid);
        return res;
    }


    public static void changeAllToZero(int left , int right){

        zeroDfs(left , right , 1 , length , 1);
    }

    private static void zeroDfs(int left , int right,
                         int nowLeftIndex , int nowRightIndex , int treeIndex){

        //完成懒更新
        if(left <= nowLeftIndex && nowRightIndex <= right){

            lazySame(false , treeIndex , nowRightIndex - nowLeftIndex + 1);
            return;
        }



        int mid = getMid(nowLeftIndex , nowRightIndex);
        //检查当前懒更新
        down(treeIndex , nowLeftIndex , nowRightIndex);


        //完成信息左右传递
        if(left <= mid)
            zeroDfs(left , right,
                    nowLeftIndex , mid , treeIndex << 1);

        if(mid < right)
            zeroDfs(left , right,
                    mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        up(nowLeftIndex , nowRightIndex , treeIndex);
    }


    //将这个位置的懒信息执行下放
    private static void down(int treeIndex , int left , int right){

        int mid = getMid(left , right);
        //这个位置被刷成一样的了
        //如果这个位置被刷成一样了，那这个位置一定不糊有isReverse的信息
        if(isSame[treeIndex]){

            lazySame(whatNum[treeIndex], treeIndex << 1 , mid - left + 1);
            lazySame(whatNum[treeIndex],treeIndex << 1 | 1 , right - mid);
            isSame[treeIndex] = false;//这个位置的统一信息失效
            //这个位置之前被翻转过
        }
        if(isReverse[treeIndex]){

            lazyReverse(treeIndex << 1 , mid - left + 1);
            lazyReverse(treeIndex << 1 | 1 , right - mid);
            isReverse[treeIndex] = false;
        }
    }

    private static void up(int leftIndex , int rightIndex , int treeIndex){

        int l= treeIndex << 1;
        int r = treeIndex << 1 | 1;

        int mid = getMid(leftIndex , rightIndex);
        int ll = mid - leftIndex + 1;
        int lr = rightIndex - mid;

        num[treeIndex] = num[l] + num[r];

        getMaxPreAft(treeIndex , maxLen1 , preLen1 , aftLen1 , ll , lr);
        getMaxPreAft(treeIndex , maxLen0 , preLen0 , aftLen0 , ll , lr);
    }

    private static void getMaxPreAft(int treeIndex , int[] max , int[] pre , int[] aft , int leftLength , int rightLength){

        int l = treeIndex << 1;
        int r = treeIndex << 1 | 1;

        max[treeIndex] = Math.max(
                Math.max(max[l] , max[r]),
                aft[l] + pre[r]
        );

        pre[treeIndex] = pre[l] + (
                    pre[l] < leftLength ? 0 : pre[r]
                );

        aft[treeIndex] = aft[r] + (
                    aft[r] < rightLength ? 0 : aft[l]
                );
    }


    //将某个范围刷成一样的数字                            区间长度
    private static void lazySame(boolean isOne , int treeIndex , int rangeLength){

        isReverse[treeIndex] = false;//效果覆盖。之后是否翻转的信息就无效了
        isSame[treeIndex] = true;
        whatNum[treeIndex] = isOne;
        num[treeIndex] = isOne ? rangeLength : 0;

        maxLen1[treeIndex] = isOne ? rangeLength : 0;
        preLen1[treeIndex] = isOne ? rangeLength : 0;
        aftLen1[treeIndex] = isOne ? rangeLength : 0;

        maxLen0[treeIndex] = isOne ? 0 : rangeLength;
        preLen0[treeIndex] = isOne ? 0 : rangeLength;
        aftLen0[treeIndex] = isOne ? 0 : rangeLength;
    }

    //将这个地方进行翻转
    private static void lazyReverse(int treeIndex , int rangeLength){

        if(isSame[treeIndex])
            lazySame( !whatNum[treeIndex] , treeIndex , rangeLength);
        else{
            isReverse[treeIndex] = !isReverse[treeIndex];
            int temp;//完成交换
            temp = maxLen0[treeIndex]; maxLen0[treeIndex] = maxLen1[treeIndex];maxLen1[treeIndex] = temp;
            temp = preLen0[treeIndex]; preLen0[treeIndex] = preLen1[treeIndex];preLen1[treeIndex] = temp;
            temp = aftLen0[treeIndex]; aftLen0[treeIndex] = aftLen1[treeIndex];aftLen1[treeIndex] = temp;

            num[treeIndex] = rangeLength - num[treeIndex];
        }
    }

    public static void changeAllToOne(int left , int right){

        oneDfs(left , right ,
                1 ,length , 1);
    }

    private static void oneDfs(int left , int right,
                        int nowLeftIndex , int nowRightIndex , int treeIndex){
        if(left <= nowLeftIndex && nowRightIndex <= right){

            lazySame(true , treeIndex , nowRightIndex - nowLeftIndex + 1);
            return;
        }

        int mid = getMid(nowLeftIndex , nowRightIndex);
        down(treeIndex , nowLeftIndex , nowRightIndex);

        if(left <= mid)
            oneDfs(left , right,
                    nowLeftIndex , mid , treeIndex << 1);
        if(mid < right)
            oneDfs(left , right ,
                    mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        up(nowLeftIndex , nowRightIndex , treeIndex);
    }

    public static void reverse(int left , int right){

        reverseDfs(left , right,
                1 , length , 1);
    }

    private static void reverseDfs(int left , int right ,
                            int nowLeftIndex , int nowRightIndex , int treeIndex){

        if(left <= nowLeftIndex && nowRightIndex <= right){

            lazyReverse(treeIndex , nowRightIndex - nowLeftIndex + 1);
            return;
        }

        int mid = getMid(nowLeftIndex , nowRightIndex);
        down(treeIndex , nowLeftIndex , nowRightIndex);

        if(left <= mid)
            reverseDfs(left , right,
                    nowLeftIndex , mid , treeIndex << 1);

        if(mid < right)
            reverseDfs(left , right ,
                    mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        up(nowLeftIndex , nowRightIndex , treeIndex);
    }



    //的得到区间1的数量
    public static int getNum(int left , int right){

        return getNumDfs(left , right ,
                1 , length , 1);
    }

    private static int getNumDfs(int left , int right ,
                               int nowLeftIndex , int nowRightIndex , int treeIndex){

        if(left <= nowLeftIndex && nowRightIndex <= right)
            return num[treeIndex];

        int mid = getMid(nowLeftIndex , nowRightIndex);
        down(treeIndex , nowLeftIndex , nowRightIndex);

        int res = 0;
        if(left <= mid)
            res += getNumDfs(left , right ,
                    nowLeftIndex , mid , treeIndex << 1);

        if(mid < right)
            res += getNumDfs(left,  right,
                    mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        return res;
    }

    public static int getMaxContinuousOne(int left , int right){

        return continueDfs(left , right ,
                1 , length , 1)[1];
    }


    //            0     1      2     3
    //返回结果组成：pre , max  ,aft , len
    private static int[] continueDfs(int left , int right ,
                             int nowLeftIndex , int nowRightIndex , int treeIndex){

        //直接返回信息
        if(left <= nowLeftIndex && nowRightIndex <= right){

            return new int[]{preLen1[treeIndex] , maxLen1[treeIndex] , aftLen1[treeIndex] , nowRightIndex - nowLeftIndex + 1};
        }

        int mid = getMid(nowLeftIndex , nowRightIndex);
        down(treeIndex ,  nowLeftIndex , nowRightIndex);

        //需要的范围完全在左边
        if(right <= mid)
            return continueDfs(left , right ,
                    nowLeftIndex , mid , treeIndex << 1);

        //需要的范围完全在右边
        if(mid < left)
            return continueDfs(left , right,
                    mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        int[] leftRes = continueDfs(left , right,
                nowLeftIndex , mid , treeIndex << 1);

        int[] rightRes = continueDfs(left , right,
                mid + 1 , nowRightIndex , treeIndex << 1 | 1);

        int[] res = new int[4];

        res[1] = Math.max(
                Math.max(leftRes[1] , rightRes[1]),
                leftRes[2] + rightRes[0]
        );

        res[0] = leftRes[0];
        if(leftRes[0] == leftRes[3])
            res[0] += rightRes[0];


        res[2] = rightRes[2];
        if(rightRes[2] == rightRes[3])
            res[2] += leftRes[2];

        res[3] = nowRightIndex - nowLeftIndex + 1;

        return res;
    }


    public static int getMid(int left, int right){

        assert left <= right;
        return left + ((right - left) >> 1);
    }

    public  static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken();
        length = (int)in.nval;

        in.nextToken();
        int functionTimes = (int)in.nval;

        for(int i = 1 ; i <= length ; i++){

            in.nextToken();
            arr[i] = (int)in.nval == 1;
        }//完成数据输入

        init();


        for(int t = 0 ; t < functionTimes ; t++){

            in.nextToken();
            int functionType = (int)in.nval;

            in.nextToken();
            int l = (int)in.nval + 1;

            in.nextToken();
            int r = (int)in.nval + 1;

            if(functionType == 0){

                changeAllToZero(l ,  r);
            }else if(functionType == 1){

                changeAllToOne(l ,r);
            }else if(functionType == 2){

                reverse(l , r);
            }else if(functionType == 3){

                out.println(getNum(l , r));
            }else {

                out.println(getMaxContinuousOne( l ,r));
            }
        }


        out.flush();
        out.close();
    }
}
