package de.cosmocode.palava.mail.attachments;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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