package se.ifmo.receiver;

import se.ifmo.data.Database;
import se.ifmo.entity.LabWork;
import java.util.List;

public class Receiver {
    private final Database db;

    public Receiver(Database db) {
        this.db = db;
    }

    public void add(LabWork person) {
        db.add(person);
    }

    public void clear() {
        db.clear();
    }

    public List<LabWork> getAll() {
        return db.getAll();
    }

    public long getSize() {
        return db.getAll().size();
    }
}