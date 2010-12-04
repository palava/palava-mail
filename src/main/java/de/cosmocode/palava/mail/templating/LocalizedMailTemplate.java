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

package de.cosmocode.palava.mail.templating;

import java.util.Map;
import java.util.Set;

/**
 * A localized mail template, providing all related information. 
 * 
 * @author Tobias Sarnowski
 */
public interface LocalizedMailTemplate {

    /**
     * Provides the unique name.
     * 
     * @return the unique name of this template
     */
    String getName();

    /**
     * Provides the mail subject.
     * 
     * @return the mail subject
     */
    String getSubject();

    /**
     * Provides the mail body.
     * 
     * @return the mail body
     */
    String getBody();

    /**
     * Provides the snippets of this template.
     * 
     * @return snippets that can be used within the template
     */
    Map<String, String> getSnippets();

    /**
     * Provices the list of embedded {@link MailAttachmentTemplate}s.
     * 
     * @return the list of embedded attachments
     */
    Set<MailAttachmentTemplate> getEmbedded();

    /**
     * Provides the list of {@link MailAttachmentTemplate}s.
     * 
     * @return the list of attachments
     */
    Set<MailAttachmentTemplate> getAttachments();

    /**
     * Provides the template engines class of this template.
     * 
     * @return the class, used as template engine
     */
    Class<? extends TemplateEngine> getTemplateEngine();

}
