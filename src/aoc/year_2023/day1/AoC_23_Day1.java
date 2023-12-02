package aoc.year_2023.day1;

import aoc.util.DataProcessor;

import java.util.*;

public class AoC_23_Day1 {

    public int aoc_23_1(String resourcePath, int part) {
        List<String> inputs = DataProcessor.readTextFile(resourcePath);
        int sumOfDigits = 0, currentDigit;
        for (String s : inputs) {
            if (part == 1) {
                List<Integer> digits = new ArrayList<>();
                for (char ch : s.toCharArray()) {
                    if (Character.isDigit(ch)) {
                        digits.add(Character.getNumericValue(ch));
                    }
                }
                currentDigit = digits.get(0);
                currentDigit = currentDigit * 10 + digits.get(digits.size() - 1);
                sumOfDigits += currentDigit;
            } else {
                Map<Integer, Integer> indexDigitMap = new HashMap<>();
                int first, last;

                // manage spelled digits
                for (String spelledDigit : spelledDigitsList()) {
                    if (!spelledDigit.isEmpty() && s.contains(spelledDigit)) {
                        // get all indexes occurrences from string
                        int spelledLength = spelledDigit.length();
                        for (int i = 0; i <= s.length() - spelledLength; i++) {
                            if (spelledDigit.equals(s.substring(i, i + spelledLength))) {
                                indexDigitMap.put(i, spelledDigitsList().indexOf(spelledDigit));
                                i += spelledLength;
                            }
                        }
                    }
                }

                // manage normal digits
                for (int i = 0; i < s.length(); i++) {
                    char currCh = s.charAt(i);
                    if (Character.isDigit(currCh)) {
                        indexDigitMap.put(i, Character.getNumericValue(currCh));
                    }
                }

                List<Integer> sortedKeyList = sortedKeyList(indexDigitMap);
                first = sortedKeyList.get(0);
                last = sortedKeyList.get(sortedKeyList.size() - 1);

                currentDigit = indexDigitMap.get(first);
                currentDigit = currentDigit * 10 + indexDigitMap.get(last);
                sumOfDigits += currentDigit;
                indexDigitMap.clear();
                }
            }
        return sumOfDigits;
    }

    public List<Integer> sortedKeyList(Map<Integer, Integer> map) {
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            list.add(entry.getKey());
        }
        Collections.sort(list);
        return list;
    }

    public List<String> spelledDigitsList() {
        return List.of(
                "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        );
    }
}
