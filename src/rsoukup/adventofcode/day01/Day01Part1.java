package rsoukup.adventofcode.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day01Part1 {
    public static void main(String[] args) throws Exception {
        Path filePath = Path.of("src/rsoukup/adventofcode/day01/input.txt");

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            int sum = 0;

            while ((line = reader.readLine()) != null) {
                //replace lettered numbers with digital number
                line = replaceLetteredNumbers(line);
                //replace all non-digit chars
                String digitsFromString = line.replaceAll("[^0-9]", "");

                char[] charArray = digitsFromString.toCharArray();
                String firstAndLast = "" + charArray[0] + charArray[charArray.length - 1];
                sum += Integer.parseInt(firstAndLast);
            }

            System.out.println("Summary: " + sum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Day 1 Part 2 */
    private static String replaceLetteredNumbers(String line) {
        String[] searchList = {"oneight", "twone", "eightwo", "threeight", "nineight", "fiveight", "eighthree", "sevenine", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] replacementList = {"18", "21", "82", "38", "98", "58", "83", "79", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        
        for (int i = 0; i < searchList.length; i++) {
            line = line.replaceAll(searchList[i], replacementList[i]);
        }

        return line;
    }
}
