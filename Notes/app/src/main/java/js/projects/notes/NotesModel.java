package js.projects.notes;

public class NotesModel {
    String title;
    String description;
    String timestamp;
    String id;

    public NotesModel( String id, String title, String description, String timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this. id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getId(){ return id;}
}
