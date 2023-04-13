package com.example.consuming.consumingrest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
// @JsonIgnoreProperties이 간단한 Java 레코드 클래스 에는
// Jackson JSON 처리 라이브러리에서 주석이 추가되어
// 이 유형에 바인딩되지 않은 모든 속성을 무시해야 함을 나타냅니다.
public record Quote(String type, Value value) {

}
