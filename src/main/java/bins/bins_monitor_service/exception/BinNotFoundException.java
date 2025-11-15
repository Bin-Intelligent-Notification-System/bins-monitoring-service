package bins.bins_monitor_service.exception;

public class BinNotFoundException extends RuntimeException {
    public BinNotFoundException(String message) {
        super(message);
    }
}
