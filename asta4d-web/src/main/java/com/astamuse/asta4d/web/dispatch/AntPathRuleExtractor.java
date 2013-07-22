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

package com.astamuse.asta4d.web.dispatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.astamuse.asta4d.web.copyleft.SpringAntPathMatcher;
import com.astamuse.asta4d.web.dispatch.mapping.UrlMappingResult;
import com.astamuse.asta4d.web.dispatch.mapping.UrlMappingRule;

/**
 * Use the spring mvc ant path matching rule to find matched rule. <br>
 * 
 * @author e-ryu
 * 
 */
public class AntPathRuleExtractor implements DispatcherRuleExtractor {
    private SpringAntPathMatcher pathMatcher = new SpringAntPathMatcher();

    @Override
    public UrlMappingResult findMappedRule(List<UrlMappingRule> ruleList, HttpMethod method, String uri, String queryString) {
        UrlMappingResult mappingResult = null;
        String srcPath;
        HttpMethod ruleMethod;
        for (UrlMappingRule rule : ruleList) {
            ruleMethod = rule.getMethod();
            if (ruleMethod != null) {
                if (ruleMethod != method) {
                    continue;
                }
            }
            srcPath = rule.getSourcePath();
            if (pathMatcher.match(srcPath, uri)) {
                mappingResult = new UrlMappingResult();
                Map<String, Object> pathVarMap = new HashMap<>();
                pathVarMap.putAll(pathMatcher.extractUriTemplateVariables(srcPath, uri));
                mappingResult.setPathVarMap(pathVarMap);
                mappingResult.setRule(rule);
                break;
            }
        }

        return mappingResult;

    }
}
