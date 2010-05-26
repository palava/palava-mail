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

import org.apache.commons.io.FileUtils;

import com.google.common.base.Preconditions;

/**
 * {@link File} based {@link MailAttachmentSource} implementation.
 * 
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
public class LocalFileAttachment implements MailAttachmentSource {

    @Override
    public MailAttachment generate(final String name, Map<String, String> configuration) {
        final String filename = configuration.get("file");
        Preconditions.checkNotNull(filename, "'file' not configured for attachment");
        final InputStream stream;
        
        try {
            stream = FileUtils.openInputStream(new File(filename));
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
                return stream;
            }
            
            @Override
            public String toString() {
                return "LocalFileAttachment [" + name + "]";
            }
            
        };
    }
    
}
