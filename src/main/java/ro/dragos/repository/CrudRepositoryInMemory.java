package ro.dragos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import ro.dragos.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CrudRepositoryInMemory<T extends BaseModel<ID>, ID> implements CrudRepository<T, ID> {

    private final List<T> entities;

    public CrudRepositoryInMemory() {
        entities = new ArrayList<>();
    }

    @Override
    public <S extends T> S save(S entity) {
        Optional<T> entityWithSameId = findById(entity.getId());
        entityWithSameId.ifPresent(this::delete);
        entities.add(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return entities.stream().filter(entity -> entity.getId().equals(id)).findAny();
    }

    @Override
    public Iterable<T> findAll() {
        return entities;
    }

    @Override
    public long count() {
        return entities.size();
    }

    @Override
    public void deleteById(ID id) {
        entities.removeIf(entity -> entity.getId().equals(id));
    }

    @Override
    public void delete(T entity) {
        entities.remove(entity);
    }

    @Override
    public boolean existsById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
