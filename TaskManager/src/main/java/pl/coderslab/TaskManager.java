package pl.coderslab;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static String tasks[][];
    public static void main(String[] args) {
        showOptions();
        tasks = tasksFromFile("tasks.csv");
        System.out.println(tasks[0][2]);
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
                for (String line: Files.readAllLines(path)) {
                    row +=1;
                    columns = line.split(",").length;
                }
        } catch (IOException e) {
                System.out.println("Nie można wyświetlić pliku");
        }
        taskArray = new String [row][columns];
        try (Scanner scanner = new Scanner(path)){
            while(scanner.hasNextLine()){
              for (int i = 0; i < row; i++) {
                    String [] line = scanner.nextLine().split(",");
                    for (int j = 0; j < columns; j++ ) {
                        taskArray[i][j] = line [j];
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Nie można wyświetlić pliku");
        }
        return taskArray;
    }
    public static void optionSelect(){

    }
}

