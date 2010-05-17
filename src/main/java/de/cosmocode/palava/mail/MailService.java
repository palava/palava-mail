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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public interface MailService {

    /**
     * Prepares a Mail with subject and body, it's nessecary to fill in TO and FROM afterwards.
     * @param name the mail templates unique name
     * @param locale the language to use
     * @param session the smtp configured mail session
     * @param templateVariables variables used by the template engine
     * @return a mail with already filled in subject and body
     * @throws MessagingException
     */
    Message prepare(String name, Locale locale, Session session, Map<? extends CharSequence,? extends Object> templateVariables) throws MessagingException;

    /**
     * Prepares a Mail with subject and body, it's nessecary to fill in TO and FROM afterwards.
     * @param name the mail templates unique name
     * @param locale the language to use
     * @param session the smtp configured mail session
     * @return a mail with already filled in subject and body
     * @throws MessagingException
     */
    Message prepare(String name, Locale locale, Session session) throws MessagingException;

}