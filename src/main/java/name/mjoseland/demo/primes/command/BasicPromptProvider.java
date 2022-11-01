package name.mjoseland.demo.primes.command;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class BasicPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(
                "> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN)
        );
    }
}
