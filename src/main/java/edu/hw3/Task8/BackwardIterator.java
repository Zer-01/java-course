package edu.hw3.Task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BackwardIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int currentIndex;

    public BackwardIterator(Collection<T> collection) {
        this.list = List.copyOf(collection);
        this.currentIndex = list.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public T next() {
        if (hasNext()) {
            T elem = list.get(currentIndex);
            currentIndex--;
            return elem;
        } else {
            throw new NoSuchElementException();
        }
    }
}
