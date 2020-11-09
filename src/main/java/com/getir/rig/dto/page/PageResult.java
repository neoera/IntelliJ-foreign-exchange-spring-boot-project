package com.getir.rig.dto.page;

import lombok.Data;

@Data
class PageResult {
    long getTotalElements; // number of total elements in the table
    int totalPages; // number of total pages in the table
}
