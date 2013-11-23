package ua.org.tumakha.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.*;

/**
 * @author Yuriy Tumakha
 */
public class ScheduledMailSender {

    private static final Log log = LogFactory.getLog(ScheduledMailSender.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    private ScheduledExecutorService executorService;

    private static final BlockingQueue<MimeMessage> MESSAGE_QUEUE = new LinkedBlockingQueue<MimeMessage>();

    public MimeMessageHelper newMessageHelper() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        return new MimeMessageHelper(mimeMessage, true);
    }

    public void send(MimeMessage mimeMessage){
        MESSAGE_QUEUE.add(mimeMessage);

        if (executorService == null || executorService.isShutdown()) {
            start();
        }
    }

    private synchronized void start() {
        if (executorService == null || executorService.isShutdown()) {
            log.debug("Start Mail Service");
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleWithFixedDelay(new SendMessageTask(), 0, 50, TimeUnit.MILLISECONDS);
        }
    }

    private synchronized void shutdown() {
        if (MESSAGE_QUEUE.isEmpty()) {
            log.debug("Stop Mail Service");
            executorService.shutdown();
        }
    }

    private class SendMessageTask implements Runnable {

        @Override
        public void run() {
            try {
                MimeMessage mimeMessage = MESSAGE_QUEUE.take();
                try {
                    mailSender.send(mimeMessage);
                } catch (Exception e) {
                    log.error("Send message failed.", e);
                    TimeUnit.SECONDS.sleep(1);
                    mailSender.send(mimeMessage);
                }

                if (MESSAGE_QUEUE.isEmpty()) {
                    TimeUnit.SECONDS.sleep(10);
                    if (MESSAGE_QUEUE.isEmpty()) {
                        shutdown();
                    }
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

    }

}
