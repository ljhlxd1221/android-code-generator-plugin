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
 * 文件名称：PackageHelper.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 14:21:57
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.utils;

import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.extractor.PackageExtractor;
import com.morcinek.android.codegenerator.extractor.XMLPackageExtractor;

import java.util.ArrayList;
import java.util.List;

public class PackageHelper {

    private final PackageExtractor packageExtractor = new XMLPackageExtractor();

    private final ProjectHelper projectHelper = new ProjectHelper();

    public String getPackageName(Project project, AnActionEvent event) {
        try {
            for (String path : possibleManifestPaths()) {
                VirtualFile file = getManifestFileFromPath(project, path);
                if (file != null && file.exists()) {
                    return packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                }
            }
            for (String path : sourceRootPaths(project, event)) {
                VirtualFile file = getManifestFileFromPath(project, path);
                if (file != null && file.exists()) {
                    return packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    private ArrayList<String> possibleManifestPaths() {
        return Lists.newArrayList("", "app/", "app/src/main/", "src/main/", "res/");
    }

    private List<String> sourceRootPaths(Project project, AnActionEvent event) {
        return projectHelper.getSourceRootPathList(project, event);
    }

    private VirtualFile getManifestFileFromPath(Project project, String path) {
        VirtualFile folder = project.getBaseDir().findFileByRelativePath(path);
        if (folder != null) {
            return folder.findChild("AndroidManifest.xml");
        }
        return null;
    }
}
