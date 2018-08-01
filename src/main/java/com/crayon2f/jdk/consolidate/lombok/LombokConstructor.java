package com.crayon2f.jdk.consolidate.lombok;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by feiFan.gou on 2018/5/7 17:04.
 *
 *
 */
@RequiredArgsConstructor(staticName = "of")
@ToString
class LombokConstructor {


    // @Data = @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
    @NonNull private String field1;
    @NonNull private Integer field2;

}
