package com.siotman.wos.yourpaper.domain;

import com.siotman.wos.yourpaper.domain.converter.*;
import com.siotman.wos.yourpaper.domain.json.CitingPaperJson;
import com.siotman.wos.yourpaper.domain.json.JournalImpactJson;
import com.siotman.wos.yourpaper.domain.json.ParsedAuthorJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@JsonTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class JsonConverterTests {
    private final String MSG_POJO2JSON_ERR = "POJO => JSON 변환에 실패했습니다.";
    private final String MSG_JSON2POJO_ERR = "JSON => POJO 변환에 실패했습니다.";


    private JsonListConverter jsonListConverter() {
        return new JsonListConverter();
    }

    private JsonMapConverter jsonMapConverter() {
        return new JsonMapConverter();
    }

    private JsonParsedAuthorListConverter jsonParsedAuthorListConverter() {
        return new JsonParsedAuthorListConverter();
    }

    private JsonCitingPaperListConverter jsonCitingPaperListConverter() {
        return new JsonCitingPaperListConverter();
    }

    private JsonJournalImpactConverter jsonJournalImpactConverter() {
        return new JsonJournalImpactConverter();
    }

    @Test
    public void authorArrayJsonShouldBeConverted() {
        JsonListConverter converter = jsonListConverter();

        String authorArrayJson = "[\"Kim SeungShin\",\"Lee SeungNyung\",\"Lim SooHyun\",\"Lee KangEun\"]";

        List<String> authorList = converter.convertToEntityAttribute(authorArrayJson);
        Assert.isTrue(authorList.size() == 4, MSG_POJO2JSON_ERR);

        String convertedAuthorJson = converter.convertToDatabaseColumn(authorList);
        Assert.isTrue(authorArrayJson.equals(convertedAuthorJson), MSG_JSON2POJO_ERR);
    }

    @Test
    public void tcDataMapJsonShouldBeConverted() {
        JsonMapConverter converter = jsonMapConverter();

        String tcDataMapJson = "{" +
                "\"2018\":{\"0\":0,\"1\":8,\"12\":1}," +
                "\"2017\":{\"0\":1,\"5\":2,\"7\":55,\"9\":1}," +
                "\"2014\":{\"0\":0,\"7\":8}," +
                "\"2020\":{\"0\":0,\"3\":1}" +
        "}";

        Map<String, String> tcDataMap = converter.convertToEntityAttribute(tcDataMapJson);
        Assert.isTrue(tcDataMap.keySet().size() == 4, MSG_POJO2JSON_ERR);

        String convertedTcDataJson = converter.convertToDatabaseColumn(tcDataMap);
        Assert.isTrue(tcDataMapJson.equals(convertedTcDataJson), MSG_JSON2POJO_ERR);
    }

    @Test
    public void journalImpactJsonShouldBeConverted() {
        JsonJournalImpactConverter converter = jsonJournalImpactConverter();

        String journalImpactJson = "{" +
            "\"sourceTitle\":\"CHAOS SOLITONS & FRACTALS\"," +
            "\"impactFactorByYear\":{" +
                "\"5year\":\"2.597\"," +
                "\"2018\":\"3.064\"" +
            "}," +
            "\"jcrDataByYear\":{" +
                "\"2018\":{" +
                    "\"category\":[" +
                        "\"MATHEMATICS, INTERDISCIPLINARY APPLICATIONS\"," +
                        "\"PHYSICS, MATHEMATICAL\"," +
                        "\"PHYSICS, MULTIDISCIPLINARY\"" +
                    "]," +
                    "\"rankInCategory\":[" +
                        "\"12 of 105\"," +
                        "\"3 of 55\"," +
                        "\"19 of 81\"" +
                    "]," +
                    "\"quartileInCategory\":[\"Q1\",\"Q1\",\"Q1\"]" +
                "}" +
            "}" +
        "}";

        JournalImpactJson journalImpactPojo = converter.convertToEntityAttribute(journalImpactJson);
        Assert.isTrue(journalImpactPojo.getSourceTitle().equals("CHAOS SOLITONS & FRACTALS"), MSG_JSON2POJO_ERR);
        Assert.isTrue(journalImpactPojo.getJcrDataByYear().get("2018").getCategory().size() == 3, MSG_JSON2POJO_ERR);
        Assert.isTrue(journalImpactPojo.getImpactFactorByYear().size() == 2, MSG_JSON2POJO_ERR);

        String convertedJournalImpactJson = converter.convertToDatabaseColumn(journalImpactPojo);
        Assert.isTrue(journalImpactJson.equals(convertedJournalImpactJson), MSG_POJO2JSON_ERR);
    }

    @Test
    public void parsedAuthorArrayJsonShouldBeConverted() {
        JsonParsedAuthorListConverter converter = jsonParsedAuthorListConverter();

        String parsedAuthorArrayJson = "[" +
                "{" +
                    "\"name\":\"Lee, CY\"," +
                    "\"fullName\":\"Lee, CY\"," +
                    "\"address\":[" +
                        "\"Sejong Univ, Dept Phys, Seoul 143747, South Korea\"" +
                    "]" +
                "}," +
                "{" +
                    "\"name\":\"Ne'eman, Y\"," +
                    "\"fullName\":\"Ne'eman, Y\"," +
                    "\"address\":[" +
                        "\"Tel Aviv Univ, Sackler Fac Exact Sci, IL-69978 Tel Aviv, Israel\"," +
                        "\"Univ Texas, Ctr Particle Phys, Austin, TX 78712 USA\"" +
                    "]" +
                "}" +
        "]";

        List<ParsedAuthorJson> parsedAuthorList = converter.convertToEntityAttribute(parsedAuthorArrayJson);
        Assert.isTrue(parsedAuthorList.size() == 2, MSG_JSON2POJO_ERR);

        String convertedParsedAuthorListJson = converter.convertToDatabaseColumn(parsedAuthorList);
        Assert.isTrue(parsedAuthorArrayJson.equals(convertedParsedAuthorListJson), MSG_JSON2POJO_ERR);
    }

    @Test
    public void citingPaperArrayJsonShouldBeConverted() {
        JsonCitingPaperListConverter converter = jsonCitingPaperListConverter();

        String parsedAuthorArrayJson = "[" +
        "]";

        List<CitingPaperJson> parsedAuthorList = converter.convertToEntityAttribute(parsedAuthorArrayJson);
        Assert.isTrue(parsedAuthorList.size() == 0, MSG_JSON2POJO_ERR);

        String convertedParsedAuthorListJson = converter.convertToDatabaseColumn(parsedAuthorList);
        Assert.isTrue(parsedAuthorArrayJson.equals(convertedParsedAuthorListJson), MSG_JSON2POJO_ERR);
    }
}
