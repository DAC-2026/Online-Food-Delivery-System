public class Main {

    public static int minSteps(String s) {

        int ones = 0;
        int steps = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '1') {
                ones++;
            } else { // ch == '0'
                steps += ones;
            }
        }

        return steps;
    }

    public static void main(String[] args) {
        String s = "01110110";
        System.out.println(minSteps(s)); // Output: 3
    }
}
