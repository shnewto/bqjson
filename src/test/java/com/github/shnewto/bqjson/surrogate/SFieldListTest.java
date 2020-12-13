package com.github.shnewto.bqjson.surrogate;

import com.github.shnewto.bqjson.context.TestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SFieldListTest {
    TestContext context;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        context = TestContext.getInstance();
    }

    @Test
    void toFieldList() {
        context.fieldLists.forEach(f -> {
            SFieldList sFieldList = new SFieldList(f);
            assertThat(sFieldList.toFieldList()).isEqualTo(f);
        });
    }
}