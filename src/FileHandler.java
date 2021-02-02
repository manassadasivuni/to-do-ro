import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHandler {

    /* Checks if folder or file exists */
    public static Boolean directoryExists(String folder) {
        Path path = Paths.get("tasks/" + folder);
        return Files.exists(path);
    }

    public static Boolean fileExists(String folder, String name) {
        Path path = Paths.get("tasks/" + folder + "/" + name + ".json");
        return Files.exists(path);
    }

    /* Creates or deletes files and folders */
    public static void createFolder(String folderName) {
        Path path = Paths.get("tasks/" + folderName);

        if (directoryExists(folderName)) {
            System.out.println("Folder already exists");
        } else {
            try {
                Files.createDirectories(path);
                System.out.println("Directory created");
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public static void deleteFolder(String folderName) {
        Path path = Paths.get("tasks/" + folderName);

        if (!directoryExists(folderName)) {
            System.out.println("Folder does not exist");
            return;
        }

        try {

            Files.walkFileTree(path,
                    new SimpleFileVisitor<>() {

                        // delete directories or folders
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            System.out.printf("Directory is deleted : %s%n", dir);
                            return FileVisitResult.CONTINUE;
                        }

                        // delete files
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            System.out.printf("File is deleted : %s%n", file);
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static void createFile(String folder, String name, String link, String notes) {
        if (!directoryExists(folder)) {
            createFolder(folder);
        }

        if (fileExists(folder, name)) {
            System.out.println("File already exists");
            return;
        }

        JSONObject jo = new JSONObject();
        jo.put("folder", folder);
        jo.put("name", name);
        jo.put("link", link);
        jo.put("notes", notes);
        jo.put("status", 0);


        PrintWriter pw = null;
        try {
            pw = new PrintWriter("tasks/" + folder + "/" + name + ".json");
            System.out.println("File created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();
    }

    public static void deleteFile(String folder, String name) {
        Path path = Paths.get("tasks/" + folder + "/" + name + ".json");
        try {
            Files.delete(path);
            System.out.println("File deleted: " + path);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    /* Reads and edits file contents after creation */
    public static String readFile(String folder, String name, String property) {

        if (!fileExists(folder, name)) {
            return null;
        }

        try {
            Object obj = new JSONParser().parse(new FileReader("tasks/" + folder + "/" + name + ".json"));
            JSONObject jo = (JSONObject) obj;

            String link = (String) jo.get("link");
            String notes = (String) jo.get("notes");
            int status = (int) jo.get("status");

            switch (property) {
                case "link":
                    return link;
                case "notes":
                    return notes;
                case "status":
                    return String.valueOf(status);
                default:
                    return ("Link: " + link + ", Notes: " + notes + ", Status: " + status);
            }

        } catch (IOException e) {
            System.err.println(e);
        } catch (ParseException e) {
            System.err.println(e);
        }

        return null;

    }

}
