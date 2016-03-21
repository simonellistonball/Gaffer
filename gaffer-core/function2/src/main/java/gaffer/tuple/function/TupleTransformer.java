/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gaffer.tuple.function;

import gaffer.tuple.Tuple;
import gaffer.function2.Transformer;
import gaffer.tuple.function.context.FunctionContext;
import gaffer.tuple.function.context.TupleFunctionValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>TupleTransformer</code> transforms input {@link gaffer.tuple.Tuple}s by applying
 * <code>Transformer</code>s to the tuple values. Outputs the input tuple, but with it's values updated.
 * @param <R> The type of reference used by tuples.
 */
public class TupleTransformer<R> extends Transformer<Tuple<R>, Tuple<R>> {
    private List<FunctionContext<Transformer, R>> transforms;

    /**
     * Default constructor - for serialisation.
     */
    public TupleTransformer() { }

    /**
     * Create a <code>TupleTransformer</code> that applies the given functions.
     * @param transforms {@link gaffer.function2.Transformer}s to transform tuple values.
     */
    public TupleTransformer(final List<FunctionContext<Transformer, R>> transforms) {
        setTransforms(transforms);
    }

    /**
     * @param transforms {@link gaffer.function2.Transformer}s to transform tuple values.
     */
    public void setTransforms(final List<FunctionContext<Transformer, R>> transforms) {
        this.transforms = transforms;
    }

    /**
     * @return {@link gaffer.function2.Transformer}s to transform tuple values.
     */
    public List<FunctionContext<Transformer, R>> getTransforms() {
        return transforms;
    }

    /**
     * @param transform {@link gaffer.function2.Transformer} to transform tuple values.
     */
    public void addTransform(final FunctionContext<Transformer, R> transform) {
        if (transforms == null) {
            transforms = new ArrayList<FunctionContext<Transformer, R>>();
        }
        transforms.add(transform);
    }

    /**
     * Transform an input tuple.
     * @param input Input tuple.
     * @return Input tuple with transformed content.
     */
    public Tuple<R> transform(final Tuple<R> input) {
        if (transforms != null) {
            for (FunctionContext<Transformer, R> transform : transforms) {
                transform.project(input, transform.getFunction().execute(transform.select(input)));
            }
        }
        return input;
    }

    @Override
    public boolean validateInput(final Object schemaTuple) {
        return TupleFunctionValidator.validateInput(transforms, schemaTuple);
    }

    @Override
    public boolean validateOutput(final Object schemaTuple) {
        return TupleFunctionValidator.validateOutput(transforms, schemaTuple);
    }

    /**
     * @return New <code>TupleTransformer</code> with new {@link gaffer.function2.Transformer}s.
     */
    public TupleTransformer<R> copy() {
        TupleTransformer<R> copy = new TupleTransformer<R>();
        for (FunctionContext<Transformer, R> transform : this.transforms) {
            copy.addTransform(transform.copy());
        }
        return copy;
    }
}