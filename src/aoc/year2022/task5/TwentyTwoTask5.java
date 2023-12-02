package aoc.year2022.task5;

import aoc.util.AdventUtil;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class TwentyTwoTask5 {

    public static String crateMover9000() {
        Map<List<Stack<Character>>, List<String>> procedureStackMap =
                AdventUtil.customDataProcessorForTask5("src/main/resources/2022/22-5-B");

        List<String> instructions = procedureStackMap.values().stream().findFirst().get();
        List<Stack<Character>> stackList = procedureStackMap.keySet().stream().findFirst().get();

        String from, to, amount, curr;
        StringBuilder result = new StringBuilder();

        for(String s : instructions) {

            if (s.length() == 4) {
                amount = s.substring(0,2);
                from = String.valueOf(s.charAt(2));
                to = String.valueOf(s.charAt(3));
            } else {
                amount = String.valueOf(s.charAt(0));
                from = String.valueOf(s.charAt(1));
                to = String.valueOf(s.charAt(2));
            }
            for (int i = 0; i < Integer.parseInt(amount); i++) {
                if (!stackList.get(Integer.parseInt(from)).isEmpty()) {
                    curr = stackList.get(Integer.parseInt(from)).pop().toString();
                    stackList.get(Integer.parseInt(to)).add(curr.charAt(0));
                }
            }
        }
        stackList = stackList.stream()
                .filter(stack -> stack.size() > 0)
                .collect(Collectors.toList());

    for (Stack<Character> stack : stackList) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    public static String crateMover9001() {
        Map<List<Stack<Character>>, List<String>> procedureStackMap =
                AdventUtil.customDataProcessorForTask5("src/main/resources/2022/22-5-B");

        List<String> instructions = procedureStackMap.values().stream().findFirst().get();
        List<Stack<Character>> stackList = procedureStackMap.keySet().stream().findFirst().get();

        String from, to, amount, singleCrate;
        StringBuilder result = new StringBuilder();
        int k = 1;

        for(String s : instructions) {
            if (s.length() == 4) {
                amount = s.substring(0,2);
                from = String.valueOf(s.charAt(2));
                to = String.valueOf(s.charAt(3));
            } else {
                amount = String.valueOf(s.charAt(0));
                from = String.valueOf(s.charAt(1));
                to = String.valueOf(s.charAt(2));
            }
            if (!stackList.get(Integer.parseInt(from)).isEmpty()) {
                if (Integer.parseInt(amount) == 1) {
                    singleCrate = stackList.get(Integer.parseInt(from)).pop().toString();
                    stackList.get(Integer.parseInt(to)).add(singleCrate.charAt(0));
                    System.out.println("\n\nrow -> " + k);
                    stackList.forEach(System.out::println);
                    System.out.println("\n\n");
                    k++;
                } else {
                    Stack<Character> stackFrom = stackList.get(Integer.parseInt(from));
                    Stack<Character> stackTo = stackList.get(Integer.parseInt(to));
                    int amountInInt = Integer.parseInt(amount);
                    for (int i = stackFrom.size()-amountInInt; i < stackFrom.size(); i++) {
                        stackTo.add(stackFrom.get(i));
                    }
                    for (int j = 0; j < amountInInt; j++) {
                        stackFrom.pop();
                    }
                    System.out.println("\n\nrow -> " + k);
                    stackList.forEach(System.out::println);
                    System.out.println("\n\n");
                    k++;
                }
            }
        }

        stackList = stackList.stream()
                .filter(stack -> stack.size() > 0)
                .collect(Collectors.toList());

        for (Stack<Character> stack : stackList) {
            result.append(stack.pop());
        }

        return result.toString();
    }
}
