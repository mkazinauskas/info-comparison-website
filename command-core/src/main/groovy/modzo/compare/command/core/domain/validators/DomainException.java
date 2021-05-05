package modzo.compare.command.core.domain.validators;

public class DomainException extends RuntimeException{

    private final String id;

    private final String message;

    public DomainException(String id, String message) {
        super(message);
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
