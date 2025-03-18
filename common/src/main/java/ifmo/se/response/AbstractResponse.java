package ifmo.se.response;

import lombok.Getter;

@Getter
public class AbstractResponse {
    private final boolean status;
    private final String message;

    public AbstractResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
