package ilya.example.TaxiTask.log;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Log {

    private String message;

    public Log(String message) {
        this.message = message;
    }

    public Log() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
