public class Main {
    public static int multiplyOrder(int[] p) {
        int n = p.length - 1;
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i][i] = 0;
        }

        for (int l = 2; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    dp[i][j] = Math.min(dp[i][j],
                            dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j]);
                }
            }
        }
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < n + 1; j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int[] test = {1,2,3,1,5,2};
        System.out.println(Main.multiplyOrder(test));
    }
}
