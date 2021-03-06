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
package gaffer.accumulostore.operation.spark.handler.scalardd;

import gaffer.accumulostore.AccumuloStore;
import gaffer.accumulostore.inputformat.ElementInputFormat;
import gaffer.accumulostore.operation.spark.handler.dataframe.ClassTagConstants;
import gaffer.data.element.Element;
import gaffer.operation.OperationException;
import gaffer.operation.simple.spark.scalardd.GetRDDOfAllElements;
import gaffer.store.Context;
import gaffer.store.Store;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.spark.SparkContext;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

public class GetRDDOfAllElementsOperationHandler
        extends AbstractGetRDDOperationHandler<RDD<Element>, GetRDDOfAllElements> {

    @Override
    public RDD<Element> doOperation(final GetRDDOfAllElements operation,
                                    final Context context,
                                    final Store store)
            throws OperationException {
        return doOperation(operation, (AccumuloStore) store);
    }

    private RDD<Element> doOperation(final GetRDDOfAllElements operation,
                                     final AccumuloStore accumuloStore)
            throws OperationException {
        final SparkContext sparkContext = operation.getSparkContext();
        final Configuration conf = getConfiguration(operation);
        addIterators(accumuloStore, conf, operation);
        final RDD<Tuple2<Element, NullWritable>> pairRDD = sparkContext.newAPIHadoopRDD(conf,
                ElementInputFormat.class,
                Element.class,
                NullWritable.class);
        return pairRDD.map(new FirstElement(), ClassTagConstants.ELEMENT_CLASS_TAG);
    }
}
