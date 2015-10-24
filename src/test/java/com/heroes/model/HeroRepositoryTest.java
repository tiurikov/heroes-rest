package com.heroes.model;

import org.testng.annotations.Test;

import static com.heroes.model.HeroRepository.UNKNOWN_HERO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;

/**
 *
 * @author Stanislav Tyurikov
 */
public class HeroRepositoryTest
{
    private final Hero robin = new Hero("Dick Grayson");
    private final Hero batman = new Hero("Bruce Wayne");
    private final Hero superman = new Hero("Clark Kent");
    //
    private static final String ROBIN = "robin";
    private static final String BATMAN = "batman";
    private static final String SUPERMAN = "superman";
    //
    private static final String UNKNOWN_HERO_BATMAN
            = "Unknown Hero '" + BATMAN + "'";
    private static final String UNKNOWN_HERO_ROBIN
            = "Unknown Hero '" + ROBIN + "'";
    private static final String FORBIDDEN_ALLIANCE_BATMAN
            = "Unable to create alliance for hero '" + BATMAN + "' and hero '" + BATMAN + "'";

    private static HeroRepository aRepository()
    {
        return new InMemoryHeroRepository();
    }

    @Test
    public void saveHero()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.saveHero(ROBIN, robin);

        assertThat(repository.findHero(BATMAN), is(batman));
        assertThat(repository.findHero(ROBIN), is(robin));
    }

    @Test
    public void updateHero()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.saveHero(BATMAN, robin);

        assertThat(repository.findHero(BATMAN), is(robin));
    }

    @Test
    public void findUnkownHero()
    {
        HeroRepository repository = aRepository();

        assertThat(repository.findHero(BATMAN), is(UNKNOWN_HERO));
    }

    @Test
    public void findAllHeros()
    {
        HeroRepository repository = aRepository();

        assertThat(repository.findAllHeroes(), is(empty()));

        repository.saveHero(BATMAN, batman);
        repository.saveHero(SUPERMAN, superman);

        assertThat(repository.findAllHeroes(), containsInAnyOrder(BATMAN, SUPERMAN));
    }

    @Test
    public void removeHero()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.removeHero(BATMAN);

        assertThat(repository.findHero(BATMAN), is(UNKNOWN_HERO));
    }

    @Test
    public void createAlliance()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.saveHero(ROBIN, robin);
        repository.saveHero(SUPERMAN, superman);

        repository.unite(BATMAN, ROBIN);
        repository.unite(BATMAN, SUPERMAN);

        assertThat(repository.findAllies(ROBIN), containsInAnyOrder(BATMAN));
        assertThat(repository.findAllies(SUPERMAN), containsInAnyOrder(BATMAN));
        assertThat(repository.findAllies(BATMAN), containsInAnyOrder(ROBIN, SUPERMAN));
    }

    @Test
    public void destoryAlliance()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.saveHero(ROBIN, robin);
        repository.saveHero(SUPERMAN, superman);

        repository.unite(BATMAN, ROBIN);
        repository.unite(BATMAN, SUPERMAN);

        repository.disunite(BATMAN, ROBIN);

        assertThat(repository.findAllies(BATMAN), containsInAnyOrder(SUPERMAN));
        assertThat(repository.findAllies(ROBIN), is(empty()));
        assertThat(repository.findAllies(BATMAN), containsInAnyOrder(SUPERMAN));
    }

    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = FORBIDDEN_ALLIANCE_BATMAN)
    public void selfAllianceIsImpossible()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.unite(BATMAN, BATMAN);
    }

    @Test
    public void removingHeroLeadsToRemoveAlliance()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.saveHero(ROBIN, robin);
        repository.saveHero(SUPERMAN, superman);

        repository.unite(BATMAN, ROBIN);
        repository.unite(BATMAN, SUPERMAN);

        repository.removeHero(ROBIN);

        assertThat(repository.findAllies(BATMAN), containsInAnyOrder(SUPERMAN));
    }

    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_BATMAN)
    public void removeUnknownHero()
    {
        HeroRepository repository = aRepository();
        repository.removeHero(BATMAN);
    }

    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_ROBIN)
    public void uniteWithUnknownAlly()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.unite(BATMAN, ROBIN);
    }

    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_ROBIN)
    public void disuniteWithUnknownAlly()
    {
        HeroRepository repository = aRepository();
        repository.saveHero(BATMAN, batman);
        repository.disunite(BATMAN, ROBIN);
    }

    @Test(expectedExceptions = HeroRepositoryException.class,
            expectedExceptionsMessageRegExp = UNKNOWN_HERO_BATMAN)
    public void findAlliesForUnknownHero()
    {
        HeroRepository repository = aRepository();
        repository.findAllies(BATMAN);
    }

}
