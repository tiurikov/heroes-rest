package com.heroes.model;

import java.util.Set;

/**
 *
 * @author Stanislav Tyurikov
 */
public interface HeroRepository
{
    Hero UNKNOWN_HERO = new Hero("UNKNOWN_HERO");
    
    void saveHero(String pseudonym, Hero hero);

    void removeHero(String pseudonym);

    Hero findHero(String pseudonym);

    Set<String> findAllHeroes();

    void unite(String pseudonymX, String pseudonymY);

    void disunite(String pseudonymX, String pseudonymY);

    Set<String> findAllies(String pseudonym);
}
