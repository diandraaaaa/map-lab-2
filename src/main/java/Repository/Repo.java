package Repository;

import java.util.List;
import java.util.Optional;

public interface Repo<T> {
    void save(T entity);
    T findById(int id);
    void deleteById(int id);
    List<T> findAll();
}