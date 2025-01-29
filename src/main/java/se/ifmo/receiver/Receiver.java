package se.ifmo.receiver;

import se.ifmo.data.Database;
import se.ifmo.entity.Person;
import java.util.ArrayList;
import java.util.List;

public class Receiver {
    private static Database database = new Database(new ArrayList<Person>(1));

    public void add(String name) {
        database.add(database.create(name));
    }

    public void clear() {
        List<Person> list = database.getAll();
        list.clear();
    }

    public void show() {
        for (Person person: database.getAll()) {
            System.out.println(person);
        }
    }
}