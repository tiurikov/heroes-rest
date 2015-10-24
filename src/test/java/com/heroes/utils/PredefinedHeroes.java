package com.heroes.utils;

import com.heroes.model.Hero;
import com.heroes.model.HeroBuilder;
import com.heroes.model.Publisher;

import static com.heroes.utils.DateUtils.date;

/**
 *
 * @author Stanislav Tyurikov
 */
public class PredefinedHeroes
{
    public static final String BATMAN_PSEUDONYM = "batman";
    public static final String ROBIN_PSEUDONYM = "robin";
    public static final String SUPERMAN_PSEUDONYM = "superman";

    public static Hero BATMAN = HeroBuilder.aHero().
            withName("Bruce Wayne").
            withFirstAppearance(date("1939-05-01")).
            withPublisher(Publisher.DC).
            withSkill("Genius­level intellect").
            withSkill("Utilizes high-tech equipment and weapons").
            build();

    public static Hero ROBIN = HeroBuilder.aHero().
            withName("Dick Grayson").
            withFirstAppearance(date("1940-04-01")).
            withPublisher(Publisher.DC).
            withSkill("Expert acrobat").
            withSkill("Highly skilled martial artist and hand­to­hand combatant").
            withSkill("Expert detective").
            withSkill("Utilizes high­tech equipment, weapons, vehicles and gadgets").
            build();

    public static Hero SUPERMAN = HeroBuilder.aHero().
            withName("Clark Kent").
            withFirstAppearance(date("1938-04-01")).
            withPublisher(Publisher.DC).
            withSkill("Superhuman strength, speed,senses, endurance, and longevity").
            withSkill("Flight").
            withSkill("Heat vision").
            withSkill("Freezing breath").
            build();
}
