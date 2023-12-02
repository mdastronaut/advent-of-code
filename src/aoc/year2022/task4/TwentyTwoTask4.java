package aoc.year2022.task4;

import aoc.util.AdventUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwentyTwoTask4 {

    private static final String INPUT = "src/main/resources/2022/22-4-B";

    public static long countFullyCoveredAssignmentPairs(boolean firstPart) {
        List<String> data = AdventUtil.readTextFile(INPUT);
        int ctr = 0;

        for (String s : data) {
            String[] splitByComma = s.split(","); // 34-37, 35-36
            int i = 3;

            // digit      index    ( 2 lists -> single digit; 2 lists -> multiple digits )
            //  1       ->   0
            //  3       ->   1
            //  1       ->   2
            //  3       ->   3
            List<List<Integer>> listOfList = new ArrayList<>();

            while (i-- >= 0) {
              listOfList.add(new ArrayList<>());
            }

            for (String z : splitByComma) {
                String[] splitByDash = z.split("-"); // 7-11

                if (z.length() >= 3) {
                   if (listOfList.get(1).isEmpty()) {
                       listOfList.set(1,AdventUtil.createList(Integer.parseInt(splitByDash[0]), Integer.parseInt(splitByDash[1])));
                   } else {
                       listOfList.set(3, AdventUtil.createList(Integer.parseInt(splitByDash[0]), Integer.parseInt(splitByDash[1])));
                   }
                } else {
                    if (listOfList.get(0).isEmpty()) {
                        List<Integer> curr1 = new ArrayList<>();
                        curr1.add(Character.getNumericValue(Integer.parseInt(splitByDash[0])));
                        listOfList.set(0, curr1);
                    } else {
                        List<Integer> curr2 = new ArrayList<>();
                        curr2.add(Character.getNumericValue(Integer.parseInt(splitByDash[0])));
                        listOfList.set(2, curr2);
                    }
                }
            }

            // filter not empty lists
            listOfList = listOfList.stream()
                    .filter(list -> list.size() != 0)
                    .collect(Collectors.toList());

            if (firstPart) {
                if (listOfList.get(0).containsAll(listOfList.get(1))
                        || listOfList.get(1).containsAll(listOfList.get(0))) ctr++;
            } else {
                Set<Integer> setA = new HashSet<>(listOfList.get(0));
                setA.retainAll(new HashSet<>(listOfList.get(1)));
                if (setA.size() > 0) ctr++;
            }
        }
        return ctr;
    }

}
