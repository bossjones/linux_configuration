Consider Groovy (& framework Grails) > Java

// Find a .class Java version
javap -v $file.class | grep version

/**EXPORT:
//1-Compile
rm -rf bin/*
libs="src/" ; for f in lib/*.jar; do libs="$libs:$f"; done
javac -Xlint:all -cp $libs -d bin/ $(find src/ -name *.java)
//2-Pack
mkdir lib_extracted
for file in $(find ../lib/ -name *.jar | grep -v 'mockito\|junit'); do jar xf $file; done
cd ..
jar cmf MANIFEST.MF archive.jar -C bin/ com/ -C lib_extracted/ . data/
rm -rf lib_extracted
//3-Run!
java -jar archive.jar # Maybe -cp .
// To be sure you're using the correct Java : namei $(which java)
*/

drip // JVM launcher with faster startup times - Alt: Nailgun, Cake

// Scripting
jrunscript // Javascript engine based on Mozilla's Rhino - Java 6-7 - Uses javax.script module
jjs // Nashborn Javascript engine - Java 8
Java Native Architecture // To call C code, used by Selenium Web Driver

java -cp junit.jar:. org.junit.runner.JUnitCore path.to.pkg.AllTests// JUnit
com.jayway.restassured && its spring-mock-mvc : great testing of REST controllers, by making real HTTP resqests on a local host
JContractS (formerly iContract), cofoja // Design By Contract libs
checkstyle, findbugs, google/error-prone // code checking tools
cobertura // code coverage
javap, JD // .class dissassembler & Java decompiler, include a GUI
Konloch/bytecode-viewer // Bytecode viewer, decompiler & debugger
jmap -histo:live <pid> // Object-type histogram on a running jvm

jdb // debugger: https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jdb.html
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n MyClass
jdb -attach javadebug
# or
java -agentlib:jdwp=transport=dt_shmem,server=y,suspend=n MyClass
jdb -attach 8000
# then
stop in java.lang.String.length
where
next
step

JLine // console input handling like BSD editline / GNU readline
SLF4J // logging API, then pick a logging engine: java.util.logging for basic stuff, Log4j2 else
Swrve/rate-limited-logger // an SLF4J-compatible, simple, fluent API for rate-limited logging in Java
Metrics // perf & health monitoring

Jersey // RESTful Web Services

Kryo + Snappy // very efficient object serialization + compression used in VSCT

OpenJDK JMH // Benchmark tool

Buildr, Fradle > ant, maven // build systems
mvn dependency:tree
mvn dependency:resolve-plugins # + cf. recurse_resolve_mvn_plugins_dependencies.sh
mvn buildplan:list # shows how goals are bound to phases - buildplan-maven-plugin from fr.jcgay.maven.plugins
gradle dependencies
mvn dependency-check:check # check for known CVE security issues in deps from owasp.org
anthemengineering/infer-maven-plugin # Facebook static analyzer for Java, does not work under Windows

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-antrun-plugin</artifactId>
    <version>1.1</version>
    <executions>
        <execution>
            <phase>validate</phase>
            <goals>
                <goal>run</goal>
            </goals>
            <configuration>
                <tasks>
                    <echo>PATH=${env.PATH}</echo>
                </tasks>
            </configuration>
        </execution>
    </executions>
</plugin>

sudo update-alternatives --config java

jps, jstat // Std perf monitoring tools: http://www.orace.com/webfolder/technetwork/tutorials/obe/java/JavaJCMD/index.html
jcmd [$process] // without args to list running Java processes
mariusaeriksen/heapster // Google perftools for the JVM

JSP (JavaServer Pages) is an alternative to PHP
    -> JSTL (JSP Standard Tag Library) : XML, SQL queries, Internationalisation...
JavaFX / OpenJFX // bytecode compiled non-dynamic language providing GUI and app packaging
Packr // Package .jar & assets for any distrib (most suited for GUI apps)
Capsule // dead-Simple packaging and deployment for JVM apps

dex2jar // Convert an Android .apk into .jar

JavaCPP Presets // provide bindings for the following C++ libs: OpenCV, FFmpeg, FlyCapture, libdc1394, libfreenect, videoInput, ARToolKitPlus, Chilitags, flandmark, FFTW, GSL, LLVM, Leptonica, Tesseract, Caffe, CUDA

