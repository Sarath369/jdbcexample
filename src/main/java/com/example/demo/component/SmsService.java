package com.example.demo.component;

import com.example.demo.entity.SmsPojo;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SmsService {


    private static final String ACCOUNT_SID = "AC40fd7d89568b22c985aba70d5f10e94d";

    private static final String AUTH_TOKEN = "f465634fa2c70bfd76620f392ca25ab8";

    private static final String FROM_NUMBER = "+13346974933";

    public void send(final SmsPojo sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage()).create();
        System.out.println("here is my id:" + message.getSid());
        // Unique resource ID created to manage this transaction

    }

    public void receive(final MultiValueMap<String, String> smscallback) {
    }
}
