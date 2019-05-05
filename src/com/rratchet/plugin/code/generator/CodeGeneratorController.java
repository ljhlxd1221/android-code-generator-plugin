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
 * 文件名称：CodeGeneratorController.java
 * 文件描述：
 *
 * 创 建 人：ASLai(laijianhua@rratchet.com)
 *
 * 上次修改时间：2019-05-05 14:18:22
 *
 * 修 改 人：ASLai(laijianhua@rratchet.com)
 * 修改时间：2019-05-05 16:23:21
 * 修改备注：
 */

package com.rratchet.plugin.code.generator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CodeGeneratorController {

    private CodeGenerator codeGenerator;

    public CodeGeneratorController(String templateName, ResourceProvidersFactory resourceProvidersFactory) {
        codeGenerator = CodeGeneratorFactory.createCodeGenerator(templateName, resourceProvidersFactory);
    }

    public String generateCode(Project project, VirtualFile file, Editor editor) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return codeGenerator.produceCode(getContents(project, editor, file), file.getName());
    }

    private InputStream getContents(Project project, Editor editor, VirtualFile file) throws IOException {
        editor = getEditor(project, editor, file);
        if (editor != null) {
            return new ByteArrayInputStream(getText(editor).getBytes());
        } else {
            return file.getInputStream();
        }
    }

    private Editor getEditor(Project project, Editor editor, VirtualFile file) {
        if (editor == null) {
            TextEditor textEditor = getTextEditor(project, file);
            if (textEditor != null) {
                return textEditor.getEditor();
            }
        }
        return editor;
    }

    private TextEditor getTextEditor(Project project, VirtualFile file) {
        FileEditor fileEditor = FileEditorManager.getInstance(project).getSelectedEditor(file);
        if (fileEditor instanceof TextEditor) {
            return (TextEditor) fileEditor;
        }
        return null;
    }

    private String getText(Editor editor) {
        return editor.getDocument().getText();
    }
}
