package aoc.year_2023.day2;

import aoc.util.DataProcessor;

import java.util.*;

public class AoC_23_Day2 {

    public int sumOfIDs(String resourcePath, int part) {
        List<String> inputs = DataProcessor.readTextFile(resourcePath);
        int sumOfValidGameIds = 0;
        int sumOfPowerOfSets = 0;

        outermostLoop:
        for (String game : inputs) {
            String[] gameComponents = game.split(":|;");
            gameComponents = createTrimmedStringArray(gameComponents);

            int gameId = Integer.parseInt(gameComponents[0].split("\\s")[1]);

            Game gameObj = new Game(gameId);

            for (int i = 1; i < gameComponents.length; i++) {
                String[] currentPull = gameComponents[i].split(",\\s");
                Pull pull = new Pull();
                for (String s : currentPull) {
                    String[] colorQuantity = s.split("\\s");
                    pull.addColorAndQuantity(colorQuantity[1], Integer.parseInt(colorQuantity[0]));
                }
                validatePull(pull);
                gameObj.addPull(pull);
                // todo: use stream
            }
            if (part == 1) {
                for (Pull pullFromGameObj : gameObj.pulls) {
                    if (!pullFromGameObj.getValid()) {
                        continue outermostLoop;
                    }
                }
                sumOfValidGameIds +=gameId;
            } else {
                long maxRedInCurrPull = 1;
                long maxGreenCurrPull = 1;
                long maxBlueCurrPull = 1;

                for (Pull pullFromGameObj : gameObj.pulls) {
                    Map<String, Integer> colorQuanityMap = pullFromGameObj.getColorQuantityMap();
                    for (Map.Entry<String, Integer> entry : colorQuanityMap.entrySet()) {
                        if (entry.getKey().equals("red")) {
                            maxRedInCurrPull = Math.max(maxRedInCurrPull, entry.getValue());
                        }
                        if (entry.getKey().equals("blue")) {
                            maxBlueCurrPull = Math.max(maxBlueCurrPull, entry.getValue());
                        }
                        if (entry.getKey().equals("green")) {
                            maxGreenCurrPull = Math.max(maxGreenCurrPull, entry.getValue());
                        }
                    }
                }
                // should not set maxes do default zero
                sumOfPowerOfSets += maxRedInCurrPull * maxBlueCurrPull * maxGreenCurrPull;
            }
        }
        return part == 1 ? sumOfValidGameIds : sumOfPowerOfSets;
    }



//        public long sumOfIDs(String resourcePath) {
//            List<String> inputs = DataProcessor.readTextFile(resourcePath);
//            int sumOfPowerOfSets = 0;
//
//            outermostLoop:
//            for (String game : inputs) {
//                String[] gameComponents = game.split(":|;");
//                gameComponents = createTrimmedStringArray(gameComponents);
//
//                int gameId = Integer.valueOf(gameComponents[0].split("\\s")[1]);
//
//                Game gameObj = new Game(gameId);
//
//                for (int i = 1; i < gameComponents.length; i++) {
//                    String[] currentPull = gameComponents[i].split(",\\s");
//                    Pull pull = new Pull();
//                    for (String s : currentPull) {
//                        String[] colorQuantity = s.split("\\s");
//                        pull.addColorAndQuantity(colorQuantity[1], Integer.valueOf(colorQuantity[0]));
//                    }
//                    validatePull(pull);
//                    gameObj.addPull(pull);
//                    // todo: use stream
//                }
//                long maxRedInCurrPull = 1;
//                long maxGreenCurrPull = 1;
//                long maxBlueCurrPull = 1;
//
//                for (Pull pullFromGameObj : gameObj.pulls) {
//                    Map<String, Integer> colorQuanityMap = pullFromGameObj.getColorQuantityMap();
//                    for (Map.Entry<String, Integer> entry : colorQuanityMap.entrySet()) {
//                        if (entry.getKey().equals("red")) {
//                            maxRedInCurrPull = Math.max(maxRedInCurrPull, entry.getValue());
//                        }
//                        if (entry.getKey().equals("blue")) {
//                            maxBlueCurrPull = Math.max(maxBlueCurrPull, entry.getValue());
//                        }
//                        if (entry.getKey().equals("green")) {
//                            maxGreenCurrPull = Math.max(maxGreenCurrPull, entry.getValue());
//                        }
//                    }
//                }
//                // should not set maxes do default zero
//                sumOfPowerOfSets += maxRedInCurrPull * maxBlueCurrPull * maxGreenCurrPull;
//            }
//            return sumOfPowerOfSets;
//        }

        public void validatePull(Pull pull) {
            Map<String, Integer> validationMap = colorQuantityValidationMap();

            for (Map.Entry<String, Integer> entry: pull.getColorQuantityMap().entrySet()) {
                // key -> color, value -> quantity
                String color = entry.getKey();
                int quantity = entry.getValue();
                if (quantity > validationMap.get(color)) {
                    pull.setValid(false);
                }
            }
        }

        // only 12 red cubes, 13 green cubes, and 14 blue cubes?
        public Map<String, Integer> colorQuantityValidationMap() {
            Map<String, Integer> map = new HashMap<>();
            map.put("red", 12);
            map.put("green", 13);
            map.put("blue", 14);
            return map;
        }

        public class Game {
            private int gameId;
            private List<Pull> pulls;

            public Game(int gameId) {
                this.gameId = gameId;
                pulls = new ArrayList<Pull>();
            }

            public void addPull(Pull pull) {
                pulls.add(pull);
            }

            public int getGameId() {
                return this.gameId;
            }

            public List<Pull> getPulls() {
                return pulls;
            }
        }

        public class Pull {
            private Map<String, Integer> colorQuantityMap;
            private boolean valid;

            public Pull() {
                colorQuantityMap = new HashMap<>();
                valid = true;
            }

            public Map<String, Integer> getColorQuantityMap() {
                return this.colorQuantityMap;
            }

            public void addColorAndQuantity(String color, int quantity) {
                colorQuantityMap.put(color, quantity);
            }

            public void setValid(boolean result) {
                this.valid = result;
            }

            public boolean getValid() {
                return this.valid;
            }

        }

        public static String[] createTrimmedStringArray(String[] arr) {
            return Arrays.stream(arr)
                    .map(String::trim)
                    .toArray(String[]::new);
        }
    }


