package de.cosmocode.palava.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import de.cosmocode.palava.services.mail.TemplateDescriptor;

/**
 * 
 *
 * @since 2.0
 * @author Willi Schoenborn
 */
public interface EmailService {

    MimeMessage send(TemplateDescriptor descriptor, Map<? extends CharSequence, ? extends Object> data) throws MailException;
    
    MimeMessage send(TemplateDescriptor descriptor, Map<? extends CharSequence, ? extends Object> data, Locale locale) 
        throws MailException;
    
}
