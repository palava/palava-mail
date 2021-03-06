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

import de.cosmocode.palava.mail.attachments.MailAttachment;
import de.cosmocode.palava.mail.attachments.MailAttachmentSource;

/**
 * A template for {@link MailAttachment}s.
 * 
 * @author Tobias Sarnowski
 */
public interface MailAttachmentTemplate {
    
    /**
     * Provides the name of this template.
     * 
     * @return the name
     */
    String getName();

    /**
     * Provides the source of this template.
     * 
     * @return the source
     */
    Class<? extends MailAttachmentSource> getSource();

    /**
     * Provices the configuration of this template.
     * 
     * @return the configuration
     */
    Map<String, String> getConfiguration();
    
}
