package modzo.compare.query.core.api.items

class ItemBean {
    String uniqueId

    String name

    String description

    String nameAsSeoString() {
        return name?.replaceAll(' ', '-')?.toLowerCase()
    }
}
