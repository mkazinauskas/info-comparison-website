package modzo.compare.ui.items;

import modzo.compare.query.core.api.items.ItemsResource;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "${application.queryServiceName}", url = "${application.queryServiceUrl}")
public interface ItemsResourceClient extends ItemsResource {
}