VisualVM, jmxterm // GUI & CLI for JMX (Java Management Extensions that specifies simple Java objects called MBeans)
// Alt profilers: byteman, JVM Monitor (free Eclipse plugin), jprofiler (non free), Java Flight Recorder (baked into the HotSpot VM) & Java Mission Control, YourKit Java Profiler
Visual VM // -> Monitor, profile, take thread dumps, browse heap dumps
Using jemalloc to get to the bottom of a memory leak : https://gdstechnology.blog.gov.uk/2015/12/11/using-jemalloc-to-get-to-the-bottom-of-a-memory-leak/
Jolokia, hawtio // Provide JMX HTTP REST access
// 1. Make JVM accept JMX connections
-Dcom.sun.management.jmxremote.port=9876 // can be any port
-Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false // turning this off for simplicity
// 2. connect to $host & set up a SOCKS proxy listening on port 43210 (can be anyone)
ssh -D 43210 -N $host
// 3. run Jconsole remotely
jconsole -J-DsocksProxyHost=localhost -J-DsocksProxyPort=43210
// 4. Use the following JMX url
service:jmx:rmi:///jndi/rmi://$host:9876/jmxrmi

Byteman // insert extra Java code into your application, either as it is loaded during JVM startup or even after it has already started running: https://developer.jboss.org/wiki/ABytemanTutorial

JDBC // Java Database Connectivity : API that defines how a client may access a relational database
H2 // small fast in-memory SQL DB, useful for testing queries
Hibernate // framework SQL
MyBatis // data mapper framework, ORM for SQL DBs using a XML descriptor or annotations

-Djava.security.egd=file:/dev/./urandom // reduce Tomcat startup time : http://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
-Xss64kb // set stack size
-XX:+HeapDumpOnOutOfMemoryError // get a heap dump at the point the application crashes
-XX:+PerfDisableSharedMem // disable JVM exporting statistics to a file in /tmp, causing pauses of 0.1-1s during garbage collection
-Xloggc:logfilename.log // log GC status to a file with time stamps
kill -3 <pid> // dump a full stack trace and heap summary, including generational garbage collection details

