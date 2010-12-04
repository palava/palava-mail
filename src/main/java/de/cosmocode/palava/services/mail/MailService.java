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

package de.cosmocode.palava.services.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

/**
 * A service for sending email.
 *
 * @deprecated replaced by {@link de.cosmocode.palava.mail.MailService}
 * @author Willi Schoenborn
 */
@Deprecated
public interface MailService {

    /**
     * Sends a message.
     * 
     * @param template the template's name/path
     * @param lang the language
     * @param params the template parameters
     * @param to the email addresses to send the message to
     * @return the sent mime message for further processing, if needed
     * @throws Exception anything goes wrong
     */
    MimeMessage sendMessage(String template, String lang, Map<String, ?> params,  String... to) throws Exception;
    
    /**
     * Sends a message using a template descriptor.
     * 
     * @param descriptor the template descriptor
     * @param lang the language
     * @param params the template parameters
     * @return the sent mime message for further processing, if needed
     * @throws Exception anything goes wrong
     */
    MimeMessage send(TemplateDescriptor descriptor, String lang, Map<String, ?> params) throws Exception;

    /**
     * Sends a message using a template descriptor and a locale.
     * 
     * @param descriptor the template descriptor
     * @param locale the locale
     * @param params the template parameters
     * @return the sent mime message for further processing, if needed
     * @throws Exception anything goes wrong
     */
    MimeMessage send(TemplateDescriptor descriptor, Locale locale, Map<String, ?> params) throws Exception;

    /**
     * Sends a message using a template descriptor.
     * 
     * @param descriptor the template descriptor
     * @param params the template parameters
     * @return the sent mime message for further processing, if needed
     * @throws Exception anything goes wrong
     */
    MimeMessage send(TemplateDescriptor descriptor, Map<String, ?> params) throws Exception;
    
}
