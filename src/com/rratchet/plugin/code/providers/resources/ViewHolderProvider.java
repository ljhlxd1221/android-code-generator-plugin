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
 * 文件名称：ViewHolderProvider.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 16:13:45
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:25:17
 * 修改备注：
 */

package com.rratchet.plugin.code.providers.resources;

import com.google.common.collect.Sets;
import com.morcinek.android.codegenerator.codegeneration.providers.resources.AbstractResourceProvider;
import com.morcinek.android.codegenerator.extractor.model.Resource;

import java.util.Set;

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
public class ViewHolderProvider extends AbstractResourceProvider {

    protected ViewHolderProvider(Resource resource) {
        super(resource);
    }

    public static ViewHolderProvider create(Resource resource) {
        return new ViewHolderProvider(resource);
    }

    @Override
    public Set<String> provideInterface() {
        return null;
    }

    @Override
    public Set<String> provideMethod() {
        return null;
    }

    @Override
    public Set<String> provideAssignment() {
        return Sets.newHashSet("ViewHolderAssignment.java.ftl");
    }

    @Override
    public Set<String> provideField() {
        return Sets.newHashSet("ViewHolderField.java.ftl");
    }
}
