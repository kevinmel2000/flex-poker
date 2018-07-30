package com.flexpoker.game.command.aggregate.tablebalancer;

import static com.flexpoker.game.command.aggregate.tablebalancer.TableBalancerTestUtils.createDefaultChipMapForSubjectTable;
import static com.flexpoker.game.command.aggregate.tablebalancer.TableBalancerTestUtils.createTableToPlayersMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;

import com.flexpoker.game.command.aggregate.TableBalancer;
import com.flexpoker.game.command.events.TableRemovedEvent;

public class TableBalancerRemoveTableTest {

    @Test
    public void testSubjectTableIsEmpty() {
        var subjectTableId = UUID.randomUUID();
        var tableToPlayersMap = createTableToPlayersMap(subjectTableId, 0, 2);

        var tableBalancer = new TableBalancer(UUID.randomUUID(), 2);
        var event = tableBalancer.createSingleBalancingEvent(subjectTableId, Collections.emptySet(),
                tableToPlayersMap, createDefaultChipMapForSubjectTable(subjectTableId, tableToPlayersMap));
        assertEquals(TableRemovedEvent.class, event.get().getClass());
        assertEquals(subjectTableId, ((TableRemovedEvent) event.get()).getTableId());
    }

    @Test
    public void testSingleOtherEmptyTable() {
        var subjectTableId = UUID.randomUUID();
        var tableToPlayersMap = createTableToPlayersMap(subjectTableId, 2, 0);
        var otherTableId = tableToPlayersMap.keySet().stream()
                .filter(x -> !x.equals(subjectTableId)).findFirst().get();

        var tableBalancer = new TableBalancer(UUID.randomUUID(), 2);
        var event = tableBalancer.createSingleBalancingEvent(subjectTableId, Collections.emptySet(),
                tableToPlayersMap, createDefaultChipMapForSubjectTable(subjectTableId, tableToPlayersMap));
        assertEquals(TableRemovedEvent.class, event.get().getClass());
        assertEquals(otherTableId, ((TableRemovedEvent) event.get()).getTableId());
    }

    @Test
    public void testMultipleOtherEmptyTables() {
        var subjectTableId = UUID.randomUUID();
        var tableToPlayersMap = createTableToPlayersMap(subjectTableId, 2, 0, 0);
        var otherTableIds = tableToPlayersMap.keySet().stream()
                .filter(x -> !x.equals(subjectTableId))
                .collect(Collectors.toSet());

        var tableBalancer = new TableBalancer(UUID.randomUUID(), 2);
        var event = tableBalancer.createSingleBalancingEvent(subjectTableId, Collections.emptySet(),
                tableToPlayersMap, createDefaultChipMapForSubjectTable(subjectTableId, tableToPlayersMap));
        assertEquals(TableRemovedEvent.class, event.get().getClass());
        assertTrue(otherTableIds.contains(((TableRemovedEvent) event.get()).getTableId()));
    }

    @Test
    public void testNoEmptyOtherwiseBalancedTables() {
        var subjectTableId = UUID.randomUUID();
        var tableToPlayersMap = createTableToPlayersMap(subjectTableId, 2, 2);

        var tableBalancer = new TableBalancer(UUID.randomUUID(), 2);
        var event = tableBalancer.createSingleBalancingEvent(subjectTableId, Collections.emptySet(),
                tableToPlayersMap, createDefaultChipMapForSubjectTable(subjectTableId, tableToPlayersMap));
        assertFalse(event.isPresent());
    }

}
