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

package de.cosmocode.palava.mailx;

/**
 * @author Tobias Sarnowski
 */
public class MailSessionConfig {

    public static final String PREFIX = "mailx.";

    public static final String DEBUG = PREFIX + "debug";

    public static final String SMTP_HOST = PREFIX + "smtp.host";
    public static final String SMTP_PORT = PREFIX + "smtp.port";
    public static final String SMTP_USER = PREFIX + "smtp.user";
    public static final String SMTP_PASSWORD = PREFIX + "smtp.password";

    public static final String IMAP_HOST = PREFIX + "imap.host";
    public static final String IMAP_PORT = PREFIX + "imap.port";
    public static final String IMAP_USER = PREFIX + "imap.user";
    public static final String IMAP_PASSWORD = PREFIX + "imap.password";

    public static final String POP_HOST = PREFIX + "pop.host";
    public static final String POP_PORT = PREFIX + "pop.port";
    public static final String POP_USER = PREFIX + "pop.user";
    public static final String POP_PASSWORD = PREFIX + "pop.password";

}