# good-story

An investigation and comparison between Kotlin and Java on an engineering level. Since beauty is in the eye of the beholder, this repository is not meant to evaluate Java or Kotlin on an aesthetic level.

## Introduction

This project started as an idea to explore differences in performance between different projects. It has grown now into a full fledge comparison between Java and Kotlin and ONLY for engineering purposes.

I won't dive into `botox engineering`, `filler engineering` or `fat injections engineering` IT discussions in this project. And of course the `elegant` card is not the focal point here.

If you understand these terms, then I think you'll find this project interesting.

## Environment

Since I'm looking for the most efficient and performing solutions, we are going to use GraalVM in this project. Because of this, either you need to install [GraalVM](https://www.graalvm.org/) manually, or use sdk-man to install it:

```shell
sdk install java 22.1.0.r17-grl
sdk use java 22.1.0.r17-grl
```
