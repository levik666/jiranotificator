package com.epam.jiranotificator.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilsTest {

    private static final String REG_IDS = "1234, 4567, 7897";
    private static final String REG_IDS_EMPTY = "";

    @Test
    public void shouldSuccessSplitToList() throws Exception {
        final List<String> regIds = StringUtils.splitToList(REG_IDS);

        assertNotNull(regIds);
        assertEquals(3, regIds.size());
    }

    @Test
    public void shouldSuccessReturnEmptyListDueToStringIsEmpty() throws Exception {
        final List<String> regIds = StringUtils.splitToList(REG_IDS_EMPTY);

        assertNotNull(regIds);
        assertEquals(1, regIds.size());
    }

    @Test
    public void shouldSuccessReturnEmptyListDueToStringIsNull() throws Exception {
        final List<String> regIds = StringUtils.splitToList(null);

        assertNotNull(regIds);
        assertEquals(0, regIds.size());
    }
}
