package com.sanzhar.telegramcurrencybot.repository;

import com.sanzhar.telegramcurrencybot.model.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionHistoryRepository
        extends JpaRepository<ConversionHistory, Long> {
}
