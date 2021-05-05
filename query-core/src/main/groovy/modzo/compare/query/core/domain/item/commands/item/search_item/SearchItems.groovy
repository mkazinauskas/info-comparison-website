package modzo.compare.query.core.domain.item.commands.item.search_item

class SearchItems {
    String name
    String description

    int page
    int size

    SearchItems(String name, String description, int page, int size) {
        this.name = name
        this.description = description
        this.page = page
        this.size = size
    }
}
