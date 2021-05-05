package modzo.compare.ui.items;

public class SeoComparisonBean {
    private final String firstItemName;
    private final String secondItemName;

    public SeoComparisonBean(String firstItemName, String secondItemName) {
        this.firstItemName = firstItemName;
        this.secondItemName = secondItemName;
    }

    public String getFirstItemName() {
        return firstItemName;
    }

    public String getSecondItemName() {
        return secondItemName;
    }

    public String getSeoFirstItemName() {
        return SeoTool.make(firstItemName);
    }

    public String getSeoSecondItemName() {
        return SeoTool.make(secondItemName);
    }
}
