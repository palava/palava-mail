package de.cosmocode.palava.mail.xml;

import de.cosmocode.palava.mail.attachments.MailAttachmentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.io.File;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public class XmlMailAttachment {
    private static final Logger LOG = LoggerFactory.getLogger(XmlMailAttachment.class);

    private String name;
    private Class<? extends MailAttachmentSource> source;
    private Map<String,String> configuration;

    public XmlMailAttachment(Node xml) {
    }

    public String getName() {
        return name;
    }

    public Class<? extends MailAttachmentSource> getSource() {
        return source;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }
}