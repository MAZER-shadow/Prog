package ifmo.se.response;

import ifmo.se.entity.LabWork;
import lombok.Getter;

@Getter
public class LabWorkResponse extends AbstractResponse {
    private final LabWork work;

    public LabWorkResponse(boolean status, String message, LabWork work) {
        super(status, message);
        this.work = work;
    }
}
