@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.2' )
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.JsonSlurper 
import groovy.json.JsonOutput

def map = new JsonSlurper().parse(new File(args[1]))

def builder = new HTTPBuilder("http://${args[0]}/api/v1/heroes/")

map.heroes.each { pseudonym, profile ->
    builder.request( PUT, JSON ) { req ->
        uri.path = "$pseudonym"
        body = JsonOutput.toJson(profile)
        response.success = { resp, json ->
            println "created $pseudonym : ($profile.name, $profile.firstAppearance, $profile.publisher)"
        }
    }
}

map.alliances.each { pseudonymX, alliance ->
    alliance.each { pseudonymY ->
        builder.request( PUT, TEXT ) { req ->
            uri.path = "$pseudonymX/allies/$pseudonymY"
            response.success = { resp, json ->
                println "created alliance for $pseudonymX : $pseudonymY"
            }
        }
    }
}

println "complete"