package fiap.com.br.vacinaja.vacina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import fiap.com.br.vacinaja.user.User;
import fiap.com.br.vacinaja.user.UserService;

@Service
public class VacinaService {

    @Autowired
    VacinaRepository repository;

    @Autowired
    UserService userService;

    public List<Vacina> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var vacina = repository.findById(id);
        if (vacina.isEmpty())
            return false;
        repository.deleteById(id);
        return true;
    }

    public void save(Vacina vacina) {
        repository.save(vacina);
    }

    public void decrement(Long id) {
        var optional = repository.findById(id);
        if (optional.isEmpty())
            throw new RuntimeException("vacina não encontrada");

        var vacina = optional.get();
        if (vacina.getStatus() == 0)
            throw new RuntimeException("status não pode ser negativo");

        vacina.setStatus(vacina.getStatus() - 10);
        repository.save(vacina);
    }

    public void increment(Long id) {
        var optional = repository.findById(id);
        if (optional.isEmpty())
            throw new RuntimeException("vacina não encontrada");

        var vacina = optional.get();
        if (vacina.getStatus() == 100)
            throw new RuntimeException("status não pode maior que 100%");
        vacina.setStatus(vacina.getStatus() + 10);

        if (vacina.getStatus() == 100) {
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(User.convert(user), vacina.getLote());
        }

        repository.save(vacina);
    }

    public void catchVacina(Long id, User user) {
        var optional = repository.findById(id);
        if (optional.isEmpty())
            throw new RuntimeException("vacina não encontrada");

        var vacina = optional.get();
        if (vacina.getUser() != null)
            throw new RuntimeException("vacina já aplicada");

        vacina.setUser(user);
        repository.save(vacina);
    }

    public void dropVacina(Long id, User user) {
        var optional = repository.findById(id);
        if (optional.isEmpty())
            throw new RuntimeException("vacina não encontrada");

        var vacina = optional.get();
        if (vacina.getUser() == null)
            throw new RuntimeException("vacina não aplicada");

        if (!vacina.getUser().equals(user))
            throw new RuntimeException("não pode aplicar esta vacina");

        vacina.setUser(null);
        repository.save(vacina);
    }
}