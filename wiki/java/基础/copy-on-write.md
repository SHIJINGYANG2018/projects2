## copy-on-write
### Linux 中的 copy-on-write
```text
fork() 调用一次，执行两次， 启动一个新的进程来执行，这main方法  执行第一次，返回给父线程，返回子进程的pid,  指定第二次返回给子线程，返回 0 ,通常使用返回结果来标识，那个是子线程执行，那个是父线程执行
```
### Redis 中的 copy-on-write
```text

```