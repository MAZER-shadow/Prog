package ifmo.se.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractRequest {
    protected String nameCommand;

    public AbstractRequest(String nameCommand) {
        this.nameCommand = nameCommand;
    }

}
