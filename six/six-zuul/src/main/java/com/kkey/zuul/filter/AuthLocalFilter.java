package com.kkey.zuul.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthLocalFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        if (user == null || password == null ||
                !user.equalsIgnoreCase("kkey") ||
                !password.equalsIgnoreCase("6666"))
        {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(400);
            try {
                HttpServletResponse response = currentContext.getResponse();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().append("认证失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
