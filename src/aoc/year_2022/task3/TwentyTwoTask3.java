package aoc.year_2022.task3;

import aoc.util.AdventUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwentyTwoTask3 {

    private static final String INPUT = "src/main/resources/2022/22-3-B";

    // part 1
    public static long sumOfPriorities() {
        List<String> data = AdventUtil.readTextFile(INPUT);
        Set<Character> setA = new HashSet<>();
        Set<Character> setB = new HashSet<>();
        long sumOfPriorities = 0;

        for (String s : data) {
            s.substring(0, s.length()/2)
                    .chars()
                    .mapToObj(i -> (char)i)
                    .forEach(setA::add);

            s.substring(s.length()/2)
                    .chars()
                    .mapToObj(i -> (char)i)
                    .forEach(setB::add);
            setA.retainAll(setB);
            Character ch = setA.stream().findFirst().get();
            sumOfPriorities += Character.isUpperCase(ch) ? (int)ch - 38 : (int)ch - 96;
            setA.clear();
            setB.clear();
        }
        return sumOfPriorities;
    }

    // part 2
    public static long sumOfPrioritiesWithNewAuth() {
        List<String> data = AdventUtil.readTextFile(INPUT);
        Set<Character> setA; long sumOfPrioritiesWithNewAuth = 0;

        for (int i = 0; i < data.size()-2; i +=3) {
            setA = AdventUtil.setMaker(data.get(i));
            setA.retainAll(AdventUtil.setMaker(data.get(i+1)));
            setA.retainAll(AdventUtil.setMaker(data.get(i+2)));
            Character ch = setA.stream().findFirst().get();
            sumOfPrioritiesWithNewAuth += Character.isUpperCase(ch) ? (int)ch - 38 : (int)ch - 96;
            setA.clear();
        }
        return sumOfPrioritiesWithNewAuth;
    }

}
