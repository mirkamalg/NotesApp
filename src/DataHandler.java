import com.jfoenix.controls.JFXListView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class DataHandler {

    private static final HashMap<String, Note> notes = new HashMap<>();

    @Contract("_, _ -> param1")
    public static @NotNull JFXListView<String> addToListView(@NotNull JFXListView<String> listView, Note newNote) {

        notes.put(newNote.getHeader(), newNote);
        listView.getItems().add(newNote.getHeader());
        return listView;
    }

    public static void insertToListView(@NotNull JFXListView<String> listView, Note newNote, int index) {
        notes.put(newNote.getHeader(), newNote);
        listView.getItems().add(index, newNote.getHeader());
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
