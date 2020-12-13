package com.github.shnewto.bqjson.surrogate;

import com.github.shnewto.bqjson.context.TestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class STableResultTest {
    TestContext context;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        context = TestContext.getInstance();
    }

    @Test
    void toTableResult() throws IOException, InterruptedException {
        context.tableResults.forEach(t -> {
            STableResult sTableResult = new STableResult(t);
            assertThat(sTableResult.toTableResult()).isEqualTo(t);
        });
    }
}