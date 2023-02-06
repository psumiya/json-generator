package json.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom comparator to ignore values during comparison. Comparator checks for keys and their depth
 * in the generated object.
 */
public class JsonKeyComparator implements Comparator<JsonNode> {

    @Override
    public int compare(JsonNode o1, JsonNode o2) {
        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o1.getClass() != o2.getClass()) {
            return -1;
        }
        if (o1.isValueNode() && o2.isValueNode()) return 0;
        if (o1.getNodeType() == JsonNodeType.ARRAY) {
            if (o2.getNodeType() == JsonNodeType.ARRAY) {
                JsonNode valueJsonNode = o2.get(0);
                JsonNode otherJsonNode = o1.get(0);
                return compare(valueJsonNode, otherJsonNode);
            } else {
                return -1;
            }
        }
        if (o1.getNodeType() == JsonNodeType.OBJECT && o2.getNodeType() != JsonNodeType.OBJECT) return -1;
        ObjectNode other = (ObjectNode) o1;
        if (other.size() != o2.size()) {
            return -1;
        }
        AtomicInteger result = new AtomicInteger();
        o2.fields().forEachRemaining(en -> {
            String key = en.getKey();
            JsonNode value = en.getValue();
            JsonNode otherValue = other.get(key);
            if (result.get() == 0) {
                if (otherValue == null) {
                    result.set(-1);
                } else if (otherValue.isContainerNode() && value.isContainerNode()) {
                    switch (otherValue.getNodeType()) {
                        case ARRAY -> {
                            if (value.getNodeType() == JsonNodeType.ARRAY) {
                                JsonNode valueJsonNode = value.get(0);
                                JsonNode otherJsonNode = otherValue.get(0);
                                result.set(compare(valueJsonNode, otherJsonNode));
                            } else {
                                result.set(-1);
                            }
                        }
                        case OBJECT -> result.set(compare(value, otherValue));
                    }
                } else if (!otherValue.isValueNode()) {
                    result.set(-1);
                }
            }
        });
        return result.get();
    }

}
