package com.heroes.web;

import com.heroes.model.HeroBuilder;
import com.heroes.model.Publisher;
import java.util.Date;
import org.testng.annotations.Test;

import static com.heroes.utils.DateUtils.date;
import static com.heroes.utils.RestUtils.put;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 *
 * @author Stanislav Tyurikov
 */
public class HeroesApiValidationTest extends AbstractSystemTestBase
{
    private static final Date DATE = date("2000-01-01");
    private static final String HERO_NAME = "Mr. Hero";
    private static final String SIMPLE_SKILL = "simple skill";
    private static final Publisher MARVEL = Publisher.MARVEL;


    @Test
    public void createHeroWithEmptyName()
    {
        assertThat(put("/heroes/test", HeroBuilder.aHero().
                withFirstAppearance(DATE).
                withPublisher(MARVEL).
                withSkill(SIMPLE_SKILL).
                build()).getStatusCode(), is(BAD_REQUEST));
    }


    @Test
    public void createHeroWithNameSizeMoreThan30()
    {
        assertThat(put("/heroes/test", HeroBuilder.aHero().
                withName("1234567890_1234567890_1234567890").
                withFirstAppearance(DATE).
                withPublisher(MARVEL).
                withSkill(SIMPLE_SKILL).
                build()).getStatusCode(), is(BAD_REQUEST));
    }


    @Test
    public void createHeroWithNullFirstAppearance()
    {
        assertThat(put("/heroes/test", HeroBuilder.aHero().
                withName(HERO_NAME).
                withPublisher(MARVEL).
                withSkill(SIMPLE_SKILL).
                build()).getStatusCode(), is(BAD_REQUEST));
    }


    @Test
    public void createHeroWithUnknownPublisher()
    {
        assertThat(put("/heroes/test", HeroBuilder.aHero().
                withName(HERO_NAME).
                withFirstAppearance(DATE).
                withSkill(SIMPLE_SKILL).
                build()).getStatusCode(), is(BAD_REQUEST));
    }


    @Test
    public void createHeroWithEmptySkillSet()
    {
        assertThat(put("/heroes/test", HeroBuilder.aHero().
                withName(HERO_NAME).
                withFirstAppearance(DATE).
                withPublisher(MARVEL).
                build()).getStatusCode(), is(BAD_REQUEST));
    }


    @Test
    public void createHeroWithMoreThan10Skills()
    {
        HeroBuilder heroBuilder = HeroBuilder.aHero().
                withName(HERO_NAME).
                withFirstAppearance(DATE).
                withPublisher(MARVEL);
        
        for (int i = 0; i < 11; i++) {
            heroBuilder.withSkill(SIMPLE_SKILL + i);
        }

        assertThat(put("/heroes/test", heroBuilder.build()).getStatusCode(), is(BAD_REQUEST));
    }
}
