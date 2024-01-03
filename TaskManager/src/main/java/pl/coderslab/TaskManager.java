package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;
    static String fileTask = "tasks.csv";

    public static void main(String[] args) {

        tasks = tasksFromFile("tasks.csv");
        showOptions();
        optionSelect();
    }

    public static void showOptions() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        String[] options = {"add", "remove", "list", "exit"};
        for (String option : options) {
            System.out.println(ConsoleColors.RESET + option);
        }
    }

    public static String[][] tasksFromFile(String fileName) {
        Path path = Paths.get(fileName);
        String[][] taskArray;
        int row = 0;
        int columns = 0;
        if (!Files.exists(path)) {
            System.out.println("Plik nie istnieje");
            System.exit(0);
        }
        try {
            for (String line : Files.readAllLines(path)) {
                row += 1;
                columns = line.split(",").length;
            }
        } catch (IOException e) {
            System.out.println("Nie można wyświetlić pliku");
        }
        taskArray = new String[row][columns];
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                for (int i = 0; i < row; i++) {
                    String[] line = scanner.nextLine().split(",");
                    for (int j = 0; j < columns; j++) {
                        taskArray[i][j] = line[j];
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Nie można wyświetlić pliku");
        }
        return taskArray;
    }

    public static void optionSelect() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option:");
        String input = "";
        while (!"exit".equals(input)) {
            input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    showOptions();
                    break;
                case "remove":
                    removeTask();
                    showOptions();
                    break;
                case "list":
                    listTask();
                    showOptions();
                    break;
                case "exit":
                    exitAndSave();
                    System.out.println(ConsoleColors.RED + "Bye Bye");
                    break;
                default:
                    System.out.println("Please select a correct option!!");
            }
        }
    }

    public static void addTask() {
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        tasks[tasks.length - 1][0] = scanner.nextLine();
        System.out.println("Please add task due date");
        tasks[tasks.length - 1][1] = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        tasks[tasks.length - 1][2] = scanner.nextLine();
    }

    public static void listTask() {
        int counter = 0;
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");
        int counter = 0;
        while (counter == 0) {
            try {

                int index = getInt();
                tasks = ArrayUtils.remove(tasks, index);
                System.out.println("Value was successflly deleted");
                counter += 1;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Incorect argument passed. Please give correct index number");
            }
        }

    }

    public static void exitAndSave() {
        try (FileWriter fileWriter = new FileWriter(fileTask, false)) {
            for (String[] task : tasks) {
                for (String string : task) {
                    fileWriter.append(string);
                }
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            System.out.println("File Save Error");
        }

    }

    public static int getInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Incorect argument passed. Please give correct number");
            scanner.next();
        }
        return scanner.nextInt();
    }

}

