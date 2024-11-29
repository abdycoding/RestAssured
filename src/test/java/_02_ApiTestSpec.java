import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class _02_ApiTestSpec {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void  Setup(){

        baseURI="https://gorest.co.in/public/v1";

        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();

    }

    @Test
    public  void Test1(){

        given()
                .spec(requestSpecification)

                .when()
                .get("/users") // base URI ekleniyor

                .then()
                .spec(responseSpecification);
    }

    @Test
    public  void Test2(){

        given() . spec(requestSpecification)
                .when()
                .get("/users")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void  extractingJsonPath4(){

        ArrayList<Integer> ids=
                given().
                        when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract()
                        .path("data.id");

        System.out.println("ids = " + ids);

    }


    @Test
    public void  extractingJsonPath5(){

        ArrayList<String> names =
                given().
                        when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract()
                        .path("data.name");

        System.out.println("names = " + names);

    }

    @Test
    public void  extractingJsonPathResponseAll(){

        Response donenData=
                given().
                        when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract()
                        .response();

        List<Integer> ids=donenData.path("data.id");
        List<String> names=donenData.path("data.name");
        int limit=donenData.path("meta.pagination.limit");

        System.out.println("ids = " + ids);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(ids.contains(7522203));
        Assert.assertTrue(names.contains("Agastya Varma"));
        Assert.assertTrue(limit==10);

    }



}
