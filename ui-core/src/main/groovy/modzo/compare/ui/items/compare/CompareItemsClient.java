package modzo.compare.ui.items.compare;

import modzo.compare.query.core.api.compare_items.CompareItemsResource;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "${application.queryServiceName}", url = "${application.queryServiceUrl}")
public interface CompareItemsClient extends CompareItemsResource {
}
