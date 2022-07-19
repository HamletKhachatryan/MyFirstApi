package Api;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class ReqresTest {
    private final static String URL="https://reqres.in/";
    @Test
    public void checkEmailAndIdTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
users.forEach(x->Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
List<String> emails = users.stream().map(UserData::getEmail).collect(Collectors.toList());
List<String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < emails.size(); i++) {
            Assert.assertTrue(emails.get(i).contains(ids.get(i)));

        }

    }
    @Test
    public void succesRegTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());

    }
    @Test
    public void unSuccesRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Register user = new Register("sydney@fife", "");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals("Missing password",unSuccessReg.getError());


    }
}

