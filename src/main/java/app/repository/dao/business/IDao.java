package app.repository.dao.business;

import java.util.List;

public interface IDao<T> {
    boolean create(T t);
    boolean createOrUpdate(T t);
    T read(String uniqueName);
    boolean update(String uniqueName, T t);
    boolean delete(String uniqueName);
    List<T> getAll();
}
