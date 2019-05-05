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
 * 文件名称：ImportsBuilder.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 14:00:23
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 14:47:48
 * 修改备注：
 */

package com.morcinek.android.codegenerator.codegeneration.builders.resources;

import com.google.common.collect.Sets;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvider;
import com.morcinek.android.codegenerator.codegeneration.templates.TemplateManager;
import com.morcinek.android.codegenerator.codegeneration.templates.TemplatesProvider;

import java.util.List;
import java.util.Set;

public class ImportsBuilder extends ResourceCodeBuilder {

    public Set<String> imports;

    public ImportsBuilder(List<ResourceProvider> resourceProviders, TemplatesProvider templatesProvider) {
        super(resourceProviders, templatesProvider);
    }

    @Override
    protected void initializeFields() {
        imports = Sets.newLinkedHashSet();
    }

    @Override
    protected void processResourceProvider(ResourceProvider resourceProvider) {
        TemplateManager templateManager = getTemplateManager();
        templateManager.addTemplateValues(resourceProvider.provideValues());
        imports.add(templateManager.getResult());
    }

    private TemplateManager getTemplateManager() {
        return new TemplateManager(templatesProvider.provideTemplateForName("import.ftl"));
    }

    @Override
    public String builtString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String objectImport : imports) {
            stringBuilder.append(objectImport);
        }
        return prepareBuildString(stringBuilder.toString());
    }

    @Override
    public String getKey() {
        return "IMPORTS";
    }
}
