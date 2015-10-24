package com.heroes.model;

import java.util.Set;

/**
 *
 * @author Stanislav Tyurikov
 */
public interface HeroRepository
{
    Hero UNKNOWN_HERO = HeroBuilder.aHero().withName("UNKNOWN_HERO").build();


    void saveHero(String pseudonym, Hero hero);


    void removeHero(String pseudonym);


    Hero findHero(String pseudonym);


    boolean existsHero(String pseudonym);


    Set<String> findAllHeroes();


    void unite(String pseudonymX, String pseudonymY);


    void disunite(String pseudonymX, String pseudonymY);


    Set<String> findAllies(String pseudonym);


    void deleteAll();
}
