package service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Service {

    List<String> getListFilesName();
    String getContentFile(int index) throws IOException;
    void createFile(String nameFile) throws IOException;
    void saveFile(String nameFile, String text) throws IOException;
    String contentFileByName(String nameFileFix) throws IOException;
    void deleteFile(int indexFileFix);
    List<String> filterFileByName(String filter);
    List<String> filterFileByContext(String context);
    Map<String, Integer> getSumWordsInFiles();
}
