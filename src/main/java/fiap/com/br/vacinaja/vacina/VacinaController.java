package fiap.com.br.vacinaja.vacina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fiap.com.br.vacinaja.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/vacina")
public class VacinaController {

    @Autowired
    VacinaService service;

    @Autowired
    MessageSource messages;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("email", user.getAttribute("email"));
        model.addAttribute("vacinas", service.findAll());
        return "vacina/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (service.delete(id)) {
            redirect.addFlashAttribute("success", getMessage("vacina.delete.success"));
        } else {
            redirect.addFlashAttribute("error", getMessage("vacina.notfound"));
        }
        return "redirect:/vacina";
    }

    @GetMapping("new")
    public String form(Vacina vacina) {
        return "vacina/form";
    }

    @PostMapping
    public String create(@Valid Vacina vacina, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors())
            return "vacina/form";

        service.save(vacina);
        redirect.addFlashAttribute("success", "Vacina aplicada com sucesso");
        return "redirect:/vacina";
    }

    private String getMessage(String code) {
        return messages.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("dec/{id}")
    public String decrement(@PathVariable Long id) {
        service.decrement(id);
        return "redirect:/vacina";
    }

    @GetMapping("inc/{id}")
    public String increment(@PathVariable Long id) {
        service.increment(id);
        return "redirect:/vacina";
    }

    @GetMapping("catch/{id}")
    public String catchVacina(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user) {
        service.catchVacina(id, User.convert(user));
        return "redirect:/vacina";
    }

    @GetMapping("drop/{id}")
    public String dropVacina(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user) {
        service.dropVacina(id, User.convert(user));
        return "redirect:/vacina";
    }
}