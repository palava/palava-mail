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