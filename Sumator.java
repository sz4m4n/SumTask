import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sumator implements SumatorInterface {

    private int countCorrect;

    private String number1;
    private String number2;
    private String number3;

    public static void main(String[] args) {
        Sumator sumator = new Sumator();
        sumator.run("");
    }

    @Override
    public void run(String file) {
        try {
            long counter = 0;
            long startTime = System.nanoTime();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                number1 = lineSplit[0];
                number2 = lineSplit[1];
                number3 = lineSplit[2];
                sumNumbers(number1, number2, number3);
                counter++;
            }
            br.close();

            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            double totalTimeInSecond = (double) totalTime / 1_000_000_000;

            System.out.print(" seconds " + totalTimeInSecond);
            System.out.print("       przetworzono " + counter);
            System.out.print("        poprawnie: " + countCorrect);

            System.out.println("-------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void sumNumbers(String num1, String num2, String num3) {
        if (num1.length() > num2.length()) {
            String t = num1;
            num1 = num2;
            num2 = t;
        }

        int lengthNum1 = num1.length(), lengthNum2 = num2.length();

        num1 = new StringBuilder(num1).reverse().toString();
        num2 = new StringBuilder(num2).reverse().toString();

        StringBuilder str = new StringBuilder();
        int carry = 0;

        for (int i = 0; i < lengthNum1; i++) {
            int sum = ((num1.charAt(i) - '0') + (num2.charAt(i) - '0') + carry);
            str.append((char) (sum % 10 + '0'));

            carry = sum / 10;
        }

        for (int i = lengthNum1; i < lengthNum2; i++) {
            int sum = ((num2.charAt(i) - '0') + carry);
            str.append((char) (sum % 10 + '0'));
            carry = sum / 10;
        }

        if (carry > 0)
            str.append((char) (carry + '0'));

        str = new StringBuilder(new StringBuilder(str.toString()).reverse().toString());

        if (str.toString().equals(num3)) {
            countCorrect++;
        }
    }

}