package study.market.etc.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;

    public void sendMail(String email) {

        String subject = "[아무거나 골라서 시켜] 인증번호";
        String tmpKey = getTempKey();

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(tmpKey);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        //5분 제한
        redisService.setDataExpire(email, tmpKey, 60 * 3L);
    }

    private String getTempKey() {
        char[] tmp = new char[6];

        for(int i=0; i<tmp.length; i++) {
            int div = (int) Math.floor(Math.random() * 2);

            if(div == 0)
                tmp[i] = (char) (Math.random() * 10 + '0');
            else
                tmp[i] = (char) (Math.random() * 26 + 'A');
        }

        return new String(tmp);
    }

}
