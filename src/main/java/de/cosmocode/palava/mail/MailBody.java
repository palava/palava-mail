package de.cosmocode.palava.mail;

import java.util.Set;

import de.cosmocode.palava.services.mail.ContentType;

public interface MailBody {

    ContentType getContentType();
    
    String getSubject();
    
    String getSender();

    Set<Person> getReceiver();
    
    Set<Person> getCarbonCopy();
    
    Set<Person> getBlindCarbonCopy();
    
    Set<Person> getReplyTo();
    
    String getHtmlMessage();
    
    String getTextMessage();
    
    Set<Binary> getEmbeddedContent();
    
    Set<Binary> getAttachments();
    
}
