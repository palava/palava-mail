package de.cosmocode.palava.services.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import de.cosmocode.palava.Service;

/**
 * A {@link Service} used to send emails.
 *
 * @author Willi Schoenborn
 */
public interface MailService extends Service {

    /**
     * Sends a message. The given {@link TemplateDescriptor} and the optional {@link Locale} are used to
     * retrieve a template which then will be filled with the given params.
     * 
     * @param descriptor the descriptor identifying the template
     * @param locale the optional locale
     * @param params the concrete and dynamic values for the template 
     * @return a {@link MimeMessage} which represents the actual mail message being sent
     * @throws NullPointerException if descriptor or params is null
     * @throws MailException if sending failed
     */
    MimeMessage send(TemplateDescriptor descriptor, Locale locale, Map<String, ?> params) throws MailException;
    
}
