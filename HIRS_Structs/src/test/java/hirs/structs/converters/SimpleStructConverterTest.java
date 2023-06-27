package hirs.structs.converters;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests suite for {@link SimpleStructConverter}.
 */
public class SimpleStructConverterTest {

    private static final byte[] EXPECTED_BYTES =
            new byte[]{0, 5, 0, 0, 0, 10, 0, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 6, 0, 0, 0, 0};

    private final TestStruct testStruct = new TestStruct();

    private StructConverter converter = new SimpleStructConverter();

    /**
     * Tests {@link SimpleStructConverter#convert(hirs.structs.elements.Struct)}.
     */
    @Test
    public final void testConvertToByteArray() {
        // convert the test struct into a serialized version.
        byte[] serializedStruct = converter.convert(testStruct);

        // assert that the returned contents are expected
        assertThat(serializedStruct, equalTo(EXPECTED_BYTES));
    }

    /**
     * Tests {@link SimpleStructConverter#convert(byte[], Class)}.
     */
    @Test
    public final void testConvertToStruct() {
        // resulting struct
        TestStruct struct = converter.convert(EXPECTED_BYTES, TestStruct.class);

        // assert the resulting struct is the same as the original test struct
        assert (struct.equals(testStruct));
    }


}
