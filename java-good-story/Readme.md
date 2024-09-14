# Java Good Story

---

## How to test and run

1.  Make sure you have already installed loom. If not, pickup the root [Makefile](./Makefile) and find the sdk script for your system (I've tested this in Ubuntu and MAC-OS distributions)

2.  Setup Java Home temporarily in your console:

```shell
. ./loom-sdk.sh
```

3.  Make the build and test

```shell
make build-test
```

4.  (optionally) Build manually

```shell
mvn clean install
make run
```
---

## Intellij

Easy run:

```shell
-f docs/good.story/GoodStory.md -lf Log.md  -dump dump
```

## Issues

-   [Support Java 19 #20372](https://github.com/gradle/gradle/issues/20372)

## References

#### Links

-   [Java Virtual Threads Explained](https://www.javai.net/post/202204/java-virtual-threads-explained/)
-   [Creating Virtual Threads with project Loom](https://www.davidvlijmincx.com/posts/create_virtual_threads_with_project_loom/)

#### Videos

<div align="center">
      <a title="What is Project Loom? | An introduction by Adam Warski" href="https://www.youtube.com/watch?v=-CPWbB-Pn14">
     <img 
          src="https://img.youtube.com/vi/-CPWbB-Pn14/0.jpg" 
          style="width:20%;">
      </a>
      <a title="Async Programming and Project Loom by Dr Venkat Subramaniam" href="https://www.youtube.com/watch?v=UqlF6Mfhnz0">
     <img 
          src="https://img.youtube.com/vi/UqlF6Mfhnz0/0.jpg" 
          style="width:20%;">
      </a>
      <a title="Java 19 Virtual Threads - JEP Café #11" href="https://www.youtube.com/watch?v=lKSSBvRDmTg">
     <img 
          src="https://img.youtube.com/vi/lKSSBvRDmTg/0.jpg" 
          style="width:20%;">
      </a>
      <a title="Virtual Thread Deep Dive - Inside Java Newscast #23" href="https://www.youtube.com/watch?v=6dpHdo-UnCg">
     <img 
          src="https://img.youtube.com/vi/6dpHdo-UnCg/0.jpg" 
          style="width:20%;">
      </a>
     <a title="Project Loom: Modern Scalable Concurrency for the Java Platform" href="https://www.youtube.com/watch?v=fOEPEXTpbJA">
     <img 
          src="https://img.youtube.com/vi/fOEPEXTpbJA/0.jpg" 
          style="width:20%;">
      </a>
     <a title="Project Loom: Modern Scalable Concurrency for the Java - Ron Pressler" href="https://www.youtube.com/watch?v=23HjZBOIshY">
     <img 
          src="https://img.youtube.com/vi/23HjZBOIshY/0.jpg" 
          style="width:20%;">
      </a>
     <a title="Java 19 - The Best Java Release? - Inside Java Newscast #27" href="https://www.youtube.com/watch?v=UG9nViGZCEw">
     <img 
          src="https://img.youtube.com/vi/UG9nViGZCEw/0.jpg" 
          style="width:20%;">
      </a>
</div>

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
