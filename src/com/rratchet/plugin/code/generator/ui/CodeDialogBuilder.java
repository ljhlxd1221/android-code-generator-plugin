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
 * 文件名称：CodeDialogBuilder.java
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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CodeDialogBuilder {

    private final DialogBuilder dialogBuilder;

    private final JPanel topPanel;

    private final JTextArea codeArea;
    private JBTextField packageText;
    private JComboBox sourcePathComboBox;

    public CodeDialogBuilder(Project project, String title, String producedCode) {
        dialogBuilder = new DialogBuilder(project);
        dialogBuilder.setTitle(title);

        JPanel centerPanel = new JPanel(new BorderLayout());

        codeArea = prepareCodeArea(producedCode);
        centerPanel.add(new JBScrollPane(codeArea), BorderLayout.CENTER);
        dialogBuilder.setCenterPanel(centerPanel);

        topPanel = new JPanel(new GridLayout(0, 2));
        centerPanel.add(topPanel, BorderLayout.PAGE_START);

        dialogBuilder.removeAllActions();
    }

    public void addAction(String title, final Runnable action) {
        addAction(title, action, false);
    }

    public void addAction(String title, final Runnable action, final boolean runWriteAction) {
        dialogBuilder.addAction(new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (runWriteAction) {
                    ApplicationManager.getApplication().runWriteAction(action);
                } else {
                    action.run();
                }
            }
        });
    }

    public void addPackageSection(String defaultText) {
        topPanel.add(new JLabel(StringResources.PACKAGE_LABEL));
        packageText = new JBTextField(defaultText);
        topPanel.add(packageText);
    }

    public String getPackage() {
        return packageText.getText();
    }

    public void addSourcePathSection(java.util.List<String> string, String defaultValue) {
        topPanel.add(new JLabel(StringResources.SOURCE_PATH_LABEL));
        sourcePathComboBox = new ComboBox(new CollectionComboBoxModel(string));
        sourcePathComboBox.setSelectedItem(defaultValue);
        topPanel.add(sourcePathComboBox);
    }

    public String getSourcePath() {
        return (String) sourcePathComboBox.getSelectedItem();
    }

    public int showDialog() {
        return dialogBuilder.show();
    }

    public void closeDialog(){
        dialogBuilder.getDialogWrapper().close(DialogWrapper.OK_EXIT_CODE);
    }

    public String getModifiedCode() {
        return codeArea.getText();
    }

    private JTextArea prepareCodeArea(String producedCode) {
        JTextArea codeArea = new JTextArea(producedCode);
        codeArea.setBorder(new LineBorder(JBColor.gray));
        return codeArea;
    }
}
