package aoc.year2022.task1;

import aoc.util.AdventUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwentyTwoTask1 {

    private static final String INPUT = "src/main/resources/2022/22-1-B";

    // part 1
    public static long totalCalories() {
        List<String> data = AdventUtil.readTextFile(INPUT);
        long max = 0;
        long currentLoad = 0;

        for (String s : data) {
            if (StringUtils.isNotBlank(s)) {
                currentLoad += Long.parseLong(s);
            } else {
                max = Math.max(currentLoad, max);
                currentLoad = 0;
            }
        }
        return currentLoad > 0 ? Math.max(currentLoad, max) : max;
    }

    // part 2
    public static long sumOfTopThreeCalories() {
        List<String> data = AdventUtil.readTextFile(INPUT);
        List<Long> caloriesByElves = new ArrayList<>();
        long currentLoad = 0;

        for (String s : data) {
            if (StringUtils.isNotBlank(s)) {
                currentLoad += Long.parseLong(s);
            } else {
                caloriesByElves.add(currentLoad);
                currentLoad = 0;
            }
        }
        caloriesByElves.add(currentLoad);
        Collections.sort(caloriesByElves);
        int s = caloriesByElves.size();
        return caloriesByElves.get(s-1) + caloriesByElves.get(s-2) + caloriesByElves.get(s-3);
    }

}
