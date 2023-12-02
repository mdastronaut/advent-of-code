package aoc.year_2022.task6;

import aoc.util.AdventUtil;

import java.util.HashSet;
import java.util.Set;

public class TwentyTwoTask6 {

    public static int markerDetection(String type) {
        String filePath = "src/main/resources/2022/22-6-A";
        String packet = AdventUtil.readTextFile(filePath).get(0);
        Set<Character> challengeSet = new HashSet<>();

        int i = 0, j = 4;
        if (type.equals("msg")) j += 10;
        while (j <= packet.length()) {
            packet
                    .substring(i,j)
                    .chars()
                    .mapToObj(c -> (char)c)
                    .forEach(challengeSet::add);

            if ((type.equals("packet") && challengeSet.size() == 4)
                || type.equals("msg") && challengeSet.size() == 14)
                return j;

            challengeSet.clear(); i++; j++;
        }
        return -1;
    }

}
