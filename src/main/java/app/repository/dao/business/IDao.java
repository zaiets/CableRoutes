package app.repository.dao.business;

import app.repository.entities.business.INamedByUniqueName;

import java.util.List;

public interface IDao<T extends INamedByUniqueName> {
    boolean create(T t);
    boolean createOrUpdate(T t);
    T read(String uniqueName);
    boolean update(String uniqueName, T t);
    boolean delete(String uniqueName);
    List<T> getAll();
}
