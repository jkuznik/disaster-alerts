package pl.ateam.disasteralerts.notification;

class SMSLimitReachException extends Exception{
    SMSLimitReachException(String message) {super(message);}
}
