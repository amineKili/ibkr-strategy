package com.ib.ai.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Triplet<U, V, W> {
    private U first;
    private V second;
    private W third;
}
