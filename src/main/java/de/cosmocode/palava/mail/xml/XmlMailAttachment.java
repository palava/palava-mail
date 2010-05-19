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

import de.cosmocode.palava.mail.attachments.MailAttachmentSource;
import de.cosmocode.palava.mail.templating.MailAttachmentTemplate;
import de.cosmocode.palava.mail.xml.gen.AttachmentType;
import de.cosmocode.palava.mail.xml.gen.ConfigType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public class XmlMailAttachment implements MailAttachmentTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(XmlMailAttachment.class);

    private String name;
    private Class<? extends MailAttachmentSource> source;
    private Map<String,String> configuration;

    public XmlMailAttachment(AttachmentType attachment) {
        name = attachment.getName();
        try {
            source = (Class<? extends MailAttachmentSource>) Class.forName(attachment.getSource());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        for (ConfigType config: attachment.getConfig()) {
            configuration.put(config.getName(), config.getValue());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<? extends MailAttachmentSource> getSource() {
        return source;
    }

    @Override
    public Map<String, String> getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final XmlMailAttachment that = (XmlMailAttachment) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}