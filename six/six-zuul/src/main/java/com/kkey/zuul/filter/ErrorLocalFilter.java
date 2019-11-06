package com.kkey.zuul.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ErrorLocalFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable throwable = currentContext.getThrowable();
        throwable.printStackTrace();

        try {
            currentContext.getResponse().setStatus(-1);
            currentContext.getResponse().getWriter().append("后台服务异常");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
