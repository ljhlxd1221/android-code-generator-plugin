/*
 * Copyright (c) 2019. RRatChet Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 项目名称：android-code-generator-plugin
 * 模块名称：android-code-generator-plugin
 *
 * 文件名称：CodeGeneratorFactory.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 15:11:40
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator;

import com.google.common.io.Resources;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.TemplateCodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.templates.TemplatesProvider;
import com.morcinek.android.codegenerator.extractor.XMLResourceExtractor;
import com.morcinek.android.codegenerator.extractor.string.FileNameExtractor;
import com.rratchet.plugin.code.generator.config.ApplicationSettings;
import com.rratchet.plugin.code.generator.config.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * <pre>
 *
 *      作 者 :        ASLai(laijianhua@rratchet.com).
 *      日 期 :        2019-05-05
 *      版 本 :        V1.0
 *      描 述 :        description
 *
 *
 * </pre>
 *
 * @author ASLai
 */
public class CodeGeneratorFactory {

    public static CodeGenerator createCodeGenerator(String templateName, ResourceProvidersFactory resourceProvidersFactory) {
        return new CodeGenerator(XMLResourceExtractor.createResourceExtractor(),
                new FileNameExtractor(),
                //FIXME change ResourceTemplateProvider for PreferencesTemplateProvider
                new TemplateCodeGenerator(templateName, resourceProvidersFactory, ApplicationSettings.getInstance()));
    }

    public static class ResourceTemplateProvider implements TemplatesProvider {

        @Override
        public String provideTemplateForName(String templateName) {
//            URL url = getClass().getResource(Configuration.TEMPLATE_DIR + templateName);
            URL url = getClass().getClassLoader().getResource(Configuration.TEMPLATE_DIR + templateName);
            try {
                return Resources.toString(url, Charset.defaultCharset());
            } catch (Exception e) {
                System.out.println("The template[" + templateName + "] not found.");
                e.printStackTrace();
            }
            return null;
        }

    }
}
