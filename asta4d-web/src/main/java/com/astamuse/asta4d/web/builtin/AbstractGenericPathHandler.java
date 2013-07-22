package com.astamuse.asta4d.web.builtin;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;

import com.astamuse.asta4d.Context;
import com.astamuse.asta4d.web.WebApplicationContext;
import com.astamuse.asta4d.web.dispatch.mapping.UrlMappingRule;

public abstract class AbstractGenericPathHandler {
    public final static String VAR_BASEPATH = "basePath";

    private final static ConcurrentHashMap<String, String> genericMapResult = new ConcurrentHashMap<>();

    private String _basePath = null;

    public AbstractGenericPathHandler() {
    }

    public AbstractGenericPathHandler(String basePath) {
        this._basePath = basePath;
    }

    public String convertPath(HttpServletRequest request, UrlMappingRule currentRule) {
        String uri = request.getRequestURI();

        String targetPath = genericMapResult.get(uri);
        if (targetPath != null) {
            return targetPath;
        } else {
            Context context = Context.getCurrentThreadContext();
            String basePath = context.getData(WebApplicationContext.SCOPE_PATHVAR, VAR_BASEPATH);
            if (basePath == null) {
                basePath = _basePath;
            }

            if (basePath == null) {// default from web context root
                targetPath = uri;
            } else {
                String src = currentRule.getSourcePath();
                // convert for /**/*
                String mask = "/**/*";
                int idx = src.indexOf(mask);
                if (idx >= 0) {
                    String parentPath = src.substring(0, idx);
                    String childPath = uri.substring(parentPath.length());

                    if (basePath.endsWith("/")) {
                        basePath = basePath.substring(0, basePath.length() - 1);
                    }
                    if (!childPath.startsWith("/")) {
                        childPath = "/" + childPath;
                    }
                    targetPath = basePath + childPath;
                } else {// be a one 2 one mapping
                    targetPath = basePath;
                }
            }

            if (fileNameSecurityCheck(targetPath)) {
                genericMapResult.put(uri, targetPath);
                return targetPath;
            } else {
                return null;
            }
        }
    }

    private boolean fileNameSecurityCheck(String path) {
        // we do not allow any unnormalized path for security reason
        String normalizedPath = FilenameUtils.normalize(path, true);
        return path.equals(normalizedPath);
    }
}
