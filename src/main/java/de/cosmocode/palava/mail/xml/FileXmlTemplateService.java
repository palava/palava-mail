/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package de.cosmocode.palava.mail.xml;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import de.cosmocode.palava.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
class FileXmlTemplateService implements MailService, Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(FileXmlTemplateService.class);

    private static final URL XSD = FileXmlTemplateService.class.getResource("/palava-mails.xsd");

    private final DocumentBuilderFactory builderFactory;
    private final SchemaFactory schemaFactory;

    private final Schema schema;

    private File directory;

    private Map<String, XmlMailTemplate> templates = Maps.newHashMap();


    @Inject
    public FileXmlTemplateService(@Named(FileXmlTemplateServiceConfig.DIRECTORY) File directory) {
        this.directory = directory;

        schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        try {
            schema = schemaFactory.newSchema(XSD);
        } catch (SAXException e) {
            throw new IllegalArgumentException(e);
        }

        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setSchema(schema);
    }

    @Override
    public void initialize() throws LifecycleException {
        LOG.debug("Loading mail templates from {}", directory);
        loadFileRecursivly(directory);
    }

    private void loadFileRecursivly(File dir) {
        for (String name: dir.list()) {
            File file = new File(name).getAbsoluteFile();
            if (file.isFile()) {
                if (file.toString().endsWith(".xml")) {
                    loadFile(file);
                }
            } else if (file.isDirectory()) {
                loadFileRecursivly(file);
            }
        }
    }

    private void loadFile(File file) {
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();

            NodeList mails = doc.getChildNodes();
            for (int childId = 0; childId < mails.getLength(); childId++) {
                Node mail = mails.item(childId);
                String mailId = mail.getAttributes().getNamedItem("id").getNodeValue();

                XmlMailTemplate template;
                if (templates.containsKey(mailId)) {
                    template = templates.get(mailId);
                } else {
                    template = new XmlMailTemplate(mailId);
                    templates.put(mailId, template);
                }

                template.parseXML(mail);
            }

        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        } catch (SAXException e) {
            throw new IllegalArgumentException("Malformed XML detected in " + file, e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read XML in " + file, e);
        }
    }

    @Override
    public Message prepare(String name, Locale locale, Session session, Map<? extends CharSequence, ? extends Object> templateVariables) throws MessagingException {
        if (!templates.containsKey(name)) {
            throw new IllegalArgumentException("Mail template '" + name + "' not found.");
        }
        LocalizedXmlMailTemplate template = templates.get(name).createLocalized(locale);

        MimeMessage message = new MimeMessage(session);
        message.setSubject(template.getSubject());
        message.setSentDate(new Date());

        // TODO fixme, create multipart email with attachments instead of simple text
        message.setText(template.getBody());

        return message;
    }

    @Override
    public Message prepare(String name, Locale locale, Session session) throws MessagingException {
        return prepare(name, locale, session, (Map<? extends CharSequence, ? extends Object>)Maps.newHashMap());
    }
}