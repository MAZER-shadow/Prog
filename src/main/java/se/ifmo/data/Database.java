package se.ifmo.data;

import se.ifmo.io.WriterJson;
import java.util.ArrayList;
import java.util.List;
import se.ifmo.entity.Person;

public class Database<T> implements Dao<T> {
    private final List<T> listOfPerson;

    public Database() {
        listOfPerson = new ArrayList<>();
    }

    public Database(List<T> listOfPerson) {
        this.listOfPerson = new ArrayList<T>(listOfPerson);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<T>(listOfPerson);
    }

    @Override
    public void clear() {
        listOfPerson.clear();
    }

    @Override
    public void delete(T person) {
        listOfPerson.remove(person);
    }

    @Override
    public void add(T person) {
        listOfPerson.add(person);
    }

    @Override
    public void write() {
        WriterJson<Person> writerJson = new WriterJson<>();
        writerJson.writeToJson((List<Person>) listOfPerson, "src/main/resources/FileWithPerson.json");
    }
}