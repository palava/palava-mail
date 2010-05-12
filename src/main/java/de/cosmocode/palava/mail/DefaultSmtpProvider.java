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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * @author Tobias Sarnowski
 */
final class DefaultSmtpProvider implements Provider<Session> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSmtpProvider.class);

    private String smtpHost = "localhost";
    private int smtpPort = 25;
    private String smtpUser;
    private String smtpPassword;

    private Authenticator smtpAuthenticator;

    private boolean debug = false;

    @Inject(optional = true)
    public void setDebug(@Named(SmtpConfig.DEBUG) boolean debug) {
        this.debug = debug;
    }

    @Inject(optional = true)
    public void setSmtpHost(@Named(SmtpConfig.HOST) String smtpHost) {
        this.smtpHost = smtpHost;
    }

    @Inject(optional = true)
    public void setSmtpPort(@Named(SmtpConfig.PORT) int smtpPort) {
        this.smtpPort = smtpPort;
    }

    @Inject(optional = true)
    public void setSmtpUser(@Named(SmtpConfig.USER) String smtpUser) {
        this.smtpUser = smtpUser;
    }

    @Inject(optional = true)
    public void setSmtpPassword(@Named(SmtpConfig.PASSWORD) String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    @Inject(optional = true)
    public void setSmtpAuthenticator(@SmtpAuthenticator Authenticator smtpAuthenticator) {
        this.smtpAuthenticator = smtpAuthenticator;
    }

    @Override
    public Session get() {
        Properties smtpConfiguration = new Properties();
        smtpConfiguration.put("mail.smtp.host", smtpHost);
        smtpConfiguration.put("mail.smtp.port", smtpPort);

        if (debug) {
            smtpConfiguration.put("mail.debug", "true");
        } else {
            smtpConfiguration.put("mail.debug", "false");
        }

        if (smtpUser != null && smtpPassword != null) {
            if (smtpAuthenticator != null) {
                LOG.warn("An Authenticator is configured in addition to user and password; using user and password.");
            } else {
                LOG.trace("User and password configured; generating Authenticator");
            }
            smtpAuthenticator = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    String username = smtpUser;
                    String password = smtpPassword;
                    return new PasswordAuthentication(username, password);
                }
            };
        }

        if (smtpAuthenticator != null) {
            LOG.trace("Using authenticator {}", smtpAuthenticator);
            smtpConfiguration.put("mail.smtp.auth", "true");
            return Session.getDefaultInstance(smtpConfiguration, smtpAuthenticator);
        } else {
            LOG.trace("Using no authenticator");
            return Session.getDefaultInstance(smtpConfiguration);
        }
    }
}