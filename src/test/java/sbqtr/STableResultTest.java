package sbqtr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sbqtr.context.TestContext;
import sbqtr.util.TestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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