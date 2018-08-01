package com.crayon2f.jdk.consolidate.lombok;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by feiFan.gou on 2018/5/7 20:29.
 */
@NoArgsConstructor
class LombokGetLazy {

    @Getter(lazy = true)
    private final long id = System.currentTimeMillis();
    @Getter(lazy = true)
    private final String name = "lazy";
}
