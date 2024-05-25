package app.clients;

import app.dto.PaymentRequest;
import app.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "${app.feign.config.name}", url = "${app.feign.config.url}")
public interface PaymentFeignClient {
    @PostMapping("/api/payments")
    ResponseEntity<PaymentResponse> makePayment(PaymentRequest paymentResponse);
}