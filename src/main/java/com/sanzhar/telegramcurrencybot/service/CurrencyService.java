package com.sanzhar.telegramcurrencybot.service;

public interface CurrencyService {

    double convert(String from, String to, double amount);
}