package spring.lab.annotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lichen
 * @date 2023-5-6 11:41
 */
@Setter
@Getter
@NoArgsConstructor
public class Hello {
    private String name;

    public Hello(String name) {
        this.name = name;
    }

    public String say(String nameParam) {
        return "Hello, " + nameParam + "! I am " + name;
    }
}
