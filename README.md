# good-story

---

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/good-story)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Good%20Story%20🐉&color=informational)](https://github.com/jesperancinha/good-story)

[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e3c9eeb7325842b4852bd6065a3007fa)](https://www.codacy.com/gh/jesperancinha/good-story/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/good-story&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/jesperancinha/good-story?branch=main)](https://bettercodehub.com/results/jesperancinha/good-story)
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

🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧 !!! UNDER CONSTRUCTION !!! 🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧

An investigation and comparison between Kotlin and Java on an engineering level. Since beauty is in the eye of the beholder, this repository is not meant to evaluate Java or Kotlin on an aesthetic level.

## 1.  Introduction

This project started as an idea to explore differences in performance between different projects. It has grown now into a full fledge comparison between Java and Kotlin and ONLY for <b>engineering purposes</b>.

I won't dive into style IT discussions in this project. And of course the `elegant` card is not the focal point here.

If you understand these terms, then I think you'll find this project interesting.

The way we are going to compare performance, response times and memory usage is going to be by processing a small novel I'm developing in the [GoodStory](https://jesperancinha.github.io/good-story/good.story/GoodStory.html) file. We will apply algorithms to it, make objects, and explore the limits of our own machines.

Further, you may find that I'm using `for`, `while` and a `do..while` in both `Java` and `Kotlin` implementations. This is purposely done in some cases. I may find that in some cases there is no point in using the already implemented algorithm for some situations. Especially if they can be implemented in exactly the same way in `Java` or `Kotlin` without the use of a DSL.

`Lombok` usage will be avoided as much as possible and so please do not be surprised if you get to see any manual implementation of the builder pattern.

## 2.  Environment

[Java Project Loom](https://wiki.openjdk.java.net/display/loom/Main) is itself a JDK and in order to use it, you need first to [install it](https://wiki.openjdk.java.net/display/loom/Main)

>check the [Makefile](./Makefile) for the most appropriate script for your operating system.

```shell
make sdk-install
```

## 3.  Tech comparisons

We cannot 100% compare Kotlin and Java in a direct way, but we will compare them using their best performant version:

| Solution                                                               | VM Name                           | VM Version                                                 | Base JDK | Type   |
|------------------------------------------------------------------------|-----------------------------------|------------------------------------------------------------|----------|--------|
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) | [Loom](http://jdk.java.net/loom/) | 19-loom+6-625 (2022/4/29)                                  | 19       | JDK    |
| [Project Loom](https://wiki.openjdk.java.net/display/loom/Main)        | [Loom](http://jdk.java.net/loom/) | 19-loom+6-625 (2022/4/29)                                  | 19       | JDK    |

Please keep checking the evolution of file [Log.md](Log.md) if you want to keep up to date with the results of the comparisons. It gets updated per commit.
You can also check the detailed file for [Java](./dump/java/Log.md) and the detailed file for [Kotlin](./dump/kotlin/Log.md).

## 4.  Actions

This project makes usage of one single action: 

-   [Jesperancinha GitHub Action for LoomJDK](https://github.com/JEsperancinhaOrg/loom-action)

## 5.  How top run

You can run the whole test in one go by running:

```shell
make clean build-run
```

For heavier tests please run:

```shell
make clean build-run-loccal
```

Just make sure that loom-jdk is setup in [/loom-jdk](loom-jdk) at the root of this project. Check the sdk scripts in the [Makefile](./Makefile) for distribution choices for the Loom JDK.

## 6.  [Coffee Sessions](https://www.buymeacoffee.com/jesperancinha/posts) ☕️

-   [How I created a JDK 19 Loom GitHub Action](https://www.buymeacoffee.com/jesperancinha/how-i-created-jdk-19-loom-github-action)

## 7.  References

#### Online

- [Scala (programming language)](https://en.wikipedia.org/wiki/Scala_(programming_language))
- [History of Scala](https://www.javatpoint.com/history-of-scala)
- [Project Loom (Java 19)](https://github.com/openjdk/loom)
- [Java (programming language)](https://en.wikipedia.org/wiki/Java_(programming_language))
- [Project Loom: Fibers and Continuations for the Java Virtual Machine](https://cr.openjdk.java.net/~rpressler/loom/Loom-Proposal.html)
- [Coming to Java 19: Virtual threads and platform threads](https://blogs.oracle.com/javamagazine/post/java-loom-virtual-threads-platform-threads)
- [STAR method interview](https://www.youtube.com/results?search_query=star+method+interview)
- [Amazon Leadership Examples on Youtube](https://www.youtube.com/results?search_query=amazon+leadership+examples)
- [System Design Messenger on Youtube](https://www.youtube.com/results?search_query=system+design+messenger)
- [Behavioral Interview Prep](https://www.algoexpert.io/behavioral-interviews)
- [System - Design - Primer](https://github.com/donnemartin/system-design-primer)
- [Grokking the System Design Interview](https://www.educative.io/courses/grokking-the-system-design-interview)
- [Grokking the Coding Interview: Patterns for Coding Questions](https://designgurus.org/course/grokking-the-coding-interview)
- [Big O Notation and Time/Space Complexity](https://medium.com/swlh/big-o-notation-and-time-space-complexity-1806936e6330)
- [Analysis of Algorithms | Big-O analysis](https://www.geeksforgeeks.org/analysis-algorithms-big-o-analysis/)
- [BTech smart class - Introduction to algorithms](http://www.btechsmartclass.com/data_structures/introduction-to-algorithms.html)
- [Splay tree](https://en.wikipedia.org/wiki/Splay_tree)
- [Big-O Quiz](https://bigoquiz.com/home)
- [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes)
- [Binary search tree](https://en.wikipedia.org/wiki/Binary_search_tree)
- [The height of an AVL tree containing n nodes](http://www.mathcs.emory.edu/~cheung/Courses/253/Syllabus/Trees/AVL-height.html)
- [AVL Tree](https://www.programiz.com/dsa/avl-tree)
- [Data Structure and Algorithms - AVL Trees](https://www.tutorialspoint.com/data_structures_algorithms/avl_tree_algorithm.htm)
- [AVL Tree Insertion, Rotation, and Balance Factor Explained](https://www.freecodecamp.org/news/avl-tree-insertion-rotation-and-balance-factor/)
- [What is an AVL tree?](https://www.educative.io/edpresso/what-is-an-avl-tree)
- [AVL Tree program in Java](https://www.javatpoint.com/avl-tree-program-in-java)
- [How to insert Strings into an AVL Tree](https://www.geeksforgeeks.org/how-to-insert-strings-into-an-avl-tree/)
- [Big O Factorial Time Complexity](https://jarednielsen.com/big-o-factorial-time-complexity/)
- [BIG O NOTATION PRIMER](https://www.topcoder.com/blog/big-o-notation-primer)
- [What would cause an algorithm to have O(log log n) complexity?](https://stackoverflow.com/questions/16472012/what-would-cause-an-algorithm-to-have-olog-log-n-complexity)
- [What does O(log n) mean exactly?](https://stackoverflow.com/questions/2307283/what-does-olog-n-mean-exactly/2307314#2307314)
- [Big O Notation, Part Two: Space Complexity](https://careerkarma.com/blog/big-o-notation-space/)
- [ALGORITHMS IN KOTLIN, BIG-O-NOTATION, PART 1/7](http://developerlife.com/2018/08/16/algorithms-in-kotlin-1/)
- [Big O Cheat Sheet](https://www.bigocheatsheet.com/)
- [Time complexity vs. space complexity](https://www.educative.io/edpresso/time-complexity-vs-space-complexity)
- [Complexity and Big-O Notation](https://pages.cs.wisc.edu/~vernon/cs367/notes/3.COMPLEXITY.html)
- [Going inside Java’s Project Loom and virtual threads](https://blogs.oracle.com/javamagazine/post/going-inside-javas-project-loom-and-virtual-threads)
- [Kotlin Coroutines dispatchers](https://kt.academy/article/cc-dispatchers)
- [VisualVM](https://visualvm.github.io/)
- [Picocli](https://picocli.info/)
- [Issues with Spring, how Micronaut solves it, and latter’s support for GraalVM](https://medium.com/dev-genius/micronaut-application-comparison-with-spring-boot-and-support-for-graalvm-d0fb0d933d55)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
- [Java Project Loom](https://wiki.openjdk.java.net/display/loom/Main)
- [GitHub Action for GraalVM](https://github.com/marketplace/actions/github-action-for-graalvm)
- [Project Loom: Understand the new Java concurrency model](https://www.infoworld.com/article/3652596/project-loom-understand-the-new-java-concurrency-model.html)
- [Going inside Java’s Project Loom and virtual threads](https://blogs.oracle.com/javamagazine/post/going-inside-javas-project-loom-and-virtual-threads)


#### Videos

<div align="center">
      <a title="The Complete Guide to Big O Notation & Complexity Analysis for Algorithms: Part 1 of 2" href="https://www.youtube.com/watch?v=HfIH3czXc-8">
     <img 
          src="https://img.youtube.com/vi/HfIH3czXc-8/0.jpg" 
          style="width:20%;">
      </a>
</div>

#### Books

-   Mcdowell, G. (23rd April 2020). <i>Cracking the Coding Interview 189 Programming Questions and Solutions</i>. (6th Edition). CareerCup
-   Cormen, T. Leiserson, C. Rivest, R. Stein, C. (2009). <i>Introduction to Algorithms</i>. (Third Edition). MIT Press

## About me 👨🏽‍💻🚀🏳️‍🌈

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/JEOrgLogo-20.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://medium.com/@jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bmc-20.png "Buy me a Coffe")](https://www.buymeacoffee.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/credly-20.png "Credly")](https://www.credly.com/users/joao-esperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=joaofilipesabinoesperancinha.nl&color=6495ED "João Esperancinha Homepage")](https://joaofilipesabinoesperancinha.nl/)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social "GitHub")](https://github.com/jesperancinha)
[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social "Twitter")](https://twitter.com/joaofse)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=yellow "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Articles&message=Across%20The%20Web&color=purple)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Articles.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Webapp&message=Image%20Train%20Filters&color=6495ED)](http://itf.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=red "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=red "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/coursera-20.png "Coursera")](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/google-apps-20.png "Google Apps")](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/sonatype-20.png "Sonatype Search Repos")](https://search.maven.org/search?q=org.jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/docker-20.png "Docker Images")](https://hub.docker.com/u/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/stack-overflow-20.png)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/reddit-20.png "Reddit")](https://www.reddit.com/user/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/devto-20.png "Dev To")](https://dev.to/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackernoon-20.jpeg "Hackernoon")](https://hackernoon.com/@jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codeproject-20.png "Code Project")](https://www.codeproject.com/Members/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/github-20.png "GitHub")](https://github.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bitbucket-20.png "BitBucket")](https://bitbucket.org/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/gitlab-20.png "GitLab")](https://gitlab.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/free-code-camp-20.jpg "FreeCodeCamp")](https://www.freecodecamp.org/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackerrank-20.png "HackerRank")](https://www.hackerrank.com/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/leet-code-20.png "LeetCode")](https://leetcode.com/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codebyte-20.png "Codebyte")](https://coderbyte.com/profile/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codewars-20.png "CodeWars")](https://www.codewars.com/users/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codepen-20.png "Code Pen")](https://codepen.io/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hacker-news-20.png "Hacker News")](https://news.ycombinator.com/user?id=jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/infoq-20.png "InfoQ")](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/linkedin-20.png "LinkedIn")](https://www.linkedin.com/in/joaoesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/xing-20.png "Xing")](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/tumblr-20.png "Tumblr")](https://jofisaes.tumblr.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/pinterest-20.png "Pinterest")](https://nl.pinterest.com/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/quora-20.png "Quora")](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)
[![VMware Spring Professional 2021](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/vmware-spring-professional-2021-20.png "VMware Spring Professional 2021")](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
[![Oracle Certified Professional, JEE 7 Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-ee-7-application-developer-20.png "Oracle Certified Professional, JEE7 Developer")](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![Oracle Certified Professional, Java SE 11 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-se-11-developer-20.png "Oracle Certified Professional, Java SE 11 Programmer")](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![IBM Cybersecurity Analyst Professional](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/ibm-cybersecurity-analyst-professional-certificate-20.png "IBM Cybersecurity Analyst Professional")](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
[![Certified Advanced JavaScript Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/cancanit-badge-1462-20.png "Certified Advanced JavaScript Developer")](https://cancanit.com/certified/1462/)
[![Certified Neo4j Professional](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/professional_neo4j_developer-20.png "Certified Neo4j Professional")](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
[![Deep Learning](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/deep-learning-20.png "Deep Learning")](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
