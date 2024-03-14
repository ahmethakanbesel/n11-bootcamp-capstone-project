package com.n11.restaurantservice.common;

import java.time.LocalDateTime;

public record GenericErrorMessage(LocalDateTime date, String message, String description) {
}
