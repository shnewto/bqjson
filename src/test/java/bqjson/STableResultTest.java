package bqjson;

import bqjson.surrogate.STableResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bqjson.context.TestContext;

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
        STableResult sTableResult = new STableResult(context.tableResult);
        assertThat(sTableResult.toTableResult()).isEqualTo(context.tableResult);
    }
}