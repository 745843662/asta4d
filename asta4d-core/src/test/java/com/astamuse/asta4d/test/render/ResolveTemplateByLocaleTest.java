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

package com.astamuse.asta4d.test.render;

import java.util.Locale;

import org.testng.annotations.Test;

import com.astamuse.asta4d.Context;
import com.astamuse.asta4d.test.render.infra.BaseTest;
import com.astamuse.asta4d.test.render.infra.SimpleCase;

public class ResolveTemplateByLocaleTest extends BaseTest {

    @Test
    public void currentLocaleJaJP() {
        Context.getCurrentThreadContext().setCurrentLocale(Locale.JAPAN);
        new SimpleCase("ResolveTemplateByLocale.html", "ResolveTemplateByLocale_ja_JP.html");
    }

    @Test
    public void currentLocaleJa() {
        Context.getCurrentThreadContext().setCurrentLocale(Locale.JAPANESE);
        new SimpleCase("ResolveTemplateByLocale.html", "ResolveTemplateByLocale.html");
    }

    @Test
    public void currentLocaleEnUS() {
        Context.getCurrentThreadContext().setCurrentLocale(Locale.US);
        new SimpleCase("ResolveTemplateByLocale.html", "ResolveTemplateByLocale_en.html");
    }

    @Test
    public void currentLocaleEnGB() {
        Context.getCurrentThreadContext().setCurrentLocale(Locale.UK);
        new SimpleCase("ResolveTemplateByLocale.html", "ResolveTemplateByLocale_en.html");
    }

    @Test
    public void currentLocaleDeDE() {
        Context.getCurrentThreadContext().setCurrentLocale(Locale.GERMANY);
        new SimpleCase("ResolveTemplateByLocale.html", "ResolveTemplateByLocale.html");
    }
}
