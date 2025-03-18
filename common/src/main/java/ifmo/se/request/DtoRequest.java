package ifmo.se.request;

import ifmo.se.entity.LabWorkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DtoRequest extends AbstractRequest implements Serializable {
    private final LabWorkDto labWorkDto;


    public DtoRequest(String name, LabWorkDto labWorkDto) {
        super(name);
        this.labWorkDto = labWorkDto;
    }
}
