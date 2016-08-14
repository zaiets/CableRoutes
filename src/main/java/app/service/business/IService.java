package app.service.business;

import java.io.Serializable;
import java.util.List;

public interface IService<T, PK extends Serializable> {
    boolean create(T t);
    boolean createOrUpdate(T t);
    T read(PK identifier);
    boolean update(PK identifier, T t);
    boolean delete(PK identifier);
    List<T> getAll();
}
