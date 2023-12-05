package aoc.year_2023.day4;

import aoc.util.DataProcessor;

import java.util.Arrays;
import java.util.List;

public class AoC_23_Day4 {

//    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53

    public int aoc_23_4_part1(String resourcePath) {
        List<String> input = DataProcessor.readTextFile(resourcePath);
        int[] result = {0};
        input.forEach(card -> result[0] += calculateCurrentCardPoints(card));
        return result[0];
    }

    public int calculateCurrentCardPoints(String cardInput){
        Card card = createCardFromInputText(cardInput);
        // workaround for -> Variable used in lambda expression should be final or effectively final
        int[] currCardPoints = {0};
        card.winNums.forEach(n -> {
                            if (card.yourNums.contains(n)) {
                                currCardPoints[0]++;
                            }
                        }
                );
        return (int) (Math.pow(2.0, (double)currCardPoints[0]-1));
    }


    public Card createCardFromInputText(String cardInput) {
        String[] strArr = cardInput.split(":|\\|");
        int id = Integer.parseInt(strArr[0].split("\\s+")[1]);
        List<Integer> winNums = convertToIntArray(strArr[1].split("\\s"));
        List<Integer> yourNums = convertToIntArray(strArr[2].split("\\s"));
        return new Card(id, winNums, yourNums);
    }

    public List<Integer> convertToIntArray(String[] strArr) {
        return Arrays.stream(strArr)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }

    static class Card {
        int id;
        List<Integer> winNums;
        List<Integer> yourNums;
        int points;

        Card(int id, List<Integer> winNums, List<Integer> yourNums) {
            this.id = id;
            this.winNums = winNums;
            this.yourNums = yourNums;
            points = 0;
        }

    }
}
