package org.example.photeca.service;

import org.example.photeca.config.GenericAppConfig;
import org.example.photeca.model.PriceModel;
import org.example.photeca.bot.TelegramBot;
import org.example.photeca.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class PriceChangeNotifierService {

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private PriceService priceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CryptoPriceService cryptoPriceService;

    @Autowired
    GenericAppConfig appConfig;

    @Scheduled(fixedRate = 5000)
    public void notifyUsersIfPriceChange() {
        List<PriceModel> newPrices = cryptoPriceService.getPrice();
        List<String> changedCryptocurrencies = comparePriceChange(newPrices, appConfig.getPercentChangeThreshold());
        List<User> usersToNotify = userService.getAllUsers();

        if (!changedCryptocurrencies.isEmpty()) {
            for (User user : usersToNotify) {
                changedCryptocurrencies.forEach(cryptoSymbol -> telegramBot.sendMessage(user.getChatId(), "Price change for " + cryptoSymbol));
            }
        }
    }

    private List<String> comparePriceChange(List<PriceModel> newPrices, Double percentChangeThreshold) {
        Map<String, Double> priceMap = priceService.getPriceMap();
        Predicate<PriceModel> priceChangePredicate = newPrice -> {
            Double oldPrice = priceMap.get(newPrice.getSymbol());
            return Math.abs(((newPrice.getPrice() - oldPrice) / oldPrice) * 100) >= percentChangeThreshold;
        };
        return newPrices.stream().filter(priceChangePredicate).map(PriceModel::getSymbol).toList();
    }
}
