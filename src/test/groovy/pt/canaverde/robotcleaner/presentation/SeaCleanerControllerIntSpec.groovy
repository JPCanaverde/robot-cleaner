package pt.canaverde.robotcleaner.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import pt.canaverde.robotcleaner.presentation.model.sea.SeaApiError
import pt.canaverde.robotcleaner.presentation.model.sea.SeaCleaningResult
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SeaCleanerControllerIntSpec extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper mapper

    def "cleaning works"() {
        given:
        def input = """
        {
            "areaSize" : [5, 5],
            "startingPosition" : [1, 2],
            "oilPatches" : [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            "navigationInstructions" : "NNESEESWNWW"
        }
        """

        when:
        def mvcResult = mvc.perform(post('/sea/clean').contentType(APPLICATION_JSON).content(input))
        def seaCleaningResult = mapper.readValue(mvcResult.andReturn().getResponse().getContentAsString(), SeaCleaningResult.class)

        then:
        mvcResult.andExpect(status().isOk())

        seaCleaningResult.finalPosition[0] == 1
        seaCleaningResult.finalPosition[1] == 3
        seaCleaningResult.oilPatchesCleaned == 1
    }

    def "instructing cleaner to go out of bounds returns appropriate error"() {
        given:
        def input = """
        {
            "areaSize" : [5, 5],
            "startingPosition" : [0, 0],
            "oilPatches" : [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            "navigationInstructions" : "S"
        }
        """

        when:
        def mvcResult = mvc.perform(post('/sea/clean').contentType(APPLICATION_JSON).content(input))
        def error = mapper.readValue(mvcResult.andReturn().getResponse().getContentAsString(), SeaApiError.class)

        then:
        mvcResult.andExpect(status().isBadRequest())

        error.partialResult.get().oilPatchesCleaned == 0

        error.partialResult.get().finalPosition[0] == 0
        error.partialResult.get().finalPosition[1] == 0

        error.getDetails().get().indexOfInvalidCardinalDirection.get() == 0

        error.getDetails().get().invalidPosition[0] == 0
        error.getDetails().get().invalidPosition[1] == -1
    }

    def "an invalid navigation instruction returns an appropriate error"() {
        given:
        def input = """
        {
            "areaSize" : [5, 5],
            "startingPosition" : [0, 0],
            "oilPatches" : [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            "navigationInstructions" : "A"
        }
        """

        when:
        def mvcResult = mvc.perform(post('/sea/clean').contentType(APPLICATION_JSON).content(input))
        def error = mapper.readValue(mvcResult.andReturn().getResponse().getContentAsString(), SeaApiError.class)

        then:
        mvcResult.andExpect(status().isBadRequest())

        error.partialResult.isEmpty()
        error.details.isEmpty()
        error.message.contains("directions")
    }
}
