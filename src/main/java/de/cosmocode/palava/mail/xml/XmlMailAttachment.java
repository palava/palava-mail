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

import java.util.Map;

import com.google.inject.internal.Maps;
import com.google.inject.internal.Objects;

import de.cosmocode.commons.reflect.Reflection;
import de.cosmocode.palava.mail.attachments.MailAttachmentSource;
import de.cosmocode.palava.mail.templating.MailAttachmentTemplate;
import de.cosmocode.palava.mail.xml.gen.AttachmentType;
import de.cosmocode.palava.mail.xml.gen.ConfigType;

/**
 * A xml based {@link MailAttachmentTemplate}.
 * 
 * @author Tobias Sarnowski
 */
public class XmlMailAttachment implements MailAttachmentTemplate {

    private final String name;
    private final Class<? extends MailAttachmentSource> source;
    private final Map<String, String> configuration = Maps.newHashMap();

    public XmlMailAttachment(AttachmentType attachment) {
        name = attachment.getName();
        
        try {
            source = Reflection.forName(attachment.getSource()).asSubclass(MailAttachmentSource.class);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        
        for (ConfigType config : attachment.getConfig()) {
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
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that == null) {
            return false;
        } else if (getClass() == that.getClass()) {
            final XmlMailAttachment other = XmlMailAttachment.class.cast(that);
            return Objects.equal(name, other.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }
    
}
