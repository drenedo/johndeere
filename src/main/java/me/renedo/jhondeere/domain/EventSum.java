package me.renedo.jhondeere.domain;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EventSum {
    private final UUID sessionId;
    private final UUID machineId;
    private final String type;
    private final Double total;

    public EventSum(UUID sessionId, UUID machineId, String type, Double total) {
        this.sessionId = sessionId;
        this.machineId = machineId;
        this.type = type;
        this.total = total;
    }

    public UUID getMachineId() {
        return machineId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public Double getTotal() {
        return total;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EventSum eventSum = (EventSum) o;

        return new EqualsBuilder().append(sessionId, eventSum.sessionId).append(machineId, eventSum.machineId)
                .append(type, eventSum.type).append(total, eventSum.total).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sessionId).append(machineId).append(type).append(total).toHashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventSum{");
        sb.append("sessionId=").append(sessionId);
        sb.append(", machineId=").append(machineId);
        sb.append(", type='").append(type).append('\'');
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
