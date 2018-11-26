package service;

import exceptions.DirectoryIsEmptyException;
import logger.ServiceLogger;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceFiles implements Service {

    private File pathToFolder;
    private List<String> nameFiles;
    private Logger logger = new ServiceLogger().getLogger();

    public ServiceFiles() {
        pathToFolder = new File("/home/tishort/documents/exp/");
        nameFiles = new ArrayList<>();
    }

    public List<String> getListFilesName() {
        nameFiles.clear();
        getName(pathToFolder);
        return nameFiles;
    }

    private void getName(File folder) {
        File[] temp = folder.listFiles();
        for (File f:
             temp) {
            if (f.isDirectory()) {
                getName(f);
            } else {
                if (f.getName().endsWith(".txt"))
                    nameFiles.add(f.getName());
            }
        }
    }

    @Override
    public String getContentFile(int index) throws IOException {
        StringBuilder result = new StringBuilder();
        String pathToFile = pathToFolder + "/" + nameFiles.get(index);
        System.out.println("name file " + pathToFile);
        File nameFile = new File(pathToFile);
        if (nameFile.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))){
                while (reader.ready()) {
                    result.append(reader.readLine());
                    result.append("\n");
                }
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, "Ошибка при открытии файла" + e);
                throw new FileNotFoundException();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка чтения файла" + e);
                throw new IOException();
            }

        }
        return result.toString();
    }

    @Override
    public void createFile(String nameFile) throws IOException {
        String name = pathToFolder + "/" + nameFile + ".txt";
        File file = new File(name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка при создании файла" + e);
            throw new IOException();
        }
    }

    @Override
    public void saveFile(String nameFile, String text) throws IOException {
        String name = pathToFolder + "/" + nameFile + ".txt";
        File fileWrite = new File(name);
        try (FileWriter writer = new FileWriter(fileWrite)){
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка при сохранении файла" + e);
            throw new IOException();
        }

    }

    @Override
    public String contentFileByName(String nameFileFix) throws IOException {
        StringBuilder result = new StringBuilder();
        String pathToFile = pathToFolder + "/" + nameFileFix;
        System.out.println("name file " + pathToFile);
        File nameFile = new File(pathToFile);
        if (nameFile.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))){
                while (reader.ready()) {
                    result.append(reader.readLine());
                    result.append("\n");
                }
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, "Ошибка при открытии файла" + e);
                throw new FileNotFoundException();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка чтения файла" + e);
                throw new IOException();
            }

        }
        return result.toString();
    }

    @Override
    public void deleteFile(int indexFileFix) {
        String pathToFile = pathToFolder + "/" + nameFiles.get(indexFileFix);
        File nameFile = new File(pathToFile);
        if (!nameFile.delete()) {
            logger.log(Level.WARNING, "Ошибка при удалении файла");
        }
    }

    @Override
    public List<String> filterFileByName(String filter) {
        List<String> result = new ArrayList<>();

        Path path = Paths.get(pathToFolder.toURI());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
            for (Path entry: stream) {
                if (entry.getFileName().endsWith(".txt")) {
                    result.add(entry.getFileName().toString());
                }

            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка чтения файла");
        }
        return result;
    }

    @Override
    public List<String> filterFileByContext(String context) {
        List<String> result = new ArrayList<>();
        getName(pathToFolder);
        for (String name:
                nameFiles) {
            String pathToFile = pathToFolder + "/" + name;
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))){
                while (reader.ready()) {
                    if (context.equals(reader.readLine())) {
                        result.add(name);
                    }
                }
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, "Ошибка при открытии файла" + e);
                e.printStackTrace();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка чтения файла" + e);
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public Map<String, Integer> getSumWordsInFiles() {
        Map<String, Integer> result = new HashMap<>();
        getName(pathToFolder);
        for (String name:
             nameFiles) {
            int sumWords = 0;
            String pathToFile = pathToFolder + "/" + name;
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))){
                while (reader.ready()) {
                    sumWords += reader.readLine().split("\\W+").length;
                }
                result.put(name, sumWords);
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, "Ошибка при открытии файла" + e);
                e.printStackTrace();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка чтения файла" + e);
                e.printStackTrace();
            }
        }
        return result;
    }
}
