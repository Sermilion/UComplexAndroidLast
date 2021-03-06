package org.ucomplex.ucomplex.Common.utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 16/05/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class InterfaceTypeAdapterFactory implements TypeAdapterFactory {

    // Effectively a singleton totally holding no state
    private static final TypeAdapterFactory interfaceTypeAdapterFactory = new InterfaceTypeAdapterFactory();

    public InterfaceTypeAdapterFactory() {
    }

    // However, let's encapsulate the instantiation
    public static TypeAdapterFactory getInterfaceTypeAdapterFactory() {
        return interfaceTypeAdapterFactory;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        // Checking if it's an interface
        return !typeToken.getRawType().isInterface()
                // If it's not, then just let Gson pick up a proper type adapter
                ? null
                // Otherwise return a null-safe custom type adapter
                : new InterfaceTypeAdapter<T>(gson).nullSafe();
    }

    private static final class InterfaceTypeAdapter<T>
            extends TypeAdapter<T> {

        private static final String TYPE_PROPERTY = "type";
        private static final String DATA_PROPERTY = "data";

        private final Gson gson;

        private InterfaceTypeAdapter(final Gson gson) {
            this.gson = gson;
        }

        @Override
        @SuppressWarnings("resource")
        public void write(final JsonWriter out, final T value)
                throws IOException {
            // Here we're just writing a property value similar to one you did
            out.beginObject();
            out.name(TYPE_PROPERTY);
            out.value(value.getClass().getName());
            out.name(DATA_PROPERTY);
            gson.toJson(value, value.getClass(), out);
            out.endObject();
        }

        @Override
        public T read(final JsonReader in)
                throws IOException {
            try {
                in.beginObject();
                final String name = in.nextName();
                final Object value;
                switch ( name ) {
                    case TYPE_PROPERTY:
                        final String type = in.nextString();
                        if ( !in.nextName().equals(DATA_PROPERTY) ) {
                            throw new MalformedJsonException("Expected " + DATA_PROPERTY + " at " + in);
                        }
                        value = gson.fromJson(in, Class.forName(type));
                        break;
                    case DATA_PROPERTY:
                        final JsonElement jsonElement = gson.fromJson(in, JsonElement.class);
                        if ( !in.nextName().equals(TYPE_PROPERTY) ) {
                            throw new MalformedJsonException("Expected " + TYPE_PROPERTY + " at " + in);
                        }
                        value = gson.fromJson(jsonElement, Class.forName(in.nextString()));
                        break;
                    default:
                        throw new MalformedJsonException("Unrecognized " + name + " at " + in);
                }
                if ( in.hasNext() ) {
                    throw new IllegalStateException("Unexpected " + in.nextName() + " at " + in);
                }
                in.endObject();
                @SuppressWarnings("unchecked")
                final T castValue = (T) value;
                return castValue;
            } catch ( final ClassNotFoundException ex ) {
                throw new IOException(ex);
            }
        }

    }

}