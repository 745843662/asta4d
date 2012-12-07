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

package com.astamuse.asta4d.misc.spring.mvc;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.astamuse.asta4d.Context;
import com.astamuse.asta4d.template.TemplateException;
import com.astamuse.asta4d.web.dispatch.response.provider.Asta4DPageProvider;

public class SpringWebPageView implements View {

    private Asta4DPageProvider templateProvider;

    public SpringWebPageView(Asta4DPageProvider templateProvider) throws TemplateException {
        super();
        this.templateProvider = templateProvider;
    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Context context = Context.getCurrentThreadContext();
        for (Entry<String, ?> entry : model.entrySet()) {
            context.setData(entry.getKey(), entry.getValue());
        }
        // templateProvider.produce(response);
    }

}
