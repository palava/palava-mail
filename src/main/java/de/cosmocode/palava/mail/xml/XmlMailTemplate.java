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

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import de.cosmocode.commons.reflect.Reflection;
import de.cosmocode.palava.mail.templating.LocalizedMailTemplate;
import de.cosmocode.palava.mail.templating.MailAttachmentTemplate;
import de.cosmocode.palava.mail.templating.MailTemplate;
import de.cosmocode.palava.mail.templating.TemplateEngine;
import de.cosmocode.palava.mail.xml.gen.AttachmentType;
import de.cosmocode.palava.mail.xml.gen.DefaultType;
import de.cosmocode.palava.mail.xml.gen.LocalizedType;
import de.cosmocode.palava.mail.xml.gen.MailType;
import de.cosmocode.palava.mail.xml.gen.SnippetType;

/**
 * A xml based {@link MailTemplate}.
 * 
 * @author Tobias Sarnowski
 */
public class XmlMailTemplate implements MailTemplate {

    private final String mailName;

    private Class<? extends TemplateEngine> defaultTemplateEngine;
    private String defaultSubject;
    private String defaultBody;
    private final Map<String, String> snippets = Maps.newHashMap();
    private final Set<MailAttachmentTemplate> defaultEmbedded = Sets.newHashSet();
    private final Set<MailAttachmentTemplate> defaultAttachments = Sets.newHashSet();

    private final Map<Locale, Class<? extends TemplateEngine>> templateEngine = Maps.newHashMap();
    private final Map<Locale, String> subject = Maps.newHashMap();
    private final Map<Locale, String> body = Maps.newHashMap();
    private final Map<Locale, Set<MailAttachmentTemplate>> embedded = Maps.newHashMap();
    private final Map<Locale, Set<MailAttachmentTemplate>> attachments = Maps.newHashMap();


    public XmlMailTemplate(String mailName) {
        this.mailName = mailName;
    }

    @Override
    public String getName() {
        return mailName;
    }

    /**
     * Adds teh {@link MailType} definition to this template.
     * 
     * @param mail the mail type definition
     */
    /* CHECKSTYLE:OFF */
    void addDefinitions(MailType mail) {
    /* CHECKSTYLE:ON */
        // default settings
        if (mail.getDefault() != null) {
            final DefaultType defaultTemplate = mail.getDefault();

            if (defaultTemplate.getTemplateEngine() != null) {
                try {
                    final String className = defaultTemplate.getTemplateEngine();
                    defaultTemplateEngine = Reflection.forName(className).asSubclass(TemplateEngine.class);
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

            for (SnippetType snippet : defaultTemplate.getSnippet()) {
                snippets.put(snippet.getName(), snippet.getValue());
            }
            for (AttachmentType attachment : defaultTemplate.getEmbedded()) {
                defaultEmbedded.add(new XmlMailAttachment(attachment));
            }
            for (AttachmentType attachment : defaultTemplate.getAttachment()) {
                defaultAttachments.add(new XmlMailAttachment(attachment));
            }
        }

        for (LocalizedType localized : mail.getLocalized()) {
            final Locale locale = new Locale(localized.getLocale());

            if (localized.getTemplateEngine() != null) {
                final Class<? extends TemplateEngine> templateEngineClass;
                
                try {
                    final String className = localized.getTemplateEngine();
                    templateEngineClass = Reflection.forName(className).asSubclass(TemplateEngine.class);
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
                
                templateEngine.put(locale, templateEngineClass);
            }
            if (localized.getSubject() != null) {
                subject.put(locale, localized.getSubject());
            }
            if (localized.getBody() != null) {
                body.put(locale, localized.getBody());
            }

            Set<MailAttachmentTemplate> localizedEmbeds = this.embedded.get(locale);
            if (localizedEmbeds == null) {
                localizedEmbeds = Sets.newHashSet();
                this.embedded.put(locale, localizedEmbeds);
            }
            for (AttachmentType attachment : localized.getEmbedded()) {
                localizedEmbeds.add(new XmlMailAttachment(attachment));
            }

            Set<MailAttachmentTemplate> localizedAttachments = this.attachments.get(locale);
            if (localizedAttachments == null) {
                localizedAttachments = Sets.newHashSet();
                this.attachments.put(locale, localizedAttachments);
            }
            for (AttachmentType attachment : localized.getEmbedded()) {
                localizedAttachments.add(new XmlMailAttachment(attachment));
            }
        }
    }

    @Override
    public LocalizedMailTemplate createLocalized(final Locale locale) {
        /* CHECKSTYLE:OFF */
        return new LocalizedMailTemplate() {
        /* CHECKSTYLE:ON */
            
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
                final Set<MailAttachmentTemplate> concreteEmbedded = Sets.newHashSet(defaultEmbedded);
                if (embedded.containsKey(locale)) {
                    concreteEmbedded.addAll(embedded.get(locale));
                }
                return concreteEmbedded;
            }

            @Override
            public Set<MailAttachmentTemplate> getAttachments() {
                final Set<MailAttachmentTemplate> concreteAttachments = Sets.newHashSet(defaultAttachments);
                if (attachments.containsKey(locale)) {
                    concreteAttachments.addAll(attachments.get(locale));
                }
                return concreteAttachments;
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
