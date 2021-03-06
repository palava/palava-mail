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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Smtp based {@link Session} {@link Provider} implementation.
 * 
 * @deprecated use {@link DefaultSmtpSessionProvider} instead
 * @author Tobias Sarnowski
 */
@Deprecated
final class DefaultSmtpProvider implements Provider<Session> {
    
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSmtpProvider.class);

    private String host = "localhost";
    private int port = 25;
    private String user;
    private String password;

    private Authenticator authenticator;

    private boolean debug;

    @Inject(optional = true)
    void setDebug(@Named(SmtpConfig.DEBUG) boolean debug) {
        this.debug = debug;
    }

    @Inject(optional = true)
    void setHost(@Named(SmtpConfig.HOST) String host) {
        this.host = host;
    }

    @Inject(optional = true)
    void setPort(@Named(SmtpConfig.PORT) int port) {
        this.port = port;
    }

    @Inject(optional = true)
    void setUser(@Named(SmtpConfig.USER) String user) {
        this.user = user;
    }

    @Inject(optional = true)
    void setPassword(@Named(SmtpConfig.PASSWORD) String password) {
        this.password = password;
    }

    @Inject(optional = true)
    void setAuthenticator(@Smtp Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public Session get() {
        final Properties configuration = new Properties();
        configuration.put("mail.smtp.host", host);
        configuration.put("mail.smtp.port", port);
        configuration.put("mail.debug", Boolean.toString(debug));

        if (user != null && password != null) {
            if (authenticator == null) {
                LOG.trace("User and password configured; generating Authenticator");
            } else {
                LOG.warn("An Authenticator is configured in addition to user and password; using user and password.");
            }
            
            authenticator = new Authenticator() {
                
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
                
            };
        }

        if (authenticator == null) {
            LOG.trace("Using no authenticator");
            return Session.getDefaultInstance(configuration);
        } else {
            LOG.trace("Using authenticator {}", authenticator);
            configuration.put("mail.smtp.auth", "true");
            return Session.getDefaultInstance(configuration, authenticator);
        }
    }
    
}
