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

package com.astamuse.asta4d.test.render.infra;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.astamuse.asta4d.Configuration;
import com.astamuse.asta4d.Context;
import com.astamuse.asta4d.snippet.resolve.DefaultSnippetResolver;
import com.astamuse.asta4d.template.ClasspathTemplateResolver;

@Test
public class BaseTest {

    public final static String ReverseInjectableScope = "ReverseInjectableScope";

    private final static Configuration configuration = new Configuration() {
        {
            ClasspathTemplateResolver templateResolver = new ClasspathTemplateResolver();
            List<String> templateBaseFolders = Arrays.asList("/com/astamuse/asta4d/test/render/templates");
            templateResolver.setSearchPathList(templateBaseFolders);
            this.setTemplateResolver(templateResolver);

            DefaultSnippetResolver snippetResolver = new DefaultSnippetResolver();
            List<String> snippetBasePackages = Arrays.asList("com.astamuse.asta4d.test.render");
            snippetResolver.setSearchPathList(snippetBasePackages);
            this.setSnippetResolver(snippetResolver);

            this.setReverseInjectableScopes(Arrays.asList(ReverseInjectableScope));
            this.setOutputAsPrettyPrint(true);
        }
    };

    @BeforeMethod
    public void initContext() {
        Context context = Context.getCurrentThreadContext();
        if (context == null) {
            context = new Context();
            context.setConfiguration(configuration);
            Context.setCurrentThreadContext(context);

        }
        context.init();
    }

    @AfterMethod
    public void clearContext() {
        Context.getCurrentThreadContext().clear();
    }

}
