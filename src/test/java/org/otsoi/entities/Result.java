package org.otsoi.entities;

import java.time.ZonedDateTime;

public record Result (int value, int minValue, int maxValue, ZonedDateTime generatedDate){
}