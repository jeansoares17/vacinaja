package fiap.com.br.vacinaja.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public void addScore(User user, Integer aplicadas) {
        var optional = repository.findById(user.getId());
        if (optional.isEmpty())
            throw new RuntimeException("usuário não encontrado");

        var userDb = optional.get();
        userDb.setAplicadas(userDb.getAplicadas() + aplicadas);
        repository.save(userDb);
    }

    public static void addAplicadas(User convert, Integer aplicadas) {
    }

}
