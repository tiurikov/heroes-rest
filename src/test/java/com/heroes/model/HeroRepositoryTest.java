package com.heroes.model;

import org.testng.annotations.Test;

import static com.heroes.model.HeroRepository.UNKNOWN_HERO;
import static com.heroes.utils.PredefinedHeroes.BATMAN;
import static com.heroes.utils.PredefinedHeroes.BATMAN_PSEUDONYM;
import static com.heroes.utils.PredefinedHeroes.ROBIN;
import static com.heroes.utils.PredefinedHeroes.ROBIN_PSEUDONYM;
import static com.heroes.utils.PredefinedHeroes.SUPERMAN;
import static com.heroes.utils.PredefinedHeroes.SUPERMAN_PSEUDONYM;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

/**
 *
 * @author Stanislav Tyurikov
 */
public class HeroRepositoryTest
{
    private static final String UNKNOWN_HERO_BATMAN = "Unknown Hero '" + BATMAN_PSEUDONYM + "'";
    private static final String UNKNOWN_HERO_ROBIN = "Unknown Hero '" + ROBIN_PSEUDONYM + "'";
    private static final String FORBIDDEN_ALLIANCE_BATMAN
            = "Unable to create alliance for hero '" + BATMAN_PSEUDONYM + "' and hero '" + BATMAN_PSEUDONYM + "'";


    private static HeroRepository aRepository()
    {
        return new InMemoryHeroRepository();
    }


    @Test
    public void saveHero()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(ROBIN_PSEUDONYM, ROBIN);

        assertThat(repository.findHero(BATMAN_PSEUDONYM), is(BATMAN));
        assertThat(repository.findHero(ROBIN_PSEUDONYM), is(ROBIN));
    }


    @Test
    public void updateHero()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(BATMAN_PSEUDONYM, ROBIN);

        assertThat(repository.findHero(BATMAN_PSEUDONYM), is(ROBIN));
    }


    @Test
    public void findUnkownHero()
    {
        HeroRepository repository = aRepository();

        assertThat(repository.findHero(BATMAN_PSEUDONYM), is(UNKNOWN_HERO));
    }


    @Test
    public void findAllHeros()
    {
        HeroRepository repository = aRepository();

        assertThat(repository.findAllHeroes(), is(empty()));

        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(SUPERMAN_PSEUDONYM, SUPERMAN);

        assertThat(repository.findAllHeroes(), containsInAnyOrder(BATMAN_PSEUDONYM, SUPERMAN_PSEUDONYM));
    }


    @Test
    public void removeHero()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.removeHero(BATMAN_PSEUDONYM);

        assertThat(repository.findHero(BATMAN_PSEUDONYM), is(UNKNOWN_HERO));
    }


    @Test
    public void createAlliance()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(ROBIN_PSEUDONYM, ROBIN);
        repository.saveHero(SUPERMAN_PSEUDONYM, SUPERMAN);

        repository.unite(BATMAN_PSEUDONYM, ROBIN_PSEUDONYM);
        repository.unite(BATMAN_PSEUDONYM, SUPERMAN_PSEUDONYM);

        assertThat(repository.findAllies(ROBIN_PSEUDONYM), containsInAnyOrder(BATMAN_PSEUDONYM));
        assertThat(repository.findAllies(SUPERMAN_PSEUDONYM), containsInAnyOrder(BATMAN_PSEUDONYM));
        assertThat(repository.findAllies(BATMAN_PSEUDONYM), containsInAnyOrder(ROBIN_PSEUDONYM, SUPERMAN_PSEUDONYM));
    }


    @Test
    public void destoryAlliance()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(ROBIN_PSEUDONYM, ROBIN);
        repository.saveHero(SUPERMAN_PSEUDONYM, SUPERMAN);

        repository.unite(BATMAN_PSEUDONYM, ROBIN_PSEUDONYM);
        repository.unite(BATMAN_PSEUDONYM, SUPERMAN_PSEUDONYM);

        repository.disunite(BATMAN_PSEUDONYM, ROBIN_PSEUDONYM);

        assertThat(repository.findAllies(BATMAN_PSEUDONYM), containsInAnyOrder(SUPERMAN_PSEUDONYM));
        assertThat(repository.findAllies(ROBIN_PSEUDONYM), is(empty()));
        assertThat(repository.findAllies(BATMAN_PSEUDONYM), containsInAnyOrder(SUPERMAN_PSEUDONYM));
    }


    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = FORBIDDEN_ALLIANCE_BATMAN)
    public void selfAllianceIsImpossible()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.unite(BATMAN_PSEUDONYM, BATMAN_PSEUDONYM);
    }


    @Test
    public void removingHeroLeadsToRemoveAlliance()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(ROBIN_PSEUDONYM, ROBIN);
        repository.saveHero(SUPERMAN_PSEUDONYM, SUPERMAN);

        repository.unite(BATMAN_PSEUDONYM, ROBIN_PSEUDONYM);
        repository.unite(BATMAN_PSEUDONYM, SUPERMAN_PSEUDONYM);

        repository.removeHero(ROBIN_PSEUDONYM);

        assertThat(repository.findAllies(BATMAN_PSEUDONYM), containsInAnyOrder(SUPERMAN_PSEUDONYM));
    }


    @Test
    public void deleteHeroesAllFormRepository()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.saveHero(ROBIN_PSEUDONYM, ROBIN);
        repository.saveHero(SUPERMAN_PSEUDONYM, SUPERMAN);

        assertThat(repository.findAllHeroes(), hasSize(3));

        repository.deleteAll();
        assertThat(repository.findAllHeroes(), hasSize(0));
    }


    @Test
    public void checkHeroExistsInRepository()
    {
        HeroRepository repository = aRepository();
        assertThat(repository.existsHero(BATMAN_PSEUDONYM), is(false));

        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        assertThat(repository.existsHero(BATMAN_PSEUDONYM), is(true));
    }


    @Test
    public void removeUnknownHero()
    {
        HeroRepository repository = aRepository();
        repository.removeHero(BATMAN_PSEUDONYM);
        assertThat(repository.existsHero(BATMAN_PSEUDONYM), is(false));
    }


    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_ROBIN)
    public void uniteWithUnknownAlly()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.unite(BATMAN_PSEUDONYM, ROBIN_PSEUDONYM);
    }


    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_ROBIN)
    public void disuniteWithUnknownAlly()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN_PSEUDONYM, BATMAN);
        repository.disunite(BATMAN_PSEUDONYM, ROBIN_PSEUDONYM);
    }


    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_BATMAN)
    public void findAlliesForUnknownHero()
    {
        HeroRepository repository = aRepository();
        repository.findAllies(BATMAN_PSEUDONYM);
    }

}
