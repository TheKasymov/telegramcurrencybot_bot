package com.sanzhar.telegramcurrencybot.bot;

import com.sanzhar.telegramcurrencybot.config.BotConfig;
import com.sanzhar.telegramcurrencybot.service.CurrencyService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;

@Component
public class CurrencyBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final CurrencyService currencyService;

    public CurrencyBot(BotConfig botConfig,
                       CurrencyService currencyService) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.currencyService = currencyService;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if (text.equals("/start")) {
            send(chatId, "Send message like: USD EUR 100");
            return;
        }

        try {
            String[] parts = text.split(" ");
            String from = parts[0].toUpperCase();
            String to = parts[1].toUpperCase();
            double amount = Double.parseDouble(parts[2]);

            double result = currencyService.convert(from, to, amount);
            send(chatId, amount + " " + from + " = " + result + " " + to);

        } catch (Exception e) {
            send(chatId, "Invalid format. Example: USD EUR 100");
        }
    }

    private void send(Long chatId, String text) {
        try {
            execute(new SendMessage(chatId.toString(), text));
        } catch (TelegramApiException ignored) {
        }
    }
}