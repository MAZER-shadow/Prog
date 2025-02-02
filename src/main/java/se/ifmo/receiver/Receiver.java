package se.ifmo.receiver;

import se.ifmo.data.Database;
import java.util.List;

public class Receiver<T> {
    private final Database<T> db;

    public Receiver(Database<T> db) {
        this.db = db;
    }

    public void add(T person) {
        db.add(person);
    }

    public void clear() {
        db.clear();
    }

    public List<T> getAll() {
        return db.getAll();
    }

    public void show() {
        for (Object person: db.getAll()) {
            System.out.println(person);
        }
    }
}