package sbqtr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sbqtr.context.TestContext;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SFieldListTest {
    TestContext context;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        context = TestContext.getInstance();
    }

    @Test
    void toFieldList() {
        SFieldList sFieldList = new SFieldList(context.fieldList);
        assertThat(sFieldList.toFieldList()).isEqualTo(context.fieldList);
    }
}