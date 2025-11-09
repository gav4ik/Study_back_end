import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "exotic"
})
public record Food(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("exotic") Boolean exotic) {
}
