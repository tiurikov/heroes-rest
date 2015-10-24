package com.heroes.model;

import org.testng.annotations.Test;

import static com.heroes.utils.DateUtils.date;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 *
 * @author Stanislav Tyurikov
 */
public class HeroBuilderTest
{
    private static final String SOME_HERO = "Some Hero";
    private static final String SOME_SKILL_Y = "some skill Y";
    private static final String SOME_SKILL_X = "some skill X";

    @Test
    public void createHeroInstance()
    {
        Hero build = HeroBuilder.aHero().
                withName(SOME_HERO).
                withFirstAppearance(date("1985-07-18")).
                withPublisher(Publisher.DC).
                withSkill(SOME_SKILL_X).
                withSkill(SOME_SKILL_Y).
                build();

        assertThat(build.getName(), is(SOME_HERO));
        assertThat(build.getPublisher(), is(Publisher.DC));
        assertThat(build.getFirstAppearance(), is(date("1985-07-18")));
        assertThat(build.getSkills(), containsInAnyOrder(SOME_SKILL_X, SOME_SKILL_Y));
    }
}
