# good-story

---

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/good-story)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Good%20Story%20🐉&color=informational)](https://github.com/jesperancinha/good-story)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

[![GS - Report Run](https://github.com/jesperancinha/good-story/actions/workflows/good-story-report-run.yml/badge.svg)](https://github.com/jesperancinha/good-story/actions/workflows/good-story-report-run.yml)

[![GS - Coroutines Intro - CPP](https://github.com/jesperancinha/good-story/actions/workflows/good-story-cpp.yml/badge.svg)](https://github.com/jesperancinha/good-story/actions/workflows/good-story-cpp.yml)

[![GS - Coroutines Intro - JVM](https://github.com/jesperancinha/good-story/actions/workflows/good-story-intro.yml/badge.svg)](https://github.com/jesperancinha/good-story/actions/workflows/good-story-intro.yml)

[![GS - Java](https://github.com/jesperancinha/good-story/actions/workflows/good-story-java.yml/badge.svg)](https://github.com/jesperancinha/good-story/actions/workflows/good-story-java.yml)
[![GS - Kotlin](https://github.com/jesperancinha/good-story/actions/workflows/good-story-kotlin.yml/badge.svg)](https://github.com/jesperancinha/good-story/actions/workflows/good-story-kotlin.yml)
[![GS - Kotlin - Loom](https://github.com/jesperancinha/good-story/actions/workflows/good-story-kotlin-loom.yml/badge.svg)](https://github.com/jesperancinha/good-story/actions/workflows/good-story-kotlin-loom.yml)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e3c9eeb7325842b4852bd6065a3007fa)](https://www.codacy.com/gh/jesperancinha/good-story/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/good-story&amp;utm_campaign=Badge_Grade)
[![Known Vulnerabilities](https://snyk.io/test/github/jesperancinha/good-story/badge.svg)](https://snyk.io/test/github/jesperancinha/good-story)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/e3c9eeb7325842b4852bd6065a3007fa)](https://www.codacy.com/gh/jesperancinha/good-story/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jesperancinha/good-story&utm_campaign=Badge_Coverage)
[![codecov](https://codecov.io/gh/jesperancinha/good-story/branch/main/graph/badge.svg?token=yrXgrznrnG)](https://codecov.io/gh/jesperancinha/good-story)
[![Coverage Status](https://coveralls.io/repos/github/jesperancinha/good-story/badge.svg?branch=main)](https://coveralls.io/github/jesperancinha/good-story?branch=main)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/good-story.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/good-story.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/good-story.svg)](#)

---

## Technologies used

---

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kotlin-50.png "Kotlin")](https://kotlinlang.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/apache-maven-50.png "Maven")](https://maven.apache.org/)

---

## 1.  Introduction

An investigation and comparison between Kotlin and Java on an engineering level. Since beauty is in the eye of the
beholder, this repository is not meant to evaluate Java or Kotlin on an aesthetic level.

This project started as an idea to explore differences in performance between different projects. It has grown now into
a full fledge comparison between Java and Kotlin and ONLY for <b>engineering purposes</b>.

I won't dive into style IT discussions in this project. And of course the `elegant` card is not the focal point here.

If you understand these terms, then I think you'll find this project interesting.

The way we are going to compare performance, response times and memory usage is going to be by processing a small novel
I'm developing in the [GoodStory](https://jesperancinha.github.io/good-story/good.story/GoodStory.html) file. We will
apply algorithms to it, make objects, and explore the limits
of our own machines.

Further, you may find that I'm using `for`, `while` and a `do..while` in both `Java` and `Kotlin` implementations. This
is purposely done in some cases. I may find that in some cases there is no point in using the already implemented
algorithm for some situations. Especially if they can be
implemented in exactly the same way in `Java` or `Kotlin` without the use of a DSL.

`Lombok` usage will be avoided as much as possible and so please do not be surprised if you get to see any manual
implementation of the builder pattern.

#### 1.1. About performance

It caught my attention recently that in many blogs and videos, people are stressing out that coroutines and virtual
threads are not about performance. That is of course directly true. What they are about is making a better use of a
resource that has been there for ages. Sometimes called Continuation,
sometimes called coroutines, but this is a concept that has been here for a long time. This repository is, regardless,
about performance, because if we use our resources better, then that will ultimately result in better performance as a
whole. So this repo is not about comparing the individual
performance of one coroutine to one virtual thread. They work as a whole, both switch context, both can be suspended and
both have different states. So I am measuring, or better yet, attempting to measure performance on a local machine and
try to see if there is any significant difference there.
These tests are also allowing to exhaust resources and therefore forcing each implementation to manage itself. It's here
where the performance study comes in.

This repo is the official support repo to my article on medium:

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://itnext.io/kotlin-coroutines-vs-java-virtual-threads-a-good-story-but-just-that-91038c7d21eb) [Kotlin Coroutines vs Java Virtual Threads — A good story, but just that…](https://itnext.io/kotlin-coroutines-vs-java-virtual-threads-a-good-story-but-just-that-91038c7d21eb)

<div align="center">
      <a title="Kotlin Coroutines vs Java Virtual Threads — A good story, but just that…" href="https://itnext.io/kotlin-coroutines-vs-java-virtual-threads-a-good-story-but-just-that-91038c7d21eb">
     <img 
          src="./docs/article-coroutines-banner.jpeg" 
          style="width:100%;">
      </a>
</div>

#### Stable releases

-   [1.0.0](https://github.com/jesperancinha/good-story/tree/1.0.0) - [315e7260b1214c8be1bc48a4af5446c8ad68bd9f](https://github.com/jesperancinha/good-story/tree/1.0.0) -
  JDK19 runs with [Jesperancinha GitHub Action for LoomJDK](https://github.com/JEsperancinhaOrg/loom-action)
-   [1.0.1](https://github.com/jesperancinha/good-story/tree/1.0.1) - [64fcf38b3448f692353dffcc3a7150bdd1b2b645](https://github.com/jesperancinha/good-story/tree/1.0.1) - JDK19 is now installed as available
-   [1.0.2](https://github.com/jesperancinha/good-story/tree/1.0.1) - [64fcf38b3448f692353dffcc3a7150bdd1b2b645](https://github.com/jesperancinha/good-story/tree/1.0.2) - Adds kotlin-loom with a Coroutine Dispatcher using a Loom Virtual Thread (original idea from [apatrida](https://github.com/apatrida). Check [kotlin-loom-good-story](./kotlin-loom-good-story/Readme.md) for more details)

---

## 2.  Environment

[Java Project Loom](https://wiki.openjdk.java.net/display/loom/Main) is itself a JDK and in order to use it, you need
first to [install it](https://wiki.openjdk.java.net/display/loom/Main)

Be sure to run `. ./sdk19.sh`  before running any of the commands, but only if you don't have JDK19 installed. This
script installs JDK19 using SDK-MAN.


---

## 3.  Tech comparisons

We cannot 100% compare Kotlin and Java in a direct way, but we will compare them using their best performant version.

JDK 19 is now available to install in different ways and it has been released. This means that this project is being updated as standard.

-   [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
-   [Project Loom](https://wiki.openjdk.java.net/display/loom/Main) 

Please keep checking the evolution of file [Log.md](Log.md) if you want to keep up to date with the results of the
comparisons. It gets updated per commit.
You can also check the detailed file for [Java](./dump/java/Log.md) and the detailed file
for [Kotlin](./dump/kotlin/Log.md).

---

## 4.  How top run

You can run the whole test in one go by running:

```shell
make clean build-run
```

For heavier tests please run:

```shell
make clean build-run-loccal
```

Just make sure that loom-jdk is setup in [/loom-jdk](loom-jdk) at the root of this project. Check the sdk scripts in
the [Makefile](./Makefile) for distribution choices for the Loom JDK.

---

## 5.  [Coffee Sessions](https://www.buymeacoffee.com/jesperancinha/posts) ☕️

-   [How I created a JDK 19 Loom GitHub Action](https://www.buymeacoffee.com/jesperancinha/how-i-created-jdk-19-loom-github-action)

---

## 6.  References

#### Online

-   [Coroutines are not about multi-threading at all](https://elizarov.medium.com/coroutines-are-not-about-multi-threading-at-all-1b2c6e97ec02)
-   [Structured concurrency by Roman Elizarov](https://elizarov.medium.com/structured-concurrency-722d765aa952)
-   [libdill: Structured Concurrency for C](http://libdill.org/structured-concurrency.html)
-   [Java Virtual Threads by Gaetano Piazzolla](https://medium.com/dev-genius/java-virtual-threads-715c162c6c39)
-   [Carrier Kernel Thread Pinning of Virtual Threads (Project Loom)](https://paluch.biz/blog/183-carrier-kernel-thread-pinning-of-virtual-threads-project-loom.html)
-   [GitHub's "Our response to the war in Ukraine" 🇺🇦](https://github.blog/2022-03-02-our-response-to-the-war-in-ukraine/)
-   [Why Continuations are Coming to Java](https://www.infoq.com/presentations/continuations-java/)
-   [Coroutines overview](https://kotlinlang.org/docs/coroutines-overview.html)
-   [JetBrains’ Statement on Ukraine 🇺🇦](https://blog.jetbrains.com/blog/2022/03/11/jetbrains-statement-on-ukraine/)
-   [Scala (programming language)](https://en.wikipedia.org/wiki/Scala_(programming_language))
-   [History of Scala](https://www.javatpoint.com/history-of-scala)
-   [Project Loom (Java 19)](https://github.com/openjdk/loom)
-   [Java (programming language) ☕](https://en.wikipedia.org/wiki/Java_(programming_language))
-   [Project Loom: Fibers and Continuations for the Java Virtual Machine](https://cr.openjdk.java.net/~rpressler/loom/Loom-Proposal.html)
-   [Coming to Java 19: Virtual threads and platform threads](https://blogs.oracle.com/javamagazine/post/java-loom-virtual-threads-platform-threads)
-   [STAR method interview ✨](https://www.youtube.com/results?search_query=star+method+interview)
-   [Amazon Leadership Examples on Youtube](https://www.youtube.com/results?search_query=amazon+leadership+examples)
-   [System Design Messenger on Youtube](https://www.youtube.com/results?search_query=system+design+messenger)
-   [Behavioral Interview Prep](https://www.algoexpert.io/behavioral-interviews)
-   [System - Design - Primer](https://github.com/donnemartin/system-design-primer)
-   [Grokking the System Design Interview](https://www.educative.io/courses/grokking-the-system-design-interview)
-   [Grokking the Coding Interview: Patterns for Coding Questions](https://designgurus.org/course/grokking-the-coding-interview)
-   [Big O Notation and Time/Space Complexity](https://medium.com/swlh/big-o-notation-and-time-space-complexity-1806936e6330)
-   [Analysis of Algorithms | Big-O analysis](https://www.geeksforgeeks.org/analysis-algorithms-big-o-analysis/)
-   [BTech smart class - Introduction to algorithms](http://www.btechsmartclass.com/data_structures/introduction-to-algorithms.html)
-   [Splay tree](https://en.wikipedia.org/wiki/Splay_tree)
-   [Big-O Quiz](https://bigoquiz.com/home)
-   [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes)
-   [Binary search tree](https://en.wikipedia.org/wiki/Binary_search_tree)
-   [The height of an AVL tree containing n nodes](http://www.mathcs.emory.edu/~cheung/Courses/253/Syllabus/Trees/AVL-height.html)
-   [AVL Tree](https://www.programiz.com/dsa/avl-tree)
-   [Data Structure and Algorithms - AVL Trees](https://www.tutorialspoint.com/data_structures_algorithms/avl_tree_algorithm.htm)
-   [AVL Tree Insertion, Rotation, and Balance Factor Explained](https://www.freecodecamp.org/news/avl-tree-insertion-rotation-and-balance-factor/)
-   [What is an AVL tree? 🌳](https://www.educative.io/edpresso/what-is-an-avl-tree)
-   [AVL Tree program in Java](https://www.javatpoint.com/avl-tree-program-in-java)
-   [How to insert Strings into an AVL Tree](https://www.geeksforgeeks.org/how-to-insert-strings-into-an-avl-tree/)
-   [Big O Factorial Time Complexity](https://jarednielsen.com/big-o-factorial-time-complexity/)
-   [BIG O NOTATION PRIMER](https://www.topcoder.com/blog/big-o-notation-primer)
-   [What would cause an algorithm to have O(log log n) complexity?](https://stackoverflow.com/questions/16472012/what-would-cause-an-algorithm-to-have-olog-log-n-complexity)
-   [What does O(log n) mean exactly?](https://stackoverflow.com/questions/2307283/what-does-olog-n-mean-exactly/2307314#2307314)
-   [Big O Notation, Part Two: Space Complexity](https://careerkarma.com/blog/big-o-notation-space/)
-   [ALGORITHMS IN KOTLIN, BIG-O-NOTATION, PART 1/7](http://developerlife.com/2018/08/16/algorithms-in-kotlin-1/)
-   [Big O Cheat Sheet](https://www.bigocheatsheet.com/)
-   [Time complexity vs. space complexity](https://www.educative.io/edpresso/time-complexity-vs-space-complexity)
-   [Complexity and Big-O Notation](https://pages.cs.wisc.edu/~vernon/cs367/notes/3.COMPLEXITY.html)
-   [Going inside Java’s Project Loom and virtual threads](https://blogs.oracle.com/javamagazine/post/going-inside-javas-project-loom-and-virtual-threads)
-   [Kotlin Coroutines dispatchers](https://kt.academy/article/cc-dispatchers)
-   [VisualVM](https://visualvm.github.io/)
-   [Picocli](https://picocli.info/)
-   [Issues with Spring, how Micronaut solves it, and latter’s support for GraalVM](https://medium.com/dev-genius/micronaut-application-comparison-with-spring-boot-and-support-for-graalvm-d0fb0d933d55)
-   [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
-   [Java Project Loom](https://wiki.openjdk.java.net/display/loom/Main)
-   [GitHub Action for GraalVM](https://github.com/marketplace/actions/github-action-for-graalvm)
-   [Project Loom: Understand the new Java concurrency model](https://www.infoworld.com/article/3652596/project-loom-understand-the-new-java-concurrency-model.html)
-   [Going inside Java’s Project Loom and virtual threads](https://blogs.oracle.com/javamagazine/post/going-inside-javas-project-loom-and-virtual-threads)

#### Videos

<div align="center">
      <a title="The Complete Guide to Big O Notation & Complexity Analysis for Algorithms: Part 1 of 2" href="https://www.youtube.com/watch?v=HfIH3czXc-8">
     <img 
          src="https://img.youtube.com/vi/HfIH3czXc-8/0.jpg" 
          style="width:20%;">
      </a>
      <a title="P99 CONF: High-Performance Networking Using eBPF, XDP, and io_uring" href="https://www.youtube.com/watch?v=dWfA5460HYU">
     <img 
          src="https://img.youtube.com/vi/dWfA5460HYU/0.jpg" 
          style="width:20%;">
      </a>
</div>

#### Books

-   Mcdowell, G. (23rd April 2020). <i>Cracking the Coding Interview 189 Programming Questions and Solutions</i>. (6th
  Edition). CareerCup
-   Cormen, T. Leiserson, C. Rivest, R. Stein, C. (2009). <i>Introduction to Algorithms</i>. (Third Edition). MIT Press

---

## About me

<div align="center">

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-100/JEOrgLogo-27.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![](https://img.shields.io/badge/youtube-%230077B5.svg?style=for-the-badge&logo=youtube&color=FF0000)](https://www.youtube.com/@joaoesperancinha)
[![](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)](https://medium.com/@jofisaes)
[![](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-%230077B5.svg?style=for-the-badge&logo=buymeacoffee&color=yellow)](https://www.buymeacoffee.com/jesperancinha)
[![](https://img.shields.io/badge/Twitter-%230077B5.svg?style=for-the-badge&logo=twitter&color=white)](https://twitter.com/joaofse)
[![](https://img.shields.io/badge/Mastodon-%230077B5.svg?style=for-the-badge&logo=mastodon&color=afd7f7)](https://masto.ai/@jesperancinha)
[![](https://img.shields.io/badge/Sessionize-%230077B5.svg?style=for-the-badge&logo=sessionize&color=cffff6)](https://sessionize.com/joao-esperancinha)
[![](https://img.shields.io/badge/Instagram-%230077B5.svg?style=for-the-badge&logo=instagram&color=purple)](https://www.instagram.com/joaofisaes)
[![](https://img.shields.io/badge/Tumblr-%230077B5.svg?style=for-the-badge&logo=tumblr&color=192841)](https://jofisaes.tumblr.com)
[![](https://img.shields.io/badge/Spotify-1ED760?style=for-the-badge&logo=spotify&logoColor=white)](https://open.spotify.com/user/jlnozkcomrxgsaip7yvffpqqm)
[![](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/joaoesperancinha/)
[![](https://img.shields.io/badge/Xing-%230077B5.svg?style=for-the-badge&logo=xing&color=064e40)](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![](https://img.shields.io/badge/YCombinator-%230077B5.svg?style=for-the-badge&logo=ycombinator&color=d0d9cd)](https://news.ycombinator.com/user?id=jesperancinha)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
[![](https://img.shields.io/badge/bitbucket-%230077B5.svg?style=for-the-badge&logo=bitbucket&color=blue)](https://bitbucket.org/jesperancinha)
[![](https://img.shields.io/badge/gitlab-%230077B5.svg?style=for-the-badge&logo=gitlab&color=orange)](https://gitlab.com/jesperancinha)
[![](https://img.shields.io/badge/Stack%20Overflow-%230077B5.svg?style=for-the-badge&logo=stackoverflow&color=5A5A5A)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![](https://img.shields.io/badge/Credly-%230077B5.svg?style=for-the-badge&logo=credly&color=064e40)](https://www.credly.com/users/joao-esperancinha)
[![](https://img.shields.io/badge/Coursera-%230077B5.svg?style=for-the-badge&logo=coursera&color=000080)](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![](https://img.shields.io/badge/Docker-%230077B5.svg?style=for-the-badge&logo=docker&color=cyan)](https://hub.docker.com/u/jesperancinha)
[![](https://img.shields.io/badge/Reddit-%230077B5.svg?style=for-the-badge&logo=reddit&color=black)](https://www.reddit.com/user/jesperancinha/)
[![](https://img.shields.io/badge/Hackernoon-%230077B5.svg?style=for-the-badge&logo=hackernoon&color=0a5d00)](https://hackernoon.com/@jesperancinha)
[![](https://img.shields.io/badge/Code%20Project-%230077B5.svg?style=for-the-badge&logo=codeproject&color=063b00)](https://www.codeproject.com/Members/jesperancinha)
[![](https://img.shields.io/badge/Free%20Code%20Camp-%230077B5.svg?style=for-the-badge&logo=freecodecamp&color=0a5d00)](https://www.freecodecamp.org/jofisaes)
[![](https://img.shields.io/badge/Hackerrank-%230077B5.svg?style=for-the-badge&logo=hackerrank&color=1e2f97)](https://www.hackerrank.com/jofisaes)
[![](https://img.shields.io/badge/LeetCode-%230077B5.svg?style=for-the-badge&logo=leetcode&color=002647)](https://leetcode.com/jofisaes)
[![](https://img.shields.io/badge/Codewars-%230077B5.svg?style=for-the-badge&logo=codewars&color=722F37)](https://www.codewars.com/users/jesperancinha)
[![](https://img.shields.io/badge/CodePen-%230077B5.svg?style=for-the-badge&logo=codepen&color=black)](https://codepen.io/jesperancinha)
[![](https://img.shields.io/badge/HackerEarth-%230077B5.svg?style=for-the-badge&logo=hackerearth&color=00035b)](https://www.hackerearth.com/@jofisaes)
[![](https://img.shields.io/badge/Khan%20Academy-%230077B5.svg?style=for-the-badge&logo=khanacademy&color=00035b)](https://www.khanacademy.org/profile/jofisaes)
[![](https://img.shields.io/badge/Pinterest-%230077B5.svg?style=for-the-badge&logo=pinterest&color=FF0000)](https://nl.pinterest.com/jesperancinha)
[![](https://img.shields.io/badge/Quora-%230077B5.svg?style=for-the-badge&logo=quora&color=FF0000)](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)
[![](https://img.shields.io/badge/Google%20Play-%230077B5.svg?style=for-the-badge&logo=googleplay&color=purple)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
| [Sonatype Search Repos](https://search.maven.org/search?q=org.jesperancinha)
| [Dev.TO](https://dev.to/jofisaes)
| [Codebyte](https://coderbyte.com/profile/jesperancinha)
| [InfoQ](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![](https://img.shields.io/badge/OCP%20Java%2011-%230077B5.svg?style=for-the-badge&logo=oracle&color=064e40)](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![](https://img.shields.io/badge/OCP%20JEE%207-%230077B5.svg?style=for-the-badge&logo=oracle&color=064e40)](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![](https://img.shields.io/badge/VMWare%20Spring%20Professional%202021-%230077B5.svg?style=for-the-badge&logo=spring&color=064e40)](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
[![](https://img.shields.io/badge/IBM%20Cybersecurity%20Analyst%20Professional-%230077B5.svg?style=for-the-badge&logo=ibm&color=064e40)](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
[![](https://img.shields.io/badge/Deep%20Learning-%230077B5.svg?style=for-the-badge&logo=ibm&color=064e40)](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
[![](https://img.shields.io/badge/Certified%20Neo4j%20Professional-%230077B5.svg?style=for-the-badge&logo=neo4j&color=064e40)](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
[![](https://img.shields.io/badge/Certified%20Advanced%20JavaScript%20Developer-%230077B5.svg?style=for-the-badge&logo=javascript&color=064e40)](https://cancanit.com/certified/1462/)
[![](https://img.shields.io/badge/Kong%20Champions-%230077B5.svg?style=for-the-badge&logo=kong&color=064e40)](https://konghq.com/kong-champions)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=064e40&style=for-the-badge "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=064e40&style=for-the-badge "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=orange&style=for-the-badge "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)

</div>
