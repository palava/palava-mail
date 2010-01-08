package de.cosmocode.palava.services.mail;

/**
 * A {@link TemplateDescriptor} is used to describe {@link Template}s
 * by assigning unique identifiers.
 *
 * @author Willi Schoenborn
 */
public interface TemplateDescriptor {

    /**
     * Provide the template's identifier.
     * 
     * @return the identifier of the template being created.
     */
    String getIdentifier();
    
}
