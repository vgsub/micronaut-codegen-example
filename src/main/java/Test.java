import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.micronaut.http.HttpResponse;
import ru.tinkoff.tma.caen.api.controller.DefaultApi;
import ru.tinkoff.tma.caen.api.model.Child;

public class Test {

    private static final ObjectMapper MAPPER = JsonMapper.builder()
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());

    public static void main(String[] args) throws JsonProcessingException {
        var api = new TestApi();

        var json = MAPPER.writeValueAsString(api.test().body());
        var body = MAPPER.readValue(json, Child.class);

        System.out.println(body);
    }

    private static class TestApi implements DefaultApi {

        @Override
        public HttpResponse<Child> test() {
            return HttpResponse.ok(new Child().name("test").code(200));
        }
    }
}
