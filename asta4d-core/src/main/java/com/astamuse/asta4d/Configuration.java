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

package com.astamuse.asta4d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.astamuse.asta4d.data.ContextDataFinder;
import com.astamuse.asta4d.data.DefaultContextDataFinder;
import com.astamuse.asta4d.format.PlaceholderFormatter;
import com.astamuse.asta4d.format.SymbolPlaceholderFormatter;
import com.astamuse.asta4d.interceptor.PageInterceptor;
import com.astamuse.asta4d.snippet.DefaultSnippetInvoker;
import com.astamuse.asta4d.snippet.SnippetInvoker;
import com.astamuse.asta4d.snippet.extract.DefaultSnippetExtractor;
import com.astamuse.asta4d.snippet.extract.SnippetExtractor;
import com.astamuse.asta4d.snippet.resolve.DefaultSnippetResolver;
import com.astamuse.asta4d.snippet.resolve.SnippetResolver;
import com.astamuse.asta4d.template.FileTemplateResolver;
import com.astamuse.asta4d.template.TemplateResolver;
import com.astamuse.asta4d.util.DefaultExecutorServiceFactory;
import com.astamuse.asta4d.util.ExecutorServiceFactory;
import com.astamuse.asta4d.util.collection.ParallelRecursivePolicy;

public class Configuration {

    private TemplateResolver templateResolver = new FileTemplateResolver();

    private SnippetInvoker snippetInvoker = new DefaultSnippetInvoker();

    private SnippetResolver snippetResolver = new DefaultSnippetResolver();

    private SnippetExtractor snippetExtractor = new DefaultSnippetExtractor();

    private List<PageInterceptor> pageInterceptorList = new ArrayList<>();

    private ContextDataFinder contextDataFinder = new DefaultContextDataFinder();

    private List<String> resourceNames = null;

    private PlaceholderFormatter placeholderFormatter = new SymbolPlaceholderFormatter();

    private boolean cacheEnable = true;

    private boolean skipSnippetExecution = false;

    private boolean outputAsPrettyPrint = false;

    private boolean blockParallelListRendering = false;

    private ExecutorServiceFactory snippetExecutorFactory = new DefaultExecutorServiceFactory("asta4d-snippet", 200);

    private ExecutorServiceFactory listExecutorFactory = new DefaultExecutorServiceFactory("asta4d-list", 600);

    private ParallelRecursivePolicy parallelRecursivePolicyForListRendering = ParallelRecursivePolicy.EXCEPTION;

    /**
     * at present, the following items are regarded as global settings
     */
    private List<String> reverseInjectableScopes = Arrays.asList(Context.SCOPE_DEFAULT, Context.SCOPE_GLOBAL);

    private List<String> clearNodeClasses = new ArrayList<>();

    private String tagNameSpace = "afd";

    public TemplateResolver getTemplateResolver() {
        return templateResolver;
    }

    public void setTemplateResolver(TemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    public SnippetInvoker getSnippetInvoker() {
        return snippetInvoker;
    }

    public void setSnippetInvoker(SnippetInvoker snippetInvoker) {
        this.snippetInvoker = snippetInvoker;
    }

    public SnippetResolver getSnippetResolver() {
        return snippetResolver;
    }

    public void setSnippetResolver(SnippetResolver snippetResolver) {
        this.snippetResolver = snippetResolver;
    }

    public SnippetExtractor getSnippetExtractor() {
        return snippetExtractor;
    }

    public void setSnippetExtractor(SnippetExtractor snippetExtractor) {
        this.snippetExtractor = snippetExtractor;
    }

    public List<PageInterceptor> getPageInterceptorList() {
        return pageInterceptorList;
    }

    public void setPageInterceptorList(List<PageInterceptor> pageInterceptorList) {
        this.pageInterceptorList = pageInterceptorList;
    }

    public ContextDataFinder getContextDataFinder() {
        return contextDataFinder;
    }

    public void setContextDataFinder(ContextDataFinder contextDataFinder) {
        this.contextDataFinder = contextDataFinder;
    }

    public List<String> getResourceNames() {
        if (resourceNames == null) {
            resourceNames = new ArrayList<>();
            resourceNames.add("messages");
        }
        return new ArrayList<String>(resourceNames);
    }

    public void setResourceNames(List<String> resourceNames) {
        this.resourceNames = Collections.unmodifiableList(resourceNames);
    }

    public PlaceholderFormatter getPlaceholderFormatter() {
        return placeholderFormatter;
    }

    public void setPlaceholderFormatter(PlaceholderFormatter placeholderFormatter) {
        this.placeholderFormatter = placeholderFormatter;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public boolean isSkipSnippetExecution() {
        return skipSnippetExecution;
    }

    public void setSkipSnippetExecution(boolean skipSnippetExecution) {
        this.skipSnippetExecution = skipSnippetExecution;
    }

    public ExecutorServiceFactory getSnippetExecutorFactory() {
        return snippetExecutorFactory;
    }

    public void setSnippetExecutorFactory(ExecutorServiceFactory snippetExecutorFactory) {
        this.snippetExecutorFactory = snippetExecutorFactory;
    }

    public ExecutorServiceFactory getListExecutorFactory() {
        return listExecutorFactory;
    }

    public void setListExecutorFactory(ExecutorServiceFactory listExecutorFactory) {
        this.listExecutorFactory = listExecutorFactory;
    }

    public ParallelRecursivePolicy getParallelRecursivePolicyForListRendering() {
        return parallelRecursivePolicyForListRendering;
    }

    public void setParallelRecursivePolicyForListRendering(ParallelRecursivePolicy parallelRecursivePolicyForListRendering) {
        this.parallelRecursivePolicyForListRendering = parallelRecursivePolicyForListRendering;
    }

    public boolean isOutputAsPrettyPrint() {
        return outputAsPrettyPrint;
    }

    public void setOutputAsPrettyPrint(boolean outputAsPrettyPrint) {
        this.outputAsPrettyPrint = outputAsPrettyPrint;
    }

    public boolean isBlockParallelListRendering() {
        return blockParallelListRendering;
    }

    public void setBlockParallelListRendering(boolean blockParallelListRendering) {
        this.blockParallelListRendering = blockParallelListRendering;
    }

    public List<String> getReverseInjectableScopes() {
        return reverseInjectableScopes;
    }

    public void setReverseInjectableScopes(List<String> reverseInjectableScopes) {
        this.reverseInjectableScopes = reverseInjectableScopes;
    }

    public List<String> getClearNodeClasses() {
        return clearNodeClasses;
    }

    public void setClearNodeClasses(List<String> clearNodeClasses) {
        this.clearNodeClasses = clearNodeClasses;
    }

    public String getTagNameSpace() {
        return tagNameSpace;
    }

    public void setTagNameSpace(String tagNameSpace) {
        this.tagNameSpace = tagNameSpace;
    }

}
