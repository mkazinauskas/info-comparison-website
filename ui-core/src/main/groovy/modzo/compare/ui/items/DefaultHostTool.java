package modzo.compare.ui.items;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class DefaultHostTool implements HostTool {

    private final HttpServletRequest request;

    public DefaultHostTool(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getRedirectLink(String url) {
        String host = request.getHeader("X-FORWARDED-HOST");
        if (host == null) {
            return "redirect:" + url;
        }
        return "redirect://" + host + url;
    }
}
