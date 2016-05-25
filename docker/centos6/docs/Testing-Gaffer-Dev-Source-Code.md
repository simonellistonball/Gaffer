## Testing Gaffer Dev Source Code

### Pre-requisites

1. Familiarity with the Gaffer framework

2. Pre-built Gaffer Dev Image and Container

### Change Source Code
1. Make required changes to the source code located in the /Gaffer_Source directory in the **_'eclipse_gafferdev_server'_** container

## Building jar files within Gaffer Dev container
1. Run the script 'build_gaffer_jar_files.sh' using the commands below within the container via a terminal
```$ cd /data/Gaffer/Script  && ./build_gaffer_jar_files.sh```

## Run Gaffer Graph Example via a terminal
(NB Assumes no changes have been made to Gaffer graph example code downloaded from
 official Gaffer github - gaffer.example.SimpleQuery)

```$ source /data/Gaffer/Script/set_java_classpath```

```$ java -cp $CLASSPATH gaffer.example.SimpleQuery```

Should display output similar to below:

> [hduser@e41d2c8478de Test]$ java -cp $CLASSPATH gaffer.example.SimpleQuery
Apr 05, 2016 1:57:24 PM org.apache.hadoop.conf.Configuration warnOnceIfDeprecated
INFO: fs.default.name is deprecated. Instead, use fs.defaultFS
Apr 05, 2016 1:57:24 PM org.apache.hadoop.util.NativeCodeLoader <clinit>
WARNING: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Enabling Bloom filter on table
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Bloom filter enabled
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Removing versioning iterator
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Versioning iterator removed
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Combiner iterator to table for all scopes
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Combiner iterator to table for all scopes
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Adding validator iterator to table for all scopes
Apr 05, 2016 1:57:25 PM gaffer.accumulostore.utils.TableUtils createTable
INFO: Added validator iterator to table for all scopes
Apr 05, 2016 1:57:25 PM gaffer.example.SimpleQuery main
INFO: Results from simple query:
Viewing{userId='user01', filmId='filmA', startTime=1401000000000}
Viewing{userId='user02', filmId='filmA', startTime=1401000000000}
Viewing{userId='user03', filmId='filmA', startTime=1408000000000}



## Removing jar files generated by maven build
```$ /data/Gaffer/Script/clean_up_gaffer_jar_files.sh```





