package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncDatabase implements PersonDatabase {
    private final Map<Integer, Person> idMap = new HashMap<>();
    private final Map<String, List<Person>> nameMap = new HashMap<>();
    private final Map<String, List<Person>> addressMap = new HashMap<>();
    private final Map<String, List<Person>> phoneMap = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        if (person.name() == null || person.address() == null || person.phoneNumber() == null) {
            throw new IllegalArgumentException();
        }
        if (idMap.containsKey(person.id())) {
            throw new RuntimeException();
        }
        idMap.put(person.id(), person);
        addToMap(nameMap, person.name(), person);
        addToMap(addressMap, person.address(), person);
        addToMap(phoneMap, person.phoneNumber(), person);
    }

    @Override
    public synchronized void delete(int id) {
        Person person = idMap.get(id);
        if (person != null) {
            removeFromMap(nameMap, person.name(), person);
            removeFromMap(addressMap, person.address(), person);
            removeFromMap(phoneMap, person.phoneNumber(), person);
            idMap.remove(id);
        }
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        List<Person> list = nameMap.get(name);
        return list == null ? null : new ArrayList<>(list);
    }

    @Override
    public synchronized List<Person> findByAddress(String name) {
        List<Person> list = addressMap.get(name);
        return list == null ? null : new ArrayList<>(list);
    }

    @Override
    public synchronized List<Person> findByPhone(String name) {
        List<Person> list = phoneMap.get(name);
        return list == null ? null : new ArrayList<>(list);
    }

    private void addToMap(Map<String, List<Person>> map, String key, Person value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    private void removeFromMap(Map<String, List<Person>> map, String key, Person person) {
        List<Person> personList = map.get(key);
        if (personList != null) {
            personList.remove(person);
            if (personList.isEmpty()) {
                map.remove(key);
            }
        }
    }
}
