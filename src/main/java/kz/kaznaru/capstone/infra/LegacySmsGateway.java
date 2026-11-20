package kz.kaznaru.capstone.infra;

import java.util.ArrayList;
import java.util.List;
/** Third-party-style API with an incompatible signature, simulated offline. */
public final class LegacySmsGateway {
    private final List<String> messages = new ArrayList<>();
    public void dispatchText(String text, String recipient) { messages.add(recipient+": "+text); }
    public List<String> sent(){return List.copyOf(messages);}
}
