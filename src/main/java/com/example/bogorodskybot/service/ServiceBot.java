package com.example.bogorodskybot.service;


import com.example.bogorodskybot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ServiceBot extends TelegramLongPollingBot {
    final BotConfig config;

    static final String HELP_TEXT = "OOO «БОГОРОДСКИЙ»\nАдрес: г. Старая Купавна ул. Дорожная 29"
            +"\nСвязаться с диспетчером.\nТелефон: \n+7(985)999-55-90 \nTelegram: @bogorodsky_beton\nПочта: ooo.bogorodky@yandex.ru";

    static final String HELP_BOSS = "OOO «БОГОРОДСКИЙ»\nАдрес: г. Старая Купавна ул. Дорожная 29"
            +"\nРуководитель: \nПетросян Эдуард Грачикович\nТелефон: \n+7(995)900-57-27\nTelegram: @bogorodsky_beton\nПочта: bogorodsky2022@gmail.com";

    static final String NAVIGATOR = "Навигатор: https://yandex.ru/navi/?whatshere%5Bzoom%5D=18&whatshere%5Bpoint%5D=38.202398%2C55.792823&lang=ru&from=navi\n" +
            "Оставить заявку: https://goo.su/LtcqaA";

    static final String DELIVERY_TEXT = "Доставка осуществляется 9м3 автомиксерами. \n10 км - 4 500,00₽"+
            "\n15 км - 4 950,00₽\n20 км - 5 400,00₽\n25 км - 5 850,00₽\n30 км - 6 300,00₽\n35 км - 6 750,00₽\n40 км - 7 200,00₽\nСвыше 40 км по договоренности." +
            "\nМестоположение завода: /navigate";


    static final String PRICE100 = "5 000,00₽";
    static final String PRICE150 = "5 200,00₽";
    static final String PRICE200 = "5 400,00₽";
    static final String PRICE200_GRANIT = "5 950,00₽";
    static final String PRICE250 = "5 600,00₽";
    static final String PRICE250_GRANIT = "6 050,00₽";
    static final String PRICE300 = "5 800,00₽";
    static final String PRICE300_GRANIT = "6 300,00₽";
    static final String PRICE350 = "6 000,00₽";
    static final String PRICE350_GRANIT = "6 500,00₽";
    static final String PRICE400 = "6 200,00₽";
    static final String PRICE400_GRANIT = "6 600,00₽";
    static final String PRICE450 = "6 400,00₽";
    static final String PRICE450_GRANIT = "6 700,00₽";
    static final String TOSHI100 = "4 000,00₽";
    static final String TOSHI150 = "4 200,00₽";
    static final String TOSHI200 = "4 400,00₽";
    static final String RASTVOR100 = "3 400,00₽";
    static final String RASTVOR150 = "3 500,00₽";
    static final String RASTVOR200 = "3 700,00₽";
    static final String RASTVOR250 = "3 850,00₽";
    static final String RASTVOR300 = "4 000,00₽";

    @Autowired
    public ServiceBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Стоимость бетона"));
        listofCommands.add(new BotCommand("/dostavka", "Стоимость доставки"));
        listofCommands.add(new BotCommand("/navigate", "Навигатор"));
        listofCommands.add(new BotCommand("/help", "Связь с диспетчером"));
        listofCommands.add(new BotCommand("/boss", "Связь с руководителем"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/dostavka":
                    sendMessage(chatId, DELIVERY_TEXT);
                    break;
                case "/navigate":
                    sendMessage(chatId, NAVIGATOR);
                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                case "/boss":
                    sendMessage(chatId, HELP_BOSS);
                    break;
                default:
                    getStockByTickerBot(chatId, messageText);
                    break;
            }
        }

    }


    private void getStockByTickerBot(long chatId, String messageText) {
        switch (messageText) {
            case "M100":
            case "М100":
            case "m100":
            case "м100":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE100 + " за руб/м3\nКласс: БСТ B7,5 П4 F50 W2\nСтоимость доставки: \n/dostavka");
                break;
            case "M150":
            case "М150":
            case "m150":
            case "м150":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE150 + " за руб/м3\nКласс: БСТ B12,5 П4 F75 W2 или W4\nСтоимость доставки: \n/dostavka");
                break;
            case "M200":
            case "М200":
            case "m200":
            case "м200":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE200 + " за руб/м3\nКласс: БСТ B15 П4 F100 W4\nСтоимость доставки: \n/dostavka");
                break;
            case "M200 гранит":
            case "М200 гранит":
            case "m200 гранит":
            case "м200 гранит":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE200_GRANIT + " за руб/м3\nКласс: БСТ B15 П4 F100 W4\nСтоимость доставки: \n/dostavka");
                break;
            case "M250":
            case "М250":
            case "m250":
            case "м250":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE250 + " за руб/м3\nКласс: БСТ B20 П4 F100 W4\nСтоимость доставки: \n/dostavka");
                break;
            case "M250 гранит":
            case "М250 гранит":
            case "m250 гранит":
            case "м250 гранит":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE250_GRANIT + " за руб/м3\nКласс: БСТ B20 П4 F100 W4\nСтоимость доставки: \n/dostavka");
                break;
            case "M300":
            case "М300":
            case "m300":
            case "м300":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE300 + " за руб/м3\nКласс: БСТ B22,5 П4 F150 W6\nСтоимость доставки: \n/dostavka");
                break;
            case "M300 гранит":
            case "М300 гранит":
            case "m300 гранит":
            case "м300 гранит":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE300_GRANIT + " за руб/м3\nКласс: БСТ B22,5 П4 F150 W6\nСтоимость доставки: \n/dostavka");
                break;
            case "M350":
            case "М350":
            case "m350":
            case "м350":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE350 + " за руб/м3\nКласс: БСТ B25 П4 F200 W8\nСтоимость доставки: \n/dostavka");
                break;
            case "M350 гранит":
            case "М350 гранит":
            case "m350 гранит":
            case "м350 гранит":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE350_GRANIT + " за руб/м3\nКласс: БСТ B25 П4 F200 W8\nСтоимость доставки: \n/dostavka");
                break;
            case "M400":
            case "М400":
            case "m400":
            case "м400":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE400 + " за руб/м3\nКласс: БСТ B30 П4 F200 W8\nСтоимость доставки: \n/dostavka");
                break;
            case "M400 гранит":
            case "М400 гранит":
            case "m400 гранит":
            case "м400 гранит":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE400_GRANIT + " за руб/м3\nКласс: БСТ B30 П4 F200 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "M450":
            case "М450":
            case "m450":
            case "м450":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE450 + " за руб/м3\nКласс: БСТ B35 П4 F200 W8\nСтоимость доставки: \n/dostavka");
                break;
            case "M450 гранит":
            case "М450 гранит":
            case "m450 гранит":
            case "м450 гранит":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + PRICE450_GRANIT + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Тощий М100":
            case "тощий М100":
            case "Тощий м100":
            case "тощий м100":
            case "Тощий M100":
            case "тощий m100":
            case "Тощий m100":
            case "тощий M100":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + TOSHI100 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Тощий М150":
            case "тощий М150":
            case "Тощий м150":
            case "тощий м150":
            case "Тощий M150":
            case "тощий m150":
            case "Тощий m150":
            case "тощий M150":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + TOSHI150 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Тощий М200":
            case "тощий М200":
            case "Тощий м200":
            case "тощий м200":
            case "Тощий M200":
            case "тощий m200":
            case "Тощий m200":
            case "тощий M200":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + TOSHI200 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Раствор М100":
            case "раствор М100":
            case "Раствор м100":
            case "раствор м100":
            case "Раствор M100":
            case "раствор m100":
            case "Раствор m100":
            case "раствор M100":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + RASTVOR100 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Раствор М150":
            case "раствор М150":
            case "Раствор м150":
            case "раствор м150":
            case "Раствор M150":
            case "раствор m150":
            case "Раствор m150":
            case "раствор M150":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + RASTVOR150 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Раствор М200":
            case "раствор М200":
            case "Раствор м200":
            case "раствор м200":
            case "Раствор M200":
            case "раствор m200":
            case "Раствор m200":
            case "раствор M200":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + RASTVOR200 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Раствор М250":
            case "раствор М250":
            case "Раствор м250":
            case "раствор м250":
            case "Раствор M250":
            case "раствор m250":
            case "Раствор m250":
            case "раствор M250":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + RASTVOR250 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            case "Раствор М300":
            case "раствор М300":
            case "Раствор м300":
            case "раствор м300":
            case "Раствор M300":
            case "раствор m300":
            case "Раствор m300":
            case "раствор M300":
                sendMessage(chatId, "Марка: " + messageText + "\nЦена: " + RASTVOR300 + " за руб/м3\nКласс: БСТ B35 П4 F300 W10\nСтоимость доставки: \n/dostavka");
                break;
            default:
                sendMessage(chatId, "Извините марка: «" + messageText + "» Не найдена.\n" +
                        "Формат марок для поиска: \nM100\nM150\nM200\nМ200 гранит\nМ250\nM250 гранит\nM300\nM300 гранит\nМ350\nM350 гранит\nМ400\nM400 гранит\nM450\nM450 гранит\nТощий М100" +
                        "\nТощий М150\nТощий М200\nРаствор М100\nРаствор М150\nРаствор М200\nРаствор М250\nРаствор М300" +
                        "\n\nНажмите кнопку /help чтобы связаться с диспетчером!");
                break;
        }
    }

    private void startCommandReceived(long chatId, String firstName) {
        String answer = "Привет, " + firstName + "!\nВас приветсвует виртуальный ассистент Бетонного завода: \nOOO «БОГОРОДСКИЙ»\nАдрес: г. Старая Купавна ул. Дорожная 29\n"
                + "\nУкажите нужную марку бетона для уточнения цены.\nНапример: М100 или M200 гранит";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            log.error("Exception ", ex);
        }
    }
}
