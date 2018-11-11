package de.ahofbauer.quicktipgenerator.domain;

/**
 * A repository for MisfortuneNumbers. May have different implementations based on the technique like filesystem or DB.
 * The repository only holds one MisfortuneNumbers object at a time.
 */
public interface MisfortuneNumberRepository {

    /**
     * Stores the given MisfortuneNumbers object. Will overwrite the existing one if there is already one saved.
     */
    void save(MisfortuneNumbers misfortuneNumbers);

    /**
     * Will load the stored MisfortuneNumbers object if there is one. Will return a new empty one if there is not saved
     * any yet.
     */
    MisfortuneNumbers load();

    /**
     * Will remove the existing MisfortuneNumbers object if there is any. The next call of <code>load</code> will return
     * an empty MisfortuneNumbers object.
     */
    void delete();

}
