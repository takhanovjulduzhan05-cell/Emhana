package kz.kaznaru.capstone.infra;

import kz.kaznaru.capstone.service.ReminderPort;
/** Adapter translates application messages into legacy SMS gateway calls. */
public final class SmsReminderAdapter implements ReminderPort {
    private final LegacySmsGateway gateway;
    public SmsReminderAdapter(LegacySmsGateway gateway){this.gateway=gateway;}
    public void remind(String patient, String message){gateway.dispatchText(message,patient);}
}
