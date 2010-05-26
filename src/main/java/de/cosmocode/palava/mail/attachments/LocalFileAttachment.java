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

package de.cosmocode.palava.mail.attachments;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * @author Tobias Sarnowski
 */
public class LocalFileAttachment implements MailAttachmentSource {
    private static final Logger LOG = LoggerFactory.getLogger(LocalFileAttachment.class);

    @Override
    public MailAttachment generate(final String name, Map<String, String> configuration) {
        String filename = Preconditions.checkNotNull(configuration.get("file"), "'file' not configured for attachment");

        File file = new File(filename);
        Preconditions.checkState(file.canRead(), "cannot read configured souce file '" + filename + "'");

        final InputStream in;
        try {
            in = file.toURI().toURL().openStream();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return new MailAttachment() {
            @Override
            public String getName() {
                return name;
            }
            @Override
            public InputStream getContent() {
                return in;
            }
            @Override
            public String toString() {
                return "LocalFileAttachment{" + name + "}";
            }
        };
    }
}