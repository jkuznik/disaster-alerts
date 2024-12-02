package pl.ateam.disasteralerts.notification;

class SMSNotSentException extends Exception {
    SMSNotSentException(String message) {
        super(message);
    }
}
