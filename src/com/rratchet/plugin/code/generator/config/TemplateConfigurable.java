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
 * 文件名称：TemplateConfigurable.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 15:09:35
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:20
 * 修改备注：
 */

package com.rratchet.plugin.code.generator.config;

import com.intellij.icons.AllIcons;
import com.intellij.ide.highlighter.JavaFileHighlighter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.util.LexerEditorHighlighter;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.SeparatorFactory;
import com.intellij.ui.roots.ToolbarPanel;
import com.rratchet.plugin.code.generator.ui.DialogsFactory;
import com.rratchet.plugin.code.generator.ui.StringResources;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class TemplateConfigurable extends BaseConfigurable {

    private JPanel editorPanel = new JPanel(new GridLayout());

    private Editor editor;

    private ApplicationSettings applicationSettings;

    private final String templateName;
    private final String templateHeaderText;
    private final String displayName;

    public TemplateConfigurable(String displayName, String templateHeaderText, String templateName) {
        this.displayName = displayName;
        this.templateHeaderText = templateHeaderText;
        this.templateName = templateName;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        applicationSettings = ApplicationSettings.getInstance();
        editor = createEditorInPanel(applicationSettings.provideTemplateForName(templateName));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(SeparatorFactory.createSeparator(templateHeaderText, null), BorderLayout.PAGE_START);
        panel.add(new ToolbarPanel(editorPanel, new DefaultActionGroup(new ResetToDefaultAction())), BorderLayout.CENTER);
        return panel;
    }

    private Editor createEditorInPanel(String string) {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Editor editor = editorFactory.createEditor(editorFactory.createDocument(string));

        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setVirtualSpace(false);
        editorSettings.setLineMarkerAreaShown(false);
        editorSettings.setIndentGuidesShown(false);
        editorSettings.setLineNumbersShown(false);
        editorSettings.setFoldingOutlineShown(false);
        editorSettings.setAdditionalColumnsCount(3);
        editorSettings.setAdditionalLinesCount(3);

        editor.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            public void documentChanged(DocumentEvent e) {
                onTextChanged();
            }
        });

        ((EditorEx) editor).setHighlighter(getEditorHighlighter());

        addEditorToPanel(editor);

        return editor;
    }

    private LexerEditorHighlighter getEditorHighlighter() {
        return new LexerEditorHighlighter(new JavaFileHighlighter(), EditorColorsManager.getInstance().getGlobalScheme());
    }

    private void onTextChanged() {
        myModified = true;
    }

    private void addEditorToPanel(Editor editor) {
        editorPanel.removeAll();
        editorPanel.add(editor.getComponent());
    }

    @Override
    public void disposeUIResources() {
        if (editor != null) {
            EditorFactory.getInstance().releaseEditor(editor);
            editor = null;
        }
        applicationSettings = null;
    }

    @Override
    public void apply() throws ConfigurationException {
        applicationSettings.setTemplateForName(templateName, editor.getDocument().getText());
        setUnmodified();
    }

    @Override
    public void reset() {
        EditorFactory.getInstance().releaseEditor(editor);
        editor = createEditorInPanel(applicationSettings.provideTemplateForName(templateName));
        setUnmodified();
    }

    private void setUnmodified() {
        setModified(false);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return editorPanel;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    class ResetToDefaultAction extends AnAction {

        ResetToDefaultAction() {
            super(StringResources.RESET_TO_DEFAULT_ACTION_TITLE, StringResources.RESET_TO_DEFAULT_ACTION_DESCRIPTION, AllIcons.Actions.Reset);
        }

        @Override
        public void actionPerformed(AnActionEvent anActionEvent) {
            if (DialogsFactory.openResetTemplateDialog()) {
                applicationSettings.removeTemplateForName(templateName);
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        editor.getDocument().setText(applicationSettings.provideTemplateForName(templateName));
                        setUnmodified();
                    }
                });
            }
        }

        @Override
        public void update(AnActionEvent e) {
            super.update(e);
            e.getPresentation().setEnabled(applicationSettings.isUsingCustomTemplateForName(templateName));
        }
    }
}
