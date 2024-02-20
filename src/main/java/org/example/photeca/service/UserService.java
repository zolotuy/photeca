package org.example.photeca.service;

import org.example.photeca.model.PriceModel;
import org.example.photeca.entity.PriceEntity;
import org.example.photeca.entity.User;
import org.example.photeca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CryptoPriceService cryptoPriceService;

    public void saveUser(Long chatId, org.telegram.telegrambots.meta.api.objects.User user) {
        User userEntity = new User();
        userEntity.setChatId(chatId);
        userEntity.setId(user.getId());
        userEntity.setName(user.getUserName());
        userRepository.save(userEntity);
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public void refreshPricesForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not exists"));
        user.deleteAllPrices();
        List<PriceEntity> newPrices = cryptoPriceService.getPrice().stream().map(PriceModel::convertToEntity).toList();
        user.setPrices(newPrices);
    }
}
