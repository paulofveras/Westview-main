package br.unitins.comics.util;

import java.util.List;

public record PageResult<T>(
        int page,
        int pageSize,
        long totalRecords,
        long filteredRecords,
        List<T> data
) {}
