package service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ServiceFilesTest {

    List<String> listFiles;
    ServiceFiles serviceFiles;

    @Test
    void getListFilesNameTestMock() {
        List<String> nameFiles = new ArrayList<>();
        nameFiles.add("Linux.txt");
        nameFiles.add("linux_man.txt");
        nameFiles.add("temp notes.txt");
        nameFiles.add("Программ.txt");
        nameFiles.add("notes c++.txt");
        nameFiles.add("Список книг.txt");

        Service service = mock(Service.class);
        when(service.getListFilesName()).thenReturn(nameFiles);

        assertArrayEquals(service.getListFilesName().toArray(), nameFiles.toArray());
        verify(service).getListFilesName();
    }

    @Test
    void getListFilesNameTest() {
        listFiles = new ArrayList<>();
        getListFiles(new File("/home/tishort/documents/exp/"));

        serviceFiles = new ServiceFiles();
        assertArrayEquals(listFiles.toArray(), serviceFiles.getListFilesName().toArray());
    }

    private void getListFiles(File file) {
        File[] temp = file.listFiles();
        for (File f:
                temp) {
            if (f.isDirectory()) {
                getListFiles(f);
            } else {
                if (f.getName().endsWith(".txt"))
                    listFiles.add(f.getName());
            }
        }
    }

    @Test
    public void getContentFileTest() throws IOException {
        String text = "проверка!";

        Service service = mock(Service.class);

        when(service.getContentFile(7)).thenReturn(text);

        assertEquals(service.getContentFile(7), text);
        verify(service).getContentFile(7);
   }

   @Test
   public void getContentFileTestException() throws IOException {
       Service service = mock(Service.class);

       when(service.getContentFile(7)).thenThrow(new FileNotFoundException());


       Throwable exception = assertThrows(FileNotFoundException.class,
               ()->{service.getContentFile(7);} );

   }

    @Test
    void createFileTest() throws IOException {
        ServiceFiles service = new ServiceFiles();
        service.createFile("testing");

        File pathToFile = new File("/home/tishort/documents/exp/testing.txt");

        assertEquals(pathToFile.exists(), true);

        pathToFile.delete();

    }

    @Test
    void saveFileTest() throws IOException {
        ServiceFiles service = new ServiceFiles();
        String act = "testing";
        service.saveFile("testing", act);

        File pathToFile = new File("/home/tishort/documents/exp/testing.txt");
        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        StringBuilder result = new StringBuilder();
        while (reader.ready()) {
            result.append(reader.readLine());
        }

        assertEquals(result.toString(), act);

        reader.close();
    }

}