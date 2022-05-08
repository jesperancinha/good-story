# Java Good Story

---

## How to test and run

1. Make sure you have already installed loom. If not, pickup the root [Makefile](./Makefile) and find the sdk script for your system (I've tested this in Ubuntu and MAC-OS distributions)

2. Setup Java Home temporarily in your console:

```shell
. ./loom-sdk.sh
```

3. Make the build and test

```shell
make build-test
```

4. (optionally) Build manually

```shell
mvn clean install
make run
```
---

## References

#### Links

- [Java Virtual Threads Explained](https://www.javai.net/post/202204/java-virtual-threads-explained/)
- [Creating Virtual Threads with project Loom](https://www.davidvlijmincx.com/posts/create_virtual_threads_with_project_loom/)

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
</div>
