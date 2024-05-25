

package app.entities;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


public class PaymentTest extends EntityTest {

    private Validator validator;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }


    private JSONObject initJSONObject() {
        var PaymentJSON = new JSONObject();
        List<Long> bookingId = new ArrayList<>();
        bookingId.add(1L);
        bookingId.add(2L);
        PaymentJSON.put("id", 10000L);
        PaymentJSON.put("bookingsId", bookingId);
        PaymentJSON.put("paymentState", "CREATED");
        return PaymentJSON;
    }


    @Test
    public void validPaymentShouldValidate() {
        Payment testPayment;
        var PaymentJSON = initJSONObject();

        try {
            testPayment = mapper.readValue(PaymentJSON.toString(), Payment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testPayment));
    }


    @Test
    public void nullBookingIdListShouldNotValidate() {
        Payment testPayment;
        var PaymentJSON = initJSONObject();
        PaymentJSON.replace("bookingsId", null);
        try {
            testPayment = mapper.readValue(PaymentJSON.toString(), Payment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testPayment));
    }

    @Test
    public void emptyBookingIdListShouldNotValidate() {
        Payment testPayment;
        var PaymentJSON = initJSONObject();
        PaymentJSON.replace("bookingsId", new ArrayList<Long>());
        try {
            testPayment = mapper.readValue(PaymentJSON.toString(), Payment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testPayment));
    }
}