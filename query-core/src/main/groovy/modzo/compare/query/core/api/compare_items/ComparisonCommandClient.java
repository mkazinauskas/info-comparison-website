package modzo.compare.query.core.api.compare_items;

import modzo.compare.command.api.compare.ComparisonResource;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "${application.commandServiceName}", url = "http://localhost:5002")
public interface ComparisonCommandClient extends ComparisonResource {
}
