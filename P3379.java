package fron_3000_to_3500;

import java.io.*;
import java.util.Arrays;

public class P3379 {

    /**
     * 另外的思路见tarjan算法
     */

    /**
     * 总体思路：
     * 1.由于题目给定的方式是哪两个节点之间有边，因此需要先将图构建出来，再深度优先遍历得到st表
     * 不能直接像给定parent-son关系的方法构建
     *  由于题目数据量较大，使用递归的话会爆栈，所以需要将栈手动实现或者使用循环代替
     * 2.当给定A，B后，将A，B中更低的移动到和B相同层级，然后再使用ST倍增的方式确定祖先
     */

    public static int MAXN = 500001;
    public static int[] nodeFirstEdge = new int[MAXN];
    public static int[] edgeNextEdge = new int[MAXN << 1];//由于是无向图，所以每个边对应两个实际存储的边。同时用于是树，所以m = n -1 ，因此只需要开两倍空间即可
    public static int[] edgeTo = new int[MAXN << 1];

    public static int realNum;
    public static int root;

    public static int[][] ST = new int[MAXN][getMaxPower(MAXN) + 1];
    public static int maxWalk;

    private static int getMaxPower(int n){

        int i = 0;

        while((1 << i) <= (n >> 1))
            i++;

        return i;
    }

    public static void main(String[] args) throws IOException {

        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken();
        realNum = (int)in.nval;
        maxWalk = getMaxPower(realNum) + 1;

        in.nextToken();
        int functionTime = (int)in.nval;

        in.nextToken();
        root = (int)in.nval;


        int cnt = 1;
        Arrays.fill(nodeFirstEdge , 0);
        for(int i = 0 ; i < realNum - 1 ; i++){//m = n - 1

            in.nextToken();
            int from = (int)in.nval;

            in.nextToken();
            int to = (int)in.nval;

            int pre = nodeFirstEdge[from];
            nodeFirstEdge[from] = cnt;
            edgeNextEdge[cnt] = pre;
            edgeTo[cnt] = to;

            cnt++;

            pre = nodeFirstEdge[to];
            nodeFirstEdge[to] = cnt;
            edgeNextEdge[cnt] = pre;
            edgeTo[cnt] = from;

            cnt++;
        }//完成图的构建


        //开始深度优先遍历
        Arrays.fill(visit , 0 , realNum , false);
        ST[root][0] = -1;
        //buildST(root , 0);====================================================
        buildST2(root);

        //开始填表
        for(int walk = 1; walk < maxWalk ; walk ++){

            for(int i = 1 ; i <= realNum ; i++){

                int next = ST[i][walk - 1];
                ST[i][walk] = next == - 1 ? -1 : ST[next][walk -1];
            }
        }//完成填表

        //开始处理每个请求
        for(int f = 0 ; f < functionTime ; f++){

            in.nextToken();
            int a = (int)in.nval;
            in.nextToken();
            int b = (int)in.nval;
            if(deep[a] > deep[b]){

                int temp = a;
                a = b;
                b = temp;
            }//保证a一定小于b

            //将a，b高度变得一致
            int w = deep[b] - deep[a];
            while(w > 0){

                int W = getMaxPower(w);
                w -= (1 << W);
                b = ST[b][W];
            }

            if(a == b){

                out.println(a);
                continue;
            }

            //开始一起向上遍历
            int thisMaxWalk = getMaxPower(deep[a]);//得到当前深度的判断步数

            for(int walk = thisMaxWalk ; walk >= 0 ;walk --){

                //如果走这么多步还是不相等，就可以直接走，否则不能走，需要考虑走更小的步骤
                if(ST[a][walk] != ST[b][walk]){

                    a = ST[a][walk];
                    b = ST[b][walk];
                }
            }

            //为什么还要前进一步：因为在上面的判断中，ab只有在不相等的时候才迈步，所以最后两个一定还是不相等，且最后只差了一格
            out.println(ST[a][0]);
        }

        out.flush();
        out.close();
    }


    public static boolean[] visit = new boolean[MAXN];//用于在深度优先遍历的时候记录之前到过的点，防止由于无向边造成的环路
    public static int[] deep = new int[MAXN];
    private static void buildST(int node , int depth){
        visit[node] = true;
        deep[node] = depth;

        int edgeId = nodeFirstEdge[node];

        while(edgeId != 0){

            int to = edgeTo[edgeId];

            if(!visit[to]){
                //构建从to到from的反向边
                ST[to][0] = node;
                buildST(to , depth + 1);
            }


            edgeId = edgeNextEdge[edgeId];//向下遍历
        }
    }

    //以下将递归改成循环
    //相当于通过手动压栈的方法实现递归
    public static int[] nodeStack = new int[MAXN];
    public static int nodeStackSize;

    public static int[] deepStack = new int[MAXN];
    public static int deepStackSize;

    public static int[] edgeStack = new int[MAXN];
    public static int edgeStackSize;

    private static void buildST2(int root){

        //初始化
        nodeStackSize = edgeStackSize = deepStackSize = 0;
        int depth = 0;
        int edgeId = nodeFirstEdge[root];

        //保留栈帧
        nodeStack[nodeStackSize++] = root;
        deepStack[deepStackSize++] = depth;
        edgeStack[edgeStackSize++] = edgeId;

        while(nodeStackSize != 0){

            boolean key = true;//当递归执行完了要进行删除

            int cur = nodeStack[nodeStackSize - 1];
            depth = deepStack[deepStackSize - 1];
            edgeId = edgeStack[edgeStackSize - 1];//取出

            visit[cur] = true;
            deep[cur] = depth;

            while(edgeId != 0){

                int to = edgeTo[edgeId];

                if(!visit[to]){
                    //构建从to到from的反向边
                    ST[to][0] = cur;

//                    buildST(to , depth + 1);
                    edgeStack[edgeStackSize - 1] = edgeId;//保存当前edgeId
                    //保存栈帧，执行递归
                    nodeStack[nodeStackSize++] = to;
                    deepStack[deepStackSize++] = depth + 1;
                    edgeStack[edgeStackSize++] = nodeFirstEdge[to];
                    key = false;
                    break;
                }


                edgeId = edgeNextEdge[edgeId];//向下遍历
            }


            if(key){//这个的edgeId已经是0了，可以将这个点删除

                nodeStackSize --;
                deepStackSize --;
                edgeStackSize --;
            }
        }


    }

}
