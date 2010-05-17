/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
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