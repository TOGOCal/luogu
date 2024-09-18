package P1002;

//import java.util.HashSet;
import java.util.Scanner;

public class Main {

    /**
     * 棋盘上
     *
     * A 点有一个过河卒，需要走到目标
     *
     * B 点。卒行走的规则：可以向下、或者向右。同时在棋盘上
     *
     * C 点有一个对方的马，该马所在的点和所有跳跃一步可达的点称为对方马的控制点。因此称之为“马拦过河卒”。
     *
     * 棋盘用坐标表示，
     * A 点(0,0)、
     * B 点(n,m)
     * (n,m)，同样马的位置坐标是需要给出的。
     *
     * 现在要求你计算出卒从
     * A 点能够到达 B 点的路径的条数，假设马的位置是固定不动的，并不是卒走一步马走一步。
     * 输入格式
     * 一行四个正整数，分别表示
     * B 点坐标和马的坐标。
     * 输出格式
     * 一个整数，表示所有的路径条数。
     */

    //static HashSet<String> canNotGo;
    static long[][] dp;

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        //int[] input = new int[4];

        int Bx = s.nextInt();
        int By = s.nextInt();

//        Node B = new Node(Bx , By);

        int Hx = s.nextInt();
        int Hy = s.nextInt();

//        Node H = new Node(Hx , Hy);
//
//        canNotGo = new HashSet<>();
//
//        canNotGo.add(new Node(Hx -2 , Hy -1).toString());
//        canNotGo.add(new Node(Hx -1 , Hy -2).toString());
//        canNotGo.add(new Node(Hx -2 , Hy +1).toString());
//        canNotGo.add(new Node(Hx -1 , Hy +2).toString());
//        canNotGo.add(new Node(Hx +2 , Hy -1).toString());
//        canNotGo.add(new Node(Hx +1 , Hy -2).toString());
//        canNotGo.add(new Node(Hx +2 , Hy +1).toString());
//        canNotGo.add(new Node(Hx +1 , Hy +2).toString());
//
//        canNotGo.add(H.toString());


        dp = new long[Bx+1][By+1];//能够访问到这Bx和By
        //填充dp为-1
        for(int i = 0 ; i <= Bx ; i ++){
            for(int j = 0 ; j <= By ; j ++)
                dp[i][j] = -1;
        }

//        //依赖项：x+1和y+1，所以只需要填充下面和右边就可以了
//        for(int i = 0 ; i <= Bx ; i ++){
//
//            dp[i][By] = 1;//只能向右走
//        }
//
//        for(int i = 0 ; i<= By ; i ++){
//
//            dp[Bx][i] = 1;
//        }
//


//        if(Hx -2 >= 0 &&  Hy -1 >= 0)
//            dp[Hx-2][Hy-1] = 0;
//        if(Hx -1 >= 0 && Hy -2 >= 0)
//            dp[Hx-1][Hy-2] = 0;
//        if(Hx -2 >= 0 && Hy +1 <= By)
//            dp[Hx-2][Hy+1] = 0;
//        if(Hx -1 >= 0 && Hy +2 <= By)
//            dp[Hx-1][Hy+2] = 0;
//        if(Hx +2 <= Bx && Hy -1 >= 0)
//            dp[Hx+2][Hy-1] = 0;
//        if(Hx +1 <= Bx && Hy -2 >= 0)
//            dp[Hx+1][Hy-2] = 0;
//        if(Hx +2 <= Bx && Hy +1 <= By)
//            dp[Hx + 2][Hy +1] = 0;
//        if(Hx +1 <= Bx && Hy +2 <= By)
//            dp[Hx + 1][Hy + 2] = 0;


        try {
            dp[Hx-2][Hy-1] = 0;
        } catch (Exception ignored) {

        }


        try {
            dp[Hx-1][Hy-2] = 0;
        } catch (Exception ignored) {

        }
        try {
            dp[Hx-2][Hy+1] = 0;
        } catch (Exception ignored) {

        }
        try {
            dp[Hx-1][Hy+2] = 0;
        } catch (Exception ignored) {

        }
        try {
            dp[Hx+2][Hy-1] = 0;
        } catch (Exception ignored) {

        }
        try {
            dp[Hx+1][Hy-2] = 0;
        } catch (Exception ignored) {

        }
        try {
            dp[Hx + 2][Hy +1] = 0;
        } catch (Exception ignored) {

        }
        try {
            dp[Hx + 1][Hy + 2] = 0;
        } catch (Exception ignored) {

        }


        try {
            dp[Hx][Hy] = 0;
        } catch (Exception ignored) {

        }

        dp[Bx][By] = 1;

        for(int i = Bx-1 ; i >= 0 ; i--){

            if(dp[i][By] == -1){
                dp[i][By] = dp[i + 1][By];
            }
        }

        for(int i = By-1 ; i >= 0; i --){

            if(dp[Bx][i] == -1){
                dp[Bx][i] = dp[Bx][i+1];
            }
        }

        for(int i = Bx -1 ; i >= 0 ; i--){

            for(int j = By -1 ; j >= 0; j --){

                if(dp[i][j] == -1){

                    dp[i][j] = dp[i][j+1] + dp[i+1][j];
                }
            }
        }



        //System.out.println(dfs(new Node(0 , 0) , B));
        System.out.println(dp[0][0]);

        s.close();
    }

//    static int dfs(Node nowNode , Node position){
//
//        if(canNotGo.contains(nowNode.toString())){
//
//            return 0;
//        }
//
//        if(nowNode.x == position.x && nowNode.y == position.y){
//
//            return 1;//这是一种合理的走法
//        }
//
//        int sum = 0;
//        if(nowNode.x < position.x){
//
//            nowNode.x += 1;
//            sum += dfs(nowNode , position);
//            nowNode.x -= 1;
//        }
//
//        if(nowNode.y < position.y){
//
//            nowNode.y += 1;
//            sum += dfs(nowNode , position);
//            nowNode.y -= 1;
//        }
//
//        return sum;
//    }
//
//
//    static class Node{
//
//        int x ,y;
//
//        Node(int x , int y){
//
//            this.x = x;
//            this.y = y;
//        }
//
//        public String toString(){
//
//            return x + "|" + y;
//        }
//    }
}
