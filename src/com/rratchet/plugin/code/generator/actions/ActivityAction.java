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
 * 文件名称：ActivityAction.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 15:33:47
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.actions;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.rratchet.plugin.code.providers.factories.ActivityResourceProvidersFactory;

public class ActivityAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Activity";
    }

    @Override
    protected String getTemplateName() {
        return "Activity.java.ftl";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new ActivityResourceProvidersFactory();
    }
}