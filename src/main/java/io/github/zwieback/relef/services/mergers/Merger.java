package io.github.zwieback.relef.services.mergers;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Merger<T, ID> {

    /**
     * Merge existed and parsed entities and return merged entities.
     *
     * @param existedEntities entities from the database
     * @param parsedEntities  parsed entities
     * @return merged entities
     */
    public List<T> merge(List<T> existedEntities, List<T> parsedEntities) {
        if (existedEntities.isEmpty()) {
            return parsedEntities;
        }
        return parsedEntities.stream()
                .map(parsedEntity -> {
                    Optional<T> entity = findById(existedEntities, getId(parsedEntity));
                    return entity.map(existedEntity -> merge(existedEntity, parsedEntity)).orElse(parsedEntity);
                })
                .collect(Collectors.toList());
    }

    /**
     * Merge existed and parsed entity and return merged entity.
     *
     * @param existedEntity entity from the database
     * @param parsedEntity  parsed entity
     * @return merged entity
     */
    public abstract T merge(@NotNull T existedEntity, @NotNull T parsedEntity);

    /**
     * Get id of entity.
     *
     * @param entity existed entity
     * @return id of entity
     */
    @NotNull
    abstract ID getId(T entity);

    /**
     * Find entity from existed entities by id.
     *
     * @param existedEntities entities from the database
     * @param id              id of the entity to be found
     * @return optional entity with same id
     */
    private Optional<T> findById(List<T> existedEntities, @NotNull ID id) {
        return existedEntities.stream().filter(entity -> id.equals(getId(entity))).findAny();
    }

    /**
     * Is need to merge objects?
     *
     * @param destination field of existed entity
     * @param source      same field of parsed entity
     * @return {@code true} if need to merge, {@code false} otherwise
     */
    static boolean needToMerge(Object destination, Object source) {
        return source != null && (destination == null || source instanceof String || !destination.equals(source));
    }
}
