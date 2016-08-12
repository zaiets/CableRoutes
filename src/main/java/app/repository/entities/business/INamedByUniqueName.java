package app.repository.entities.business;

/**
 * This is interface for an unique-named entities
 * Every entity marked by this interface must have
 * at least one unique String name (KKS/kksName/uniqueName - as usual)
 */
public interface INamedByUniqueName {
    String getUniqueName();
}
