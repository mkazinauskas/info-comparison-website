package modzo.compare.ui.items;

import modzo.compare.query.core.api.photos.ItemPhotoBean;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PhotosService {

    private final ItemPhotosClient itemPhotosClient;

    public PhotosService(ItemPhotosClient itemPhotosClient) {
        this.itemPhotosClient = itemPhotosClient;
    }

    public ItemPhotoBean itemPhoto(String itemUniqueId) {
        return itemPhotos(itemUniqueId).stream().findFirst().orElse(null);
    }

    public List<ItemPhotoBean> itemPhotos(String itemUniqueId) {
        return itemPhotosClient.getItemPhotos(itemUniqueId)
                .getBody()
                .getContent()
                .stream()
                .sorted(Comparator.comparingLong(ItemPhotoBean::getSequence))
                .collect(toList());
    }
}
