package aoc.year_2023.day3;

import aoc.util.DataProcessor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AoC_23_Day3 {

    public int aoc_23_3_part1(String resourcePath) {
        List<String> input = DataProcessor.readTextFile(resourcePath);
        char[][] matrix = convertInputToCharMatrix(input);
        Map<Integer, List<EnginePart>> rowEnginePartsMap = rowEnginePartMap(input);
        int result = 0;
        Map<Integer, List<EnginePart>> ultimateResultMap = new HashMap<>();

        for (int n = 0; n < matrix.length; n++) {
            for (int m = 0; m < matrix[n].length; m++) {
                char ch = matrix[n][m];

                if (!Character.isDigit(ch) && ch != '.') {
                    Map<Integer, List<EnginePart>> resultMap = scanAround(matrix, n, m, rowEnginePartsMap);

                    for (Map.Entry<Integer, List<EnginePart>> entry : resultMap.entrySet()) {
                        int key = entry.getKey();

                        if (ultimateResultMap.containsKey(key)) {
                            List<EnginePart> epList = entry.getValue();
                            epList.forEach(e -> {
                                List<EnginePart> ultimateEngineParts = ultimateResultMap.get(key);
                                boolean containsRangeStart = false;
                                for (EnginePart epElement : ultimateEngineParts) {
                                    if (epElement.range[0] == e.range[0]) {
                                        containsRangeStart = true;
                                        break;
                                    }
                                }
                                if (!containsRangeStart) ultimateEngineParts.add(e);
                            });
                        } else {
                            ultimateResultMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
        }
        for (Map.Entry<Integer, List<EnginePart>> entry : ultimateResultMap.entrySet()) {
                for (EnginePart ep : entry.getValue()) {
                    result += ep.number;
                }
        }
        return result;
    }

    public int aoc_23_3_part2(String resourcePath) {
        List<String> input = DataProcessor.readTextFile(resourcePath);
        char[][] matrix = convertInputToCharMatrix(input);
        Map<Integer, List<EnginePart>> rowEnginePartsMap = rowEnginePartMap(input);
        int result = 0, n1, n2;
        Map<Integer, List<EnginePart>> ultimateResultMap = new HashMap<>();

        for (int n = 0; n < matrix.length; n++) {

            for (int m = 0; m < matrix[n].length; m++) {
                char ch = matrix[n][m];

                if (ch == '*') {
                    Map<Integer, List<EnginePart>> resultMap = scanAround(matrix, n, m, rowEnginePartsMap);
                    int numberOfEnginePart = 0;
                    for (Map.Entry<Integer, List<EnginePart>> entry : resultMap.entrySet()) {
                        for (EnginePart enginePart : entry.getValue()) {
                            numberOfEnginePart++;
                        }
                    }
                    if (numberOfEnginePart != 2) {
                        continue;
                    }
                    // resultMap vs ultimateMap
                    for (Map.Entry<Integer, List<EnginePart>> entry : resultMap.entrySet()) {
                        int key = entry.getKey();

                        if (ultimateResultMap.containsKey(key)) {
                            List<EnginePart> epList = entry.getValue();

                            Map<Integer, List<EnginePart>> finalUltimateResultMap = ultimateResultMap;
                            epList.forEach(e -> {
                                List<EnginePart> ultimateEngineParts = finalUltimateResultMap.get(key);
                                boolean containsRangeStart = false;

                                for (EnginePart epElement : ultimateEngineParts) {
                                    if (epElement.range[0] == e.range[0]) {
                                        containsRangeStart = true;
                                        break;
                                    }
                                }
                                if (!containsRangeStart) ultimateEngineParts.add(e);
                            });
                        } else {
                            ultimateResultMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    List<EnginePart> enginePartGroup = new ArrayList<>();
                    for (List<EnginePart> enginePartList : ultimateResultMap.values()) {
                        enginePartGroup.addAll(enginePartList);
                    }
                    ultimateResultMap = new HashMap<>();
                    n1 = enginePartGroup.get(0).number;
                    n2 = enginePartGroup.get(1).number;
                    int n3 = n1*n2;
                    result += n3;
                }
            }
        }
        return result;

    }

    public Map<Integer, List<EnginePart>> scanAround(char[][] matrix, int n, int m, Map<Integer, List<EnginePart>> rowEnginePartsMap) {
        String pattern = "LURRDDLL";
        Map<Integer, List<EnginePart>> resultMap = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char patternChar = pattern.charAt(i);
            switch (patternChar) {
                case 'L' -> {
                    m--;
                    manageResultMap(n, resultMap, findNumber(matrix, rowEnginePartsMap, n, m));
                }
                case 'U' -> {
                    n--;
                    manageResultMap(n, resultMap, findNumber(matrix, rowEnginePartsMap, n, m));
                }
                case 'R' -> {
                    m++;
                    manageResultMap(n, resultMap, findNumber(matrix, rowEnginePartsMap, n, m));
                }
                case 'D' -> {
                    n++;
                    manageResultMap(n, resultMap, findNumber(matrix, rowEnginePartsMap, n, m));
                }
            }
        }
        return resultMap;
    }

    private static void manageResultMap(int n, Map<Integer, List<EnginePart>> resultMap, EnginePart ep) {
        if (ep != null) {
            if (resultMap.containsKey(n)) {
                List<EnginePart> list = resultMap.get(n);
                for (EnginePart e : list) {
                    // to avoid counting the same number multiple times
                    if (e.range[0] == ep.range[0]) return;
                }
                resultMap.get(n).add(ep);
            } else {
                resultMap.put(n, new ArrayList<>(List.of(ep)));
            }
        }
    }

    public EnginePart findNumber(char[][] matrix, Map<Integer, List<EnginePart>> rowEnginePartsMap, int n, int m) {
        char matrixCurrChar = matrix[n][m];
        if(Character.isDigit(matrixCurrChar)) {
            List<EnginePart> engineParts = rowEnginePartsMap.get(n);
            for (EnginePart part : engineParts) {
                if (m >= part.range[0] && m <= part.range[1]) {
                    return part;
                }
            }
        }
        return null;
    }

    static class EnginePart {
        int number;
        int[] range;

        public EnginePart() {
            number = 0;
            range = new int[2];
        }
    }

    public Map<Integer, List<EnginePart>> rowEnginePartMap(List<String> input) {
        Map<Integer, List<EnginePart>> rowEnginePartsMap = new HashMap<>();
        List<EnginePart> enginePartsInRow = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");

        for (int i = 0; i < input.size(); i++) {
            Matcher matcher = pattern.matcher(input.get(i));
            int fromIndex = 0;
            while (matcher.find()) {
                EnginePart enginePart = new EnginePart();
                String numberInStr = matcher.group();
                enginePart.number = Integer.parseInt(matcher.group());
                int start = input.get(i).indexOf(numberInStr, fromIndex);
                int end = start + numberInStr.length() - 1;
                fromIndex = end;
                enginePart.range[0] = start;
                enginePart.range[1] = end;
                enginePartsInRow.add(enginePart);
            }
            if (!enginePartsInRow.isEmpty()) {
                rowEnginePartsMap.put(i, enginePartsInRow);
            }
            enginePartsInRow = new ArrayList<>();
        }
        return rowEnginePartsMap;
    }

    public char[][] convertInputToCharMatrix(List<String> inputs) {
        int n = inputs.size();
        int m = inputs.get(0).length();

        char[][] matrix = new char[n][m];
        n = -1;
        m = -1;

        for (String str : inputs) {
            n++;
            for (char ch : str.toCharArray()) {
                m++;
                matrix[n][m] = ch;
            }
            m = -1;
        }
        return matrix;
    }
}