// String / ByteString correct conversion :
String asString = new String( byteString.getArray(), "UTF-8" );
ByteString asBytes = ByteString.wrap( string.getBytes( "UTF-8" );
"" == (new String("")).intern() // Using .intern() can be risky + interned Strings live in PermGen space
"regexp".matches("regex.*") // Also: .indexOf .lastIndexOf .substring .trim .split
// char[] >slightly> String for passwords: http://stackoverflow.com/a/8881376

Cloneable interface // Somehow broken because it does not have a 'clone()' method and Object.clone() is protected - Do not implement it

java.util.AbstractSequentialList, AbstractList // Skeleton implementations for list classes

Integer.bitCount
BigDecimal("1.6") // instead of float, for exact decimal arithmetic + do not use BigDecimal(1.6)

public <T> T[] toArray(T[] array) {
    if (array.lengh < size)
        array = (T[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size);
import java.util.Arrays;
Arrays.toString(myCollection.toArray()) // or 'deepToString' to deal with nested arrays
Collections.shuffle(myList)
           .sort(myList) // stable Python TimSort
           .binarySearch(myList, key)
           .frequency(myCollection, objToCount)
           .indexkOfSubList .lastIndexOfSubList
           .nCopies(count, obj) // -> list
           .reverse(myList) .rotate(myList)
           .replaceAll(myList, oldVal, newVal)
           .unmodifiable[Sorted]Collection // -> collection view

assert *<condition>* : *<object>* // Don't forget to -enableassertions

System.nanoTime() // 30 ns latency. Use it wisely: the latency, granularity, and scalability effects introduced may and will affect your measurements -> probably not true anymore in Java8
Joda.time

// Reflection
import java.lang.reflect.Field;
import java.lang.reflect.Method;
java.lang.refect.Constructor.newInstance > MyClass.newInstance // it can throw undeclared checked exceptions !

// Methods/attribute privacy only true at compile time : if one recompile a class into bytecode with all private, it will still work !

// Google Guava, + cf. Concurrency.md
Optional<> : Optional.of(...), Optional.absent(), opt.isPresent(), opt.get()
public String toString() { Objects.toStringHelper(getClass()).add("Attr", attr).add(...); }
ImmutableSet<V>
ImmutableMap.Builder<K,V>
// + Guice (dependency injection) & GuiceBerry for testing it

// http://docs.oracle.com/javase/tutorial/essential/io/formatting.html
System.out.println("a" + "b"); System.out.format("a%s", "b");

// Spring
has support for events
SpringFox to auto-generate Swagger from REST controllers @annotations
<!-- DEBUG: provides /info /mappings /trace /env /configprops /metrics /health /dump /beans -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

// Dozer : forbidden @VSCT -> bad experiences

nilhcem/FakeSMTP // very useful local email server

SIGAR  // == df du free ifconfig iostat netstat ps route top ulimit uptime who : portable interface for gathering system information, used by ElasticSearch

org.json.JSONObject, Argo, Gson, Jackson, JSON.simple
JavaCC // parser generator
antlr // language parser cf. antlr/grammars-v4

static enum Action {
    PUT(ClientPut.class),
    GET(ClientGet.class),
    LIST(ClientList.class);
    final Class< ? extends BackupClient > client;
    private Action(Class< ? extends BackupClient > client) { this.client = client; }
    public static Action fromString(String value) {
        for (Action action : Action.values()) {
            if (action.toString().equalsIgnoreCase(value)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Value " + value + " can not be converted to Action. "
                + "Available values are: " + Arrays.toString(Action.values()));
    }
}
EnumSet.range() .allOf() .clone() .removeAll() .size()

Client client = injector.getInstance(params.action.client);
Method action = Client.class.getMethod(params.action.toString().toLowerCase());
action.invoke(client);

// Jcommander
static Settings parseCommandLineArguments(String[] args) {
    Settings params = new Settings(); // with attributes commented with @Parameter
    JCommander parser = new JCommander(params);
    parser.setProgramName("whatever");
    try {
         parser.parse(args);
    } catch (ParameterException ex) {
        parser.usage();
        Throwables.propagate(ex);
    }
    if (params.help) {
        parser.usage();
    }
    return params;
}

// Java exception handling (use only RuntimeExceptions !!)
public static void main(String[] args) {
    if (this.logger.isDebugEnabled()) {
        this.logger.debug("{} starting with JSON: {}", varName, costlyMethodCallThatGeneratesJsonOnlyInDebug());
    }
    try {
        run(args);
    } catch (InvocationTargetException e) {
        throw (RuntimeException)e.getTargetException();
    } catch (RuntimeException e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        this.logger.error(stackTrace.toString());
        throw e;
    }
    this.logger.info("{} exited sucessfully", PROG_NAME);
}
// Print stack trace outside a 'catch'
for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
    System.out.println(ste);
}

// Use shutdown hooks for behavior that must occur before the VM exist (a simple 'finally' block won't be executed in case of a System.exit
Runtime.getRuntime().addShutdownHook(new Thread() { public void run() { /* clean-up */ } });

/* Useful standard runtime exceptions:
 *  - IllegalArgumentException
 *  - NullPointerException
 *  - IllegalStateException => for class invariants
 *  - UnsupportedOperationException
 *  - NoSuchElementException
 *  - IndexOutOfBoundsException / ArrayIndexOutOfBoundsException
 *  - NumberFormatException
 *  - ProviderException / MissingResourceException(String s, String className, String key)
 *  - TypeNotPresentException / TypeConstraintException / UnknownEntityException
 *  - ClassCastException
 * Less useful but worth knowing:
 *  - BufferOverflowException / BufferUnderflowException
 *  - WrongMethodTypeException
 *  - AnnotationTypeMismatchException(Method element, String foundType) / IncompleteAnnotationException(Class<? extends Annotation> annotationType, String elementName)
 *  - EnumConstantNotPresentException
 *  - ArrayStoreException
 *  - ArithmeticException
 *  - EmptyStackException
 *  - SystemException / SecurityException
 * Complete list : http://docs.oracle.com/javase/7/docs/api/java/lang/RuntimeException.html
 */

// GUI: Swing easy to customize color picker, editable text pane, file chooser, password field, progress bar, spreadsheet table, hierarchical tree...
http://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html


/*********/
// Java 8
/*********/
Arrays.parallelSort(myArray) // break up the collection into several parts sorted independently across a number of cores. Will be less efficient on a loaded machine, depending on its architecture
concurrent Adders > Atomics
SecureRandom.getInstanceStrong() // Secure random generator
Optional<T> -> Optional.ofNullable often better than Optional.of that may throw a NullPointerException

// Streaming API:
Map<String, String[]> DICT = new HashMap<String, String[]>() {{
    put("A", new String[]{"x", "y"});
    put("B", new String[0]);
    put("C", new String[]{"z"});
}};
List.forEach(x -> p -> { System.out.println(p); })
List<Thing> things = DICT.entrySet().stream()
        .map(e -> makeThing(e.getKey(), Arrays.stream(e.getValue())))
        .collect(Collectors.toList());
// FROM & MORE AT: http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
IntStream.range(1, 4) // Another use example: IntStream.iterate(0, i -> i + 2).limit(3)
    .mapToObj(i -> new Foo("Foo" + i))
    .peek(f -> IntStream.range(1, 4)
        .mapToObj(i -> new Bar("Bar" + i + " <- " f.name))
        .forEach(f.bars::add))
    .flatMap(f -> f.bars.stream())
    .forEach(b -> System.out.println(b.name));
    
// try-with-resources: any object that implements java.lang.AutoCloseable, which includes java.io.Closeable ones:
try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    return br.readLine();
}

Function<InputType, ReturnType>.apply(arg) .compose(before) .andThen(after) & static identity()

CompletableFuture > Future
