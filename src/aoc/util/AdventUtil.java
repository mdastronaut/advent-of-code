package aoc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdventUtil {

    public static List<String> readTextFile(String path) {
        List<String> list = new ArrayList<>();

        try {
            BufferedReader bfr = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bfr.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException ex) {
            System.out.println("could not find the file at the given path");
        }
        return list;
    }

    public static List<Integer> createList(int from, int to) {
        List<Integer> list = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            list.add(i);
        }
        return list;
    }

    public static Map<String, Integer> casePointMap() {
        Map<String, Integer> casePointsMap = new HashMap<>();
        casePointsMap.put("A Z", 0);
        casePointsMap.put("B X", 0);
        casePointsMap.put("C Y", 0);
        casePointsMap.put("A X", 3);
        casePointsMap.put("B Y", 3);
        casePointsMap.put("C Z", 3);
        casePointsMap.put("A Y", 6);
        casePointsMap.put("B Z", 6);
        casePointsMap.put("C X", 6);
        return casePointsMap;
    }

    public static Map<Character, Integer> symbolPoint() {
        Map<Character, Integer> symbolPointMap = new HashMap<>();
        symbolPointMap.put('X', 1);
        symbolPointMap.put('Y', 2);
        symbolPointMap.put('Z', 3);
        return symbolPointMap;
    }

    public static Map<Character, Integer> outcomePoint() {
        Map<Character, Integer> outcomePointMap = new HashMap<>();
        outcomePointMap.put('X', 0);
        outcomePointMap.put('Y', 3);
        outcomePointMap.put('Z', 6);
        return outcomePointMap;
    }

    public static Map<Character, Map<Integer, Integer>> caseMap() {
        Map<Character, Map<Integer, Integer>> caseMap = new HashMap<>();
        caseMap.put('A', rockMap());
        caseMap.put('B', paperMap());
        caseMap.put('C', scissorsMap());
        return caseMap;
    }

    public static Map<Integer, Integer> rockMap() {
        Map<Integer, Integer> rockMap = new HashMap<>();
        rockMap.put(0, 3);
        rockMap.put(3, 1);
        rockMap.put(6, 2);
        return rockMap;
    }

    public static Map<Integer, Integer> paperMap() {
        Map<Integer, Integer> paperMap = new HashMap<>();
        paperMap.put(0, 1);
        paperMap.put(3, 2);
        paperMap.put(6, 3);
        return paperMap;
    }

    public static Map<Integer, Integer> scissorsMap() {
        Map<Integer, Integer> outcomePointMap = new HashMap<>();
        outcomePointMap.put(0, 2);
        outcomePointMap.put(3, 3);
        outcomePointMap.put(6, 1);
        return outcomePointMap;
    }

    public static Set<Character> setMaker(String data) {
        Set<Character> set = new HashSet<>();
        data
                .chars()
                .mapToObj(i -> (char)i)
                .forEach(set::add);
        return set;
    }

                //line = line.replaceAll("[\\[\\]]"," ");  // not sol: HTVDTLC
    public static Map<List<Stack<Character>>, List<String>> customDataProcessorForTask5(String path) {
        Map<List<Stack<Character>>, List<String>> procedureStackMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> stringsForStacks = new ArrayList<>(Collections.nCopies(10, ""));
        List<String> fromToAmount = new ArrayList<>();

        try {
            BufferedReader bfr = new BufferedReader(new FileReader(path));
            String line; int j = 7;
            while ((line = bfr.readLine()) != null) {
                list.add(line);
                if (!line.startsWith("move")) {
                    for (int i = 0; i < line.length(); i++) {
                        if (Character.isLetter(line.charAt(i))) {
                            int stackIndex = indexColMap(i);
                            String s = stringsForStacks.get(stackIndex);
                            s += line.charAt(i);
                            stringsForStacks.set(stackIndex, s);
                        }
                    }
                } else {
                    line = line.replaceAll("[^\\d]", "");
                    fromToAmount.add(line);
                }
            }
        List<Stack<Character>> stackList = stackList(9);
            for (int i = 1; i < stringsForStacks.size(); i++) {
                StringBuilder sb = new StringBuilder();
                sb = sb.append(stringsForStacks.get(i)).reverse();
                for (Character c : sb.toString().toCharArray()) {
                    stackList.get(i).push(c);
                }
            }
        procedureStackMap.put(stackList,fromToAmount);
        } catch (IOException ex) {
            System.out.println("could not find the file at the given path");
        }
        return procedureStackMap;
    }

    // index - column
    public static int indexColMap(int n) {
        Map<Integer, Integer> indexColMap = new HashMap<>();
        indexColMap.put(1, 1);
        indexColMap.put(5, 2);
        indexColMap.put(9, 3);
        indexColMap.put(13, 4);
        indexColMap.put(17, 5);
        indexColMap.put(21, 6);
        indexColMap.put(25, 7);
        indexColMap.put(29, 8);
        indexColMap.put(33, 9);
        return indexColMap.get(n);
    }

    public static List<Stack<Character>> stackList(int size) {
        List<Stack<Character>> stackList = new ArrayList<>();
        int i = size;
        while (i-- >= 0) {
            stackList.add(new Stack<>());
        }
        return stackList;
    }

}
