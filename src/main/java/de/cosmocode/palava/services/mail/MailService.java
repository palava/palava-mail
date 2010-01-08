/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.services.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import de.cosmocode.palava.Service;

/**
 * A {@link Service} used to send emails.
 *
 * @author Willi Schoenborn
 */
public interface MailService extends Service {

    /**
     * Sends a message. The given {@link TemplateDescriptor} and the optional {@link Locale} are used to
     * retrieve a template which then will be filled with the given params.
     * 
     * @param descriptor the descriptor identifying the template
     * @param locale the optional locale
     * @param params the concrete and dynamic values for the template 
     * @return a {@link MimeMessage} which represents the actual mail message being sent
     * @throws NullPointerException if descriptor or params is null
     * @throws MailException if sending failed
     */
    MimeMessage send(TemplateDescriptor descriptor, Locale locale, Map<String, ?> params) throws MailException;
    
}
