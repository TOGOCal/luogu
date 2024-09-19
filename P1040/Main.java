package P1040;

import java.util.Scanner;

public class Main {

    static Node[] treeNodes;


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);


        int n =s.nextInt();
        int[] arr = new int[n];

        treeNodes = new Node[n];

        for(int i = 0; i < n; i ++){

            arr[i] = s.nextInt();
            treeNodes[i] = new Node(i);
        }


        //决定哪个节点是根节点
        resultMap = new Result[n][n];
        Result res = dfs(arr , 0 , n-1);
        System.out.println(res.count);

        Node root = treeNodes[res.position];


//        while(now != null){
//
//            if(now.left == null){
//
//                System.out.print(now.index);
//                System.out.print(" ");
//                now = now.right;
//            }else{
//
//                Node mostRight = now.left;
//
//                while(mostRight.right != null && mostRight.right != now){
//
//                    mostRight = mostRight.right;
//                }//找到了左树的最右节点
//
//                //第一次来到这个节点
//                if(mostRight.right == null){
//
//                    System.out.print(now.index);
//                    System.out.print(" ");
//
//                    mostRight.right = now;
//                    now = now.left;
//                }else{
//
//                    mostRight.right = null;
//                    now = now.right;
//                }
//            }
//        }

        printTree(root  , 0 , n-1 , arr);

        s.close();
    }


    static void printTree(Node nowNode , int left , int right , int[] tree){

        //先序遍历
        System.out.print(nowNode.index + 1);
        System.out.print(" ");

        try {
            printTree(treeNodes[ dfs(tree , left , nowNode.index-1).position ] , left , nowNode.index-1 , tree);
        } catch (Exception ignored) {

        }

        try {
            printTree(treeNodes[ dfs(tree , nowNode.index + 1 , right).position ] , nowNode.index + 1 , right , tree);
        } catch (Exception ignored) {

        }
    }


    static class Node{

        int index;

        Node(int index){

            this.index = index;
        }

    }

    static class Result {

        int position;

        int count;


        Result(int position , int count){

            this.position = position;
            this.count = count;
        }

    }


    static Result[][] resultMap;
    static Result empty = new Result(-1 , 1);

    public static Result dfs(int[] tree , int left , int right){



        //空树
        if(left > right){

            return empty;
        }

        if(resultMap[left][right] != null){

            return resultMap[left][right];
        }

        //只有一个节点
        if(left == right){

            Result r = new Result(left , tree[left]);

            resultMap[left][right] = r;

            return r;
        }




        int max = Integer.MIN_VALUE;
        int position = -1;

        //i为根节点的位置
        for(int i = left ; i <= right ; i++){

            Result leftCount = dfs(tree , left , i-1);
            Result rightCount = dfs(tree , i + 1 , right);

            int count = leftCount.count * rightCount.count + tree[i];

            if(count > max){

                max = count;
                position = i;

            }
        }


        Result r = new Result(position , max);

        resultMap[left][right] = r;

        return r;
    }

}
