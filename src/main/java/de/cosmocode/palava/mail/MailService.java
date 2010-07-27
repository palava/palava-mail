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

package de.cosmocode.palava.mail;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

import de.cosmocode.palava.mail.templating.MailTemplate;

/**
 * A service which can be used to prepare {@link Message}s from predefined templates.
 * 
 * @author Tobias Sarnowski
 */
public interface MailService {

    /**
     * Prepares a Mail with subject and body, it's nessecary to fill in TO and FROM afterwards.
     * 
     * @param name the mail templates unique name
     * @param locale the language to use
     * @param session the smtp configured mail session
     * @param content variables used by the template engine
     * @return a mail with already filled in subject and body
     * @throws MessagingException if an error occured during preparation
     */
    Message prepare(String name, Locale locale, Session session, Map<String, ? extends Object> content) 
        throws MessagingException;

    /**
     * Prepares a Mail with subject and body, it's nessecary to fill in TO and FROM afterwards.
     * 
     * @param name the mail templates unique name
     * @param locale the language to use
     * @param session the smtp configured mail session
     * @return a mail with already filled in subject and body
     * @throws MessagingException if an error occured during preparation
     */
    Message prepare(String name, Locale locale, Session session) throws MessagingException;

    /**
     * Returns all templates that this mail service knows of.
     *
     * @return a collection of all known templates. can be empty.
     */
    Collection<? extends MailTemplate> getAllTemplates();

}
