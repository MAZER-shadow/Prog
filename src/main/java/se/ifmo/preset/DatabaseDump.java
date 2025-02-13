package se.ifmo.preset;

import lombok.Getter;
import lombok.Setter;
import se.ifmo.data.DatabaseMetaData;
import se.ifmo.entity.LabWork;

import java.util.List;
@Getter
@Setter
public class DatabaseDump {
    private DatabaseMetaData databaseMetaData;
    private List<LabWork> listOfLabWork;

    public DatabaseDump(DatabaseMetaData databaseMetaData, List<LabWork> listOfLabWork) {
        this.databaseMetaData = databaseMetaData;
        this.listOfLabWork = listOfLabWork;
    }
}