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
 * 文件名称：ActionVisibilityHelper.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 14:22:18
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.utils;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.vfs.VirtualFile;

public class ActionVisibilityHelper {

    private String folder;

    private String extension;

    public ActionVisibilityHelper(String folder, String extension) {
        this.folder = folder;
        this.extension = extension;
    }

    public boolean isVisible(DataContext dataContext) {
        VirtualFile[] files = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(dataContext);
        if (files != null && files.length == 1) {
            VirtualFile file = files[0];
            return !file.isDirectory() && hasCorrectExtension(file) && hasCorrectFolder(file);
        } else {
            return false;
        }
    }

    private boolean hasCorrectExtension(VirtualFile data) {
        return data.getExtension() != null && data.getExtension().equals(extension);
    }

    private boolean hasCorrectFolder(VirtualFile data) {
        return data.getParent().getName().equals(folder);
    }
}
