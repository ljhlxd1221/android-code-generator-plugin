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
 * 文件名称：ProjectSettings.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 15:21:47
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

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
        name = "ProjectSettings",
        storages = {
                @Storage(file = Configuration.PROJECT_PATH, scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class ProjectSettings implements PersistentStateComponent<ProjectSettings> {

    private String sourcePath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public static ProjectSettings getInstance(Project project) {
        return ServiceManager.getService(project, ProjectSettings.class);
    }

    @Nullable
    @Override
    public ProjectSettings getState() {
        return this;
    }

    @Override
    public void loadState(ProjectSettings settings) {
        XmlSerializerUtil.copyBean(settings, this);
    }
}
