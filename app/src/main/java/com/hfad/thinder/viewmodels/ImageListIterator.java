package com.hfad.thinder.viewmodels;


import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A custom {@link ListIterator} which allows to move between elements of a List without repeating
 * elements.
 *
 * @param <E> a generic type.
 */
public class ImageListIterator<E> implements ListIterator<E> {

  private final ArrayList<E> list;

  private int cursor = 0;

  /**
   * Constructor for {@link ImageListIterator}.
   *
   * @param list an ArrayList.
   */
  public ImageListIterator(ArrayList<E> list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return cursor + 1 < list.size();
  }

  @Override
  public E next() {
    if (hasNext()) {
      cursor++;
    }
    return list.get(cursor);
  }

  public E current() {
    return list.get(cursor);
  }

  @Override
  public E previous() {
    if (hasPrevious()) {
      cursor--;
    }
    return this.list.get(cursor);
  }

  @Override
  public int nextIndex() {
    if (hasNext()) {
      return cursor++;
    }
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
