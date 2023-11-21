package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDatabase implements PersonDatabase {
    private final Map<Integer, Person> idMap = new HashMap<>();
    private final Map<String, List<Person>> nameMap = new HashMap<>();
    private final Map<String, List<Person>> addressMap = new HashMap<>();
    private final Map<String, List<Person>> phoneMap = new HashMap<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        readWriteLock.writeLock().lock();
        try {
            if (idMap.containsKey(person.id())) {
                throw new RuntimeException();
            }
            if (person.name() == null || person.address() == null || person.phoneNumber() == null) {
                throw new IllegalArgumentException();
            }
            idMap.put(person.id(), person);
            addToMap(nameMap, person.name(), person);
            addToMap(addressMap, person.address(), person);
            addToMap(phoneMap, person.phoneNumber(), person);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        readWriteLock.writeLock().lock();
        try {
            Person person = idMap.get(id);
            if (person != null) {
                removeFromMap(nameMap, person.name(), person);
                removeFromMap(addressMap, person.address(), person);
                removeFromMap(phoneMap, person.phoneNumber(), person);
                idMap.remove(id);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        readWriteLock.readLock().lock();
        try {
            List<Person> list = nameMap.get(name);
            return list == null ? null : new ArrayList<>(list);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String name) {
        readWriteLock.readLock().lock();
        try {
            List<Person> list = addressMap.get(name);
            return list == null ? null : new ArrayList<>(list);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String name) {
        readWriteLock.readLock().lock();
        try {
            List<Person> list = phoneMap.get(name);
            return list == null ? null : new ArrayList<>(list);
        } finally {
            readWriteLock.readLock().unlock();
        }
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
