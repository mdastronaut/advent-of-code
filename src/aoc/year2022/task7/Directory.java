package aoc.year2022.task7;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    public String name;
    public List<Directory> directories;
    public long sizeOfFiles;

    public Directory(String name) {
        this.name = name;
        this.directories = new ArrayList<>();
        this.sizeOfFiles = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public long getSizeOfFiles() {
        return sizeOfFiles;
    }

    public void setSizeOfFiles(long sizeOfFiles) {
        this.sizeOfFiles = sizeOfFiles;
    }
}
