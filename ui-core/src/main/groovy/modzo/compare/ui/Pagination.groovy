package modzo.compare.ui;

import org.springframework.hateoas.PagedResources;

class Pagination {
    boolean hasPrevious
    boolean hasNext
    long previous
    long current
    long next
    long totalPages

    Pagination(PagedResources.PageMetadata metadata) {
        hasPrevious = metadata.number - 1 >= 0
        previous = metadata.number - 1 >= 0 ? metadata.number - 1 : 0 as long
        hasNext = metadata.number + 1 < metadata.totalPages
        next = metadata.number + 1 < metadata.totalPages ? metadata.number + 1 : metadata.totalPages as long
        current = metadata.number
        totalPages = metadata.totalPages
    }
}
