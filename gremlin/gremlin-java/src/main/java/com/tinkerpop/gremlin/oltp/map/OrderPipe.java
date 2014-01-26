package com.tinkerpop.gremlin.oltp.map;

import com.tinkerpop.blueprints.util.StreamFactory;
import com.tinkerpop.gremlin.Holder;
import com.tinkerpop.gremlin.Pipeline;
import com.tinkerpop.gremlin.oltp.AbstractPipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class OrderPipe<S> extends AbstractPipe<S, S> {

    public Iterator<Holder<S>> itty = null;
    public Comparator<Holder<S>> comparator;

    public OrderPipe(final Pipeline pipeline, final Comparator<Holder<S>> comparator) {
        super(pipeline);
        this.comparator = comparator;
    }

    protected Holder<S> processNextStart() {
        if (null != this.itty) {
            return itty.next();
        } else {
            final List<Holder<S>> list = (List) StreamFactory.stream(this.getPreviousPipe()).collect(Collectors.toList());
            Collections.sort(list, this.comparator);
            this.itty = list.iterator();
            return processNextStart();
        }
    }
}
