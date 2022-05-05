# good-story

🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧 !!! UNDER CONSTRUCTION !!! 🚧🚧🚧🚧🚧🚧🚧🚧🚧🚧

An investigation and comparison between Kotlin and Java on an engineering level. Since beauty is in the eye of the beholder, this repository is not meant to evaluate Java or Kotlin on an aesthetic level.

## Introduction

This project started as an idea to explore differences in performance between different projects. It has grown now into a full fledge comparison between Java and Kotlin and ONLY for <b>engineering purposes</b>.

I won't dive into style IT discussions in this project. And of course the `elegant` card is not the focal point here.

If you understand these terms, then I think you'll find this project interesting.

## Environment

#### [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)

Kotlin's coroutines work on any JDK and they can be used on most JDK's out there.

Since I'm looking for the most efficient and performing solutions, we are going to use GraalVM in this project. Because of this, either you need to install [GraalVM](https://www.graalvm.org/) manually, or use sdk-man to install it:

```shell
sdk install java 22.1.0.r17-grl
sdk use java 22.1.0.r17-grl
```

#### [Java Project Loom](https://wiki.openjdk.java.net/display/loom/Main)

Project Loom is itself a JDK and in order to use it, you need first to [install it](https://wiki.openjdk.java.net/display/loom/Main)

>check the [Makefile](./Makefile) for the most appropriate script for your operating system.
```shell
make sdk-install
```

## Tech comparisons

We cannot 100% compare Kotlin and Java in a direct way, but we will compare them using their best performant version:

| Solution                                                               | VM Name                           | VM Version                                                 | Base JDK | Type   |
|------------------------------------------------------------------------|-----------------------------------|------------------------------------------------------------|----------|--------|
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) | [GraalVM](22.1.0)                 | [22.1.0](https://www.graalvm.org/release-notes/22_1/#2210) | 17       | Module |
| [Project Loom](https://wiki.openjdk.java.net/display/loom/Main)        | [Loom](http://jdk.java.net/loom/) | 19-loom+6-625 (2022/4/29)                                  | 19       | JDK    |

## Playbook

```shell
gradle init
```

## References

- [Issues with Spring, how Micronaut solves it, and latter’s support for GraalVM](https://medium.com/dev-genius/micronaut-application-comparison-with-spring-boot-and-support-for-graalvm-d0fb0d933d55)
