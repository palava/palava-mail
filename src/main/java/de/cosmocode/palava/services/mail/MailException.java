package de.cosmocode.palava.services.mail;

/**
 * Indicates an error while sending an email.
 *
 * @author Willi Schoenborn
 */
public final class MailException extends Exception {

    private static final long serialVersionUID = 7997745774039875871L;
    
    public MailException(String message) {
        super(message);
    }
    
    public MailException(Throwable throwable) {
        super(throwable);
    }

    public MailException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
