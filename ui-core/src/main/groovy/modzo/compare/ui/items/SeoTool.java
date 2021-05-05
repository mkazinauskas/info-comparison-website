package modzo.compare.ui.items;

public class SeoTool {
    public static String make(String url){
        return url
                .toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("[^a-zA-Z0-9,-]", "");
    }
}
