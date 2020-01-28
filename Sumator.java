import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sumator implements SumatorInterface {

    private int lengthLine;

    private long countCorrect;
    private long countIncorrect;

    private String number1;
    private String number2;
    private String number3;
    private String str;

    private Pattern pattern = Pattern.compile("[a-zA-Z$&+:=?@#|'<>^*()%!]");
    private Matcher matcher;

    public static void main(String[] args) {
        Sumator sumator = new Sumator();
        sumator.run("");
    }

    @Override
    public void run(String file) {
        LocalTime timeAtTheBeginning = LocalTime.now();
        System.out.println(timeAtTheBeginning);
        try {
            long startTime = System.nanoTime();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                //validFile(lineSplit);      //METHOD FOR FILE VALIDATION, IF THE FILE CONTAINS UNEXPECTED DATA
                number1 = lineSplit[0];
                number2 = lineSplit[1];
                number3 = lineSplit[2];
                sumNumbers(number1, number2, number3);
            }
            br.close();

            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            double totalTimeInSecond = (double) totalTime / 1_000_000_000;
            System.out.println("-------------------------------");
            System.out.println(totalTimeInSecond + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println(countCorrect + " valid sums");
        System.out.println(countIncorrect + " invalid sums");
        System.out.println("-------------------------------");

        LocalTime timeAtTheEnd = LocalTime.now();
        System.out.println(timeAtTheEnd);
    }

    public void validFile(String[] line) {
        lengthLine = line.length;
        if (lengthLine != 3) {
            System.out.println("Invalid number of arguments in file");
            System.exit(0);
        }

        for (String s : line) {
            matcher = pattern.matcher(s);
            if (matcher.find()) {
                System.out.println("File should not contain letters or unexpected characters");
                System.exit(0);
            }
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

        str = "";
        int carry = 0;

        for (int i = 0; i < lengthNum1; i++) {
            int sum = ((num1.charAt(i) - '0') + (num2.charAt(i) - '0') + carry);
            str += (char) (sum % 10 + '0');

            carry = sum / 10;
        }

        for (int i = lengthNum1; i < lengthNum2; i++) {
            int sum = ((num2.charAt(i) - '0') + carry);
            str += (char) (sum % 10 + '0');
            carry = sum / 10;
        }

        if (carry > 0)
            str += (char) (carry + '0');

        str = new StringBuilder(str).reverse().toString();

        if (str.equals(num3)) {
            countCorrect++;
        } else {
            countIncorrect++;
        }
    }

}