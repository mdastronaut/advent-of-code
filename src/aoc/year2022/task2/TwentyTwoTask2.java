package aoc.year2022.task2;

import aoc.util.AdventUtil;

import java.util.List;

public class TwentyTwoTask2 {

    private static final String INPUT = "src/main/resources/2022/22-2-B";

    // part 1
    public static long rockPaperScissorsTotalScoreBaseOnOurHypothesis() {
        List<String> data = AdventUtil.readTextFile(INPUT);
        long totalScore = 0;

        for (String s : data) {
            totalScore += AdventUtil.casePointMap().get(s) + AdventUtil.symbolPoint().get(s.charAt(s.length()-1));
        }
        return totalScore;
    }

    // part 2
    public static long rockPaperScissorsTotalScoreBaseOnOurStrategyGuide() {
        List<String> data = AdventUtil.readTextFile(INPUT);
        long totalScore = 0;

        for (String s : data) {
            int outcomePoint = AdventUtil.outcomePoint().get(s.charAt(s.length()-1));
            int symbolPoint = AdventUtil.caseMap().get(s.charAt(0)).get(outcomePoint);
            totalScore += outcomePoint;
            totalScore += symbolPoint;
        }
        return totalScore;
    }

}
