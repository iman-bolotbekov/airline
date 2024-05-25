package app.exceptions;

import microservice.starter.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

public class TicketNumberException extends BusinessException {

    public TicketNumberException(String ticketNumber) {
        super("Ticket number " + ticketNumber + " is already exists", HttpStatus.BAD_REQUEST);
    }
}