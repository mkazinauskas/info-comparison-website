package modzo.compare.ui.items;

import modzo.compare.query.core.api.photos.PhotosResource;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "${application.queryServiceName}", url = "${application.queryServiceUrl}")
public interface ItemPhotosClient extends PhotosResource {
}
