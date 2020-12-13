package com.github.shnewto.bqjson.surrogate;

import com.github.shnewto.bqjson.context.TestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SSchemaTest {
    TestContext context;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        context = TestContext.getInstance();
    }

    @Test
    void toSchema() {
        context.schemas.forEach(s -> {
            SSchema sSchema = new SSchema(s);
            assertThat(sSchema.toSchema()).isEqualTo(s);
        });
    }

    @Test
    void getFields() {
        context.schemas.forEach(s -> {
            SSchema sSchema = new SSchema(s);
            assertThat(sSchema.getFields()).isEqualTo(s.getFields());
        });
    }
}