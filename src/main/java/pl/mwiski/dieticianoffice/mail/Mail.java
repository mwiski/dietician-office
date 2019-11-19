package pl.mwiski.dieticianoffice.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {

    private String mailTo;
    private String subject;
    private String message;
}
