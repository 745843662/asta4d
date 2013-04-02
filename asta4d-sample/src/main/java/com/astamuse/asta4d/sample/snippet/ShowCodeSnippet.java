/*
 * Copyright 2012 astamuse company,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.astamuse.asta4d.sample.snippet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;

import com.astamuse.asta4d.render.GoThroughRenderer;
import com.astamuse.asta4d.render.Renderer;
import com.astamuse.asta4d.util.ElementUtil;

public class ShowCodeSnippet {
    private static final String JAVA_PACKAGE = "/com/astamuse/asta4d/sample";
    private static final String VM_ARG = "asta4d.sample.source_location";
    private static final String SHOW_MARK = "@ShowCode:";

    public Renderer showCode(HttpServletRequest request, String file, String startMark, String endMark, String title) {
        Renderer render = new GoThroughRenderer();
        ServletContext servletContext = request.getSession().getServletContext();
        String contents = readFileByLines(servletContext, file, SHOW_MARK + startMark, SHOW_MARK + endMark);
        render.add("div", makeShowHtml(file, title, contents));

        return render;
    }

    private Element makeShowHtml(String file, String title, String contents) {
        // create the pre tag
        Element pre = new Element(Tag.valueOf("pre"), "");
        pre.addClass("prettyprint source");
        pre.attr("style", "overflow-x:auto");
        List<Node> preChildren = new ArrayList<>();
        if (contents != null) {
            preChildren.add(new Element(Tag.valueOf("span"), "").appendText(contents));
        }
        ElementUtil.appendNodes(pre, preChildren);

        // create the article tag
        Element article = new Element(Tag.valueOf("article"), "");
        List<Node> articleChildren = new ArrayList<>();
        if (title == null) {
            if (file != null) {
                articleChildren.add(new Element(Tag.valueOf("div"), "").appendText(file));
            }
        } else {
            articleChildren.add(new Element(Tag.valueOf("div"), "").appendText(title));
        }
        articleChildren.add(pre);
        ElementUtil.appendNodes(article, articleChildren);

        // create the section tag
        Element section = new Element(Tag.valueOf("section"), "");
        List<Node> sectionChildren = new ArrayList<>();
        sectionChildren.add(article);
        ElementUtil.appendNodes(section, sectionChildren);

        return section;
    }

    private static String readFileByLines(ServletContext servletContext, String fileName, String startMark, String endMark) {
        String filePath = "";
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            // read the file
            if (fileName.endsWith(".java")) {
                String source_location = System.getProperty(VM_ARG);
                if (source_location != null) {
                    filePath = source_location + JAVA_PACKAGE + fileName;
                    inputStream = new FileInputStream(filePath);
                } else {
                    filePath = "/WEB-INF/src" + JAVA_PACKAGE + fileName;
                    inputStream = servletContext.getResourceAsStream(filePath);
                }
            } else {
                inputStream = servletContext.getResourceAsStream(fileName);
            }

            if (inputStream == null) {
                return null;
            }

            // find the line that has the mark
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            String contents = "";
            boolean isMark = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(endMark)) {
                    isMark = false;
                }
                if (isMark && !line.contains(SHOW_MARK)) {
                    contents = contents + line + "\n";
                }
                if (line.contains(startMark)) {
                    isMark = true;
                }
            }

            return contents;
        } catch (IOException e) {
            return null;

            // close
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
