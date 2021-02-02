import java.util.InputMismatchException;
import java.util.Scanner;

public class Task {
    /* ----- Variables ----- */
    private String folder;
    private String name;
    private int status; // 0: not done, 1: in progress, 2: completed
    private String link;
    private String notes;

    /* ----- Constructors ----- */
    public Task(String folder, String name) { // Basic constructor with minimum required info
        this.folder = folder;
        this.name = name;
    }

    public Task(String folder, String name, String link, String notes) { // Constructor without date and status
        this.folder = folder;
        this.name = name;
        this.link = link;
        this.notes = notes;
    }

    public Task(String folder, String name, int status, String link, String notes) { // Constructor without date
        this.folder = folder;
        this.name = name;
        this.status = status;
        this.link = link;
        this.notes = notes;
    }

    public Task() { // Empty constructor

    }

    /* ----- Methods ----- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAllTaskProperties() {
        return ("Folder: " + this.folder
                + ", Name: " + this.name
                + ", Link: " + this.link
                + ", Notes: " + this.notes
                + ", Status: " + this.status);
    }

    public void setAllTaskProperties() {
        Scanner input = new Scanner(System.in);

        System.out.println("Which folder do you want to create a task in?");
        String folder = input.nextLine();
        setFolder(folder);

        System.out.println("What is the name of the task?");
        String name = input.nextLine();
        setName(name);

        System.out.println("What is the link to the task?");
        String link = input.nextLine();
        setLink(link);

        System.out.println("What are the notes for the task?");
        String notes = input.nextLine();
        setNotes(notes);

        System.out.println("What is the status of the task (0 = not done, 1 = in progress, 2 = done");
        try {
            int status = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("That is not a valid input, the status will be set to \"not done\" by default");
            status = 0;
        }
        setStatus(status);

        input.close();
    }
}