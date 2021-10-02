package com.facouto.usersservice.query;

import com.facouto.core.model.PaymentDetails;
import com.facouto.core.model.User;
import com.facouto.core.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserEventsQueryHandler {
    @QueryHandler
    public User find(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("FABIO COUTO")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        User userRest = User.builder()
                .firstName("Fábio")
                .lastName("Couto")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();

        return userRest;
    }
}
