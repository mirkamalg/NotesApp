import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class DataHandler {

    private static final HashMap<String, Note> notes = new HashMap<>();

    @Contract("_, _ -> param1")
    public static void addToListView(Note newNote) {

        notes.put(newNote.getHeader(), newNote);
        MainController.items.add(newNote.getHeader());
    }

    public static void insertToListView(Note newNote, int index) {
        notes.put(newNote.getHeader(), newNote);
        MainController.items.add(index, newNote.getHeader());
    }

    public static HashMap<String, Note> getNotes() {
        return notes;
    }

    @Contract(pure = true)
    public static @NotNull String formatDate(@NotNull LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }
}
