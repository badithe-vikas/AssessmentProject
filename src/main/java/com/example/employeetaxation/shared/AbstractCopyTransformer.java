package com.example.employeetaxation.shared;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractCopyTransformer<Origin, Destination> {
    protected final Class<Destination> destinationClass;
    protected final Class<Origin> sourceClass;

    @SuppressWarnings("unchecked")
    public AbstractCopyTransformer() {
        super();
        this.sourceClass = (Class<Origin>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.destinationClass = (Class<Destination>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SneakyThrows
    public Destination apply(final Origin orig) {
        final Destination dest = destinationClass.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(orig, dest);
        return dest;
    }
}
