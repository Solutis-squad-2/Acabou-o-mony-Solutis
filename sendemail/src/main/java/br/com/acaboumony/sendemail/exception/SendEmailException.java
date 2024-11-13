package br.com.acaboumony.sendemail.exception;

public class SendEmailException extends RuntimeException{
    public SendEmailException(String message){
        super(message);
    }
}
