package P1009;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append('1');
        sb1.append('0');

        for (int i = 1; i <= n; i++) {

            sb2 = 乘法(i, sb2);
            sb1 = plus(sb1, sb2);

            int num = sb1.length() - 1;
            int num_ = sb2.length() - 1;
            for (int ii = num; ii >= 0; ii--) {

                if (sb1.charAt(ii) == '0') {

                    sb1.deleteCharAt(ii);
                } else {
                    break;
                }

            }

            for (int ii = num_; ii >= 0; ii--) {

                if (sb2.charAt(ii) == '0') {

                    sb2.deleteCharAt(ii);
                } else {
                    break;
                }

            }
        }

        sb1.reverse();
        System.out.println(sb1);

        s.close();
    }

    public static StringBuilder plus(StringBuilder sb1, StringBuilder sb2) {

        StringBuilder sb = new StringBuilder();

        if (sb1.length() > sb2.length()) {

            for (int i = 0; i < (sb1.length() - sb2.length()); i++) {
                sb2.append('0');
            }
        }

        else if (sb1.length() < sb2.length()) {

            for (int i = 0; i < (sb2.length() - sb1.length()); i++) {
                sb1.append('0');
            }
        }

        boolean key = false;
        for (int i = 0; i < sb1.length(); i++) {

            int a = (sb1.charAt(i) - '0') + (sb2.charAt(i) - '0');
            if (key) {

                a++;
                key = false;
            }

            if (a >= 10) {
                a -= 10;
                key = true;
            }
            char add = (char) (a + (int) '0');

            sb.append(add);
        }

        if (key) {
            sb.append('1');// 进位
        }

        return sb;
    }

    public static StringBuilder 乘法(int x, StringBuilder sb1) {

        int n = sb1.length();
        int arr[] = new int[x * n];
        for (int i = 0; i < x * n; i++) {
            arr[i] = 0;
        }

        StringBuilder answers = new StringBuilder();

        int 进位 = 0;
        for (int i = 0; i < sb1.length(); i++) {

            arr[i] = (sb1.charAt(i) - '0') * x;
        }

        for (int i = 0; i < arr.length; i++) {

            arr[i] += 进位;
            进位 = 0;
            while (arr[i] >= 10) {
                arr[i] -= 10;
                进位++;
            }

            if (check(i, arr)) {
                break;
            }
            answers.append(arr[i]);
        }
        return answers;
    }

    public static boolean check(int n, int arr[]) {

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] != 0) {
                return false;
            }
        }
        return true;// 剩下的都是0
    }

}

/**
 * 使用倒着储存表示一个整数
 */