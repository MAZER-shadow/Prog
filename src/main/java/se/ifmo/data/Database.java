package se.ifmo.data;

import se.ifmo.entity.LabWork;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database implements Dao<LabWork> {
    private final List<LabWork> listOfLabWork;
    private long maxId;

    public Database() {
        listOfLabWork = new ArrayList<>();
    }

    public Database(List<LabWork> listOfLabWork) {
        this.listOfLabWork = new ArrayList<>(listOfLabWork);
    }

    @Override
    public List<LabWork> getAll() {
        return new ArrayList<>(listOfLabWork);
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
        setMaxId(id);
        labwork.setCreationDate(LocalDate.now());
        listOfLabWork.add(labwork);
    }

    public long getMaxId() {
        return maxId;
    }

    private void setMaxId(long maxId) {
        this.maxId = maxId;
    }
}