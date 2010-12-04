/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.mail.xml;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import de.cosmocode.palava.mail.MailService;
import de.cosmocode.palava.mail.templating.LocalizedMailTemplate;
import de.cosmocode.palava.mail.templating.MailTemplate;
import de.cosmocode.palava.mail.templating.TemplateEngine;
import de.cosmocode.palava.mail.templating.TemplateException;
import de.cosmocode.palava.mail.xml.gen.MailType;
import de.cosmocode.palava.mail.xml.gen.MailsType;
import de.cosmocode.palava.mail.xml.gen.ObjectFactory;

/**
 * A xml/file based {@link MailService}.
 * 
 * @author Tobias Sarnowski
 */
class FileXmlTemplateService implements MailService, Initializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(FileXmlTemplateService.class);

    private static final URL XSD = FileXmlTemplateService.class.getResource("/palava-mails.xsd");

    private Unmarshaller unmarshaller;

    private File directory;

    private Map<String, XmlMailTemplate> templates = Maps.newHashMap();

    @Inject
    public FileXmlTemplateService(@Named(FileXmlTemplateServiceConfig.DIRECTORY) File directory) {
        this.directory = directory;

        final Schema schema;
        
        try {
            final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(XSD);
        } catch (SAXException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
            unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);
        } catch (JAXBException e) {
            LOG.error("An exception occured!", e);
        }
    }

    @Override
    public void initialize() throws LifecycleException {
        LOG.debug("Loading mail templates from {}", directory);
        loadFileRecursivly(directory);
        LOG.info("Loaded {} mail templates.", templates.size());
    }

    private void loadFileRecursivly(File dir) {
        LOG.trace("Looking in directory {}...", dir);
        for (String name : dir.list()) {
            final File file = new File(dir, name).getAbsoluteFile();
            if (file.isFile()) {
                if (file.toString().endsWith(".xml")) {
                    loadFile(file);
                } else {
                    LOG.trace("File {} is no Mail Template", file);
                }
            } else if (file.isDirectory()) {
                loadFileRecursivly(file);
            } else {
                LOG.warn("{} is not a directory or file?!", file);
            }
        }
    }

    private void loadFile(File file) {
        LOG.debug("Loading Mail template {}", file);
        try {
            @SuppressWarnings("unchecked")
            final JAXBElement<MailsType> element = (JAXBElement<MailsType>) unmarshaller.unmarshal(file);
            final MailsType mails = element.getValue();

            for (MailType mail : mails.getMail()) {
                XmlMailTemplate template;
                if (templates.containsKey(mail.getName())) {
                    template = templates.get(mail.getName());
                } else {
                    template = new XmlMailTemplate(mail.getName());
                    templates.put(mail.getName(), template);
                }

                template.addDefinitions(mail);
            }

        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Message prepare(String name, Locale locale, Session session, 
            Map<String, ? extends Object> templateVariables) throws MessagingException {
        
        if (!templates.containsKey(name)) {
            throw new IllegalArgumentException("Mail template '" + name + "' not found.");
        }
        LocalizedMailTemplate template = templates.get(name).createLocalized(locale);

        if (template.getTemplateEngine() != null) {
            TemplateEngine tplEngine;
            try {
                tplEngine = template.getTemplateEngine().newInstance();
            } catch (InstantiationException e) {
                throw new MessagingException("Cannot load TemplateEngine " + template.getTemplateEngine(), e);
            } catch (IllegalAccessException e) {
                throw new MessagingException("Cannot access TemplateEngine " + template.getTemplateEngine(), e);
            }
            try {
                template = tplEngine.generate(template, templateVariables);
            } catch (TemplateException e) {
                throw new MessagingException("Cannot parse Template", e);
            }
        }

        final MimeMessage message = new MimeMessage(session);
        message.setSubject(template.getSubject());
        message.setSentDate(new Date());
        
        if (template.getAttachments().size() == 0 && template.getEmbedded().size() == 0) {
            message.setText(template.getBody());
        } else  {
            throw new UnsupportedOperationException("cannot send attachments or use embedded binaries");
        }

        return message;
    }

    @Override
    public Message prepare(String name, Locale locale, Session session) throws MessagingException {
        return prepare(name, locale, session, Collections.<String, Object>emptyMap());
    }

    @Override
    public Collection<? extends MailTemplate> getAllTemplates() {
        return Collections.unmodifiableCollection(templates.values());
    }
    
}
