package app.dto.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Найденные рейсы.
 */
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SearchResult {

    @NotNull
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @ToString.Exclude
    private List<SearchResultCard> flights;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Search search;
}