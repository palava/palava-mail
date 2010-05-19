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


package de.cosmocode.palava.mail.xml.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.cosmocode.palava.mail.xml.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Mails_QNAME = new QName("http://palava.cosmocode.de/palava-mail/2.0", "mails");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.cosmocode.palava.mail.xml.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MailsType }
     * 
     */
    public MailsType createMailsType() {
        return new MailsType();
    }

    /**
     * Create an instance of {@link SnippetType }
     * 
     */
    public SnippetType createSnippetType() {
        return new SnippetType();
    }

    /**
     * Create an instance of {@link AttachmentType }
     * 
     */
    public AttachmentType createAttachmentType() {
        return new AttachmentType();
    }

    /**
     * Create an instance of {@link MailType }
     * 
     */
    public MailType createMailType() {
        return new MailType();
    }

    /**
     * Create an instance of {@link DefaultType }
     * 
     */
    public DefaultType createDefaultType() {
        return new DefaultType();
    }

    /**
     * Create an instance of {@link LocalizedType }
     * 
     */
    public LocalizedType createLocalizedType() {
        return new LocalizedType();
    }

    /**
     * Create an instance of {@link ConfigType }
     * 
     */
    public ConfigType createConfigType() {
        return new ConfigType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MailsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://palava.cosmocode.de/palava-mail/2.0", name = "mails")
    public JAXBElement<MailsType> createMails(MailsType value) {
        return new JAXBElement<MailsType>(_Mails_QNAME, MailsType.class, null, value);
    }

}
