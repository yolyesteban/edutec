/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estebanbank.filter;

import com.estebanbank.annotations.CacheControl;
import java.io.IOException;
import java.lang.annotation.Annotation;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Yoly
 */
@Provider
@CacheControl
public class CacheControlFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext pRequestContext, ContainerResponseContext pResponseContext)
            throws IOException {

        for (Annotation a : pResponseContext.getEntityAnnotations()) {
            if (a.annotationType() == CacheControl.class) {
                String value = ((CacheControl) a).value();
                pResponseContext.getHeaders().putSingle(HttpHeaders.CACHE_CONTROL, value);
                break;
            }
        }

        pResponseContext.getHeaders().putSingle("X-UA-Compatible", "IE=edge");
    }
}