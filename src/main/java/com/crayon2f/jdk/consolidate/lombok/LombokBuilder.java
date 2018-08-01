package com.crayon2f.jdk.consolidate.lombok;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by feiFan.gou on 2018/5/7 19:42.
 */
@Builder(builderClassName = "BuilderInner")
@ToString
class LombokBuilder {

    private String string;
    @Builder.Default private long id;
    private LocalDate date;
    @Singular // 奇异化
    private List<String> occupations;
    private Map<String, String> map;

}
