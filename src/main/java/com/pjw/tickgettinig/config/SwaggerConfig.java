package com.pjw.tickgettinig.config;

import com.pjw.tickgettinig.common.ApiResponseExamples;
import com.pjw.tickgettinig.common.ErrorResponse;
import com.pjw.tickgettinig.common.ResponseCode;
import com.pjw.tickgettinig.common.ResponseExampleHolder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        // header에 bearerAuth토큰을 넣도록 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("authKey");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI().components(
                new Components().addSecuritySchemes("bearerAuth", securityScheme)
            ).security(Collections.singletonList(securityRequirement));
    }

    // API응답값 예시 설정
    @Bean
    public OperationCustomizer customizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiResponseExamples apiResponseExamples = handlerMethod.getMethodAnnotation(ApiResponseExamples.class);

            if(apiResponseExamples != null) {
                generateErrorCodeExamples(operation, apiResponseExamples.value());
            }

            return operation;
        };
    }

    // http code값에 따라 에러 응답값 예시 설정
    public void generateErrorCodeExamples(Operation operation, ResponseCode[] codes) {
        ApiResponses responses = operation.getResponses();

        // ErrorCode 별로 ExampleHolder생성
        Map<Integer, List<ResponseExampleHolder>> statusExampleHolder = Arrays.stream(codes)
                .map(
                    code -> ResponseExampleHolder.builder()
                            .example(getSwaggerResponseExample(code))
                            .code(code.getHttpStatus().value())
                            .name(code.toString())
                            .build()
                )
                .collect(Collectors.groupingBy(ResponseExampleHolder::getCode));

        addExamplesToResponses(responses, statusExampleHolder);
    }

    public Example getSwaggerResponseExample(ResponseCode code){
        ErrorResponse errorResponse = ErrorResponse.from(code);

        Example example = new Example();
        example.setValue(errorResponse);

        return example;
    }

    private void addExamplesToResponses(ApiResponses responses, Map<Integer, List<ResponseExampleHolder>> statusWithExampleHolders){
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();
                    v.forEach(exampleHolder ->
                            mediaType.addExamples(exampleHolder.getName(), exampleHolder.getExample())
                    );

                    content.addMediaType("application/json",mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(String.valueOf(status), apiResponse);
                }
        );
    }
}
