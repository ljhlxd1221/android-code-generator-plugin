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
 * 文件名称：ApplicationSettings.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 15:22:00
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:21
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.config;

import com.google.common.collect.Maps;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.morcinek.android.codegenerator.codegeneration.templates.TemplatesProvider;
import com.rratchet.plugin.code.generator.CodeGeneratorFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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
@State(
        name = "ApplicationSettings",
        storages = {
                @Storage(file = Configuration.APPLICATION_PATH)
        }
)
public class ApplicationSettings implements PersistentStateComponent<ApplicationSettings>, TemplatesProvider {

    private Map<String, String> templateValues = Maps.newHashMap();

    private TemplatesProvider templatesProvider = new CodeGeneratorFactory.ResourceTemplateProvider();

    public static ApplicationSettings getInstance() {
        return ServiceManager.getService(ApplicationSettings.class);
    }

    public Map<String, String> getTemplateValues() {
        return templateValues;
    }

    public void setTemplateValues(Map<String, String> templateValues) {
        this.templateValues = templateValues;
    }

    @Nullable
    @Override
    public ApplicationSettings getState() {
        return this;
    }

    @Override
    public void loadState(ApplicationSettings settings) {
        XmlSerializerUtil.copyBean(settings, this);
    }

    @Override
    public String provideTemplateForName(String templateName) {
        if (isUsingCustomTemplateForName(templateName)) {
            return templateValues.get(templateName);
        }
        return templatesProvider.provideTemplateForName(templateName);
    }

    public void removeTemplateForName(String templateName) {
        templateValues.remove(templateName);
    }

    public boolean isUsingCustomTemplateForName(String templateName) {
        return templateValues.containsKey(templateName);
    }

    public void setTemplateForName(String templateName, String template) {
        templateValues.put(templateName, template);
    }
}
