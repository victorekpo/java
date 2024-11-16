package com.example.demo.config.opensearch.examplesdontuse;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.DataStreamChannel;
import org.apache.hc.core5.util.ByteArrayBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

public class _ExampleLoggingHttpEntity implements AsyncEntityProducer {

    private final AsyncEntityProducer wrappedProducer;
    private final ByteArrayBuffer buffer;

    public _ExampleLoggingHttpEntity(AsyncEntityProducer wrappedProducer) {
        this.wrappedProducer = wrappedProducer;
        this.buffer = new ByteArrayBuffer(1024);
    }

    @Override
    public void produce(DataStreamChannel channel) throws IOException {
        wrappedProducer.produce(new DataStreamChannel() {
            @Override
            public void requestOutput() {
                channel.requestOutput();
            }

            @Override
            public int write(ByteBuffer src) throws IOException {
                byte[] data = new byte[src.remaining()];
                src.get(data);
                buffer.append(data, 0, data.length);
                return channel.write(ByteBuffer.wrap(data));
            }

            @Override
            public void endStream() throws IOException {
                String content = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
                System.out.println("Entity content: " + content);
                channel.endStream();
            }

            @Override
            public void endStream(List<? extends Header> trailers) throws IOException {
                String content = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
                System.out.println("Entity content: " + content);
                channel.endStream(trailers);
            }
        });
    }

    @Override
    public long getContentLength() {
        return wrappedProducer.getContentLength();
    }

    @Override
    public String getContentType() {
        return wrappedProducer.getContentType();
    }

    @Override
    public String getContentEncoding() {
        return "";
    }

    @Override
    public boolean isChunked() {
        return false;
    }

    @Override
    public Set<String> getTrailerNames() {
        return Set.of();
    }

    @Override
    public boolean isRepeatable() {
        return wrappedProducer.isRepeatable();
    }

    @Override
    public void failed(Exception cause) {
        wrappedProducer.failed(cause);
    }

    @Override
    public void releaseResources() {
        wrappedProducer.releaseResources();
    }

    @Override
    public int available() {
        return wrappedProducer.available();
    }
}