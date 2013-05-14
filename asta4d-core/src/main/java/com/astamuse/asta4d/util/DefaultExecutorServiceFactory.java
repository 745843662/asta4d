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

package com.astamuse.asta4d.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultExecutorServiceFactory implements ExecutorServiceFactory {

    private final static AtomicInteger instanceCounter = new AtomicInteger();

    private AtomicInteger counter = new AtomicInteger();

    private ExecutorService es = null;

    public DefaultExecutorServiceFactory() {
        this(200);
    }

    public DefaultExecutorServiceFactory(int poolSize) {
        this("asta4d", poolSize);
    }

    public DefaultExecutorServiceFactory(final String threadName, int poolSize) {
        final int instanceId = instanceCounter.incrementAndGet();
        es = Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                String name = threadName + "-" + instanceId + "-t-" + counter.incrementAndGet();
                return new Thread(r, name);
            }
        });
    }

    @Override
    public ExecutorService getExecutorService() {
        return es;
    }

    @Override
    protected void finalize() throws Throwable {
        es.shutdown();
        super.finalize();
    }

}
