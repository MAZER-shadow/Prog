package se.ifmo;


import se.ifmo.entity.Person;
import se.ifmo.io.ReaderFile;
import se.ifmo.io.WriterJson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WriterJson<Person> writerJson = new WriterJson<>();
        List<Person> people = new ArrayList<>();
        people.add(new Person("David"));
        people.add(new Person("Mark"));
        writerJson.writeToJson(people, "src\\main\\resources\\FileWithPerson.json");
        ReaderFile<Person> reader = new ReaderFile<>(Person.class);
        List<Person> personList = reader.readJson("FileWithPerson.json");
        System.out.println(personList.get(0).getName());
    }
}




