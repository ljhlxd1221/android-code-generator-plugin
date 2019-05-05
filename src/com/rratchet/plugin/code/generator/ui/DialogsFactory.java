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
 * 文件名称：DialogsFactory.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 14:18:13
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:21
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.ui;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.UIUtil;

public class DialogsFactory {

    public static boolean openOverrideFileDialog(Project project, String folderPath, String fileName) {
        return Messages.showYesNoDialog(
                project,
                String.format(StringResources.OVERRIDE_DIALOG_MESSAGE, folderPath, fileName),
                StringResources.OVERRIDE_DIALOG_TITLE,
                StringResources.OVERRIDE_DIALOG_YES_TEXT,
                StringResources.OVERRIDE_DIALOG_NO_TEXT,
                UIUtil.getWarningIcon()
        ) == Messages.OK;
    }

    public static void showMissingSourcePathDialog(Project project) {
        Messages.showErrorDialog(
                project,
                StringResources.MISSING_SOURCE_PATH_DIALOG_MESSAGE,
                StringResources.MISSING_SOURCE_PATH_DIALOG_TITLE
        );
    }

    public static boolean openResetTemplateDialog() {
        return Messages.showOkCancelDialog(IdeBundle.message("prompt.reset.to.original.template"),
                IdeBundle.message("title.reset.template"), Messages.getQuestionIcon()) ==
                Messages.OK;
    }
}
