package rsoukup.adventofcode.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12Part12 {
    public static void main(String[] args) throws Exception {
        Path filePath = Path.of("src/rsoukup/adventofcode/day12/input.txt");

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            int sumArrangements = 0;

            while ((line = reader.readLine()) != null) {
                // Part 1
                String[] parts = line.split(" ");
                String recordToCount = parts[0];
                List<Integer> groups = Arrays.asList(parts[1].split(",")).stream().map(p -> Integer.valueOf(p)).toList();

                // Part 2
                recordToCount = String.join(recordToCount, "?", recordToCount, "?", recordToCount, "?", recordToCount, "?", recordToCount);
                //groups.addAll(groups);
                groups = expandedGroups(groups);

                sumArrangements += countArrangements(recordToCount, groups);
            }

            System.out.println("Total of arrangements: " + sumArrangements);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countArrangements(String recordToCount, List<Integer> groups) {
        int arrangements = 0;

        if (recordToCount.isBlank()) { // recordToCount is completed (blank)
            // valid arrangement if groups is empty
            return groups.isEmpty() ? 1 : 0;
        }

        char firstChar = recordToCount.charAt(0);

        if (firstChar == '.') { // working status, skip and truncate recordToCount from left, not groups!
            arrangements = countArrangements(recordToCount.substring(1), groups);
        } else if (firstChar == '?') { // unknown status, we need to count it for both status (based on groups)
            arrangements = countArrangements("." + recordToCount.substring(1), groups)
                    + countArrangements("#" + recordToCount.substring(1), groups);
        } else { // damaged status
            if (groups.size() == 0) {
                // No more groups but damaged -> not valid
                arrangements = 0;
            } else {
                // get number of damaged springs from groups first element
                int numberOfDamagedSprings = groups.get(0);
                // Condition: if number of damaged springs is lower or equal to the length of record to count
                // and all springs from truncated recordToCount (by numberOfDamagedSprings as max size) are damaged or unknown
                if (numberOfDamagedSprings <= recordToCount.length() && recordToCount.chars().limit(numberOfDamagedSprings).allMatch(c -> c == '#' || c == '?')) {
                    // truncate groups from left to new list
                    List<Integer> truncatedGroups = groups.subList(1, groups.size());
                    if (numberOfDamagedSprings == recordToCount.length()) { // The remaining record length is equal to the number of damaged springs in the group
                        // just one arrangement if truncatedGroups is empty
                        arrangements = truncatedGroups.isEmpty() ? 1 : 0;
                    } else if (recordToCount.charAt(numberOfDamagedSprings) == '.') {
                        // The next spring is functional, so we will skip it
                        arrangements = countArrangements(recordToCount.substring(numberOfDamagedSprings + 1), truncatedGroups);
                    } else if (recordToCount.charAt(numberOfDamagedSprings) == '?') {
                        // The next spring can be functional only
                        arrangements = countArrangements("." + recordToCount.substring(numberOfDamagedSprings + 1), truncatedGroups);
                    } else {
                        // The next spring is also damaged -> not valid.
                        arrangements = 0;
                    }
                } else {
                    // Number of damaged springs is greater then the lenght of record to count
                    // or all springs in recordToCount are functional
                    arrangements = 0;
                }
            }
        }
        return arrangements;
    }

    private static List<Integer> expandedGroups(List<Integer> groups) {
        List<Integer> expandedGroups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expandedGroups.addAll(groups);
        }
        return expandedGroups;
    }
}
