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
 * 文件名称：LayoutAction.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 15:17:54
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.rratchet.plugin.code.generator.CodeGeneratorController;
import com.rratchet.plugin.code.generator.config.ProjectSettings;
import com.rratchet.plugin.code.generator.ui.CodeDialogBuilder;
import com.rratchet.plugin.code.generator.ui.DialogsFactory;
import com.rratchet.plugin.code.generator.ui.StringResources;
import com.rratchet.plugin.code.generator.utils.*;
import com.rratchet.plugin.code.generator.utils.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

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
public abstract class LayoutAction extends AnAction {

    private final ErrorHandler errorHandler = new ErrorHandler();

    private final PackageHelper packageHelper = new PackageHelper();

    private final ProjectHelper projectHelper = new ProjectHelper();

    private final PathHelper pathHelper = new PathHelper();

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = getEventProject(event);
        VirtualFile selectedFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        ProjectSettings settings = ProjectSettings.getInstance(project);
        try {
            showCodeDialog(event, project, selectedFile, settings);
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    private void showCodeDialog(AnActionEvent event, final Project project, final VirtualFile selectedFile, ProjectSettings settings) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, org.xml.sax.SAXException {
        CodeGeneratorController codeGeneratorController = new CodeGeneratorController(getTemplateName(), getResourceProvidersFactory());
        String generatedCode = codeGeneratorController.generateCode(project, selectedFile, event.getData(PlatformDataKeys.EDITOR));
        final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                String.format(StringResources.TITLE_FORMAT_TEXT, selectedFile.getName()), generatedCode);
        codeDialogBuilder.addSourcePathSection(projectHelper.getSourceRootPathList(project, event), settings.getSourcePath());
        codeDialogBuilder.addPackageSection(packageHelper.getPackageName(project, event));
        codeDialogBuilder.addAction(StringResources.COPY_ACTION_LABEL, new Runnable() {
            @Override
            public void run() {
                ClipboardHelper.copy(getFinalCode(codeDialogBuilder));
                codeDialogBuilder.closeDialog();
            }
        });
        codeDialogBuilder.addAction(StringResources.CREATE_ACTION_LABEL, new Runnable() {
            @Override
            public void run() {
                try {
                    createFileWithGeneratedCode(codeDialogBuilder, selectedFile, project);
                } catch (IOException exception) {
                    errorHandler.handleError(project, exception);
                }
            }
        }, true);
        if (codeDialogBuilder.showDialog() == DialogWrapper.OK_EXIT_CODE) {
            settings.setSourcePath(codeDialogBuilder.getSourcePath());
        }
    }

    private void createFileWithGeneratedCode(CodeDialogBuilder codeDialogBuilder, VirtualFile selectedFile, Project project) throws IOException {
        if (codeDialogBuilder.getSourcePath() == null) {
            DialogsFactory.showMissingSourcePathDialog(project);
        } else {
            String folderPath = getFolderPath(codeDialogBuilder);
            String fileName = pathHelper.getFileName(selectedFile.getName(), getResourceName());
            if (!projectHelper.fileExists(project, fileName, folderPath)) {
                createOrOverrideFileWithGeneratedCode(codeDialogBuilder, project, folderPath, fileName);
            } else {
                if (DialogsFactory.openOverrideFileDialog(project, folderPath, fileName)) {
                    createOrOverrideFileWithGeneratedCode(codeDialogBuilder, project, folderPath, fileName);
                }
            }
        }
    }

    private void createOrOverrideFileWithGeneratedCode(CodeDialogBuilder codeDialogBuilder, Project project, String folderPath, String fileName) throws IOException {
        String finalCode = getFinalCode(codeDialogBuilder);
        VirtualFile createdFile = projectHelper.createOrFindFile(project, fileName, folderPath);
        projectHelper.setFileContent(project, createdFile, finalCode);
        codeDialogBuilder.closeDialog();
    }

    protected abstract String getResourceName();

    protected abstract String getTemplateName();

    protected abstract ResourceProvidersFactory getResourceProvidersFactory();

    private String getFolderPath(CodeDialogBuilder codeDialogBuilder) {
        String sourcePath = codeDialogBuilder.getSourcePath();
        String packageName = codeDialogBuilder.getPackage();
        return pathHelper.getFolderPath(sourcePath, packageName);
    }

    private String getFinalCode(CodeDialogBuilder codeDialogBuilder) {
        String packageName = codeDialogBuilder.getPackage();
        String modifiedCode = codeDialogBuilder.getModifiedCode();
        return pathHelper.getMergedCodeWithPackage(packageName, modifiedCode);
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(new ActionVisibilityHelper("layout", "xml").isVisible(event.getDataContext()));
    }
}

