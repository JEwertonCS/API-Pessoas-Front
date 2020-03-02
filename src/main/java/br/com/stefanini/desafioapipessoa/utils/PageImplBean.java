package br.com.stefanini.desafioapipessoa.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageImplBean<T> extends PageImpl<T> {

    private boolean last;
    private boolean first;
    private int totalPages;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageImplBean(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int page,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") Long totalElements,
                        @JsonProperty("pageable")JsonNode pageable,
                        @JsonProperty("first") boolean first,
                        @JsonProperty("last") boolean last,
                        @JsonProperty("totalPages") int totalPages,
                        @JsonProperty("sort") JsonNode sort,
                        @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(page, size), totalElements);
    }

    public PageImplBean() {
        super(new ArrayList<T>());
    }

    @Override
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
