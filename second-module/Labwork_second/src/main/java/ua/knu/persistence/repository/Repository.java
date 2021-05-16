package ua.knu.persistence.repository;

import java.util.Collection;

public interface Repository<T> extends AutoCloseable {

    Collection<T> findAll();

    T findById(int id);

    boolean save(T item);

    boolean update(T item);

    boolean removeById(int id);
}
