# Good Story Log Book

<ins>2022/05/08</ins>

1. There seems to be an improvement in performance between Java Project Loom and Kotlin Coroutines. In a simple start and stop of processes to increase the value of an atomic integer, 10000000 Virtual Threads took 3 second to complete. The same number of their counterparts in kotlin coroutines took
   on average 6 seconds. It's too early to draw any conclusions, but the results match my expectations.