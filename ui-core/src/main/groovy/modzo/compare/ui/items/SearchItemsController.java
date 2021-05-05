package modzo.compare.ui.items;

import modzo.compare.ui.ApplicationMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class SearchItemsController {

    private final ApplicationMessageSource messageSource;

    private final HostTool hostTool;

    public SearchItemsController(ApplicationMessageSource messageSource,
                                 HostTool hostTool) {
        this.messageSource = messageSource;
        this.hostTool = hostTool;
    }

    @PostMapping("/search")
    String searchItems(@RequestParam(value = "query", required = false) String query,
                       Locale locale,
                       HttpServletRequest request) {
        String allItems = messageSource.getMessage("common.links.all.items", locale);
        return hostTool.getRedirectLink(allItems + "?query=" + SeoTool.make(query));
    }
}
