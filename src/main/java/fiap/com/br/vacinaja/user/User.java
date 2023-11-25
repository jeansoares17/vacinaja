package fiap.com.br.vacinaja.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    Long id;
    String name;
    String email;
    @Builder.Default
    Integer aplicadas = 1;

    public static User convert(OAuth2User user) {
        return new UserBuilder()
                .id(Long.valueOf(user.getName()))
                .name(user.getAttribute("name"))
                .email(user.getAttribute("email"))
                .build();
    }

}
