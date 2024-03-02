### It's a simple Java library to get a random TCP port.

You can reserve a random TCP port:

```java
PortRegistry portRegistry = new PortRegistry();
portRegistry.hold(PortUtil.randomPort());
```
<br />

And you can also use it like this, to reserve a specific TCP port:

```java
PortRegistry portRegistry = new PortRegistry();
portRegistry.hold(8081);
```

<br />

The PortRegistry guarantees that the port won't be used again. You can remove the port from the pool after usage:

```java
PortRegistry portRegistry = new PortRegistry();
portRegistry.release(7081);
```
<br />

Also it's possible to generate random port in specific range:

```java
PortRegistry portRegistry = new PortRegistry();
portRegistry.hold(PortUtil.rangePort(8080,8090));
```
<br />

### The pool is not yet thread-safe.


### How to contribute: 

#### Soon to be described...
