package com.astamuse.asta4d.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;

public class BinaryDataUtil {

    public final static InputStream retrieveInputStreamByPath(ServletContext servletContext, ClassLoader classLoader, String path) {
        if (path.startsWith("file://")) {
            try {
                return new FileInputStream(path);
            } catch (FileNotFoundException e) {
                return null;
            }
        } else if (path.startsWith("classpath:")) {
            String cls = path.substring("classpath".length());
            return classLoader.getResourceAsStream(cls);
        } else {
            return servletContext.getResourceAsStream(path);
        }
    }

    /**
     * 
     * @param servletContext
     * @param classLoader
     * @param path
     * @return 0 when something is wrong or the actual last modified time of the
     *         resource for given path
     */
    public final static long retrieveLastModifiedByPath(ServletContext servletContext, ClassLoader classLoader, String path) {
        if (path.startsWith("file://")) {
            return new File(path).lastModified();
        } else if (path.startsWith("classpath:")) {
            String cls = path.substring("classpath".length());
            return retriveLastModifiedFromURL(classLoader.getResource(cls));
        } else {
            try {
                return retriveLastModifiedFromURL(servletContext.getResource(path));
            } catch (MalformedURLException e) {
                return 0L;
            }
        }
    }

    private final static long retriveLastModifiedFromURL(URL url) {
        try {
            URLConnection con = url.openConnection();
            try {
                return con.getLastModified();
            } catch (Exception ex) {
                return 0L;
            } finally {
                con.getInputStream().close();
            }
        } catch (IOException e) {
            return 0L;
        }
    }

}
