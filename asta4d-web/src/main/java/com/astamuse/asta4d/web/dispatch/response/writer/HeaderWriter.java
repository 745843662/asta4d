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

package com.astamuse.asta4d.web.dispatch.response.writer;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.astamuse.asta4d.web.dispatch.mapping.UrlMappingRule;
import com.astamuse.asta4d.web.dispatch.response.provider.HeaderInfo;

public class HeaderWriter implements ContentWriter<HeaderInfo> {

    @Override
    public void writeResponse(UrlMappingRule currentRule, HttpServletResponse response, HeaderInfo info) throws Exception {
        if (info == null) {
            return;
        }
        Integer status = info.getStatus();
        if (status != null) {
            response.setStatus(status);
        }

        Set<Entry<String, String>> headers = info.getHeaderMap().entrySet();
        for (Entry<String, String> h : headers) {
            response.addHeader(h.getKey(), h.getValue());
        }

        List<Cookie> cookies = info.getCookieList();
        for (Cookie c : cookies) {
            response.addCookie(c);
        }

    }

}
