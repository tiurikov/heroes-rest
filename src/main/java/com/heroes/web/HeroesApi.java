package com.heroes.web;

import com.heroes.model.Hero;
import com.heroes.model.HeroRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.heroes.model.HeroRepository.UNKNOWN_HERO;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 *
 * @author Stanislav Tyurikov
 */
@RestController
@RequestMapping("/api/v1")
public class HeroesApi
{
    @Autowired
    private HeroRepository heroRepository;


    @RequestMapping(value = "/heroes/{pseudonym}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity createHero(@PathVariable("pseudonym") String pseudonym, @RequestBody Hero hero)
    {
        heroRepository.saveHero(pseudonym, hero);
        return created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
    }


    @RequestMapping(value = "/heroes/{pseudonym}", method = DELETE)
    public ResponseEntity removeHero(@PathVariable("pseudonym") String pseudonym)
    {
        if (!UNKNOWN_HERO.equals(heroRepository.findHero(pseudonym))) {
            heroRepository.removeHero(pseudonym);
        }

        return noContent().build();
    }


    @RequestMapping(value = "/heroes", method = GET)
    public Set<String> findAllHeroes()
    {
        return heroRepository.findAllHeroes();
    }


    @RequestMapping(value = "/heroes/{pseudonym}", method = GET)
    public ResponseEntity findHero(@PathVariable("pseudonym") String pseudonym)
    {
        final Hero hero = heroRepository.findHero(pseudonym);
        
        if (UNKNOWN_HERO.equals(hero)) {
            return notFound().build();
        }

        return ResponseEntity.ok(hero);
    }


    @RequestMapping(value = "/heroes/{pseudonym}/allies", method = GET)
    public Set<String> findAllies(@PathVariable("pseudonym") String pseudonym)
    {
        return heroRepository.findAllies(pseudonym);
    }


    @RequestMapping(value = "/heroes/{pseudonymX}/allies/{pseudonymY}", method = GET)
    public ResponseEntity checkAlliance(
            @PathVariable("pseudonymX") String pseudonymX,
            @PathVariable("pseudonymY") String pseudonymY)
    {
        if (heroRepository.findAllies(pseudonymX).contains(pseudonymY)) {
            return noContent().build();
        } else {
            return notFound().build();
        }
    }


    @RequestMapping(value = "/heroes/{pseudonymX}/allies/{pseudonymY}", method = PUT)
    public ResponseEntity createAlliance(
            @PathVariable("pseudonymX") String pseudonymX,
            @PathVariable("pseudonymY") String pseudonymY)
    {
        heroRepository.unite(pseudonymX, pseudonymY);
        return created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
    }


    @RequestMapping(value = "/heroes/{pseudonymX}/allies/{pseudonymY}", method = DELETE)
    public ResponseEntity deleteAlliance(
            @PathVariable("pseudonymX") String pseudonymX,
            @PathVariable("pseudonymY") String pseudonymY)
    {
        heroRepository.disunite(pseudonymX, pseudonymY);
        return ok(EMPTY);
    }
}
