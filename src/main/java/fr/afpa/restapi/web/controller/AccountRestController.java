package fr.afpa.restapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.afpa.restapi.dao.AccountDao;
import fr.afpa.restapi.model.Account;

/**
 * => implémenter un constructeur
 * 
 * => ajouter la/les annotations nécessaires pour faire de
 * "AccountRestController" un contrôleur de REST API
 * 
 * => injecter {@link AccountDao} en dépendance par injection via
 * constructeur Plus d'informations :
 * https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
 */
@RestController
@RequestMapping("/accounts") // Pour ne pas mettre le parametre Account sur tous les Get et Post
public class AccountRestController {
    private final AccountDao accountDao;

    public AccountRestController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * => implémenter une méthode qui traite les requêtes GET et qui renvoie une
     * liste de comptes
     */
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    } // Insomnia ok

    /*
     * {*
     * => implémenter une méthode qui traite les requêtes GET avec un identifiant
     * "variable de chemin" et qui retourne les informations du compte associé
     * Plus d'informations sur les variables de chemin ->
     * https://www.baeldung.com/spring-pathvariable
     */
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé"));
    } // Insomnia ok

    /**
     * => implémenter une méthode qui traite les requêtes POST
     * Cette méthode doit recevoir les informations d'un compte en tant que "request
     * body", elle doit sauvegarder le compte en mémoire et retourner ses
     * informations (en json)
     * Tutoriel intéressant -> https://stackabuse.com/get-http-post-body-in-spring/
     * Le serveur devrai retourner un code http de succès (201 Created)
     **/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) {
        return accountDao.save(account);
    } // Insomnia ok

    /**
     * => implémenter une méthode qui traite les requêtes PUT
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatetAccount(@PathVariable long id, @RequestBody Account updatedAccount) {
        Account existingAccount = accountDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé"));

        existingAccount.setFirstName(updatedAccount.getFirstName());
        existingAccount.setLastName(updatedAccount.getLastName());
        existingAccount.setEmail(updatedAccount.getEmail());

        accountDao.save(existingAccount);
    } // Insomnia ok

    /**
     * => implémenter une méthode qui traite les requêtes DELETE
     * L'identifiant du compte devra être passé en "variable de chemin" (ou "path
     * variable")
     * Dans le cas d'un suppression effectuée avec succès, le serveur doit retourner
     * un status http 204 (No content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletetAccount(@PathVariable long id) {
        Account existingAccount = accountDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé"));

        accountDao.delete(existingAccount);
    }// Insomnia ok
}