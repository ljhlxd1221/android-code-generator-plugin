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
 * 文件名称：StringResources.java
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

public interface StringResources {

    String PACKAGE_LABEL = "Package";
    String SOURCE_PATH_LABEL = "Source Path";
    String TITLE_FORMAT_TEXT = "Code generated from: '%s'";
    String COPY_ACTION_LABEL = "Copy Code To Clipboard";
    String CREATE_ACTION_LABEL = "Create File";

    String OVERRIDE_DIALOG_TITLE = "File Already Exists";
    String OVERRIDE_DIALOG_MESSAGE = "File '%s/%s' already exists.\nDo you want to override file content with generated code.";
    String OVERRIDE_DIALOG_YES_TEXT = "Override";
    String OVERRIDE_DIALOG_NO_TEXT = "Cancel";

    String MISSING_SOURCE_PATH_DIALOG_MESSAGE = "You need to select 'Source Path' in which the file will be created.\nSelect one from the project's source roots.";
    String MISSING_SOURCE_PATH_DIALOG_TITLE = "Missing Source Path";

    String RESET_TO_DEFAULT_ACTION_DESCRIPTION = "Reset templates to Defaults";
    String RESET_TO_DEFAULT_ACTION_TITLE = "Reset to Defaults";
}
