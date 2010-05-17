package de.cosmocode.palava.mail.xml;

import com.google.common.collect.Maps;
import de.cosmocode.palava.mail.attachments.MailAttachment;
import de.cosmocode.palava.mail.templating.TemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public class XmlMailTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(XmlMailTemplate.class);

    private final String mailId;

    private Class<? extends TemplateEngine> defaultTemplateEngine;
    private String defaultSubject;
    private String defaultBody;
    private final Map<String,String> snippets = Maps.newHashMap();
    private final Map<String,XmlMailAttachment> defaultAttachments = Maps.newHashMap();

    private final Map<Locale,Class<? extends TemplateEngine>> templateEngine = Maps.newHashMap();
    private final Map<Locale,String> subject = Maps.newHashMap();
    private final Map<Locale,String> body = Maps.newHashMap();
    private final Map<Locale,Map<String,XmlMailAttachment>> attachments = Maps.newHashMap();


    public XmlMailTemplate(String mailId) {
        this.mailId = mailId;
    }

    public void parseXML(Node mail) {
        NodeList rootElements = mail.getChildNodes();
        for (int rootId = 0; rootId < rootElements.getLength(); rootId++) {
            Node root = rootElements.item(rootId);

            if ("default".equals(root.getNodeName())) {
                
            } else if ("localized".equals(root.getNodeName())) {

            }
        }
    }

    public LocalizedXmlMailTemplate createLocalized(final Locale locale) {
        return new LocalizedXmlMailTemplate() {
            @Override
            public String getSubject() {
                if (subject.containsKey(locale)) {
                    return subject.get(locale);
                } else if (defaultSubject == null) {
                    throw new IllegalStateException("No localized and no default subject configured");
                } else {
                    return defaultSubject;
                }
            }

            @Override
            public String getBody() {
                if (body.containsKey(locale)) {
                    return body.get(locale);
                } else if (defaultBody == null) {
                    throw new IllegalStateException("No localized and no default body configured");
                } else {
                    return defaultBody;
                }
            }

            @Override
            public Map<String, String> getSnippets() {
                return snippets;
            }

            @Override
            public Map<String,XmlMailAttachment> getAttachments() {
                Map<String,XmlMailAttachment> attachments = Maps.newHashMap(defaultAttachments);
                if (XmlMailTemplate.this.attachments.containsKey(locale)) {
                    attachments.putAll(XmlMailTemplate.this.attachments.get(locale));
                }
                return attachments;
            }

            @Override
            public Class<? extends TemplateEngine> getTemplateEngine() {
                if (templateEngine.containsKey(locale)) {
                    return templateEngine.get(locale);
                } else if (defaultTemplateEngine == null) {
                    throw new IllegalStateException("No localized and no default template engine configured");
                } else {
                    return defaultTemplateEngine;
                }
            }
        };
    }
}