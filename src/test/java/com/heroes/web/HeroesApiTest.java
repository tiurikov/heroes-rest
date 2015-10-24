package com.heroes.web;

import com.heroes.model.Hero;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import static com.heroes.utils.PredefinedHeroes.BATMAN;
import static com.heroes.utils.PredefinedHeroes.ROBIN;
import static com.heroes.utils.PredefinedHeroes.SUPERMAN;
import static com.heroes.utils.RestUtils.delete;
import static com.heroes.utils.RestUtils.getBody;
import static com.heroes.utils.RestUtils.getStatus;
import static com.heroes.utils.RestUtils.put;
import static com.heroes.utils.RestUtils.withBase;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 *
 * @author Stanislav Tyurikov
 */
public class HeroesApiTest extends AbstractSystemTestBase
{
    @Test
    public void createHero()
    {
        ResponseEntity response = put("/heroes/batman", BATMAN);
        assertThat(response.getStatusCode(), is(CREATED));
        assertThat(response.getHeaders().getLocation(), is(withBase("/heroes/batman")));
        assertThat(getBody("/heroes/batman", Hero.class), is(BATMAN));
    }


    @Test
    public void getListOfAllHeroes()
    {
        put("/heroes/batman", BATMAN);
        put("/heroes/robin", ROBIN);
        put("/heroes/superman", SUPERMAN);

        Set<String> heroes = getBody("/heroes", Set.class);
        assertThat(heroes, containsInAnyOrder("batman", "superman", "robin"));
    }


    @Test
    public void createAlliance()
    {
        put("/heroes/batman", BATMAN);
        put("/heroes/robin", ROBIN);
        put("/heroes/superman", SUPERMAN);
        put("/heroes/batman/allies/robin");
        put("/heroes/batman/allies/superman");

        Set<String> batmansAllies = getBody("/heroes/batman/allies", Set.class);
        assertThat(batmansAllies, containsInAnyOrder("robin", "superman"));

        Set<String> robinsAllies = getBody("/heroes/robin/allies", Set.class);
        assertThat(robinsAllies, containsInAnyOrder("batman"));

        Set<String> supermansAllies = getBody("/heroes/superman/allies", Set.class);
        assertThat(supermansAllies, containsInAnyOrder("batman"));
    }


    @Test
    public void deleteAlliance()
    {
        put("/heroes/batman", BATMAN);
        put("/heroes/robin", ROBIN);
        put("/heroes/superman", SUPERMAN);
        put("/heroes/batman/allies/robin");
        put("/heroes/batman/allies/superman");
        delete("/heroes/batman/allies/superman");

        Set<String> batmansAllies = getBody("/heroes/batman/allies", Set.class);
        assertThat(batmansAllies, containsInAnyOrder("robin"));
    }


    @Test
    public void findUnknownHero()
    {
        assertThat(getStatus("/heroes/unknown"), is(NOT_FOUND));
    }


    @Test
    public void removeHero()
    {
        put("/heroes/batman", BATMAN);
        delete("/heroes/batman");
        assertThat(getStatus("/heroes/batman"), is(NOT_FOUND));

        delete("/heroes/batman");
        assertThat(getStatus("/heroes/batman"), is(NOT_FOUND));
    }


    @Test
    public void checkAllianсeExists()
    {
        put("/heroes/batman", BATMAN);
        put("/heroes/robin", BATMAN);
        
        assertThat(getStatus("/heroes/batman/allies/robin"), is(NOT_FOUND));
        assertThat(getStatus("/heroes/robin/allies/batman"), is(NOT_FOUND));

        put("/heroes/batman/allies/robin");
        
        assertThat(getStatus("/heroes/batman/allies/robin"), is(NO_CONTENT));
        assertThat(getStatus("/heroes/robin/allies/batman"), is(NO_CONTENT));
    }
}
