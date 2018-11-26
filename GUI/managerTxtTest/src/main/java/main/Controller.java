package main;

import exceptions.DirectoryIsEmptyException;
import gui.Gui;
import service.Service;
import service.ServiceFiles;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Controller {

    private Gui view;
    private Service service;

    public Controller(Gui view) {
        this.view = view;
        service = new ServiceFiles();
    }

    public List<String> getListFilesName() throws DirectoryIsEmptyException {
        List<String> list = service.getListFilesName();
        if (list.isEmpty()) {
            throw new DirectoryIsEmptyException();
        }
        return list;
    }

    public String contentFile(int index) throws IOException {
        return service.getContentFile(index);
    }

    public void createFile(String nameFile) throws IOException {
        service.createFile(nameFile);
    }

    public void saveFile(String nameFile, String text) throws IOException {
        service.saveFile(nameFile, text);
    }

    public String contentFileByName(String nameFileFix) throws IOException {
        return service.contentFileByName(nameFileFix);
    }

    public void deleteFile(int indexFileFix) {
        service.deleteFile(indexFileFix);
    }

    public List<String> filterFileByName(String filter) throws DirectoryIsEmptyException {
        List<String> list = service.filterFileByName(filter);
        if (list.isEmpty()) {
            throw new DirectoryIsEmptyException();
        }
        return list;
    }

    public List<String> filterFileByContext(String context) throws DirectoryIsEmptyException {
        List<String> list = service.filterFileByContext(context);
        if (list.isEmpty()) {
            throw new DirectoryIsEmptyException();
        }
        return list;
    }

    public Map<String, Integer> getSumWordsInFiles() throws DirectoryIsEmptyException {
        Map<String, Integer> map = service.getSumWordsInFiles();
        if (map.isEmpty()) {
            throw new DirectoryIsEmptyException();
        }
        return map;
    }
}
