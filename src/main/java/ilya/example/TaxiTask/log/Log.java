package ilya.example.TaxiTask.log;

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
