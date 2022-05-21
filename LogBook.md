# Good Story Log Book

<ins>2022/05/21</ins>

Nothing to do with this project but very cool that we can make math like this now:

**The Cauchy-Schwarz Inequality**

$$\left( \sum_{k=1}^n a_k b_k \right)^2 \leq \left( \sum_{k=1}^n a_k^2 \right) \left( \sum_{k=1}^n b_k^2 \right)$$

[Render mathematical expressions in Markdown](https://github.blog/changelog/2022-05-19-render-mathematical-expressions-in-markdown/)

<ins>2022/05/18</ins>

A bizarre world. If we use `Tools`->`Kotlin`->`Decompile to Java`, we get mega classes in Java that do not compile, have errors and are honestly not Java anyway. Or better yet, it is a Java version of the Kotlin classes who just had too much to drink.

The reality though, is that IntelliJ doesn't really let you see what is happening under the hood. What's really happening is that the Kotlin Compiler has created a bunch of classes:

```shell
ls kotlin-good-story/build/classes/kotlin/main/org/jesperancinha/kotlin/good/story 
```

This is where we can see all classes that have been created:

![alt text](./docs/20220519/FileListing.png)

This IS the place where the actual Java Bytecode exists    

<ins>2022/05/16</ins>

Using Visual VM, it is seen that only 12 Dispatchers are generated per Thread. Is this fixed? Why? What does it mean? Can it be higher? The dispatchers seem to match coroutines.

![alt text](./docs/20220516/VisualVMCatch20220516075334.png)

>12 is the number of cores available. Coroutines make use of all available cores, as needed, in order to segment a system Thread into different separate suspended executions.

Look exactly the same as virtual threads! Except that in Java they are called workers of a ForkJoinPool:

![alt text](./docs/20220516/VisualVMCatch20220516225609.png)

Sounds like the same, only that workers seem to work much faster than coroutines. I guess because they work? And coroutines are like routines? Dunno, but something to investigate further.

<ins>2022/05/13</ins>

1. There is something different about `fun suspend main`. It reflects itself on the duration of coroutines. Figure it out!
>Nothing is wrong with it, actually. It's just that no clear exception is given in the logs. The latency for the next coroutine just gets longer and longer

<ins>2022/05/08</ins>

3. There seems to be an improvement in performance between Java Project Loom and Kotlin Coroutines. In a simple start and stop of processes to increase the value of an atomic integer, 10000000 Virtual Threads took 3 second to complete. The same number of their counterparts in kotlin coroutines took
   on average 6 seconds. It's too early to draw any conclusions, but the results match my expectations.

## TODO List

1. Parse .class generated files and make a text file with all the byte unrecognizable characters removed.