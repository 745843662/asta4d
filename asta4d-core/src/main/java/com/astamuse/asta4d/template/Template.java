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

package com.astamuse.asta4d.template;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.BlockTagSupportHtmlTreeBuilder;
import org.jsoup.parser.Parser;

import com.astamuse.asta4d.Configuration;
import com.astamuse.asta4d.Context;
import com.astamuse.asta4d.extnode.ExtNodeConstants;

public class Template {

    private String path;

    private Document doc;

    /**
     * 
     * @param path
     *            not being used, just for debug purpose
     * @param input
     * @throws IOException
     */
    public Template(String path, InputStream input) throws TemplateException {
        try {
            this.path = path;
            this.doc = Jsoup.parse(input, "UTF-8", "", new Parser(new BlockTagSupportHtmlTreeBuilder()));
            // this.doc = Jsoup.parse(input, "UTF-8", "");
            initDocument();
        } catch (Exception e) {
            String msg = String.format("Template %s initialize failed.", path);
            throw new TemplateException(msg, e);
        }
    }

    private void initDocument() throws TemplateException {
        // find inject
        processExtension();
        TemplateUtil.regulateElement(doc);
    }

    private void processExtension() throws TemplateException {
        Element extension = doc.select(ExtNodeConstants.EXTENSION_NODE_TAG_SELECTOR).first();
        if (extension != null) {
            String parentPath = extension.attr(ExtNodeConstants.EXTENSION_NODE_ATTR_PARENT);
            if (parentPath == null || parentPath.isEmpty()) {
                throw new TemplateException(String.format("You must specify the parent of an extension (%s).", this.path));
            }
            Configuration conf = Context.getCurrentThreadContext().getConfiguration();
            Template parent = conf.getTemplateResolver().findTemplate(parentPath);
            Document parentDoc = parent.getDocumentClone();
            TemplateUtil.mergeBlock(parentDoc, extension);
            doc = parentDoc;
        }
    }

    public String getPath() {
        return path;
    }

    public Document getDocumentClone() {
        return doc.clone();
    }
}
