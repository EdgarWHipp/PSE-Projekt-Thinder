package com.hfad.thinder.viewmodels;


import java.util.ArrayList;
import java.util.ListIterator;


public class ImageListIterator<E> implements ListIterator<E> {

    private ArrayList<E> list;

    private int cursor = 0;

    public ImageListIterator(ArrayList<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return cursor + 1 < list.size();
    }

    @Override
    public E next() {
        if (hasNext())
            cursor++;
        return list.get(cursor);
    }

    public E current() {
        return list.get(cursor);
    }

    @Override
    public E previous() {
        if (hasPrevious())
            cursor--;
        return this.list.get(cursor);
    }

    @Override
    public int nextIndex() {
        if (hasNext())
            return cursor++;
        return cursor;
    }

    @Override
    public int previousIndex() {
        if (hasPrevious()) {
            return cursor - 1;
        }
        return cursor;
    }

    @Override
    public void remove() {

    }

    @Override
    public void set(E e) {

    }

    @Override
    public void add(E e) {

    }

    @Override
    public boolean hasPrevious() {
        return cursor > 0;
    }
}
