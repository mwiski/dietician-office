package pl.mwiski.dieticianoffice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.service.DieticianService;
import pl.mwiski.dieticianoffice.service.VisitService;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class MailScheduler {

    private static final String SUBJECT = "Dietician-office application: Reminder about tomorrow's visits";

    @Autowired
    private EmailService emailService;
    @Autowired
    private DieticianService dieticianService;
    @Autowired
    private VisitService visitService;

    @Scheduled(cron = "*/10 * * * * *")
    public void sendMailAboutVisits() {
        dieticianService.getAll()
                .forEach(d -> emailService.send(new Mail(
                        d.getMail(),
                        SUBJECT,
                        "Dear " + d.getName() + ",\n\n" + "Tomorrow you have scheduled " + visitService.getDieticianVisitsForTomorrow(d.getId()).size() + " visits\n\n" +
                        printVisits(visitService.getDieticianVisitsForTomorrow(d.getId())) + "Best regards,\nDieticianOffice Team")));
    }

    private String printVisits(List<VisitDto> visits) {
        AtomicInteger counter = new AtomicInteger(1);
        return visits.stream()
                .map(v -> counter.getAndIncrement() + ". Date: " + v.getDateTime() + "\n" +
                        "Client: " + v.getUser().getName() + " " + v.getUser().getLastName() + "\n" +
                        "Phone number: " + v.getUser().getPhoneNumber() + ", email: " + v.getUser().getMail() + "\n\n")
                .collect(Collectors.joining());
    }
}
