package se.ifmo.data;

import se.ifmo.entity.Person;
import se.ifmo.io.ReaderFile;
import se.ifmo.io.WriterJson;

import java.util.ArrayList;
import java.util.List;

public class Database implements Dao {
    private final List<Person> listOfPerson;

    public Database(List<Person> listOfPerson) {
        this.listOfPerson = new ArrayList<>(listOfPerson);
    }

    @Override
    public List<Person> getAll() {
        return new ArrayList<>(listOfPerson);
    }

    @Override
    public Person create(String name) {
        return new Person(name);
    }

    @Override
    public List<Person> read(String path) {
        ReaderFile<Person> readerFile = new ReaderFile<>(Person.class);
        return readerFile.readJson(path);
    }

    @Override
    public void delete(Person person) {
        listOfPerson.remove(person);
    }

    @Override
    public void add(Person person) {
        listOfPerson.add(person);
    }

    @Override
    public void write() {
        WriterJson<Person> writerJson = new WriterJson<>();
        writerJson.writeToJson(listOfPerson, "src/main/resources/FileWithPerson.json");
    }
}