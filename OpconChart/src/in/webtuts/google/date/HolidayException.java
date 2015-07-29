package in.webtuts.google.date;

public class HolidayException extends Exception {

    private static final long serialVersionUID = 1L;

    public HolidayException() {
        super();
    }

    public HolidayException(String message) {
        super(message);
    }

    public HolidayException(String message, Throwable t) {
        super(message, t);
    }

    public HolidayException(Throwable t) {
        super(t);
    }

}