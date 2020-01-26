import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sumator implements SumatorInterface {

    private int length;

    private long countCorrect;
    private long countIncorrect;

    private BigDecimal number1;
    private BigDecimal number2;
    private BigDecimal number3;
    private BigDecimal checkSum;

    private String num1;
    private String num2;
    private String num3;

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
                num1 = lineSplit[0];
                num2 = lineSplit[1];
                num3 = lineSplit[2];
                sumNumbers(num1, num2, num3);
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
        length = line.length;
        if (length != 3) {
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
    public void sumNumbers(String n1, String n2, String n3) {
        number1 = new BigDecimal(n1);
        number2 = new BigDecimal(n2);
        number3 = new BigDecimal(n3);

        checkSum = number1.add(number2);
        if (checkSum.equals(number3)) {
            countCorrect++;
        } else {
            countIncorrect++;
        }
    }
}