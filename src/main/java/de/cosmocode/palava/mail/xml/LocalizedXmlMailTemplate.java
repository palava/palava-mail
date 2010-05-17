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

import de.cosmocode.palava.mail.attachments.MailAttachment;
import de.cosmocode.palava.mail.templating.TemplateEngine;

import java.util.List;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public interface LocalizedXmlMailTemplate {

    /**
     * @return the mail subject
     */
    String getSubject();

    /**
     * @return the mail body
     */
    String getBody();

    /**
     * @return snippets that can be used within the template
     */
    Map<String,String> getSnippets();

    /**
     * @return the list of attachments
     */
    Map<String,XmlMailAttachment> getAttachments();

    /**
     * @return the class, used as template engine
     */
    Class<? extends TemplateEngine> getTemplateEngine();

}