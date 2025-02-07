package se.ifmo.data;

import se.ifmo.entity.LabWork;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Database implements Dao<LabWork> {
    private final List<LabWork> listOfLabWork;

    public Database() {
        listOfLabWork = new ArrayList<>();
    }

    public Database(List<LabWork> listOfLabWork) {
        this.listOfLabWork = new ArrayList<LabWork>(listOfLabWork);
    }

    @Override
    public List<LabWork> getAll() {
        return new ArrayList<LabWork>(listOfLabWork);
    }

    @Override
    public void clear() {
        listOfLabWork.clear();
    }

    @Override
    public void delete(LabWork person) {
        listOfLabWork.remove(person);
    }

    @Override
    public void add(LabWork labwork) {
        AtomicLong counter = new AtomicLong();
        labwork.setId(counter.incrementAndGet());
        labwork.setCreationDate(LocalDate.now());
        listOfLabWork.add(labwork);
    }
}