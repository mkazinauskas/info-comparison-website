package modzo.compare.ui.items;

import modzo.compare.query.core.api.item_properties.ItemPropertiesResource;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "${application.queryServiceName}", url = "${application.queryServiceUrl}")
public interface ItemPropertiesClient extends ItemPropertiesResource {
}
