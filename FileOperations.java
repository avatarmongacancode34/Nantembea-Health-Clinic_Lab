import java.util.ArrayList;
import java.util.TreeMap;

public interface FileOperations {
    void saveToFile(String filename) throws IOException;

    void loadFromFile(String filename) throws IOException;

}
