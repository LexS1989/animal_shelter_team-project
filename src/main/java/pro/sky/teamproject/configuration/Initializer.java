package pro.sky.teamproject.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pro.sky.teamproject.listener.TelegramBotUpdatesListener;

@Component
public class Initializer {
    private final Logger logger = LoggerFactory.getLogger(Initializer.class);

    final TelegramBotUpdatesListener telegramBotUpdatesListener;

    public Initializer(TelegramBotUpdatesListener telegramBotUpdatesListener) {
        this.telegramBotUpdatesListener = telegramBotUpdatesListener;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBotUpdatesListener);
        } catch (TelegramApiException e) {
            logger.error("Error occurred: " + e.getMessage());
        }
    }

}
