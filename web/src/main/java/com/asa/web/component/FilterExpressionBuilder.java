package com.asa.web.component;

import org.springframework.stereotype.Component;

import com.asa.web.model.Filter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilterExpressionBuilder {

    public String buildExpression(Set<Filter> filters) {
        if (filters == null || filters.isEmpty()) {
            return "";
        }

        return filters.stream()
                .map(this::toExpression)
                .collect(Collectors.joining(" and "));  // Change to " and "
    }

    private String toExpression(Filter filter) {
        // Check if multi-valued (comma-separated)
        if (filter.getValue().contains(",")) {
            String[] values = filter.getValue().split(",");
            String joined = Arrays.stream(values)
                    .map(v -> "'" + v.trim() + "'")
                    .collect(Collectors.joining(", "));
            return filter.getName() + " in (" + joined + ")";  // Change [] to ()
        } else {
            return filter.getName() + " == '" + filter.getValue() + "'";
        }
    }
}

