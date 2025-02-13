package se.ifmo.data;

import se.ifmo.entity.LabWork;
import se.ifmo.preset.DatabaseDump;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database implements Dao<LabWork> {
    private final List<LabWork> listOfLabWork;
    private DatabaseMetaData databaseMetaData;

    public Database(DatabaseDump databaseDump) {
        this.listOfLabWork = new ArrayList<>(databaseDump.getListOfLabWork());
        this.databaseMetaData = databaseDump.getDatabaseMetaData();
    }

    @Override
    public List<LabWork> getAll() {
        return listOfLabWork;
    }

    @Override
    public void clear() {
        listOfLabWork.clear();
    }

    @Override
    public boolean delete(LabWork person) {
        return listOfLabWork.remove(person);
    }

    @Override
    public void add(LabWork labwork) {
        long id = 0;
        for (LabWork labWork : listOfLabWork) {
            if (labWork.getId() > id) {
                id = labWork.getId();
            }
        }
        labwork.setId(++id);
        labwork.setCreationDate(LocalDate.now());
        listOfLabWork.add(labwork);
    }

    @Override
    public void updateById(int id, LabWork labWork) {
        //сделать схему oldItem.set(newItem.get)
    }

    @Override
    public DatabaseMetaData getDatabaseMetaData() {
        databaseMetaData.setSize(listOfLabWork.size());
        return databaseMetaData;
    }
}