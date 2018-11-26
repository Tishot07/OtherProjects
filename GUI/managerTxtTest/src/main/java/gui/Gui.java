package gui;

import exceptions.DirectoryIsEmptyException;
import main.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Gui extends JFrame implements ActionListener {

    private Controller controller;

    private JFrame frame;
    private JMenuBar menuBar;
    private JLabel statusBar;
    private JPanel statusBarPanel;
    private JPanel centerPanel;

    private JList list;
    private DefaultListModel model;
    private JScrollPane scrollPaneList;
    private JPanel listFilesPanel;

    private JTextArea area;
    private JScrollPane scrollPaneText;
    private JPanel textPanel;

    private JPanel panelButton;
    private JButton saveFileButton;
    private JButton newFileButton;
    private JButton deleteFileButton;

    private String nameFileFix;
    private int indexFileFix;

    private JDialog windowStata;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Gui() {

        frame = new JFrame("Приложение для работы с текстовыми файлами");
        //frame.setLayout(new FlowLayout());
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(createMenuBar());

        listFilesPanel = new JPanel();
        listFilesPanel.add(createListFiles());
        textPanel = new JPanel();
        textPanel.add(createAreaText());

        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(listFilesPanel);
        centerPanel.add(textPanel);
        centerPanel.add(createButtons());
        frame.add(centerPanel, BorderLayout.CENTER);

        statusBar = new JLabel();
        statusBar.setText("");
        statusBarPanel = new JPanel();
        statusBarPanel.add(statusBar);
        frame.add(statusBarPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    private JMenuBar createMenuBar() {
        JMenu showFiles = new JMenu("Files");
        JMenuItem showItem = new JMenuItem("Show files");
        showItem.addActionListener(this);
        showFiles.add(showItem);

        JMenu edit = new JMenu("File");
        JMenuItem addFile = new JMenuItem("add file");
        addFile.addActionListener(this);
        JMenuItem deleteFile = new JMenuItem("delete file");
        deleteFile.addActionListener(this);
        edit.add(addFile);
        edit.add(deleteFile);

        JMenu filter = new JMenu("Filter");
        JMenuItem filterName = new JMenuItem("Name file");
        filterName.addActionListener(this);
        JMenuItem filterContext = new JMenuItem("Context file");
        filterContext.addActionListener(this);
        filter.add(filterName);
        filter.add(filterContext);

        JMenu stata = new JMenu("Stata");
        JMenuItem stataItem = new JMenuItem("Stata files");
        stataItem.addActionListener(this);
        stata.add(stataItem);

        menuBar = new JMenuBar();
        menuBar.add(showFiles);
        menuBar.add(edit);
        menuBar.add(filter);
        menuBar.add(stata);

        return menuBar;
    }

    private JScrollPane createListFiles() {
        model = new DefaultListModel();
        list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPaneList = new JScrollPane(list);
        scrollPaneList.setPreferredSize(new Dimension(200, 500));

        list.addListSelectionListener(e -> {
            //срабатываем только когда выбираем пунк меню (иначе два раза выбирается; когда нажимаем на мышь и когда отпускаем мышь)
            if (!e.getValueIsAdjusting()) {
                indexFileFix = list.getSelectedIndex();
                //nameFileFix = (String)list.getSelectedValue();
                System.out.println("Проверяем индекс: " + indexFileFix);
                if (indexFileFix != -1) {
                    System.out.println("И...");
                    try {
                        area.setText(controller.contentFile(indexFileFix));
                        //area.setText(controller.contentFileByName(nameFileFix));
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(this, "Ошибка при открытии файла. Файл не может быть открыт.");
                    }


                area.addCaretListener(ex -> {
                    saveFileButton.setEnabled(true);
                });
                }
            }
        });

        return scrollPaneList;
    }

    private void createListFiles(List<String> listFiles) {
        model.clear();
        for (String str:
             listFiles) {
            model.addElement(str);
        }
    }

    private JScrollPane createAreaText() {
        area = new JTextArea();
        area.setWrapStyleWord(true);
        area.setLineWrap(true);

        scrollPaneText = new JScrollPane(area);
        scrollPaneText.setPreferredSize(new Dimension(650, 500));
        return scrollPaneText;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Show files" :
                list.clearSelection();
                try {
                    createListFiles(controller.getListFilesName());
                } catch (DirectoryIsEmptyException e1) {
                    JOptionPane.showMessageDialog(this, "В директории нет текстовых файлов!");
                }
                break;
             case "add file" :
                 list.clearSelection();
                 nameFileFix = JOptionPane.showInputDialog("Enter name new file");
                 if (nameFileFix.isEmpty()) {
                     JOptionPane.showMessageDialog(this, "Вы не ввели имя файла");
                     statusBar.setText("Вы не ввели имя файла");
                 } else {
                     try {
                         controller.createFile(nameFileFix);
                     } catch (IOException e1) {
                         JOptionPane.showMessageDialog(this, "Файл не может быть создан");
                     }

                     area.setText("");

                     try {
                         createListFiles(controller.getListFilesName());
                     } catch (DirectoryIsEmptyException e1) {
                         JOptionPane.showMessageDialog(this, "В директории нет текстовых файлов!");
                     }
                     //созданный файл был выделен по умолчанию
                     list.setSelectedValue(nameFileFix + ".txt", false);
                     statusBar.setText("Вы создали файл с именем " + nameFileFix);
                 }
                break;
            case "delete file" :
                indexFileFix = list.getSelectedIndex();
                if (indexFileFix == -1) {
                    JOptionPane.showMessageDialog(this, "Ни один файл не выделен!");
                } else {
                    controller.deleteFile(indexFileFix);
                    statusBar.setText("Файл удален");
                    try {
                        createListFiles(controller.getListFilesName());
                    } catch (DirectoryIsEmptyException e1) {
                        JOptionPane.showMessageDialog(this, "В директории нет текстовых файлов!");
                    }
                }
                break;
            case "Name file" :
                String filter = JOptionPane.showInputDialog("Фильтрация по названию:");
                if (filter == null)
                    break;
                if (filter.isEmpty()) {
                    statusBar.setText("Фильтр не задан.");
                    try {
                        createListFiles(controller.getListFilesName());
                    } catch (DirectoryIsEmptyException e1) {
                        JOptionPane.showMessageDialog(this, "В директории нет текстовых файлов!");
                    }
                } else {
                    try {
                        createListFiles(controller.filterFileByName(filter));
                    } catch (DirectoryIsEmptyException e1) {
                        JOptionPane.showMessageDialog(this, "В директории нет файлов, удовлетворяющих запросу!");
                    }
                }
                break;
            case "Context file" :
                list.clearSelection();
                area.setText("");
                String context = JOptionPane.showInputDialog("Строка в файле:");
                if (context == null)
                    break;
                if (context.isEmpty()) {
                    statusBar.setText("Фильтр не задан.");
                    try {
                        createListFiles(controller.getListFilesName());
                    } catch (DirectoryIsEmptyException e1) {
                        JOptionPane.showMessageDialog(this, "В директории нет текстовых файлов!");
                    }
                } else {
                    try {
                        createListFiles(controller.filterFileByContext(context));
                    } catch (DirectoryIsEmptyException e1) {
                        JOptionPane.showMessageDialog(this, "В директории нет файлов, удовлетворяющих запросу!");
                    }
                }
                break;
            case "Stata files" :
                list.clearSelection();
                area.setText("");
                windowStata = new JDialog(this, "Статистика файлов", true);
                getWindowStata(windowStata);
                break;
        }
    }

    private void saveToFile(String nameFile, String text) {
        try {
            controller.saveFile(nameFile, text);
            statusBar.setText("Файл сохранен.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ошибка при сохранении файла. Файл не может быть сохранен.");
        }
    }

    private JPanel createButtons() {
        panelButton = new JPanel();
        panelButton.setLayout(new FlowLayout());

        saveFileButton = new JButton("сохранить изменения в файле");
        saveFileButton.setEnabled(false);
        saveFileButton.addActionListener(e -> {
            saveToFile(nameFileFix, area.getText());
        });

        newFileButton = new JButton("Создать новый файл");
        newFileButton.setActionCommand("add file");
        newFileButton.addActionListener(this);
        deleteFileButton = new JButton("Удалить выделенный файл");
        deleteFileButton.setActionCommand("delete file");
        deleteFileButton.addActionListener(this);

        panelButton.add(newFileButton);
        panelButton.add(saveFileButton);
        panelButton.add(deleteFileButton);

        return panelButton;
    }

    private void getWindowStata(JDialog dialog) {
        dialog.setSize(500, 500);
        Object[][] data = getDataForStata();
        String[] head = {"File", "Word"};
        JTable table = new JTable(data, head);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private String[][] getDataForStata() {
        String[][] result = null;
        long sumWord = 0;
        try {
            Map<String, Integer> stata = controller.getSumWordsInFiles();
            result = new String[stata.size() + 1][2];
            int i = 0;
            for (Map.Entry<String, Integer> entry:
                 stata.entrySet()) {
                result[i][0] = entry.getKey();
                result[i][1] = entry.getValue().toString();
                sumWord += entry.getValue();
                i++;
            }
            result[i][0] = "Всего: ";
            result[i][1] = String.valueOf(sumWord);
        } catch (DirectoryIsEmptyException e) {
            JOptionPane.showMessageDialog(this, "В директории нет файлов для статистики");
        }
        return result;
    }
}
