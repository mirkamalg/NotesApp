import java.time.LocalDateTime;

public class Note {

    private String header;
    private String body;
    private LocalDateTime time;

    public Note(String header, String body, LocalDateTime time) {
        this.header = header;
        this.body = body;
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
