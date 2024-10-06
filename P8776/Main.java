package P8776;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int length = s.nextInt();
        int k = s.nextInt();

        int[] arr = new int[length];

        for(int i = 0 ; i < length ; i++) {

            arr[i] = s.nextInt();
        }//完成输入

        if(k > length) {

            System.out.println(0);
        }

        //这个数组代表在目前的情况下，k区间左边的长度为i的子序列最小的结尾是多大
        int[] pre = new int[length];
        //pre[0] = arr[0];//长度为1的子序列开头只有可能是arr[0[//最开始
        int maxLength = 0;//最长子序列长度

        int[] record = new int[length];

        //leftIndex 代表在刷成a的区间外的第一个数字的位置
        for(int leftIndex = -1,kIndex = k; kIndex < length ;leftIndex ++ , kIndex++) {
            int preLength;//小于等于当前a的最长子序列
            if(leftIndex == -1) {

                preLength = 0;//没有距离
            }else {

                int place = findBigger(pre, maxLength, arr[leftIndex]);//找到了大于这个数的第一个数的位置

                //不存在比这个数小或者相等的位置
                if (place == -1) {

                    //不存在大于这个数的情况
                    pre[maxLength++] = arr[leftIndex];
                }else{

                    pre[place] = Math.min(pre[place], arr[leftIndex]);
                }

                preLength = findBigger(pre, maxLength, arr[kIndex]);

                if (preLength == -1) {

                    preLength = maxLength;
                }
            }
            record[kIndex] = preLength + (k + 1);//当将kIndex位置前k个数翻转能够得到的最长子序列
        }

        //检查k区间后面的部分中，大于等于a的最长子序列
        int[] after = new int[length];
        int afterLength = 0;

        int max = 0;

        //枚举每个位置
        //理论上after应该是单调递减的
        reverse(arr);//翻转arr
        //以下要求数组情况：必须单调递减或者相等（找到大于等于a的最右边（找到第一个小于的数
        for(int kIndex = length -1 ; kIndex >= k ; kIndex--) {

            int correctIndex = length - kIndex - 2;//翻转后数组的下标

            int position;

            if(correctIndex == -1){

                position = 0;
            }else{

                position = findSmaller(after, afterLength, arr[correctIndex]);


                //没有小的
                if(position == -1) {

                    after[afterLength++] = arr[correctIndex];
                }else{

                    after[position] = Math.max(after[position], arr[correctIndex]);
                }

                position = findSmaller(after, afterLength, arr[correctIndex+1]);

                if(position == -1) {
                    position = afterLength;
                }
            }


            max = Math.max(max, record[kIndex] + position);
        }


        System.out.println(max);
        s.close();
    }

    private static void reverse(int[] arr){

        for(int i = 0 , j = arr.length - 1 ; i < j ; i++ , j--) {

            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static int findSmaller(int[] arr , int length , int findNum){

        int left = 0, right = length - 1,ans = -1;

        while(left <= right) {

            int mid = left + ((right - left) >> 1);

            if(arr[mid] < findNum) {

                ans = mid;
                right = mid - 1;
            }else{

                left = mid + 1;
            }
        }

        return ans;
    }

    //小于等于的最右边
    //相当于找到第一个大于这个数的位置
    private static int findBigger(int[] arr , int length , int findNum){

        int left = 0, right = length - 1,ans = -1,equal;

        while(left <= right) {

            int mid = left + ((right - left) >> 1);

            if(arr[mid] > findNum) {

                ans = mid;
                right = mid - 1;
            }else{

                left = mid + 1;
            }
        }

        return ans;
    }
}
