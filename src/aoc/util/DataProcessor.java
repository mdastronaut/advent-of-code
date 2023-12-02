package aoc.util;

//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {

    //private final static Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    public static List<String> readTextFile(String path) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bfr.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException ex) {
            //logger.info(ex, () -> "could not find the file at the given path");
        }
        return list;
    }

}
