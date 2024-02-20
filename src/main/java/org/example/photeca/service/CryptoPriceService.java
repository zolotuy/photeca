package org.example.photeca.service;

import org.example.photeca.model.PriceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CryptoPriceService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String CHECK_PRICE_URL = "https://api.mexc.com/api/v3/ticker/price";

    public List<PriceModel> getPrice() {
        try {
            PriceResponse response = restTemplate.getForObject(CHECK_PRICE_URL, PriceResponse.class);
            if (response != null && response.getData() != null && !response.getData().isEmpty()) {
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    // Define response object to map JSON response
    private static class PriceResponse {
        private List<PriceModel> data;

        public List<PriceModel> getData() {
            return data;
        }

        public void setData(List<PriceModel> data) {
            this.data = data;
        }
    }

//    private static class PriceData {
//        private Double last;
//
//        public Double getLast() {
//            return last;
//        }
//
//        public void setLast(Double last) {
//            this.last = last;
//        }
//    }
}
