package invenz.example.bijohn.dhamma.models;

public class Event {

    private String date;
    private String eventName;
    private int month;

    public Event(String date, String eventName, int month) {
        this.date = date;
        this.eventName = eventName;
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
