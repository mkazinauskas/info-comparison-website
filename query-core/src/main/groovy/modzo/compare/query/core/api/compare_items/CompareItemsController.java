package modzo.compare.query.core.api.compare_items;

import modzo.compare.query.core.api.ApiException;
import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean;
import modzo.compare.query.core.api.item_properties.service.ItemPropertiesService;
import modzo.compare.query.core.api.items.ItemBeanMapper;
import modzo.compare.query.core.domain.item.Comparison;
import modzo.compare.query.core.domain.item.Comparisons;
import modzo.compare.query.core.domain.item.Item;
import modzo.compare.query.core.domain.item.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class CompareItemsController implements CompareItemsResource {

    private final Items items;

    private final ItemBeanMapper itemBeanMapper;

    private final ItemPropertiesService itemPropertiesService;

    private final ComboPropertiesService comboPropertiesService;

    private final ReportComparisonService reportComparisonService;

    private final Comparisons comparisons;


    public CompareItemsController(Items items,
                                  ItemBeanMapper itemBeanMapper,
                                  ItemPropertiesService itemPropertiesService,
                                  ComboPropertiesService comboPropertiesService,
                                  ReportComparisonService reportComparisonService,
                                  Comparisons comparisons) {
        this.items = items;
        this.itemBeanMapper = itemBeanMapper;
        this.itemPropertiesService = itemPropertiesService;
        this.comboPropertiesService = comboPropertiesService;
        this.reportComparisonService = reportComparisonService;
        this.comparisons = comparisons;
    }

    @Override
    public ResponseEntity<CompareItemsBean> compareItems(@RequestParam("firstItemUniqueId") String firstItemUniqueId,
                                                         @RequestParam("secondItemUniqueId") String secondItemUniqueId) {

        Item firstItem = items.findByUniqueId(firstItemUniqueId)
                .orElseThrow(() -> new ApiException("ITEM_BY_UNIQUE_ID_DOES_NOT_EXIST",
                        "First item with id = ${firstItemUniqueId} was not found",
                        HttpStatus.NOT_FOUND));


        Item secondItem = items.findByUniqueId(secondItemUniqueId)
                .orElseThrow(() -> new ApiException("ITEM_BY_UNIQUE_ID_DOES_NOT_EXIST",
                        "First item with id = ${secondItemUniqueId} was not found",
                        HttpStatus.NOT_FOUND));

        List<ItemPropertyDescriptorBean> firstItemProperties = itemPropertiesService
                .getItemProperties(firstItem.getUniqueId());

        List<ItemPropertyDescriptorBean> secondItemProperties = itemPropertiesService
                .getItemProperties(secondItem.getUniqueId());

        reportComparisonService.report(firstItemUniqueId, secondItemUniqueId);

        CompareItemsBean result = new CompareItemsBean();
        result.setFirstItem(itemBeanMapper.map(firstItem));
        result.setSecondItem(itemBeanMapper.map(secondItem));
        result.setComboProperties(comboPropertiesService.map(firstItemProperties, secondItemProperties));
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<PagedResources<ComparisonBean>> getComparisons(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "size", defaultValue = "100") int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "sequence"));
        Page<Comparison> byOrderBySequence = comparisons.findAll(pageRequest);

        List<ComparisonBean> collect = byOrderBySequence.getContent()
                .stream()
                .map(this::toComparisonBean)
                .collect(Collectors.toList());

        PagedResources.PageMetadata metadata = new PagedResources.PageMetadata(
                byOrderBySequence.getSize(),
                byOrderBySequence.getNumber(),
                byOrderBySequence.getTotalElements(),
                byOrderBySequence.getTotalPages()
        );
        return ResponseEntity.ok(new PagedResources<>(collect, metadata));
    }

    private ComparisonBean toComparisonBean(Comparison comparison) {
        ComparisonBean comparisonBean = new ComparisonBean();
        comparisonBean.setUniqueId(comparison.getUniqueId());
        comparisonBean.setFirstItemUniqueId(comparison.getFirstItemUniqueId());
        comparisonBean.setFirstItemName(
                items.findByUniqueId(comparison.getFirstItemUniqueId()).map(Item::getName).orElse("")
        );
        comparisonBean.setSecondItemUniqueId(comparison.getSecondItemUniqueId());
        comparisonBean.setSecondItemName(
                items.findByUniqueId(comparison.getSecondItemUniqueId()).map(Item::getName).orElse("")
        );
        return comparisonBean;
    }
}

