package dev.mccue.microhttp.html;

import dev.mccue.html.Html;
import dev.mccue.html.HtmlEncodable;
import dev.mccue.microhttp.handler.IntoResponse;
import dev.mccue.reasonphrase.ReasonPhrase;
import org.microhttp.Header;
import org.microhttp.Response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of {@link IntoResponse} which encodes a blob of {@link Html}
 * as its body.
 *
 * @param status The status code the response.
 * @param headers The headers to include with the response. {@code Content-Type} is automatically added.
 * @param body A {@link HtmlEncodable} to use as the body.
 */
public record HtmlResponse(
        int status,
        List<Header> headers,
        HtmlEncodable body
) implements IntoResponse {
    public HtmlResponse {
        Objects.requireNonNull(headers);
        Objects.requireNonNull(body);
    }

    /**
     * Construct a new {@link HtmlResponse} with a status code of 200 and no extra headers.
     * @param body The body to use for the {@link HtmlResponse}.
     */
    public HtmlResponse(HtmlEncodable body) {
        this(200, List.of(), body);
    }

    /**
     * Construct a new {@link HtmlResponse} with no extra headers.
     * @param status The status code.
     * @param body The body to use.
     */
    public HtmlResponse(int status, HtmlEncodable body) {
        this(status, List.of(), body);
    }

    @Override
    public Response intoResponse() {
        var headers = new ArrayList<>(headers());
        headers.add(
                new Header("Content-Type", "text/html; charset=utf-8")
        );
        return new Response(
                status,
                ReasonPhrase.forStatus(status),
                Collections.unmodifiableList(headers),
                body.toHtml().toString().getBytes(StandardCharsets.UTF_8)
        );
    }
}
