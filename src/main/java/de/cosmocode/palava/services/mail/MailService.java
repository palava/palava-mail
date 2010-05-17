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

package de.cosmocode.palava.services.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

/**
 * A service 
 *
 * @author Willi Schoenborn
 */
public interface MailService {

    @Deprecated
    MimeMessage sendMessage(String template, String lang, Map<String, ?> params,  String... to) throws Exception;
    
    MimeMessage send(TemplateDescriptor descriptor, String lang, Map<String, ?> params) throws Exception;
    
    MimeMessage send(TemplateDescriptor descriptor, Locale locale, Map<String, ?> params) throws Exception;
    
    MimeMessage send(TemplateDescriptor descriptor, Map<String, ?> params) throws Exception;
    
}