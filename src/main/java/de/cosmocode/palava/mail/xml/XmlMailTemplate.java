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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import de.cosmocode.palava.mail.templating.LocalizedMailTemplate;
import de.cosmocode.palava.mail.templating.MailAttachmentTemplate;
import de.cosmocode.palava.mail.templating.MailTemplate;
import de.cosmocode.palava.mail.templating.TemplateEngine;
import de.cosmocode.palava.mail.xml.gen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Tobias Sarnowski
 */
public class XmlMailTemplate implements MailTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(XmlMailTemplate.class);

    private final String mailName;

    private Class<? extends TemplateEngine> defaultTemplateEngine;
    private String defaultSubject;
    private String defaultBody;
    private final Map<String,String> snippets = Maps.newHashMap();
    private final Set<MailAttachmentTemplate> defaultEmbedded = Sets.newHashSet();
    private final Set<MailAttachmentTemplate> defaultAttachments = Sets.newHashSet();

    private final Map<Locale,Class<? extends TemplateEngine>> templateEngine = Maps.newHashMap();
    private final Map<Locale,String> subject = Maps.newHashMap();
    private final Map<Locale,String> body = Maps.newHashMap();
    private final Map<Locale,Set<MailAttachmentTemplate>> embedded = Maps.newHashMap();
    private final Map<Locale,Set<MailAttachmentTemplate>> attachments = Maps.newHashMap();


    public XmlMailTemplate(String mailName) {
        this.mailName = mailName;
    }

    @Override
    public String getName() {
        return mailName;
    }

    public void addDefinitions(MailType mail) {
        // default settings
        if (mail.getDefault() != null) {
            DefaultType defaultTemplate = mail.getDefault();

            if (defaultTemplate.getTemplateEngine() != null) {
                try {
                    defaultTemplateEngine = (Class<? extends TemplateEngine>)Class.forName(defaultTemplate.getTemplateEngine());
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if (defaultTemplate.getSubject() != null) {
                defaultSubject = defaultTemplate.getSubject();
            }
            if (defaultTemplate.getBody() != null) {
                defaultBody = defaultTemplate.getBody();
            }

            for (SnippetType snippet: defaultTemplate.getSnippet()) {
                snippets.put(snippet.getName(), snippet.getValue());
            }
            for (AttachmentType attachment: defaultTemplate.getEmbedded()) {
                defaultEmbedded.add(new XmlMailAttachment(attachment));
            }
            for (AttachmentType attachment: defaultTemplate.getAttachment()) {
                defaultAttachments.add(new XmlMailAttachment(attachment));
            }
        }

        for (LocalizedType localized: mail.getLocalized()) {
            Locale locale = new Locale(localized.getLocale());

            if (localized.getTemplateEngine() != null) {
                Class<? extends TemplateEngine> cls;
                try {
                    cls = (Class<? extends TemplateEngine>) Class.forName(localized.getTemplateEngine());
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
                templateEngine.put(locale, cls);
            }
            if (localized.getSubject() != null) {
                subject.put(locale, localized.getSubject());
            }
            if (localized.getBody() != null) {
                body.put(locale, localized.getBody());
            }

            Set<MailAttachmentTemplate> embedded = this.embedded.get(locale);
            if (embedded == null) {
                embedded = Sets.newHashSet();
                this.embedded.put(locale, embedded);
            }
            for (AttachmentType attachment: localized.getEmbedded()) {
                embedded.add(new XmlMailAttachment(attachment));
            }

            Set<MailAttachmentTemplate> attachments = this.attachments.get(locale);
            if (attachments == null) {
                attachments = Sets.newHashSet();
                this.attachments.put(locale, attachments);
            }
            for (AttachmentType attachment: localized.getEmbedded()) {
                attachments.add(new XmlMailAttachment(attachment));
            }
        }
    }

    @Override
    public LocalizedMailTemplate createLocalized(final Locale locale) {
        return new LocalizedMailTemplate() {
            @Override
            public String getName() {
                return XmlMailTemplate.this.getName();
            }

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
            public Set<MailAttachmentTemplate> getEmbedded() {
                Set<MailAttachmentTemplate> embedded = Sets.newHashSet(defaultEmbedded);
                if (XmlMailTemplate.this.embedded.containsKey(locale)) {
                    embedded.addAll(XmlMailTemplate.this.embedded.get(locale));
                }
                return embedded;
            }

            @Override
            public Set<MailAttachmentTemplate> getAttachments() {
                Set<MailAttachmentTemplate> attachments = Sets.newHashSet(defaultAttachments);
                if (XmlMailTemplate.this.attachments.containsKey(locale)) {
                    attachments.addAll(XmlMailTemplate.this.attachments.get(locale));
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