import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MaxSum {
    public int solution(int[] A) {
        Map<String, ArrayList<Integer>> map = new HashMap<>();
        for (int j : A) {
            String numStr = String.valueOf(j);
            String key = numStr.charAt(0) + "" + numStr.charAt(numStr.length() - 1);
            map.computeIfAbsent(key, k -> new ArrayList<>());
            map.get(key).add(j);
        }
        int maxSum = -1;
        for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() >= 2) {
                ArrayList<Integer> list = entry.getValue();
                list.sort(Collections.reverseOrder());
                maxSum = Math.max(maxSum, list.get(0) + list.get(1));
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        MaxSum maxSum = new MaxSum();
        System.out.println(maxSum.solution(new int[]{130, 191, 200, 10})); // 140
        System.out.println(maxSum.solution(new int[]{405, 45, 300, 300})); // 600
        System.out.println(maxSum.solution(new int[]{50, 222, 49, 52, 25})); // -1
        System.out.println(maxSum.solution(new int[]{30, 909, 3190, 99, 3990, 9009})); // 9918
        System.out.println(maxSum.solution(new int[]{30, 909, 3190, 99, 3990, 9009, 9009})); // 18018
        System.out.println(maxSum.solution(new int[]{10, 10, 110, 1990})); // 2100
        System.out.println(maxSum.solution(new int[]{10, 10})); // 2100
        System.out.println(maxSum.solution(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE})); // -1 > -2
    }
}


