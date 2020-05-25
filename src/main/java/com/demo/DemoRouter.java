package com.demo;

import com.demo.model.ApiResponse;
import com.demo.model.User;
import com.demo.service.UserService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("restbuilder")
public class DemoRouter extends RouteBuilder {

    private final static Logger log = LoggerFactory.getLogger(DemoRouter.class);

    public final static String HEADER_BUSINESSID = "businessId"; //Optional custom correlation id


    @Autowired
    UserService userService;
    ApiResponse apiResponse;


    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true");

        onException(Exception.class)
                .handled(true)
                .to("log:"+DemoRouter.class.getName()+"?showAll=true&multiline=true&level=ERROR")
                .removeHeaders("*",HEADER_BUSINESSID) //don't let message headers get inserted in the http response
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .bean("restbuilder","errorResponse(500,'Internal server error')");

        rest("/users").description("User REST service")
                .consumes("application/json")
                .produces("application/json")
                .get().description("Find all users").outTypeList(User.class)
                    .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
                    .to("bean:userService?method=findUsers")
                .post("").type(User.class).description("Create user")
                .to("json-validator:userSchema.json")
                    .param().name(HEADER_BUSINESSID).type(RestParamType.header).description("Business transaction id. Defaults to a random uuid").required(false).dataType("string").endParam()
                    .route().routeId("post-user")
                    .log("User received: ${body}").id("received-user")
                    .to("bean:userService?method=createUser")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                    .endRest();
    }


    //Build error response pojo
    public ApiResponse errorResponse(int code, String message){
        return new ApiResponse(code, message);

    }

}