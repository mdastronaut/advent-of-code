package aoc.year2022.task7;

import aoc.util.AdventUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwentyTwoTask7 {

    public static Map<Character, Long> totalSizePerDirMap = new HashMap<>();

    public static int noSpaceLeftOnDevicePart1() {
        String filePath = "src/main/resources/2022/22-7-A";
        List<String> data = AdventUtil.readTextFile(filePath);

        List<Directory> directories = data
                .stream()
                .filter(d -> d.startsWith("dir"))
                .map(d -> new Directory(d.substring(4)))
                .collect(Collectors.toList());
        directories.add(new Directory("/"));
        Character dir = ' ';

        /*
        dir
        ls -> loop to next $ cd | end
            if number accumulate
            add dirs
         */
        for (int i = 0; i < data.size(); i++) {
            String currLine = data.get(i);
            if (currLine.startsWith("$ ls")) {
                String prev = data.get(i - 1);
                dir = prev.charAt(prev.length() - 1);
                for (int j = i + 1; j < data.size(); j++) {
                    if (Character.isDigit(data.get(j).charAt(0))) {
                        int currSize = Integer.parseInt(data.get(j).split("\\s")[0]);
                        Character currDir = dir;
                        directories.forEach(d -> {
                            // if size
                            if (d.getName().charAt(d.getName().length() - 1) == currDir) {
                                long currSizeOfFiles = d.getSizeOfFiles();
                                currSizeOfFiles += currSize;
                                d.setSizeOfFiles(currSizeOfFiles);
                            }
                        });
                    }
                    // if dir
                    if (data.get(j).charAt(0) == 'd') {
                        Directory newDirToAdd = new Directory(
                                String.valueOf(data.get(j).charAt(data.get(j).length() - 1)));


                        Character currDir = dir;
                        directories.forEach(d -> {
                            if(d.getName().equals(String.valueOf(currDir))) {
                                d.getDirectories().add(newDirToAdd);
                        }});
                    }
                    if (data.get(j).startsWith("$ cd")) break;
                }
            }
        }

        directories.forEach(d -> totalSizePerDirMap.put(d.getName().charAt(0), 0L));


        directories.forEach(d ->
            totalSizePerDirMap.put(d.getName().charAt(0), calculateSizeForDirectory(d)));

        return 0;
    }

    public static long calculateSizeForDirectory(Directory directory) {
            long size = directory.getSizeOfFiles();
            if (!directory.getDirectories().isEmpty()) {
                size += directory.getDirectories()
                        .stream()
                        .map(TwentyTwoTask7::calculateSizeForDirectory)
                        .mapToLong(Long::longValue)
                        .sum();
            }
            return size;
    }
}


/*
public static long calculateSizeForDirectory(Directory directory) {
            long size = directory.getSizeOfFiles();
            if (!directory.getDirectories().isEmpty()) {
                size += directory.getDirectories()
                        .stream()
                        .map(TwentyTwoTask7::calculateSizeForDirectory)
                        .mapToLong(Long::longValue)
                        .sum();
            }
            return size;
    }
 */
